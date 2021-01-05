package de.basicbit.system.minecraft.commands;

import static de.basicbit.system.minecraft.CommandResult.InvalidUsage;
import static de.basicbit.system.minecraft.CommandResult.None;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

public class Farmer extends Command {

    @Override
    public String getUsage(Player p) {
        return "";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("farmer");
        names.add("verkaufen");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isStammi(p);
    }

    @Override
    public String getDescription(Player p) {
        return "Öffnet den Farmer.";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 0) {
            de.basicbit.system.minecraft.listeners.npc.guis.Farmer.openFarmer(p);
            return None;
        } else {
            return InvalidUsage;
        }
    }
}
