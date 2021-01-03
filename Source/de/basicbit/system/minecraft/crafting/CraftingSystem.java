package de.basicbit.system.minecraft.crafting;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import de.basicbit.system.ClassFinder;
import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.ListenerSystem;
import de.basicbit.system.minecraft.Utils;
import de.basicbit.system.minecraft.crafting.recipes.SuperLapis;
import de.basicbit.system.minecraft.dynamic.DynamicObject;
import de.basicbit.system.minecraft.gui.Gui;
import de.basicbit.system.minecraft.tasks.TaskManager;

@SuppressWarnings("all")
public class CraftingSystem extends Listener {

    private static ArrayList<ShapedRecipe> recipes = new ArrayList<ShapedRecipe>();

    public static void init() {
        ListenerSystem.register(new CraftingSystem());

        try {
            ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
            classes.addAll(ClassFinder.findClasses("de.basicbit.system.minecraft.crafting.recipes"));

            for (Class<?> c : classes) {
                if (!c.getName().contains("$")) {
                    try {
                        addRecipe((ShapedRecipe) c.getConstructors()[0].newInstance());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void openGuide(Player p) {
        HashSet<ItemStack> items = new HashSet<ItemStack>();

        for (ShapedRecipe recipe : recipes) {
            items.add(recipe.getResult());
        }

        int size = items.size();
        List<ItemStack> itemList = items.stream().sorted(Comparator.comparing(ItemStack::getTypeId)).collect(Collectors.toList());
        Gui.open(p, new Gui("§0§lCraftingGuide", size % 45 == 0 ? size / 45 : size / 45 + 1) {

            @Override
            public void onInit(DynamicObject args) {
                int i = 0;
                for (ItemStack is : itemList) {
                    setItemAt(i, is);
                    i++;
                }
            }

            @Override
            public DynamicObject onClick(DynamicObject args) {
                int slot = args.getInt(de.basicbit.system.minecraft.dynamic.Field.PLAYER_GUI_SLOT_POS);

                if (slot < size) {
                    openGuide(p, itemList.get(slot));
                }

                return new DynamicObject();
            }
            
        }, 0);
    }

    public static void openGuide(Player p, ItemStack is) {
        HashSet<ShapedRecipe> shapedRecipes = new HashSet<ShapedRecipe>();

        for (ShapedRecipe recipe : recipes) {
            if (equalsItemStack(recipe.getResult(), is)) {
                shapedRecipes.add(recipe);
            }
        }

        int size = shapedRecipes.size();
        Gui.open(p, new Gui("§0§lCraftingGuide", size) {

            @Override
            public void onInit(DynamicObject args) {
                setBackgroundInAllPagesToAir();

                for (int i = 0; i < size * 45; i++) {
                    setItemAt(i, new ItemStack(160, 1, (short) 15), "§r");
                }

                setDownBarItemAt(4, new ItemStack(166), "§c§lZurück");

                int i = 0;
                for (ShapedRecipe recipe : shapedRecipes) {
                    setItemAt(7, 2, i, recipe.getResult());
                    setItemAt(5, 2, i, new ItemStack(160, 1, (short) 5), "§a§l--§a»");

                    Map<Character, ItemStack> map = recipe.getIngredientMap();
                    for (int j = 0; j < 9; j++) {
                        int x = j % 3;
                        int y = j / 3;

                        setItemAt(x + 1, y + 1, i, map.get(recipe.getShape()[y].charAt(x)));
                    }

                    i++;
                }
            }

            @Override
            public DynamicObject onClick(DynamicObject args) {
                int x = args.getInt(de.basicbit.system.minecraft.dynamic.Field.PLAYER_GUI_SLOT_POS_X);
                int y = args.getInt(de.basicbit.system.minecraft.dynamic.Field.PLAYER_GUI_SLOT_POS_Y);

                if (x == 7 && y == 2 && isModerator(p)) {
                    giveItemToPlayer(p, is);
                }

                return new DynamicObject();
            }

            @Override
            public void onDownBarClick(DynamicObject args) {
                int slot = args.getInt(de.basicbit.system.minecraft.dynamic.Field.PLAYER_GUI_SLOT_POS);

                if (slot == 4) {
                    openGuide(p);
                }
            }
            
        }, 0);
    }

    public static void set(ShapedRecipe shapedRecipe, char character, ItemStack is) {
        try {
            Field field = ShapedRecipe.class.getDeclaredField("ingredients");
            field.setAccessible(true);
            Map<Character, ItemStack> map = (Map<Character, ItemStack>) field.get(shapedRecipe);
            map.put(character, is);
            field.set(shapedRecipe, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addRecipe(ShapedRecipe recipe) {
        String[] shape = recipe.getShape();
        char[] row0 = shape[0].toCharArray();
        char[] row1 = shape[1].toCharArray();
        char[] row2 = shape[2].toCharArray();

        Utils.log("Adding recipe: " + recipe.getClass().getSimpleName());
        Utils.log("");
        Utils.log("        +-+-+-+");
        Utils.log("        |" + row0[0] + "|" + row0[1] + "|" + row0[2] + "|");
        Utils.log("        +-+-+-+");
        Utils.log(" Shape: |" + row1[0] + "|" + row1[1] + "|" + row1[2] + "|");
        Utils.log("        +-+-+-+");
        Utils.log("        |" + row2[0] + "|" + row2[1] + "|" + row2[2] + "|");
        Utils.log("        +-----+");
        Utils.log("");
        Bukkit.addRecipe(recipe);
        recipes.add(recipe);
    }

    @EventHandler
    public static void onCraft(PrepareItemCraftEvent e) {
        Recipe recipe = e.getRecipe();

        if (recipe instanceof ShapedRecipe) {
            ShapedRecipe shapedRecipe = (ShapedRecipe) recipe;

            for (ShapedRecipe shapedRecipe2 : recipes) {
                CraftingInventory craftingInventory = e.getInventory();

                if (equalsItemStack(shapedRecipe.getResult(), shapedRecipe2.getResult())) {
                    boolean breakForLoop = false;
                    String[] shape = shapedRecipe2.getShape();
                    Map<Character, ItemStack> items = shapedRecipe2.getIngredientMap();

                    for (int i = 0; i < 9; i++) {
                        char c = shape[i / 3].toCharArray()[i % 3];
                        if (equalsItemStack(items.get(c), craftingInventory.getItem(i + 1))) {
                            if (i == 8) {
                                breakForLoop = true;
                                break;
                            }
                        } else {
                            break;
                        }
                    }

                    if (breakForLoop) {
                        break;
                    }

                    craftingInventory.setResult(null);

                    break;
                }
            }
        }
    }

    public static boolean equalsItemStack(ItemStack is1, ItemStack is2) {
        if (is1 == null && is2 == null) {
            return true;
        }

        if (is1 == null || is2 == null || is1.getTypeId() != is2.getTypeId() || is1.getDurability() != is2.getDurability()) {
            return false;
        }

        net.minecraft.server.v1_8_R1.ItemStack nmsIs1 = CraftItemStack.asNMSCopy(is1);
        String tag1 = nmsIs1.hasTag() ? nmsIs1.getTag().toString() : "{}";

        net.minecraft.server.v1_8_R1.ItemStack nmsIs2 = CraftItemStack.asNMSCopy(is2);
        String tag2 = nmsIs2.hasTag() ? nmsIs2.getTag().toString() : "{}";

        return tag1.contentEquals(tag2);
    }
}