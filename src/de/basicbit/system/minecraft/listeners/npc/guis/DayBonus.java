package de.basicbit.system.minecraft.listeners.npc.guis;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.basicbit.system.database.UserData;
import de.basicbit.system.database.UserValue;
import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.cases.CaseType;
import de.basicbit.system.minecraft.dynamic.DynamicObject;
import de.basicbit.system.minecraft.dynamic.Field;
import de.basicbit.system.minecraft.events.PlayerNPCRightClickEvent;
import de.basicbit.system.minecraft.gui.Gui;
import de.basicbit.system.minecraft.npc.NPC;
import de.basicbit.system.minecraft.npc.NPCType;

@SuppressWarnings("deprecation")
public class DayBonus extends Listener {

	@EventHandler
	public void onInteractAtNPC(PlayerNPCRightClickEvent e) {
		Player p = e.getPlayer();
		NPC npc = e.getNPC();

		int dayBonusOpend = UserData.getInt(p, UserValue.dayBonusOpened);
		int dayBonusDay = UserData.getInt(p, UserValue.dayBonusDay);

		int today = getUniqueDayValue();

		if (npc.getType().equals(NPCType.DayBonus)) {
			Gui.open(p, new Gui(new String[] { "§5§lTagesbonus" }, 1) {

				@Override
				public void onInit(DynamicObject args) {
					setBackgroundInAllPagesToGlassColor(15);

					ItemStack is = new ItemStack(Material.HOPPER, 1);
					ItemMeta im = is.getItemMeta();
					im.setDisplayName("§r");
					is.setItemMeta(im);
					setItemAt(4, 1, is);

					is = new ItemStack(dayBonusOpend >= today ? 166 : 353, 1);
					im = is.getItemMeta();
					im.setDisplayName(dayBonusOpend >= today ? "§c§lBlockiert" : "§a§lTagesbonus einfordern");
					is.setItemMeta(im);
					setItemAt(4, 4, is);

					for (int i = 0; i < 9; i++) {
						setItemAt(i, 2, getDayBonusItem(dayBonusDay + i - 4));
					}
				}

				@Override
				public void onDownBarClick(DynamicObject args) {

				}

				@Override
				public void onClose(DynamicObject args) {

				}

				@Override
				public DynamicObject onClick(DynamicObject args) {
					ItemStack is = args.getItemStack(Field.PLAYER_GUI_ITEM_CLICKED);
					int slotX = args.getInt(Field.PLAYER_GUI_SLOT_POS_X);
					int slotY = args.getInt(Field.PLAYER_GUI_SLOT_POS_Y);

					if (slotX == 4 && slotY == 4) {
						if (dayBonusOpend < today) {
							is = getDayBonusItem(dayBonusDay);

							if (is.getTypeId() == 166) {
								sendMessage(p, "§cDu hast den Tagesbonus bereits durchgespielt.");
								p.closeInventory();
								sendErrorSound(p);
								return new DynamicObject();
							}

							giveItemToPlayer(p, is);
							UserData.set(p, UserValue.dayBonusOpened, today);
							UserData.set(p, UserValue.dayBonusDay, dayBonusDay + 1);
							p.closeInventory();
							sendSuccessSound(p);
						} else {
							sendMessage(p, "§cDu hast deinen Tagesbonus bereits abgeholt.");
							p.closeInventory();
							sendErrorSound(p);
						}
					}

					return new DynamicObject();
				}
			}, 0);
		}
	}

	public static ItemStack getDayBonusItem(int day) {
		return getItem(day);
		/*
		try {
			return getItem(((day * 7 * 17 * 19 * (day / 3 + 1) * (day / 7 + 1) * (day * 3) * (day / 13 + 1)) % 69) + 1);
		} catch (Exception e) {
			final ItemStack is = new ItemStack(166, 0);
			final ItemMeta im = is.getItemMeta();
			im.setDisplayName("§rBug");
			is.setItemMeta(im);
			return is;
		}
		*/
	}

	public static ItemStack getItem(int day) {

		if (day == 1) {
			final ItemStack is = new ItemStack(Material.STONE_PICKAXE, 1);
			final ItemMeta im = is.getItemMeta();
            im.setDisplayName("§f§lNOOB-HACKE");
            im.spigot().setUnbreakable(true);
            is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.getById(32), 1);
			return is;
		}

		if (day == 2) {
			return new ItemStack(Material.ENDER_CHEST, 1);
		}

		if (day == 3) {
			return new ItemStack(Material.IRON_BLOCK, 10);
		}

		if (day == 4) {
			final ItemStack is = new ItemStack(Material.RABBIT_FOOT, 1);
			final ItemMeta im = is.getItemMeta();
			im.setDisplayName("§5§lTRITT MICH!");
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.getById(19), 4);
			return is;
		}

		if (day == 5) {
			return new ItemStack(Material.GOLD_INGOT, 1);
		}

		if (day == 6) {
			return new ItemStack(Material.GOLDEN_APPLE, 8);
		}

		if (day == 7) {
			final ItemStack is = new ItemStack(Material.IRON_PICKAXE, 1);
			final ItemMeta im = is.getItemMeta();
			im.setDisplayName("§5§lSuperpicke");
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.getById(32), 6);
			is.addUnsafeEnchantment(Enchantment.getById(34), 3);
			return is;
		}

		if (day == 8) {
			return new ItemStack(Material.ENDER_PEARL, 16);
		}

		if (day == 9) {
			return new ItemStack(Material.GOLDEN_APPLE, 3, (short) 1);
		}

		if (day == 10) {
			final ItemStack is = new ItemStack(Material.IRON_BOOTS, 1);
			final ItemMeta im = is.getItemMeta();
			im.setDisplayName("§b§lHERMESSTIEFEL");
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.getById(0), 5);
			is.addUnsafeEnchantment(Enchantment.getById(34), 3);
			is.addUnsafeEnchantment(Enchantment.getById(2), 10);
			return is;
		}

		if (day == 11) {
			final ItemStack is = new ItemStack(CaseType.MAJO.getKey());
			is.setAmount(2);
			return is;
		}

		if (day == 12) {
			return new ItemStack(Material.DIAMOND, 16);
		}

		if (day == 13) {
			return new ItemStack(Material.EXP_BOTTLE, 32);
		}

		if (day == 14) {
			final ItemStack is = new ItemStack(Material.DIAMOND_PICKAXE, 1);
			final ItemMeta im = is.getItemMeta();
			im.setDisplayName("§5§lSuperpicke");
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.getById(32), 6);
			is.addUnsafeEnchantment(Enchantment.getById(34), 3);
			return is;
		}

		if (day == 15) {
			return new ItemStack(Material.PISTON_BASE, 32);
		}

		if (day == 16) {
			final ItemStack is = new ItemStack(CaseType.BLOCKS.getKey());
			is.setAmount(2);
			return is;
		}

		if (day == 17) {
			final ItemStack is = new ItemStack(CaseType.LOTTO.getKey());
			return is;
		}

		if (day == 18) {
			final ItemStack is = new ItemStack(CaseType.TOOLS.getKey());
			return is;
		}

		if (day == 19) {
			final ItemStack is = new ItemStack(CaseType.PERK.getKey());
			return is;
		}

		if (day == 20) {
			return new ItemStack(Material.SLIME_BALL, 32);
		}

		if (day == 21) {
			return new ItemStack(Material.FIREWORK_CHARGE, 16);
		}

		if (day == 22) {
			return new ItemStack(Material.SEA_LANTERN, 16);
		}

		if (day == 23) {
			return new ItemStack(Material.QUARTZ, 32);
		}

		if (day == 24) {
			return new ItemStack(Material.COAL_BLOCK, 32);
		}

		if (day == 25) {
			return new ItemStack(Material.POWERED_RAIL, 16);
		}

		if (day == 26) {
			return new ItemStack(Material.ANVIL, 4);
		}

		if (day == 27) {
			final ItemStack is = new ItemStack(Material.POTION, 8, (short) 16453);
			final ItemMeta im = is.getItemMeta();
			im.setDisplayName("§4§lHEILUNGSTRANK");
			is.setItemMeta(im);
			return is;
		}

		if (day == 28) {
			return new ItemStack(Material.OBSIDIAN, 32);
		}

		if (day == 29) {
			return new ItemStack(Material.NAME_TAG, 4);
		}

		if (day == 30) {
			return new ItemStack(Material.TNT, 64);
		}

		if (day == 31) {
			return new ItemStack(Material.CLAY_BALL, 64);
		}

		if (day == 32) {
			final ItemStack is = new ItemStack(Material.POTION, 8, (short) 16460);
			final ItemMeta im = is.getItemMeta();
			im.setDisplayName("§5§lSCHADENSTRANK");
			is.setItemMeta(im);
			return is;
		}
		
		if (day == 33) {
			return new ItemStack(Material.REDSTONE_LAMP_OFF, 32);
		}

		if (day == 34) {
			final ItemStack is = new ItemStack(CaseType.BLOCKS.getKey());
			is.setAmount(2);	
			return is;
		}

		if (day == 35) {
			final ItemStack is = new ItemStack(Material.POTION, 8, (short) 16453);
			final ItemMeta im = is.getItemMeta();
			im.setDisplayName("§4§lHEILUNGSTRANK");
			is.setItemMeta(im);
			return is;
		}

		if (day == 36) {
			return new ItemStack(Material.BLAZE_ROD, 8);
		}

		if (day == 37) {
			final ItemStack is = new ItemStack(Material.GOLDEN_APPLE, 4, (short) 1);
			return is;		
		}

		if (day == 38) {
			final ItemStack is = new ItemStack(getMajoItemStack(16));
			return is;
		}

		if (day == 39) {
			final ItemStack is = new ItemStack(Material.DIAMOND_BOOTS, 1);
			final ItemMeta im = is.getItemMeta();
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.getById(0), 4); // SCHUTZ
			return is;
		}

		if (day == 40) {
			final ItemStack is = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
			final ItemMeta im = is.getItemMeta();
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.getById(0), 4); // SCHUTZ
			return is;
		}

		if (day == 41) {
			final ItemStack is = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
			final ItemMeta im = is.getItemMeta();
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.getById(0), 4); // SCHUTZ
			return is;
		}

		if (day == 42) {
			final ItemStack is = new ItemStack(Material.DIAMOND_HELMET, 1);
			final ItemMeta im = is.getItemMeta();
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.getById(0), 4); // SCHUTZ
			return is;
		}

		if (day == 43) {
			return new ItemStack(Material.GOLD_INGOT, 4);
		}

		if (day == 44) {
			final ItemStack is = new ItemStack(getMajoItemStack(8));
			return is;		
		}

		if (day == 45) {
			return new ItemStack(Material.IRON_INGOT, 32);
		}
		
		if (day == 46) {
			final ItemStack is = new ItemStack(CaseType.BLOCKS.getKey());
			is.setAmount(2);
			return is;
		}

		if (day == 47) {
			final ItemStack is = new ItemStack(Material.DIAMOND_SWORD, 1);
			final ItemMeta im = is.getItemMeta();
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.getById(16), 4); // SCHÄRFE
			is.addUnsafeEnchantment(Enchantment.getById(34), 3); // HALTBARKEIT
			is.addUnsafeEnchantment(Enchantment.getById(20), 2); // VERBRENNUNG
			return is;		
		}

		if (day == 48) {
			return new ItemStack(Material.ENDER_PEARL, 16);
		}

		if (day == 49) {
			final ItemStack is = new ItemStack(CaseType.TOOLS.getKey());
			is.setAmount(1);
			return is;		
		}

		if (day == 50) {
			final ItemStack is = new ItemStack(Material.POTION, 8, (short) 16460);
			final ItemMeta im = is.getItemMeta();
			im.setDisplayName("§5§lSCHADENSTRANK");
			is.setItemMeta(im);
			return is;
		}

		if (day == 51) {
			return new ItemStack(Material.GOLD_INGOT, 8);
		}

		if (day == 52) {
			return new ItemStack(Material.COOKED_BEEF, 64);
		}

		if (day == 53) {
			final ItemStack is = new ItemStack(Material.POTION, 8, (short) 16453);
			final ItemMeta im = is.getItemMeta();
			im.setDisplayName("§4§lHEILUNGSTRANK");
			is.setItemMeta(im);
			return is;
		}

		if (day == 54) {
			final ItemStack is = new ItemStack(CaseType.PERK.getKey());
			return is;
		}

		if (day == 55) {
			return new ItemStack(Material.DIAMOND, 32);
		}

		if (day == 56) {
			final ItemStack is = new ItemStack(getMajoItemStack(16));
			return is;
		}

		if (day == 57) {
			return new ItemStack(Material.GOLD_INGOT, 4);
		}

		if (day == 58) {
			final ItemStack is = new ItemStack(CaseType.PERK.getKey());
			is.setAmount(2);
			return is;
		}

		if (day == 59) {
			return new ItemStack(Material.QUARTZ, 32);
		}

		if (day == 60) {
			final ItemStack is = new ItemStack(CaseType.MAJO.getKey());
			is.setAmount(4);
			return is;
		}

		if (day == 61) {
			return new ItemStack(Material.GOLD_INGOT, 6);
		}

		if (day == 62) {
			final ItemStack is = new ItemStack(CaseType.BLOCKS.getKey());
			return is;
		}

		if (day == 63) {
			final ItemStack is = new ItemStack(getMajoItemStack(4));
			return is;
		}

		if (day == 64) {
			return new ItemStack(Material.IRON_BLOCK, 32);
		}

		if (day == 65) {
			return new ItemStack(Material.GOLDEN_APPLE, 32);
		}

		if (day == 66) {
			final ItemStack is = new ItemStack(CaseType.SPECIAL.getKey());
			return is;
		}

		if (day == 67) {
			final ItemStack is = new ItemStack(CaseType.TOOLS.getKey());
			return is;
		}

		if (day == 68) {
			return new ItemStack(Material.GOLD_BLOCK, 1);
		}

		if (day == 69) {
			final ItemStack is = new ItemStack(Material.POTION, 8, (short) 16460);
			final ItemMeta im = is.getItemMeta();
			im.setDisplayName("§5§lSCHADENSTRANK");
			is.setItemMeta(im);
			return is;
		}

		if (day == 70) {
			return new ItemStack(Material.PISTON_BASE, 32);
		}

		if (day == 71) {
			final ItemStack is = new ItemStack(CaseType.BLOCKS.getKey());
			is.setAmount(2);
			return is;
		}

		if (day == 72) {
			final ItemStack is = new ItemStack(CaseType.LOTTO.getKey());
			return is;
		}

		if (day == 73) {
			final ItemStack is = new ItemStack(CaseType.TOOLS.getKey());
			return is;
		}

		if (day == 74) {
			final ItemStack is = new ItemStack(CaseType.PERK.getKey());
			return is;
		}

		if (day == 75) {
			return new ItemStack(Material.SLIME_BALL, 32);
		}

		if (day == 76) {
			return new ItemStack(Material.FIREWORK_CHARGE, 16);
		}

		if (day == 77) {
			return new ItemStack(Material.SEA_LANTERN, 16);
		}

		if (day == 78) {
			return new ItemStack(Material.QUARTZ, 32);
		}

		if (day == 79) {
			return new ItemStack(Material.COAL_BLOCK, 32);
		}

		if (day == 80) {
			return new ItemStack(Material.POWERED_RAIL, 16);
		}

		if (day == 81) {
			return new ItemStack(Material.ANVIL, 4);
		}

		if (day == 82) {
			final ItemStack is = new ItemStack(Material.POTION, 8, (short) 16453);
			final ItemMeta im = is.getItemMeta();
			im.setDisplayName("§4§lHEILUNGSTRANK");
			is.setItemMeta(im);
			return is;
		}

		if (day == 83) {
			return new ItemStack(Material.OBSIDIAN, 32);
		}

		if (day == 84) {
			return new ItemStack(Material.NAME_TAG, 4);
		}

		if (day == 85) {
			return new ItemStack(Material.TNT, 64);
		}

		if (day == 86) {
			return new ItemStack(Material.CLAY_BALL, 64);
		}

		if (day == 87) {
			final ItemStack is = new ItemStack(Material.POTION, 8, (short) 16460);
			final ItemMeta im = is.getItemMeta();
			im.setDisplayName("§5§lSCHADENSTRANK");
			is.setItemMeta(im);
			return is;
		}
		
		if (day == 88) {
			return new ItemStack(Material.REDSTONE_LAMP_OFF, 32);
		}

		if (day == 89) {
			final ItemStack is = new ItemStack(CaseType.BLOCKS.getKey());
			is.setAmount(2);	
			return is;
		}

		if (day == 90) {
			final ItemStack is = new ItemStack(Material.POTION, 8, (short) 16453);
			final ItemMeta im = is.getItemMeta();
			im.setDisplayName("§4§lHEILUNGSTRANK");
			is.setItemMeta(im);
			return is;
		}

		if (day == 91) {
			return new ItemStack(Material.BLAZE_ROD, 8);
		}

		if (day == 92) {
			final ItemStack is = new ItemStack(Material.GOLDEN_APPLE, 4, (short) 1);
			return is;		
		}

		if (day == 93) {
			final ItemStack is = new ItemStack(getMajoItemStack(16));
			return is;
		}

		if (day == 94) {
			final ItemStack is = new ItemStack(Material.DIAMOND_BOOTS, 1);
			final ItemMeta im = is.getItemMeta();
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.getById(0), 4); // SCHUTZ
			return is;
		}

		if (day == 95) {
			final ItemStack is = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
			final ItemMeta im = is.getItemMeta();
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.getById(0), 4); // SCHUTZ
			return is;
		}

		if (day == 96) {
			final ItemStack is = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
			final ItemMeta im = is.getItemMeta();
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.getById(0), 4); // SCHUTZ
			return is;
		}

		if (day == 97) {
			final ItemStack is = new ItemStack(Material.DIAMOND_HELMET, 1);
			final ItemMeta im = is.getItemMeta();
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.getById(0), 4); // SCHUTZ
			return is;
		}

		if (day == 98) {
			return new ItemStack(Material.GOLD_INGOT, 4);
		}

		if (day == 99) {
			final ItemStack is = new ItemStack(getMajoItemStack(8));
			return is;		
		}

		if (day == 100) {
			return new ItemStack(Material.IRON_INGOT, 32);
		}
		
		if (day == 101) {
			final ItemStack is = new ItemStack(CaseType.BLOCKS.getKey());
			is.setAmount(2);
			return is;
		}

		if (day == 102) {
			final ItemStack is = new ItemStack(Material.DIAMOND_SWORD, 1);
			final ItemMeta im = is.getItemMeta();
			is.setItemMeta(im);
			is.addUnsafeEnchantment(Enchantment.getById(16), 4); // SCHÄRFE
			is.addUnsafeEnchantment(Enchantment.getById(34), 3); // HALTBARKEIT
			is.addUnsafeEnchantment(Enchantment.getById(20), 2); // VERBRENNUNG
			return is;		
		}

		if (day == 103) {
			return new ItemStack(Material.ENDER_PEARL, 16);
		}

		if (day == 104) {
			final ItemStack is = new ItemStack(CaseType.TOOLS.getKey());
			is.setAmount(1);
			return is;		
		}

		if (day == 105) {
			final ItemStack is = new ItemStack(Material.POTION, 8, (short) 16460);
			final ItemMeta im = is.getItemMeta();
			im.setDisplayName("§5§lSCHADENSTRANK");
			is.setItemMeta(im);
			return is;
		}

		if (day == 106) {
			return new ItemStack(Material.GOLD_INGOT, 8);
		}

		if (day == 107) {
			return new ItemStack(Material.COOKED_BEEF, 64);
		}

		if (day == 108) {
			final ItemStack is = new ItemStack(Material.POTION, 8, (short) 16453);
			final ItemMeta im = is.getItemMeta();
			im.setDisplayName("§4§lHEILUNGSTRANK");
			is.setItemMeta(im);
			return is;
		}

		if (day == 109) {
			final ItemStack is = new ItemStack(CaseType.PERK.getKey());
			return is;
		}

		if (day == 110) {
			return new ItemStack(Material.DIAMOND, 32);
		}

		if (day == 111) {
			final ItemStack is = new ItemStack(getMajoItemStack(16));
			return is;
		}

		if (day == 112) {
			return new ItemStack(Material.GOLD_INGOT, 4);
		}

		if (day == 113) {
			final ItemStack is = new ItemStack(CaseType.PERK.getKey());
			is.setAmount(2);
			return is;
		}

		if (day == 114) {
			return new ItemStack(Material.QUARTZ, 32);
		}

		if (day == 115) {
			final ItemStack is = new ItemStack(CaseType.MAJO.getKey());
			is.setAmount(4);
			return is;
		}

		if (day == 116) {
			return new ItemStack(Material.GOLD_INGOT, 6);
		}

		if (day == 117) {
			final ItemStack is = new ItemStack(CaseType.BLOCKS.getKey());
			return is;
		}

		if (day == 118) {
			final ItemStack is = new ItemStack(getMajoItemStack(4));
			return is;
		}

		if (day == 119) {
			return new ItemStack(Material.IRON_BLOCK, 32);
		}

		if (day == 120) {
			return new ItemStack(Material.GOLDEN_APPLE, 32);
		}

		if (day == 121) {
			final ItemStack is = new ItemStack(CaseType.SPECIAL.getKey());
			return is;
		}

		if (day == 122) {
			final ItemStack is = new ItemStack(CaseType.TOOLS.getKey());
			return is;
		}

		if (day == 123) {
			return new ItemStack(Material.GOLD_BLOCK, 1);
		}

		if (day == 124) {
			final ItemStack is = new ItemStack(Material.POTION, 8, (short) 16460);
			final ItemMeta im = is.getItemMeta();
			im.setDisplayName("§5§lSCHADENSTRANK");
			is.setItemMeta(im);
			return is;
		}

		final ItemStack is = new ItemStack(Material.BARRIER, 1);
		final ItemMeta im = is.getItemMeta();
		im.setDisplayName("§r");
		is.setItemMeta(im);
		return is;
	}
}