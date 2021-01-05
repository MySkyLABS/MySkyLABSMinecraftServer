package de.basicbit.system.minecraft.gui;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.basicbit.system.minecraft.ListenerSystem;
import de.basicbit.system.minecraft.Utils;
import de.basicbit.system.minecraft.dynamic.DynamicObject;
import de.basicbit.system.minecraft.dynamic.Field;
import de.basicbit.system.minecraft.dynamic.FieldAndObject;
import de.basicbit.system.minecraft.dynamic.RunnableWithArgsAndResult;
import de.basicbit.system.minecraft.tasks.TaskManager;

@SuppressWarnings("deprecation")
public abstract class Gui extends Utils {

    private HashMap<Integer, ItemStack> backgrounds = new HashMap<Integer, ItemStack>();
    private InventoryWrapper content = new InventoryWrapper();
    private InventoryWrapper contentDownBar = new InventoryWrapper();
    private ArrayList<String> titles;
    private int size;

    public abstract void onInit(DynamicObject args);
    public abstract DynamicObject onClick(DynamicObject args);
    public void onDownBarClick(DynamicObject args) { }
    public void onClose(DynamicObject args) { }
    public void onOpened(DynamicObject args) { }

    public static void init() {
        ListenerSystem.register(new GuiListener());
    }

    public Gui(String title, int pages) {
        ArrayList<String> titleList = new ArrayList<String>();

        for (int i = 0; i < pages; i++) {
            titleList.add(title);
        }

        this.titles = titleList;
        this.size = pages * 45;
    }

    public Gui(String[] titles, int pages) {
        ArrayList<String> titleList = new ArrayList<String>();
        for (String title : titles) {
            titleList.add(title);
        }

        this.titles = titleList;
        this.size = pages * 45;
    }

    public final int getSize() {
        return size;
    }

    public String getTitle(int page) {
        return titles.get(page);
    }

    public final void setBackground(int page, ItemStack is) {
        backgrounds.put(page, is);
    }

    public final void setBackgroundInAllPages(ItemStack is) {
        for (int i = 0; i < getPages(); i++) {
            setBackground(i, is);
        }
    }

    public final void setBackgroundInAllPagesToGlassColor(int colorMeta) {
        for (int i = 0; i < getPages(); i++) {
            setBackgroundToGlassColor(i, colorMeta);
        }
    }

    public final void setDownBarItemAt(int slot, ItemStack is) {
        contentDownBar.setItem(slot, is);
    }

    public final void setDownBarItemAt(int slot, ItemStack is, String name) {
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(name);
        is.setItemMeta(im);
        setDownBarItemAt(slot, is);
    }

    public final void setBackgroundInAllPagesToBarriers() {
        for (int i = 0; i < getPages(); i++) {
            setBackgroundToBarriers(i);
        }
    }

    public final void setBackgroundInAllPagesToAir() {
        for (int i = 0; i < getPages(); i++) {
            setBackgroundToAir(i);
        }
    }

    public final void setBackgroundToGlassColor(int page, int colorMeta) {
        setBackground(page, new ItemStack(160, 1, (short)colorMeta));
    }

    public final void setBackgroundToBarriers(int page) {
        setBackground(page, new ItemStack(166, 1, (short)0));
    }

    public final void setBackgroundToAir(int page) {
        setBackground(page, new ItemStack(0));
    }

    public final void setBackgroundToBarriers() {
        int pages = getPages();
        for (int i = 0; i < pages; i++) {
            setBackgroundToBarriers(i);
        }
    }

    public final ItemStack getBackground(int page) {
        if (!backgrounds.containsKey(page)) {
            ItemStack is = new ItemStack(160, 1, (short)15);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName("§r");
            is.setItemMeta(im);
            return is;
        }

        ItemStack is = backgrounds.get(page);
        try {
            ItemMeta im = is.getItemMeta();
            im.setDisplayName("§r");
            is.setItemMeta(im);
        } catch (Exception e) { }
        return is;
    }

    public final void setSize(int size) {
        this.size = size;
    }

    public final ItemStack getItemAt(int slot) {
        return content.getItem(slot);
    }

    public final ItemStack getItemAt(int x, int y) {
        return getItemAt(9 * y + x);
    }

    public final ItemStack getItemAt(int x, int y, int page) {
        return getItemAt(x, 45 * page + y);
    }

    public final void setItemAt(int slot, ItemStack is) {
        content.setItem(slot, is);
    }

    public final void setItemAt(int slot, ItemStack is, String name) {
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(name);
        is.setItemMeta(im);
        setItemAt(slot, is);
    }

    public final void setItemAt(int x, int y, ItemStack is) {
        content.setItem(9 * y + x, is);
    }


    public final void setItemAt(int x, int y, ItemStack is, String... texts) {
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(texts[0]);
        ArrayList<String> lore = new ArrayList<String>();

        for (int i = 1; i < texts.length; i++) {
            lore.add(texts[i]);
        }

        im.setLore(lore);
        is.setItemMeta(im);
        setItemAt(x, y, is);
    }

    public final int getPages() {
        return size % 45 == 0 ? size / 45 : size / 45 + 1;
    }

    public final void setItemAt(int x, int y, int page, ItemStack is) {
        content.setItem(x, 5 * page + y, is);
    }

    public final void setItemAt(int x, int y, int page, ItemStack is, String... texts) {
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(texts[0]);
        ArrayList<String> lore = new ArrayList<String>();

        for (int i = 1; i < texts.length; i++) {
            lore.add(texts[i]);
        }

        im.setLore(lore);
        is.setItemMeta(im);
        setItemAt(x, y, page, is);
    }

    public final static void open(Player p, Gui gui, int page) {
        TaskManager.runAsyncTask(new Runnable() {
        
            @Override
            public void run() {
                gui.onInit(new DynamicObject(new FieldAndObject(Field.PLAYER_MAIN, p)));
                
                TaskManager.runSyncTask("GuiOpenInv", new Runnable() {
                
                    @Override
                    public void run() {
                        p.openInventory(gui.createInventory(gui, page));

                        gui.onOpened(new DynamicObject(
                            new FieldAndObject(
                                Field.PLAYER_MAIN,
                                p
                            ),
                            new FieldAndObject(
                                Field.PLAYER_GUI_PAGE,
                                page
                            )
                        ));
                    }
                });
            }
        });
    }

    public final static void openYesNo(Player p, String title, Runnable yes, Runnable no) {
        Gui.open(p, new Gui(new String[] { title }, 1) {

            @Override
            public void onInit(DynamicObject args) {
                setBackgroundToGlassColor(0, 15);

                ItemStack is = new ItemStack(159, 1, (short)5);
                ItemMeta im = is.getItemMeta();
                im.setDisplayName("§2§lJa");
                is.setItemMeta(im);
                setItemAt(2, 2, is);

                is = new ItemStack(159, 1, (short)14);
                im = is.getItemMeta();
                im.setDisplayName("§c§lNein");
                is.setItemMeta(im);
                setItemAt(6, 2, is);
            }

            @Override
            public DynamicObject onClick(DynamicObject args) {
                int slotX = args.getInt(Field.PLAYER_GUI_SLOT_POS_X);
                int slotY = args.getInt(Field.PLAYER_GUI_SLOT_POS_Y);

                if (slotX == 2 && slotY == 2) {
                    yes.run();
                    return new DynamicObject();
                }

                if (slotX == 6 && slotY == 2) {
                    no.run();
                    return new DynamicObject();
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

    public final static void openSelectNumber(Player p, String title, RunnableWithArgsAndResult cancel, RunnableWithArgsAndResult confirm) {
        openSelectNumber(p, title, 0, cancel, confirm);
    }

    public final static void openSelectNumber(Player p, String title, int number, RunnableWithArgsAndResult cancel, RunnableWithArgsAndResult confirm) {
        if (number > 9999999 || number < 0) {
            number = 0;
        }

        final int finalNumber = number;

        String numberAsString = number + "";
        while (numberAsString.length() < 7) {
            numberAsString = "0" + numberAsString;
        }

        char[] numberAsCharArray = numberAsString.toCharArray();

        Gui.open(p, new Gui(new String[] { title }, 1) {

            @Override
            public void onInit(DynamicObject args) {
                setItemAt(2, 3, new ItemStack(166, 1), "§cAbbrechen");
                setItemAt(6, 3, new ItemStack(266, 1), "§aBestätigen");

                for (int x = 0; x < 7; x++) {
                    setItemAt(x + 1, 1, new ItemStack(371, Integer.parseInt(numberAsCharArray[x] + "")),
                        "§b§l" + numberAsCharArray[x],
                        "§r",
                        "§b§lLinksklick: §aErhöhen",
                        "§d§lRechtsklick: §aVerringern",
                        "§r"
                    );
                }
            }

            @Override
            public DynamicObject onClick(DynamicObject args) {
                Player p = args.getPlayer(Field.PLAYER_MAIN);
                int slotX = args.getInt(Field.PLAYER_GUI_SLOT_POS_X);
                int slotY = args.getInt(Field.PLAYER_GUI_SLOT_POS_Y);
                int click = args.getInt(Field.PLAYER_GUI_CLICKTYPE);
                
                if (slotX == 2 && slotY == 3) {
                    cancel.run(new DynamicObject(
                        new FieldAndObject(Field.PLAYER_MAIN, p)
                    ));
                } else if (slotX == 6 && slotY == 3) {
                    confirm.run(new DynamicObject(
                        new FieldAndObject(Field.PLAYER_MAIN, p),
                        new FieldAndObject(Field.PLAYER_GUI_NUMBER, finalNumber)
                    ));
                }

                if (slotY == 1 && slotX >= 1 && slotX <= 7) {
                    int x = slotX - 1;
                    int partOfNumber = Integer.parseInt(numberAsCharArray[x] + "");

                    if (click == Click.LEFT) {
                        partOfNumber++;
                    } else if (click == Click.RIGHT) {
                        partOfNumber--;
                    }

                    if (partOfNumber < 0) {
                        partOfNumber += 10;
                    }

                    partOfNumber %= 10;
                    numberAsCharArray[x] = (partOfNumber + "").toCharArray()[0];
                    openSelectNumber(p, title, Integer.parseInt(new String(numberAsCharArray)), cancel, confirm);
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

    private final Inventory createInventory(Gui gui, int page) {
        int pages = getPages();
        boolean hasDownBar = !(pages == 1 && !contentDownBar.hasItem());

        Inventory inv = Bukkit.createInventory(new GuiHolder(gui, page), hasDownBar ? 54 : 45, gui.titles.get(page) + (pages == 1 ? "" : "§8 [" + (page + 1) + "/" + pages + "]"));
        int offset = page * 45;

        for (int i = 0; i < 45; i++) {
            ItemStack is = content.getItem(offset + i);

            if (is == null || is.getTypeId() == 0) {
                is = getBackground(page);
            } 

            inv.setItem(i, is);
        }

        if (hasDownBar) {
            for (int i = 0; i < 9; i++) {
                ItemStack is = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)15);
                ItemMeta im = is.getItemMeta();
                im.setDisplayName("§r");
                is.setItemMeta(im);
                inv.setItem(45 + i, is);

                is = contentDownBar.getItem(i);
                if (is != null) {
                    inv.setItem(45 + i, is);
                }
            }
    
            ItemStack is = new ItemStack(351, 1, (short)(page == 0 ? 8 : 9));
            ItemMeta im = is.getItemMeta();
            im.setDisplayName("§c§lZurück");
            is.setItemMeta(im);
            inv.setItem(45, is);
    
            is = new ItemStack(351, 1, (short)(page + 1 == pages ? 8 : 13));
            im = is.getItemMeta();
            im.setDisplayName("§a§lWeiter");
            is.setItemMeta(im);
            inv.setItem(53, is);
        }

        return inv;
    }
}