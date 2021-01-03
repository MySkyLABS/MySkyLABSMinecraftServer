package de.basicbit.system.minecraft.listeners.world;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.world.WorldInitEvent;

import de.basicbit.system.minecraft.Listener;

public class SkyBlockAntiWorldInitLag extends Listener {
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWorldInit(WorldInitEvent e)
    {
        e.getWorld().setKeepSpawnInMemory(false);
    }
}