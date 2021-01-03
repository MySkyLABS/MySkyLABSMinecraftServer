package de.basicbit.system.minecraft.skyblock.listeners;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import de.basicbit.system.database.UserData;
import de.basicbit.system.database.UserValue;
import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.skyblock.SkyBlock;

public class BuildListener extends Listener {
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        World w = p.getWorld();
        
        if (isSkyblockWorld(w)) {
            UUID id = getUUIDFromSkyBlockWorld(w);
            int number = getNumberFromSkyBlockWorld(w);

            if (!UserData.getBoolean(id, UserValue.valueOf("island" + number + "UsersBuild")) && !isOwnerOfWorld(w, p) && !isInTeam(p) && !SkyBlock.isTrusted(id, number, p)) {
                e.setCancelled(true);

                sendMessage(p, "§cDer Besitzer hat das Bauen für Besucher deaktiviert.");
            }
        }
    }
    
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        World w = p.getWorld();
        
        if (isSkyblockWorld(w)) {
            UUID id = getUUIDFromSkyBlockWorld(w);
            int number = getNumberFromSkyBlockWorld(w);

            if (!UserData.getBoolean(id, UserValue.valueOf("island" + number + "UsersBuild")) && !isOwnerOfWorld(w, p) && !isInTeam(p) && !SkyBlock.isTrusted(id, number, p)) {
                e.setCancelled(true);

                sendMessage(p, "§cDer Besitzer hat das Bauen für Besucher deaktiviert.");
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        World w = p.getWorld();

        if (isSkyblockWorld(w)) {
            UUID id = getUUIDFromSkyBlockWorld(w);
            int number = getNumberFromSkyBlockWorld(w);

            ArrayList<Material> buildItems = new ArrayList<Material>();
            buildItems.add(Material.ITEM_FRAME);
            buildItems.add(Material.ARMOR_STAND);
            buildItems.add(Material.BUCKET);
            buildItems.add(Material.WATER_BUCKET);
            buildItems.add(Material.LAVA_BUCKET);
            buildItems.add(Material.PAINTING);
    
            ItemStack is = p.getItemInHand();
    
            if (is != null && buildItems.contains(is.getType()) && !isOwnerOfWorld(w, p) && !isInTeam(p) && !SkyBlock.isTrusted(id, number, p)) {
                e.setUseInteractedBlock(Result.DENY);
                e.setUseItemInHand(Result.DENY);
            }
        }
    }
}