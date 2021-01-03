package de.basicbit.system.minecraft.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import de.basicbit.system.minecraft.Listener;

public class AntiDefaultWorld extends Listener {
    
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (p.getWorld().getName().contentEquals("world")) {
            p.teleport(getSpawn());
        }
    }
}