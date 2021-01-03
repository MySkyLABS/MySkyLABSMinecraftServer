package de.basicbit.system.minecraft.skyblock.worldgenerator;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.basicbit.system.GlobalValues;
import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.ListenerSystem;
import de.basicbit.system.minecraft.cases.CaseType;
import de.basicbit.system.minecraft.listeners.player.Majo;
import de.basicbit.system.minecraft.skyblock.SkyBlock;
import de.basicbit.system.minecraft.tasks.TaskManager;

@SuppressWarnings("deprecation")
public class WorldGenerator extends Listener {

	private static ArrayList<ItemStack> items = new ArrayList<ItemStack>();

	public static void init() {
		initItems();

		ListenerSystem.register(new WorldGenerator());
	}

	private static void initItems() {
		int i = 0;
		int oldI = 0;

		log("Register items...");

		for (i = 0; i < 2; i++) {
			oldI = i;
			for (i = 0; i < 32; i++) { // 64 Mal (1-32 Items)
				items.add(new ItemStack(168, i + 1, (short) 0)); // Prismarine
				items.add(new ItemStack(168, i + 1, (short) 1)); // PrismarineBricks
				items.add(new ItemStack(168, i + 1, (short) 2)); // DarkPrismarine
				items.add(new ItemStack(169, i + 1, (short) 0)); // SeaLantern
				items.add(new ItemStack(409, i + 1, (short) 0)); // PrismarineShard
				items.add(new ItemStack(410, i + 1, (short) 0)); // PrismarineCrystals
				items.add(new ItemStack(384, i + 1, (short) 0)); // Bottleo'Enchanting
				items.add(Majo.getMajoItemStack(i + 1)); // MAYO-Item
			}
			i = oldI;

			oldI = i;
			for (i = 0; i < 2; i++) { // 4 Mal
				oldI = i;
				for (i = 0; i < 16; i++) { // 64 Mal (1-16 Items)
					items.add(new ItemStack(368, i + 1, (short) 0)); // EnderPearl
					items.add(new ItemStack(332, i + 1, (short) 0)); // Snowball
					items.add(new ItemStack(344, i + 1, (short) 0)); // Egg
					items.add(new ItemStack(49, i + 1, (short) 0)); // Obsidian
					items.add(new ItemStack(30, i + 1, (short) 0)); // Cobweb
					items.add(new ItemStack(46, i + 1, (short) 0)); // TNT
					items.add(new ItemStack(106, i + 1, (short) 0)); // Vines
					items.add(new ItemStack(323, i + 1, (short) 0)); // Sign
					items.add(new ItemStack(341, i + 1, (short) 0)); // Slimeball
					items.add(new ItemStack(86, i + 1, (short) 0)); // Pumpkin
					items.add(new ItemStack(103, i + 1, (short) 0)); // Melon

				}
				i = oldI;

				oldI = i;
				for (i = 0; i < 2; i++) { // 8 Mal
					oldI = i;
					for (i = 0; i < 8; i++) { // 64 Mal (1-8 Items)
						items.add(new ItemStack(263, i + 1, (short) 0)); // Coal
						items.add(new ItemStack(265, i + 1, (short) 0)); // IronIngot
						items.add(new ItemStack(331, i + 1, (short) 0)); // Redstone
						items.add(new ItemStack(388, i + 1, (short) 0)); // Emerald
						items.add(new ItemStack(264, i + 1, (short) 0)); // Diamond
						items.add(new ItemStack(25, i + 1, (short) 0)); // NoteBlock
						items.add(new ItemStack(54, i + 1, (short) 0)); // Chest
						items.add(new ItemStack(47, i + 1, (short) 0)); // Bookshelf
						items.add(new ItemStack(23, i + 1, (short) 0)); // Dispencer
						items.add(new ItemStack(29, i + 1, (short) 0)); // StickyPiston
						items.add(new ItemStack(33, i + 1, (short) 0)); // Piston
						items.add(new ItemStack(76, i + 1, (short) 0)); // RedstoneTorch
						items.add(new ItemStack(123, i + 1, (short) 0)); // RedstoneLamp
						items.add(new ItemStack(151, i + 1, (short) 0)); // DaylightSensor
						items.add(new ItemStack(154, i + 1, (short) 0)); // Hopper
						items.add(new ItemStack(158, i + 1, (short) 0)); // Dropper
						items.add(new ItemStack(356, i + 1, (short) 0)); // RedstoneRepeater
						items.add(new ItemStack(404, i + 1, (short) 0)); // RedstoneComparator

					}
					i = oldI;

					oldI = i;
					for (i = 0; i < 2; i++) { // 16 Mal
						for (i = 0; i < 4; i++) { // 64 Mal (1-4 Items)
							items.add(new ItemStack(266, i + 1, (short) 0)); // GoldIngot
						}
					}
					i = oldI;
				}
				i = oldI;
			}
			i = oldI;
		}

		for (i = 0; i < 64; i++) { // 64 Mal (1-64 Items)
			items.add(new ItemStack(337, i + 1, (short) 0)); // Clay(Klumpen)
			items.add(new ItemStack(82, i + 1, (short) 0)); // Clay(Block)
			items.add(new ItemStack(172, i + 1, (short) 0)); // HardenedClay
			items.add(new ItemStack(12, i + 1, (short) 0)); // Sand
			items.add(new ItemStack(12, i + 1, (short) 1)); // RedSand
			items.add(new ItemStack(3, i + 1, (short) 0)); // Dirt
			items.add(new ItemStack(2, i + 1, (short) 0)); // GrassBlock
			items.add(new ItemStack(4, i + 1, (short) 0)); // Cobblestone
			items.add(new ItemStack(262, i + 1, (short) 0)); // Arrow
			items.add(new ItemStack(1, i + 1, (short) 0)); // Stone
			items.add(new ItemStack(81, i + 1, (short) 0)); // Cactus
			items.add(new ItemStack(280, i + 1, (short) 0)); // Stick
			items.add(new ItemStack(111, i + 1, (short) 0)); // LilyPad
			items.add(new ItemStack(2, i + 1, (short) 0)); // GrassBlock
			items.add(new ItemStack(20, i + 1, (short) 0)); // Glass
			items.add(new ItemStack(102, i + 1, (short) 0)); // GlassPane
			items.add(new ItemStack(98, i + 1, (short) 0)); // StoneBricks
			items.add(new ItemStack(287, i + 1, (short) 0)); // String
			items.add(new ItemStack(24, i + 1, (short) 0)); // Sandstone
			items.add(new ItemStack(179, i + 1, (short) 0)); // RedSandstone
			items.add(new ItemStack(17, i + 1, (short) 0)); // OakWood
			items.add(new ItemStack(17, i + 1, (short) 1)); // SpruceWood
			items.add(new ItemStack(17, i + 1, (short) 2)); // BirchWood
			items.add(new ItemStack(17, i + 1, (short) 3)); // JungleWood
			items.add(new ItemStack(162, i + 1, (short) 0)); // AcaciaWood
			items.add(new ItemStack(162, i + 1, (short) 1)); // DarkOakWood
			items.add(new ItemStack(45, i + 1, (short) 0)); // Bricks
			items.add(new ItemStack(297, i + 1, (short) 0)); // Bread
			items.add(new ItemStack(364, i + 1, (short) 0)); // Steak
			items.add(new ItemStack(260, i + 1, (short) 0)); // Apple
			items.add(new ItemStack(357, i + 1, (short) 0)); // Cookie
			items.add(new ItemStack(391, i + 1, (short) 0)); // Carrot
			items.add(new ItemStack(392, i + 1, (short) 0)); // Potato
			items.add(new ItemStack(295, i + 1, (short) 0)); // Seeds
			items.add(new ItemStack(361, i + 1, (short) 0)); // PumpkinSeeds
			items.add(new ItemStack(362, i + 1, (short) 0)); // MelonSeeds

			items.add(CaseType.PERK.getKey()); // Perk-Case-Key
			items.add(CaseType.MAJO.getKey()); // Majo-Case-Key
			items.add(CaseType.BLOCKS.getKey()); // Blöcke-Case-Key
			items.add(CaseType.TOOLS.getKey()); // Tool-Case-Key
			items.add(CaseType.LOTTO.getKey()); // Lotto-Case-Key
			items.add(CaseType.SPECIAL.getKey());

			for (short j = 0; j < 16; j++) {
				items.add(new ItemStack(159, i + 1, j)); // FarbigerClay
				items.add(new ItemStack(35, i + 1, j)); // Wool
				items.add(new ItemStack(95, i + 1, j)); // FarbigesGlass
				items.add(new ItemStack(160, i + 1, j)); // ColorGlassPane
				items.add(new ItemStack(351, i + 1, j)); // Dye

			}
			for (short j = 0; j < 9; j++) {
				items.add(new ItemStack(38, i + 1, j)); // Flower
			}

			for (short j = 0; j < 6; j++) {
				items.add(new ItemStack(6, i + 1, j)); // Saplings
				items.add(new ItemStack(5, i + 1, j)); // WoodPlanks
			}

			// einzelne Item
			items.add(new ItemStack(2256, 1, (short) 0)); // MusicDisc
			items.add(new ItemStack(2257, 1, (short) 0)); // MusicDisc
			items.add(new ItemStack(2258, 1, (short) 0)); // MusicDisc
			items.add(new ItemStack(2259, 1, (short) 0)); // MusicDisc
			items.add(new ItemStack(2260, 1, (short) 0)); // MusicDisc
			items.add(new ItemStack(2261, 1, (short) 0)); // MusicDisc
			items.add(new ItemStack(2262, 1, (short) 0)); // MusicDisc
			items.add(new ItemStack(2263, 1, (short) 0)); // MusicDisc
			items.add(new ItemStack(2264, 1, (short) 0)); // MusicDisc
			items.add(new ItemStack(2265, 1, (short) 0)); // MusicDisc
			items.add(new ItemStack(2266, 1, (short) 0)); // MusicDisc
			items.add(new ItemStack(2267, 1, (short) 0)); // MusicDisc
			items.add(new ItemStack(326, 1, (short) 0)); // WaterBucket
			items.add(new ItemStack(327, 1, (short) 0)); // LavaBucket
			items.add(new ItemStack(259, 1, (short) 0)); // FlintandSteel
			items.add(new ItemStack(261, 1, (short) 0)); // Bow
			items.add(new ItemStack(84, 1, (short) 0)); // Jukebox
			items.add(new ItemStack(116, 1, (short) 0)); // EntchantmentTable
			items.add(new ItemStack(322, 1, (short) 0)); // GoldenApple
			items.add(new ItemStack(130, 1, (short) 0)); // EnderChest
			items.add(new ItemStack(19, 1, (short) 0)); // Sponge
			items.add(new ItemStack(84, 1, (short) 0)); // Jukebox
		}

		// Enchanted Book
		int id = 0; // protection 4
		int maxLevel = 4;
		for (int j = 0; j < maxLevel; j++) {
			ItemStack is = new ItemStack(403);
			is.addUnsafeEnchantment(Enchantment.getById(id), j + 1);
			items.add(is);
		}

		id = 1; // fire_protection 4
		maxLevel = 4;
		for (int j = 0; j < maxLevel; j++) {
			ItemStack is = new ItemStack(403);
			is.addUnsafeEnchantment(Enchantment.getById(id), j + 1);
			items.add(is);
		}

		id = 2; // feather_falling 4
		maxLevel = 4;
		for (int j = 0; j < maxLevel; j++) {
			ItemStack is = new ItemStack(403);
			is.addUnsafeEnchantment(Enchantment.getById(id), j + 1);
			items.add(is);
		}
		id = 3; // blast_protection 4
		maxLevel = 4;
		for (int j = 0; j < maxLevel; j++) {
			ItemStack is = new ItemStack(403);
			is.addUnsafeEnchantment(Enchantment.getById(id), j + 1);
			items.add(is);
		}

		id = 4; // projectile_protection 4
		maxLevel = 4;
		for (int j = 0; j < maxLevel; j++) {
			ItemStack is = new ItemStack(403);
			is.addUnsafeEnchantment(Enchantment.getById(id), j + 1);
			items.add(is);
		}

		id = 5; // respiration 3
		maxLevel = 3;
		for (int j = 0; j < maxLevel; j++) {
			ItemStack is = new ItemStack(403);
			is.addUnsafeEnchantment(Enchantment.getById(id), j + 1);
			items.add(is);
		}

		id = 6; // aqua_affinity 1
		maxLevel = 1;
		for (int j = 0; j < maxLevel; j++) {
			ItemStack is = new ItemStack(403);
			is.addUnsafeEnchantment(Enchantment.getById(id), j + 1);
			items.add(is);
		}

		id = 7; // thorns 3
		maxLevel = 3;
		for (int j = 0; j < maxLevel; j++) {
			ItemStack is = new ItemStack(403);
			is.addUnsafeEnchantment(Enchantment.getById(id), j + 1);
			items.add(is);
		}

		id = 8; // depth_strider 3
		maxLevel = 3;
		for (int j = 0; j < maxLevel; j++) {
			ItemStack is = new ItemStack(403);
			is.addUnsafeEnchantment(Enchantment.getById(id), j + 1);
			items.add(is);
		}

		id = 16; // sharpness 5
		maxLevel = 5;
		for (int j = 0; j < maxLevel; j++) {
			ItemStack is = new ItemStack(403);
			is.addUnsafeEnchantment(Enchantment.getById(id), j + 1);
			items.add(is);
		}

		id = 17; // smite 5
		maxLevel = 5;
		for (int j = 0; j < maxLevel; j++) {
			ItemStack is = new ItemStack(403);
			is.addUnsafeEnchantment(Enchantment.getById(id), j + 1);
			items.add(is);
		}

		id = 18; // bane_of_arthropods 5
		maxLevel = 5;
		for (int j = 0; j < maxLevel; j++) {
			ItemStack is = new ItemStack(403);
			is.addUnsafeEnchantment(Enchantment.getById(id), j + 1);
			items.add(is);
		}

		id = 19; // knockback 2
		maxLevel = 2;
		for (int j = 0; j < maxLevel; j++) {
			ItemStack is = new ItemStack(403);
			is.addUnsafeEnchantment(Enchantment.getById(id), j + 1);
			items.add(is);
		}

		id = 20; // fire_aspect 2
		maxLevel = 2;
		for (int j = 0; j < maxLevel; j++) {
			ItemStack is = new ItemStack(403);
			is.addUnsafeEnchantment(Enchantment.getById(id), j + 1);
			items.add(is);
		}

		id = 21; // looting 3
		maxLevel = 3;
		for (int j = 0; j < maxLevel; j++) {
			ItemStack is = new ItemStack(403);
			is.addUnsafeEnchantment(Enchantment.getById(id), j + 1);
			items.add(is);
		}

		id = 32; // efficiency 5
		maxLevel = 5;
		for (int j = 0; j < maxLevel; j++) {
			ItemStack is = new ItemStack(403);
			is.addUnsafeEnchantment(Enchantment.getById(id), j + 1);
			items.add(is);
		}

		id = 33; // silk_touch 1
		maxLevel = 1;
		for (int j = 0; j < maxLevel; j++) {
			ItemStack is = new ItemStack(403);
			is.addUnsafeEnchantment(Enchantment.getById(id), j + 1);
			items.add(is);
		}

		id = 34; // unbreaking 3
		maxLevel = 3;
		for (int j = 0; j < maxLevel; j++) {
			ItemStack is = new ItemStack(403);
			is.addUnsafeEnchantment(Enchantment.getById(id), j + 1);
			items.add(is);
		}

		id = 35; // fortune 3
		maxLevel = 3;
		for (int j = 0; j < maxLevel; j++) {
			ItemStack is = new ItemStack(403);
			is.addUnsafeEnchantment(Enchantment.getById(id), j + 1);
			items.add(is);
		}

		id = 48; // power 5
		maxLevel = 5;
		for (int j = 0; j < maxLevel; j++) {
			ItemStack is = new ItemStack(403);
			is.addUnsafeEnchantment(Enchantment.getById(id), j + 1);
			items.add(is);
		}
		id = 49; // punch 2
		maxLevel = 2;
		for (int j = 0; j < maxLevel; j++) {
			ItemStack is = new ItemStack(403);
			is.addUnsafeEnchantment(Enchantment.getById(id), j + 1);
			items.add(is);
		}

		id = 50; // flame 1
		maxLevel = 1;
		for (int j = 0; j < maxLevel; j++) {
			ItemStack is = new ItemStack(403);
			is.addUnsafeEnchantment(Enchantment.getById(id), j + 1);
			items.add(is);
		}

		id = 51; // infinity 1
		maxLevel = 1;
		for (int j = 0; j < maxLevel; j++) {
			ItemStack is = new ItemStack(403);
			is.addUnsafeEnchantment(Enchantment.getById(id), j + 1);
			items.add(is);
		}

		id = 61; // luck_of_the_sea 3
		maxLevel = 3;
		for (int j = 0; j < maxLevel; j++) {
			ItemStack is = new ItemStack(403);
			is.addUnsafeEnchantment(Enchantment.getById(id), j + 1);
			items.add(is);
		}

		id = 62; // lure 3
		maxLevel = 3;
		for (int j = 0; j < maxLevel; j++) {
			ItemStack is = new ItemStack(403);
			is.addUnsafeEnchantment(Enchantment.getById(id), j + 1);
			items.add(is);
		}

		// Tränke
		items.add(new ItemStack(373, 1, (short) 0));
		items.add(new ItemStack(373, 1, (short) 8193));
		items.add(new ItemStack(373, 1, (short) 8225));
		items.add(new ItemStack(373, 1, (short) 8257));
		items.add(new ItemStack(373, 1, (short) 16385));
		items.add(new ItemStack(373, 1, (short) 16417));
		items.add(new ItemStack(373, 1, (short) 16449));
		items.add(new ItemStack(373, 1, (short) 8194));
		items.add(new ItemStack(373, 1, (short) 8226));
		items.add(new ItemStack(373, 1, (short) 8258));
		items.add(new ItemStack(373, 1, (short) 16386));
		items.add(new ItemStack(373, 1, (short) 16418));
		items.add(new ItemStack(373, 1, (short) 16450));
		items.add(new ItemStack(373, 1, (short) 8227));
		items.add(new ItemStack(373, 1, (short) 8259));
		items.add(new ItemStack(373, 1, (short) 16419));
		items.add(new ItemStack(373, 1, (short) 16451));
		items.add(new ItemStack(373, 1, (short) 8196));
		items.add(new ItemStack(373, 1, (short) 8228));
		items.add(new ItemStack(373, 1, (short) 8260));
		items.add(new ItemStack(373, 1, (short) 16388));
		items.add(new ItemStack(373, 1, (short) 16420));
		items.add(new ItemStack(373, 1, (short) 16452));
		items.add(new ItemStack(373, 1, (short) 8261));
		items.add(new ItemStack(373, 1, (short) 8229));
		items.add(new ItemStack(373, 1, (short) 16453));
		items.add(new ItemStack(373, 1, (short) 16421));
		items.add(new ItemStack(373, 1, (short) 8230));
		items.add(new ItemStack(373, 1, (short) 8262));
		items.add(new ItemStack(373, 1, (short) 16422));
		items.add(new ItemStack(373, 1, (short) 16454));
		items.add(new ItemStack(373, 1, (short) 8232));
		items.add(new ItemStack(373, 1, (short) 8264));
		items.add(new ItemStack(373, 1, (short) 16424));
		items.add(new ItemStack(373, 1, (short) 16456));
		items.add(new ItemStack(373, 1, (short) 8201));
		items.add(new ItemStack(373, 1, (short) 8233));
		items.add(new ItemStack(373, 1, (short) 8265));
		items.add(new ItemStack(373, 1, (short) 16393));
		items.add(new ItemStack(373, 1, (short) 16425));
		items.add(new ItemStack(373, 1, (short) 16457));
		items.add(new ItemStack(373, 1, (short) 8234));
		items.add(new ItemStack(373, 1, (short) 8266));
		items.add(new ItemStack(373, 1, (short) 16426));
		items.add(new ItemStack(373, 1, (short) 16458));
		items.add(new ItemStack(373, 1, (short) 8203));
		items.add(new ItemStack(373, 1, (short) 8235));
		items.add(new ItemStack(373, 1, (short) 8267));
		items.add(new ItemStack(373, 1, (short) 16395));
		items.add(new ItemStack(373, 1, (short) 16427));
		items.add(new ItemStack(373, 1, (short) 16459));
		items.add(new ItemStack(373, 1, (short) 8268));
		items.add(new ItemStack(373, 1, (short) 8236));
		items.add(new ItemStack(373, 1, (short) 16460));
		items.add(new ItemStack(373, 1, (short) 16428));
		items.add(new ItemStack(373, 1, (short) 8237));
		items.add(new ItemStack(373, 1, (short) 8269));
		items.add(new ItemStack(373, 1, (short) 16429));
		items.add(new ItemStack(373, 1, (short) 16461));
		items.add(new ItemStack(373, 1, (short) 8238));
		items.add(new ItemStack(373, 1, (short) 8270));
		items.add(new ItemStack(373, 1, (short) 16430));
		items.add(new ItemStack(373, 1, (short) 16462));
	}

	@EventHandler
	public void onChunkLoad(ChunkLoadEvent e) {
		Chunk c = e.getChunk();

		if (isSkyblockWorld(c.getWorld()) && e.isNewChunk()) {

			for (int x = 0; x < 16; x++) {
				for (int z = 0; z < 16; z++) {
					for (int y = 0; y < 256; y++) {
						c.getBlock(x, y, z).setType(Material.AIR, false);
					}
				}
			}

			Random random = GlobalValues.random;
			boolean createIsland = random.nextInt(7) == 0;

			if (createIsland && c.getX() % 2 == 0 && c.getZ() % 2 == 0
					&& !((c.getX() == 0 || c.getX() == -1) && (c.getZ() == 0 || c.getZ() == -1))) {

				TaskManager.runSyncTaskLater("WorldGeneratorCreateRandomIsland", new Runnable() {

					@Override
					public void run() {
						// try {
						// 	new SimpleBlockAreaFile(SkyBlock.getRandomIslandSba()).paste(new Location(e.getWorld(), c.getX() * 16 + 8, 69, c.getZ() * 16 + 8));
						// } catch (IllegalArgumentException | IOException e) {
						// 	e.printStackTrace();
						// }
						pasteSchematic(SkyBlock.getRandomIslandSchematic(), new Location(e.getWorld(), c.getX() * 16 + 8, 69, c.getZ() * 16 + 8));
					}

				}, 20);
				
				TaskManager.runSyncTaskLater("WorldGeneratorFillChestsInChunk", new Runnable() {

					@Override
					public void run() {
						fillChestsInChunk(c);
					}

				}, 40);
			}
        }
	}
	
	public ItemStack getLootItem() {
		return items.get(GlobalValues.random.nextInt(items.size()));
	}

	public void fillChestsInChunk(Chunk c) {
		for (Chest chest : findChests(c)) {
			fillChest(chest);
		}
	}

	public ArrayList<Chest> findChests(Chunk c) {
		ArrayList<Chest> chests = new ArrayList<Chest>();
		BlockState[] states = c.getTileEntities();
		
		for (BlockState state : states) {
			if (state instanceof Chest) {
				chests.add((Chest) state);
			}
		}

		return chests;
	}
	
	public void fillChest(Chest chest) {
		Inventory inv = chest.getBlockInventory();
		Random random = GlobalValues.random;

		for (int i = 0; i < 27; i++) {
			int randomValue = random.nextInt(5);

			if (randomValue == 0) {
				inv.setItem(i, getLootItem());
			}
		}

		if (random.nextInt(40) == 0) {
			for (int i = 0; i < 27; i++) {
				ItemStack is = inv.getItem(i);

				if (is == null || is.getTypeId() == 0) {
					inv.setItem(i, new ItemStack(371));
				}
			}
		}
	}
}