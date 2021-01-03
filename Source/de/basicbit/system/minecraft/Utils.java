package de.basicbit.system.minecraft;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.schematic.SchematicFormat;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.v1_8_R1.CraftServer;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import de.basicbit.system.GlobalValues;
import de.basicbit.system.ServerCommunicationSystem;
import de.basicbit.system.database.Database;
import de.basicbit.system.database.UserData;
import de.basicbit.system.database.UserValue;
import de.basicbit.system.database.Var;
import de.basicbit.system.minecraft.cases.CaseType;
import de.basicbit.system.minecraft.commands.DisableRanks;
import de.basicbit.system.minecraft.geometry.Cuboid;
import de.basicbit.system.minecraft.geometry.Point3D;
import de.basicbit.system.minecraft.geometry.Size3D;
import de.basicbit.system.minecraft.listeners.player.join.AdminJoinCoins;
import de.basicbit.system.minecraft.skyblock.listeners.ShowChestListener;
import de.basicbit.system.minecraft.tasks.TaskManager;
import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.EntityPlayer;
import net.minecraft.server.v1_8_R1.EnumClientCommand;
import net.minecraft.server.v1_8_R1.EnumGamemode;
import net.minecraft.server.v1_8_R1.EnumParticle;
import net.minecraft.server.v1_8_R1.EnumPlayerInfoAction;
import net.minecraft.server.v1_8_R1.EnumTitleAction;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.IScoreboardCriteria;
import net.minecraft.server.v1_8_R1.MojangsonParser;
import net.minecraft.server.v1_8_R1.NBTTagCompound;
import net.minecraft.server.v1_8_R1.NBTTagList;
import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.Packet;
import net.minecraft.server.v1_8_R1.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R1.PacketPlayOutChat;
import net.minecraft.server.v1_8_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R1.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.server.v1_8_R1.PacketPlayOutScoreboardObjective;
import net.minecraft.server.v1_8_R1.PacketPlayOutScoreboardScore;
import net.minecraft.server.v1_8_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R1.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_8_R1.PlayerInfoData;
import net.minecraft.server.v1_8_R1.ScoreboardObjective;
import net.minecraft.server.v1_8_R1.ScoreboardScore;

@SuppressWarnings("deprecation")
public class Utils {

	public static String toTrimmed(UUID id) {
		return id.toString().replace("-", "");
	}

	public static void runCommand(String cmd) {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
	}

	public static void pasteSchematic(File schematicFile, Location pasteLoc) {
		try {
			EditSession editSession = WorldEdit.getInstance().getEditSessionFactory()
					.getEditSession(BukkitUtil.getLocalWorld(pasteLoc.getWorld()), 999999999);
			editSession.enableQueue();

			SchematicFormat.getFormat(schematicFile).load(schematicFile).paste(editSession,
					BukkitUtil.toVector(pasteLoc), true);

			editSession.flushQueue();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void sendPlayerListHeaderFooter(final Player p, final String header, final String footer) {
		final PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();

		try {
			final Field headerField = packet.getClass().getDeclaredField("a");
			headerField.setAccessible(true);
			headerField.set(packet, toChatBaseComponent(header));

			final Field footerField = packet.getClass().getDeclaredField("b");
			footerField.setAccessible(true);
			footerField.set(packet, toChatBaseComponent(footer));
		} catch (final Exception e) {
			e.printStackTrace();
		}

		sendPacket(p, packet);
	}

	public static <T> ArrayList<ArrayList<T>> getSublists(List<T> list, int subLength) {
		ArrayList<ArrayList<T>> result = new ArrayList<ArrayList<T>>();

		while (true) {
			ArrayList<T> subList = new ArrayList<T>();

			try {
				for (int i = 0; i < subLength; i++) {
					subList.add(list.get(0));
					list.remove(0);
				}
			} catch (Exception e) {
				if (subList.size() != 0) {
					result.add(subList);
				}

				return result;
			}

			result.add(subList);
		}
	}

	public static void sendSuccessSound(Player p) {
		p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
	}

	public static void sendErrorSound(Player p) {
		p.playSound(p.getLocation(), Sound.BLAZE_HIT, 1, 1);
	}

	public static UUID toUUID(String uuid) {
		uuid = uuid.trim();
		return uuid.length() == 32 ? getUUIDFromTrimmed(uuid.replaceAll("-", "")) : UUID.fromString(uuid);
	}

	public static UUID getUUIDFromSkyBlockWorldName(String worldName) {
		return toUUID(worldName.split("#")[0]);
	}

	public static int toInt(String s) {
		return Integer.parseInt(s);
	}

	public static int getNumberFromSkyBlockWorldName(String worldName) {
		return toInt(worldName.split("#")[1]);
	}

	public static UUID getUUIDFromSkyBlockWorld(World w) {
		return getUUIDFromSkyBlockWorldName(w.getName());
	}

	public static int getNumberFromSkyBlockWorld(World w) {
		return getNumberFromSkyBlockWorldName(w.getName());
	}

	public static void transfer(Player p, String server) {
		transfer(p.getUniqueId(), server);
	}

	public static void transfer(UUID id, String server) {
		ServerCommunicationSystem.sendCommand("proxy transfer " + toTrimmed(id) + " " + server);
	}

	public static UUID getUUIDFromTrimmed(final String trimmedUUID) {
		try {
			final StringBuilder builder = new StringBuilder(trimmedUUID.trim());

			builder.insert(20, "-");
			builder.insert(16, "-");
			builder.insert(12, "-");
			builder.insert(8, "-");

			return UUID.fromString(builder.toString());
		} catch (Exception e) {
			return null;
		}
	}

	public static void sendClickSound(Player p) {
		p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
	}

	public static void sendPacket(Player p, Packet packet) {
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}

	public static void giveItemToPlayer(Player p, ItemStack is) {
		final PlayerInventory inv = p.getInventory();
		final boolean invFull = inv.firstEmpty() == -1;

		if (invFull) {
			p.getWorld().dropItem(p.getLocation(), is);
			sendMessage(p, "§cDein Inventar ist voll.");
			sendMessage(p, "§cDas Item wurde stattdessen gedroppt.");
		} else {
			inv.addItem(is);
		}
	}

	public static void giveItemsToPlayer(Player p, ArrayList<ItemStack> items) {
		final PlayerInventory inv = p.getInventory();

		boolean isFull = false;

		for (ItemStack is : items) {
			boolean invFull = inv.firstEmpty() == -1;

			if (invFull) {
				p.getWorld().dropItem(p.getLocation(), is);
				isFull = true;
			} else {
				inv.addItem(is);
			}
		}

		if (isFull) {
			sendMessage(p, "§cDein Inventar ist voll.");
			sendMessage(p, "§cEs wurden Items gedroppt.");
		}
	}

	public static boolean isVanish(Player p) {
		try {
			return GlobalValues.playerMode.get(p.getUniqueId()) == 2;
		} catch (Exception e) {
			return false;
		}
	}

	public static ItemStack getSkullFromLink(String link, String id, int count) {
		String base64 = encodeBase64("{\"textures\":{\"SKIN\":{\"url\":\"" + link + "\"}}}");
		ItemStack is = new ItemStack(Material.SKULL_ITEM, count, (short) 3);
		net.minecraft.server.v1_8_R1.ItemStack mis = CraftItemStack.asNMSCopy(is);
		mis.setTag(MojangsonParser
				.parse("{SkullOwner:{Id:\"" + id + "\"},Properties:{textures:[0:{Value:\"" + base64 + "\"}]}}"));
		return CraftItemStack.asBukkitCopy(mis);
	}

	public static String encodeBase64(String input) {
		return new String(Base64.encodeBase64(input.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
	}

	public static String decodeBase64(String input) {
		return new String(Base64.decodeBase64(input.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
	}

	public static void addNBTToPlayer(final Player p, final NBTTagCompound compound) {
		final EntityPlayer nmsEntity = ((CraftPlayer) p).getHandle();
		final NBTTagCompound tag = new NBTTagCompound();
		nmsEntity.c(tag);

		for (final Object key : compound.c()) {
			tag.set((String) key, compound.get((String) key));
		}

		nmsEntity.a(tag);
	}

	public static NBTTagCompound getNBTFromPlayer(final Player p) {
		final NBTTagCompound compound = new NBTTagCompound();
		((CraftPlayer) p).getHandle().b(compound);
		return compound;
	}

	public static CraftServer getServer() {
		return (CraftServer) Bukkit.getServer();
	}

	public static String getDataFromPlayer(final Player p) {
		final NBTTagCompound playerData = getNBTFromPlayer(p);
		final NBTTagCompound data = new NBTTagCompound();

		data.set("Inventory", (NBTTagList) playerData.get("Inventory"));
		data.set("EnderItems", (NBTTagList) playerData.get("EnderItems"));
		data.setInt("foodLevel", playerData.getInt("foodLevel"));
		data.setFloat("Health", playerData.getFloat("Health"));
		data.setInt("XpLevel", playerData.getInt("XpLevel"));
		data.setInt("XpP", playerData.getInt("XpP"));
		data.setInt("XpSeed", playerData.getInt("XpSeed"));
		data.setInt("XpTotal", playerData.getInt("XpTotal"));

		return encodeBase64(data.toString());
	}

	public static NBTTagCompound parseNbt(String nbt) {
		return MojangsonParser.parse(nbt);
	}

	public static void setDataToPlayer(final Player p, final String data) {
		if (data.length() == 0) {
			addNBTToPlayer(p, MojangsonParser.parse(
					"{Health:20.0f,XpSeed:0,EnderItems:[],XpP:0,foodLevel:20,XpTotal:0,XpLevel:0,Inventory:[],}"));
			return;
		}

		addNBTToPlayer(p, parseNbt(decodeBase64(data)));
	}

	public static <T> List<T> createCopy(List<T> list) {
		ArrayList<T> copy = new ArrayList<T>();

		for (int i = 0; i < list.size(); i++) {
			copy.add(list.get(i));
		}

		return copy;
	}

	public static void setVanish(Player p, boolean b) {
		if (isVanish(p) && !b) {
			GlobalValues.playerMode.put(p.getUniqueId(), 0);
			sendMessageToAllPlayers(getChatName(p) + "§a ist jetzt online.");
			AdminJoinCoins.power(p);
		}

		if (!isVanish(p) && b) {
			GlobalValues.playerMode.put(p.getUniqueId(), 2);
			sendMessageToAllPlayers(getChatName(p) + "§c ist jetzt offline.");
		}

		updateVanish(p);
	}

	public static void updateVanish(Player p) {
		for (Player t : getPlayers()) {
			updateVanish(p, t);
			updateVanish(t, p);
		}

		PlayerListSystem.update();
	}

	public static void updateVanish(Player p, Player t) {
		if (p.getUniqueId().equals(t.getUniqueId())) {
			return;
		}

		if (!isInTeam(t)) {
			if (isVanish(p)) {
				t.hidePlayer(p);
			} else {
				t.showPlayer(p);
			}
		} else if (!isAdmin(t) && isAdmin(p)) {
			if (isVanish(p)) {
				t.hidePlayer(p);
			} else {
				t.showPlayer(p);
			}
		} else if (!isOwner(t) && isOwner(p)) {
			if (isVanish(p)) {
				t.hidePlayer(p);
			} else {
				t.showPlayer(p);
			}
		}
	}

	public static void sendGameMode(Player p, Player t, EnumGamemode mode) {
		PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.UPDATE_GAME_MODE);
		List<PlayerInfoData> list = new ArrayList<PlayerInfoData>();
		EntityPlayer t_ep = getEntityPlayer(t);
		list.add(new PlayerInfoData(packet, t_ep.getProfile(), t_ep.ping, mode, toChatBaseComponent("?")));
		setValue(packet, "b", list);
		sendPacket(p, packet);
	}

	public static void fakePacket(final Player p, final Packet packet) {
		packet.a(((CraftPlayer) p).getHandle().playerConnection);
	}

	public static boolean isGodMode(Player p) {
		return GlobalValues.godModePlayers.contains(p.getUniqueId());
	}

	public static int getCoins(Player p) {
		return UserData.getInt(p, UserValue.coins);
	}

	public static void setCoins(Player p, int c, String reason) {
		UserData.set(p, UserValue.coins, c);
		System.out.println("coins set");
		ScoreboardSystem.update(p);
	}

	public static void addCoins(Player p, int c, String reason) {
		int coins = getCoins(p);

		if (coins == Integer.MAX_VALUE) {
			return;
		}

		setCoins(p, coins + c, reason);

		try {
			MoneyLogger.write(p, c, "[ADD]" + reason);
			log(p.getName() + ": " + reason + " [ADD] " + c + " Coins");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void removeCoins(Player p, int c, String reason) {
		int coins = getCoins(p);

		if (coins == Integer.MAX_VALUE) {
			return;
		}

		if (!hasCoins(p, c)) {
			setCoins(p, 0, reason);
			return;
		}

		setCoins(p, coins - c, reason);

		try {
			MoneyLogger.write(p, c, "[REM]" + reason);
			log(p.getName() + ": " + reason + " [REM] " + c + " Coins");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int getCoins(UUID id) {
		return UserData.getInt(id, UserValue.coins);
	}

	public static void setCoins(UUID id, int c, String reason) {
		Player p = getPlayer(id);

		System.out.println("coins set");

		if (p != null) {
			setCoins(p, c, reason);
		} else {
			UserData.set(id, UserValue.coins, c);
		}

		try {
			MoneyLogger.write(p, c, "[SET]" + reason);
			log(p.getName() + ": " + reason + " [SET] " + c + " Coins");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void addCoins(UUID id, int c, String reason) {
		int coins = getCoins(id);

		if (coins == Integer.MAX_VALUE) {
			return;
		}

		setCoins(id, coins + c, reason);
	}

	public static boolean hasCoins(Player p, int c) {
		return getCoins(p) >= c;
	}

	public static boolean hasCoins(UUID id, int c) {
		return getCoins(id) >= c;
	}

	public static void removeCoins(UUID id, int c, String reason) {
		int coins = getCoins(id);

		if (coins == Integer.MAX_VALUE) {
			return;
		}

		if (!hasCoins(id, c)) {
			setCoins(id, 0, reason);
			return;
		}

		setCoins(id, coins - c, reason);
	}

	public static void sendMessageInWorld(World w, String msg) {
		for (Player p : w.getPlayers()) {
			sendMessage(p, msg);
		}
	}

	public static int getBankCoins(Player p) {
		return UserData.getInt(p, UserValue.coinsBank);
	}

	public static void setBankCoins(Player p, int c, String reason) {
		UserData.set(p, UserValue.coinsBank, c);
		ScoreboardSystem.update(p);

		try {
			MoneyLogger.write(p, c, "[SET]" + reason);
			log(p.getName() + ": " + reason + " [SET] " + c + " Coins");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void addBankCoins(Player p, int c, String reason) {
		setBankCoins(p, getBankCoins(p) + c, reason + " (" + c + ")");
	}

	public static void removeBankCoins(Player p, int c, String reason) {
		setBankCoins(p, getBankCoins(p) - c, reason);
	}

	public static int getBankInvest(Player p) {
		return UserData.getInt(p, UserValue.coinsBankInvest);
	}

	public static void setBankInvest(Player p, int c, String reason) {
		UserData.set(p, UserValue.coinsBankInvest, c);
		ScoreboardSystem.update(p);

		try {
			MoneyLogger.write(p, c, "[SET]" + reason);
			log(p.getName() + ": " + reason + " [SET] " + c + " Coins");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int getBankCoins(UUID id) {
		return UserData.getInt(id, UserValue.coinsBank);
	}

	public static void setBankCoins(UUID id, int c, String reason) {
		Player p = getPlayer(id);

		if (p != null) {
			setBankCoins(p, c, reason);
		} else {
			UserData.set(id, UserValue.coinsBank, c);
		}

	}

	public static void addBankCoins(UUID id, int c, String reason) {
		setBankCoins(id, getBankCoins(id) + c, reason);

	}

	public static void removeBankCoins(UUID id, int c, String reason) {
		setBankCoins(id, getBankCoins(id) - c, reason);

	}

	public static int getBankInvest(UUID id) {
		return UserData.getInt(id, UserValue.coinsBankInvest);
	}

	public static void setBankInvest(UUID id, int c, String reason) {
		Player p = getPlayer(id);

		if (p != null) {
			setBankInvest(p, c, reason);
		} else {
			UserData.set(id, UserValue.coinsBankInvest, c);
		}

		try {
			MoneyLogger.write(p, c, "[SET]" + reason);
			log(p.getName() + ": " + reason + " [SET] " + c + " Investlevel");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int getInvPos(int x, int y) {
		return y * 9 + x;
	}

	public static void sendParticles(Player p, EnumParticle particle, double x, double y, double z, double x2,
			double y2, double z2, int count) {
		sendPacket(p, new PacketPlayOutWorldParticles(particle, true, (float) x, (float) y, (float) z, (float) x2,
				(float) y2, (float) z2, 0f, count));
	}

	public static void sendParticles(Player p, EnumParticle particle, Location location, Size3D size, int count) {
		sendParticles(p, particle, location.getX(), location.getY(), location.getZ(), size.getXSize(), size.getYSize(),
				size.getZSize(), count);
	}

	public static void sendParticle(Player p, EnumParticle particle, Location location) {
		sendPacket(p, new PacketPlayOutWorldParticles(particle, true, (float) location.getX(), (float) location.getY(),
				(float) location.getZ(), 0f, 0f, 0f, 0f, 1));
	}

	public static void sendColoredParticles(Player p, EnumParticle particle, Location location, float r, float g,
			float b, int count) {
		sendParticles(p, particle, location.getX(), location.getY(), location.getZ(), r - 1.F, g, b, count);
	}

	public static void sendColoredParticle(Player p, EnumParticle particle, Location location, float r, float g,
			float b) {
		sendParticles(p, particle, location.getX(), location.getY(), location.getZ(), r, g, b, 1);
	}

	public static void sendColoredParticles(Player p, EnumParticle particle, double x, double y, double z, float r,
			float g, float b, int count) {
		sendParticles(p, particle, x, y, z, r - 1.F, g, b, count);
	}

	public static void sendColoredParticle(Player p, EnumParticle particle, double x, double y, double z, float r,
			float g, float b) {
		sendParticles(p, particle, x, y, z, r, g, b, 1);
	}

	public static void crash(Player p) {
		Random random = GlobalValues.random;

		TaskManager.runAsyncTask(new Runnable() {

			@Override
			public void run() {
				sendPacket(p,
						new PacketPlayOutWorldParticles(EnumParticle.EXPLOSION_HUGE, true, Float.MAX_VALUE,
								Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE,
								Float.MAX_VALUE, Integer.MAX_VALUE));

				Location loc = p.getLocation();

				for (int i = 0; i < 10000; i++) {
					sendPacket(p,
							new PacketPlayOutWorldParticles(EnumParticle.EXPLOSION_HUGE, true, (float) loc.getX(),
									(float) loc.getY(), (float) loc.getZ(), 10, 10, 10, random.nextInt(1000),
									random.nextInt(1000) + 1000));
					sendPacket(p,
							new PacketPlayOutWorldParticles(EnumParticle.FLAME, true, (float) loc.getX(),
									(float) loc.getY(), (float) loc.getZ(), 10, 10, 10, random.nextInt(1000),
									random.nextInt(1000) + 1000));
					sendPacket(p,
							new PacketPlayOutWorldParticles(EnumParticle.BARRIER, true, (float) loc.getX(),
									(float) loc.getY(), (float) loc.getZ(), 10, 10, 10, random.nextInt(1000),
									random.nextInt(1000) + 1000));
					sendPacket(p,
							new PacketPlayOutWorldParticles(EnumParticle.LAVA, true, (float) loc.getX(),
									(float) loc.getY(), (float) loc.getZ(), 10, 10, 10, random.nextInt(1000),
									random.nextInt(1000) + 1000));
					sendPacket(p,
							new PacketPlayOutWorldParticles(EnumParticle.FIREWORKS_SPARK, true, (float) loc.getX(),
									(float) loc.getY(), (float) loc.getZ(), 10, 10, 10, random.nextInt(1000),
									random.nextInt(1000) + 1000));
				}
			}
		});
	}

	public static void teleportWithCooldown(Player p, Location l) {

		if (isAdmin(p)) {
			TaskManager.runSyncTask("TeleportWithCooldown", new Runnable() {

				@Override
				public void run() {
					p.teleport(l);
				}
			});
			return;
		}

		UUID id = p.getUniqueId();
		if (GlobalValues.teleportCooldownPlayers.contains(id)) {
			sendMessage(p, "§cDiese Aktion ist momentan nicht möglich.");
			return;
		} else {
			GlobalValues.teleportCooldownPlayers.add(id);
		}

		sendMessage(p, "§aDu wirst in Kürze teleportiert.");
		sendMessage(p, "§aBitte bewege dich nicht!");

		Location oldLocation = p.getLocation();

		TaskManager.runAsyncTask(new Runnable() {

			@Override
			public void run() {
				try {
					if (oldLocation.distance(p.getLocation()) > 1) {
						breakTeleport(p);
					} else {
						sendTitle(p, "§3Teleport in: ", "§f§l3");

						TaskManager.runAsyncTaskLater(new Runnable() {

							@Override
							public void run() {
								try {
									if (oldLocation.distance(p.getLocation()) > 1) {
										breakTeleport(p);
									} else {
										sendTitle(p, "§3Teleport in: ", "§f§l2");

										TaskManager.runAsyncTaskLater(new Runnable() {

											@Override
											public void run() {
												try {
													if (oldLocation.distance(p.getLocation()) > 1) {
														breakTeleport(p);
													} else {
														sendTitle(p, "§3Teleport in: ", "§f§l1");

														TaskManager.runAsyncTaskLater(new Runnable() {

															@Override
															public void run() {
																try {
																	if (oldLocation.distance(p.getLocation()) > 1) {
																		breakTeleport(p);
																	} else {
																		sendTitle(p, "§3Teleport in: ", "§f§lGo!");

																		TaskManager.runSyncTask("TeleportWithCooldown",
																				new Runnable() {

																					@Override
																					public void run() {
																						p.teleport(l);
																					}
																				});

																		UUID id = p.getUniqueId();
																		if (GlobalValues.teleportCooldownPlayers
																				.contains(id)) {
																			GlobalValues.teleportCooldownPlayers
																					.remove(id);
																		}
																	}
																} catch (Throwable throwable) {
																	breakTeleport(p);
																}
															}

														}, 20);
													}
												} catch (Throwable throwable) {
													breakTeleport(p);
												}
											}

										}, 20);
									}
								} catch (Throwable throwable) {
									breakTeleport(p);
								}
							}

						}, 20);
					}
				} catch (Throwable throwable) {
					breakTeleport(p);
				}
			}

		});
	}

	private static void breakTeleport(Player p) {
		sendMessage(p, "§cDer Teleport wurde abgebrochen. :(");
		sendTitle(p, "§cTeleport abgebrochen", "");

		UUID id = p.getUniqueId();
		if (GlobalValues.teleportCooldownPlayers.contains(id)) {
			GlobalValues.teleportCooldownPlayers.remove(id);
		}
	}

	public static void setGodMode(Player p, boolean b) {
		if (GlobalValues.godModePlayers.contains(p.getUniqueId()) && !b) {
			GlobalValues.godModePlayers.remove(p.getUniqueId());
		}

		if (!GlobalValues.godModePlayers.contains(p.getUniqueId()) && b) {
			GlobalValues.godModePlayers.add(p.getUniqueId());
		}
	}

	public static void respawnPlayer(Player p) {
		if (p.isDead()) {
			fakePacket(p, new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN));
			p.teleport(getSpawn());
		}
	}

	public static boolean canPlayerRunVanillaCommands(Player p) {
		return isDev(p);
	}

	public static void sendMessage(Player p, String msg) {
		p.sendMessage(MySkyLABS.prefix + msg);
	}

	public static void sendMessage(Player p, String msg, boolean removePrefix) {
		if (removePrefix) {
			p.sendMessage(msg);
		} else {
			p.sendMessage(MySkyLABS.prefix + msg);
		}
	}

	public static void sendMessageToAllPlayers(String msg) {
		for (Player p : getPlayers()) {
			sendMessage(p, msg);
		}
	}

	public static void sendMessageToAllPlayers(String msg, boolean removePrefix) {
		for (Player p : getPlayers()) {
			sendMessage(p, msg, removePrefix);
		}
	}

	public static Player getPlayer(UUID id) {
		return Bukkit.getPlayer(id);
	}

	public static Player getPlayer(String name) {
		return Bukkit.getPlayerExact(name);
	}

	public static Collection<? extends Player> getPlayers() {
		return Bukkit.getOnlinePlayers();
	}

	public static void log(Object msg) {
		if (MySkyLABS.disableLog) {
			return;
		}

		if (msg == null) {
			log("NULL");
			return;
		}

		for (String line : msg.toString().split("\n")) {
			Bukkit.getConsoleSender().sendMessage("[ServerSystem] " + line);

			for (Player p : getPlayers()) {
				if (MySkyLABS.debugMode || GlobalValues.devLogPlayers.contains(p.getUniqueId())) {
					p.sendMessage("§7> " + line.replace("\t", "-> "));
				}
			}
		}
	}

	public static void sendChatBaseComponent(Player p, String msg) {
		sendPacket(p, new PacketPlayOutChat(toChatBaseComponent(msg, false)));
	}

	public static void sendChatBaseComponent(Player p, ChatMessageBuilder chatMessageBuilder) {
		sendChatBaseComponent(p, chatMessageBuilder.toString());
	}

	public static boolean isDev(Player p) {
		if (DisableRanks.disabled) {
			return false;
		}

		if (MySkyLABS.debugMode) {
			// return true;
		}

		return p.getUniqueId().toString().replace("-", "").contentEquals("22dddc7a2e584c0d82fbf91694571e4b") // BasicBit
				|| p.getUniqueId().toString().replace("-", "").contentEquals("4fee4c90aa504d7c85395349480f2614") // gratisCobalt
				|| p.getUniqueId().toString().replace("-", "").contentEquals("818b465d6d7b4aad9ffebda8c3a30db0"); // Crepper710
	}

	public static Location getDirectionPoint(Location location, double space) {
		double x = location.getX();
		double y = location.getY();
		double z = location.getZ();
		double yaw = (double) location.getYaw();
		double pitch = (double) location.getPitch();
		return new Location(location.getWorld(),
				(float) (x - (Math.cos(Math.toRadians(pitch)) * Math.sin(Math.toRadians(yaw)) * space)),
				(float) (y - (Math.sin(Math.toRadians(pitch)) * space)),
				(float) (z + (Math.cos(Math.toRadians(pitch)) * Math.cos(Math.toRadians(yaw)) * space)), 0f, 0f);
	}

	public static boolean isSolidBlock(Block block) {
		return block.getType().isSolid();
	}

	public static boolean collideWithEntitys(Location location, HashSet<Entity> ignoreList) {
		for (Entity e : location.getWorld().getEntities()) {
			Cuboid cuboid = Cuboid.getFromBounds(e);
			if (cuboid.contains(new Point3D(location)) && !ignoreList.contains(e)) {
				return true;
			}
		}

		return false;
	}

	public static boolean collideWithEntity(Location location, Entity en) {
		for (Entity e : location.getWorld().getEntities()) {
			Cuboid cuboid = Cuboid.getFromBounds(e);
			if (cuboid.contains(new Point3D(location)) && !en.getUniqueId().equals(e.getUniqueId())) {
				return true;
			}
		}

		return false;
	}

	public static boolean collideWithEntitys(Location location, Entity... ignoreArray) {
		return collideWithEntitys(location, new HashSet<Entity>(Arrays.asList(ignoreArray)));
	}

	public static Location rayCast(Location location, double maxLength, double precision, Entity... ignoreList) {
		double space;
		for (space = -precision; space < maxLength; space += precision) {
			Location pos = getDirectionPoint(location, space + precision);

			if (isSolidBlock(pos.getBlock()) || collideWithEntitys(pos, ignoreList)) {
				return getDirectionPoint(location, space);
			}
		}

		return getDirectionPoint(location, space);
	}

	public static Location rayCastIgnoreEntitys(Location location, double maxLength, double precision) {
		double space;
		for (space = -precision; space < maxLength; space += precision) {
			Location pos = getDirectionPoint(location, space + precision);

			if (isSolidBlock(pos.getBlock())) {
				return getDirectionPoint(location, space);
			}
		}

		return getDirectionPoint(location, space);
	}

	public static <T> boolean containsHash(List<T> list, T obj) {
		int hashCode = obj.hashCode();

		for (T obj2 : list) {
			if (obj2.hashCode() == hashCode) {
				return true;
			}
		}

		return false;
	}

	public static IChatBaseComponent toChatBaseComponent(final String str) {
		return toChatBaseComponent(str, true);
	}

	public static IChatBaseComponent toChatBaseComponent(final String str, boolean textOnly) {
		if (textOnly) {
			return ChatSerializer.a("{\"text\":\"" + str + "\"}");
		} else {
			return ChatSerializer.a(str);
		}
	}

	public static boolean isInventoryFull(Inventory inv) {
		return inv.firstEmpty() == -1;
	}

	public static void payAll(int coins, String reason) {
		for (Player p : getPlayers()) {
			addCoins(p, coins, reason);

			sendMessage(p, "§aDu hast §e" + (coins == 1 ? "ein Coin" : coins + " Coins") + "§a bekommen.");
		}

	}

	public static void stopServer() {

		for (final Player p : getPlayers()) {
			if (MySkyLABS.debugMode) {
				transfer(p.getUniqueId(), "MainServer");
			} else {
				p.kickPlayer("§cDer Server wird neu gestartet.");
			}
		}

		try {
			Thread.sleep(500);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}

		Bukkit.shutdown();
	}

	public static int getUniqueDayValue() {
		Calendar c = Calendar.getInstance();
		short year = (short) c.get(Calendar.YEAR);
		byte month = (byte) c.get(Calendar.MONTH);
		byte day = (byte) c.get(Calendar.DAY_OF_MONTH);

		return (((int) year) << 16) + (((int) month) << 8) + ((int) day);
	}

	public static boolean hasItem(Player p, int id) {
		return p.getInventory().contains(id);
	}

	public static boolean hasItemStack(Player p, ItemStack is) {
		return p.getInventory().contains(is);
	}

	public static int getItemStacksWithName(Player p, String name) {
		int i = 0;
		for (ItemStack is : p.getInventory().getContents()) {
			if (is != null && is.hasItemMeta() && is.getItemMeta().hasDisplayName()
					&& is.getItemMeta().getDisplayName().contentEquals(name)) {
				i += is.getAmount();
			}
		}
		return i;
	}

	public static void removeItemStacksWithName(Player p, String name, int i) {
		int x = 0;
		Inventory inv = p.getInventory();
		for (ItemStack is : inv.getContents()) {
			if (is != null && is.hasItemMeta() && is.getItemMeta().hasDisplayName()
					&& is.getItemMeta().getDisplayName().contentEquals(name)) {
				int c = is.getAmount();
				if (c > i - x) {
					is.setAmount(c - (i - x));
					return;
				} else if (c == i - x) {
					inv.remove(is);
				} else {
					x += c;
					inv.remove(is);
				}
			}
		}
	}

	public static boolean hasItemStacksWithName(Player p, String name, int i) {
		return getItemStacksWithName(p, name) >= i;
	}

	public static boolean hasItem(Player p, int id, int count) {
		return p.getInventory().contains(id, count);
	}

	@SuppressWarnings("unchecked")
	public static <T> ArrayList<T> createListFromObjects(T... ts) {
		ArrayList<T> result = new ArrayList<T>();

		for (T t : ts) {
			if (t != null) {
				result.add(t);
			}
		}

		return result;
	}

	public static void removeItem(Player p, int id, int count) {
		p.getInventory().removeItem(new ItemStack(id, count));
	}

	public static void removeItemStack(Player p, ItemStack is) {
		p.getInventory().removeItem(new ItemStack(is));
	}

	public static boolean isBadMessage(String msg) {
		return isBadMessage(msg, true);
	}

	public static boolean isBadMessage(String msg, final boolean first) {
		msg = msg.toLowerCase();

		if (msg.toLowerCase().contains("fick")) {
			return true;
		}

		if (msg.toLowerCase().contains("bitch")) {
			return true;
		}

		if (msg.toLowerCase().contains("arsch")) {
			return true;
		}

		if (msg.toLowerCase().contains("hure")) {
			return true;
		}

		if (msg.toLowerCase().contains("huso")) {
			return true;
		}

		if (msg.toLowerCase().contains("anal")) {
			return true;
		}

		if (msg.toLowerCase().contains("sex")) {
			return true;
		}

		if (msg.toLowerCase().contains("fotze")) {
			return true;
		}

		if (msg.toLowerCase().contains("fett")) {
			return true;
		}

		if (msg.toLowerCase().contains("cock")) {
			return true;
		}

		if (msg.toLowerCase().contains("opfer")) {
			return true;
		}

		if (msg.toLowerCase().contains("wixxer") || msg.toLowerCase().contains("wichser")) {
			return true;
		}

		if (msg.toLowerCase().contains("pimmel")) {
			return true;
		}

		if (msg.toLowerCase().contains("schwanz") || msg.toLowerCase().contains("schwans")) {
			return true;
		}

		if (msg.toLowerCase().contains("penner")) {
			return true;
		}

		if (msg.toLowerCase().contains("spast")) {
			return true;
		}

		if (msg.toLowerCase().contains("bastard")) {
			return true;
		}

		if (msg.toLowerCase().contains("penis")) {
			return true;
		}

		if (msg.toLowerCase().contains("scheis")) {
			return true;
		}

		if (msg.toLowerCase().contains("drecks")) {
			return true;
		}

		if (msg.toLowerCase().contains("gay")) {
			return true;
		}

		if (msg.toLowerCase().contains("scheiss")) {
			return true;
		}

		if (msg.toLowerCase().contains("kack")) {
			return true;
		}

		if (msg.toLowerCase().contains("leckmich")) {
			return true;
		}

		if (msg.toLowerCase().contains("behindert")) {
			return true;
		}

		if (msg.toLowerCase().contains("dildo")) {
			return true;
		}

		if (msg.toLowerCase().contains("pottsau")) {
			return true;
		}

		if (msg.toLowerCase().contains("erotik")) {
			return true;
		}

		if (msg.toLowerCase().contains("hitler")) {
			return true;
		}

		if (msg.toLowerCase().contains("siegheil")) {
			return true;
		}

		if (msg.toLowerCase().contains("nazi")) {
			return true;
		}

		if (msg.toLowerCase().contains("porn")) {
			return true;
		}

		if (msg.toLowerCase().contains("piss")) {
			return true;
		}

		if (first) {
			msg = msg.replace(":3", "b").replace("$", "s").replace("€", "e").replace("ß", "s").replace("[3", "b")
					.replace("|3", "b").replace("4", "a").replace("@", "a").replace("[)", "d").replace("|)", "d")
					.replace("(", "c").replace("5", "s").replace("3", "e").replace("0", "o").replace("2", "s")
					.replace("1", "i").replace("9", "g").replace("|", "i").replaceAll("[A-Za-z0-9]", "");

			return isBadMessage(msg, false);
		} else {
			return false;
		}
	}

	public static void setValue(Object obj, String name, Object value) {
		try {
			Field field = obj.getClass().getDeclaredField(name);
			field.setAccessible(true);

			if (Modifier.isFinal(field.getModifiers())) {
				setValue(field, "modifiers", field.getModifiers() & ~Modifier.FINAL);
			}

			field.set(obj, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Object getValue(Object obj, String name) {
		try {
			Field field = obj.getClass().getDeclaredField(name);
			field.setAccessible(true);
			return field.get(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void setValue(Class<?> clazz, Object obj, String name, Object value) {
		try {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);

			if (Modifier.isFinal(field.getModifiers())) {
				setValue(field, "modifiers", field.getModifiers() & ~Modifier.FINAL);
			}

			field.set(obj, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Object getValue(Class<?> clazz, Object obj, String name) {
		try {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			return field.get(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<Player> getPlayers(World w) {
		return w.getPlayers();
	}

	public static boolean isOnline(UUID id) {
		return getPlayer(id) != null;
	}

	public static boolean isOwnerOfWorld(World w, Player p) {
		if (!isSkyblockWorld(w)) {
			return false;
		}

		return getUUIDFromSkyBlockWorld(w).equals(p.getUniqueId());
	}

	public static boolean isOnline(String name) {
		return getPlayer(name) != null;
	}

	public static String getChatName(Player p) {
		return getChatName(getGroup(p), getGender(p), p.getName());
	}

	public static Gender getGender(Player p) {
		return getGender(p.getUniqueId());
	}

	public static Gender getGender(UUID id) {
		return UserData.getBoolean(id, UserValue.isWomen) ? Gender.WOMAN : Gender.MAN;
	}

	public static Group getGroup(UUID id) {
		return Group.valueOf(UserData.get(id, UserValue.playerGroup));
	}

	public static void setGender(Player p, Gender gender) {
		UserData.set(p, UserValue.isWomen, gender == Gender.WOMAN);
	}

	public static String getChatName(Group group, Gender gender, String name) {
		return group.getName(gender) + " §8┃ " + group.getPrefix().split(" §8┃ ")[1] + name;
	}

	public static String getChatName(UUID id) {
		return getChatName(Group.valueOf(UserData.get(id, UserValue.playerGroup)), getGender(id),
				UserData.get(id, UserValue.playerName));
	}

	public static String getPrefixedName(Player p) {
		return getPrefix(p) + p.getName();
	}

	public static Player getRandomPlayer() {
		final ArrayList<Player> players = new ArrayList<Player>();
		for (final Player p : getPlayers()) {
			players.add(p);
		}

		return players.get(GlobalValues.random.nextInt(getPlayers().size()));
	}

	public static int getBonusCoins(Player p) {
		return (int) Math.pow(2, UserData.getInt(p, UserValue.coinsBankInvest)) * 50;
	}

	public static void bonus(Player p, String reason) {
		int coins = getBonusCoins(p);
		addBankCoins(p, coins, reason);
		sendMessage(p, "§aDu hast einen Bonus von §e" + coins + " Coins§a erhalten.");

	}

	public static void bonusForAll(String reason) {
		for (final Player p : getPlayers()) {
			bonus(p, reason);
		}
	}

	public static String getPrefix(Player p) {
		return getGroup(p).getPrefix();
	}

	public static Group getGroup(Player p) {
		return Group.valueOf(UserData.get(p, UserValue.playerGroup));
	}

	public static void clearChat(Player p) {
		TaskManager.runAsyncTask(new Runnable() {

			@Override
			public void run() {

				for (int i = 0; i < 100; i++) {
					for (Player t : getPlayers()) {
						t.sendMessage(
								"\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
					}
				}
				sendMessageToAllPlayers("§cDer Chat wurde von " + getChatName(p) + "§c geleert.");
			}
		});
	}

	public static void clearChat() {
		for (Player p : getPlayers()) {
			clearChat(p);
		}
	}

	public static Entity getEntityById(int id) {
		for (World w : Bukkit.getWorlds()) {
			for (Entity e : w.getEntities()) {
				if (e.getEntityId() == id) {
					return e;
				}
			}
		}

		return null;
	}

	public static Entity getPlayerByEntityId(int id) {
		for (Player p : getPlayers()) {
			if (p.getEntityId() == id) {
				return p;
			}
		}

		return null;
	}

	public static boolean isOwner(Player p) {
		return getGroup(p) == Group.OWNER;
	}

	public static boolean isAdmin(Player p) {
		return getGroup(p) == Group.ADMIN || isOwner(p) || isDev(p);
	}

	public static boolean isModerator(Player p) {
		return getGroup(p) == Group.MODERATOR || isAdmin(p);
	}

	public static boolean isSupporter(Player p) {
		return getGroup(p) == Group.SUPPORTER || isModerator(p);
	}

	public static boolean isTestSupporter(Player p) {
		return getGroup(p) == Group.TEST_SUPPORTER || isSupporter(p);
	}

	public static boolean isContent(Player p) {
		return getGroup(p) == Group.CONTENT || isAdmin(p);
	}

	public static boolean isBuilder(Player p) {
		return getGroup(p) == Group.BUILDER || isAdmin(p);
	}

	public static boolean isInTeam(Player p) {
		return isBuilder(p) || isContent(p) || isTestSupporter(p);
	}

	public static boolean isPromoPlus(Player p) {
		return getGroup(p) == Group.PROMO_PLUS || isInTeam(p);
	}

	public static boolean isPromo(Player p) {
		return getGroup(p) == Group.PROMO || isPromoPlus(p);
	}

	public static boolean isSenpai(Player p) {
		return getGroup(p) == Group.SENPAI || isPromo(p);
	}

	public static boolean isHero(Player p) {
		return getGroup(p) == Group.HERO || isSenpai(p);
	}

	public static boolean isStammi(Player p) {
		return getGroup(p) == Group.STAMMI || isHero(p);
	}

	public static boolean isInventoryFull(Player p) {
		return isInventoryFull(p.getInventory());
	}

	public static void setLive(Player p, boolean b) {

		if (isLive(p) && !b) {
			GlobalValues.playerMode.put(p.getUniqueId(), 0);
		}

		if (!isLive(p) && b) {
			setVanish(p, false);
			GlobalValues.playerMode.put(p.getUniqueId(), 1);
		}

		updateVanish(p);
	}

	public static boolean isLive(Player p) {
		try {
			return GlobalValues.playerMode.get(p.getUniqueId()) == 1;
		} catch (Exception e) {
			return false;
		}
	}

	public static void antiChatDetect(Player p, String msg) {
		for (final Player t : getPlayers()) {
			if (isInTeam(t)) {
				t.sendMessage("§6[§3Team§6] §c§lAntiCheat§8 » " + getChatName(p) + "§c: " + msg);
			}
		}
	}

	public static void setMaintenance(boolean b) {
		Database.setVar(Var.maintenance, b + "");
	}

	public static Player getPlayer(NetworkManager networkManager) {
		for (Player p : getPlayers()) {
			if (getNetworkManager(p) == networkManager) {
				return p;
			}
		}

		return null;
	}

	public static EntityPlayer getEntityPlayer(Player p) {
		return ((CraftPlayer) p).getHandle();
	}

	public static NetworkManager getNetworkManager(Player p) {
		return getEntityPlayer(p).playerConnection.networkManager;
	}

	public static void callEvent(Event e) {
		Bukkit.getPluginManager().callEvent(e);
	}

	public static void showStacktrace() {
		try {
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean isMaintenance() {
		return Database.getVar(Var.maintenance).equalsIgnoreCase("true");
	}

	public static <T extends Enum<?>> T searchEnum(Class<T> enumeration, String search) {
		for (T each : enumeration.getEnumConstants()) {
			if (each.name().compareToIgnoreCase(search) == 0) {
				return each;
			}
		}
		return null;
	}

	public static void sendTitle(final Player p, final String title, final String subtitle) {
		sendPacket(p, new PacketPlayOutTitle(0, 40, 0));
		sendPacket(p, new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, toChatBaseComponent(subtitle)));
		sendPacket(p, new PacketPlayOutTitle(EnumTitleAction.TITLE, toChatBaseComponent(title)));
	}

	public static void sendActionBar(Player p, String text) {
		sendPacket(p, new PacketPlayOutChat(toChatBaseComponent(text, true), (byte) 2));
	}

	public static boolean hasBankCoins(Player p, int c) {
		return getBankCoins(p) >= c;
	}

	public static void sendScorebord(final Player p, final String title, final String[] lines) {

		final net.minecraft.server.v1_8_R1.Scoreboard scoreboardObj = new net.minecraft.server.v1_8_R1.Scoreboard();

		final ScoreboardObjective obj = new ScoreboardObjective(scoreboardObj, title, IScoreboardCriteria.b);
		obj.setDisplayName(title);

		sendPacket(p, new PacketPlayOutScoreboardObjective(obj, 1));
		sendPacket(p, new PacketPlayOutScoreboardObjective(obj, 0));
		sendPacket(p, new PacketPlayOutScoreboardDisplayObjective(1, obj));

		for (int i = 0; i < lines.length; i++) {

			String prefix = i + "";
			if (prefix.length() < 2) {
				for (int j = 0; j < 2 - prefix.length(); j++) {
					prefix = "0" + prefix;
				}
			}

			final String line = "§" + prefix.toCharArray()[0] + "§" + prefix.toCharArray()[1] + lines[i];
			final ScoreboardScore score = new ScoreboardScore(scoreboardObj, obj, line);
			score.setScore(0);
			sendPacket(p, new PacketPlayOutScoreboardScore(score));
		}
	}

	public static void simulateRejoin(Player p) {
		TaskManager.runSyncTaskLater("SimulateJoin", new Runnable() {

			@Override
			public void run() {
				simulateQuit(p);
			}

		}, 10);

		TaskManager.runSyncTaskLater("SimulateJoin", new Runnable() {

			@Override
			public void run() {
				simulateJoin(p);
			}

		}, 20);
	}

	public static void simulateQuit(Player p) {
		Bukkit.getPluginManager().callEvent(new PlayerQuitEvent(p, ""));
	}

	public static void simulateJoin(Player p) {
		Bukkit.getPluginManager().callEvent(new PlayerLoginEvent(p, "", p.getAddress().getAddress()));
		Bukkit.getPluginManager().callEvent(new PlayerJoinEvent(p, ""));
	}

	public static boolean isInSkyPvP(Location l) {
		return l.getY() <= 160 && equalsWorld(l.getWorld(), getSpawnWorld());
	}

	public static boolean isInSkyPvP(Player p) {
		return isInSkyPvP(p.getLocation());
	}

	public static boolean isSkyblockWorld(World w) {
		return w.getName().contains("#");
	}

	public static boolean inInSkyBlockWorld(Player p) {
		return isSkyblockWorld(p.getWorld());
	}

	public static World getSpawnWorld() {
		return Bukkit.getWorld("Spawn");
	}

	public static boolean isSpawnWorld(World w) {
		return w.getName().contentEquals(getSpawnWorld().getName());
	}

	public static Location getSpawn() {
		return new Location(getSpawnWorld(), -16.5, 199.5, 21.5, 90f, 0);
	}

	public static World getKnockItWorld() {
		return Bukkit.getWorld("KnockIt");
	}

	public static boolean isKnockItWorld(World w) {
		return w.getName().contentEquals(getKnockItWorld().getName());
	}

	public static boolean isInKnockIt(Player p) {
		return isKnockItWorld(p.getWorld());
	}

	public static ItemStack getMajoItemStack(int i) {
		final ItemStack is = new ItemStack(351, i, (short) 7);
		final ItemMeta im = is.getItemMeta();
		im.setDisplayName("§f§lMajo");
		im.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 10, true);
		is.setItemMeta(im);
		return is;
	}

	public static ItemStack getCakeItemStack(int i) {
		final ItemStack is = new ItemStack(397, 1, (short) 3);
		Bukkit.getUnsafe().modifyItemStack(is,
				"{display:{Name:\"§r§i§r§d§lCake\"},SkullOwner:{Id:\"7cce0961-9c7d-425f-b4b9-9427c1621a33\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTIxZDhkOWFlNTI3OGUyNmJjNDM5OTkyM2QyNWNjYjkxNzNlODM3NDhlOWJhZDZkZjc2MzE0YmE5NDM2OWUifX19\"}]}}}");
		final ItemMeta im = is.getItemMeta();
		im.setDisplayName("§d§lKuchen");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§r");
		lore.add("§e§l- 30 Sekunden zwei extra Herzen");
		lore.add("§r");
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	public static ItemStack getCupCakeItemStack(int i) {
		final ItemStack is = new ItemStack(397, 1, (short) 3);
		Bukkit.getUnsafe().modifyItemStack(is,
				"{display:{Name:\"§d§lC§f§lu§d§lp§f§lc§d§la§f§lk§d§le\"},SkullOwner:{Id:\"5aa3ad05-40c7-45be-b265-45dc5995a7c5\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGVhZjZkMjY3ZTNhYjNjMGE5ZjUyNjUwM2E0MjFlNmNhMmE2M2RiZmE5YzZhZGEzYmM5ZjhhOWI1NTYzNyJ9fX0=\"}]}}}");
		final ItemMeta im = is.getItemMeta();
		im.setDisplayName("§r§i§r§d§lC§f§lu§d§lp§f§lc§d§la§f§lk§d§le");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§r");
		lore.add("§e§l- 10 Sekunden Absorption");
		lore.add("§r");
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	public static ItemStack getDonutItemStack(int i) {
		final ItemStack is = new ItemStack(397, 1, (short) 3);
		Bukkit.getUnsafe().modifyItemStack(is,
				"{display:{Name:\"§r§i§r§5§lDonut\"},SkullOwner:{Id:\"b48503a4-6dec-438c-a3bc-6b5da7fb1fde\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODM3YzliODJiMTg2NjU2ZTlmNjM2M2EyYTFjNmE0YjViOTNjZmE5ZWY0ZGFkNmYxNmI5NGViYjVlMzYyNjc4In19fQ==\"}]}}}");
		final ItemMeta im = is.getItemMeta();
		im.setDisplayName("§5§lDonut");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§r");
		lore.add("§e§l- 10 Sekunden zufälliger positiver Effekt");
		lore.add("§r");
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	public static ItemStack getCookieItemStack(int i) {
		final ItemStack is = new ItemStack(397, 1, (short) 3);
		Bukkit.getUnsafe().modifyItemStack(is,
				"{display:{Name:\"§r§i§r§6§lK§e§le§6§lk§e§ls\"},SkullOwner:{Id:\"03879f6c-1ed0-475c-ba68-e144d0ed7755\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTFmZDk4MWNlMTVhMjk4MGE5NjExNWM4ZDY3MDk5ZjRiY2I0OTFmMmU2Yzc5MDg0N2E3OWMzNjBlNWZjIn19fQ==\"}]}}}");
		final ItemMeta im = is.getItemMeta();
		im.setDisplayName("§6§lK§e§le§6§lk§e§ls");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§r");
		lore.add("§e- Kekse sind toll.");
		lore.add("§r");
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	public static ItemStack getPancakeItemStack(int i) {
		final ItemStack is = new ItemStack(397, 1, (short) 3);
		Bukkit.getUnsafe().modifyItemStack(is,
				"{display:{Name:\"§r§i§r§6§lPancakes\"},SkullOwner:{Id:\"2a341cc8-437d-4e83-88fc-fa3a153f58e9\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGVjNmEzN2FhNjIwNzdmNmU2ZTg1YjQyYTA2MjI5OTM2MzZjM2IzNjEzMmRiYzM0MWYxZjEzMDZmNjFjMmYyNiJ9fX0=\"}]}}}");
		final ItemMeta im = is.getItemMeta();
		im.setDisplayName("§6§lPancakes");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§r");
		lore.add("§e§l- 15 Sekunden Nachtsicht & Unterwasseratmung");
		lore.add("§r");
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	public static ItemStack getKidSurpriseItemStack(int i) {
		final ItemStack is = new ItemStack(397, 1, (short) 3);
		Bukkit.getUnsafe().modifyItemStack(is,
				"{display:{Name:\"§r§i§r§4§lÜ§f§l-Ei\"},SkullOwner:{Id:\"3c39942c-01d0-4c42-8d3f-3f5ac112b5c5\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTI0Y2RlMzBjMjNkOTMzNGVhOWJjMmVkMTU2MDZkNzBkY2NlYmQyZDZiMWZkYTlkYzlhMGQyNzUwMjZiZTdjNiJ9fX0=\"}]}}}");
		final ItemMeta im = is.getItemMeta();
		im.setDisplayName("§4§lÜ§f§l-Ei");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§r");
		lore.add("§e§l- Eine tolle Überraschung");
		lore.add("§r");
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	public static ItemStack getHoneyItemStack(int i) {
		final ItemStack is = new ItemStack(397, 1, (short) 3);
		Bukkit.getUnsafe().modifyItemStack(is,
				"{display:{Name:\"§r§i§r§6§lHonig\"},SkullOwner:{Id:\"162250d4-eaf6-4e68-a862-60f606215bbd\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGI4OTQyYjIyZGRkMjQ0MWYyY2U2YWQ2NTY3Zjg3NzRhOTdjYTZiMmE4MzJhM2FhZjVlNWNjNmJhY2JmIn19fQ==\"}]}}}");
		final ItemMeta im = is.getItemMeta();
		im.setDisplayName("§6§lHonig");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§r");
		lore.add("§e§l- 10 Sekunden Regeneration II");
		lore.add("§r");
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	public static ItemStack getPopcornItemStack(int i) {
		final ItemStack is = new ItemStack(397, 1, (short) 3);
		Bukkit.getUnsafe().modifyItemStack(is,
				"{display:{Name:\"§r§i§r§e§lPopcorn\"},SkullOwner:{Id:\"fa122542-3930-4ac2-a1ee-8a023b00dc3f\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzM1NDdkZDU1OTE2MmVkMzU0MTFhZWM5ZjY4MzI4MTVmYjA5ZTRkOGFkNDE2YmIyMWNiNGMxYzk0N2ViYjVhIn19fQ==\"}]}}}");
		final ItemMeta im = is.getItemMeta();
		im.setDisplayName("§e§lPopcorn");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§r");
		lore.add("§e§l- 10 Sekunden Feuerresistenz");
		lore.add("§r");
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	public static ItemStack getMushroomItemStack(int i) {
		final ItemStack is = new ItemStack(397, 1, (short) 3);
		Bukkit.getUnsafe().modifyItemStack(is,
				"{display:{Name:\"§r§i§r§b§lMagic Mushroom\"},SkullOwner:{Id:\"89824269-0fa1-440d-945c-b6fcd38c2d84\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDgzNGQ2MjllZWRmMzMyMWM1ODI2ZmE5Yjg5NDg2ZWYwMmNhYmJlYWRkMjU1ODg3NThmOGI1YzAxMjNmMGIwNyJ9fX0=\"}]}}}");
		final ItemMeta im = is.getItemMeta();
		im.setDisplayName("§b§lMagic Mushroom");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§r");
		lore.add("§e§l- Setzt dich auf Drogen ;)");
		lore.add("§r");
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	public static ItemStack getColaItemStack(int i) {
		final ItemStack is = new ItemStack(397, 1, (short) 3);
		Bukkit.getUnsafe().modifyItemStack(is,
				"{display:{Name:\"§r§i§r§4§lCoca Cola\"},SkullOwner:{Id:\"bb0664c6-5ddb-436f-addf-2d654c61c5a0\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjlmYTMxNjM1YTZiY2JkNjAwNjEzNTYxNTQ5YTMwYzE4ODg4ZWQ2MmZiMDViZjJkYTIzMTM5OGY4ODJlYTNkIn19fQ==\"}]}}}");
		final ItemMeta im = is.getItemMeta();
		im.setDisplayName("§4§lCoca Cola");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§r");
		lore.add("§e§l- 30 Sekunden Schnelligkeit");
		lore.add("§r");
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	public static ItemStack getChocolateItemStack(int i) {
		final ItemStack is = new ItemStack(397, 1, (short) 3);
		Bukkit.getUnsafe().modifyItemStack(is,
				"{display:{Name:\"§r§i§rChocolate\"},SkullOwner:{Id:\"eaa47b1d-cb57-48c8-af33-cf4fd9872341\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODE5Zjk0OGQxNzcxOGFkYWNlNWRkNmUwNTBjNTg2MjI5NjUzZmVmNjQ1ZDcxMTNhYjk0ZDE3YjYzOWNjNDY2In19fQ==\"}]}}}");
		final ItemMeta im = is.getItemMeta();
		im.setDisplayName("§8§lSchokolade");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§r");
		lore.add("§e§l- 10 Sekunden Stärke");
		lore.add("§r");
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	public static boolean isCaseItemStack(ItemStack is, CaseType type) {
		ItemStack key = type.getKey();

		return is != null && is.getType() == key.getType() && is.getItemMeta() != null
				&& is.getItemMeta().getDisplayName().contentEquals(key.getItemMeta().getDisplayName());
	}

	public static boolean isInWorld(Entity e, World w) {
		return isInWorld(e, w.getName());
	}

	public static boolean isInWorld(final Entity e, final String w) {
		return e.getWorld().getName().contentEquals(w);
	}

	public static boolean equalsWorld(World w, World w2) {
		try {
			return w.getName().contentEquals(w2.getName());
		} catch (Exception e) {
			return false;
		}
	}

	public static int removeItemsFromWorld(World w) {
		int itemCounter = 0;

		for (Entity e : w.getEntities()) {
			if (e instanceof Item) {
				itemCounter += ((Item) e).getItemStack().getAmount();
				e.remove();
			}
		}

		return itemCounter;
	}

	public static void removeAllItems() {
		int itemCounter = 0;

		for (World w : Bukkit.getWorlds()) {
			itemCounter += removeItemsFromWorld(w);
		}

		if (itemCounter == 1) {
			sendMessageToAllPlayers("§cEin Item wurde entfernt.");
		} else {
			sendMessageToAllPlayers("§c" + itemCounter + "§c Items wurden entfernt.");
		}
	}

	public static int hexToInt(String hex) {
		hex = hex.toUpperCase();

		if (hex.length() == 8) {
			char[] hexAsCharArray = hex.toCharArray();
			String chars = "0123456789ABCDEF";
			for (int i = 0; i < 8; i++) {
				if (!chars.contains(hexAsCharArray[i] + "")) {
					return 0;
				}
			}
		} else {
			return 0;
		}

		return (int) Long.parseLong(hex, 16);
	}

	public static HashSet<Block> getBlocksByRadius(Block middle, double r) {
		HashSet<Block> list = new HashSet<Block>();

		list.add(middle);

		for (int i = 0; i < 360; i++) {
			Point3D point = new Point3D(middle).getDirectionPoint(i, 0, r);
			Block b = point.getBlock(middle.getWorld());

			list.add(b);
		}

		if (r >= 1) {
			for (Block b : getBlocksByRadius(middle, r - 1)) {
				list.add(b);
			}
		}

		return list;
	}

	public static String intToHex(int i) {
		return Integer.toHexString(i);
	}

	public static String fillNumberWithDots(int i) {
		return String.format("%,d", i);
	}

	public static void safeBackup() {
		TaskManager.runAsyncTask(new Runnable() {

			@Override
			public void run() {
				log("saving Backup...");
				String timeStamp = new SimpleDateFormat("dd.MM.yyyy HH-mm-ss").format(Calendar.getInstance().getTime());
				File srcDir = new File("./skyblock/player_worlds/");
				File destDir = new File(
						com.google.common.io.Files.simplifyPath("./backups/skyblock (" + timeStamp + ")/"));

				if (!destDir.exists()) {
					destDir.mkdir();
				}
				for (File playerWorld : srcDir.listFiles()) {
					try {
						FileUtils.copyDirectoryToDirectory(playerWorld, destDir);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	public static void loadHolograms(Player p, UUID id, int number) throws IOException {
		File folder = new File(toTrimmed(id) + "#" + number);
		File file = new File("./" + folder + "/holograms.txt");
		Path path = Paths.get("./" + folder + "/holograms.txt");
		World w = p.getWorld();
		if (!file.exists()) {
			file.createNewFile();
		}
		List<String> allLines = Files.readAllLines(path);
		for (String line : allLines) {
			String[] pos = line.split(";");
			double x = Double.parseDouble(pos[0]);
			double y = Double.parseDouble(pos[1]);
			double z = Double.parseDouble(pos[2]);
			Location loc = new Location(w, x, y, z);
			Block b = loc.getBlock();
			if (ShowChestListener.isShowChest(b)) {
				Chest chest = (Chest) b.getState();
				Inventory inv = chest.getInventory();
				String title = inv.getTitle();
				GlobalValues.showChestHolos.put(loc, new HologramSystem(new Location(w, x + 0.5, y + 1.2, z + 0.5), title));
			}
		}
	}

	/*
	 * "#" ist ein gibt ein punkt an alles andere ist KEIN punkt
	 
	public static Point3D[][] getPositionsByShape(String... shape) {
		List<List<Point3D>> out = new ArrayList<>();
		for (int i = 0; i < shape.length; i++) {
			String s = shape[i];
			List<Point3D> currList = new ArrayList<>();
			for (int j = 0; j < s.length(); j++) {
				char curr = s.charAt(j);
				if (curr != '#') {
					continue;
				}
				currList.add(new Point3D(i, j, 0));
			}
			out.add(currList);
		}
		return out.stream().map(new Function<List<Point3D>, Point3D[]>() {
			@Override
			public Point3D[] apply(List<Point3D> t) {
				return t.stream().toArray(size -> new Point3D[size]);
			}
		}).toArray(size -> new Point3D[size][]);
	}*/
}
