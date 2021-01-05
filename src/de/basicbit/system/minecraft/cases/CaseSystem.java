package de.basicbit.system.minecraft.cases;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.ListenerSystem;
import de.basicbit.system.minecraft.MySkyLABS;
import de.basicbit.system.minecraft.dynamic.DynamicObject;
import de.basicbit.system.minecraft.dynamic.Field;
import de.basicbit.system.minecraft.dynamic.FieldAndObject;
import de.basicbit.system.minecraft.gui.Gui;

public class CaseSystem extends Listener {

	public static ArrayList<UUID> openPlayers = new ArrayList<UUID>();

	public static void init() {
		ListenerSystem.register(new CaseSystem());

		CaseItems.init();
	}

	public static ArrayList<ItemStack> getKeys() {
		final ArrayList<ItemStack> keys = new ArrayList<ItemStack>();

		for (final CaseType type : CaseType.values()) {
			keys.add(type.getKey());
		}

		return keys;
	}

	public static Inventory createSlotInventory(final CaseType t) {
		final Inventory inv = Bukkit.createInventory(null, 27, "§5§lCase §8- " + t.getTitle());
		ItemStack is = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName("§r");
		is.setItemMeta(im);

		for (int i = 0; i < 27; i++) {
			inv.setItem(i, is);
		}

		is = new ItemStack(Material.HOPPER, 1);
		im = is.getItemMeta();
		im.setDisplayName("§r");
		is.setItemMeta(im);
		inv.setItem(4, is);

		for (int i = 0; i < 9; i++) {
			is = t.getRandomItem();
			inv.setItem(9 + i, is);
		}

		return inv;
	}

	public static void update(final Inventory inv, final int updated, final int nextUpdate) {

		JavaPlugin instance = MySkyLABS.getSystemPlugin();

		if (inv.getViewers().size() == 0) {
			return;
		}

		final Player p = (Player) inv.getViewers().get(0);

		for (int i = 7; i >= 0; i--) {
			inv.setItem(i + 10, inv.getItem(i + 9));
		}

		inv.setItem(9, CaseType.fromName(inv.getName().substring(13)).getRandomItem());

		sendClickSound(p);

		if (nextUpdate > 15) {
			Bukkit.getScheduler().runTaskLater(instance, new Runnable() {

				@Override
				public void run() {
					if (openPlayers.contains(p.getUniqueId())) {
						openPlayers.remove(p.getUniqueId());

						giveItemToPlayer(p, inv.getItem(13));

						sendSuccessSound(p);
						p.closeInventory();
					}
				}

			}, 20);
		} else {
			Bukkit.getScheduler().runTaskLater(instance, new Runnable() {

				@Override
				public void run() {
					update(inv, updated + 1, nextUpdate + (updated > 30 ? 1 : 0));
				}

			}, nextUpdate);
		}
	}

	public static void open(final Player p, final CaseType t) {
		if (!openPlayers.contains(p.getUniqueId())) {
			openPlayers.add(p.getUniqueId());

			final Inventory inv = createSlotInventory(t);
			p.openInventory(inv);
			update(inv, 0, 1);
		}
	}

	@EventHandler
	public static void onInvClick(final InventoryClickEvent e) {
		final Inventory inv = e.getClickedInventory();

		if (inv != null) {
			if (inv.getTitle().startsWith("§5§lCase §8- ")) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public static void onInvClose(InventoryCloseEvent e) {
		final Player p = (Player) e.getPlayer();
		final Inventory inv = e.getInventory();

		if (inv != null) {
			if (inv.getTitle().startsWith("§5§lCase §8- ")) {
				if (openPlayers.contains(p.getUniqueId())) {
					openPlayers.remove(p.getUniqueId());

					final CaseType t = CaseType.fromName(inv.getName().substring(13));
					giveItemToPlayer(p, t.getRandomItem());
					sendSuccessSound(p);
				}
			}
		}
	}

	@EventHandler
	public static void onQuit(PlayerQuitEvent e) {
		if (openPlayers.contains(e.getPlayer().getUniqueId())) {
			openPlayers.remove(e.getPlayer().getUniqueId());
		}
	}

	@EventHandler
	public static void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		ItemStack is = e.getItem();
		Action action = e.getAction();

		if (action == Action.RIGHT_CLICK_BLOCK) {
			Block b = e.getClickedBlock();
			Location l = b.getLocation();

			if (isSpawnWorld(l.getWorld())) {
				int x = l.getBlockX();
				int y = l.getBlockY();
				int z = l.getBlockZ();

				boolean used = false;

				if (x == -29 && y == 198 && z == 5) {
					if (isCaseItemStack(is, CaseType.TOOLS)) {
						if (isInventoryFull(p)) {
							sendMessage(p, "§cDu kannst keine Case öffnen, wenn dein Inventar voll ist.");
						} else {
							CaseSystem.open(p, CaseType.TOOLS);
							used = true;
						}
					} else {
						sendMessage(p, "§cDu hast keinen gültigen Schlüssel in der Hand.");
					}
				}

				if (x == -33 && y == 198 && z == 10) {
					if (isCaseItemStack(is, CaseType.MAJO)) {
						if (isInventoryFull(p)) {
							sendMessage(p, "§cDu kannst keine Case öffnen, wenn dein Inventar voll ist.");
						} else {
							CaseSystem.open(p, CaseType.MAJO);
							used = true;
						}
					} else {
						sendMessage(p, "§cDu hast keinen gültigen Schlüssel in der Hand.");
					}
				}

				if (x == -27 && y == 198 && z == 5) {
					if (isCaseItemStack(is, CaseType.BLOCKS)) {
						if (isInventoryFull(p)) {
							sendMessage(p, "§cDu kannst keine Case öffnen, wenn dein Inventar voll ist.");
						} else {
							CaseSystem.open(p, CaseType.BLOCKS);
							used = true;
						}
					} else {
						sendMessage(p, "§cDu hast keinen gültigen Schlüssel in der Hand.");
					}
				}

				if (x == -32 && y == 198 && z == 8) {
					if (isCaseItemStack(is, CaseType.LOTTO)) {
						if (isInventoryFull(p)) {
							sendMessage(p, "§cDu kannst keine Case öffnen, wenn dein Inventar voll ist.");
						} else {
							CaseSystem.open(p, CaseType.LOTTO);
							used = true;
						}
					} else {
						sendMessage(p, "§cDu hast keinen gültigen Schlüssel in der Hand.");
					}
				}

				if (x == -31 && y == 198 && z == 6) {
					if (isCaseItemStack(is, CaseType.PERK)) {
						if (isInventoryFull(p)) {
							sendMessage(p, "§cDu kannst keine Case öffnen, wenn dein Inventar voll ist.");
						} else {
							CaseSystem.open(p, CaseType.PERK);
							used = true;
						}
					} else {
						sendMessage(p, "§cDu hast keinen gültigen Schlüssel in der Hand.");
					}
				}

				if (x == -25 && y == 198 && z == 6) {
					if (isCaseItemStack(is, CaseType.SPECIAL)) {
						if (isInventoryFull(p)) {
							sendMessage(p, "§cDu kannst keine Case öffnen, wenn dein Inventar voll ist.");
						} else {
							CaseSystem.open(p, CaseType.SPECIAL);
							used = true;
						}
					} else {
						sendMessage(p, "§cDu hast keinen gültigen Schlüssel in der Hand.");
					}
				}

				if (used) {
					if (is.getAmount() == 1) {
						p.setItemInHand(null);
					} else {
						is.setAmount(is.getAmount() - 1);
					}
				}
			}
		} else if (action == Action.LEFT_CLICK_BLOCK) {
			Block b = e.getClickedBlock();
			Location l = b.getLocation();

			if (isSpawnWorld(l.getWorld())) {
				int x = l.getBlockX();
				int y = l.getBlockY();
				int z = l.getBlockZ();

				CaseType type = null;

				if (x == -29 && y == 198 && z == 5) {// TOOLS
					type = CaseType.TOOLS;
				}

				if (x == -33 && y == 198 && z == 10) { // MAJO
					type = CaseType.MAJO;
				}

				if (x == -27 && y == 198 && z == 5) { // BLOCKS
					type = CaseType.BLOCKS;
				}

				if (x == -32 && y == 198 && z == 8) { // LOTTO
					type = CaseType.LOTTO;
				}

				if (x == -31 && y == 198 && z == 6) { // PERK
					type = CaseType.PERK;
				}

				if (x == -25 && y == 198 && z == 6) { // SPECIAL
					type = CaseType.SPECIAL;
				}

				if (type == null) {
					return;
				}

				ArrayList<ItemStack> items = type.getItemsInView();
				int size = items.size();

				Gui.open(p, new Gui(type.getTitle(), size / 45 + (size % 45 == 0 ? 0 : 1)) {

					@Override
					public void onInit(DynamicObject args) {
						setBackgroundInAllPagesToGlassColor(15);

						for (int i = 0; i < size; i++) {
							setItemAt(i, items.get(i).clone());
						}
					}

					@Override
					public DynamicObject onClick(DynamicObject args) {
						int slot = args.getInt(Field.PLAYER_GUI_SLOT_POS);

						if (slot < size) {
							ItemStack is = items.get(slot);

							if (isModerator(p)) {
								giveItemToPlayer(p, is);
							}
						}

						return new DynamicObject(new FieldAndObject(Field.CANCEL, true));
					}
					
				}, 0);
			}
		}
	}
}