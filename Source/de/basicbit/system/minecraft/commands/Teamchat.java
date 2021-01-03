package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

public class Teamchat extends Command {

    @Override
    public String getUsage(Player p) {
        return "<Nachricht>";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("teamchat");
        names.add("tc");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isInTeam(p);
    }

    @Override
    public String getDescription(Player p) {
        return "Ermöglicht das teaminterne Chatten.";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length > 0) {

			for (final Player t : getPlayers()) {
				if (isInTeam(t)) {
					t.sendMessage("§6[§3Team§6] " + getChatName(p) + "§8 » §7" + allArgs);
				}
            }
            
            return CommandResult.None;
        } else {
            return CommandResult.InvalidUsage;
        }
    }
}
