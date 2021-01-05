package de.basicbit.system.minecraft.listeners;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.InventoryHolder;

import de.basicbit.system.minecraft.Listener;

public class AntiDuplicate extends Listener {
    
    @EventHandler
    public void onExplode(EntityExplodeEvent e) {

        for (int i = e.blockList().size() - 1; i >= 0; i--) {
            Block b = e.blockList().get(i);

            if (b.getState() instanceof InventoryHolder) {
                e.blockList().remove(i);
            }
        }
    }
}