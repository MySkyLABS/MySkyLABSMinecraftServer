package de.basicbit.system.minecraft.listeners.player;
/*
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.basicbit.system.GlobalValues;
import de.basicbit.system.minecraft.MySkyLABS;*/
import de.basicbit.system.minecraft.Listener;

public class Dog extends Listener {
    /*
    @EventHandler
    public static void onDogAngry(EntityDamageByEntityEvent e) {
        Entity et = e.getEntity();
        Entity ed = e.getDamager();

        if (et instanceof Player) {
            makeDogPeaceful((Player) et);
        }

        if (ed instanceof Player) {
            makeDogPeaceful((Player) ed);
        }
    }

    @EventHandler
    public static void onDogDamage(EntityDamageEvent e) {
        Entity en = e.getEntity();
        if (en instanceof Wolf && GlobalValues.dogPets.containsValue(en)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public static void dogRemoveOnQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        Wolf wolf = GlobalValues.dogPets.get(p.getUniqueId());
        if (wolf != null && !wolf.isDead()) {
            wolf.remove();
            GlobalValues.dogPets.remove(p.getUniqueId(), wolf);
        }
    }

    public static void makeDogPeaceful(Player p) {
        UUID id = p.getUniqueId();

        if (GlobalValues.dogPets.containsKey(id)) {
            Wolf wolf = GlobalValues.dogPets.get(id);
            wolf.setTarget(null);
            Bukkit.getScheduler().runTaskLater(MySkyLABS.getSystemPlugin(), new Runnable() {

                @Override
                public void run() {
                    wolf.setTarget(null);
                }

            }, 10);
        }
    }*/
}