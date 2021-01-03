package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

public class Error extends Command {
    
    @Override
    public String getUsage(Player p) {
        return "";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("error");
        return names;
    }

    @Override
    public String getDescription(Player p) {
        return "Dieser Befehl erzeugt eine Exception.";
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isDev(p);
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        ((String)null).length();
        return null;
    }
}
