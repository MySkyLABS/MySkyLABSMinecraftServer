package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

public class Rename extends Command {

    @Override
    public String getUsage(Player p) {
        return "<Name>";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("rename");
        names.add("umbenennen");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isPromo(p);
    }

    @Override
    public String getDescription(Player p) {
        return "Benennt das Item in deiner Hand um.";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length >= 1) {
            String name = allArgs;
            ItemStack is = p.getItemInHand();

            if (!isDev(p)) {
                name = name.replace("§r", "");
            }

            if (is == null || is.getType() == Material.AIR) {
                sendMessage(p, "§cBitte nehme ein Item in die Hand.");
            } else {
                ItemMeta im = is.getItemMeta();
                im.setDisplayName(name);
                is.setItemMeta(im);
                p.setItemInHand(is);
                sendMessage(p, "§aDein Item wurde erfolgreich umbenannt.");
            }

            return CommandResult.None;
        } else {
            return CommandResult.InvalidUsage;
        }
    }
}
