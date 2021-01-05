package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

public class DisableRanks extends Command {

    public static boolean disabled = false;

    @Override
    public String getUsage(Player p) {
        return "";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("disableranks");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isDev(p);
    }

    @Override
    public String getDescription(Player p) {
        return "Macht alle Ränge auf dem Server unbrauchbar.";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 0) {
            disabled = true;

            for (Player t : getPlayers()) {
                simulateRejoin(t);
            }

            sendMessage(p, "§aAlle Ränge wurden deaktiviert.");

            return CommandResult.None;
        } else {
            return CommandResult.InvalidUsage;
        }
    }
}