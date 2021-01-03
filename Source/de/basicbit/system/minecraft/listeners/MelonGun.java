package de.basicbit.system.minecraft.listeners;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import de.basicbit.system.GlobalValues;
import de.basicbit.system.minecraft.Listener;

@SuppressWarnings("deprecation")
public class MelonGun extends Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Block b = p.getTargetBlock((Set<Material>)null, 100);
        Location end = new Location(b.getWorld(), b.getX() + 0.5, b.getY() + 0.5, b.getZ() + 0.5);

        if ((e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR) && GlobalValues.melonGunPlayers.contains(p.getUniqueId())) {
            FallingBlock fb = p.getWorld().spawnFallingBlock(p.getLocation(), Material.MELON_BLOCK, (byte)0);

            final Location lc = fb.getLocation();
            lc.setY(lc.getY() + 0.8D);
            fb.teleport(lc);
            final double g = -0.08D;
            final double d = end.distance(lc);
            final double t = d;
            final double v_x = (1.0D + 0.07D * t) * (end.getX() - lc.getX()) / t;
            final double v_y = (1.0D + 0.03D * t) * (end.getY() - lc.getY()) / t - 0.5D * g * t;
            final double v_z = (1.0D + 0.07D * t) * (end.getZ() - lc.getZ()) / t;
            final Vector v = fb.getVelocity();
            v.setX(v_x);
            v.setY(v_y);
            v.setZ(v_z);
            fb.setVelocity(v.multiply(0.5));
            p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 1.0F, 1.0F);
            e.setCancelled(true);
        }
    }
}