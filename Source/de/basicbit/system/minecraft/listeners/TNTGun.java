package de.basicbit.system.minecraft.listeners;

import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.basicbit.system.GlobalValues;
import de.basicbit.system.minecraft.Listener;

public class TNTGun extends Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        World world = p.getWorld();
        List<Block> blocks = p.getLineOfSight((Set<Material>)null, 100);
        Block block = p.getTargetBlock((Set<Material>)null, 100);

        if ((e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) && isSkyblockWorld(world) && GlobalValues.tntGunPlayers.contains(p.getUniqueId())) {
            for (Block b : blocks) {
                world.createExplosion(b.getLocation(), 1);
            }

            world.createExplosion(block.getLocation(), 10);
        }
    }
}