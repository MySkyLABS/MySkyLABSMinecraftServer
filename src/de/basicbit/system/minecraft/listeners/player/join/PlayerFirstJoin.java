package de.basicbit.system.minecraft.listeners.player.join;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.tasks.TaskManager;

public class PlayerFirstJoin extends Listener {

    @EventHandler
    public static void FirstJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (p.getStatistic(Statistic.LEAVE_GAME) == 0) { // 10 Sekunden
            sendMessage(p, "§aHerzlich  willkommen auf §e§nMySkyLABS.de§a!");

            TaskManager.runAsyncTaskLater(new Runnable() {
                @Override
                public void run() {
                    sendMessage(p, "§aBeim §e§nTutorial NPC§a erhältst du Hilfe und mit");
                }
            }, 30);

            TaskManager.runAsyncTaskLater(new Runnable() {
                @Override
                public void run() {
                    sendMessage(p, "§e§n/help§a siehst du eine Liste mit allen Commands.");
                }
            }, 60);

            TaskManager.runAsyncTaskLater(new Runnable() {
                @Override
                public void run() {
                    sendMessage(p, "§eWenn du von einem §e§nPromo§a angeworben wurdest, kannst du");
                    sendMessage(p, "§edir mit§e§n/angeworben [Name des Promos]§a eine Belohung abholen.");
                }
            }, 60);
            addCoins(p, 250, " PlayerFirstJoin");
        }
    }
}
