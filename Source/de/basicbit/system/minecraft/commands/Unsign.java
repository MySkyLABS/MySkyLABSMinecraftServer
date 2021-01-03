package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

public class Unsign extends Command {

    @Override
    public String getUsage(Player p) {
        return "";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("unsign");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return true;
    }

    @Override
    public String getDescription(Player p) {
        return "Entfernt die Signierung eines Items.";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {

        // stack = Bukkit.getUnsafe().modifyItemStack(stack, Joiner.on(' ').join(Arrays.asList(args).subList(4, args.length)));

        if (args.length == 0) {
            ItemStack is = p.getItemInHand();
            ItemMeta im = is.getItemMeta();
            List<String> lore = im.hasLore() ? im.getLore() : new ArrayList<String>();
            int size = lore.size();

            for (int i = 0; i < size; i++) {
                try {
                    String line0 = lore.get(i);
                    String line1 = lore.get(i + 1);
                    String line2 = lore.get(i + 2);

                    if (line0.contentEquals("§r") && line1.startsWith("§eSigniert von ") && line2.contentEquals("§r")) {
                        lore.remove(i + 2);
                        lore.remove(i + 1);
                        lore.remove(i);

                        im.setLore(lore);
                        is.setItemMeta(im);
                        p.setItemInHand(is);

                        sendMessage(p, "§aDieses Item ist jetzt nicht mehr signiert.");
                            
                        return CommandResult.None;
                    } else {
                        String line3 = lore.get(i + 3);
                        
                        if (line0.contentEquals("§r") && line1.startsWith("§eSigniert von ") && line2.startsWith("§7» ") && line3.contentEquals("§r")) {
                            lore.remove(i + 3);
                            lore.remove(i + 2);
                            lore.remove(i + 1);
                            lore.remove(i);

                            im.setLore(lore);
                            is.setItemMeta(im);
                            p.setItemInHand(is);

                            sendMessage(p, "§aDieses Item ist jetzt nicht mehr signiert.");

                            return CommandResult.None;
                        }
                    }
                } catch (Throwable throwable) {
                    break;
                }
            }

            sendMessage(p, "§cDieses Item ist nicht signiert.");

            return CommandResult.None;
        } else {
            return CommandResult.InvalidUsage;
        }
    }
}