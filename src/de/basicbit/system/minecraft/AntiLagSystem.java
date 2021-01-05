package de.basicbit.system.minecraft;

import java.util.Collection;
import java.util.HashSet;

import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.tasks.TaskManager;
import net.minecraft.server.v1_8_R1.EntityPlayer;
import net.minecraft.server.v1_8_R1.PlayerConnection;

public class AntiLagSystem extends Listener implements Runnable {
    
    public static void init() {
        AntiLagSystem antiLagSystem = new AntiLagSystem();

        ListenerSystem.register(antiLagSystem);
        TaskManager.runAsyncLoop("AntiLagSystem", antiLagSystem, 10);
    }

    @Override
    public void run() {
        removeBugPlayers();
    }

    public static void removeBugPlayers() {
        HashSet<String> names = new HashSet<String>();
        Collection<? extends Player> players = getPlayers();
        int size = players.size();
        Player[] playerArray = players.toArray(new Player[size]);

        for (int i = 0; i < size; i++) {
            Player p = playerArray[i];
            String name = p.getName();
    
            if (names.contains(name)) {
                removeBugPlayer(name);
            } else {
                names.add(name);
            }
        }
    }

    public static void removeBugPlayer(String name) {
        TaskManager.runSyncTask("AntiLagKickClon", new Runnable() {

            @Override
            public void run() {
                Collection<? extends Player> players = getPlayers();
                int size = players.size();
                Player[] playerArray = players.toArray(new Player[size]);

                for (int i = 0; i < size; i++) {
                    Player p = playerArray[i];
                    String playerName = p.getName();

                    if (name.contentEquals(playerName)) {
                        CraftPlayer craftPlayer = (CraftPlayer) p;
                        EntityPlayer handle = craftPlayer.getHandle();
                        PlayerConnection playerConnection = handle.playerConnection;
                        playerConnection.disconnect("§cEs ist ein Fehler aufgetreten.");
                        craftPlayer.remove();
                    }
                }
            }
            
        });
    }
}