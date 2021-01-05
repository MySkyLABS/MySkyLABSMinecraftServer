package de.basicbit.system.minecraft.listeners.npc.guis;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.dynamic.DynamicObject;
import de.basicbit.system.minecraft.dynamic.Field;
import de.basicbit.system.minecraft.events.PlayerNPCRightClickEvent;
import de.basicbit.system.minecraft.gui.Gui;
import de.basicbit.system.minecraft.npc.NPC;
import de.basicbit.system.minecraft.npc.NPCType;

@SuppressWarnings("deprecation")
public class CandyShop extends Listener {

    @EventHandler
    public void onInteractAtNPC(PlayerNPCRightClickEvent e) {
        NPC npc = e.getNPC();
        Player p = e.getPlayer();

        if (npc.getType().equals(NPCType.CandyShop)) {
            Gui.open(p, new Gui("§d§lCandyshop", 1) {

                @Override
                public void onInit(DynamicObject args) {

                    setBackgroundInAllPagesToGlassColor(15);

                    setItemAtWithCost(3, 1, 0, getCakeItemStack(1), 50); 
                    setItemAtWithCost(5, 1, 0, getCupCakeItemStack(1), 15); 
                    setItemAtWithCost(2, 2, 0, getDonutItemStack(1), 25); 
                    setItemAtWithCost(3, 2, 0, getCookieItemStack(1), 100); 
                    setItemAtWithCost(4, 2, 0, getPancakeItemStack(1), 50); 
                    setItemAtWithCost(5, 2, 0, getKidSurpriseItemStack(1), 150); 
                    setItemAtWithCost(6, 2, 0, getHoneyItemStack(1), 75); 
                    setItemAtWithCost(3, 3, 0, getPopcornItemStack(1), 25); 
                    setItemAtWithCost(4, 3, 0, getMushroomItemStack(1), 25); 
                    setItemAtWithCost(5, 3, 0, getColaItemStack(1), 100); 
                    //setItemAtWithCost(7, 2, 0, getChocolateItemStack(1), cost);
                }

                public void setItemAtWithCost(int x, int y, int page, ItemStack is, int cost) {
                    cost *= is.getAmount();
                    ItemMeta im = is.getItemMeta();
                    List<String> lore = im.hasLore() ? im.getLore() : new ArrayList<String>();
                    lore.add("§e" + (cost == 1 ? "Ein Coin" : cost + " Coins"));
                    lore.add("§r");
                    im.setLore(lore);
                    is.setItemMeta(im);
                    setItemAt(x, y, page, is);
                }

                @Override
                public DynamicObject onClick(DynamicObject args) {
                    ItemStack is = args.getItemStack(Field.PLAYER_GUI_ITEM_CLICKED);

                    // Überprüfe die ID und wenn die ID nicht von der Glassscheibe ist, (dann kaufe
                    // es)
                    if (is != null && is.getTypeId() != 160) {
                        ItemMeta im = is.getItemMeta();
                        List<String> lore = im.getLore();
                        
                        String costAsString = lore.get(3).substring(2).split(" ")[0];
                        int cost = 0;

                        if (costAsString.contentEquals("Ein")) {
                            cost = 1;
                        } else {
                            cost = Integer.parseInt(costAsString);
                        }

                        is = is.clone();
                        lore.remove(4);
                        lore.remove(3);
                        im.setLore(lore);
                        is.setItemMeta(im);

                        if (getCoins(p) < cost) {
                            sendMessage(p, "§cDu hast nicht genug Coins.");
                        } else {
                            giveItemToPlayer(p, is);
                            removeCoins(p, cost, " CandyShopRemoveCoins");

                            sendMessage(p, "§aKauf erfolgreich!");
                        }
                    }

                    return new DynamicObject();
                }

                @Override
                public void onClose(DynamicObject args) {
                }

                @Override
                public void onDownBarClick(DynamicObject args) {
                }
            }, 0);
        }
    }
}