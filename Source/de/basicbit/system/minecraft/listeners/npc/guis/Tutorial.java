package de.basicbit.system.minecraft.listeners.npc.guis;

import java.util.ArrayList;

import org.bukkit.Location;
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
import de.basicbit.system.minecraft.tasks.TaskManager;

@SuppressWarnings("deprecation")
public class Tutorial extends Listener {

    @EventHandler
    public void onInteractAtNPC(PlayerNPCRightClickEvent e) {
        NPC npc = e.getNPC();
        Player p = e.getPlayer();

        if (npc.getType().equals(NPCType.Tutorial)) {
            Gui.open(p, new Gui(new String[] { "§c§lTutorial" }, 1) {

                @Override
                public void onInit(DynamicObject args) {

                    setBackgroundToGlassColor(0, 15);

                    ItemStack is = new ItemStack(2, 1);
                    ItemMeta im = is.getItemMeta();
                    im.setDisplayName("§2§lSkyBlock");
                    ArrayList<String> lore = new ArrayList<String>();
                    lore.add("§7");
                    lore.add("§b§lKlicke §ezum teleportieren");
                    lore.add("§r");
                    im.setLore(lore);
                    is.setItemMeta(im);
                    setItemAt(4, 2, is);

                    is = new ItemStack(280, 1);
                    im = is.getItemMeta();
                    im.setDisplayName("§3§lKnock§b§lIt");
                    lore = new ArrayList<String>();
                    lore.add("§r");
                    lore.add("§b§lKlicke §ezum teleportieren");
                    lore.add("§r");
                    im.setLore(lore);
                    is.setItemMeta(im);
                    setItemAt(3, 0, is);

                    is = new ItemStack(267, 1);
                    im = is.getItemMeta();
                    im.setDisplayName("§c§l1vs1");
                    lore = new ArrayList<String>();
                    lore.add("§r");
                    lore.add("§cBald Verfügbar!");
                    // kein lore.add("§r"); weil schwert
                    im.setLore(lore);
                    is.setItemMeta(im);
                    setItemAt(5, 0, is);

                    is = new ItemStack(266, 1);
                    im = is.getItemMeta();
                    im.setDisplayName("§e§lBank");
                    lore = new ArrayList<String>();
                    lore.add("§r");
                    lore.add("§b§lKlicke §ezum teleportieren");
                    lore.add("§r");
                    im.setLore(lore);
                    is.setItemMeta(im);
                    setItemAt(7, 1, is);

                    is = new ItemStack(145, 1);
                    im = is.getItemMeta();
                    im.setDisplayName("§b§lSchmied");
                    lore = new ArrayList<String>();
                    lore.add("§r");
                    lore.add("§b§lKlicke §ezum teleportieren");
                    lore.add("§r");
                    im.setLore(lore);
                    is.setItemMeta(im);
                    setItemAt(1, 1, is);

                    is = new ItemStack(54, 1);
                    im = is.getItemMeta();
                    im.setDisplayName("§9§lMarkt");
                    lore = new ArrayList<String>();
                    lore.add("§r");
                    lore.add("§b§lKlicke §ezum teleportieren");
                    lore.add("§r");
                    im.setLore(lore);
                    is.setItemMeta(im);
                    setItemAt(1, 3, is);

                    is = new ItemStack(388, 1);
                    im = is.getItemMeta();
                    im.setDisplayName("§5§lVerkäufer");
                    lore = new ArrayList<String>();
                    lore.add("§r");
                    lore.add("§b§lKlicke §ezum teleportieren");
                    lore.add("§r");
                    im.setLore(lore);
                    is.setItemMeta(im);
                    setItemAt(3, 4, is);

                    is = new ItemStack(399, 1);
                    im = is.getItemMeta();
                    im.setDisplayName("§6§lTagesbonus");
                    lore = new ArrayList<String>();
                    lore.add("§r");
                    lore.add("§b§lKlicke §ezum teleportieren");
                    lore.add("§r");
                    im.setLore(lore);
                    is.setItemMeta(im);
                    setItemAt(5, 4, is);

                    is = new ItemStack(351, 1, (short) 7);
                    im = is.getItemMeta();
                    im.setDisplayName("§f§lMajo");
                    lore = new ArrayList<String>();
                    lore.add("§r");
                    lore.add("§b§lKlicke §ezum teleportieren");
                    lore.add("§r");
                    im.setLore(lore);
                    is.setItemMeta(im);
                    setItemAt(7, 3, is);
                }

                @Override
                public DynamicObject onClick(DynamicObject args) {
                    int slotX = args.getInt(Field.PLAYER_GUI_SLOT_POS_X);
                    int slotY = args.getInt(Field.PLAYER_GUI_SLOT_POS_Y);

                    if (slotX == 4 && slotY == 2) {/* Skyblock */

                        p.teleport(new Location(getSpawnWorld(), -29, 198, 22.5, 87.8f, 3.7f));
                        sendMessage(p, "§aHier geht es zu deiner persönlichen Welt.");

                        TaskManager.runAsyncTaskLater(new Runnable() {

                            @Override
                            public void run() {
                                sendMessage(p, "§aDu kannst dort mit Freunden bauen oder neue Inseln erkunden,");
                            }

                        }, 40);

                        TaskManager.runAsyncTaskLater(new Runnable() {

                            @Override
                            public void run() {
                                sendMessage(p, "§aauf denen verborgene Schätze liegen.");
                            }

                        }, 50);

                        TaskManager.runAsyncTaskLater(new Runnable() {

                            @Override
                            public void run() {
                                sendMessage(p, "§aMit \"§e§n/is\"§r §akannst du dein SkyBlock Menü öffnen.");
                                log("TODO: IChatBaseComponent \"/is\"");
                            }

                        }, 90);

                    } else if (slotX == 3 && slotY == 0) {/* KnockIt */

                        p.teleport(new Location(getSpawnWorld(), -28, 198, 24.0, 89.5f, 5.2f));
                        sendMessage(p,
                                "§aZiel bei diesem Spielmodus ist es, andere Spieler von der Plattform zu stoßen.");

                        TaskManager.runAsyncTaskLater(new Runnable() {

                            @Override
                            public void run() {
                                sendMessage(p,
                                        "§aDu bekommst Coins, wenn du jemanden tötest, solltest du sterben, verlierst du welche.");
                            }

                        }, 60);

                        TaskManager.runSyncTaskLater("tutorialTeleportKnockIt", new Runnable() {

                            @Override
                            public void run() {
                                if (!isInWorld(p, "KnockIt")) {
                                    p.teleport(new Location(getKnockItWorld(), 0.5, 46, 0.5, 0f, 0f));
                                }
                            }

                        }, 100);

                        TaskManager.runAsyncTaskLater(new Runnable() {

                            @Override
                            public void run() {
                                sendMessage(p,
                                        "§aÜber die Kiste in deinem Inventar kannst du dir verschiedene Verbesserungen kaufen.");
                            }

                        }, 120);

                        TaskManager.runAsyncTaskLater(new Runnable() {

                            @Override
                            public void run() {
                                if (isInWorld(p, "KnockIt")) {
                                    log("*open shop inv*");
                                }
                            }

                        }, 160);

                    } else if (slotX == 5 && slotY == 0) {/* 1vs1 */
                        // p.teleport(new Location(getSpawnWorld(), -19.207, 118, 14.437, 50.4f,
                        // 14.1f));
                    } else if (slotX == 7 && slotY == 1) {/* Bank */
                        p.teleport(new Location(getSpawnWorld(), -29.5, 198, 21.5, 113, 0));

                        sendMessage(p,
                                "§aHier kannst du dein Geld sichern, damit zu es zum Beispiel bei PvP-Kämpfen nicht verlierst.");

                        TaskManager.runAsyncTaskLater(new Runnable() {

                            @Override
                            public void run() {
                                sendMessage(p,
                                        "§aDu bekommst jede Spielstunde einen Coin-Bonus, den du mit Coins upgraden kannst.");
                            }

                        }, 60);

                        TaskManager.runAsyncTaskLater(new Runnable() {

                            @Override
                            public void run() {
                                sendMessage(p, "§aDu kannst hier außerdem dein Gold gegen 500 Coins eintauschen.");
                            }

                        }, 120);

                    } else if (slotX == 1 && slotY == 1) {/* Schmied */
                        p.teleport(new Location(getSpawnWorld(), -58.5, 198, 26.5, 135.5f, -1.2f));
                        sendMessage(p, "§aHier kannst du deine Items reparieren, auch wenn es im Amboss nicht mehr geht.");

                        TaskManager.runAsyncTaskLater(new Runnable() {

                            @Override
                            public void run() {
                                sendMessage(p,
                                        "§aDafür musst du mit §6Coins §aund §9Superlapis §abezahlen");
                            }

                        }, 60);

                        TaskManager.runAsyncTaskLater(new Runnable() {

                            @Override
                            public void run() {
                                sendMessage(p, "§aAber mit jeder Reparatur steigt der Preis!");
                            }

                        }, 120);
                    } else if (slotX == 1 && slotY == 3) {/* Markt */

                        p.teleport(new Location(getSpawnWorld(), -22.5, 198, 34.5, -89.6f, -0.6f));

                        sendMessage(p, "§aHier kannst du drei Items im Markt anbieten.");

                        TaskManager.runAsyncTaskLater(new Runnable() {

                            @Override
                            public void run() {
                                sendMessage(p,
                                        "§aJeder kann deine Items kaufen und du kannst Items von anderen kaufen.");
                            }

                        }, 60);

                        TaskManager.runAsyncTaskLater(new Runnable() {

                            @Override
                            public void run() {
                                sendMessage(p, "§aAchte aber darauf, dass du den Preis nicht zu hoch einstellst!");
                            }

                        }, 120);

                        TaskManager.runAsyncTaskLater(new Runnable() {

                            @Override
                            public void run() {
                                sendMessage(p,
                                        "§aDas Preissystem funktioniert so: ganz rechts ist die 'Einer' Stelle, links daneben die 'Zehner'.");
                            }

                        }, 180);

                        TaskManager.runAsyncTaskLater(new Runnable() {

                            @Override
                            public void run() {
                                sendMessage(p, "§aAlso ist 0-0-0-7-2-5-0 entspricht 7250 Coins.");
                            }

                        }, 240);
                    } else if (slotX == 3 && slotY == 4) {/* Verkäufer */
                        p.teleport(new Location(getSpawnWorld(), -22.5, 198, 34.5, -46.8f, -1.8f));

                        sendMessage(p, "§aBeim Verkäufer kannst du die verschiedensten Items in Massen zu kaufen.");

                        TaskManager.runAsyncTaskLater(new Runnable() {

                            @Override
                            public void run() {
                                sendMessage(p, "§aAllerdings könnte der Preis höher sein, als im Markt!");
                            }

                        }, 60);
                    } else if (slotX == 5 && slotY == 4) {/* Tagesbonus */
                        p.teleport(new Location(getSpawnWorld(), -34, 203, 39, -89.4f, 3.3f));

                        sendMessage(p, "§aIch habe jeden Tag eine tolle Überraschung für dich :D");
                    } else if (slotX == 7 && slotY == 3) {/* Majo */
                        p.teleport(new Location(getSpawnWorld(), -36, 198, 16.5, 86.6f, -0.9f));

                        sendMessage(p, "§aHier kannst du Majo kaufen.");

                        TaskManager.runAsyncTaskLater(new Runnable() {

                            @Override
                            public void run() {
                                sendMessage(p, "§aMajo ist zwar etwas teurer als Steaks, doch sie hat viele Vorteile.");
                            }

                        }, 60);

                        TaskManager.runAsyncTaskLater(new Runnable() {

                            @Override
                            public void run() {
                                sendMessage(p, "§aDafür sättigt dich Majo aber auch KOMPLETT!");
                            }

                        }, 120);
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
}