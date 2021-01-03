package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

public class Vote extends Command {

    @Override
    public String getUsage(Player p) {
        return "";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("vote");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return true;
    }

    @Override
    public String getDescription(Player p) {
        return "Gibt den Vote Link aus";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 0) {
            sendMessage(p, "§cDu kannst derzeit leider noch nicht voten!");
            sendMessage(p, "§cDiese Funktion wird nach der Beta verfügbar sein.");
            return CommandResult.None;
        } else {
            return CommandResult.InvalidUsage;
        }
    }
}