package de.basicbit.system.minecraft.cases;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import de.basicbit.system.GlobalValues;
import de.basicbit.system.minecraft.PerkSystem;
import de.basicbit.system.minecraft.Utils;

@SuppressWarnings("deprecation")
public enum CaseType {
	MAJO("§fMajo"), BLOCKS("§2Blöcke"), PERK("§3Perk"), LOTTO("§6Lotto"), TOOLS("§cAusrüstung"), SPECIAL("§5Spezial"), DEVITEMS("§b§lDevItems");

	private final String name;

	CaseType(final String t) {
		name = t;
	}

	public static CaseType getCaseTypeByTitle(String title) {

		for (CaseType type : values()) {
			if (type.name.contentEquals(title)) {
				return type;
			}
		}

		return null;
	}

	public ItemStack getKey() {
		final ItemStack is = new ItemStack(131, 1);
		final ItemMeta im = is.getItemMeta();
		im.setDisplayName("§eCase §7- " + name);
		is.setItemMeta(im);
		is.addUnsafeEnchantment(Enchantment.LUCK, 10);
		return is;
	}

	public String getTitle() {
		return name;
	}

	public ItemStack getRandomItem() {
		Random random = GlobalValues.random;
		final ArrayList<ItemStack> items = getItems();
		return items.get(random.nextInt(items.size()));
	}

	public ArrayList<ItemStack> getItems() {
		final ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		
		if (this == SPECIAL) {

			items.add(new ItemStack(322, 2, (short) 0));//GoldenApple
			items.add(new ItemStack(322, 1, (short) 1));//GoldenApple
			items.add(new ItemStack(145, 1, (short) 0));//Anvil
			items.add(new ItemStack(130, 1, (short) 0));//EnderChest
			items.add(new ItemStack(120, 1, (short) 0));//EndPortal
			items.add(new ItemStack(138, 1, (short) 0));//Beacon
			items.add(new ItemStack(121, 1, (short) 0));//EndStone
			items.add(new ItemStack(399, 1, (short) 0));//NetherStar
			items.add(new ItemStack(266, 5, (short) 0));//GoldIngot
			items.add(new ItemStack(52, 1, (short) 0));//MonsterSpawner
			items.add(new ItemStack(383, 1, (short) 50));//SpawnCreeper
			items.add(new ItemStack(383, 1, (short) 51));//SpawnSkeleton
			items.add(new ItemStack(383, 1, (short) 52));//SpawnSpider
			items.add(new ItemStack(383, 1, (short) 54));//SpawnZombie
			items.add(new ItemStack(383, 1, (short) 55));//SpawnSlime
			items.add(new ItemStack(383, 1, (short) 56));//SpawnSkeleton
			items.add(new ItemStack(383, 1, (short) 58));//SpawnEnderman
			items.add(new ItemStack(383, 1, (short) 59));//SpawnCaveSpider
			items.add(new ItemStack(383, 1, (short) 60));//SpawnSilverfish
			items.add(new ItemStack(383, 1, (short) 61));//SpawnBlaze
			items.add(new ItemStack(383, 1, (short) 62));//SpawnMagmaCube
			items.add(new ItemStack(383, 1, (short) 65));//SpawnBat
			items.add(new ItemStack(383, 1, (short) 66));//SpawnWitch
			items.add(new ItemStack(383, 1, (short) 67));//SpawnEndermite
			items.add(new ItemStack(383, 1, (short) 68));//SpawnGuardian
			items.add(new ItemStack(383, 1, (short) 90));//SpawnPig
			items.add(new ItemStack(383, 1, (short) 91));//SpawnSheep
			items.add(new ItemStack(383, 1, (short) 92));//SpawnCow
			items.add(new ItemStack(383, 1, (short) 93));//SpawnChicken
			items.add(new ItemStack(383, 1, (short) 94));//SpawnSquid
			items.add(new ItemStack(383, 1, (short) 95));//SpawnWolf
			items.add(new ItemStack(383, 1, (short) 96));//SpawnMooshroom
			items.add(new ItemStack(383, 1, (short) 98));//SpawnOcelot
			items.add(new ItemStack(383, 1, (short) 100));//SpawnHorse
			items.add(new ItemStack(383, 1, (short) 101));//SpawnRabbit
			items.add(new ItemStack(383, 1, (short) 120));//SpawnVillager

			int size = items.size() * 2;
			for (int i = 0; i < size; i++) {
				items.add(new ItemStack(371, i % 10 + 1, (short) 0));
			}

		} else if (this == MAJO) {

			for (int i = 0; i < 64; i++) {
				items.add(Utils.getMajoItemStack(i + 1));
			}

		} else if (this == PERK) {

			for (int i = 0; i < 50; i++) {
				items.add(new ItemStack(Material.GOLD_NUGGET, 1));
			}

			items.addAll(PerkSystem.getPerkItems());

		} else if (this == BLOCKS) {

			for (int i = 0; i < 64; i++) {
				items.add(new ItemStack(12, i + 1));
				items.add(new ItemStack(12, i + 1, (short) 1));
				items.add(new ItemStack(13, i + 1));
				items.add(new ItemStack(48, i + 1));
				items.add(new ItemStack(45, i + 1));
				items.add(new ItemStack(86, i + 1));
				items.add(new ItemStack(91, i + 1));
				items.add(new ItemStack(103, i + 1));
				items.add(new ItemStack(110, i + 1));
				items.add(new ItemStack(1, i + 1));
				items.add(new ItemStack(80, i + 1));
				items.add(new ItemStack(168, i + 1));
				items.add(new ItemStack(168, i + 1, (short) 1));
				items.add(new ItemStack(168, i + 1, (short) 2));
				items.add(new ItemStack(174, i + 1));
				items.add(new ItemStack(179, i + 1));
				items.add(new ItemStack(87, i + 1));
				items.add(new ItemStack(88, i + 1));
				items.add(new ItemStack(89, i + 1));
				items.add(new ItemStack(169, i + 1));
				items.add(new ItemStack(153, i + 1));
				items.add(new ItemStack(155, i + 1));
				items.add(new ItemStack(152, i + 1));

				for (int j = 0; j < 16; j++) {
					items.add(new ItemStack(159, i + 1, (short) j));
				}
			}

		} else if (this == LOTTO) {

			for (int i = 0; i < 64; i++) {
				items.add(new ItemStack(Material.GOLD_NUGGET, i + 1));
			}

			for (int i = 0; i < 10; i++) {
				items.add(new ItemStack(Material.GOLD_INGOT, i + 1));
			}

			for (int i = 0; i < 5; i++) {
				items.add(new ItemStack(Material.GOLD_BLOCK, 1));
			}
			
		} else if (this == TOOLS) {
			ItemStack is = new ItemStack(Material.DIAMOND_AXE, 1);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName("§r§i§r§c§lFoxi's Feuerschweif");
			ArrayList<String> l = new ArrayList<String>();
			l.add("§r");
			l.add("§f-----------------§6§l Effekt§r §f-----------------");
			l.add("§eErzeugt Feuerpartikel um dich herum.");
			l.add("§r");
			im.setLore(l);
			is.setItemMeta(im);
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 7);
			is.addUnsafeEnchantment(Enchantment.getById(34), 3); // Unbreaking
			is.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 4);
			is.addUnsafeEnchantment(Enchantment.KNOCKBACK, 3);
			items.add(is);
	
			is = new ItemStack(Material.DIAMOND_HELMET, 1);
			im = is.getItemMeta();
			im.setDisplayName("§c§lFoxi's Fell");
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
			is.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 6);
			is.addUnsafeEnchantment(Enchantment.getById(34), 3); // Unbreaking
			items.add(is);
	
			is = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
			im = is.getItemMeta();
			im.setDisplayName("§c§lFoxi's Fell");
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
			is.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 6);
			is.addUnsafeEnchantment(Enchantment.getById(34), 3); // Unbreaking
			items.add(is);
	
			is = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
			im = is.getItemMeta();
			im.setDisplayName("§c§lFoxi's Fell");
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
			is.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 6);
			is.addUnsafeEnchantment(Enchantment.getById(34), 3); // Unbreaking
			items.add(is);
	
			is = new ItemStack(Material.DIAMOND_BOOTS, 1);
			im = is.getItemMeta();
			im.setDisplayName("§c§lFoxi's Fell");
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
			is.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 6);
			is.addUnsafeEnchantment(Enchantment.getById(34), 3); // Unbreaking
			items.add(is);
	
			is = new ItemStack(Material.BOW, 1);
			im = is.getItemMeta();
			im.setDisplayName("§a§lBasic's Badass-Bow");
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 7);
			is.addUnsafeEnchantment(Enchantment.getById(34), 3); // Unbreaking
			is.addUnsafeEnchantment(Enchantment.ARROW_FIRE, 4);
			is.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
			items.add(is);
	
			is = new ItemStack(Material.IRON_HELMET, 1);
			im = is.getItemMeta();
			im.setDisplayName("§a§lBasic's Badass-Helm");
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
			is.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, 6);
			is.addUnsafeEnchantment(Enchantment.getById(34), 3); // Unbreaking
			items.add(is);
	
			is = new ItemStack(Material.IRON_CHESTPLATE, 1);
			im = is.getItemMeta();
			im.setDisplayName("§a§lBasic's Badass-Brust");
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
			is.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, 6);
			is.addUnsafeEnchantment(Enchantment.getById(34), 3); // Unbreaking
			items.add(is);
	
			is = new ItemStack(Material.IRON_LEGGINGS, 1);
			im = is.getItemMeta();
			im.setDisplayName("§a§lBasic's Badass-Jeans");
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
			is.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, 6);
			is.addUnsafeEnchantment(Enchantment.getById(34), 3); // Unbreaking
			items.add(is);
	
			is = new ItemStack(Material.IRON_BOOTS, 1);
			im = is.getItemMeta();
			im.setDisplayName("§a§lBasic's Badass-Boots");
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
			is.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, 6);
			is.addUnsafeEnchantment(Enchantment.getById(34), 3); // Unbreaking
			items.add(is);
	
			is = new ItemStack(Material.STONE_SWORD, 1);
			im = is.getItemMeta();
			im.setDisplayName("§r§0§r§8§lSchattendolch");
			l = new ArrayList<String>();
			l.add("§r");
			l.add("§f-----------------§6§l Effekt§r §f-----------------");
			l.add("§eMacht andere Spieler beim Angreifen blind.");
			l.add("§r");
			im.setLore(l);
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.getById(34), 1); // Unbreaking
			is.addUnsafeEnchantment(Enchantment.getById(35), 1);
			is.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 7);
			is.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
			items.add(is);
	
			is = new ItemStack(Material.STICK, 1);
			im = is.getItemMeta();
			im.setDisplayName("§9§lKnüppel");
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 5);
			is.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 5);
			items.add(is);
	
			is = new ItemStack(Material.IRON_PICKAXE, 1, (short) 200);
			im = is.getItemMeta();
			im.setDisplayName("§2§lDestroyer");
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.getById(32), 10);
			is.addUnsafeEnchantment(Enchantment.getById(35), 3);
			items.add(is);
	
			is = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
			LeatherArmorMeta lam = (LeatherArmorMeta) is.getItemMeta();
			lam.setDisplayName("§4§lKatzi's Schutz");
			lam.setColor(Color.fromRGB(200, 100, 100));
			lam.spigot().setUnbreakable(true);
			is.setItemMeta(lam);
			is.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 10);
			is.addUnsafeEnchantment(Enchantment.getById(34), 3); //Unbreaking
			items.add(is);
	
			is = new ItemStack(Material.FLINT_AND_STEEL, 1);
			im = is.getItemMeta();
			im.setDisplayName("§c§lBlockBuster's Zippo");
			im.spigot().setUnbreakable(true);
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.getById(20), 5);
			items.add(is);
	
			is = new ItemStack(Material.GOLD_HELMET, 1);
			im = is.getItemMeta();
			im.setDisplayName("§aElite's Arroganz");
			im.spigot().setUnbreakable(true);
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.getById(34), 3); // Unbreaking
			is.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 10);
			items.add(is);
	
			is = new ItemStack(Material.IRON_HOE, 1);
			im = is.getItemMeta();
			im.setDisplayName("§r§1§r§8§lFox's Todessense");
			l = new ArrayList<String>();
			l.add("§r");
			l.add("§f-----------------§6§l Effekt§r §f-----------------");
			l.add("§eVergiftet andere Spieler beim Angreifen.");
			l.add("§r");
			im.setLore(l);
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.getById(34), 3); // Unbreaking
			is.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 9);
			is.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
			items.add(is);
	
			is = new ItemStack(Material.IRON_HELMET, 1);
			im = is.getItemMeta();
			im.setDisplayName("§f§lGolemrüstung");
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.getById(34), 3); // Unbreaking
			is.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 10);
			is.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, 5);
			items.add(is);
	
			is = new ItemStack(Material.IRON_CHESTPLATE, 1);
			im = is.getItemMeta();
			im.setDisplayName("§f§lGolemrüstung");
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.getById(34), 3); // Unbreaking
			is.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 10);
			is.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, 5);
			items.add(is);
	
			is = new ItemStack(Material.IRON_LEGGINGS, 1);
			im = is.getItemMeta();
			im.setDisplayName("§f§lGolemrüstung");
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.getById(34), 3); // Unbreaking
			is.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 10);
			is.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, 5);
			items.add(is);
	
			is = new ItemStack(Material.IRON_BOOTS, 1);
			im = is.getItemMeta();
			im.setDisplayName("§f§lGolemrüstung");
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.getById(34), 3); // Unbreaking
			is.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 10);
			is.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, 5);
			items.add(is);
			
			is = new ItemStack(Material.LEATHER_BOOTS, 1);
			lam = (LeatherArmorMeta) is.getItemMeta();
			lam.setDisplayName("§r§2§r§2§lHasenfüße");
			lam.setColor(Color.fromRGB(50, 200, 50));
			l = new ArrayList<String>();
			l.add("§r");
			l.add("§f-----------------§6§l Effekt§r §f-----------------");
			l.add("§eDu bekommst Speed I und Jumpboost I.");
			l.add("§r");
			lam.setLore(l);
			is.setItemMeta(lam);
			is.addUnsafeEnchantment(Enchantment.getById(34), 3); // Unbreaking
			is.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
			items.add(is);
			
			is = new ItemStack(Material.GOLDEN_CARROT, 1);
			im = is.getItemMeta();
			im.setDisplayName("§e§lCobalt's Bonzenmöhre");
			l = new ArrayList<String>();
			im.setLore(l);
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.getById(32), 5); // efficiency
			is.addUnsafeEnchantment(Enchantment.getById(34), 5); // unbreaking
			is.addUnsafeEnchantment(Enchantment.getById(16), 5); // sharpness
			is.addUnsafeEnchantment(Enchantment.getById(20), 5); // fire aspect
			is.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 5);
			is.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, 5);
			items.add(is);
			
			is = new ItemStack(Material.LEATHER_LEGGINGS, 1);
			lam = (LeatherArmorMeta) is.getItemMeta();
			lam.setDisplayName("§r§3§r§9§lRagnaron_'s Kummer");
			lam.setColor(Color.fromRGB(0, 64, 255));
			l = new ArrayList<String>();
			l.add("§r");
			l.add("§f-----------------§6§l Effekt§r §f-----------------");
			l.add("§eErstellt eine Regenwolke, die dich löscht, wenn"); 
			l.add("§edu brennst.");
			l.add("§r");
			lam.setLore(l);
			is.setItemMeta(lam);
			is.addUnsafeEnchantment(Enchantment.getById(34), 4); //Unbreaking
			is.addUnsafeEnchantment(Enchantment.getById(1), 10); //Fire_Protection
			is.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
			items.add(is);
			
			is = new ItemStack(Material.RED_ROSE, 1);
			im = is.getItemMeta();
			im.setDisplayName("§c§lAusgebrannt");
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 5);
			is.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 5);
			items.add(is);

			is = new ItemStack(Material.DIAMOND_PICKAXE, 1);
			im = is.getItemMeta();
			im.setDisplayName("§r§4§r§c§lSmogmaa's Arbeiterpicke");
			l = new ArrayList<String>();
			l.add("§r");
			l.add("§f-----------------§6§l Effekt§r §f-----------------");
			l.add("§eKann 9 Blöcke auf einmal abbauen!");
			l.add("§r");
			im.setLore(l);
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.getById(34), 3);
			is.addUnsafeEnchantment(Enchantment.getById(32), 4);
			items.add(is);

			is = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);
			im =  is.getItemMeta();
			im.setDisplayName("§r§c§r§c§lSmogmaa's Arbeiterjacke");
			l = new ArrayList<String>();
			l.add("§r");
			l.add("§f-----------------§6§l Effekt§r §f-----------------");
			l.add("§eDu bekommst Speed II und Eile I.");
			l.add("§r");
			im.setLore(l);
			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.ARROW_FIRE, 1);
			items.add(is);
	
			is = new ItemStack(Material.STONE_SWORD, 1, (short) 50);
			im = is.getItemMeta();
			im.setDisplayName("§r§9§r§8§lVaape's Giftklinge");
			l = new ArrayList<String>();
			l.add("§r");
			l.add("§f-----------------§6§l Effekt§r §f-----------------");
			l.add("§eFügt Gegnern den Wither- und Hunger Effekt");
			l.add("§ezu.");
			l.add("§r");
			im.setLore(l);
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.getById(34), 1);
			items.add(is);

			is = new ItemStack(Material.BLAZE_ROD, 1);
			im = is.getItemMeta();
			im.setDisplayName("§r§5§r§b§lCrepper's Zauberstaub");
			l = new ArrayList<String>();
			l.add("§r");
			l.add("§f-----------------§6§l Effekt§r §f-----------------");
			l.add("§eLässt dich bei einem Rechtsklick nach vorne fliegen.");
			l.add("§r");
			im.setLore(l);
			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.getById(34), 1);
			items.add(is);

			is = new ItemStack(19, 10, (short) 0);
			im = is.getItemMeta();
			im.setDisplayName("§r§b§r§6§lNickNack's Stinkekäse");
			l = new ArrayList<String>();
			l.add("§r");
			l.add("§f-----------------§6§l Effekt§r §f-----------------");
			l.add("§eStinkt 30 Sekunden lang übel für Spieler in der");
			l.add("§eNähe.");
			l.add("§r");
			im.setLore(l);
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.getById(34), 10);
			items.add(is);

			is = new ItemStack(346, 1);
			im = is.getItemMeta();
			im.setDisplayName("§r§f§r§6§lKitty's Glücksangel");
			l = new ArrayList<String>();
			l.add("§r");
			l.add("§f-----------------§6§l Effekt§r §f-----------------");
			l.add("§eDu bekommst dreifache Erfahrungspunkte.");
			l.add("§r");
			im.setLore(l);
			im.spigot().setUnbreakable(true);
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.LURE, 4);
			is.addUnsafeEnchantment(Enchantment.getById(61), 4); // luck of the sea
			items.add(is);
			
			is = new ItemStack(Material.GOLD_SWORD, 1);
			im = is.getItemMeta();
			im.setDisplayName("§0§0§r§4§lBasic's Feuerklinge");
			l = new ArrayList<String>();
			l.add("§r");
			l.add("§f-----------------§6§l Effekt§r §f-----------------");
			l.add("§eWehrt andere Spieler ab, wenn du blockst!");
			l.add("§eJe weniger Herzen, desto größer der Schild!");
			im.setLore(l);
			im.spigot().setUnbreakable(true);
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 5);
			is.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 2);
			items.add(is);
			
			is = new ItemStack(Material.GOLD_HOE, 1);
			im = is.getItemMeta();
			im.setDisplayName("§r§j§r§6§lCobalt's Farmersense");
			l = new ArrayList<String>();
			l.add("§r");
			l.add("§f-----------------§6§l Effekt§r §f-----------------");
			l.add("§ePflanzt Abgebautes automatisch nach.");
			l.add("§r");
			im.setLore(l);
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 7);
			is.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
			items.add(is);
		} else if (this == DEVITEMS) {
			ItemStack is = new ItemStack(Material.LEATHER_BOOTS, 1);
			LeatherArmorMeta lam = (LeatherArmorMeta) is.getItemMeta();
			lam.setDisplayName("§r§m§r§d§lEinhornschuhe");
			lam.setColor(Color.fromRGB(255, 204, 255)); // fade color
			ArrayList<String> l = new ArrayList<String>();
			l.add("§r");
			l.add("§f-----------------§6§l Effekt §f-----------------");
			l.add("§eLässt dich auf einem Regenbogen laufen.");
			l.add("§r");
			lam.setLore(l);
			is.setItemMeta(lam);
			lam.spigot().setUnbreakable(true);
			lam.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			is.setItemMeta(lam);
			is.addUnsafeEnchantment(Enchantment.ARROW_FIRE, 1);
			items.add(is);
		}

		return items;
	}

	public ArrayList<ItemStack> getItemsInView() {
		if (this == TOOLS) {
			return getItems();
		}

		HashSet<ItemStack> items = new HashSet<ItemStack>();
		
		for (ItemStack is : getItems()) {
			is.setAmount(1);
			items.add(is);
		}

		ArrayList<ItemStack> result = new ArrayList<ItemStack>();
		
		for (ItemStack is : items) {
			result.add(is);
		}

		return result;
	}

	public static CaseType fromName(final String name) {
		for (final CaseType t : values()) {
			if (t.name.contentEquals(name)) {
				return t;
			}
		}

		return CaseType.BLOCKS;
	}
}