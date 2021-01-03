package de.basicbit.system.minecraft.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.basicbit.system.minecraft.Listener;

@SuppressWarnings("deprecation")
public class Majo extends Listener {
    
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action action = e.getAction();

        if ((action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) && e.hasItem()) {
            ItemStack is = e.getItem();

            if (is.getTypeId() == 351 && is.getDurability() == 7 && is.hasItemMeta()) {
                int count = is.getAmount();
                ItemMeta im = is.getItemMeta();

                if (im.hasDisplayName() && im.getDisplayName().contentEquals(getMajoItemStack(1).getItemMeta().getDisplayName())) {
                    if (count == 1) {
                        if (p.getFoodLevel() >= 20) {
                            sendMessage(p, "§cDu bist bereits satt.");
                            sendMessage(p, "§cBitte verschwende nicht deine Majo. >:(");
                        } else {
                            sendMessage(p, "§aGuten Hunger!");

                            p.setFoodLevel(40);

                            p.setItemInHand(null);
                        }
                    } else {
                        if (p.getFoodLevel() >= 20) {
                            sendMessage(p, "§cDu bist bereits satt.");
                            sendMessage(p, "§cBitte verschwende nicht deine Majo. >:(");
                        } else {
                            sendMessage(p, "§aGuten Hunger!");

                            p.setFoodLevel(40);

                            is.setAmount(count - 1);
                            p.setItemInHand(is);
                        }
                    }
                }
            }
        }
    }
}