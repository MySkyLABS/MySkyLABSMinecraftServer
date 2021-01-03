package de.basicbit.system.minecraft.listeners.npc.guis;

import java.util.ArrayList;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.crafting.recipes.SuperLapis;
import de.basicbit.system.minecraft.dynamic.DynamicObject;
import de.basicbit.system.minecraft.dynamic.Field;
import de.basicbit.system.minecraft.events.PlayerNPCRightClickEvent;
import de.basicbit.system.minecraft.gui.Gui;
import de.basicbit.system.minecraft.gui.GuiHolder;
import de.basicbit.system.minecraft.npc.NPCType;

@SuppressWarnings("deprecation")
public class Smith extends Listener {

    @EventHandler
    public void onInteractAtNPC(PlayerNPCRightClickEvent e) {
        Player p = e.getPlayer();

        if (e.getNPC().getType().equals(NPCType.Smith)) {
            openSmith(p);
        }
    }

    public static void openSmith(Player p) {
        ItemStack is = p.getItemInHand();
        ItemMeta im = is != null && is.hasItemMeta() ? is.getItemMeta() : null;
        String name = im != null && im.hasDisplayName() ? im.getDisplayName() : null;
        int id = is == null || is.getDurability() == 0 ? -1 : is.getTypeId();
        
        ArrayList<Integer> itemIds = new ArrayList<Integer>();
        itemIds.add(256);
        itemIds.add(257);
        itemIds.add(258);
        itemIds.add(259);
        itemIds.add(269);
        itemIds.add(270);
        itemIds.add(271);
        itemIds.add(273);
        itemIds.add(274);
        itemIds.add(275);
        itemIds.add(277);
        itemIds.add(278);
        itemIds.add(279);
        itemIds.add(284);
        itemIds.add(285);
        itemIds.add(286);
        itemIds.add(290);
        itemIds.add(291);
        itemIds.add(292);
        itemIds.add(293);
        itemIds.add(294);
        itemIds.add(346);
        itemIds.add(359);
        itemIds.add(261);
        itemIds.add(267);
        itemIds.add(268);
        itemIds.add(272);
        itemIds.add(276);
        itemIds.add(283);
        itemIds.add(298);
        itemIds.add(299);
        itemIds.add(300);
        itemIds.add(301);
        itemIds.add(302);
        itemIds.add(303);
        itemIds.add(304);
        itemIds.add(305);
        itemIds.add(306);
        itemIds.add(307);
        itemIds.add(308);
        itemIds.add(309);
        itemIds.add(310);
        itemIds.add(311);
        itemIds.add(312);
        itemIds.add(313);
        itemIds.add(314);
        itemIds.add(315);
        itemIds.add(316);
        itemIds.add(317);

        int coins = is.getDurability() + (name != null && name.startsWith("§r") ? 500 : 0);

        for (int level : is.getEnchantments().values()) {
            coins += level * 100;
        }

        final int cost = coins;
        int lapis = cost / 300;

        Gui.open(p, new Gui(new String[] { "§0§lSchmied" }, 1) {

            @Override
            public void onInit(DynamicObject args) {
                setBackgroundInAllPagesToGlassColor(15);

                ItemStack is;
                ItemMeta im;

                if (!itemIds.contains(id)) {
                    is = new ItemStack(166, 1);
                    im = is.getItemMeta();
                    im.setDisplayName("§cDu hast kein gültiges Item in der Hand.");
                    is.setItemMeta(im);
                    setItemAt(4, 2, is);
                } else {
                    is = new ItemStack(145, 1);
                    im = is.getItemMeta();
                    im.setDisplayName("§a§lItem in Hand reparieren!");
                    ArrayList<String> lore = new ArrayList<String>();
                    lore.add("§r");
                    lore.add("§7Reparierkosten betragen §9" + lapis + " SuperLapis§7 und §e" + cost + " Coins§7!");
                    lore.add("§r");
                    im.setLore(lore);
                    is.setItemMeta(im);
                    setItemAt(4, 2, is);
                }

                is = new ItemStack(166, 1);
                im = is.getItemMeta();
                im.setDisplayName("§c§lBald verfügbar!");
                is.setItemMeta(im);
                setItemAt(2, 2, is);

                is = new ItemStack(166, 1);
                im = is.getItemMeta();
                im.setDisplayName("§c§lBald verfügbar!");
                is.setItemMeta(im);
                setItemAt(6, 2, is);
            }

            @Override
            public DynamicObject onClick(DynamicObject args) {
                int x = args.getInt(Field.PLAYER_GUI_SLOT_POS_X);
                int y = args.getInt(Field.PLAYER_GUI_SLOT_POS_Y);

                if (x == 4 && y == 2 && itemIds.contains(id)) {
                    if (hasCoins(p, cost)) {
                        ItemStack is = SuperLapis.get(lapis);
                        String name = is.getItemMeta().getDisplayName();
                        if (hasItemStacksWithName(p, name, lapis)) {
                            removeItemStacksWithName(p, name, lapis);
                            removeCoins(p, cost, " SmithRemoveCoins");
                            sendSuccessSound(p);

                            p.getItemInHand().setDurability((short) 0);

                            sendMessage(p, "§aDein Item wurde repariert!");
                        } else {
                            p.closeInventory();
                            sendErrorSound(p);
                            sendMessage(p, "§cDu hast nicht genug SuperLapis.");
                        }
                    } else {
                        p.closeInventory();
                        sendErrorSound(p);
                        sendMessage(p, "§cDu hast nicht genug Coins.");
                    }
                }
                
                return new DynamicObject();
            }
            
        }, 0);
    }

    @EventHandler
    public static void onInventoryClick(InventoryClickEvent e) {
        HumanEntity h = e.getWhoClicked();

        if (h instanceof Player) {
            Player p = (Player) h;

            InventoryView inv = p.getOpenInventory();
            Inventory topInv = inv.getTopInventory();
            InventoryHolder invHolder = topInv.getHolder();

            if (invHolder instanceof GuiHolder) {
                GuiHolder guiHolder = (GuiHolder) invHolder;
                Gui gui = guiHolder.getGui();

                if (gui.getTitle(0).equalsIgnoreCase("§0§lSchmied")) {
                    Inventory clickedInv = e.getClickedInventory();
                    
                    if (clickedInv != null) {
                        invHolder = clickedInv.getHolder();

                        if (invHolder instanceof Player) {
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
}