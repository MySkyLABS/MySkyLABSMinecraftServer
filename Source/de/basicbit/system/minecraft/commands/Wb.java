package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

public class Wb extends Command {

    @Override
    public String getUsage(Player p) {
        return "";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("craft");
        names.add("wb");
        names.add("ct");
        names.add("workbench");
        names.add("werkbank");
        names.add("craftingtable");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isStammi(p);
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 0) {

            p.openWorkbench(null, true);

            return CommandResult.None;
        } else {
            return CommandResult.InvalidUsage;
        }
    }

    @Override
    public String getDescription(Player p) {
        return "Öffnet eine Werkbank";
    }
}
