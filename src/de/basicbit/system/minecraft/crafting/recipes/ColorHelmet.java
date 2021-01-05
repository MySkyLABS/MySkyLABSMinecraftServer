package de.basicbit.system.minecraft.crafting.recipes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import de.basicbit.system.minecraft.crafting.CraftingSystem;
@SuppressWarnings("deprecation")
public class ColorHelmet extends ShapedRecipe {

    public ColorHelmet() {
        super(get());
        shape("GCB", "YHP", "R01");

        CraftingSystem.set(this, 'G', new ItemStack(35, 1, (short) 13));
        CraftingSystem.set(this, 'C', new ItemStack(35, 1, (short) 9));
        CraftingSystem.set(this, 'B', new ItemStack(35, 1, (short) 11));
        CraftingSystem.set(this, 'Y', new ItemStack(35, 1, (short) 4));
        CraftingSystem.set(this, 'H', new ItemStack(298));
        CraftingSystem.set(this, 'P', new ItemStack(35, 1, (short) 10));
        CraftingSystem.set(this, 'R', new ItemStack(35, 1, (short) 14));
        CraftingSystem.set(this, '0', new ItemStack(35, 1, (short) 15));
        CraftingSystem.set(this, '1', new ItemStack(35, 1, (short) 0));
    }

    public static ItemStack get() {
        ItemStack is = new ItemStack(298);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("§r§l§r§f§lUltra Helm");
        List<String> lore = new ArrayList<>();
        lore.add("§r");
		lore.add("§f-----------------§6§l Effekt§r §f-----------------");
		lore.add("§eLässt dich auf farbigen Wolken laufen.");
        lore.add("§eDu kannst die Farbe ändern indem");
        lore.add("§edu das Item in die Hand nimmst");
		lore.add("§eund dann einmal schlägst.");
		lore.add("§r");
        im.setLore(lore);
        im.spigot().setUnbreakable(true);
        is.setItemMeta(im);
        is.addUnsafeEnchantment(Enchantment.DEPTH_STRIDER, 3);
        return is;
    }
    
}
