package de.basicbit.system.minecraft.crafting.recipes;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import de.basicbit.system.minecraft.crafting.CraftingSystem;

@SuppressWarnings("all")
public class SuperRedstone extends ShapedRecipe {
    
    public SuperRedstone() {
        super(get(1));
        shape(
            "000",
            "000",
            "000"
        );
        
        CraftingSystem.set(this, '0', new ItemStack(152, 1));
    }

    public static ItemStack get(int count) {
        ItemStack is = new ItemStack(331, count, (short) 0);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("§r§cSuperRedstone");
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        is.setItemMeta(im);
        is.addUnsafeEnchantment(Enchantment.ARROW_FIRE, 1);
        return is;
    }
}