package de.basicbit.system.minecraft.skyblock.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockFromToEvent;

import de.basicbit.system.GlobalValues;
import de.basicbit.system.minecraft.Listener;

public class CobblestoneGenerator extends Listener {

	@EventHandler
	public void onBlockFlow(BlockFromToEvent e) {
		Material type = e.getBlock().getType();
		
		if (type == Material.WATER || type == Material.STATIONARY_WATER || type == Material.LAVA || type == Material.STATIONARY_LAVA) {
			Block b = e.getToBlock();
			
			if (isSkyblockWorld(b.getWorld())) {
				if (b.getType() == Material.AIR) {
					if (generatesCobble(type, b)) {
						e.setCancelled(true);
	
						byte[] bytes = new byte[1];
						GlobalValues.random.nextBytes(bytes);
						byte r = bytes[0];

						if (r < 0 || r >= 69) {
							b.setType(Material.COBBLESTONE);
							return;
						}
	
						if (r < 20) {
							b.setType(Material.COAL_ORE);
						} else if (r < 40) {
							b.setType(Material.IRON_ORE);
						} else if (r < 50) {
							b.setType(Material.REDSTONE_ORE);
						} else if (r < 65) {
							b.setType(Material.LAPIS_ORE);
						} else if (r < 67) {
							b.setType(Material.DIAMOND_ORE);
						} else if (r < 68) {
							b.setType(Material.GOLD_ORE);
						} else if (r < 69) {
							b.setType(Material.EMERALD_ORE);
						}
					}
				}
			}
		}
	}

	private final BlockFace[] blockFaces = new BlockFace[] { BlockFace.SELF, BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };

	public boolean generatesCobble(Material type, Block b) {
		Material mirrorID1 = (type == Material.WATER || type == Material.STATIONARY_WATER ? Material.LAVA : Material.WATER);
		Material mirrorID2 = (type == Material.WATER || type == Material.STATIONARY_WATER ? Material.STATIONARY_LAVA : Material.STATIONARY_WATER);
		
		for (BlockFace face : blockFaces) {
			Block r = b.getRelative(face, 1);
			
			if (r.getType() == mirrorID1 || r.getType() == mirrorID2) {
				return true;
			}
		}
		
		return false;
	}
	
}
