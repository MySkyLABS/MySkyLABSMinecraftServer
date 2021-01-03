package de.basicbit.system.minecraft.crafting.recipes;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import de.basicbit.system.minecraft.crafting.CraftingSystem;

@SuppressWarnings("deprecation")
public class SuperLapis extends ShapedRecipe {

    public SuperLapis() {
        super(get(1));
        shape(
            "000",
            "000",
            "000"
        );
        
        CraftingSystem.set(this, '0', new ItemStack(22, 1));
    }

    public static ItemStack get(int count) {
        ItemStack is = new ItemStack(351, count, (short) 4);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("§r§9SuperLapis");
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        is.setItemMeta(im);
        is.addUnsafeEnchantment(Enchantment.ARROW_FIRE, 1);
        return is;
    }
}