package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.basicbit.system.database.UserData;
import de.basicbit.system.database.UserValue;
import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;
import de.basicbit.system.minecraft.dynamic.DynamicObject;
import de.basicbit.system.minecraft.dynamic.Field;
import de.basicbit.system.minecraft.gui.Gui;

@SuppressWarnings("deprecation")
public class Kit extends Command {

    @Override
    public String getUsage(Player p) {
        return "";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("kit");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return true;
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 0) {

            openKitGui(p);

            return CommandResult.None;
        }

        return CommandResult.InvalidUsage;
    }

    public static void openKitGui(Player p) {
        ArrayList<ArrayList<ItemStack>> kits = new ArrayList<ArrayList<ItemStack>>();
        ArrayList<ItemStack> icons = new ArrayList<ItemStack>();
        ArrayList<String> names = new ArrayList<String>();
        
        names.add("§8§lSpieler");
        icons.add(new ItemStack(298));
        ArrayList<ItemStack> kit = new ArrayList<ItemStack>();
        
        ItemStack is = new ItemStack(272);
        is.addUnsafeEnchantment(Enchantment.getById(34), 1);
        is.addUnsafeEnchantment(Enchantment.getById(16), 1);
        kit.add(is);

        is = new ItemStack(275);
        is.addUnsafeEnchantment(Enchantment.getById(34), 1);
        kit.add(is);

        is = new ItemStack(274);
        is.addUnsafeEnchantment(Enchantment.getById(34), 1);
        kit.add(is);

        is = new ItemStack(273);
        is.addUnsafeEnchantment(Enchantment.getById(34), 1);
        kit.add(is);

        is = new ItemStack(298);
        is.addUnsafeEnchantment(Enchantment.getById(34), 1);
        is.addUnsafeEnchantment(Enchantment.getById(0), 3);
        kit.add(is);

        is = new ItemStack(303);
        is.addUnsafeEnchantment(Enchantment.getById(34), 1);
        is.addUnsafeEnchantment(Enchantment.getById(0), 3);
        kit.add(is);

        is = new ItemStack(304);
        is.addUnsafeEnchantment(Enchantment.getById(34), 1);
        is.addUnsafeEnchantment(Enchantment.getById(0), 3);
        kit.add(is);

        is = new ItemStack(305);
        is.addUnsafeEnchantment(Enchantment.getById(34), 1);
        is.addUnsafeEnchantment(Enchantment.getById(0), 3);
        kit.add(is);
        

        kit.add(new ItemStack(368, 16, (short)0));
        kit.add(new ItemStack(364, 32, (short)0));
        kits.add(kit);
        //------------
        names.add("§a§lStammi");
        icons.add(new ItemStack(302));
        kit = new ArrayList<ItemStack>();
        
        is = new ItemStack(272);
        is.addUnsafeEnchantment(Enchantment.getById(34), 3);
        is.addUnsafeEnchantment(Enchantment.getById(16), 2);
        kit.add(is);

        is = new ItemStack(302);
        is.addUnsafeEnchantment(Enchantment.getById(34), 2);
        is.addUnsafeEnchantment(Enchantment.getById(0), 3);
        kit.add(is);

        is = new ItemStack(303);
        is.addUnsafeEnchantment(Enchantment.getById(34), 2);
        is.addUnsafeEnchantment(Enchantment.getById(0), 3);
        kit.add(is);

        is = new ItemStack(304);
        is.addUnsafeEnchantment(Enchantment.getById(34), 2);
        is.addUnsafeEnchantment(Enchantment.getById(0), 3);
        kit.add(is);

        is = new ItemStack(305);
        is.addUnsafeEnchantment(Enchantment.getById(34), 2);
        is.addUnsafeEnchantment(Enchantment.getById(0), 3);
        kit.add(is);

        kit.add(new ItemStack(368, 16, (short)0));
        kit.add(new ItemStack(364, 32, (short)0));
        kits.add(kit);
        //------------
        names.add("§e§lHero");
        icons.add(new ItemStack(306));
        kit = new ArrayList<ItemStack>();
        
        is = new ItemStack(267);
        is.addUnsafeEnchantment(Enchantment.getById(16), 3);
        is.addUnsafeEnchantment(Enchantment.getById(34), 3);
        kit.add(is);

        is = new ItemStack(306);
        is.addUnsafeEnchantment(Enchantment.getById(34), 2);
        is.addUnsafeEnchantment(Enchantment.getById(0), 3);
        kit.add(is);

        is = new ItemStack(307);
        is.addUnsafeEnchantment(Enchantment.getById(34), 2);
        is.addUnsafeEnchantment(Enchantment.getById(0), 3);
        kit.add(is);

        is = new ItemStack(304);
        is.addUnsafeEnchantment(Enchantment.getById(34), 2);
        is.addUnsafeEnchantment(Enchantment.getById(0), 3);
        kit.add(is);

        is = new ItemStack(305);
        is.addUnsafeEnchantment(Enchantment.getById(34), 2);
        is.addUnsafeEnchantment(Enchantment.getById(0), 3);
        kit.add(is);

        kit.add(new ItemStack(368, 16, (short)0));
        kit.add(new ItemStack(364, 32, (short)0));
        kits.add(kit);
        //------------
        names.add("§3§lSenpai");
        icons.add(new ItemStack(310));
        kit = new ArrayList<ItemStack>();
        
        is = new ItemStack(276);
        is.addUnsafeEnchantment(Enchantment.getById(34), 3);
        is.addUnsafeEnchantment(Enchantment.getById(16), 3);
        kit.add(is);

        is = new ItemStack(310);
        is.addUnsafeEnchantment(Enchantment.getById(34), 1);
        is.addUnsafeEnchantment(Enchantment.getById(0), 1);
        kit.add(is);

        is = new ItemStack(307);
        is.addUnsafeEnchantment(Enchantment.getById(34), 2);
        is.addUnsafeEnchantment(Enchantment.getById(0), 3);
        kit.add(is);

        is = new ItemStack(308);
        is.addUnsafeEnchantment(Enchantment.getById(34), 2);
        is.addUnsafeEnchantment(Enchantment.getById(0), 3);
        kit.add(is);

        is = new ItemStack(313);
        is.addUnsafeEnchantment(Enchantment.getById(34), 1);
        is.addUnsafeEnchantment(Enchantment.getById(0), 1);
        kit.add(is);

        kit.add(new ItemStack(322, 1, (short)0));
        kit.add(new ItemStack(368, 16, (short)0));
        kit.add(new ItemStack(364, 32, (short)0));
        kits.add(kit);
        //------------
        names.add("§d§lPromo");
        icons.add(new ItemStack(314));
        kit = new ArrayList<ItemStack>();
        
        is = new ItemStack(276);
        is.addUnsafeEnchantment(Enchantment.getById(34), 3);
        is.addUnsafeEnchantment(Enchantment.getById(16), 3);
        kit.add(is);

        is = new ItemStack(310);
        is.addUnsafeEnchantment(Enchantment.getById(34), 1);
        is.addUnsafeEnchantment(Enchantment.getById(0), 1);
        kit.add(is);

        is = new ItemStack(307);
        is.addUnsafeEnchantment(Enchantment.getById(34), 4);
        is.addUnsafeEnchantment(Enchantment.getById(0), 4);
        kit.add(is);

        is = new ItemStack(308);
        is.addUnsafeEnchantment(Enchantment.getById(34), 4);
        is.addUnsafeEnchantment(Enchantment.getById(0), 4);
        kit.add(is);

        is = new ItemStack(313);
        is.addUnsafeEnchantment(Enchantment.getById(34), 1);
        is.addUnsafeEnchantment(Enchantment.getById(0), 1);
        kit.add(is);

        kit.add(new ItemStack(322, 3, (short)0));
        kit.add(new ItemStack(368, 16, (short)0));
        kit.add(new ItemStack(364, 32, (short)0));
        kits.add(kit);
        //------------
        
        int kitCount = names.size();
        String[] titles = new String[kitCount];
        for (int i = 0; i < kitCount; i++) {
            titles[i] = names.get(i);
        }

        Gui.open(p, new Gui(titles, kitCount) {
        
            @Override
            public void onInit(DynamicObject args) {
                setBackgroundInAllPagesToAir();

                for (int i = 0; i < kitCount; i++) {
                    for (int j = 0; j < 9; j++) {
                        setItemAt(j, 0, i, new ItemStack(160, 1, (short)15), "§r");
                        setItemAt(j, 1, i, new ItemStack(160, 1, (short)14), "§r");
                    }

                    setItemAt(4, 0, i, icons.get(i), "§7" + names.get(i) + " §f(§aKit abholen§f)");

                    ArrayList<ItemStack> items = kits.get(i);
                    int itemCount = items.size();
                    for (int j = 0; j < itemCount; j++) {
                        setItemAt(j, 2, i, items.get(j));
                    }
                }
            }
        
            @Override
            public void onDownBarClick(DynamicObject args) {
                
            }
        
            @Override
            public void onClose(DynamicObject args) {
                
            }
        
            @Override
            public DynamicObject onClick(DynamicObject args) {
                int slotX = args.getInt(Field.PLAYER_GUI_SLOT_POS_X);
                int slotY = args.getInt(Field.PLAYER_GUI_SLOT_POS_Y);
                int page = args.getInt(Field.PLAYER_GUI_PAGE);

                if (isInKnockIt(p)) {
                    sendMessage(p, "§cDu kannst diesen Befehl hier nicht nutzen.");
                } else {
                    int lastUsed = UserData.getInt(p, UserValue.kitUsed);
                    int unixTime = (int) (System.currentTimeMillis() / 1000L);

                    if ((unixTime - lastUsed) > 10 * 60 || isAdmin(p)) {
                        if (slotX == 4 && slotY == 0) {
                            if (page == 0) {
                                ArrayList<ItemStack> kit = kits.get(0);

                                for (ItemStack is : kit) {
                                    giveItemToPlayer(p, is);
                                }
                                sendMessage(p, "§aDu hast das Kit erfolgreich abgeholt.");
                                sendSuccessSound(p);
                                UserData.set(p, UserValue.kitUsed, unixTime);
                                p.closeInventory();
                            } else if (page == 1 && isStammi(p)) {
                                ArrayList<ItemStack> kit = kits.get(1);

                                for (ItemStack is : kit) {
                                    giveItemToPlayer(p, is);
                                }
                                sendMessage(p, "§aDu hast das Kit erfolgreich abgeholt.");
                                sendSuccessSound(p);
                                UserData.set(p, UserValue.kitUsed, unixTime);
                                p.closeInventory();
                            } else if (page == 2 && isHero(p)) {
                                ArrayList<ItemStack> kit = kits.get(2);

                                for (ItemStack is : kit) {
                                    giveItemToPlayer(p, is);
                                }
                                sendMessage(p, "§aDu hast das Kit erfolgreich abgeholt.");
                                sendSuccessSound(p);
                                UserData.set(p, UserValue.kitUsed, unixTime);
                                p.closeInventory();
                            } else if (page == 3 && isSenpai(p)) {
                                ArrayList<ItemStack> kit = kits.get(3);

                                for (ItemStack is : kit) {
                                    giveItemToPlayer(p, is);
                                }
                                sendMessage(p, "§aDu hast das Kit erfolgreich abgeholt.");
                                sendSuccessSound(p);
                                UserData.set(p, UserValue.kitUsed, unixTime);
                                p.closeInventory();
                            } else if (page == 4 && isPromo(p)) {
                                ArrayList<ItemStack> kit = kits.get(4);

                                for (ItemStack is : kit) {
                                    giveItemToPlayer(p, is);
                                }
                                sendMessage(p, "§aDu hast das Kit erfolgreich abgeholt.");
                                sendSuccessSound(p);
                                UserData.set(p, UserValue.kitUsed, unixTime);
                                p.closeInventory();
                            } else {
                                sendMessage(p, "§cDu kannst dir dieses Kit nicht abholen.");
                                sendErrorSound(p);
                            }
                        }
                    } else {
                        int nextUse = 10 * 60 - (unixTime - lastUsed); // time left in seconds
                        sendMessage(p, "§cDu kannst diesen Befehl erst in " + (nextUse / 60) + "min" + " wieder benutzen.");
                    }
                }
                return new DynamicObject();
            }
        }, 0);
    }

    @Override
    public String getDescription(Player p) {
        return "Ermöglicht das Auswählen von Kits.";
    }
}