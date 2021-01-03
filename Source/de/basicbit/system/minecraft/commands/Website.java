package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

public class Website extends Command {

    @Override
    public String getUsage(Player p) {
        return "";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("website");
        names.add("www");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return true;
    }

    @Override
    public String getDescription(Player p) {
        return "Gibt den Website Link aus";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 0) {
            sendMessage(p, "§cUnsere Website ist derzeit noch in Entwicklung.");
            // sendMessage(p, "§a§aUnsere Website: §e§nmyskylabs.de");

            return CommandResult.None;
        } else {
            return CommandResult.InvalidUsage;
        }
    }
}