package de.basicbit.system.minecraft.skypvp.listeners;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.events.PlayerSkyPvPDeathEvent;
import de.basicbit.system.minecraft.skypvp.SkyPvP;

public class KillStreak extends Listener {
    
    @EventHandler
    public void onDeath(PlayerSkyPvPDeathEvent e) {
        Player p = e.getPlayer();
        Player k = e.getKiller();
        World w = p.getWorld();

        SkyPvP.addKillToStreak(k);

        int killStreak = SkyPvP.getKillStreak(k);
        if (killStreak % 5 == 0 && killStreak != 0 && killStreak <= 30) {
            if (!isVanish(k)) {
                sendMessageInWorld(w, getChatName(k) + "§7 ist auf einer §e" + killStreak + "er-Killstreak§7.");
            }

            int coins = killStreak * 50 / 4;
            addCoins(k, coins, " SkyPvPAddCoins");
            sendMessage(k, "§aDu hast §e" + coins + "§a Coins für deine Killstreak erhalten.");
        }

        killStreak = SkyPvP.getKillStreak(p);
        if (killStreak / 5 > 0) {
            int coins = killStreak * 50 / 2;
            addCoins(k, coins, " SkyPvpAddCoins");
            sendMessage(k, getChatName(p) + "§7 hatte eine §e" + killStreak + "er-Killstreak§7.");
            sendMessage(k, "§aDu hast §e" + coins + "§a Coins erhalten.");
        }
        
        SkyPvP.resetKillstreak(p);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        SkyPvP.resetKillstreak(p);
    }
}