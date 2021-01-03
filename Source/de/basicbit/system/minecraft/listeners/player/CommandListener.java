package de.basicbit.system.minecraft.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import de.basicbit.system.minecraft.CommandSystem;
import de.basicbit.system.minecraft.Listener;

public class CommandListener extends Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();

		e.setMessage(e.getMessage().replace("&r", "").replace("&", "§"));

		String msg = e.getMessage().substring(1);

		if (isBadMessage(msg)) {
			sendMessage(p, "§cBitte achte auf deine Wortwahl.");
			sendMessage(p, "§cDas ist nicht nett. :(");
			return;
		}
		
		log(p.getName() + ": /" + msg);

		String cmd = msg.split(" ")[0];
		String[] args = new String[0];

		if (msg.split(" ").length != 1) {
			args = msg.substring(cmd.length() + 1).split(" ");
		}

		e.setCancelled(CommandSystem.onCommand(p, cmd, args, args.length == 0 ? "" : msg.substring(cmd.length() + 1)));
    }
}