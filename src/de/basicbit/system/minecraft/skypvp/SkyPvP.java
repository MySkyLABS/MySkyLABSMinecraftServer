package de.basicbit.system.minecraft.skypvp;

import java.util.UUID;

import org.bukkit.entity.Player;

import de.basicbit.system.GlobalValues;

public class SkyPvP {
    
    public static void init() {

    }

    public static int getKillStreak(Player p) {
        UUID id = p.getUniqueId();
        if (GlobalValues.killStreaks.containsKey(id)) {
            return GlobalValues.killStreaks.get(id);
        } else {
            return 0;
        }
    }

    public static void setKillStreak(Player p, int kills) {
        GlobalValues.killStreaks.put(p.getUniqueId(), kills);
    }

    public static void resetKillstreak(Player p) {
        UUID id = p.getUniqueId();
        if (GlobalValues.killStreaks.containsKey(id)) {
            GlobalValues.killStreaks.remove(id);
        }
    }

    public static void addKillToStreak(Player p) {
        setKillStreak(p, getKillStreak(p) + 1);
    }
}