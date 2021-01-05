package de.basicbit.system.minecraft.listeners;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import com.google.common.hash.Hashing;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.basicbit.system.GlobalValues;
import de.basicbit.system.minecraft.Listener;
import net.minecraft.server.v1_8_R1.NBTTagCompound;

@SuppressWarnings("deprecation")
public class BackpackListener extends Listener {

    public static final String emptyMiniBackpackContent = inventoryToNBT(Bukkit.createInventory(null, InventoryType.DISPENSER, "")).toString();
    public static final String emptyBackpackContent = inventoryToNBT(Bukkit.createInventory(null, 54, "")).toString();
    
    @EventHandler
    public static void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            int slot = p.getInventory().getHeldItemSlot();
            ItemStack backpack = p.getInventory().getItem(slot);

            if (backpack != null) {
                if (backpack.hasItemMeta()) {
                    ItemMeta backpackMeta = backpack.getItemMeta();

                    if (backpackMeta.hasDisplayName()) {
                        if (backpackMeta.getDisplayName().startsWith("§r§6§r")) {          //klein
                            Inventory inv = null;
                            NBTTagCompound tag = CraftItemStack.asNMSCopy(backpack).getTag();

                            GlobalValues.backpackStartSlot.put(p.getUniqueId(), slot);

                            if (tag.hasKey("BackpackInventory")) {
                                if (backpack.getAmount() != 1) {
                                    sendMessage(p, "§cBitte nimm nur ein Backpack in die Hand.");
                                    return;
                                }

                                NBTTagCompound invtag = tag.getCompound("BackpackInventory");
                                inv = Bukkit.createInventory(null, 9, "§5Kleiner Rucksack");

                                for (int i = 0; i < 9; i++) {
                                    if (invtag.hasKey(String.valueOf(i))) {
                                        inv.setItem(i, tagToItem(invtag.getCompound(String.valueOf(i))));
                                    }
                                }
                            } else {
                                inv = Bukkit.createInventory(null, 9, "§5Kleiner Rucksack");
                            }

                            p.openInventory(inv);
                        } else if (backpackMeta.getDisplayName().startsWith("§r§7§r")) {   //groß
                            Inventory inv = null;
                            NBTTagCompound tag = CraftItemStack.asNMSCopy(backpack).getTag();
                                
                            GlobalValues.backpackStartSlot.put(p.getUniqueId(), slot);

                            if (tag.hasKey("BackpackInventory")) {
                                if (backpack.getAmount() != 1) {
                                    sendMessage(p, "§cBitte nimm nur ein Backpack in die Hand.");
                                    return;
                                }

                                NBTTagCompound invtag = tag.getCompound("BackpackInventory");
                                inv = Bukkit.createInventory(null, 54, "§5Großer Rucksack");

                                for (int i = 0; i < 54; i++) {
                                    if (invtag.hasKey(String.valueOf(i))) {
                                        inv.setItem(i, tagToItem(invtag.getCompound(String.valueOf(i))));
                                    }
                                }
                            } else {
                                inv = Bukkit.createInventory(null, 54, "§5Großer Rucksack");
                            }


                            p.openInventory(inv);
                        } 
                    }
                }
            }
        }
    }

    @EventHandler
    public static void onInventoryClick(InventoryClickEvent e) {
        org.bukkit.entity.HumanEntity h = e.getWhoClicked();

        if (h instanceof Player) {
            Player p = (Player) h;
            InventoryView view = p.getOpenInventory();

            if (e.getAction() == InventoryAction.HOTBAR_SWAP || e.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD) {
                if (GlobalValues.backpackStartSlot.containsKey(p.getUniqueId())) {
                    if (e.getHotbarButton() == GlobalValues.backpackStartSlot.get(p.getUniqueId())) {
                        e.setCancelled(true);
                        return;
                    }
                }
            }

            if (p.getInventory().equals(e.getClickedInventory())) {
                if (GlobalValues.backpackStartSlot.containsKey(p.getUniqueId())) {
                    if (e.getSlot() == GlobalValues.backpackStartSlot.get(p.getUniqueId())) {
                        e.setCancelled(true);
                        return;
                    }
                }
            }
            
            if (view != null) {
                Inventory topInv = view.getTopInventory();


                if (topInv != null) {
                    InventoryHolder holder = topInv.getHolder();

                    if (topInv.getTitle().contains("Rucksack")) {
                        Inventory clickedInv = e.getClickedInventory();

                        if (clickedInv != null && clickedInv.getHolder() == p) {
                            ItemStack is = e.getCurrentItem();

                            if (is != null && is.hasItemMeta()) {
                                ItemMeta im = is.getItemMeta();

                                if (im.hasDisplayName() && (im.getDisplayName().startsWith("§r§6§r") || im.getDisplayName().startsWith("§r§7§r"))) {
                                    e.setCancelled(true);
                                }
                            }
                        }
                    } else if (holder instanceof Player) {
                        Player t = (Player) holder;
                        view = t.getOpenInventory();

                        if (e.getAction() == InventoryAction.HOTBAR_SWAP || e.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD) {
                            if (GlobalValues.backpackStartSlot.containsKey(t.getUniqueId())) {
                                if (e.getHotbarButton() == GlobalValues.backpackStartSlot.get(t.getUniqueId())) {
                                    e.setCancelled(true);
                                    return;
                                }
                            }
                        }
                        
                        if (t.getInventory().equals(e.getClickedInventory())) {
                            if (GlobalValues.backpackStartSlot.containsKey(t.getUniqueId())) {
                                if (e.getSlot() == GlobalValues.backpackStartSlot.get(t.getUniqueId())) {
                                    e.setCancelled(true);
                                    return;
                                }
                            }
                        }

                        if (view != null) {
                            topInv = view.getTopInventory();
            
                            if (topInv != null) {
                                holder = topInv.getHolder();
            
                                if (topInv.getTitle().contains("Rucksack")) {
                                    Inventory clickedInv = e.getClickedInventory();
            
                                    if (clickedInv != null && clickedInv.getHolder() == t) {
                                        ItemStack is = e.getCurrentItem();
            
                                        if (is != null && is.hasItemMeta()) {
                                            ItemMeta im = is.getItemMeta();
            
                                            if (im.hasDisplayName() && (im.getDisplayName().startsWith("§r§6§r") || im.getDisplayName().startsWith("§r§7§r"))) {
                                                e.setCancelled(true);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public static void onQuit(PlayerQuitEvent e) {
        UUID id = e.getPlayer().getUniqueId();
        if (GlobalValues.backpackStartSlot.containsKey(id)) {
            GlobalValues.backpackStartSlot.remove(id);
        }
    }

    @EventHandler
    public static void onInventoryClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        Inventory inv = e.getInventory();

        if (inv.getName().equalsIgnoreCase("§5Kleiner Rucksack")) {
            ItemStack is = p
                .getInventory()
                .getItem(GlobalValues
                .backpackStartSlot
                .get(p
                .getUniqueId()));
            String invStr = inventoryToNBT(inv).toString();

            if (invStr.equals(emptyMiniBackpackContent)) {
                Bukkit.getUnsafe().modifyItemStack(is, "{BackpackInventory:" + invStr + "}");
            } else {
                String hash = Hashing.sha256().hashString(System.currentTimeMillis() + p.getName(), StandardCharsets.UTF_8).toString();
                Bukkit.getUnsafe().modifyItemStack(is, "{BackpackInventory:" + invStr + ", Hash:" + hash + "}");
            }

            ItemMeta im = is.getItemMeta();
            im.setDisplayName("§r§6§r§6Kleiner Rucksack");
            im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            is.setItemMeta(im);
            is.addUnsafeEnchantment(Enchantment.OXYGEN, 1);
            GlobalValues.backpackStartSlot.remove(p.getUniqueId());
        } else if (inv.getName().equalsIgnoreCase("§5Großer Rucksack")) {
            ItemStack is = p.getInventory().getItem(GlobalValues.backpackStartSlot.get(p.getUniqueId()));
            String invStr = inventoryToNBT(inv).toString();

            if (invStr.equals(emptyBackpackContent)) {
                Bukkit.getUnsafe().modifyItemStack(is, "{BackpackInventory:" + invStr + "}");
            } else {
                String hash = Hashing.sha256().hashString(System.currentTimeMillis() + p.getName(), StandardCharsets.UTF_8).toString();
                Bukkit.getUnsafe().modifyItemStack(is, "{BackpackInventory:" + invStr + ", Hash:" + hash + "}");
            }

            ItemMeta im = is.getItemMeta();
            im.setDisplayName("§r§7§r§6Großer Rucksack");
            im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            is.setItemMeta(im);
            is.addUnsafeEnchantment(Enchantment.OXYGEN, 1);
            GlobalValues.backpackStartSlot.remove(p.getUniqueId());
        }
    }

    @EventHandler
    public void onPickUpItem(PlayerPickupItemEvent e) {
        if (GlobalValues.backpackStartSlot.containsKey(e.getPlayer().getUniqueId())) {
            ItemStack is = e.getItem().getItemStack();
            if (is != null) {
                if (is.hasItemMeta()) {
                    ItemMeta im = is.getItemMeta();
                    if (im.hasDisplayName()) {
                        String name = im.getDisplayName();
                        if (name.startsWith("§r§6§r") || name.startsWith("§r§7§r")) {
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

    public static NBTTagCompound inventoryToNBT(Inventory inv) {
        NBTTagCompound tag = new NBTTagCompound();
        int i = 0;
        for (ItemStack is : inv.getContents()) {
            if (is == null) {
                is = new ItemStack(0, 1, (short) 0);
            }
            tag.set(String.valueOf(i), itemToTag(is));
            i++;
        }
        return tag;
    }

    public static NBTTagCompound itemToTag(ItemStack is) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInt("id", is.getTypeId());
        tag.setInt("amount", is.getAmount());
        tag.setShort("durability", is.getDurability());
        net.minecraft.server.v1_8_R1.ItemStack test = CraftItemStack.asNMSCopy(is);
        if (test != null) {
            if (CraftItemStack.asNMSCopy(is).hasTag()) {
                NBTTagCompound temp = CraftItemStack.asNMSCopy(is).getTag();
                if (temp != null) {
                    tag.set("tag", CraftItemStack.asNMSCopy(is).getTag());
                }
            }
        }
        return tag;
    }

    public static ItemStack tagToItem(NBTTagCompound tag) {
        ItemStack is = new ItemStack(tag.getInt("id"), tag.getInt("amount"), tag.getShort("durability"));
        if (tag.hasKey("tag")) {
            Bukkit.getUnsafe().modifyItemStack(is, tag.get("tag").toString());
        }
        return is;
    }

}