package de.basicbit.system.minecraft.listeners.npc;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.npc.NPC;
import de.basicbit.system.minecraft.events.PlayerNPCRightClickEvent;
import de.basicbit.system.minecraft.events.PlayerPacketInEvent;
import net.minecraft.server.v1_8_R1.EnumEntityUseAction;
import net.minecraft.server.v1_8_R1.Packet;
import net.minecraft.server.v1_8_R1.PacketPlayInUseEntity;

public class NPCInteractListener extends Listener {

    @EventHandler
    public void onPacketIn(PlayerPacketInEvent e) {
        Player p = e.getPlayer();
        Packet packetIn = e.getPacket();

        if (packetIn instanceof PacketPlayInUseEntity) {
            PacketPlayInUseEntity packet = (PacketPlayInUseEntity) packetIn;

            if (packet.a() == EnumEntityUseAction.INTERACT_AT) {
                NPC npc = NPC.getById((int) getValue(packet, "a"));

                if (npc != null) {
                    Bukkit.getPluginManager().callEvent(new PlayerNPCRightClickEvent(p, npc));
                    return;
                }

                e.setCancelled(true);
            }
        }
    }
}