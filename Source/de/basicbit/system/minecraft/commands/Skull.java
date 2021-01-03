package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import de.basicbit.system.database.UserData;
import de.basicbit.system.database.UserValue;
import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

import static de.basicbit.system.minecraft.CommandResult.*;

public class Skull extends Command {

    @Override
    public String getUsage(Player p) {
        return "<Spieler>";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("kopf");
        names.add("head");
        names.add("skull");
        return names;
    }

    @Override
    public String getDescription(Player p) {
        return "Gibt dir den Kopf eines Spielers";
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isSenpai(p);
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 1) {
            if (isInKnockIt(p)) {
                sendMessage(p, "§cDu kannst diesen Befehl hier nicht nutzen.");
            } else {
                int lastUsed = UserData.getInt(p, UserValue.skullUsed);
                int unixTime = (int) (System.currentTimeMillis() / 1000L);

                if ((unixTime - lastUsed) > 3 * 60 * 60 * 24 || isAdmin(p)) {
                    ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                    SkullMeta sm = (SkullMeta) is.getItemMeta();
                    sm.setDisplayName("§eKopf von " + args[0]);
                    sm.setOwner(args[0]);
                    is.setItemMeta(sm);

                    sendMessage(p, "§aDu hast den Kopf von §e" + args[0] + "§a erhalten.");
                    sendSuccessSound(p);
                    giveItemToPlayer(p, is);
                    UserData.set(p, UserValue.skullUsed, unixTime);
                } else {
                    int nextUse = (60 * 60 * 24 * 3) - (unixTime - lastUsed); // time left in seconds

                    sendMessage(p, "§cDu kannst diesen Befehl erst in " + (nextUse / 3600) + "h " + ((nextUse / 60) - ((nextUse / 3600) * 60)) + "min" + " wieder benutzen.");
                }
            }

            return None;
        } else {
            return InvalidUsage;
        }
    }
}
