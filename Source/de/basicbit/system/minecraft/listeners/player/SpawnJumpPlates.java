package de.basicbit.system.minecraft.listeners.player;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import de.basicbit.system.minecraft.Listener;

public class SpawnJumpPlates extends Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();

        if (p.getLocation().getBlock().getType() == Material.IRON_PLATE && (isInKnockIt(p) || isSpawnWorld(p.getWorld()))) {
            final Vector v = p.getLocation().getDirection().multiply(3).setY(0.75);
            p.setVelocity(new Vector(v.getX(), v.getY(), v.getZ()));
            p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 1, 1);
        }
    }

}