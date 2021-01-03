package de.basicbit.system.minecraft.crafting.recipes;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import de.basicbit.system.minecraft.crafting.CraftingSystem;

@SuppressWarnings("all")
public class SuperIron extends ShapedRecipe {
    
    public SuperIron() {
        super(get(1));
        shape(
            "000",
            "000",
            "000"
        );
        
        CraftingSystem.set(this, '0', new ItemStack(42, 1));
    }

    public static ItemStack get(int count) {
        ItemStack is = new ItemStack(265, count, (short) 0);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("§r§7SuperEisen");
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        is.setItemMeta(im);
        is.addUnsafeEnchantment(Enchantment.ARROW_FIRE, 1);
        return is;
    }
}