package de.basicbit.system.minecraft.listeners;

import org.bukkit.event.EventHandler;

import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.MySkyLABS;
import de.basicbit.system.minecraft.events.ServerCommunicationSystemMessageEvent;

public class Update extends Listener {

    @EventHandler
    public static void onServerCommunicationSystemMessage(ServerCommunicationSystemMessageEvent e) {
        String msg = e.getMessage();

        if (msg.equalsIgnoreCase("update") && MySkyLABS.debugMode) {
            stopServer();
        }
    }
}