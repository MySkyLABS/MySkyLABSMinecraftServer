package de.basicbit.system.minecraft.listeners.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.tasks.TaskManager;

public class AutoRespawn extends Listener {

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		TaskManager.runSyncTaskLater("AutoRespawn", new Runnable() {
			
			@Override
			public void run() {
				respawnPlayer(e.getEntity());
			}
		}, 20);
	}
}
