package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.entity.Player;

import de.basicbit.system.database.UserData;
import de.basicbit.system.database.UserValue;
import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;
import de.basicbit.system.minecraft.Group;

public class Enlisted extends Command {

    @Override
    public String getUsage(Player p) {
        return "<Promo>";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("angeworben");
        names.add("enlisted");
        return names;
    }

    @Override
    public String getDescription(Player p) {
        return "Mit diesem Befehl kannst du zeigen, von welchem Promo du kommst.";
    }

    @Override
    public boolean hasPermissions(Player p) {
        return true;
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 1) {
            UUID id = UserData.getUUIDFromName(args[0]);

            if (id == null) {
                return CommandResult.PlayerNotInDataBase;
            }

            Group group = Group.valueOf(UserData.get(id, UserValue.playerGroup));

            if (getUUIDFromTrimmed(UserData.get(p, UserValue.enlistedBy)) != null) {
                sendMessage(p, "§cDu kannst diesen Command nur einmal eingeben.");
            } else if (group == Group.PROMO || group == Group.PROMO_PLUS || toTrimmed(id).contentEquals("5f05e0a4da08415cb1a6f26cedd52fc4")) {
                UserData.set(p, UserValue.enlistedBy, toTrimmed(id));

                sendMessage(p, "§aAngeworben von: " + getChatName(id));
                sendMessage(p, "§aDu hast §e500 Coins§a erhalten!");
                addCoins(p, 500, " EnlistedCoins");

                Player t = getPlayer(id);

                if (t != null) {
                    sendMessage(t, "§aDu hast " + getChatName(p) + "§a angeworben.");
                    sendMessage(t, "§aDu hast §e500 Coins§a erhalten!");
                    addCoins(t, 500, " EnlistedCoinsPromo");
                } else {
                    UserData.set(id, UserValue.coins, UserData.get(id, UserValue.coins) + 500);
                }
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
