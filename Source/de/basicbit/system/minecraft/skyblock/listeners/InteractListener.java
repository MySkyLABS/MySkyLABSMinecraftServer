package de.basicbit.system.minecraft.skyblock.listeners;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Event.Result;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import de.basicbit.system.database.UserData;
import de.basicbit.system.database.UserValue;
import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.skyblock.SkyBlock;

public class InteractListener extends Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        World w = p.getWorld();

        if (isSkyblockWorld(w)) {
            UUID id = getUUIDFromSkyBlockWorld(w);
            int number = getNumberFromSkyBlockWorld(w);

            boolean allowInteract = UserData.getBoolean(id, UserValue.valueOf("island" + number + "UsersInteract"));

            if (!allowInteract && !isOwnerOfWorld(w, p) && !isInTeam(p) && !SkyBlock.isTrusted(id, number, p)) {
                final ArrayList<Material> blockInteractList = new ArrayList<Material>();
                blockInteractList.add(Material.LEVER);
                blockInteractList.add(Material.STONE_BUTTON);
                blockInteractList.add(Material.WOOD_BUTTON);
                blockInteractList.add(Material.ACACIA_DOOR);
                blockInteractList.add(Material.BIRCH_DOOR);
                blockInteractList.add(Material.DARK_OAK_DOOR);
                blockInteractList.add(Material.JUNGLE_DOOR);
                blockInteractList.add(Material.SPRUCE_DOOR);
                blockInteractList.add(Material.WOOD_DOOR);
                blockInteractList.add(Material.WOODEN_DOOR);
                blockInteractList.add(Material.TRAP_DOOR);
                blockInteractList.add(Material.REDSTONE_COMPARATOR);
                blockInteractList.add(Material.REDSTONE_COMPARATOR_ON);
                blockInteractList.add(Material.REDSTONE_COMPARATOR_OFF);
                blockInteractList.add(Material.DIODE);
                blockInteractList.add(Material.DIODE_BLOCK_OFF);
                blockInteractList.add(Material.DIODE_BLOCK_ON);
                blockInteractList.add(Material.DAYLIGHT_DETECTOR);
                blockInteractList.add(Material.DAYLIGHT_DETECTOR_INVERTED);
                blockInteractList.add(Material.JUKEBOX);
                blockInteractList.add(Material.NOTE_BLOCK);
                blockInteractList.add(Material.FENCE_GATE);
                blockInteractList.add(Material.ACACIA_FENCE_GATE);
                blockInteractList.add(Material.BIRCH_FENCE_GATE);
                blockInteractList.add(Material.DARK_OAK_FENCE_GATE);
                blockInteractList.add(Material.JUNGLE_FENCE_GATE);
                blockInteractList.add(Material.SPRUCE_FENCE_GATE);
                blockInteractList.add(Material.ENDER_PORTAL_FRAME);
                blockInteractList.add(Material.BED);
                blockInteractList.add(Material.BED_BLOCK);
                blockInteractList.add(Material.ANVIL);

                final ArrayList<Material> blockInteractListItem = new ArrayList<Material>();
                blockInteractListItem.add(Material.MONSTER_EGG);
                blockInteractListItem.add(Material.MONSTER_EGGS);
                blockInteractListItem.add(Material.EGG);
                blockInteractListItem.add(Material.POTION);

                Block b = e.getClickedBlock();
                ItemStack is = e.getItem();

                if (b != null
                        && (blockInteractList.contains(b.getType())
                                || ((CraftWorld) w).getTileEntityAt(b.getX(), b.getY(), b.getZ()) != null)
                        && !ShowChestListener.isShowChest(b)) {
                    e.setUseInteractedBlock(Result.DENY);

                    if (is == null) {
                        e.setCancelled(true);
                    }

                    sendMessage(p, "§cDer Besitzer hat das Interagieren für Besucher deaktiviert.");
                }

                if (is != null && blockInteractListItem.contains(is.getType()) && !ShowChestListener.isShowChest(b)) {

                    e.setUseInteractedBlock(Result.DENY);
                    e.setUseItemInHand(Result.DENY);

                    sendMessage(p, "§cDer Besitzer hat das Interagieren für Besucher deaktiviert.");
                }
            }
        }
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        World w = p.getWorld();

        if (isSkyblockWorld(w)) {
            UUID id = getUUIDFromSkyBlockWorld(w);
            int number = getNumberFromSkyBlockWorld(w);

            boolean allowInteract = UserData.getBoolean(id, UserValue.valueOf("island" + number + "UsersInteract"));

            if (!allowInteract && !isOwnerOfWorld(w, p) && !isInTeam(p) && !SkyBlock.isTrusted(id, number, p)) {
                e.setCancelled(true);

                sendMessage(p, "§cDer Besitzer hat das Interagieren für Besucher deaktiviert.");
            }
        }
    }

    @EventHandler
    public void onInteractEntity(EntityDamageByEntityEvent e) {
        Entity d = e.getDamager();

        if (d instanceof Player && !(e.getEntity() instanceof Player)) {
            Player p = (Player) d;
            World w = p.getWorld();

            if (isSkyblockWorld(w)) {
                UUID id = getUUIDFromSkyBlockWorld(w);
                int number = getNumberFromSkyBlockWorld(w);

                boolean allowInteract = UserData.getBoolean(id, UserValue.valueOf("island" + number + "UsersInteract"));

                if (!allowInteract && !isOwnerOfWorld(w, p) && !isInTeam(p) && !SkyBlock.isTrusted(id, number, p)) {
                    e.setCancelled(true);

                    sendMessage(p, "§cDer Besitzer hat das Interagieren für Besucher deaktiviert.");
                }
            }
        }
    }
}