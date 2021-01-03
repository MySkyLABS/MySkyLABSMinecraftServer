package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;
import static de.basicbit.system.minecraft.CommandResult.*;

public class Bonus extends Command {

    @Override
    public String getUsage(Player p) {
        return "";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("bonus");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isAdmin(p);
    }

    @Override
    public String getDescription(Player p) {
        return "Simuliert einen Stundenbonus.";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 0) {

            bonusForAll("BonusForAll");

            return None;
        } else {
            return InvalidUsage;
        }
    }
}
