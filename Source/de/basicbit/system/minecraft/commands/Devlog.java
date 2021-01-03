package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.entity.Player;

import de.basicbit.system.GlobalValues;
import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

public class Devlog extends Command {
    
    @Override
    public String getUsage(Player p) {
        return "";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("devlog");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isAdmin(p);
    }

    @Override
    public String getDescription(Player p) {
        return "Zeigt einen Log für Developer an.";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 0) {

            UUID id = p.getUniqueId();
            if (GlobalValues.devLogPlayers.contains(id)) {
                GlobalValues.devLogPlayers.remove(id);
                sendMessage(p, "§cDu kannst die DevLogs jetzt nicht mehr sehen.");
            } else {
                GlobalValues.devLogPlayers.add(id);
                sendMessage(p, "§aDu kannst die DevLogs jetzt sehen.");
            }
            
            return CommandResult.None;
        } else {
            return CommandResult.InvalidUsage;
        }
    }
}