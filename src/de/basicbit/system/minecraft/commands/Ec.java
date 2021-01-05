package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

public class Ec extends Command {

    @Override
    public String getUsage(Player p) {
        return "[Spieler]";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("ec");
        names.add("enderchest");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return true;
    }

    @Override
    public String getDescription(Player p) {
        return "Öffnet deine oder die Enderchest eines anderen.";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 0) {

            p.openInventory(p.getEnderChest());

            return CommandResult.None;
        } else if (args.length == 1) {
            Player t = getPlayer(args[0]);

            if (t == null || isVanish(t)) {
                return CommandResult.PlayerNotOnline;
            }

            p.openInventory(t.getEnderChest());

            return CommandResult.None;
        }
        else {
            return CommandResult.InvalidUsage;
        }
    }
}
