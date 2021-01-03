package de.basicbit.system.minecraft.crafting.recipes.tools.gold;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import de.basicbit.system.minecraft.crafting.CraftingSystem;

@SuppressWarnings("all")
public class GoldAxe extends ShapedRecipe {

    public GoldAxe() {
        super(get());
        shape(
            "GG ", 
            "GS ", 
            " S "
        );

        CraftingSystem.set(this, 'G', new ItemStack(41));
        CraftingSystem.set(this, 'S', new ItemStack(280));
    }

    public static ItemStack get() {
        ItemStack is = new ItemStack(286, 1, (short) 0);
        ItemMeta im = is.getItemMeta();
        im.spigot().setUnbreakable(true);
        is.setItemMeta(im);
        return is;
    }
}