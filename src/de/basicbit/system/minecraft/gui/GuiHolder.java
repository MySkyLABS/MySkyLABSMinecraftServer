package de.basicbit.system.minecraft.gui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class GuiHolder implements InventoryHolder {

    private Gui gui;
    private int page;

    public GuiHolder(Gui gui, int page) {
        this.gui = gui;
        this.page = page;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }

    public Gui getGui() {
        return gui;
    }

    public int getPage() {
        return page;
    }
}