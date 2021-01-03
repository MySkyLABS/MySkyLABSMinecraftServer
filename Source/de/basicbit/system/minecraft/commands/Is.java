package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import de.basicbit.system.database.UserData;
import de.basicbit.system.database.UserValue;
import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;
import de.basicbit.system.minecraft.dynamic.DynamicObject;
import de.basicbit.system.minecraft.dynamic.Field;
import de.basicbit.system.minecraft.gui.Click;
import de.basicbit.system.minecraft.gui.Gui;
import de.basicbit.system.minecraft.skyblock.SkyBlock;
import de.basicbit.system.minecraft.skyblock.IslandBan;
import de.basicbit.system.minecraft.skyblock.IslandTrust;
import de.basicbit.system.minecraft.tasks.TaskManager;

@SuppressWarnings("deprecation")
public class Is extends Command {

    @Override
    public String getUsage(Player p) {
        return "[Spieler]";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("is");
        names.add("skyblock");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return true;
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 0) {

            openWorldsView(p, p.getUniqueId());

            return CommandResult.None;
        } else if (args.length == 1) {
            UUID id = UserData.getUUIDFromName(args[0]);

            if (id == null) {
                return CommandResult.PlayerNotInDataBase;
            }

            openWorldsView(p, id);

            return CommandResult.None;
        } else {
            return CommandResult.InvalidNumber;
        }
    }

    @Override
    public String getDescription(Player p) {
        return "Ermöglicht das verwalten und betreten von Inseln.";
    }

    public static void openWorldsView(Player p, UUID id) {
        TaskManager.runAsyncTask(new Runnable() {
        
            @Override
            public void run() {
                Gui.open(p, new Gui(new String[] {
                    "§2§lSkyBlock"
                }, 1) {
                    
                    @Override
                    public void onInit(DynamicObject args) {
                        setBackgroundToGlassColor(0, 15);

                        ItemStack is = new ItemStack(397, 1, (short)3);
                        SkullMeta sm = (SkullMeta)is.getItemMeta();
                        sm.setDisplayName(getChatName(id));
                        sm.setOwner(UserData.get(id, UserValue.playerName));
                        is.setItemMeta(sm);
                        setItemAt(4, 1, is);

                        ArrayList<String> info = new ArrayList<String>();
                        info.add("§r");
                        info.add("§b§lLinksklick: §eWelt betreten");

                        if (isModerator(p) || p.getUniqueId().equals(id)) {
                            info.add("§d§lRechtsklick: §eWelt verwalten");
                        }

                        info.add("§r");

                        is = new ItemStack(2, 1, (short)0);
                        ItemMeta im = is.getItemMeta();
                        im.setDisplayName("§6§lWelt 1");
                        im.setLore(info);
                        is.setItemMeta(im);
                        setItemAt(2, 3, is);

                        is = new ItemStack(3, 1, (short)2);
                        im = is.getItemMeta();
                        im.setDisplayName("§6§lWelt 2");
                        im.setLore(info);
                        is.setItemMeta(im);
                        setItemAt(4, 3, is);

                        is = new ItemStack(110, 1, (short)0);
                        im = is.getItemMeta();
                        im.setDisplayName("§6§lWelt 3");
                        im.setLore(info);
                        is.setItemMeta(im);
                        setItemAt(6, 3, is);
                    }
                    
                    @Override
                    public void onClose(DynamicObject args) {
                        
                    }
                    
                    @Override
                    public DynamicObject onClick(DynamicObject args) {
                        int slotX = args.getInt(Field.PLAYER_GUI_SLOT_POS_X);
                        int slotY = args.getInt(Field.PLAYER_GUI_SLOT_POS_Y);
                        int click = args.getInt(Field.PLAYER_GUI_CLICKTYPE);

                        if (click == Click.LEFT) {
                            if (slotX == 2 && slotY == 3) {
                                SkyBlock.joinWorld(p, id, 0);
                            }
                            
                            if (slotX == 4 && slotY == 3) {
                                SkyBlock.joinWorld(p, id, 1);
                            }
                            
                            if (slotX == 6 && slotY == 3) {
                                SkyBlock.joinWorld(p, id, 2);
                            }
                        } else if (isModerator(p) || p.getUniqueId().equals(id)) {
                            if (slotX == 2 && slotY == 3) {
                                openSettings(p, id, 0);
                            }
                            
                            if (slotX == 4 && slotY == 3) {
                                openSettings(p, id, 1);
                            }
                            
                            if (slotX == 6 && slotY == 3) {
                                openSettings(p, id, 2);
                            }
                        }

                        return new DynamicObject();
                    }

                    @Override
                    public void onDownBarClick(DynamicObject args) {
                        
                    }
                }, 0);
            }
        });
    }

    public static void openSettings(Player p, UUID id, int number) {
        Gui.open(p, new Gui(new String[] {
            "§2§lSkyBlock §7- §6§lWelt " + (number + 1)
        }, 1) {
            
            @Override
            public void onInit(DynamicObject args) {
                setBackgroundToGlassColor(0, 15);

                String name = UserData.get(id, UserValue.playerName);
                ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
                SkullMeta sm = (SkullMeta)is.getItemMeta();
                sm.setDisplayName(getChatName(id));
                sm.setOwner(name);
                is.setItemMeta(sm);
                setItemAt(1, 0, is);

                is = new ItemStack(388, 1, (short)0);
                ItemMeta im = is.getItemMeta();
                im.setDisplayName("§aVertraute Spieler");
                ArrayList<String> lore = new ArrayList<String>();
                lore.add("§r");
                lore.add("§b§lLinksklick: §aSpieler vertrauen");
                lore.add("§d§lRechtsklick: §cSpieler misstrauen");
                lore.add("§r");
                im.setLore(lore);
                is.setItemMeta(im);
                setItemAt(3, 0, is);

                is = new ItemStack(323, 1, (short)0);
                im = is.getItemMeta();
                im.setDisplayName("§cVerbotene Spieler");
                lore = new ArrayList<String>();
                lore.add("§r");
                lore.add("§b§lLinksklick: §cSpieler sperren");
                lore.add("§d§lRechtsklick: §aSpieler entsperren");
                lore.add("§r");
                im.setLore(lore);
                is.setItemMeta(im);
                setItemAt(5, 0, is);

                is = new ItemStack(166, 1, (short)0);
                im = is.getItemMeta();
                im.setDisplayName("§4Insel löschen");
                is.setItemMeta(im);
                setItemAt(7, 0, is);

                is = new ItemStack(386, 1, (short)0);
                im = is.getItemMeta();
                im.setDisplayName("§aSpawn für Vertraute setzen");
                is.setItemMeta(im);
                setItemAt(7, 2, is);

                is = new ItemStack(340, 1, (short)0);
                im = is.getItemMeta();
                im.setDisplayName("§aSpawn für Besucher setzen");
                is.setItemMeta(im);
                setItemAt(7, 3, is);

                is = new ItemStack(397, 1, (short)2);
                im = is.getItemMeta();
                im.setDisplayName("§cMonster spawnen erlauben");
                is.setItemMeta(im);
                setItemAt(1, 2, is);

                is = new ItemStack(267, 1, (short)0);
                im = is.getItemMeta();
                im.setDisplayName("§ePvP erlauben");
                is.setItemMeta(im);
                setItemAt(2, 2, is);

                is = new ItemStack(324, 1, (short)0);
                im = is.getItemMeta();
                im.setDisplayName("§9Besucher erlauben");
                is.setItemMeta(im);
                setItemAt(3, 2, is);

                is = new ItemStack(54, 1, (short)0);
                im = is.getItemMeta();
                im.setDisplayName("§dInteraktionen erlauben");
                is.setItemMeta(im);
                setItemAt(4, 2, is);

                is = new ItemStack(2, 1, (short)0);
                im = is.getItemMeta();
                im.setDisplayName("§2Bauen erlauben");
                is.setItemMeta(im);
                setItemAt(5, 2, is);

                boolean allowMonsters = UserData.getBoolean(id, UserValue.valueOf("island" + number + "Monsters"));
                boolean allowPvP = UserData.getBoolean(id, UserValue.valueOf("island" + number + "UsersPvp"));
                boolean allowVisit = UserData.getBoolean(id, UserValue.valueOf("island" + number + "UsersVisit"));
                boolean allowInteract = UserData.getBoolean(id, UserValue.valueOf("island" + number + "UsersInteract"));
                boolean allowBuild = UserData.getBoolean(id, UserValue.valueOf("island" + number + "UsersBuild"));

                if (allowMonsters) {
                    is = new ItemStack(160, 1, (short)5);
                    im = is.getItemMeta();
                    im.setDisplayName("§aAn");
                    is.setItemMeta(im);
                    setItemAt(1, 3, is);
                } else {
                    is = new ItemStack(160, 1, (short)14);
                    im = is.getItemMeta();
                    im.setDisplayName("§cAus");
                    is.setItemMeta(im);
                    setItemAt(1, 3, is);
                }

                if (allowPvP) {
                    is = new ItemStack(160, 1, (short)5);
                    im = is.getItemMeta();
                    im.setDisplayName("§aAn");
                    is.setItemMeta(im);
                    setItemAt(2, 3, is);
                } else {
                    is = new ItemStack(160, 1, (short)14);
                    im = is.getItemMeta();
                    im.setDisplayName("§cAus");
                    is.setItemMeta(im);
                    setItemAt(2, 3, is);
                }
                
                if (allowVisit) {
                    is = new ItemStack(160, 1, (short)5);
                    im = is.getItemMeta();
                    im.setDisplayName("§aAn");
                    is.setItemMeta(im);
                    setItemAt(3, 3, is);
                } else {
                    is = new ItemStack(160, 1, (short)14);
                    im = is.getItemMeta();
                    im.setDisplayName("§cAus");
                    is.setItemMeta(im);
                    setItemAt(3, 3, is);
                }

                if (allowInteract) {
                    is = new ItemStack(160, 1, (short)5);
                    im = is.getItemMeta();
                    im.setDisplayName("§aAn");
                    is.setItemMeta(im);
                    setItemAt(4, 3, is);
                } else {
                    is = new ItemStack(160, 1, (short)14);
                    im = is.getItemMeta();
                    im.setDisplayName("§cAus");
                    is.setItemMeta(im);
                    setItemAt(4, 3, is);
                }

                if (allowBuild) {
                    is = new ItemStack(160, 1, (short)5);
                    im = is.getItemMeta();
                    im.setDisplayName("§aAn");
                    is.setItemMeta(im);
                    setItemAt(5, 3, is);
                } else {
                    is = new ItemStack(160, 1, (short)14);
                    im = is.getItemMeta();
                    im.setDisplayName("§cAus");
                    is.setItemMeta(im);
                    setItemAt(5, 3, is);
                }
            }
            
            @Override
            public void onClose(DynamicObject args) {

            }
            
            @Override
            public DynamicObject onClick(DynamicObject args) {
                int slotX = args.getInt(Field.PLAYER_GUI_SLOT_POS_X);
                int slotY = args.getInt(Field.PLAYER_GUI_SLOT_POS_Y);
                int click = args.getInt(Field.PLAYER_GUI_CLICKTYPE);
                
                if (slotX == 1 && slotY == 0) {
                    openWorldsView(p, id);
                    return new DynamicObject();
                }

                if (slotX == 7 && slotY == 2) {
                    if (p.getWorld().getName().equalsIgnoreCase(SkyBlock.getWorldName(id, number))) {
                        Location pos = p.getLocation();
                        float yaw = p.getLocation().getYaw();
                        float pitch = p.getLocation().getPitch();
                        SkyBlock.setSpawnTrust(id, number, pos, yaw, pitch);
                        sendMessage(p, "§aDer Einstiegspunkt wurde erfolgreich geändert.");
                    } else {
                        sendMessage(p, "§cDu musst diese Insel betreten, um einen Einstiegspunkt zu setzen.");
                        p.closeInventory();
                    }
                    return new DynamicObject();
                }

                if (slotX == 7 && slotY == 3) {
                    if (p.getWorld().getName().equalsIgnoreCase(SkyBlock.getWorldName(id, number))) {
                        Location pos = p.getLocation();
                        float yaw = p.getLocation().getYaw();
                        float pitch = p.getLocation().getPitch();
                        SkyBlock.setSpawnVisit(id, number, pos, yaw, pitch);
                        sendMessage(p, "§aDer Einstiegspunkt wurde erfolgreich geändert.");
                    } else {
                        sendMessage(p, "§cDu musst diese Insel betreten, um einen Einstiegspunkt zu setzen.");
                        p.closeInventory();
                    }
                    return new DynamicObject();
                }

                if (slotX == 3 && slotY == 0) {
                    if (click == Click.LEFT) {
                        openAddTrust(p, id, number);
                    } else if (click == Click.RIGHT) {
                        openRemoveTrust(p, id, number);
                    }
                    return new DynamicObject();
                }

                if (slotX == 5 && slotY == 0) {
                    if (click == Click.LEFT) {
                        openAddBan(p, id, number);
                    } else if (click == Click.RIGHT) {
                        openRemoveBan(p, id, number);
                    }
                    return new DynamicObject();
                }

                if (slotX == 7 && slotY == 0) {
                    Gui.openYesNo(p, "§4§lWelt " + (number + 1) + " wirklich löschen?", new Runnable() {
                    
                        @Override
                        public void run() {
                            SkyBlock.deleteWorld(id, number);

                            sendMessage(p, "§aWelt erfolgreich gelöscht.");
                            p.closeInventory();
                        }

                    }, new Runnable() {
                    
                        @Override
                        public void run() {
                            openSettings(p, id, number);
                        }
                    });
                    return new DynamicObject();
                }

                //allowMonsters
                if (slotX == 1 && slotY == 3) {
                    UserValue value = UserValue.valueOf("island" + number + "Monsters");
                    UserData.set(id, value, !UserData.getBoolean(id, value));
                    
                    openSettings(p, id, number);
                    return new DynamicObject();
                }

                //allowPvP
                if (slotX == 2 && slotY == 3) {
                    UserValue value = UserValue.valueOf("island" + number + "UsersPvp");
                    UserData.set(id, value, !UserData.getBoolean(id, value));
                    
                    openSettings(p, id, number);
                    return new DynamicObject();
                }

                //allowVisit
                if (slotX == 3 && slotY == 3) {
                    UserValue value = UserValue.valueOf("island" + number + "UsersVisit");
                    UserData.set(id, value, !UserData.getBoolean(id, value));
                    
                    openSettings(p, id, number);
                    return new DynamicObject();
                }

                //allowInteract
                if (slotX == 4 && slotY == 3) {
                    UserValue value = UserValue.valueOf("island" + number + "UsersInteract");
                    UserData.set(id, value, !UserData.getBoolean(id, value));

                    openSettings(p, id, number);
                    return new DynamicObject();
                }

                //allowBuild
                if (slotX == 5 && slotY == 3) {
                    UserValue value = UserValue.valueOf("island" + number + "UsersBuild");
                    UserData.set(id, value, !UserData.getBoolean(id, value));

                    openSettings(p, id, number);
                    return new DynamicObject();
                }

                return new DynamicObject();
            }

            @Override
            public void onDownBarClick(DynamicObject args) {
                
            }
        }, 0);
    }

    public static void openAddBan(Player p, UUID id, int number) {
        
        ArrayList<Player> players = new ArrayList<Player>();
        ArrayList<IslandBan> bans = getBansFromDatabase(id, number);

        for (Player t : getPlayers()) {
            boolean isBanned = false;

            for (IslandBan ban : bans) {
                if (ban.id.equals(t.getUniqueId())) {
                    isBanned = true;
                    break;
                }
            }

            if (!id.equals(t.getUniqueId()) && !isBanned) {
                players.add(t);
            }
        }

        int playerCount = players.size();

        Gui.open(p, new Gui(new String[] { "§4Wähle einen Spieler..." }, playerCount == 0 ? 1 : (playerCount % 45 == 0 ? playerCount / 45 : playerCount / 45 + 1)) {
        
            @Override
            public void onInit(DynamicObject args) {
                setBackgroundToBarriers();

                for (int i = 0; i < playerCount; i++) {
                    Player t = players.get(i);

                    if (!isVanish(t)) {
                        ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
                        SkullMeta sm = (SkullMeta) is.getItemMeta();
                        sm.setDisplayName(getChatName(t));
                        sm.setOwner(t.getName());
                        is.setItemMeta(sm);
                        setItemAt(i, is);
                    }
                }

                ItemStack is = new ItemStack(331, 1, (short)0);
                ItemMeta im = is.getItemMeta();
                im.setDisplayName("§c§lZurück");
                is.setItemMeta(im);
                setDownBarItemAt(4, is);
            }
        
            @Override
            public void onClose(DynamicObject args) {

            }
        
            @Override
            public DynamicObject onClick(DynamicObject args) {
                ItemStack is = args.getItemStack(Field.PLAYER_GUI_ITEM_CLICKED);

                if (is != null && is.getType() == Material.SKULL_ITEM) {
                    SkullMeta sm = (SkullMeta) is.getItemMeta();
                    Player t = getPlayer(sm.getOwner());

                    if (t == null) {
                        p.closeInventory();
                        sendMessage(p, "§cDieser Spieler ist nicht mehr online.");
                        return new DynamicObject();
                    }

                    bans.add(new IslandBan(t.getUniqueId(), t.getName(), getGroup(t), getGender(t)));
                    sendMessage(p, getChatName(t) + "§c ist jetzt gebannt.");

                    if (isInWorld(t, Bukkit.getWorld(SkyBlock.getWorldName(id, number))) && !isInTeam(t)) {
                        TaskManager.runSyncTask("IsCommandTeleport", new Runnable() {
                        
                            @Override
                            public void run() {
                                t.teleport(getSpawn());
                            }
                        });
                        sendMessage(t, "§cDu wurdest von der Insel gebannt.");
                    }

                    setBansInDatabase(id, number, bans);
                    openAddBan(p, id, number);
                }

                return new DynamicObject();
            }

            @Override
            public void onDownBarClick(DynamicObject args) {
                if (args.getInt(Field.PLAYER_GUI_SLOT_POS) == 4) {
                    openSettings(p, id, number);
                }
            }
        }, 0);
    }

    public static void openAddTrust(Player p, UUID id, int number) {
        
        ArrayList<Player> players = new ArrayList<Player>();
        ArrayList<IslandTrust> trusts = getTrustsFromDatabase(id, number);

        for (Player t : getPlayers()) {
            boolean isTrusted = false;

            for (IslandTrust trust : trusts) {
                if (trust.id.equals(t.getUniqueId())) {
                    isTrusted = true;
                    break;
                }
            }

            if (!id.equals(t.getUniqueId()) && !isTrusted) {
                players.add(t);
            }
        }

        int playerCount = players.size();

        Gui.open(p, new Gui(new String[] { "§2Wähle einen Spieler..." }, playerCount == 0 ? 1 : (playerCount % 45 == 0 ? playerCount / 45 : playerCount / 45 + 1)) {
        
            @Override
            public void onInit(DynamicObject args) {
                setBackgroundToBarriers();

                for (int i = 0; i < playerCount; i++) {
                    Player t = players.get(i);

                    if (!isVanish(t)) {
                        ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
                        SkullMeta sm = (SkullMeta) is.getItemMeta();
                        sm.setDisplayName(getChatName(t));
                        sm.setOwner(t.getName());
                        is.setItemMeta(sm);
                        setItemAt(i, is);
                    }
                }

                ItemStack is = new ItemStack(331, 1, (short)0);
                ItemMeta im = is.getItemMeta();
                im.setDisplayName("§c§lZurück");
                is.setItemMeta(im);
                setDownBarItemAt(4, is);
            }
        
            @Override
            public void onClose(DynamicObject args) {

            }
        
            @Override
            public DynamicObject onClick(DynamicObject args) {
                ItemStack is = args.getItemStack(Field.PLAYER_GUI_ITEM_CLICKED);

                if (is != null && is.getType() == Material.SKULL_ITEM) {
                    SkullMeta sm = (SkullMeta) is.getItemMeta();
                    Player t = getPlayer(sm.getOwner());

                    if (t == null) {
                        p.closeInventory();
                        sendMessage(p, "§cDieser Spieler ist nicht mehr online.");
                        return new DynamicObject();
                    }

                    trusts.add(new IslandTrust(t.getUniqueId(), t.getName(), getGroup(t), getGender(t)));
                    sendMessage(p, getChatName(t) + "§a ist jetzt ein Vertrauter dieser Insel.");

                    setTrustsInDatabase(id, number, trusts);
                    openAddTrust(p, id, number);
                }

                return new DynamicObject();
            }

            @Override
            public void onDownBarClick(DynamicObject args) {
                if (args.getInt(Field.PLAYER_GUI_SLOT_POS) == 4) {
                    openSettings(p, id, number);
                }
            }
        }, 0);
    }

    public static void openRemoveTrust(Player p, UUID id, int number) {
        
        ArrayList<IslandTrust> trusts = getTrustsFromDatabase(id, number);

        int trustCount = trusts.size();

        Gui.open(p, new Gui(new String[] { "§4Wähle einen Spieler..." }, trustCount == 0 ? 1 : (trustCount % 45 == 0 ? trustCount / 45 : trustCount / 45 + 1)) {
        
            @Override
            public void onInit(DynamicObject args) {
                setBackgroundToBarriers();

                for (int i = 0; i < trustCount; i++) {
                    IslandTrust trust = trusts.get(i);

                    ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
                    SkullMeta sm = (SkullMeta)is.getItemMeta();
                    sm.setDisplayName(getChatName(trust.group, trust.gender, trust.name) + " §cnicht mehr vertrauen");
                    sm.setOwner(trust.name);
                    is.setItemMeta(sm);
                    setItemAt(i, is);
                }

                ItemStack is = new ItemStack(331, 1, (short)0);
                ItemMeta im = is.getItemMeta();
                im.setDisplayName("§c§lZurück");
                is.setItemMeta(im);
                setDownBarItemAt(4, is);
            }
        
            @Override
            public void onClose(DynamicObject args) {

            }
        
            @Override
            public DynamicObject onClick(DynamicObject args) {
                ItemStack is = args.getItemStack(Field.PLAYER_GUI_ITEM_CLICKED);
                int slot = args.getInt(Field.PLAYER_GUI_SLOT_POS);

                if (is != null && is.getType() == Material.SKULL_ITEM) {
                    IslandTrust trust = trusts.get(slot);

                    trusts.remove(slot);
                    sendMessage(p, getChatName(trust.group, trust.gender, trust.name) + "§c ist jetzt kein Vertrauter dieser Insel mehr.");

                    setTrustsInDatabase(id, number, trusts);
                    openRemoveTrust(p, id, number);
                }

                return new DynamicObject();
            }

            @Override
            public void onDownBarClick(DynamicObject args) {
                if (args.getInt(Field.PLAYER_GUI_SLOT_POS) == 4) {
                    openSettings(p, id, number);
                }
            }
        }, 0);
    }

    public static void openRemoveBan(Player p, UUID id, int number) {
        
        ArrayList<IslandBan> bans = getBansFromDatabase(id, number);

        int banCount = bans.size();

        Gui.open(p, new Gui(new String[] { "§2Wähle einen Spieler..." }, banCount == 0 ? 1 : (banCount % 45 == 0 ? banCount / 45 : banCount / 45 + 1)) {
        
            @Override
            public void onInit(DynamicObject args) {
                setBackgroundToBarriers();

                for (int i = 0; i < banCount; i++) {
                    IslandBan ban = bans.get(i);

                    ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
                    SkullMeta sm = (SkullMeta)is.getItemMeta();
                    sm.setDisplayName(getChatName(ban.group, ban.gender, ban.name) + " §cnicht mehr vertrauen");
                    sm.setOwner(ban.name);
                    is.setItemMeta(sm);
                    setItemAt(i, is);
                }

                ItemStack is = new ItemStack(331, 1, (short)0);
                ItemMeta im = is.getItemMeta();
                im.setDisplayName("§c§lZurück");
                is.setItemMeta(im);
                setDownBarItemAt(4, is);
            }
        
            @Override
            public void onClose(DynamicObject args) {

            }
        
            @Override
            public DynamicObject onClick(DynamicObject args) {
                ItemStack is = args.getItemStack(Field.PLAYER_GUI_ITEM_CLICKED);
                int slot = args.getInt(Field.PLAYER_GUI_SLOT_POS);

                if (is != null && is.getType() == Material.SKULL_ITEM) {
                    IslandBan ban = bans.get(slot);

                    bans.remove(slot);
                    sendMessage(p, getChatName(ban.group, ban.gender, ban.name) + "§c ist jetzt entbannt.");

                    setBansInDatabase(id, number, bans);
                    openRemoveBan(p, id, number);
                }
                
                return new DynamicObject();
            }

            @Override
            public void onDownBarClick(DynamicObject args) {
                if (args.getInt(Field.PLAYER_GUI_SLOT_POS) == 4) {
                    openSettings(p, id, number);
                }
            }
        }, 0);
    }

    public static ArrayList<IslandTrust> getTrustsFromDatabase(UUID id, int number) {
        ArrayList<IslandTrust> trusts = new ArrayList<IslandTrust>();
        String trustsAsString = UserData.get(id, UserValue.valueOf("island" + number + "Trusts"));
        
        if (trustsAsString != null) {
            if (trustsAsString.contains(";")) {
                String[] trustsAsStringArray = trustsAsString.split(";");
                
                for (String trustAsString : trustsAsStringArray) {
                    trusts.add(IslandTrust.parse(trustAsString));
                }
            } else if (trustsAsString.contains(",")) {
                trusts.add(IslandTrust.parse(trustsAsString));
            }
        }

        return trusts;
    }

    private static void setTrustsInDatabase(UUID id, int number, ArrayList<IslandTrust> trusts) {
        String trustsAsString = "";

        for (IslandTrust trust : trusts) {
            trustsAsString += ";" + trust;
        }

        if (trustsAsString.startsWith(";")) {
            trustsAsString = trustsAsString.substring(1);
        }

        UserData.set(id, UserValue.valueOf("island" + number + "Trusts"), trustsAsString);
    }

    public static ArrayList<IslandBan> getBansFromDatabase(UUID id, int number) {
        ArrayList<IslandBan> bans = new ArrayList<IslandBan>();
        String bansAsString = UserData.get(id, UserValue.valueOf("island" + number + "Bans"));
        
        if (bansAsString != null) {
            if (bansAsString.contains(";")) {
                String[] bansAsStringArray = bansAsString.split(";");
                
                for (String banAsString : bansAsStringArray) {
                    bans.add(IslandBan.parse(banAsString));
                }
            } else if (bansAsString.contains(",")) {
                bans.add(IslandBan.parse(bansAsString));
            }
        }

        return bans;
    }

    private static void setBansInDatabase(UUID id, int number, ArrayList<IslandBan> bans) {
        String bansAsString = "";

        for (IslandBan ban : bans) {
            bansAsString += ";" + ban;
        }

        if (bansAsString.startsWith(";")) {
            bansAsString = bansAsString.substring(1);
        }

        UserData.set(id, UserValue.valueOf("island" + number + "Bans"), bansAsString);
    }

    @Override
    public boolean isAsync() {
        return true;
    }
}