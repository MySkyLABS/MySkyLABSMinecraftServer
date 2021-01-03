package de.basicbit.system.minecraft.skyblock.listeners;

import java.io.IOException;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.v1_8_R1.block.CraftChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import de.basicbit.system.GlobalValues;
import de.basicbit.system.minecraft.HologramSystem;
import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.skyblock.SkyBlock;
import de.basicbit.system.minecraft.skyblock.WriteToSkyBlockFile;

public class ShowChestListener extends Listener {

    @EventHandler
    public static void onShowChestClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (isShowChest(e) && !isAllowedToInteract(p)) {
            e.setCancelled(true);
        }
    }

    private static boolean isAllowedToInteract(Player p) {
        World w = p.getWorld();
        if (isOwnerOfWorld(w, p)) {
            return true;
        }
        if (SkyBlock.isTrusted(w, p)) {
            return true;
        }
        if (isInTeam(p)) {
            return true;
        }
        return false;
    }

    private static boolean isShowChest(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory invClicked = e.getClickedInventory();
        Inventory invShowChest = e.getInventory();
        try {
            if (invClicked.getTitle().toLowerCase().indexOf("showcase") > -1 || !invClicked.equals(invShowChest)) {
                if (!isAllowedToInteract(p)) {
                    e.setCancelled(true);
                }
            }
        } catch (NullPointerException ne) {
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean isShowChest(Block b) {
        if (b.getType().equals(Material.CHEST)) {
            CraftChest chest = (CraftChest) b.getState();
            Inventory inv = chest.getBlockInventory();
            String title = inv.getTitle().toLowerCase();

            if (title.indexOf("showcase") > -1) {
                return true;
            }
        }

        return false;
    }

    @EventHandler
    public static void onShowChestPlace(BlockPlaceEvent e) throws IllegalArgumentException, IOException {
        Block b = e.getBlock();
        Location loc = b.getLocation();
        World w = b.getWorld();
        UUID id = getUUIDFromSkyBlockWorld(w);

        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();

        if (isShowChest(b)) {
            Chest chest = (Chest) b.getState();
            Inventory inv = chest.getInventory();
            String title = inv.getTitle();
            sendMessage(e.getPlayer(), "§aDas Hologramm wird generiert...");
            GlobalValues.showChestHolos.put(loc, new HologramSystem(new Location(w, x + 0.5, y + 1.2, z + 0.5), title));
            WriteToSkyBlockFile.write(x + ";" + y + ";" + z + "\n", getNumberFromSkyBlockWorld(w), id);
            GlobalValues.showChestHolos.get(loc).update(e.getPlayer());
        }
    }

    @EventHandler
    public static void onShowChestBreak(BlockBreakEvent e) throws IOException {
        Block b = e.getBlock();
        Location loc = b.getLocation();
        World w = b.getWorld();

        if (isShowChest(b)) {
            GlobalValues.showChestHolos.get(loc).destroy();
            GlobalValues.showChestHolos.remove(loc);
            
            int number = getNumberFromSkyBlockWorld(w);
            UUID id = getUUIDFromSkyBlockWorld(w);
            WriteToSkyBlockFile.remove(id, number, loc);
        }
    }
}
