package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.entity.Player;

import de.basicbit.system.database.UserData;
import de.basicbit.system.database.UserValue;
import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

public class Maintenance extends Command {

    @Override
    public String getUsage(Player p) {
        return "[<Add/Remove> <Spieler>]";
        //[Add <Spieler>|Remove <Spieler>]
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("wartung");
        names.add("maintenance");
        return names;
    }

    @Override
    public String getDescription(Player p) {
        return "Schaltet den Wartungsmodus um.";
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isPromo(p);
    }
    
    @Override
    public boolean isAsync() {
    	return true;
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 0) {
            if (!isAdmin(p)) {
                sendMessage(p, "§cDu hast keine Rechte, um den Wartungsmodus umzuschalten.");
                sendMessage(p, "§cMöchtest du stattdessen jemanden whitelisten?");
                sendMessage(p, "§cDann benutze: §e/Wartung Add <Spieler>");
                return CommandResult.None;
            }

            boolean maintenance = !isMaintenance();
            setMaintenance(maintenance);

            if (maintenance) {
                sendMessage(p, "§cDer Server ist nun im Wartungsmodus.");
            } else {
                sendMessage(p, "§aDer Server ist nun wieder offen.");
            }

            return CommandResult.None;
        } else if (args.length == 2) {
            UUID id = UserData.getUUIDFromName(args[1]);

            if (id == null) {
                return CommandResult.PlayerNotInDataBase;
            }
            
            boolean canJoin = UserData.getBoolean(id, UserValue.canJoinMaintenance);

            if (args[0].equalsIgnoreCase("add")) {
            	if (canJoin) {
            		sendMessage(p, "§c" + args[1] + " ist schon in der Whitelist");
            	} else {
            		UserData.set(id, UserValue.canJoinMaintenance, true);
            		sendMessage(p, "§a" + args[1] + " ist nun in der Whitelist");
            	}
            } else if (args[0].equalsIgnoreCase("remove")) {
            	if (!canJoin) {
            		sendMessage(p, "§c" + args[1] + " war nicht in der Whitelist");
            	} else {
            		UserData.set(id, UserValue.canJoinMaintenance, false);
            		sendMessage(p, "§c" + args[1] + " ist nun nicht mehr in der Whitelist");
            	}
            } else {
                return CommandResult.InvalidUsage;
            }
            
            return CommandResult.None;
        } else {
            return CommandResult.InvalidUsage;
        }
    }
}