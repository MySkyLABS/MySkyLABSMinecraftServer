package de.basicbit.system.minecraft.listeners.player.join;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.MySkyLABS;

public class PlayerJoinAtSpawn extends Listener {

	@EventHandler
	public static void onJoin(PlayerJoinEvent e) {
		if (!MySkyLABS.debugMode) {
			e.getPlayer().teleport(getSpawn());
		}
	}
}