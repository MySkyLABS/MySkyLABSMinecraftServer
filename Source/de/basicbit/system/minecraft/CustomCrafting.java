package de.basicbit.system.minecraft;

/*
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import com.google.common.hash.Hashing;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import de.basicbit.system.minecraft.dynamic.DynamicObject;
import de.basicbit.system.minecraft.gui.Gui;
import net.minecraft.server.v1_8_R1.NBTTagCompound;

@SuppressWarnings("deprecation")
*/

public class CustomCrafting extends Listener {

    /*public static void openCraftingGuide(Player p) {
        int size = recipes.size();
        
        Gui.open(p, new Gui("§5§lRezepte", size) {

            @Override
            public void onInit(DynamicObject args) {
                for (int i = 0; i < size; i++) {
                    ShapedRecipe recipe = recipes.get(i);
                    
                    setItemAt(6, 2, i, recipe.getResult());
                }
            }

            @Override
            public DynamicObject onClick(DynamicObject args) {
                return new DynamicObject();
            }

        }, 0);
    }

    public static void init() {
        ListenerSystem.register(new afting());

        ShapedRecipe sr = new ShapedRecipe(getSuperLapis(1));
        sr.shape("000", "000", "000");
        sr.setIngredient('0', new MaterialData(Material.getMaterial(22), (byte) 0));
        addRecipe(sr);

        ItemStack is = new ItemStack(334, 1, (short) 0);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("§r§7§r§6Großer ");
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        is.setItemMeta(im);
        is.addUnsafeEnchantment(Enchantment.OXYGEN, 1);
        sr = new ShapedRecipe(is);
        sr.shape("SLS", "SGS", "SLS");
        sr.setIngredient('G', new MaterialData(Material.getMaterial(41), (byte) 0));
        sr.setIngredient('L', new MaterialData(Material.getMaterial(334), (byte) 0));
        sr.setIngredient('S', new MaterialData(Material.getMaterial(287), (byte) 0));
        addRecipe(sr);

        is = new ItemStack(415, 1, (short) 0);
        im = is.getItemMeta();
        im.setDisplayName("§r§6§r§6Kleiner ");
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        is.setItemMeta(im);
        is.addUnsafeEnchantment(Enchantment.OXYGEN, 1);
        sr = new ShapedRecipe(is);
        sr.shape("SLS", "SGS", "SLS");
        sr.setIngredient('G', new MaterialData(Material.getMaterial(266), (byte) 0));
        sr.setIngredient('L', new MaterialData(Material.getMaterial(334), (byte) 0));
        sr.setIngredient('S', new MaterialData(Material.getMaterial(287), (byte) 0));
        addRecipe(sr);
/*
        is = new ItemStack(Material.IRON_AXE, 1);
        im = is.getItemMeta();
        im.setDisplayName("§r§a§r§b§lCrepper's Magische-Axt");
        is.setItemMeta(im);
        is.addUnsafeEnchantment(Enchantment.getById(34), 3);
        is.addUnsafeEnchantment(Enchantment.getById(32), 3);
        sr = new ShapedRecipe(is);
        sr.shape("", " L ", " G ");
        sr.setIngredient('L', new MaterialData(Material.getMaterial(258), (byte) 0));
        //sr.setIngredient('G', getSuperIron(1));
        
        

        initGoldBlockTools();

        
         * is = new ItemStack(348, 1, (short) 0); im = is.getItemMeta();
         * im.setDisplayName("§r§8§r§6§lTreibstoff");
         * im.addItemFlags(ItemFlag.HIDE_ENCHANTS); is.setItemMeta(im);
         * is.addUnsafeEnchantment(Enchantment.OXYGEN, 1); sr = new ShapedRecipe(is);
         * sr.shape(" C ", "CRC", " C "); sr.setIngredient('C', new
         * MaterialData(Material.getMaterial(263), (byte) 0)); sr.setIngredient('R', new
         * MaterialData(Material.getMaterial(331), (byte) 0)); Bukkit.addRecipe(sr);
         
    }

    public static void initGoldBlockTools() {
        ItemStack is = new ItemStack(283, 1, (short) 0);
        ItemMeta im = is.getItemMeta();
        im.spigot().setUnbreakable(true);
        is.setItemMeta(im);
        ShapedRecipe sr = new ShapedRecipe(is);
        sr.shape(" G ", " G ", " S ");
        sr.setIngredient('G', new MaterialData(Material.getMaterial(41), (byte) 0));
        sr.setIngredient('S', new MaterialData(Material.getMaterial(280), (byte) 0));
        addRecipe(sr);

        is = new ItemStack(285, 1, (short) 0);
        im = is.getItemMeta();
        im.spigot().setUnbreakable(true);
        is.setItemMeta(im);
        sr = new ShapedRecipe(is);
        sr.shape("GGG", " S ", " S ");
        sr.setIngredient('G', new MaterialData(Material.getMaterial(41), (byte) 0));
        sr.setIngredient('S', new MaterialData(Material.getMaterial(280), (byte) 0));
        addRecipe(sr);

        is = new ItemStack(294, 1, (short) 0);
        im = is.getItemMeta();
        im.spigot().setUnbreakable(true);
        is.setItemMeta(im);
        sr = new ShapedRecipe(is);
        sr.shape("GG ", " S ", " S ");
        sr.setIngredient('G', new MaterialData(Material.getMaterial(41), (byte) 0));
        sr.setIngredient('S', new MaterialData(Material.getMaterial(280), (byte) 0));
        addRecipe(sr);

        is = new ItemStack(284, 1, (short) 0);
        im = is.getItemMeta();
        im.spigot().setUnbreakable(true);
        is.setItemMeta(im);
        sr = new ShapedRecipe(is);
        sr.shape(" G ", " S ", " S ");
        sr.setIngredient('G', new MaterialData(Material.getMaterial(41), (byte) 0));
        sr.setIngredient('S', new MaterialData(Material.getMaterial(280), (byte) 0));
        addRecipe(sr);

        is = new ItemStack(284, 1, (short) 0);
        im = is.getItemMeta();
        im.spigot().setUnbreakable(true);
        is.setItemMeta(im);
        sr = new ShapedRecipe(is);
        sr.shape("G  ", "S  ", "S  ");
        sr.setIngredient('G', new MaterialData(Material.getMaterial(41), (byte) 0));
        sr.setIngredient('S', new MaterialData(Material.getMaterial(280), (byte) 0));
        addRecipe(sr);

        is = new ItemStack(284, 1, (short) 0);
        im = is.getItemMeta();
        im.spigot().setUnbreakable(true);
        is.setItemMeta(im);
        sr = new ShapedRecipe(is);
        sr.shape("  G", "  S", "  S");
        sr.setIngredient('G', new MaterialData(Material.getMaterial(41), (byte) 0));
        sr.setIngredient('S', new MaterialData(Material.getMaterial(280), (byte) 0));
        addRecipe(sr);

        is = new ItemStack(286, 1, (short) 0);
        im = is.getItemMeta();
        im.spigot().setUnbreakable(true);
        is.setItemMeta(im);
        sr = new ShapedRecipe(is);
        sr.shape("GG ", "GS ", " S ");
        sr.setIngredient('G', new MaterialData(Material.getMaterial(41), (byte) 0));
        sr.setIngredient('S', new MaterialData(Material.getMaterial(280), (byte) 0));
        addRecipe(sr);

        is = new ItemStack(286, 1, (short) 0);
        im = is.getItemMeta();
        im.spigot().setUnbreakable(true);
        is.setItemMeta(im);
        sr = new ShapedRecipe(is);
        sr.shape(" GG", " SG", " S ");
        sr.setIngredient('G', new MaterialData(Material.getMaterial(41), (byte) 0));
        sr.setIngredient('S', new MaterialData(Material.getMaterial(280), (byte) 0));
        addRecipe(sr);

        is = new ItemStack(314, 1, (short) 0);
        im = is.getItemMeta();
        im.spigot().setUnbreakable(true);
        is.setItemMeta(im);
        sr = new ShapedRecipe(is);
        sr.shape("GGG", "G G", "   ");
        sr.setIngredient('G', new MaterialData(Material.getMaterial(41), (byte) 0));
        addRecipe(sr);

        is = new ItemStack(314, 1, (short) 0);
        im = is.getItemMeta();
        im.spigot().setUnbreakable(true);
        is.setItemMeta(im);
        sr = new ShapedRecipe(is);
        sr.shape("   ", "GGG", "G G");
        sr.setIngredient('G', new MaterialData(Material.getMaterial(41), (byte) 0));
        addRecipe(sr);

        is = new ItemStack(315, 1, (short) 0);
        im = is.getItemMeta();
        im.spigot().setUnbreakable(true);
        is.setItemMeta(im);
        sr = new ShapedRecipe(is);
        sr.shape("G G", "GGG", "GGG");
        sr.setIngredient('G', new MaterialData(Material.getMaterial(41), (byte) 0));
        addRecipe(sr);

        is = new ItemStack(316, 1, (short) 0);
        im = is.getItemMeta();
        im.spigot().setUnbreakable(true);
        is.setItemMeta(im);
        sr = new ShapedRecipe(is);
        sr.shape("GGG", "G G", "G G");
        sr.setIngredient('G', new MaterialData(Material.getMaterial(41), (byte) 0));
        addRecipe(sr);

        is = new ItemStack(317, 1, (short) 0);
        im = is.getItemMeta();
        im.spigot().setUnbreakable(true);
        is.setItemMeta(im);
        sr = new ShapedRecipe(is);
        sr.shape("   ", "G G", "G G");
        sr.setIngredient('G', new MaterialData(Material.getMaterial(41), (byte) 0));
        addRecipe(sr);

        is = new ItemStack(317, 1, (short) 0);
        im = is.getItemMeta();
        im.spigot().setUnbreakable(true);
        is.setItemMeta(im);
        sr = new ShapedRecipe(is);
        sr.shape("G G", "G G", "   ");
        sr.setIngredient('G', new MaterialData(Material.getMaterial(41), (byte) 0));
        addRecipe(sr);
    }
    
    public static ItemStack getSuperLapis(int count) {
        ItemStack is = new ItemStack(351, 1, (short) 4);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("§r§9SuperLapis");
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        is.setItemMeta(im);
        is.addUnsafeEnchantment(Enchantment.ARROW_FIRE, 1);
        return is;
    }
    
    public static ItemStack getSuperIron(int count) {
        ItemStack is = new ItemStack(265, 1, (short) 4);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("§r§9SuperEisen");
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        is.setItemMeta(im);
        is.addUnsafeEnchantment(Enchantment.ARROW_FIRE, 1);
        return is;
    }*/

}