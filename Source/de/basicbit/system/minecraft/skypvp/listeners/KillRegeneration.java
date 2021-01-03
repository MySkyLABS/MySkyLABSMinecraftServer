package de.basicbit.system.minecraft.skypvp.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.events.PlayerSkyPvPDeathEvent;

public class KillRegeneration extends Listener {
    
    @EventHandler
    public void onDeath(PlayerSkyPvPDeathEvent e) {
        Player k = e.getKiller();

        k.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 5), true);
    }
}