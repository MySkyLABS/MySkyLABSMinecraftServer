package de.basicbit.system.minecraft.listeners.player;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

import de.basicbit.system.minecraft.Listener;

public class DisableDamageSpawn extends Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        Entity en = e.getEntity();
        if (en instanceof Player) {
            Player p = (Player) en;

            if (isInWorld(p, getSpawnWorld())) {
                if (!isInSkyPvP(p)) {
                    e.setCancelled(true);
                }
            }
        }
    }
}