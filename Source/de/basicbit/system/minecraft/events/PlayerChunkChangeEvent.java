package de.basicbit.system.minecraft.events;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerChunkChangeEvent extends PlayerEvent {
    private static HandlerList l = new HandlerList();
    private boolean c = false;
    private Chunk from;
    private Chunk to;
 
    public PlayerChunkChangeEvent(Player p, Chunk from, Chunk to) {
        super(p);
        this.from = from;
        this.to = to;
    }

    public Chunk getFromChunk() {
        return from;
    }

    public Chunk getToChunk() {
        return to;
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