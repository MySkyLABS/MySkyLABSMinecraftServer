package de.basicbit.system.minecraft.listeners.player;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.basicbit.system.minecraft.Listener;

public class PlayerJoinLeaveMessage extends Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		e.setJoinMessage(null);

		Player p = e.getPlayer();

		if (!isVanish(p)) {
			sendMessageToAllPlayers(getChatName(p) + "§a ist jetzt online.");
		}
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		e.setQuitMessage(null);

		Player p = e.getPlayer();

		if (!isVanish(p)) {
			sendMessageToAllPlayers(getChatName(p) + "§c ist jetzt offline.");
		}
	}
}
