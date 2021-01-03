package de.basicbit.system.minecraft.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.basicbit.system.database.UserData;
import de.basicbit.system.database.UserValue;
import de.basicbit.system.minecraft.Listener;

public class PlayerDataHandler extends Listener {
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        setDataToPlayer(p, UserData.get(p, UserValue.minecraftPlayerData));
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWorldChange(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();
        if (isKnockItWorld(e.getFrom())) {
            setDataToPlayer(p, UserData.get(p, UserValue.minecraftPlayerData));
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onWorldChange2(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();
        if (isKnockItWorld(p.getWorld())) {
            UserData.set(p, UserValue.minecraftPlayerData, getDataFromPlayer(p));
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (!isKnockItWorld(p.getWorld())) {
            UserData.set(p, UserValue.minecraftPlayerData, getDataFromPlayer(p));
        }
    }
}