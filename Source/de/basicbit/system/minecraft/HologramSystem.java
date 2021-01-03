package de.basicbit.system.minecraft;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import de.basicbit.system.minecraft.cases.CaseType;
import de.basicbit.system.minecraft.events.PlayerChunkChangeEvent;
import de.basicbit.system.minecraft.tasks.TaskManager;
import net.minecraft.server.v1_8_R1.AxisAlignedBB;
import net.minecraft.server.v1_8_R1.EntityArmorStand;
import net.minecraft.server.v1_8_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R1.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R1.WorldServer;

public class HologramSystem extends Utils implements org.bukkit.event.Listener {

	public static ArrayList<HologramSystem> holograms = new ArrayList<HologramSystem>();
	public static int idOffset = 20000;

	public net.minecraft.server.v1_8_R1.EntityArmorStand handle;
	public Location location;
	public String text;
	public int id;
 
	public static void init() {
		Bukkit.getPluginManager().registerEvents(new HologramSystem(), MySkyLABS.getSystemPlugin());
		
		// SkyPvP
		new HologramSystem(new Location(getSpawnWorld(), -17.5, 197.75, 6.5),
				"§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●");
		new HologramSystem(new Location(getSpawnWorld(), -17.5, 197.5, 6.5), "§c§lSkyPvP");
		new HologramSystem(new Location(getSpawnWorld(), -17.5, 197.25, 6.5), "§aDer Kampf kann beginnen!");
		new HologramSystem(new Location(getSpawnWorld(), -17.5, 197, 6.5),
				"§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●");

		// Cases 
		new HologramSystem(new Location(getSpawnWorld(), -28.5, 200.75, 8.5),
				"§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●");
		new HologramSystem(new Location(getSpawnWorld(), -28.5, 200.5, 8.5), "§e§lCases");
		new HologramSystem(new Location(getSpawnWorld(), -28.5, 200.25, 8.5), "§aMöge die Macht mit dir sein!");
		new HologramSystem(new Location(getSpawnWorld(), -28.5, 200, 8.5),
				"§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●");

		//Info
		new HologramSystem(new Location(getSpawnWorld(), -8.5, 201.75, 21.5),
				"§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●");
		new HologramSystem(new Location(getSpawnWorld(), -8.5, 201.5, 21.5), "§e§lInfo");
		new HologramSystem(new Location(getSpawnWorld(), -8.5, 201.25, 21.5), "§aUnser Discord Server: §e§ndiscord.gg/h2XpncT");
		new HologramSystem(new Location(getSpawnWorld(), -8.5, 201, 21.5), "§aUnser Teamspeak Server: §e§nmyskylabs.de");
		// new HologramSystem(new Location(getSpawnWorld(), -8.5, 200.75, 21.5), "§aUnsere Website: §e§nmyskylabs.de");
		new HologramSystem(new Location(getSpawnWorld(), -8.5, 200.75, 21.5),
				"§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●§f─§6●");
		
		new HologramSystem(new Location(getSpawnWorld(), -21.5, 201, 27.5), "§aAlle Commands: §e§n/Help");

		new HologramSystem(new Location(getSpawnWorld(), -32.65, 198.75, 10.35), CaseType.MAJO.getTitle());
		new HologramSystem(new Location(getSpawnWorld(), -31.65, 198.75, 8.35), CaseType.LOTTO.getTitle());
		new HologramSystem(new Location(getSpawnWorld(), -30.65, 198.75, 6.35), CaseType.PERK.getTitle());
		new HologramSystem(new Location(getSpawnWorld(), -28.5, 198.75, 5.35), CaseType.TOOLS.getTitle());
		new HologramSystem(new Location(getSpawnWorld(), -26.5, 198.75, 5.35), CaseType.BLOCKS.getTitle());
		new HologramSystem(new Location(getSpawnWorld(), -24.35, 198.75, 6.35), CaseType.SPECIAL.getTitle());
	}
	
	private HologramSystem() { }

	public HologramSystem(final Location pos, final String t) {
		location = pos;
		text = t;

		final WorldServer world = ((CraftWorld) location.getWorld()).getHandle();

		holograms.add(this);

		id = idOffset + holograms.size();

		handle = new EntityArmorStand(world);

		try {
			Field idField = net.minecraft.server.v1_8_R1.Entity.class.getDeclaredField("id");
			idField.setAccessible(true);
			idField.set(handle, idOffset + id);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		final AxisAlignedBB armorStand_bb = handle.getBoundingBox();
		final double armorStand_height = (armorStand_bb.e - armorStand_bb.b) / 2;

		handle.setCustomNameVisible(true);
		handle.setGravity(false);
		handle.setInvisible(true);
		handle.setSmall(true);
		handle.setCustomName(text);
		handle.setBasePlate(false);
		handle.locX = location.getX();
		handle.locY = location.getY() - armorStand_height - 0.25;
		handle.locZ = location.getZ();

		log("Hologram created: " + handle.getId() + ", \"" + t + "§r\"");
	}

	public void setText(String t) {
		handle.setCustomName(text = t);

		for (Player p : location.getWorld().getPlayers()) {
			update(p);
		}
	}

	public String getText() {
		return text;
	}

	public void destroy() {
		holograms.remove(this);

		for (final Player p : getPlayers()) {
			remove(p);
		}
	}

	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent e) {
		TaskManager.runSyncTaskLater("RespawnHolograms", new Runnable() {

			@Override
			public void run() {
				updateStatic(e.getPlayer());
			}
			
		}, 1000);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		TaskManager.runSyncTaskLater("RespawnHolograms", new Runnable() {

			@Override
			public void run() {
				updateStatic(e.getPlayer());
			}
			
		}, 1000);
	}

	@EventHandler
	public void onChunkChange(PlayerChunkChangeEvent e) {
		updateStatic(e.getPlayer());
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		TaskManager.runSyncTaskLater("RespawnHolograms", new Runnable() {

			@Override
			public void run() {
				updateStatic(e.getEntity());
			}
			
		}, 1000);
	}

	@EventHandler
	public void onTeleport(PlayerTeleportEvent e) {
		TaskManager.runSyncTaskLater("RespawnHolograms", new Runnable() {

			@Override
			public void run() {
				updateStatic(e.getPlayer());
			}
			
		}, 1000);
	}

	public void remove(final Player p) {
		sendPacket(p, new PacketPlayOutEntityDestroy(handle.getId()));
	}

	public void send(final Player p) {
		sendPacket(p, new PacketPlayOutSpawnEntityLiving(handle));
	}

	public void update(final Player p) {
		if (isInWorld(p, location.getWorld())) {
			send(p);
		} else {
			remove(p);
		}
	}

	public static void updateStatic(final Player p) {
		for (final HologramSystem h : holograms) {
			h.update(p);
		}
	}
}
