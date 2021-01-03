package de.basicbit.system.minecraft.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.tasks.TaskManager;

public class InvisibleBugFix extends Listener {
    
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        TaskManager.runSyncTaskLater("InvisibleBugFix", new Runnable(){
        
            @Override
            public void run() {
                for (Player t : getPlayers()) {
                    if (!isVanish(p)) {
                        t.hidePlayer(p);
                        t.showPlayer(p);
                    }
                }
            }
        }, 40);
    }
}