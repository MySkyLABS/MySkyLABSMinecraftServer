package de.basicbit.system.minecraft;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import de.basicbit.system.minecraft.tasks.TaskManager;

public class ReallifeTime extends Utils {
    
    public static void init() {
        TaskManager.runAsyncLoop("TimeUpdater", new Runnable() {

            @Override
            public void run() {
                Calendar calendar = GregorianCalendar.getInstance();
                calendar.setTime(new Date());
                int time = (int)((calendar.get(Calendar.MINUTE) / 60d + calendar.get(Calendar.HOUR_OF_DAY)) * 1000 + 18000) % 24000;

                getSpawnWorld().setTime(time);
                getKnockItWorld().setTime(time);
            }
            
        }, 20);
    }
}