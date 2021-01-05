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
import de.basicbit.system.minecraft.npc.NPCType;

@SuppressWarnings("deprecation")
public class Farmer extends Listener {

    @EventHandler
    public void onInteractAtNPC(PlayerNPCRightClickEvent e) {
        if (e.getNPC().getType().equals(NPCType.Farmer)) {
            openFarmer(e.getPlayer());
        }
    }

    public static void openFarmer(Player p) {

        Gui.open(p, new Gui("§e§lFarmer", 1) {

            @Override
            public void onInit(DynamicObject args) {
                setBackgroundInAllPagesToGlassColor(15);

                setItemAtWithCost(3, 1, 0, new ItemStack(106, 64, (short) 0), 96); // Ranken
                setItemAtWithCost(4, 1, 0, new ItemStack(296, 64, (short) 0), 64); // Weizen
                setItemAtWithCost(5, 1, 0, new ItemStack(81, 64, (short) 0), 64); // Kaktus
                setItemAtWithCost(3, 2, 0, new ItemStack(391, 64, (short) 0), 32); // Karotten
                setItemAtWithCost(4, 2, 0, new ItemStack(338, 64, (short) 0), 128); // Zuckerrohr
                setItemAtWithCost(5, 2, 0, new ItemStack(392, 64, (short) 0), 64); // Kartoffeln
                setItemAtWithCost(2, 2, 0, new ItemStack(360, 64, (short) 0), 48); // Melonen
                setItemAtWithCost(6, 2, 0, new ItemStack(86, 64, (short) 0), 256); // Kürbis
                setItemAtWithCost(3, 3, 0, new ItemStack(295, 64, (short) 0), 16); // Seeds
                setItemAtWithCost(4, 3, 0, new ItemStack(372, 64, (short) 0), 64); // Netherwartzen
                setItemAtWithCost(5, 3, 0, new ItemStack(351, 64, (short) 3), 96); // Kakaobohnen
            }

            public void setItemAtWithCost(int x, int y, int page, ItemStack is, int cost) {
                ItemMeta im = is.getItemMeta();
                ArrayList<String> lore = new ArrayList<String>();
                lore.add("§r");
                lore.add("§b§lLinksklick: §aVerkaufen");
                lore.add("§e" + (cost == 1 ? "Ein Coin" : cost + " Coins"));
                lore.add("§r");
                im.setLore(lore);
                is.setItemMeta(im);
                setItemAt(x, y, page, is);
            }

            @Override
            public DynamicObject onClick(DynamicObject args) {
                ItemStack is = args.getItemStack(Field.PLAYER_GUI_ITEM_CLICKED);

                if (is != null && is.getTypeId() != 160) {
                    String costAsString = is.getItemMeta().getLore().get(2).substring(2).split(" ")[0];
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

                    if (hasItemStack(p, is) && is.getAmount() == 64) {
                        removeItemStack(p, is);
                        addCoins(p, cost, " FarmerNpcAddCoins");
                        sendClickSound(p);
                        sendMessage(p, "§aVerkauf erfolgreich.");
                    } else {
                        sendErrorSound(p);
                        sendMessage(p, "§cDu hast nicht genügend Items zum verkaufen.");
                    }
                }

                return new DynamicObject();
            }

            @Override
            public void onClose(DynamicObject args) {
            }
        }, 0);
    }
}