package de.basicbit.system.minecraft.listeners.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import de.basicbit.system.minecraft.Listener;

public class RemoveDeathMessage extends Listener {
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		e.setDeathMessage(null);
	}
	
}
