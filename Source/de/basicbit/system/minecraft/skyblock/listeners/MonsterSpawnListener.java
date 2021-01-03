package de.basicbit.system.minecraft.skyblock.listeners;

import java.util.UUID;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntitySpawnEvent;

import de.basicbit.system.database.UserData;
import de.basicbit.system.database.UserValue;
import de.basicbit.system.minecraft.Listener;

public class MonsterSpawnListener extends Listener {
    
    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {
        Entity en = e.getEntity();
        if (en instanceof Monster) {
            Monster m = (Monster) en;
            World w = m.getWorld();

            if (isSkyblockWorld(w)) {
                UUID id = getUUIDFromSkyBlockWorld(w);
                int number = getNumberFromSkyBlockWorld(w);
                
                if (!UserData.getBoolean(id, UserValue.valueOf("island" + number + "Monsters"))) {
                    e.setCancelled(true);
                }
            }
        }
        if (en instanceof Slime) {
            Entity m = en;
            World w = m.getWorld();

            if (isSkyblockWorld(w)) {
                UUID id = getUUIDFromSkyBlockWorld(w);
                int number = getNumberFromSkyBlockWorld(w);
                
                if (!UserData.getBoolean(id, UserValue.valueOf("island" + number + "Monsters"))) {
                    e.setCancelled(true);
                }
            } 
        }
    }
}