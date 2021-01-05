package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

public class Food extends Command {

    @Override
    public String getUsage(Player p) {
        return "[Spieler]";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("food");
        names.add("feed");
        names.add("essen");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isInTeam(p);
    }

    @Override
    public String getDescription(Player p) {
        return "Füttert dich oder einen anderen Spieler.";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 0) {
            p.setFoodLevel(40);

            sendMessage(p, "§aDu wurdest gefüttert.");
            return CommandResult.None;
        } else if (args.length == 1) {

            Player t = getPlayer(args[0]);

            if (t == null) {
                return CommandResult.PlayerNotOnline;
            }

            t.setFoodLevel(40);

            sendMessage(p, getChatName(t) + "§a wurde gefüttert.");

            return CommandResult.None;
        } else {
            return CommandResult.InvalidUsage;
        }
    }
}
