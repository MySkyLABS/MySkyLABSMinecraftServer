package de.basicbit.system.minecraft.commands;

import static de.basicbit.system.minecraft.CommandResult.InvalidUsage;
import static de.basicbit.system.minecraft.CommandResult.None;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.database.UserData;
import de.basicbit.system.database.UserValue;
import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

public class Shop extends Command {

    @Override
    public String getUsage(Player p) {
        return "";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("shop");
        names.add("usershop");
        names.add("markt");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isHero(p);
    }

    @Override
    public String getDescription(Player p) {
        return "Öffnet den Markt.";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length ==0) {
            if (isInKnockIt(p)) {
                sendMessage(p, "§cDu kannst diesen Befehl hier nicht nutzen.");
            } else {
                int lastUsed = UserData.getInt(p, UserValue.shopUsed);
                int unixTime = (int) (System.currentTimeMillis() / 1000L);

                if ((unixTime - lastUsed) > 15 * 60 || isAdmin(p)) {
                    de.basicbit.system.minecraft.usershop.UserShop.openUserShop(p);
                    UserData.set(p, UserValue.shopUsed, unixTime);
                } else {
                    int nextUse = (15 * 60) - (unixTime - lastUsed); // time left in seconds
                    sendMessage(p, "§cDu kannst diesen Befehl erst in " + (nextUse / 60) + "min" + " wieder benutzen.");
                }
            }
            return None;
        } else {
            return InvalidUsage;
        }
    }
}
