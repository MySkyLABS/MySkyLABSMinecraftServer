package de.basicbit.system.minecraft.skyblock.listeners;

import java.util.UUID;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import de.basicbit.system.database.UserData;
import de.basicbit.system.database.UserValue;
import de.basicbit.system.minecraft.Listener;

public class PvPListener extends Listener {
    
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        Entity en = e.getEntity();
        Entity en2 = e.getDamager();

        if (en instanceof Player) {
            World w = en.getWorld();

            if (isSkyblockWorld(w)) {
                if (en2 instanceof Player || (en2 instanceof Projectile && ((Projectile)en2).getShooter() instanceof Player)) {
                    UUID id = getUUIDFromSkyBlockWorld(w);
                    int number = getNumberFromSkyBlockWorld(w);
                    
                    if (!UserData.getBoolean(id, UserValue.valueOf("island" + number + "UsersPvp"))) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}