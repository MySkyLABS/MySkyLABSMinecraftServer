package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

public class Launch extends Command {

    @Override
    public String getUsage(Player p) {
        return "<Spieler>";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("launch");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isAdmin(p);
    }

    @Override
    public String getDescription(Player p) {
        return "Feuert einen Spieler in die Luft.";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 1) {
            Player t = getPlayer(args[0]);

            if (t == null) {
                return CommandResult.PlayerNotOnline;
            }

            sendMessage(p, getChatName(t) + "§a lernt jetzt fliegen!");
            t.setVelocity(p.getLocation().getDirection().multiply(10));

            return CommandResult.None;
        } else {
            return CommandResult.InvalidUsage;
        }
    }
}