package de.basicbit.system.minecraft.crafting.recipes;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import de.basicbit.system.minecraft.crafting.CraftingSystem;

@SuppressWarnings("all")
public class PowerDust extends ShapedRecipe {
    
    public PowerDust() {
        super(get(16));
        shape(
            " R ",
            "RCR",
            " R "
        );
        
        CraftingSystem.set(this, 'R', SuperRedstone.get(1));
        CraftingSystem.set(this, 'C', SuperCoal.get(1));
    }

    public static ItemStack get(int count) {
        ItemStack is = new ItemStack(348, count, (short) 0);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("§r§6Treibstoff");
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        is.setItemMeta(im);
        is.addUnsafeEnchantment(Enchantment.ARROW_FIRE, 1);
        return is;
    }
}