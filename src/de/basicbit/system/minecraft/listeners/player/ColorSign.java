package de.basicbit.system.minecraft.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.SignChangeEvent;

import de.basicbit.system.minecraft.Listener;

public class ColorSign extends Listener {

	@EventHandler
	public void onSignCreate(SignChangeEvent e) {
		Player p = e.getPlayer();

		if (isInTeam(p)) {
			e.setLine(0, e.getLine(0).replace("&", "§"));
			e.setLine(1, e.getLine(1).replace("&", "§"));
			e.setLine(2, e.getLine(2).replace("&", "§"));
			e.setLine(3, e.getLine(3).replace("&", "§"));
		}
	}
}