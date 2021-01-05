package de.basicbit.system.minecraft.crafting.recipes;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import de.basicbit.system.minecraft.crafting.CraftingSystem;
import de.basicbit.system.minecraft.listeners.BackpackListener;

@SuppressWarnings("deprecation")
public class BackPackBig extends ShapedRecipe {

    public BackPackBig() {
        super(get());
        shape(
            "SLS",
            "SGS",
            "SLS"
        );

        CraftingSystem.set(this, 'S', new ItemStack(287));
        CraftingSystem.set(this, 'L', new ItemStack(334));
        CraftingSystem.set(this, 'G', new ItemStack(41));
    }

    public static ItemStack get() {
        ItemStack is = new ItemStack(334, 1, (short) 0);
        Bukkit.getUnsafe().modifyItemStack(is, "{BackpackInventory:" + BackpackListener.emptyBackpackContent + "}");
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("§r§7§r§6Großer Rucksack");
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        is.setItemMeta(im);
        is.addUnsafeEnchantment(Enchantment.OXYGEN, 1);
        return is;
    }
}