package de.basicbit.system.minecraft.listeners.player.join;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

import de.basicbit.system.minecraft.Group;
import de.basicbit.system.minecraft.Listener;

public class AdminJoinCoins extends Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (!isVanish(p)) {
            if (getGroup(p).equals(Group.ADMIN)) {
                payAll(1, " AdminJoinCoins");
            }
    
            if (isOwner(p)) {
                payAll(3, " AdminJoinCoins");
            }
        }
    }

    public static void power(Player p) {
        if (!isVanish(p)) {
            if (getGroup(p).equals(Group.ADMIN)) {
                payAll(1, " AdminJoinCoins");
            }
    
            if (isOwner(p)) {
                payAll(3, " AdminJoinCoins");
            }
        }
    }
}