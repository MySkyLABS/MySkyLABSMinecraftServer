package de.basicbit.system.minecraft.listeners.player;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import de.basicbit.system.minecraft.Listener;

public class CaseBounceListener extends Listener {
    
    @EventHandler
    public static void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Location playerPos = p.getLocation();

        ArrayList<Location> locations = new ArrayList<Location>();
        locations.add(new Location(getSpawnWorld(), -31.5, 197.5, 8.5));
        locations.add(new Location(getSpawnWorld(), -32.5, 197.5, 10.5));
        locations.add(new Location(getSpawnWorld(), -30.5, 197.5, 6.5));
        locations.add(new Location(getSpawnWorld(), -28.5, 197.5, 5.5));
        locations.add(new Location(getSpawnWorld(), -26.5, 197.5, 5.5));
        locations.add(new Location(getSpawnWorld(), -24.5, 197.5, 6.5));

        for (Location location : locations) {
            if (p.getWorld().getName().contentEquals(location.getWorld().getName())) {
                if (location.distance(playerPos) <= 1.5) {
                    p.setVelocity(playerPos.toVector().subtract(location.toVector()));
                }
            }
        }
    }
}