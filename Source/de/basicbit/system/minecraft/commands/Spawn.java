package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

public class Spawn extends Command {

    @Override
    public String getUsage(Player p) {
        return "";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("spawn");
        names.add("hub");
        names.add("l");
        names.add("lobby");
        names.add("leave");
        return names;
    }

    @Override
    public String getDescription(Player p) {
        return "Teleportiert dich zum Spawn.";
    }

    @Override
    public boolean hasPermissions(Player p) {
        return true;
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 0) {
            teleportWithCooldown(p, getSpawn());

            return CommandResult.None;
        } else {
            return CommandResult.InvalidUsage;
        }
    }
}