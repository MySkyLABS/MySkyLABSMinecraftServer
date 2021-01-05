package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.database.UserData;
import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;
import de.basicbit.system.minecraft.Utils;

public class Transfer extends Command {

    @Override
    public String getUsage(Player p) {
        return "[Spieler] <Server>";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("server");
        names.add("transfer");
        names.add("move");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isInTeam(p);
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 1) {
            Utils.transfer(p, args[0]);

            return CommandResult.None;
        } else if (args.length == 2) {
            if (args[0].length() != 0) {
                Utils.transfer(UserData.getUUIDFromName(args[0]), args[1]);
            }
            
            return CommandResult.None;
        }
        return CommandResult.InvalidUsage;
    }

    @Override
    public String getDescription(Player p) {
        return "Ermöglicht das Wechseln des Servers.";
    }
}