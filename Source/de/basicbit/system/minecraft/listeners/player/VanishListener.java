package de.basicbit.system.minecraft.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.PlayerListSystem;
import de.basicbit.system.minecraft.tasks.TaskManager;

public class VanishListener extends Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        
        if (isVanish(p)) {
            updateVanish(p);
        }

        updateVanish(p);

        TaskManager.runSyncTaskLater("VanishListenerUpdatePlayerlist", new Runnable(){
        
            @Override
            public void run() {
                PlayerListSystem.update();
            }
        }, 20);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        if (isVanish(p)) {
            updateVanish(p);
        }
    }
}