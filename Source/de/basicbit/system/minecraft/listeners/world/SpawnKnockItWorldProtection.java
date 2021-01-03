package de.basicbit.system.minecraft.listeners.world;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import de.basicbit.system.minecraft.Listener;

public class SpawnKnockItWorldProtection extends Listener {

	/*
	 * Zerstören von Blöcken am Spawn und in KnockIt verhindern.
	 */
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		World w = e.getBlock().getWorld();
		
		if (equalsWorld(w, getKnockItWorld()) || equalsWorld(w, getSpawnWorld())) {
			e.setCancelled(!p.getGameMode().equals(GameMode.CREATIVE) || !p.isSneaking());
		}
	}

	/*
	 * Plazieren von Blöcken am Spawn und in KnockIt verhindern.
	 */
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		World w = e.getBlock().getWorld();
		
		if (equalsWorld(w, getKnockItWorld()) || equalsWorld(w, getSpawnWorld())) {
			e.setCancelled(!p.getGameMode().equals(GameMode.CREATIVE) || !p.isSneaking());
		}
	}

	/*
	 * Interagieren am Spawn einschränken.
	 */
	@EventHandler
	@SuppressWarnings("deprecation")
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		ItemStack is = e.getItem();
		Block clickedBlock = e.getClickedBlock();
		
		if ((isInWorld(p, getSpawnWorld()) || isInWorld(p, getKnockItWorld())) && e.getAction() == Action.PHYSICAL) {
			e.setCancelled(!p.getGameMode().equals(GameMode.CREATIVE));
			return;
		}

		if (isInWorld(p, getSpawnWorld())) {
			
			e.setUseInteractedBlock(Result.DENY);
			e.setUseItemInHand(isInSkyPvP(p) ? Result.ALLOW : Result.DENY);

			if (p.getGameMode().equals(GameMode.CREATIVE)) {
				e.setUseInteractedBlock(Result.DEFAULT);
				e.setUseItemInHand(Result.DEFAULT);
			}

			if (clickedBlock != null) {
				if (clickedBlock.getType().equals(Material.ENDER_CHEST)
						|| clickedBlock.getType().equals(Material.WORKBENCH)
						|| clickedBlock.getType().equals(Material.WOODEN_DOOR)
						|| clickedBlock.getType().equals(Material.ENCHANTMENT_TABLE)) {
					e.setUseInteractedBlock(Result.DEFAULT);
				}
			}

			if (is != null) {
				if (is.getType().isEdible() || is.getTypeId() == 335) {
					e.setUseItemInHand(Result.DEFAULT);
				} else if (e.useItemInHand() == Result.DENY) {
					p.updateInventory();
				}
			}
		}
	}

	/*
	 * Das Zerstören von ItemFrames und Bildern am Spawn und in KnockIt verhindern.
	 */
	@EventHandler
	public void onHangingBreak(HangingBreakByEntityEvent e) {
		if (e.getRemover() instanceof Player) {
			Player p = (Player)e.getRemover();
			World w = e.getEntity().getWorld();
			
			if (equalsWorld(w, getKnockItWorld()) || equalsWorld(w, getSpawnWorld())) {
				e.setCancelled(!p.getGameMode().equals(GameMode.CREATIVE));
			}
		}
	}

	/*
	 * Verhindert das Spawnen von Lebewesen am Spawn und in KnockIt.
	 */
	@EventHandler
	public void onSpawn(EntitySpawnEvent e) {
		Entity en = e.getEntity();
		World w = e.getLocation().getWorld();
		
		if (equalsWorld(w, getKnockItWorld()) || equalsWorld(w, getSpawnWorld())) {
			e.setCancelled(en instanceof LivingEntity);
		}
	}
}
