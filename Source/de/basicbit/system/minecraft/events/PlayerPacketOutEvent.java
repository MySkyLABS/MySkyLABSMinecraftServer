package de.basicbit.system.minecraft.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import de.basicbit.system.minecraft.Utils;
import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.Packet;

public class PlayerPacketOutEvent extends PlayerEvent {
    private static HandlerList l = new HandlerList();
    private NetworkManager nm;
    private boolean c = false;
    private Packet p;
 
    public PlayerPacketOutEvent(Player pl, Packet object) {
        super(pl);
        this.p = object;
        this.nm = Utils.getNetworkManager(pl);
    }
 
    public Packet getPacket() {
        return this.p;
    }
 
    public void setPacket(Packet packet) {
        this.p = packet;
    }
 
    public NetworkManager getNetworkManager() {
        return this.nm;
    }
 
    public boolean isCancelled() {
        return this.c;
    }
 
    public void setCancelled(boolean cancel) {
        this.c = cancel;
    }
 
    public HandlerList getHandlers() {
        return l;
    }
 
    public static HandlerList getHandlerList() {
        return l;
    }
}