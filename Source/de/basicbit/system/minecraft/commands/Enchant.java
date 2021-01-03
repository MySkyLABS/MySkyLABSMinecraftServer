package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

import static de.basicbit.system.minecraft.CommandResult.*;

@SuppressWarnings("deprecation")
public class Enchant extends Command {

    @Override
    public String getUsage(Player p) {
        return "<Enchantment-ID> <Level>";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("enchant");
        names.add("ench");
        names.add("verzaubern");
        return names;
    }

    @Override
    public String getDescription(Player p) {
        return "Verzaubert ein das Item in deiner Hand.";
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isAdmin(p);
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 2) {
            if (p.getItemInHand() != null && p.getItemInHand().getType() != Material.AIR) {
                try {
                    int id = Integer.parseInt(args[0]);
                    int level = Integer.parseInt(args[1]);

                    if (level < 0) {
                        sendMessage(p, "§cDie angegebenen Werte sind ungültig.");
                        return None;
                    }

                    ItemStack is = p.getItemInHand();

                    if (level == 0) {
                        is.removeEnchantment(Enchantment.getById(id));
                    } else {
                        is.addUnsafeEnchantment(Enchantment.getById(id), level);
                    }

                    p.setItemInHand(is);
                } catch (Exception e) {
                    sendMessage(p, "§cDie angegebenen Werte sind ungültig.");
                    return None;
                }
            } else {
                sendMessage(p, "§cBitte nehme ein Item in die Hand.");
            }
            return None;
        } else {
            return InvalidUsage;
        }
    }
}
