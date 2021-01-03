package de.basicbit.system.minecraft.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.events.PlayerPacketInEvent;
import de.basicbit.system.minecraft.events.PlayerPacketOutEvent;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.Packet;

public class PlayerPacketListener extends Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        inject(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        uninject(e.getPlayer());
    }

    public void uninject(Player p) {
        log("Uninjecting PacketListener: " + p.getName());

        NetworkManager networkManager = getNetworkManager(p);
        Channel channel = (Channel) getValue(networkManager, "i");
        channel.eventLoop().submit(() -> {
            channel.pipeline().remove(p.getUniqueId().toString());
            return null;
        });
    }

    public void inject(Player p) {
        log("Injecting PacketListener: " + p.getName());

        ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {

            @Override
            public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
                Packet packet = (Packet) obj;
                PlayerPacketInEvent playerPacketInEvent = new PlayerPacketInEvent(p, packet);
                callEvent(playerPacketInEvent);
                packet = playerPacketInEvent.getPacket();
                if (!playerPacketInEvent.isCancelled()) {
                    super.channelRead(channelHandlerContext, packet);
                }
            }

            @Override
            public void write(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) throws Exception {
                Packet packet = (Packet) obj;
                PlayerPacketOutEvent playerPacketOutEvent = new PlayerPacketOutEvent(p, packet);
                callEvent(playerPacketOutEvent);
                packet = playerPacketOutEvent.getPacket();
                if (!playerPacketOutEvent.isCancelled()) {
                    super.write(channelHandlerContext, packet, channelPromise);
                }
            }
        };

        NetworkManager networkManager = getNetworkManager(p);
        Channel channel = (Channel) getValue(networkManager, "i");
        channel.pipeline().addBefore("packet_handler", p.getUniqueId().toString(), channelDuplexHandler);
    }
}