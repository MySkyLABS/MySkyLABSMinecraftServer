package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import de.basicbit.system.database.Database;
import de.basicbit.system.database.UserData;
import de.basicbit.system.database.UserValue;
import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;
import de.basicbit.system.minecraft.Group;

public class EnlistedInfo extends Command {

    @Override
    public String getUsage(Player p) {
        return "<Promo>";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("angeworbeninfo");
        names.add("enlistedinfo");
        names.add("ai");
        return names;
    }

    @Override
    public String getDescription(Player p) {
        return "Mit diesem Befehl kannst du zeigen, welcher Promo wie viele Spieler angeworben hat.";
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isPromo(p);
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 1) {
            UUID id = UserData.getUUIDFromName(args[0]);

            if (id == null) {
                return CommandResult.PlayerNotInDataBase;
            }

            Group group = getGroup(id);
            ArrayList<HashMap<String, String>> rows = Database.fetchAll("SELECT " +
                    UserValue.minecraftId + " FROM users WHERE " +
                    UserValue.enlistedBy + " = '" + toTrimmed(id) + "';");

            ArrayList<UUID> uuids = new ArrayList<UUID>();
            for (HashMap<String, String> row : rows) {
                String uuidAsString = row.get(UserValue.minecraftId.toString());
                UUID uuid = getUUIDFromTrimmed(uuidAsString);

                if (uuid != null) {
                    uuids.add(uuid);
                }
            }

            if (uuids.size() != 0 && group == Group.PROMO || group == Group.PROMO_PLUS
                    || toTrimmed(id).contentEquals("5f05e0a4da08415cb1a6f26cedd52fc4")) {
                sendMessage(p, "§eAngeworbene Spieler von " + getChatName(id) + "§e:");
                for (UUID t : uuids) {
                    sendMessage(p, "§e- " + getChatName(t));
                }
            } else if (uuids.size() == 0) {
                sendMessage(p, "§cDieser Spieler hat niemanden angeworben.");
            } else {
                sendMessage(p, "§cDieser Spieler ist kein Promo.");
            }
            
            return CommandResult.None;
        } else {
            return CommandResult.InvalidUsage;
        }
    }

    @Override
    public boolean isAsync() {
        return true;
    }
}
