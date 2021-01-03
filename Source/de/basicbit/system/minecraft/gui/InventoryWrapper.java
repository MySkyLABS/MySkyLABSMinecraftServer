package de.basicbit.system.minecraft.gui;

import java.util.HashMap;

import org.bukkit.inventory.ItemStack;

class InventoryWrapper {
    
    private HashMap<Integer, ItemStack> items = new HashMap<Integer, ItemStack>();

    InventoryWrapper() {
    }

    void setItem(int slot, ItemStack is) {
        items.put(slot, is);
    }

    void setItem(int x, int y, ItemStack is) {
        setItem(y * 9 + x, is);
    }

    ItemStack getItem(int slot) {
        if (items.containsKey(slot)) {
            return items.get(slot);
        } else {
            return null;
        }
    }

	public boolean hasItem() {
		return items.keySet().size() != 0;
	}
}