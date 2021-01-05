package de.basicbit.system.minecraft;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerCommandEvent;

public class SaveReloadSystem extends Listener {
	
	public static void init() {
		ListenerSystem.register(new SaveReloadSystem());

		log("Players found: " + getPlayers().size());
		for (Player p : getPlayers()) {
			log("SimulateJoin: " + p.getName());
			simulateJoin(p);
		}
	}

	public static void close() {
		for (Player p : getPlayers()) {
			log("SimulateQuit: " + p.getName());
			simulateQuit(p);
		}
	}

	@EventHandler
	public void onServerCommand(ServerCommandEvent e) {
		String cmd = e.getCommand();
		
		if (e.getSender().isOp() && cmd.equalsIgnoreCase("rl")) {
			close();
		}
	}
}
