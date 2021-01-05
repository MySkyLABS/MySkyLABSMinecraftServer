package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import de.basicbit.system.GlobalValues;
import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;
import de.basicbit.system.minecraft.dynamic.DynamicObject;
import de.basicbit.system.minecraft.dynamic.Field;
import de.basicbit.system.minecraft.gui.Gui;

public class Dog extends Command {

    @Override
    public String getUsage(Player p) {
        return "";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("dog");
        names.add("pet");
        names.add("hund");
        names.add("wolf");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isDev(p);
    }

    @Override
    public String getDescription(Player p) {
        return "Spawnt deinen Hund.";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 0) {
            openPetmenu(p);

            return CommandResult.None;
        } else {
            return CommandResult.InvalidUsage;
        }
    }

    public static void openPetmenu(Player p) {
        Gui.open(p, new Gui(new String[] { "§d§lHaustiermenü" }, 1) {

            @Override
            public void onInit(DynamicObject args) {
                setBackgroundInAllPagesToGlassColor(15);

                ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                SkullMeta sm = (SkullMeta) is.getItemMeta();
                sm.setDisplayName("§b§lAlter ändern");
                sm.setOwner("DogeMCM");
                is.setItemMeta(sm);
                setItemAt(2, 2, is);
                
                is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                sm = (SkullMeta) is.getItemMeta();
                sm.setDisplayName("§b§lHalsband ändern");
                sm.setOwner("DogeMCM");
                is.setItemMeta(sm);
                setItemAt(3, 2, is);

                is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                sm = (SkullMeta) is.getItemMeta();
                sm.setDisplayName("§a§lHund spawnen");
                sm.setOwner("DogeMCM");
                is.setItemMeta(sm);
                setItemAt(5, 2, is);

                is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                sm = (SkullMeta) is.getItemMeta();
                sm.setDisplayName("§c§lHund entfernen");
                sm.setOwner("DogeMCM");
                is.setItemMeta(sm);
                setItemAt(6, 2, is);
            }

            @Override
            public DynamicObject onClick(DynamicObject args) {
                int slotX = args.getInt(Field.PLAYER_GUI_SLOT_POS_X);
                int slotY = args.getInt(Field.PLAYER_GUI_SLOT_POS_Y);

                if (slotX == 2 && slotY == 2) {
                    Wolf wolf = GlobalValues.dogPets.get(p.getUniqueId());

                    if (wolf != null && !wolf.isDead()) {
                        if (wolf.isAdult()) {
                            wolf.setBaby();
                            sendSuccessSound(p);
                            sendMessage(p, "§aDein Hund ist jetzt ein Baby.");
                        } else {
                            sendSuccessSound(p);
                            wolf.setAdult();
                            sendMessage(p, "§aDein Hund ist jetzt erwachsen.");
                        }
                    }
                }
                if (slotX == 3 && slotY == 2) {
                    Gui.open(p, new Gui(new String[] { "§d§lHalsbandmenü" }, 1) {

                        @SuppressWarnings("deprecation")
                        @Override
                        public void onInit(DynamicObject args) {
                            setBackgroundInAllPagesToGlassColor(15);

                            ItemStack is = new ItemStack(351, 1, (short) 11);
                            setItemAt(2, 1, is);
                            is = new ItemStack(351, 1, (short) 14);
                            setItemAt(3, 1, is);
                            is = new ItemStack(351, 1, (short) 1);
                            setItemAt(4, 1, is);
                            is = new ItemStack(351, 1, (short) 9);
                            setItemAt(5, 1, is);
                            is = new ItemStack(351, 1, (short) 13);
                            setItemAt(6, 1, is);
                            is = new ItemStack(351, 1, (short) 5);
                            setItemAt(1, 2, is);
                            is = new ItemStack(351, 1, (short) 4);
                            setItemAt(2, 2, is);
                            is = new ItemStack(351, 1, (short) 6);
                            setItemAt(3, 2, is);
                            is = new ItemStack(351, 1, (short) 12);
                            setItemAt(4, 2, is);
                            is = new ItemStack(351, 1, (short) 10);
                            setItemAt(5, 2, is);
                            is = new ItemStack(351, 1, (short) 2);
                            setItemAt(6, 2, is);
                            is = new ItemStack(351, 1, (short) 3);
                            setItemAt(7, 2, is);
                            is = new ItemStack(351, 1, (short) 0);
                            setItemAt(2, 3, is);
                            is = new ItemStack(351, 1, (short) 8);
                            setItemAt(3, 3, is);
                            is = new ItemStack(351, 1, (short) 7);
                            setItemAt(5, 3, is);
                            is = new ItemStack(351, 1, (short) 15);
                            setItemAt(6, 3, is);

                            is = new ItemStack(166);
                            ItemMeta im = is.getItemMeta();
                            im.setDisplayName("§c§lZurück");
                            is.setItemMeta(im);
                            setItemAt(4, 4, is);
                        }

                        @Override
                        public DynamicObject onClick(DynamicObject args) {
                            int slotX = args.getInt(Field.PLAYER_GUI_SLOT_POS_X);
                            int slotY = args.getInt(Field.PLAYER_GUI_SLOT_POS_Y);

                            Wolf wolf = GlobalValues.dogPets.get(p.getUniqueId());
                            DyeColor color = null;
                            
                            if (slotX == 2 && slotY == 1 && wolf != null && !wolf.isDead()) {
                                color = DyeColor.YELLOW;
                                wolf.setCollarColor(color);
                                sendMessage(p, "§aDein Hundehalsband ist jetzt gelb.");
                            } else if (slotX == 3 && slotY == 1 && wolf != null && !wolf.isDead()) {
                                color = DyeColor.ORANGE;
                                wolf.setCollarColor(color);
                                sendMessage(p, "§aDein Hundehalsband ist jetzt orange.");
                            } else if (slotX == 4 && slotY == 1 && wolf != null && !wolf.isDead()) {
                                color = DyeColor.RED;
                                wolf.setCollarColor(color);
                                sendMessage(p, "§aDein Hundehalsband ist jetzt rot.");
                            } else if (slotX == 5 && slotY == 1 && wolf != null && !wolf.isDead()) {
                                color = DyeColor.PINK;
                                wolf.setCollarColor(color);
                                sendMessage(p, "§aDein Hundehalsband ist jetzt rosa.");
                            } else if (slotX == 6 && slotY == 1 && wolf != null && !wolf.isDead()) {
                                color = DyeColor.MAGENTA;
                                wolf.setCollarColor(color);
                                sendMessage(p, "§aDein Hundehalsband ist jetzt magenta.");
                            } else if (slotX == 1 && slotY == 2 && wolf != null && !wolf.isDead()) {
                                color = DyeColor.PURPLE;
                                wolf.setCollarColor(color);
                                sendMessage(p, "§aDein Hundehalsband ist jetzt lila.");
                            } else if (slotX == 2 && slotY == 2 && wolf != null && !wolf.isDead()) {
                                color = DyeColor.BLUE;
                                wolf.setCollarColor(color);
                                sendMessage(p, "§aDein Hundehalsband ist jetzt blau.");
                            } else if (slotX == 3 && slotY == 2 && wolf != null && !wolf.isDead()) {
                                color = DyeColor.CYAN;
                                wolf.setCollarColor(color);
                                sendMessage(p, "§aDein Hundehalsband ist jetzt türkis.");
                            } else if (slotX == 4 && slotY == 2 && wolf != null && !wolf.isDead()) {
                                color = DyeColor.LIGHT_BLUE;
                                wolf.setCollarColor(color);
                                sendMessage(p, "§aDein Hundehalsband ist jetzt hellblau.");
                            } else if (slotX == 5 && slotY == 2 && wolf != null && !wolf.isDead()) {
                                color = DyeColor.LIME;
                                wolf.setCollarColor(color);
                                sendMessage(p, "§aDein Hundehalsband ist jetzt hellgrün.");
                            } else if (slotX == 6 && slotY == 2 && wolf != null && !wolf.isDead()) {
                                color = DyeColor.GREEN;
                                wolf.setCollarColor(color);
                                sendMessage(p, "§aDein Hundehalsband ist jetzt grün.");
                            } else if (slotX == 7 && slotY == 2 && wolf != null && !wolf.isDead()) {
                                color = DyeColor.BROWN;
                                wolf.setCollarColor(color);
                                sendMessage(p, "§aDein Hundehalsband ist jetzt braun.");
                            } else if (slotX == 2 && slotY == 3 && wolf != null && !wolf.isDead()) {
                                color = DyeColor.BLACK;
                                wolf.setCollarColor(color);
                                sendMessage(p, "§aDein Hundehalsband ist jetzt schwarz.");
                            } else if (slotX == 3 && slotY == 3 && wolf != null && !wolf.isDead()) {
                                color = DyeColor.GRAY;
                                wolf.setCollarColor(color);
                                sendMessage(p, "§aDein Hundehalsband ist jetzt grau.");
                            } else if (slotX == 5 && slotY == 3 && wolf != null && !wolf.isDead()) {
                                color = DyeColor.SILVER;
                                wolf.setCollarColor(color);
                                sendMessage(p, "§aDein Hundehalsband ist jetzt hellgrau.");
                            } else if (slotX == 6 && slotY == 3 && wolf != null && !wolf.isDead()) {
                                color = DyeColor.WHITE;
                                wolf.setCollarColor(color);
                                sendMessage(p, "§aDein Hundehalsband ist jetzt weiß.");
                            } else if (slotX == 4 && slotY == 4) {
                                openPetmenu(p);
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
                } else if (slotX == 5 && slotY == 2) {
                    if (p.getWorld() != getSpawnWorld() && p.getWorld() != getKnockItWorld()) {
                        if (!GlobalValues.dogPets.containsKey(p.getUniqueId()) || 
                            GlobalValues.dogPets.get(p.getUniqueId()) != null) {
                            Wolf wolf = p.getWorld().spawn(p.getLocation(), Wolf.class);
                            GlobalValues.dogPets.put(p.getUniqueId(), wolf);
                            wolf.setCustomName("§f§lHund von " + getChatName(p));
                            wolf.setCustomNameVisible(true);
                            wolf.setTamed(true);
                            wolf.setOwner(p);
                            wolf.setBreed(false);

                            sendSuccessSound(p);
                            sendMessage(p, "§aDein Hund ist erschienen. Wuff!");
                        } else {
                            sendErrorSound(p);
                            sendMessage(p, "§cDu kannst nicht noch einen Hund haben!");
                        }
                    } else {
                        sendErrorSound(p);
                        sendMessage(p, "§cDu kannst deinen Hund hier nicht spawnen.");
                    }
                } else if (slotX == 6 && slotY == 2) {
                    Wolf wolf = GlobalValues.dogPets.get(p.getUniqueId());
                    if (wolf != null && !wolf.isDead()) {
                        wolf.remove();
                        GlobalValues.dogPets.remove(p.getUniqueId(), wolf);
                        sendMessage(p, "§aDein Hund wurde entfernt.");
                    } else {
                        sendMessage(p, "§cDu hast keinen Hund.");
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
        }, 0);
    }
}
