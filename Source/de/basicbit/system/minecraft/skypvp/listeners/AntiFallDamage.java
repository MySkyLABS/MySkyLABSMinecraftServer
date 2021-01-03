package de.basicbit.system.minecraft.skypvp.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import de.basicbit.system.minecraft.Listener;

public class AntiFallDamage extends Listener {
    
    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        Entity en = e.getEntity();
        if (en instanceof Player) {
            Player p = (Player)en;

            if (isInSkyPvP(p) && e.getCause() == DamageCause.FALL) {
                e.setCancelled(true);
            }
        }
    }
}