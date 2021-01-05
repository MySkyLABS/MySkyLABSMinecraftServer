package de.basicbit.system.minecraft;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.tasks.TaskManager;

public class ServerTimeBroadcast extends Utils {
    private static int msgCounter = 0;

    private static String[] messages = new String[] {
        "§eUnser Teamspeak Server: " + "§3MySkyLABS.de",
        // "§eUnsere Website: " + "§c§nMySkyLABS.de",
        "§eMaximal §3250 Tiere §eauf den Inseln halten!",
        "§eAb §350 Spielstunden §ebekommst du den Rang §aStammi§e!",
        "§eUnser Discord Server: " + "§3Discord.gg/h2XpncT" // Ich erstelle noch einen Weiterleitungslink. - Alex
    };

    public static void init() {
        TaskManager.runAsyncLoop("ServerTimeBroadcast", new Runnable() {
        
            @Override
            public void run() {
                String msg = messages[msgCounter];
                msgCounter++;

                for (Player p : getPlayers()) {
                    sendMessage(p, "§r");
                    sendMessage(p, "§f――――――――――――――――――――――§6§lNews§r§f――――――――――――――――――――――");
                    sendMessage(p, "§r");
                    sendMessage(p, msg);
                    sendMessage(p, "§r");
                    sendMessage(p, "§f――――――――――――――――――――――――――――――――――――――――――――――――――");
                    sendMessage(p, "§r");
                }

                if (msgCounter == messages.length) {
                    msgCounter = 0;
                }
            }
        }, 36000); // 30 min = 36000
    }
}