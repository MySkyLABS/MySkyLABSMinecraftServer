package de.basicbit.system.minecraft.skypvp.listeners;

import java.util.ArrayList;
import java.util.UUID;
import java.util.Map.Entry;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import de.basicbit.system.GlobalValues;
import de.basicbit.system.minecraft.Listener;

public class LastHitListener extends Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onDamage(final EntityDamageByEntityEvent e) {
        final Entity en = e.getEntity();
        final Entity ed = e.getDamager();
        if (en instanceof Player) {
            if (ed instanceof Player) {
                final Player p = (Player) en;
                final Player d = (Player) ed;
                final UUID pid = p.getUniqueId();
                final UUID did = d.getUniqueId();

                if (isInSkyPvP(p)) {
                    GlobalValues.lastHits.put(pid, did);
                }
            } else if (ed instanceof Projectile) {
                final Player p = (Player) en;
                final Projectile dp = (Projectile) ed;
                if (dp.getShooter() instanceof Player) {
                    final Player d = (Player) dp.getShooter();
                    final UUID pid = p.getUniqueId();
                    final UUID did = d.getUniqueId();

                    if (isInSkyPvP(p)) {
                        GlobalValues.lastHits.put(pid, did);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent e) {
        final Player p = e.getPlayer();
        final UUID id = p.getUniqueId();
        
        ArrayList<Entry<UUID, UUID>> copy = new ArrayList<>(GlobalValues.lastHits.entrySet());
        for (final Entry<UUID, UUID> entry : copy) {
            if (entry.getValue().equals(id) || entry.getKey().equals(id)) {
                GlobalValues.lastHits.remove(entry.getKey());
            }
        }
    }

    @EventHandler
    public void onTeleport(final PlayerTeleportEvent e) {
        final Player p = e.getPlayer();
        final UUID id = p.getUniqueId();

        ArrayList<Entry<UUID, UUID>> copy = new ArrayList<>(GlobalValues.lastHits.entrySet());
        for (final Entry<UUID, UUID> entry : copy) {
            if (entry.getValue().equals(id) || entry.getKey().equals(id)) {
                GlobalValues.lastHits.remove(entry.getKey());
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onDeath(final PlayerDeathEvent e) {
        final Player p = e.getEntity();
        final UUID id = p.getUniqueId();

        if (GlobalValues.lastHits.containsKey(id)) {
            GlobalValues.lastHits.remove(id);
        }
    }
}