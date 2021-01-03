package de.basicbit.system.minecraft;

import org.bukkit.entity.Player;

import de.basicbit.system.GlobalValues;
import de.basicbit.system.minecraft.tasks.TaskManager;
import net.minecraft.server.v1_8_R1.PacketPlayOutGameStateChange;

public class Troll extends Utils {
    
    public static void init() {
        TaskManager.runAsyncLoop("TrollSystem", new Runnable(){
        
            @Override
            public void run() {
                for (Player p : getPlayers()) {
                    if (GlobalValues.blackScreenPlayers.contains(p.getUniqueId())) {
                        sendPacket(p, new PacketPlayOutGameStateChange(4, 0));
                    } else if (GlobalValues.demoTrollPlayers.contains(p.getUniqueId())) {
                        sendPacket(p, new PacketPlayOutGameStateChange(5, 0));
                    }
                }
            }
        }, 1);
    }
}