package de.basicbit.system.minecraft.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import de.basicbit.system.minecraft.Listener;

public class MapListener extends Listener {
    
    @EventHandler
    public void onCraft(PrepareItemCraftEvent e) {
        Material itemType = e.getRecipe().getResult().getType();
        if (itemType == Material.EMPTY_MAP) {
            e.getInventory().setResult(new ItemStack(Material.AIR));
        }
    }
}