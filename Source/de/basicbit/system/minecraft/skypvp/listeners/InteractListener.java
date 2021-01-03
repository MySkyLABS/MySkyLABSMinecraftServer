package de.basicbit.system.minecraft.skypvp.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import de.basicbit.system.minecraft.Listener;

public class InteractListener extends Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (isInSkyPvP(p)) {
            if (p.getItemInHand().getType() == Material.LAVA_BUCKET || p.getItemInHand().getType() == Material.WATER_BUCKET 
            || p.getItemInHand().getType() == Material.BUCKET) {
                e.setCancelled(true);
            }
        }
    }
}