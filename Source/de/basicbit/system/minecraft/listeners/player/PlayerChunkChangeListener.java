package de.basicbit.system.minecraft.listeners.player;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.events.PlayerChunkChangeEvent;

public class PlayerChunkChangeListener extends Listener {
    
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        World w = p.getWorld();

        Chunk from = w.getChunkAt(e.getFrom());
        Chunk to = w.getChunkAt(e.getTo());

        if (from.getX() != to.getX() || from.getZ() != to.getZ()) {
            PlayerChunkChangeEvent event = new PlayerChunkChangeEvent(p, from, to);
            Bukkit.getPluginManager().callEvent(event);
            e.setCancelled(event.isCancelled());
        }
    }
}