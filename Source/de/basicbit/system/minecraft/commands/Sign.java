package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

@SuppressWarnings("deprecation")
public class Sign extends Command {

    @Override
    public String getUsage(Player p) {
        return "[Nachricht]";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("sign");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isHero(p);
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 0) {
            ItemStack is = p.getItemInHand();

            if (is == null || is.getTypeId() == 0) {
                sendMessage(p, "§cBitte nehme ein Item in die Hand.");
                return CommandResult.None;
            }

            ItemMeta im = is.getItemMeta();
            List<String> lore = im.hasLore() ? im.getLore() : new ArrayList<String>();
            Boolean signed = false;

            for (int i = 0; i < lore.size(); i++) {
                if (lore.get(i).startsWith("§eSigniert von ")) {
                    signed = true;
                }
            }

            if (signed) {
                sendMessage(p, "§cDieses Item wurde bereits signiert.");
                return CommandResult.None;
            } else {
                lore.add("§r");
                lore.add("§eSigniert von " + getChatName(p));
                lore.add("§r");
                signed = true;
            }

            im.setLore(lore);
            is.setItemMeta(im);

            p.setItemInHand(is);

            sendMessage(p, "§aDu hast das Item erfolgreich signiert.");

            return CommandResult.None;
        } else {
            ItemStack is = p.getItemInHand();

            if (is == null || is.getTypeId() == 0) {
                sendMessage(p, "§cBitte nehme ein Item in die Hand.");
                return CommandResult.None;
            }

            ItemMeta im = is.getItemMeta();
            List<String> lore = im.hasLore() ? im.getLore() : new ArrayList<String>();
            Boolean signed = false;

            for (int i = 0; i < lore.size(); i++) {
                if (lore.get(i).startsWith("§eSigniert von ")) {
                    signed = true;
                }
            }

            if (signed) {
                sendMessage(p, "§cDieses Item wurde bereits signiert.");
                return CommandResult.None;
            } else {
                lore.add("§r");
                lore.add("§eSigniert von " + getChatName(p));
                lore.add("§7» " + allArgs);
                lore.add("§r");
                signed = true;
            }

            im.setLore(lore);
            is.setItemMeta(im);

            p.setItemInHand(is);

            sendMessage(p, "§aDu hast das Item erfolgreich signiert.");

            return CommandResult.None;
        }
    }

    @Override
    public String getDescription(Player p) {
        return "Signiert das Item in deiner Hand.";
    }
}