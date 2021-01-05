package de.basicbit.system.minecraft.listeners.player;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.basicbit.system.GlobalValues;
import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.events.PlayerPacketInEvent;
import net.minecraft.server.v1_8_R1.Packet;
import net.minecraft.server.v1_8_R1.PacketPlayInTabComplete;
import net.minecraft.server.v1_8_R1.PacketPlayOutTabComplete;

public class Chat extends Listener {

    @EventHandler
	public void onChat(final AsyncPlayerChatEvent e) {
		final Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();
		String msg = e.getMessage();

		e.setCancelled(true);

		if (GlobalValues.muted.contains(uuid) && !isInTeam(p)) {
			sendMessage(p, "§cDu bist gemuted.");
			return;
		}

		if (isBadMessage(msg)) {
			sendMessage(p, "§cBitte achte auf deine Wortwahl.");
			sendMessage(p, "§cDas ist nicht nett. :(");
			return;
		}

		if (isPromo(p)) {
			msg = msg.replace("&", "§");
		}

		if (isAdmin(p)) {
			if (msg.startsWith("#")) {

				while (msg.startsWith("#")) {
					msg = msg.substring(1);
				}

				Bukkit.broadcastMessage("§6[§4§lAchtung§6] §4§l" + p.getName() + "§4: " + msg);

				for (final Player pp : getPlayers()) {
					sendTitle(pp, "§4§lAchtung:", "§4§l" + p.getName() + ": §4" + msg);
					pp.playSound(p.getLocation(), Sound.WITHER_DEATH, 10, 20);
				}

				return;
			}
		}

		
		try {
			if ((System.currentTimeMillis() - GlobalValues.timeLastMessage.get(uuid)) < 3000 && !isInTeam(getPlayer(uuid))) {
				sendMessage(p, "§cBitte warte kurz...");
				GlobalValues.spamMessageCount.remove(uuid);
				return;
			}
		} catch (Exception exception) {
		}
		GlobalValues.timeLastMessage.put(uuid, System.currentTimeMillis());

		Bukkit.broadcastMessage(getChatName(p) + "§8 » §7" + msg);
	}

	@EventHandler
	public void onTabComplete(PlayerPacketInEvent e) {
		Player p = e.getPlayer();
		Packet packet = e.getPacket();
		
		if (packet instanceof PacketPlayInTabComplete) {
			e.setCancelled(true);
			ArrayList<String> playerNameList = new ArrayList<String>();
			String msg = ((PacketPlayInTabComplete)packet).a();
			String last = msg.split(" ")[msg.split(" ").length - 1].toLowerCase();

			for (Player t : getPlayers()) {

				if (isVanish(t)) {
					continue;
				}

				if (t.getName().toLowerCase().startsWith(last)) {
					playerNameList.add(t.getName());
				}
			}

			int playerNameCount = playerNameList.size();
			String[] playerNameArray = new String[playerNameList.size()];

			for (int i = 0; i < playerNameCount; i++) {
				playerNameArray[i] = playerNameList.get(i);
			}

			sendPacket(p, new PacketPlayOutTabComplete(playerNameArray));
		}
	}
}