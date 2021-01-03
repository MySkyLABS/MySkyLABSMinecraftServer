package de.basicbit.system.minecraft.crafting.recipes;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import de.basicbit.system.minecraft.crafting.CraftingSystem;

@SuppressWarnings("all")
public class SuperCactus extends ShapedRecipe {
    
    public SuperCactus() {
        super(get(1));
        shape(
            "CCC",
            "CCC",
            "CCC"
        );
        
        CraftingSystem.set(this, 'C', new ItemStack(81, 64));
    }

    public static ItemStack get(int count) {
        ItemStack is = new ItemStack(81, count, (short) 0);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("§r§aSuperCactus");
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        is.setItemMeta(im);
        is.addUnsafeEnchantment(Enchantment.ARROW_FIRE, 1);
        return is;
    }
}
