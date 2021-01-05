package de.basicbit.system.minecraft.crafting.recipes.armor.gold;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import de.basicbit.system.minecraft.crafting.CraftingSystem;
import de.basicbit.system.minecraft.crafting.recipes.SuperCactus;

@SuppressWarnings("all")
public class CactusBoots extends ShapedRecipe {

    public CactusBoots() {
        super(get());
        shape(
            "   ",
            "C C",
            "C C"
        );

        CraftingSystem.set(this, 'C', SuperCactus.get(1));
    }

    public static ItemStack get() {
        ItemStack is = new ItemStack(Material.LEATHER_BOOTS, 1);
        LeatherArmorMeta lam = (LeatherArmorMeta) is.getItemMeta();
        lam.setColor(Color.fromRGB(50, 200, 50));
        lam.setDisplayName("§aKaktusSchuhe");
        is.setItemMeta(lam);
        is.addUnsafeEnchantment(Enchantment.THORNS, 6);
        is.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        return is;
    }
}