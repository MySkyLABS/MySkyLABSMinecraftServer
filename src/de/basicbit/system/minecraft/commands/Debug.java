package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;
import de.basicbit.system.minecraft.MySkyLABS;

public class Debug extends Command {

    @Override
    public String getUsage(Player p) {
        return "";
    }

    @Override
    public ArrayList<String> getNames() {
		ArrayList<String> names = new ArrayList<String>();
        names.add("debug");
        names.add("debugmode");
        names.add("debugmodus");
		return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isDev(p);
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length != 0) {
            return CommandResult.InvalidUsage;
        }

        MySkyLABS.debugMode = !MySkyLABS.debugMode;

        if (MySkyLABS.debugMode) {
            sendMessage(p, "§cDer Debugmodus ist jetzt an.");
        } else {
            sendMessage(p, "§aDer Debugmodus ist jetzt aus.");
        }

        return CommandResult.None;
    }

    @Override
    public String getDescription(Player p) {
        return "Schaltete den Debugmodus um.";
    }
}