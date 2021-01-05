package de.basicbit.system.minecraft.skypvp.listeners;

import java.util.ArrayList;
import java.util.UUID;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import de.basicbit.system.GlobalValues;
import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.events.PlayerSkyPvPDeathEvent;

public class DeathListener extends Listener {
    
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        UUID id = p.getUniqueId();

        if (GlobalValues.lastHits.containsKey(id)) {
            UUID otherId = GlobalValues.lastHits.get(id);
            Player t = getPlayer(otherId);

            if (t != null && t.getUniqueId() != p.getUniqueId() && !isVanish(p) && !isVanish(t)) {
                sendMessageInWorld(t.getWorld(), getChatName(t) + "§7 hat " + getChatName(p) + "§7 getötet.");
                PlayerSkyPvPDeathEvent deathEvent = new PlayerSkyPvPDeathEvent(p, t, e);
                callEvent(deathEvent);
                GlobalValues.lastHits.remove(id);
            }
            
            ArrayList<Entry<UUID, UUID>> copy = new ArrayList<>(GlobalValues.lastHits.entrySet());
            for (final Entry<UUID, UUID> entry : copy) {
                if (entry.getValue().equals(id)) {
                    GlobalValues.lastHits.remove(entry.getKey());
                }
            }
            return;
        }

        if (isInSkyPvP(p)) {
            e.setKeepInventory(true);
        }
    }
}