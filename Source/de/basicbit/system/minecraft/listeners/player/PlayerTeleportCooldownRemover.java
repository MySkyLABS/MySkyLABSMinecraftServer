package de.basicbit.system.minecraft.listeners.player;

import java.util.UUID;

import org.bukkit.event.player.PlayerQuitEvent;

import de.basicbit.system.GlobalValues;
import de.basicbit.system.minecraft.Listener;

public class PlayerTeleportCooldownRemover extends Listener {
    
    public void onQuit(PlayerQuitEvent e) {
        UUID id = e.getPlayer().getUniqueId();
        if (GlobalValues.teleportCooldownPlayers.contains(id)) {
            GlobalValues.teleportCooldownPlayers.remove(id);
        }
    }
}