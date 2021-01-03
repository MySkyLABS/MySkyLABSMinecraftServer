package de.basicbit.system.minecraft;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.tasks.TaskManager;

public class BonusTimer extends Utils {
    
    public static void init() {
        TaskManager.runAsyncLoop("BonusTimer", new Runnable() {
        
            @Override
            public void run() {
                HashMap<UUID, Integer> playHours = new HashMap<UUID, Integer>();
                for (Player p : getPlayers()) {
                    playHours.put(p.getUniqueId(), p.getStatistic(Statistic.PLAY_ONE_TICK) / 72000);
                }

                TaskManager.runAsyncTaskLater(new Runnable() {
                
                    @Override
                    public void run() {
                        for (Player p : getPlayers()) {
                            try {
                                if (p.isOnline() && playHours.get(p.getUniqueId()) != p.getStatistic(Statistic.PLAY_ONE_TICK) / 72000) {
                                    bonus(p, " BonusTimer");
                                }
                            } catch (Exception e) { }
                        }
                    }
                }, 15);
            }
        }, 20);
    }
}