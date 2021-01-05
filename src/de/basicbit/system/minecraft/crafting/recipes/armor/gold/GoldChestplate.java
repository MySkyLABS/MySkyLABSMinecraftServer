package de.basicbit.system.minecraft.crafting.recipes.armor.gold;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import de.basicbit.system.minecraft.crafting.CraftingSystem;

@SuppressWarnings("all")
public class GoldChestplate extends ShapedRecipe {

    public GoldChestplate() {
        super(get());
        shape(
            "G G",
            "GGG",
            "GGG"
        );

        CraftingSystem.set(this, 'G', new ItemStack(41));
    }

    public static ItemStack get() {
        ItemStack is = new ItemStack(315, 1, (short) 0);
        ItemMeta im = is.getItemMeta();
        im.spigot().setUnbreakable(true);
        is.setItemMeta(im);
        return is;
    }
}