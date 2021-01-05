package de.basicbit.system.minecraft.skypvp.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.events.PlayerSkyPvPDeathEvent;

public class KillCoins extends Listener {
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onDeath(PlayerSkyPvPDeathEvent e) {
        Player p = e.getPlayer();
        Player k = e.getKiller();

        if (k != p) {
            removeCoins(p, 4, " SkyPvpRemoveCoins");
            sendMessage(p, "§cDu hast §e4 Coins§c durch deinen Tod verloren.");
        }

        if (k != p) {
            addCoins(k, 8, " SkyPvpRemoveCoins");
            sendMessage(k, "§aDu hast §e8 Coins§a durch deinen Sieg erhalten.");
        }
    }
}