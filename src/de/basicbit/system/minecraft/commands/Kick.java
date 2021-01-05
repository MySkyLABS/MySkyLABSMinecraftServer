package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;
import de.basicbit.system.minecraft.tasks.TaskManager;

public class Kick extends Command {

    @Override
    public String getUsage(Player p) {
        return "<Spieler> ";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("kick");
        names.add("k");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isInTeam(p);
    }

    @Override
    public String getDescription(Player p) {
        return "Ermöglicht das Kicken von Spielern.";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 1) {

            Player t = getPlayer(args[0]);

            if (t == null) {
                return CommandResult.PlayerNotOnline;
            } 

            if (isInTeam(t)) {
                sendMessage(p, "§cDu darfst diesen Spieler nicht kicken.");
                return CommandResult.None;
            }

            TaskManager.runSyncTask("KickCommand", new Runnable() {

                @Override
                public void run() {
                    t.kickPlayer("§cDu wurdest gekickt.");
                }
            });

            for (Player tm : getPlayers()) {
                if (isInTeam(tm)) {
                    sendMessage(tm, getChatName(t) + "§a wurde von §e" + getChatName(p) + "§a gekickt.");
                }
            }

            return CommandResult.None;
        } else if (args.length >= 2) {
            Player t = getPlayer(args[0]);

            if (t == null) {
                return CommandResult.PlayerNotOnline;
            }

            if (isInTeam(t)) {
                sendMessage(p, "§cDu darfst diesen Spieler nicht kicken.");
                return CommandResult.None;
            }

            TaskManager.runSyncTask("KickCommand", new Runnable() {

                @Override
                public void run() {
                    t.kickPlayer("§cDu wurdest gekickt.\n\n§6Grund: §7" + allArgs.substring(1 + args[0].length()));
                }
            });

            for (Player tm : getPlayers()) {
                if (isInTeam(tm)) {
                    sendMessage(tm, getChatName(t) + "§a wurde von §e" + getChatName(p) + "§a gekickt.");
                }
            }
            
            return CommandResult.None;
        } else {
            return CommandResult.InvalidUsage;
        }
    }
}
