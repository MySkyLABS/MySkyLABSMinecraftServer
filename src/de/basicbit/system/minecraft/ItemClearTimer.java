package de.basicbit.system.minecraft;

import java.util.Date;

import de.basicbit.system.minecraft.tasks.TaskManager;

@SuppressWarnings("deprecation")
public class ItemClearTimer extends Utils {

    public static void init() {
        TaskManager.runAsyncLoop("ItemClear", new Runnable() {
        
            @Override
            public void run() {
                final Date date = new Date(System.currentTimeMillis());
                
                if ((date.getMinutes() + 1) % 10 == 0 && date.getSeconds() == 0) {
                    // 60 Sekunden vorher!
                    sendMessageToAllPlayers("§cAlle Items werden in einer Minute entfernt!");
                }

                if ((date.getMinutes() + 1) % 10 == 0 && date.getSeconds() == 30) {
                    // 30 Sekunden vorher!
                    sendMessageToAllPlayers("§cAlle Items werden in 30 Sekunden entfernt!");
                }

                if ((date.getMinutes() + 1) % 10 == 0 && date.getSeconds() == 50) {
                    // 10 Sekunden vorher!
                    sendMessageToAllPlayers("§cAlle Items werden in 10 Sekunden entfernt!");
                }

                if (date.getMinutes() % 10 == 0 && date.getSeconds() == 0) {
                    removeAllItems();
                }
            }
        }, 20);
    }
}