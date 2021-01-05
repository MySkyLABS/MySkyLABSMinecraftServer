package de.basicbit.system.minecraft.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerCommandEvent;

import de.basicbit.system.minecraft.Listener;

public class ServerConsoleStopListener extends Listener {
    
    @EventHandler
    public void onCommand(ServerCommandEvent e) {
        String cmd = e.getCommand();

        if (cmd.equalsIgnoreCase("stop")) {
            stopServer();
        }
    }
}