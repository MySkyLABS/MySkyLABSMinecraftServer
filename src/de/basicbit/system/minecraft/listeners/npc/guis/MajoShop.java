package de.basicbit.system.minecraft.listeners.npc.guis;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.dynamic.DynamicObject;
import de.basicbit.system.minecraft.dynamic.Field;
import de.basicbit.system.minecraft.events.PlayerNPCRightClickEvent;
import de.basicbit.system.minecraft.gui.Gui;
import de.basicbit.system.minecraft.listeners.player.Majo;
import de.basicbit.system.minecraft.npc.NPC;
import de.basicbit.system.minecraft.npc.NPCType;

@SuppressWarnings("deprecation")
public class MajoShop extends Listener {

    @EventHandler
    public void onInteractAtNPC(PlayerNPCRightClickEvent e) {
        NPC npc = e.getNPC();
        Player p = e.getPlayer();

        int cost = 4;

        if (npc.getType().equals(NPCType.MajoShop)) {
            Gui.open(p, new Gui(new String[] {
                "§d§lMajoverkauf"
            }, 1) {

                @Override
                public void onInit(DynamicObject args) {
                    setBackgroundInAllPagesToGlassColor(15);

                    for (int x = 0; x < 7; x++) {
                        setItemAtWithCost(x + 1, 2, Majo.getMajoItemStack((int) Math.pow(2, x)));
                    }
                }

                @Override
                public DynamicObject onClick(DynamicObject args) {
                    ItemStack is = args.getItemStack(Field.PLAYER_GUI_ITEM_CLICKED);

                    if (is != null && is.getTypeId() != 160) {
                        String costAsString = is.getItemMeta().getLore().get(1).substring(2).split(" ")[0];
                        int cost = 0;

                        if (costAsString.contentEquals("Ein")) {
                            cost = 1;
                        } else {
                            cost = Integer.parseInt(costAsString);
                        }

                        is = is.clone();
                        ItemMeta im = is.getItemMeta();
                        im.setLore(new ArrayList<String>());
                        is.setItemMeta(im);

                        if (getCoins(p) < cost) {
                            sendMessage(p, "§cDu hast nicht genug Coins.");
                        } else {
                            giveItemToPlayer(p, is);
                            removeCoins(p, cost, " MajoShopRemoveCoins");

                            sendMessage(p, "§aKauf erfolgreich!");
                        }
                    }

                    return new DynamicObject();
                }

                @Override
                public void onDownBarClick(DynamicObject args) {
                    
                }

                @Override
                public void onClose(DynamicObject args) {
                    
                }

                public void setItemAtWithCost(int x, int y, ItemStack is) {
                    int amountCost = cost * is.getAmount();
                    ItemMeta im = is.getItemMeta();
                    ArrayList<String> lore = new ArrayList<String>();
                    lore.add("§r");
                    lore.add("§e" + (amountCost == 1 ? "Ein Coin" : amountCost + " Coins"));
                    lore.add("§r");
                    im.setLore(lore);
                    is.setItemMeta(im);
                    setItemAt(x, y, is);
                }
                
            }, 0);
        }
    }
}