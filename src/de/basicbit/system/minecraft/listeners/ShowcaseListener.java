package de.basicbit.system.minecraft.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.basicbit.system.minecraft.Listener;

public class ShowcaseListener extends Listener {

    public static final List<String> showcases = new ArrayList<>();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        for (final String name : showcases) {
            if (name.equalsIgnoreCase(e.getInventory().getName())) {
                if (e.getWhoClicked() instanceof Player) {
                    if(isAdmin((Player) e.getWhoClicked())){
                        e.setCancelled(false);
                        return;
                    }
                }
                e.setCancelled(true);
            }
        }
    }
}