package de.basicbit.system.minecraft.crafting.listeners;

// import java.lang.reflect.Field;

// import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;
// import org.bukkit.entity.Player;
// import org.bukkit.event.EventHandler;
// import org.bukkit.event.inventory.InventoryClickEvent;
// import org.bukkit.inventory.Inventory;
// import org.bukkit.inventory.ItemStack;
// import org.bukkit.inventory.meta.ItemMeta;

import de.basicbit.system.minecraft.Listener;
// import de.basicbit.system.minecraft.events.PlayerPacketOutEvent;
// import de.basicbit.system.minecraft.tasks.TaskManager;
// import net.minecraft.server.v1_8_R1.Blocks;
// import net.minecraft.server.v1_8_R1.Packet;
// import net.minecraft.server.v1_8_R1.PacketPlayOutEntityEquipment;
// import net.minecraft.server.v1_8_R1.PacketPlayOutSetSlot;

public class ColorItemsListener extends Listener {

    /*private static Field itemStackField = null;
    private static Field inventoryField = null;
    private static Field itemSlotField = null;
    private static Field armorStackField = null;

    static {
        try {
            itemStackField = PacketPlayOutEntityEquipment.class.getDeclaredField("c");
            itemStackField.setAccessible(true);
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        try {
            inventoryField = PacketPlayOutSetSlot.class.getDeclaredField("a");
            inventoryField.setAccessible(true);
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        try {
            itemSlotField = PacketPlayOutSetSlot.class.getDeclaredField("b");
            itemSlotField.setAccessible(true);
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        try {
            armorStackField = PacketPlayOutSetSlot.class.getDeclaredField("c");
            armorStackField.setAccessible(true);
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onPacketOut(PlayerPacketOutEvent e) {
        Packet p = e.getPacket();
        if (p instanceof PacketPlayOutEntityEquipment) {
            PacketPlayOutEntityEquipment ppoee = (PacketPlayOutEntityEquipment) p;
            try {
                Object object = itemStackField.get(ppoee);
                if (object instanceof net.minecraft.server.v1_8_R1.ItemStack) {
                    ItemStack is = CraftItemStack.asBukkitCopy((net.minecraft.server.v1_8_R1.ItemStack) object);
                    if (is.hasItemMeta()) {
                        ItemMeta im = is.getItemMeta();
                        if (im.hasDisplayName()) {
                            if (im.getDisplayName().startsWith("§r§l§r")) {
                                e.setCancelled(true);
                            }
                        }
                    }
                }
            } catch (IllegalArgumentException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        } else if (p instanceof PacketPlayOutSetSlot) {
            PacketPlayOutSetSlot pposs = (PacketPlayOutSetSlot) p;
            try {
                Object inv = inventoryField.get(pposs);
                if (inv instanceof Integer) {
                    int invId = (int) inv;
                    if (invId == 0) {
                        Object slot = itemSlotField.get(pposs);
                        if (slot instanceof Integer) {
                            int slotId = (int) slot;
                            //if (slotId == 5 || slotId == 6 || slotId == 7 || slotId == 8) {
                                Object object = armorStackField.get(pposs);
                                if (object instanceof net.minecraft.server.v1_8_R1.ItemStack) {
                                    ItemStack is = CraftItemStack.asBukkitCopy((net.minecraft.server.v1_8_R1.ItemStack) object);
                                    if (is.hasItemMeta()) {
                                        ItemMeta im = is.getItemMeta();
                                        if (im.hasDisplayName()) {
                                            if (im.getDisplayName().startsWith("§r§l§r")) {
                                                log(slotId);
                                                armorStackField.set(pposs, new net.minecraft.server.v1_8_R1.ItemStack(Blocks.STONE_BUTTON));
                                            }
                                        }
                                    }
                                }
                            //}
                        }
                    }
                }
            } catch (IllegalArgumentException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        }
    }

    //@EventHandler
    public void onArmorChange(InventoryClickEvent e) {
        org.bukkit.entity.HumanEntity h = e.getWhoClicked();
        if (h instanceof Player) {
            Player p = (Player) h;
            Inventory inv = p.getInventory();
            if (inv.equals(e.getClickedInventory())) {
                log("c1");
                ItemStack is = e.getCursor();
                if (is != null) {
                    log("c2");
                    int slot = e.getRawSlot();
                    log(slot);
                    if (slot == 5 || slot == 6 || slot == 7 || slot == 8) {
                        log("c3");
                        if (is.hasItemMeta()) {
                            log("c4");
                            ItemMeta im = is.getItemMeta();
                            if (im.hasDisplayName()) {
                                log("c5");
                                ItemStack[] armor = p.getInventory().getArmorContents();
                                ItemStack piece = null;
                                if (armor[0] != null) {
                                    log("c6.1");
                                    piece = armor[0];
                                    if (piece.hasItemMeta()) {
                                        log("c7.1");
                                        if (piece.getItemMeta().hasDisplayName()) {
                                            log("c8.1");
                                            if (piece.getItemMeta().getDisplayName().startsWith("§r§l§r")) {
                                                log("c9.1");
                                                TaskManager.runSyncTaskLater("Fake Armor Slot", () -> sendPacket(p, new PacketPlayOutSetSlot(0, 5, new net.minecraft.server.v1_8_R1.ItemStack(Blocks.STONE_BUTTON))), 2);
                                            }
                                        }
                                    }
                                }
                                if (armor[1] != null) {
                                    log("c6.2");
                                    piece = armor[1];
                                    if (piece.hasItemMeta()) {
                                        log("c7.2");
                                        if (piece.getItemMeta().hasDisplayName()) {
                                            log("c8.2");
                                            if (piece.getItemMeta().getDisplayName().startsWith("§r§l§r")) {
                                                log("c9.2");
                                                TaskManager.runSyncTaskLater("Fake Armor Slot", () -> sendPacket(p, new PacketPlayOutSetSlot(0, 6, new net.minecraft.server.v1_8_R1.ItemStack(Blocks.STONE_BUTTON))), 2);
                                            }
                                        }
                                    }
                                }
                                if (armor[2] != null) {
                                    log("c6.3");
                                    piece = armor[2];
                                    if (piece.hasItemMeta()) {
                                        log("c7.3");
                                        if (piece.getItemMeta().hasDisplayName()) {
                                            log("c8.3");
                                            if (piece.getItemMeta().getDisplayName().startsWith("§r§l§r")) {
                                                log("c9.3");
                                                TaskManager.runSyncTaskLater("Fake Armor Slot", () -> sendPacket(p, new PacketPlayOutSetSlot(0, 7, new net.minecraft.server.v1_8_R1.ItemStack(Blocks.STONE_BUTTON))), 2);
                                            }
                                        }
                                    }
                                }
                                if (armor[3] != null) {
                                    log("c6.4");
                                    piece = armor[3];
                                    if (piece.hasItemMeta()) {
                                        log("c7.4");
                                        if (piece.getItemMeta().hasDisplayName()) {
                                            log("c8.4");
                                            if (piece.getItemMeta().getDisplayName().startsWith("§r§l§r")) {
                                                log("c9.4");
                                                TaskManager.runSyncTaskLater("Fake Armor Slot", () -> sendPacket(p, new PacketPlayOutSetSlot(0, 8, new net.minecraft.server.v1_8_R1.ItemStack(Blocks.STONE_BUTTON))), 2);
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
    }*/

}

