package de.basicbit.system.minecraft.listeners.npc.guis;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.basicbit.system.database.UserData;
import de.basicbit.system.database.UserValue;
import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.dynamic.DynamicObject;
import de.basicbit.system.minecraft.dynamic.Field;
import de.basicbit.system.minecraft.events.PlayerNPCRightClickEvent;
import de.basicbit.system.minecraft.gui.Click;
import de.basicbit.system.minecraft.gui.Gui;
import de.basicbit.system.minecraft.npc.NPCType;

@SuppressWarnings("deprecation")
public class Bank extends Listener {

    @EventHandler
    public void onInteractAtNPC(PlayerNPCRightClickEvent e) {
        Player p = e.getPlayer();

        if (e.getNPC().getType().equals(NPCType.Bank)) {
            openBank(p);
        }
    }

    public static void openBank(Player p) {
        int level = UserData.getInt(p, UserValue.coinsBankInvest);
        int bonus = (int) Math.pow(2, level) * 50;
        int bonusNext = (int) Math.pow(2, level + 1) * 50;
        int cost = (int) Math.pow(4, level) * 50;

        Gui.open(p, new Gui(new String[] { "§5§lBankkaufmann" }, 1) {

            @Override
            public void onInit(DynamicObject args) {
                setBackgroundInAllPagesToGlassColor(15);

                ItemStack is = new ItemStack(388, 1);
                ItemMeta im = is.getItemMeta();
                im.setDisplayName("§a§lSpielzeitbonus erhöhen");
                ArrayList<String> lore = new ArrayList<String>();
                lore.add("§r");
                lore.add("§7Du erhältst zu jeder vollen Stunde auf dem");
                lore.add("§7Server einen Spielzeitbonus. Diesen kannst");
                lore.add("§7du hier erhöhen.");
                lore.add("§r");
                lore.add("§e§lSpielzeitbonus: §e" + bonus + " Coins pro Stunde");
                lore.add("§r");
                lore.add("§7--------------------------------------");
                lore.add("§a§lVerbesserung");
                lore.add("§r");
                lore.add("§9§lSpielzeitbonus: §9" + bonusNext + " Coins pro Stunde");
                lore.add("§c§lKosten: §c" + cost + " Coins");
                lore.add("§r");
                im.setLore(lore);
                is.setItemMeta(im);
                setItemAt(2, 2, is);

                is = new ItemStack(266, 1);
                im = is.getItemMeta();
                im.setDisplayName("§6§lGold einzahlen");
                lore = new ArrayList<String>();
                lore.add("§r");
                lore.add("§b§lLinksklick: §aEin Gold einzahlen");
                lore.add("§d§lShift + Rechtsklick: §aAll dein Gold einzahlen");
                lore.add("§r");
                im.setLore(lore);
                is.setItemMeta(im);
                setItemAt(4, 2, is);

                is = new ItemStack(386, 1);
                im = is.getItemMeta();
                im.setDisplayName("§6§lKonto verwalten");
                is.setItemMeta(im);
                setItemAt(6, 2, is);
            }

            @Override
            public DynamicObject onClick(DynamicObject args) {
                int slotX = args.getInt(Field.PLAYER_GUI_SLOT_POS_X);
                int slotY = args.getInt(Field.PLAYER_GUI_SLOT_POS_Y);
                int click = args.getInt(Field.PLAYER_GUI_CLICKTYPE);

                if (slotX == 2 && slotY == 2) {
                    if (hasCoins(p, cost)) {
                        removeCoins(p, cost, " BankNpcRemoveCoins");
                        UserData.set(p, UserValue.coinsBankInvest, level + 1);
                        sendClickSound(p);
                        sendMessage(p, "§aDein Spielzeitbonus beträgt nun " + bonusNext + " Coins pro Stunde.");
                        openBank(p);
                    } else {
                        sendErrorSound(p);
                        sendMessage(p, "§cDu hast nicht genug Coins.");
                    }
                } else if (slotX == 4 && slotY == 2) {
                    if (hasItem(p, 266)) {
                        if (click == Click.LEFT) {
                            removeItem(p, 266, 1);
                            addBankCoins(p, 500, " BankNpcDepositGold");
                            sendClickSound(p);
                            sendMessage(p, "§aDu hast erfolgreich §eein Gold §aeingezahlt.");
                        } else if (click == Click.RIGHT + Click.SHIFT) {
                            int goldCount = 0;
                            while (hasItem(p, 266)) {
                                removeItem(p, 266, 1);
                                addBankCoins(p, 500, " BankNpcDepositAllGold");
                                goldCount++;
                            }
                            sendClickSound(p);
                            sendMessage(p, "§aDu hast erfolgreich §e" + goldCount + " Gold §aeingezahlt.");
                        }
                    } else {
                        sendErrorSound(p);
                        sendMessage(p, "§cDu besitzt kein Gold zum einzahlen.");
                    }
                } else if (slotX == 6 && slotY == 2) {
                    Gui.open(p, new Gui(new String[] {
                        "§5§lBankkaufmann"
                    }, 1) {

                        @Override
                        public void onInit(DynamicObject args) {
                            ItemStack is = new ItemStack(339);
                            ItemMeta im = is.getItemMeta();
                            int coins =  UserData.getCoins(p);
                            int bankCoins = UserData.getBankCoins(p);

                            im.setDisplayName("§a1 Coin einzahlen");
                            ArrayList<String> lore = new ArrayList<String>();
                            lore.add("§r");
                            lore.add("§b§lLinksklick: §e1 Coin einzahlen");
                            lore.add("§d§lRechtsklick: §e10% deiner Coins einzahlen (§a" + (int)(coins * 0.1) + " §eCoins)");
                            lore.add("§r");
                            im.setLore(lore);
                            is.setItemMeta(im);
                            setItemAt(1, 1, is);

                            is = new ItemStack(339);
                            im = is.getItemMeta();
                            im.setDisplayName("§a10 coins einzahlen");
                            lore = new ArrayList<String>();
                            lore.add("§r");
                            lore.add("§b§lLinksklick: §e10 Coins einzahlen");
                            lore.add("§d§lRechtsklick: §e25% deiner Coins einzahlen (§a" + (int)(coins * 0.25) + " §eCoins)");
                            lore.add("§r");
                            im.setLore(lore);
                            is.setItemMeta(im);
                            setItemAt(2, 1, is);

                            is = new ItemStack(339);
                            im = is.getItemMeta();
                            im.setDisplayName("§a100 Coins einzahlen");
                            lore = new ArrayList<String>();
                            lore.add("§r");
                            lore.add("§b§lLinksklick: §e100 Coins einzahlen");
                            lore.add("§d§lRechtsklick: §e50% deiner Coins einzahlen (§a" + (int)(coins * 0.5) + " §eCoins)");
                            lore.add("§r");
                            im.setLore(lore);
                            is.setItemMeta(im);
                            setItemAt(3, 1, is);

                            is = new ItemStack(339);
                            im = is.getItemMeta();
                            im.setDisplayName("§a1000 Coins einzahlen");
                            lore = new ArrayList<String>();
                            lore.add("§r");
                            lore.add("§b§lLinksklick: §e1000 Coins einzahlen");
                            lore.add("§d§lRechtsklick: §e75% deiner Coins einzahlen (§a" + (int)(coins * 0.75) + " §eCoins)");
                            lore.add("§r");
                            im.setLore(lore);
                            is.setItemMeta(im);
                            setItemAt(4, 1, is);

                            is = new ItemStack(339);
                            im = is.getItemMeta();
                            im.setDisplayName("§a10000 Coins einzahlen");
                            lore = new ArrayList<String>();
                            lore.add("§r");
                            lore.add("§b§lLinksklick: §e10000 Coins einzahlen");
                            lore.add("§d§lRechtsklick: §e100% deiner Coins einzahlen (§a" + coins + " §eCoins)");
                            lore.add("§r");
                            im.setLore(lore);
                            is.setItemMeta(im);
                            setItemAt(5, 1, is);

                            is = new ItemStack(395);
                            im = is.getItemMeta();
                            im.setDisplayName("§c1 Coins abheben");
                            lore = new ArrayList<String>();
                            lore.add("§r");
                            lore.add("§b§lLinksklick: §e1 Coins abheben");
                            lore.add("§d§lRechtsklick: §e10% deiner Coins abheben (§a" + (int)(bankCoins * 0.1) + " §eCoins)");
                            lore.add("§r");
                            im.setLore(lore);
                            is.setItemMeta(im);
                            setItemAt(1, 3, is);

                            is = new ItemStack(395);
                            im = is.getItemMeta();
                            im.setDisplayName("§c10 Coins abheben");
                            lore = new ArrayList<String>();
                            lore.add("§r");
                            lore.add("§b§lLinksklick: §e10 Coins abheben");
                            lore.add("§d§lRechtsklick: §e25% deiner Coins abheben (§a" + (int)(bankCoins * 0.25) + " §eCoins)");
                            lore.add("§r");
                            im.setLore(lore);
                            is.setItemMeta(im);
                            setItemAt(2, 3, is);

                            is = new ItemStack(395);
                            im = is.getItemMeta();
                            im.setDisplayName("§c100 Coins abheben");
                            lore = new ArrayList<String>();
                            lore.add("§r");
                            lore.add("§b§lLinksklick: §e100 Coins abheben");
                            lore.add("§d§lRechtsklick: §e50% deiner Coins abheben (§a" + (int)(bankCoins * 0.5) + " §eCoins)");
                            lore.add("§r");
                            im.setLore(lore);
                            is.setItemMeta(im);
                            setItemAt(3, 3, is);

                            is = new ItemStack(395);
                            im = is.getItemMeta();
                            im.setDisplayName("§c1000 Coins abheben");
                            lore = new ArrayList<String>();
                            lore.add("§r");
                            lore.add("§b§lLinksklick: §e1000 Coins abheben");
                            lore.add("§d§lRechtsklick: §e75% deiner Coins abheben (§a" + (int)(bankCoins * 0.75) + " §eCoins)");
                            lore.add("§r");
                            im.setLore(lore);
                            is.setItemMeta(im);
                            setItemAt(4, 3, is);

                            is = new ItemStack(395);
                            im = is.getItemMeta();
                            im.setDisplayName("§c10000 Coins abheben");
                            lore = new ArrayList<String>();
                            lore.add("§r");
                            lore.add("§b§lLinksklick: §e10000 Coins abheben");
                            lore.add("§d§lRechtsklick: §e100% deiner Coins abheben (§a" + bankCoins + "§e Coins)");
                            lore.add("§r");
                            im.setLore(lore);
                            is.setItemMeta(im);
                            setItemAt(5, 3, is);

                            is = new ItemStack(166);
                            im = is.getItemMeta();
                            im.setDisplayName("§c§lZurück");
                            is.setItemMeta(im);
                            setItemAt(7, 2, is);
                        }

                        @Override
                        public DynamicObject onClick(DynamicObject args) {
                            int click = args.getInt(Field.PLAYER_GUI_CLICKTYPE);
                            int slotX = args.getInt(Field.PLAYER_GUI_SLOT_POS_X);
                            int slotY = args.getInt(Field.PLAYER_GUI_SLOT_POS_Y);
                            
                            if (click == Click.LEFT) {
                                if (slotX >= 1 && slotX <= 5 && slotY == 1) {
                                    int modifier = slotX - 1;
                                    int result = 1;
                                    for (int i = 0; i < modifier; i++) {
                                        result *= 10;
                                    }
                                    
                                    if (hasCoins(p, result)) {
                                        removeCoins(p, result, " BankNpcRemoveCoins");
                                        addBankCoins(p, result, " BankNpcAddCoins");
                                    } else if (hasCoins(p, 1)) {
                                        int coins = getCoins(p);
                                        removeCoins(p, coins, " BankNpcRemoveCoins");
                                        addBankCoins(p, coins, " BankNpcAddCoins");
                                    } else {
                                        sendMessage(p, "§cDu hast keine Coins, welche du einzahlen könntest.");
                                    }
                                }

                                if (slotX >= 1 && slotX <= 5 && slotY == 3) {
                                    int modifier = slotX - 1;
                                    int result = 1;
                                    for (int i = 0; i < modifier; i++) {
                                        result *= 10;
                                    }
                                    
                                    if (hasBankCoins(p, result)) {
                                        removeBankCoins(p, result, " BankNpcRemoveCoins");
                                        addCoins(p, result, " BankNpcAddCoins");
                                    } else if (hasBankCoins(p, 1)) {
                                        int coins = getBankCoins(p);
                                        removeBankCoins(p, coins, " BankNpcRemoveCoins");
                                        addCoins(p, coins, " BankNpcAddCoins");
                                    } else {
                                        sendMessage(p, "§cDu hast keine Coins, welche du abheben könntest.");
                                    }
                                }

                            } else if (click == Click.RIGHT) {
                                // ab hier einzahlen
                                if (slotX == 1 && slotY == 1) {
                                    int coins = getCoins(p) / 10;
                                    if (hasCoins(p, coins)) {
                                        removeCoins(p, coins, " BankNpcRemoveCoins");
                                        addBankCoins(p, coins, " BankNpcAddCoins");
                                    } else {
                                        sendMessage(p, "§cDu hast keine Coins, welche du einzahlen könntest.");
                                    }
                                } else if (slotX == 2 && slotY == 1) { // 25% Coins
                                    int coins = getCoins(p) / 4;
                                    if (hasCoins(p, coins)) {
                                        removeCoins(p, coins, " BankNpcRemoveCoins");
                                        addBankCoins(p, coins, " BankNpcAddCoins");
                                    } else {
                                        sendMessage(p, "§cDu hast keine Coins, welche du einzahlen könntest.");
                                    }
                                } else if (slotX == 3 && slotY == 1) { // 50% Coins
                                    int coins = getCoins(p) / 2;
                                    if (hasCoins(p, coins)) {
                                        removeCoins(p, coins, " BankNpcRemoveCoins");
                                        addBankCoins(p, coins, " BankNpcAddCoins");
                                    } else {
                                        sendMessage(p, "§cDu hast keine Coins, welche du einzahlen könntest.");
                                    }
                                } else if (slotX == 4 && slotY == 1) { // 75% Coins
                                    int coins = (int) (getCoins(p) * 0.75);
                                    if (hasCoins(p, coins)) {
                                        removeCoins(p, coins, " BankNpcRemoveCoins");
                                        addBankCoins(p, coins, " BankNpcAddCoins");
                                    } else {
                                        sendMessage(p, "§cDu hast keine Coins, welche du einzahlen könntest.");
                                    }
                                } else if (slotX == 5 && slotY == 1) { // 100% Coins
                                    int coins = getCoins(p);
                                    if (hasCoins(p, coins)) {
                                        removeCoins(p, coins, " BankNpcRemoveCoins");
                                        addBankCoins(p, coins, " BankNpcAddCoins");
                                    } else {
                                        sendMessage(p, "§cDu hast keine Coins, welche du einzahlen könntest.");
                                    }
                                }

                                // ab hier coins abheben

                                if (slotX == 1 && slotY == 3) { // 10% Coins
                                    int coins = getBankCoins(p) / 10;
                                    if (hasBankCoins(p, coins)) {
                                        removeBankCoins(p, coins, " BankNpcRemoveCoins");
                                        addCoins(p, coins, " BankNpcAddCoins");
                                    } else {
                                        sendMessage(p, "§cDu hast keine Coins, welche du abheben könntest.");
                                    }
                                } else if (slotX == 2 && slotY == 3) { // 25% Coins
                                    int coins = getBankCoins(p) / 4;
                                    if (hasBankCoins(p, coins)) {
                                        removeBankCoins(p, coins, " BankNpcRemoveCoins");
                                        addCoins(p, coins, " BankNpcAddCoins");
                                    } else {
                                        sendMessage(p, "§cDu hast keine Coins, welche du abheben könntest.");
                                    }
                                } else if (slotX == 3 && slotY == 3) { // 50% Coins
                                    int coins = getBankCoins(p) / 2;
                                    if (hasBankCoins(p, coins)) {
                                        removeBankCoins(p, coins, " BankNpcRemoveCoins");
                                        addCoins(p, coins, " BankNpcAddCoins");
                                    } else {
                                        sendMessage(p, "§cDu hast keine Coins, welche du abheben könntest.");
                                    }
                                } else if (slotX == 4 && slotY == 3) { // 75% Coins
                                    int coins = (int) (getBankCoins(p) * 0.75);
                                    if (hasBankCoins(p, coins)) {
                                        removeBankCoins(p, coins, " BankNpcRemoveCoins");
                                        addCoins(p, coins, " BankNpcAddCoins");
                                    } else {
                                        sendMessage(p, "§cDu hast keine Coins, welche du abheben könntest.");
                                    }
                                } else if (slotX == 5 && slotY == 3) { // 100% Coins
                                    int coins = getBankCoins(p);
                                    if (hasBankCoins(p, coins)) {
                                        removeBankCoins(p, coins, " BankNpcRemoveCoins");
                                        addCoins(p, coins, " BankNpcAddCoins");
                                    } else {
                                        sendMessage(p, "§cDu hast keine Coins, welche du abheben könntest.");
                                    }
                                }
                            }

                            if (slotX == 7 && slotY == 2) {
                                openBank(p);
                            }

                            return new DynamicObject();
                        }

                        @Override
                        public void onDownBarClick(DynamicObject args) {
                            
                        }

                        @Override
                        public void onClose(DynamicObject args) {
                            
                        }
                        
                    }, 0);
                }

                return new DynamicObject();
            }

            @Override
            public void onDownBarClick(DynamicObject args) {
            }

            @Override
            public void onClose(DynamicObject args) {
                
            }

        }, 0);
    }
}