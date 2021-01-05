package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

public class Seat extends Command {

    @Override
    public String getUsage(Player p) {
        return "<Spieler> [Spieler]";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("seat");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isAdmin(p);
    }

    @Override
    public String getDescription(Player p) {
        return "Ermöglicht das Sitzen auf anderen Spielern.";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 1) {
            
            Player t = getPlayer(args[0]);

            if (t == null) {
                return CommandResult.PlayerNotOnline;
            }

            if (!t.getUniqueId().equals(p.getUniqueId())) {
                t.setPassenger(p);
                sendMessage(p, "§aDu reitest den Hengst!");
            } else {
                sendMessage(p, "§cHör auf Quatsch zu machen.");
            }
            

            return CommandResult.None;
        } else if (args.length == 2) {

            Player t = getPlayer(args[0]);
            Player t2 = getPlayer(args[1]);

            if (t == null || t2 == null) {
                return CommandResult.PlayerNotOnline;
            }

            if (!t2.getUniqueId().equals(t.getUniqueId())) {
                t2.setPassenger(t);
                sendMessage(p, "§aDas ist sehr merkwürdig... °-°");
            } else {
                sendMessage(p, "§cHör auf Quatsch zu machen.");
            }


            return CommandResult.None;
        }

        return CommandResult.InvalidUsage;
    }
}