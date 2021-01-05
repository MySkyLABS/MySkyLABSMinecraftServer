package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.basicbit.system.minecraft.cases.CaseSystem;
import de.basicbit.system.minecraft.cases.CaseType;
import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

public class Case extends Command {

    @Override
    public String getUsage(Player p) {
        return "";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("case");
        names.add("cases");
        return names;
    }

    @Override
    public String getDescription(Player p) {
        return "Öffnet ein Inventar mit allen CaseKeys.";
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isAdmin(p);
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 0) {

            Inventory inv = Bukkit.createInventory(null, 27, "§e§lCases");

            for (ItemStack is : CaseSystem.getKeys()) {
                if (!is.equals(CaseType.DEVITEMS.getKey())) {
                    inv.addItem(is);
                }
            }

            p.openInventory(inv);

            return CommandResult.None;
        } else {
            return CommandResult.InvalidUsage;
        }
    }
}
