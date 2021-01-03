package de.basicbit.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Wolf;

import de.basicbit.system.minecraft.HologramSystem;
import de.basicbit.system.minecraft.PlayerReport;

public class GlobalValues {
	public static Random random = new Random(System.currentTimeMillis());

	public static HashMap<UUID, Integer> playerMode = new HashMap<UUID, Integer>();
	public static HashMap<UUID, UUID> lastHits = new HashMap<UUID, UUID>();
	public static HashMap<UUID, Integer> killStreaks = new HashMap<UUID, Integer>();
	public static HashMap<UUID, Wolf> dogPets = new HashMap<UUID, Wolf>();
	public static HashMap<UUID, BlockFace> blockFaces = new HashMap<UUID, BlockFace>();
	public static HashMap<UUID, Integer> warpStickCooldowns = new HashMap<UUID, Integer>();
	public static HashMap<UUID, UUID> lastMessage = new HashMap<UUID, UUID>();
	public static HashMap<UUID, Integer> backpackStartSlot = new HashMap<UUID, Integer>();
	public static HashMap<UUID, Long> timeLastMessage = new HashMap<UUID, Long>();
	public static HashMap<UUID, Integer> spamMessageCount = new HashMap<UUID, Integer>();
	public static HashMap<String, String> tpaRequests = new HashMap<String, String>();
	public static HashMap<Location, HologramSystem> showChestHolos = new HashMap<Location, HologramSystem>();

	public static ArrayList<UUID> flyingPlayersKnockIt = new ArrayList<UUID>();
	public static ArrayList<UUID> godModePlayers = new ArrayList<UUID>();
	public static ArrayList<UUID> devLogPlayers = new ArrayList<UUID>();
	public static ArrayList<UUID> fireLinePlayers = new ArrayList<UUID>();
	public static ArrayList<UUID> melonGunPlayers = new ArrayList<UUID>();
	public static ArrayList<UUID> demoTrollPlayers = new ArrayList<UUID>();
	public static ArrayList<UUID> blackScreenPlayers = new ArrayList<UUID>();
	public static ArrayList<UUID> tntGunPlayers = new ArrayList<UUID>();
	public static ArrayList<UUID> teleportCooldownPlayers = new ArrayList<UUID>();
	public static ArrayList<UUID> muted = new ArrayList<UUID>();
	public static ArrayList<UUID> reportBlocked = new ArrayList<UUID>();

    public static String discordToken = "NzQxMzg4MTM5MTQ2NTEwMzk4.Xy21gg.Tq6eaR_IvX97ygUkwFftj8ddSm0";
	public static HashMap<Integer, PlayerReport> playerReports = new HashMap<Integer, PlayerReport>();
	public static int playerReportCounter = 0;
	public static int magicValue = 0x43173AE0;
}
