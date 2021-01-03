package de.basicbit.system.minecraft.listeners.player;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;

import de.basicbit.system.minecraft.Listener;

public class InvseeEnderChestListener extends Listener {
    
    @EventHandler
    public static void onClick(InventoryClickEvent e) {
        HumanEntity h = e.getWhoClicked();

        if (h instanceof Player) {
            Player p = (Player) h;

            InventoryView view = p.getOpenInventory();
            Inventory inv = view.getTopInventory();
            
            if (inv != null) {
                InventoryHolder invHolder = inv.getHolder();

                if (invHolder instanceof Player) {
                    Player t = (Player) invHolder;

                    if (isInKnockIt(t)) {
                        e.setCancelled(true);
                        return;
                    }
                    
                    if (!isModerator(p) && !t.equals(p) && !inv.equals(p.getInventory())) {
                        e.setCancelled(true);
                    }
                } else {
                    if (!isModerator(p) && !inv.equals(p.getEnderChest()) && inv.getName().equals(p.getEnderChest().getName())) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}