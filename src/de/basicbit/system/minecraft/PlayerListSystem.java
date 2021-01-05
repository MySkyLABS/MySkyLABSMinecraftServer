package de.basicbit.system.minecraft;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import de.basicbit.system.minecraft.tasks.TaskManager;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class PlayerListSystem extends Listener {
	
	public static void init() {
		ListenerSystem.register(new PlayerListSystem());

		TaskManager.runAsyncLoop("PlayerList", new Runnable(){
		
			@Override
			public void run() {
				for (Player p : getPlayers()) {
					Gender gender = getGender(p);

					ArrayList<ArrayList<String>> sublists = getSublists(createListFromObjects(Group.values()).stream().map(obj -> obj.getName(gender)).collect(Collectors.toList()), 5);
					ArrayList<String> rows = new ArrayList<String>();

					for (ArrayList<String> sublist : sublists) {
						rows.add(String.join("§r§7, ", sublist));
					}

					String footer = String.join("§r§7,\n", rows);
					sendPlayerListHeaderFooter(p, "§r\n§7Herzlich Willkommen auf " + MySkyLABS.name + "§7!\n§r",
					"§r\n§7Ränge: " + footer + "\n§r");
				}
			}
		}, 20);
	}
	
	@EventHandler
	public static void onJoin(PlayerJoinEvent e) {
		update();
	}
	
	public static void update() {
		Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		Group[] groups = Group.values();

		for (int i = 0; i < groups.length; i++) {
			final String preTeam = ("" + i).length() == 1 ? "0" + i : "" + i;
			final Team team = scoreboard.registerNewTeam(preTeam + groups[i].toString().toLowerCase());
			team.setPrefix(groups[i].getPrefix());
		}

		for (final Player p : getPlayers()) {
			for (int i = 0; i < groups.length; i++) {
				if (getGroup(p) == groups[i]) {
					final String preTeam = ("" + i).length() == 1 ? "0" + i : "" + i;
					scoreboard.getTeam(preTeam + groups[i].toString().toLowerCase()).addPlayer(p);
					break;
				}
			}

			p.setScoreboard(scoreboard);
			p.setPlayerListName(getPrefixedName(p) + (isVanish(p) ? " §f[§fVANISH§f]" : (isLive(p) ? " §f[§c● §4LIVE§f]" : "")));
		}
	}
}
