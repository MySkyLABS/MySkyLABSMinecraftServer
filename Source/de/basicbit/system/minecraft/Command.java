package de.basicbit.system.minecraft;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public abstract class Command extends Utils {

	public abstract String getUsage(Player p);
	
	public abstract ArrayList<String> getNames();
	
	public abstract boolean hasPermissions(Player p);
	
	public abstract CommandResult onCommand(Player p, String[] args, String allArgs);

	public abstract String getDescription(Player p);
	
	public boolean isAsync() {
		return false;
	}
}
