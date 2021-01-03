package de.basicbit.system.minecraft.listeners.player.join;

import java.time.Instant;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import de.basicbit.system.database.UserData;
import de.basicbit.system.database.UserValue;
import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.commands.Ban;

public class PlayerJoinBanListener extends Listener {
    
    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        long time = UserData.getLong(p, UserValue.bannedUntil);
        String[] reasons = UserData.get(p, UserValue.banReasons).split(";");

        if (time > Instant.now().getEpochSecond() && !isInTeam(p)) {
            e.disallow(Result.KICK_OTHER, Ban.getBanMessage(reasons[reasons.length - 1], time));
        }
        /*
        for (Player player : getPlayers()) {
            if (UserData.get(p, UserValue.ip).equals(UserData.get(player, UserValue.ip)) && !isInTeam(player)) {
                UserData.set(player, UserValue.banReasons, reasons + ";" + UserData.get(player, UserValue.banReasons));
                UserData.set(player, UserValue.bannedUntil, time);
                e.disallow(Result.KICK_OTHER, Ban.getBanMessage(reasons[reasons.length - 1], time));
            }
        }*/
    }
}