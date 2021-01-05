package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

import static de.basicbit.system.minecraft.CommandResult.*;

public class GodMode extends Command {

    @Override
    public String getUsage(Player p) {
        return "[Spieler]";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("god");
        names.add("godmode");
        return names;
    }

    @Override
    public String getDescription(Player p) {
        if (isModerator(p)) {
            return "Macht dich oder andere unsterblich.";
        } else {
            return "Macht dich unsterblich.";

        }
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isSupporter(p);
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 1 && isModerator(p)) {
            Player t = getPlayer(args[0]);

            if (t == null) {
                return PlayerNotOnline;
            }

            setGodMode(t, !isGodMode(t));

            if (isGodMode(t)) {
                sendMessage(p, getChatName(t) + "§a ist nun unsterblich.");
            } else {
                sendMessage(p, getChatName(t) + "§c ist nun verwundbar.");
            }

            return None;
        } else if (args.length == 0) {
            setGodMode(p, !isGodMode(p));

            if (isGodMode(p)) {
                sendMessage(p, "§aDu bist nun unsterblich.");
            } else {
                sendMessage(p, "§cDu bist nun verwundbar.");
            }

            return None;
        }

        return InvalidUsage;
    }
}
