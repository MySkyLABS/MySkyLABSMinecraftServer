package de.basicbit.system.minecraft.usershop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.basicbit.system.minecraft.Utils;
import de.basicbit.system.minecraft.dynamic.DynamicObject;
import de.basicbit.system.minecraft.dynamic.Field;
import de.basicbit.system.minecraft.dynamic.FieldAndObject;
import de.basicbit.system.minecraft.dynamic.RunnableWithArgsAndResult;
import de.basicbit.system.minecraft.gui.Click;
import de.basicbit.system.minecraft.gui.Gui;
import de.basicbit.system.minecraft.tasks.TaskManager;

@SuppressWarnings("deprecation")
public class UserShop extends Utils {
    
    public static void init() {
        UserShopOffers.init();
    }

    public static void close() {
        UserShopOffers.close();
    }

    public static void openUserShop(Player p) {
        ArrayList<UserShopItem> shopItems = new ArrayList<UserShopItem>();
        for (UserShopItem item : UserShopOffers.getOffers()) {
            ItemStack is = item.getItemStack();

            if (is.getTypeId() != 0 && item.getCost() != Integer.MAX_VALUE) {
                shopItems.add(item);
            }
        }

        Collections.sort(shopItems, Comparator.comparing((UserShopItem is) -> (9999999 - is.getCost())));

        int shopItemCount = shopItems.size();

        int pages = shopItemCount % 45 == 0 ? shopItemCount / 45 : shopItemCount / 45 + 1;
        if (pages == 0) {
            pages = 1;
        }

        final int pagesAsArgument = pages;

        String title = "§9§lMarkt";
        String[] titles = new String[pages];
        for (int i = 0; i < pages; i++) {
            titles[i] = title;
        }

        Gui.open(p, new Gui(titles, pages) {

            @Override
            public void onInit(DynamicObject args) {
                setBackgroundInAllPagesToGlassColor(14);

                for (int page = 0; page < pagesAsArgument; page++) {
                    for (int i = 0; i < 45; i++) {
                        int slot = page * 45 + i;
                        if (slot < shopItemCount) {
                            UserShopItem item = shopItems.get(slot);
                            ItemStack is = item.getItemStack();
                            ItemMeta im = is.getItemMeta();
                            List<String> lore = im.getLore();
                            if (lore == null) {
                                lore = new ArrayList<String>();
                            }

                            int cost = item.getCost();
                            lore.add("§r");
                            lore.add("§6§lPreis: §e" + (cost == 1 ? "Ein Coin" : cost + " Coins"));
                            lore.add("§3§lVerkäufer: §7" + item.getChatName());
                            lore.add("§r");
                            lore.add("§b§lLinksklick: §aKaufen");

                            if (isModerator(p)) {
                                lore.add("§d§lShift + Rechtsklick: §aEntfernen");
                            }

                            lore.add("§r");
                            im.setLore(lore);
                            is.setItemMeta(im);
                            setItemAt(slot, is);
                        } else {
                            break;
                        }
                    }
                }

                setDownBarItemAt(4, new ItemStack(388), "§a§lEigene Items verwalten");
            }

            @Override
            public DynamicObject onClick(DynamicObject args) {
                int page = args.getInt(Field.PLAYER_GUI_PAGE);
                int slot = args.getInt(Field.PLAYER_GUI_SLOT_POS);
                int offerSlot = page * 45 + slot;
                int click = args.getInt(Field.PLAYER_GUI_CLICKTYPE);

                if (offerSlot >= shopItems.size()) {
                    return new DynamicObject();
                }

                UserShopItem item = shopItems.get(offerSlot);
                
                if (click == Click.LEFT) {
                    int cost = item.getCost();

                    if (hasCoins(p, cost)) {
                        boolean failed = !UserShopOffers.removeOffer(item.getOwnerId(), item.getSlot());
    
                        if (failed) {
                            sendMessage(p, "§cDieses Item befindet sich nicht mehr im Markt.");
                            openUserShop(p);
                            return new DynamicObject();
                        }
    
                        sendMessage(p, "§aKauf erfolgreich.");
                        giveItemToPlayer(p, item.getItemStack());
                        openUserShop(p);
                        removeCoins(p, cost, " UserShopRemoveCoins");
    
                        UUID id = item.getOwnerId();
                        Player t = getPlayer(id);
    
                        if (t != null) {
                            sendMessage(t, getChatName(p) + "§a hat ein Item von dir gekauft.");
                        }
    
                        TaskManager.runAsyncTask(new Runnable() {
                        
                            @Override
                            public void run() {
                                addCoins(item.getOwnerId(), cost, " UserShopAddCoins");
                            }
                        });
    
                        sendSuccessSound(p);
                    } else {
                        sendMessage(p, "§cDu hast nicht genug Coins.");
                        sendErrorSound(p);
                    }
                } else if (click == Click.RIGHT + Click.SHIFT && isModerator(p)) {
                    item.setCost(Integer.MAX_VALUE);
                    openUserShop(p);
                }

                return new DynamicObject();
            }

            @Override
            public void onDownBarClick(DynamicObject args) {
                int slot = args.getInt(Field.PLAYER_GUI_SLOT_POS);

                if (slot == 4) {
                    manageItems(p, null);
                    return;
                }
            }

            @Override
            public void onClose(DynamicObject args) {
                Player p = args.getPlayer(Field.PLAYER_MAIN);
                InventoryView view = p.getOpenInventory();
                
                if (view.getType() == InventoryType.CRAFTING) {
                    onClosed(p);
                }
            }
            
        }, 0);
    }

    public static void onClosed(Player p) {
        UserShopItem item0 = UserShopOffers.getOffer(p, 0);
        UserShopItem item1 = UserShopOffers.getOffer(p, 1);
        UserShopItem item2 = UserShopOffers.getOffer(p, 2);

        if ((item0 != null && item0.getItemStack().getTypeId() != 0 && item0.getCost() == Integer.MAX_VALUE) ||
            (item1 != null && item1.getItemStack().getTypeId() != 0 && item1.getCost() == Integer.MAX_VALUE) ||
            (item2 != null && item2.getItemStack().getTypeId() != 0 && item2.getCost() == Integer.MAX_VALUE)) {
            sendMessage(p, "§cDu hast unverkäufliche Items im Markt.");
        }
    }

    public static void manageItems(Player p, ItemStack cursor) {
        Gui.open(p, new Gui(new String[] { "§2§lEigene Items verwalten" }, 1) {

            @Override
            public void onInit(DynamicObject args) {
                UserShopItem item = UserShopOffers.getOffer(p, 0);
                ItemStack is = null;

                if (item != null) {
                    is = item.getItemStack().clone();
                    int cost = item.getCost();

                    if (is != null && is.getTypeId() != 0) {
                        ItemMeta im = is.getItemMeta();
                        List<String> lore = im.getLore();
                        if (lore == null) {
                            lore = new ArrayList<String>();
                        }
    
                        lore.add("§r");
                        lore.add("§b§lLinksklick: §aNehmen");
                        lore.add("§d§lRechtsklick: §aPreis anpassen");
                        lore.add("§r");
                        lore.add("§6§lPreis: §e" + (cost == 1 ? "Ein Coin" : (cost == Integer.MAX_VALUE ? "nicht verkäuflich" : cost + " Coins")));
                        lore.add("§r");
    
                        im.setLore(lore);
                        is.setItemMeta(im);
                    }
                }

                if (is == null || is.getTypeId() == 0) {
                    setItemAt(1, 2, new ItemStack(160, 1, (short)14), "§r");
                } else {
                    setItemAt(1, 2, is);
                }

                item = UserShopOffers.getOffer(p, 1);
                is = null;

                if (item != null) {
                    is = item.getItemStack().clone();
                    int cost = item.getCost();

                    if (is != null && is.getTypeId() != 0) {
                        ItemMeta im = is.getItemMeta();
                        List<String> lore = im.getLore();
                        if (lore == null) {
                            lore = new ArrayList<String>();
                        }
    
                        lore.add("§r");
                        lore.add("§b§lLinksklick: §aNehmen");
                        lore.add("§d§lRechtsklick: §aPreis anpassen");
                        lore.add("§r");
                        lore.add("§6§lPreis: §e" + (cost == 1 ? "Ein Coin" : (cost == Integer.MAX_VALUE ? "nicht verkäuflich" : cost + " Coins")));
                        lore.add("§r");
    
                        im.setLore(lore);
                        is.setItemMeta(im);
                    }
                }
                
                if (is == null || is.getTypeId() == 0) {
                    setItemAt(3, 2, new ItemStack(160, 1, (short)14), "§r");
                } else {
                    setItemAt(3, 2, is);
                }

                item = UserShopOffers.getOffer(p, 2);
                is = null;

                if (item != null) {
                    is = item.getItemStack().clone();
                    int cost = item.getCost();

                    if (is != null && is.getTypeId() != 0) {
                        ItemMeta im = is.getItemMeta();
                        List<String> lore = im.getLore();
                        if (lore == null) {
                            lore = new ArrayList<String>();
                        }
    
                        lore.add("§r");
                        lore.add("§b§lLinksklick: §aNehmen");
                        lore.add("§d§lRechtsklick: §aPreis anpassen");
                        lore.add("§r");
                        lore.add("§6§lPreis: §e" + (cost == 1 ? "Ein Coin" : (cost == Integer.MAX_VALUE ? "nicht verkäuflich" : cost + " Coins")));
                        lore.add("§r");
    
                        im.setLore(lore);
                        is.setItemMeta(im);
                    }
                }
                
                if (is == null || is.getTypeId() == 0) {
                    setItemAt(5, 2, new ItemStack(160, 1, (short)14), "§r");
                } else {
                    setItemAt(5, 2, is);
                }

                setItemAt(7, 2, new ItemStack(166), "§c§lZurück");
            }

            @Override
            public void onClose(DynamicObject args) {
                Player p = args.getPlayer(Field.PLAYER_MAIN);
                InventoryView view = p.getOpenInventory();
                
                if (view.getType() == InventoryType.CRAFTING) {
                    onClosed(p);
                }
            }

            @Override
            public DynamicObject onClick(DynamicObject args) {
                int slotX = args.getInt(Field.PLAYER_GUI_SLOT_POS_X);
                int slotY = args.getInt(Field.PLAYER_GUI_SLOT_POS_Y);
                ItemStack cur = args.getItemStack(Field.PLAYER_GUI_ITEM_CURSOR);
                int click = args.getInt(Field.PLAYER_GUI_CLICKTYPE);
                int slot = (slotX + 1) / 2 - 1;

                if (slotX == 7 && slotY == 2) {
                    openUserShop(p);
                }

                if ((slotX - 1) % 2 == 0 && slotX != 7 && slotY == 2) {
                    UserShopItem item = UserShopOffers.getOffer(p, slot);
                    if (click == Click.LEFT) {
                        ItemStack is = item != null ? item.getItemStack() : null;

                        if (is != null && is.getTypeId() == 160 && is.getDurability() == 14 && is.hasItemMeta()) {
                            ItemMeta im = is.getItemMeta();
                            if (im.hasDisplayName() && im.getDisplayName().contentEquals("§r")) {
                                is = null;
                            }
                        }
    
                        if (cur != null) {
                            UserShopOffers.setOffer(new UserShopItem(cur, p.getUniqueId(), getGroup(p), p.getName(), Integer.MAX_VALUE, slot, getGender(p)));
                        } else {
                            UserShopOffers.removeOffer(p, slot);
                        }
    
                        p.getOpenInventory().setCursor(null);
                        manageItems(p, is);
                        return new DynamicObject();
                    } else if (click == Click.RIGHT) {
                        ItemStack is = args.getItemStack(Field.PLAYER_GUI_ITEM_CLICKED);
                        if (is.getTypeId() == 160 && is.getDurability() == 14 && is.hasItemMeta()) {
                            ItemMeta im = is.getItemMeta();
                            if (im.hasDisplayName() && im.getDisplayName().contentEquals("§r")) {
                                return new DynamicObject();
                            }
                        }

                        Gui.openSelectNumber(p, "§eWähle dein Preis...", item.getCost(), new RunnableWithArgsAndResult() {
                        
                            @Override
                            public DynamicObject run(DynamicObject args) {
                                manageItems(p, null);
                                return new DynamicObject();
                            }
                        }, new RunnableWithArgsAndResult() {
                        
                            @Override
                            public DynamicObject run(DynamicObject args) {
                                int cost = args.getInt(Field.PLAYER_GUI_NUMBER);

                                if (cost != 0) {
                                    item.setCost(cost);
                                    manageItems(p, null);
                                }

                                return new DynamicObject(new FieldAndObject(Field.CANCEL, true));
                            }
                        });
                    }
                }
                
                return new DynamicObject();
            }

            @Override
            public void onDownBarClick(DynamicObject args) {

            }

            @Override
            public void onOpened(DynamicObject args) {
                InventoryView inv = p.getOpenInventory();
                inv.setCursor(cursor);
            }

        }, 0);
    }
}