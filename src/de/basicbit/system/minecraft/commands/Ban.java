package de.basicbit.system.minecraft.commands;

import java.time.Instant;
import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.basicbit.system.database.UserData;
import de.basicbit.system.database.UserValue;
import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;
import de.basicbit.system.minecraft.dynamic.DynamicObject;
import de.basicbit.system.minecraft.dynamic.Field;
import de.basicbit.system.minecraft.gui.Gui;
import de.basicbit.system.minecraft.tasks.TaskManager;

@SuppressWarnings("deprecation")
public class Ban extends Command {

    public static String banMessage = "§cDu wurdest von unserem Server ausgeschlossen.\n§cGrund: §e{0}\n\n§cAblauf der Ausschließung: §e{1}";

    @Override
    public String getUsage(Player p) {
        return "<Spieler>";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("ban");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isSupporter(p);
    }

    public long getUnixTimeFromTimeSpan(int days, int hours, int minutes, int seconds) {
        return Instant.now().getEpochSecond() + ((long)seconds) + (minutes * 60l) + (hours * 3600l) + (days * 86400l);
    }

    public static String getStringFromUnixTime(long time) {
        time -= Instant.now().getEpochSecond();

        long days = time / 86400l;
        time = time % 86400l;

        long hours = time / 3600l;
        time = time % 3600l;

        long minutes = time / 60l;
        time = time % 60l;

        return days + " Tage, " + hours + " Stunden, " + minutes + " Minuten, " + time + " Sekunden";
    }

    public static String getBanMessage(String reason, long time) {
        return banMessage.replace("{0}", reason).replace("{1}", time == Long.MAX_VALUE ? "Nie" : getStringFromUnixTime(time));
    }

    @Override
    public String getDescription(Player p) {
        return "Schließt andere Spieler vom Server aus.";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 1) {
            Player t = getPlayer(args[0]);

            if (t == null) {
                return CommandResult.PlayerNotOnline;                
            }
            // test

            /*
            if (getPlayer(args[0]) != null) {
                t = getPlayer(args[0]);
            } else {
                t = Bukkit.getServer().getOfflinePlayer(UserData.getUUIDFromName(args[0])).getPlayer();
            }*/

            if (isInTeam(t)) {
                sendMessage(p, "§cDu darfst diesen Spieler nicht bannen.");
                return CommandResult.None;
            }

            Gui.open(p, new Gui(new String[] { "§cWähle einen Banngrund..." }, 1) {

                @Override
                public void onInit(DynamicObject args) {
                    ItemStack is = new ItemStack(160, 1, (short)15);
                    ItemMeta im = is.getItemMeta();
                    im.setDisplayName("§r");
                    is.setItemMeta(im);
                    setBackground(0, is);

                    is = new ItemStack(166, 1, (short)0);
                    im = is.getItemMeta();
                    im.setDisplayName("§4§lPermanent");
                    ArrayList<String> lore = new ArrayList<String>();
                    lore.add("§r");
                    lore.add("§eDauer: §cPermanent");
                    lore.add("§r");
                    im.setLore(lore);
                    is.setItemMeta(im);
                    setItemAt(1, 1, is);

                    is = new ItemStack(7, 1, (short)0);
                    im = is.getItemMeta();
                    im.setDisplayName("§2§lHacking");
                    lore = new ArrayList<String>();
                    lore.add("§r");
                    lore.add("§eDauer: §c30 Tage");
                    lore.add("§r");
                    im.setLore(lore);
                    is.setItemMeta(im);
                    setItemAt(3, 1, is);

                    is = new ItemStack(339, 1, (short)0);
                    im = is.getItemMeta();
                    im.setDisplayName("§3§lWerbung");
                    lore = new ArrayList<String>();
                    lore.add("§r");
                    lore.add("§eDauer: §c3 Tage");
                    lore.add("§r");
                    im.setLore(lore);
                    is.setItemMeta(im);
                    setItemAt(5, 1, is);

                    is = new ItemStack(289, 1, (short)0);
                    im = is.getItemMeta();
                    im.setDisplayName("§2§lBeleidigung");
                    lore = new ArrayList<String>();
                    lore.add("§r");
                    lore.add("§eDauer: §c5 Tage");
                    lore.add("§r");
                    im.setLore(lore);
                    is.setItemMeta(im);
                    setItemAt(7, 1, is);

                    is = new ItemStack(280, 1, (short)0);
                    im = is.getItemMeta();
                    im.setDisplayName("§c§lBugusing");
                    lore = new ArrayList<String>();
                    lore.add("§r");
                    lore.add("§eDauer: §c30 Tage");
                    lore.add("§r");
                    im.setLore(lore);
                    is.setItemMeta(im);
                    setItemAt(1, 3, is);

                    is = new ItemStack(386, 1, (short)0);
                    im = is.getItemMeta();
                    im.setDisplayName("§d§lFakereport");
                    lore = new ArrayList<String>();
                    lore.add("§r");
                    lore.add("§eDauer: §c10 Tage");
                    lore.add("§r");
                    im.setLore(lore);
                    is.setItemMeta(im);
                    setItemAt(3, 3, is);

                    is = new ItemStack(340, 1, (short)0);
                    im = is.getItemMeta();
                    im.setDisplayName("§f§lSpam");
                    lore = new ArrayList<String>();
                    lore.add("§r");
                    lore.add("§eDauer: §c5 Tage");
                    lore.add("§r");
                    im.setLore(lore);
                    is.setItemMeta(im);
                    setItemAt(5, 3, is);

                    is = new ItemStack(327, 1, (short)0);
                    im = is.getItemMeta();
                    im.setDisplayName("§c§lProvokation");
                    lore = new ArrayList<String>();
                    lore.add("§r");
                    lore.add("§eDauer: §c5 Tage");
                    lore.add("§r");
                    im.setLore(lore);
                    is.setItemMeta(im);
                    setItemAt(7, 3, is);
                }

                @Override
                public DynamicObject onClick(DynamicObject args) {
                    int slotX = args.getInt(Field.PLAYER_GUI_SLOT_POS_X);
                    int slotY = args.getInt(Field.PLAYER_GUI_SLOT_POS_Y);
                    
                    if (slotX == 1 && slotY == 1) {
                        for (Player tm : getPlayers()) {
                            if (isInTeam(tm)) {
                                sendMessage(tm, getChatName(t) + "§a wurde von §e" + getChatName(p) + "§a gebannt.");
                            }
                        }

                        String reason = "Hausverbot";
                        long time = Long.MAX_VALUE;

                        UserData.set(t, UserValue.banReasons, reason + ";" + UserData.get(t, UserValue.banReasons));
                        UserData.set(t, UserValue.bannedUntil, Long.MAX_VALUE);
                        banSameIps(t, reason, time);

                        sendClickSound(p);

                        TaskManager.runSyncTask("BanCommandKickPlayer", new Runnable() {
                        
                            @Override
                            public void run() {
                                t.kickPlayer(getBanMessage(reason, time));
                                p.closeInventory();
                            }
                        });
                    }

                    if (slotX == 3 && slotY == 1) {
                        for (Player tm : getPlayers()) {
                            if (isInTeam(tm)) {
                                sendMessage(tm, getChatName(t) + "§a wurde von §e" + getChatName(p) + "§a gebannt.");
                            }
                        }

                        String reason = "Unerlaubte Clientmodification";
                        long time = getUnixTimeFromTimeSpan(30, 0, 0, 0);

                        UserData.set(t, UserValue.banReasons, reason + ";" + UserData.get(t, UserValue.banReasons));
                        UserData.set(t, UserValue.bannedUntil, time);
                        banSameIps(t, reason, time);
                        
                        sendClickSound(p);

                        TaskManager.runSyncTask("BanCommandKickPlayer", new Runnable() {
                        
                            @Override
                            public void run() {
                                t.kickPlayer(getBanMessage(reason, time));
                                p.closeInventory();
                            }
                        });
                    }

                    if (slotX == 5 && slotY == 1) {
                        for (Player tm : getPlayers()) {
                            if (isInTeam(tm)) {
                                sendMessage(tm, getChatName(t) + "§a wurde von §e" + getChatName(p) + "§a gebannt.");
                            }
                        }

                        String reason = "Werbung";
                        long time = getUnixTimeFromTimeSpan(5, 0, 0, 0);

                        UserData.set(t, UserValue.banReasons, reason + ";" + UserData.get(t, UserValue.banReasons));
                        UserData.set(t, UserValue.bannedUntil, time);
                        banSameIps(t, reason, time);
                        
                        sendClickSound(p);

                        TaskManager.runSyncTask("BanCommandKickPlayer", new Runnable() {
                        
                            @Override
                            public void run() {
                                t.kickPlayer(getBanMessage(reason, time));
                                p.closeInventory();
                            }
                        });
                    }

                    if (slotX == 7 && slotY == 1) {
                        for (Player tm : getPlayers()) {
                            if (isInTeam(tm)) {
                                sendMessage(tm, getChatName(t) + "§a wurde von §e" + getChatName(p) + "§a gebannt.");
                            }
                        }

                        String reason = "Beleidigung";
                        long time = getUnixTimeFromTimeSpan(5, 0, 0, 0);

                        UserData.set(t, UserValue.banReasons, reason + ";" + UserData.get(t, UserValue.banReasons));
                        UserData.set(t, UserValue.bannedUntil, time);
                        banSameIps(t, reason, time);
                        
                        sendClickSound(p);

                        TaskManager.runSyncTask("BanCommandKickPlayer", new Runnable() {
                        
                            @Override
                            public void run() {
                                t.kickPlayer(getBanMessage(reason, time));
                                p.closeInventory();
                            }
                        });
                    }

                    if (slotX == 1 && slotY == 3) {
                        for (Player tm : getPlayers()) {
                            if (isInTeam(tm)) {
                                sendMessage(tm, getChatName(t) + "§a wurde von §e" + getChatName(p) + "§a gebannt.");
                            }
                        }

                        String reason = "Ausnutzung von Systemfehlern";
                        long time = getUnixTimeFromTimeSpan(30, 0, 0, 0);

                        UserData.set(t, UserValue.banReasons, reason + ";" + UserData.get(t, UserValue.banReasons));
                        UserData.set(t, UserValue.bannedUntil, time);
                        banSameIps(t, reason, time);
                        
                        sendClickSound(p);

                        TaskManager.runSyncTask("BanCommandKickPlayer", new Runnable() {
                        
                            @Override
                            public void run() {
                                t.kickPlayer(getBanMessage(reason, time));
                                p.closeInventory();
                            }
                        });
                    }

                    if (slotX == 3 && slotY == 3) {
                        for (Player tm : getPlayers()) {
                            if (isInTeam(tm)) {
                                sendMessage(tm, getChatName(t) + "§a wurde von §e" + getChatName(p) + "§a gebannt.");
                            }
                        }

                        String reason = "Erstellung von gefälschten Reports";
                        long time = getUnixTimeFromTimeSpan(10, 0, 0, 0);

                        UserData.set(t, UserValue.banReasons, reason + ";" + UserData.get(t, UserValue.banReasons));
                        UserData.set(t, UserValue.bannedUntil, time);
                        banSameIps(t, reason, time);
                        
                        sendClickSound(p);

                        TaskManager.runSyncTask("BanCommandKickPlayer", new Runnable() {
                        
                            @Override
                            public void run() {
                                t.kickPlayer(getBanMessage(reason, time));
                                p.closeInventory();
                            }
                        });
                    }

                    if (slotX == 5 && slotY == 3) {
                        for (Player tm : getPlayers()) {
                            if (isInTeam(tm)) {
                                sendMessage(tm, getChatName(t) + "§a wurde von §e" + getChatName(p) + "§a gebannt.");
                            }
                        }

                        String reason = "Spam";
                        long time = getUnixTimeFromTimeSpan(3, 0, 0, 0);

                        UserData.set(t, UserValue.banReasons, reason + ";" + UserData.get(t, UserValue.banReasons));
                        UserData.set(t, UserValue.bannedUntil, time);
                        banSameIps(t, reason, time);
                        
                        sendClickSound(p);

                        TaskManager.runSyncTask("BanCommandKickPlayer", new Runnable() {
                        
                            @Override
                            public void run() {
                                t.kickPlayer(getBanMessage(reason, time));
                                p.closeInventory();
                            }
                        });
                    }

                    if (slotX == 7 && slotY == 3) {
                        for (Player tm : getPlayers()) {
                            if (isInTeam(tm)) {
                                sendMessage(tm, getChatName(t) + "§a wurde von §e" + getChatName(p) + "§a gebannt.");
                            }
                        }

                        String reason = "Provokation";
                        long time = getUnixTimeFromTimeSpan(5, 0, 0, 0);

                        UserData.set(t, UserValue.banReasons, reason + ";" + UserData.get(t, UserValue.banReasons));
                        UserData.set(t, UserValue.bannedUntil, time);
                        banSameIps(t, reason, time);
                        
                        sendClickSound(p);

                        TaskManager.runSyncTask("BanCommandKickPlayer", new Runnable() {
                        
                            @Override
                            public void run() {
                                t.kickPlayer(getBanMessage(reason, time));
                                p.closeInventory();
                            }
                        });
                    }
                    
                    return new DynamicObject();
                }

                private void banSameIps(Player t, String reason, long time) {
                    for (Player player : getPlayers()) {
                        if (UserData.get(t, UserValue.ip).equals(UserData.get(player, UserValue.ip)) && !isInTeam(player) && player != t) {
                            UserData.set(player, UserValue.banReasons, reason + ";" + UserData.get(player, UserValue.banReasons));
                            UserData.set(player, UserValue.bannedUntil, time);

                            TaskManager.runSyncTask("BanCommandKickPlayer", new Runnable() {

                                @Override
                                public void run() {
                                    player.kickPlayer(getBanMessage(reason, time));
                                    p.closeInventory();
                                }
                            });
                            for (Player tm : getPlayers()) {
                                if (isInTeam(tm)) {
                                    sendMessage(tm, getChatName(player) + "§a wurde von §e" + getChatName(p) + "§a gebannt.");
                                }
                            }
                        }
                    }
                }

                @Override
                public void onClose(DynamicObject args) {
                    
                }

                @Override
                public void onDownBarClick(DynamicObject args) {
                    
                }
                
            }, 0);
            
            return CommandResult.None;
        } else {
            return CommandResult.InvalidUsage;
        }
    }
}