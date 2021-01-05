package de.basicbit.system.minecraft.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import de.basicbit.system.database.UserData;
import de.basicbit.system.database.UserValue;
import de.basicbit.system.minecraft.Listener;

public class Maintenance extends Listener {
    
    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();

        if (isMaintenance() && !isPromo(p)) {
        	if (!UserData.getBoolean(p, UserValue.canJoinMaintenance)) {
        		e.disallow(Result.KICK_OTHER, "§cDer Server befindet sich momentan im Wartungsmodus.");

        		sendMessageToAllPlayers(getChatName(p) + "§7 hat versucht zu joinen.");
        	}
        }
    }
}