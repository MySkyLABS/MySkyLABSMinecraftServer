package de.basicbit.system.minecraft.listeners.world;

import org.bukkit.entity.EnderCrystal;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

import de.basicbit.system.minecraft.Listener;

public class AntiEnderCrystalExplode extends Listener {
    
    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof EnderCrystal) {
            e.setCancelled(true);
        }
    }
}