package de.basicbit.system.minecraft;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;

public class WorldMobCleaner {
    
    public static void init() {
        for (World world : Bukkit.getWorlds()) {
            if (Utils.isKnockItWorld(world) || Utils.isSpawnWorld(world)) {
                Utils.log("Cleaning world: " + world.getName());
                for (LivingEntity livingEntity : world.getLivingEntities()) {
                    livingEntity.remove();
                }
            }
        }
    }
}