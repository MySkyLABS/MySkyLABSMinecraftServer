package de.basicbit.system.minecraft;

import org.bukkit.Location;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import de.basicbit.system.database.UserValue;
import de.basicbit.system.database.UserData;
import de.basicbit.system.minecraft.tasks.TaskManager;

public class ScoreboardSystem extends Listener {
	
	public static void init() {
		ListenerSystem.register(new ScoreboardSystem());
	}

	@EventHandler
	public static void onWorldChange(PlayerChangedWorldEvent e) {
		update(e.getPlayer());
	}

	@EventHandler
	public static void onJoin(PlayerJoinEvent e) {
		update(e.getPlayer());
	}
	
	@EventHandler
	public static void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		
		Location pos1 = e.getFrom();
		Location pos2 = e.getTo();
		
		if (isInSkyPvP(pos1) != isInSkyPvP(pos2)) {
			TaskManager.runAsyncTaskLater(new Runnable(){
			
				@Override
				public void run() {
					update(p);
				}
			}, 5);
		}
	}

	@EventHandler
	public static void onTeleport(PlayerTeleportEvent e) {
		Player p = e.getPlayer();
		
		Location pos1 = e.getFrom();
		Location pos2 = e.getTo();
		
		if (isInSkyPvP(pos1) != isInSkyPvP(pos2)) {
			TaskManager.runAsyncTaskLater(new Runnable(){
			
				@Override
				public void run() {
					update(p);
				}
			}, 5);
		}
	}
	
	public static void update(Player p) {
		int playHours = p.getStatistic(Statistic.PLAY_ONE_TICK) / 72000;
		int coins = UserData.getInt(p, UserValue.coins);
		int bankCoins = UserData.getInt(p, UserValue.coinsBank);
		int tokens = UserData.getInt(p, UserValue.tokens);
		
		String title = MySkyLABS.name;
		
		if (inInSkyBlockWorld(p)) {
			title = "§2SkyBlock";
		} else if (isInSkyPvP(p)) {
			title = "§cSkyPvP";
		} else if (isInKnockIt(p)) {
			title = "§3Knock§b§lIt";
		}
		
		sendScorebord(p, title, new String[] {
			"",
			"§2§lVoteCoins",
			"§7▶§a " + tokens,
			"",
			"§6§lCoins",
			"§7▶§e " + (coins == Integer.MAX_VALUE ? "*" : fillNumberWithDots(coins)),
			"§7▶§3 " + (bankCoins == Integer.MAX_VALUE ? "*" : fillNumberWithDots(bankCoins)),
			"",
			"§b§lSpielzeit",
			"§7▶§e " + (playHours == 1 ? "Eine Stunde" : playHours + " Stunden"),
			""
		});
	}
}
