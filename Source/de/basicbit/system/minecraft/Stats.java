package de.basicbit.system.minecraft;

import org.bukkit.entity.Player;

import de.basicbit.system.database.UserData;
import de.basicbit.system.database.UserValue;

public class Stats extends Utils {
    
	public static double getKD(double kills, double deaths) {
		return kills / deaths;
    }
    
    public static int getSkyPvPKills(Player p) {
        return UserData.getInt(p, UserValue.skyPvpKills);
    }

    public static int getSkyPvPDeaths(Player p) {
        return UserData.getInt(p, UserValue.skyPvpDeaths);
    }

    public static void setSkyPvPKills(Player p, int kills) {
        UserData.set(p, UserValue.skyPvpKills, kills);
    }

    public static void setSkyPvPDeaths(Player p, int deaths) {
        UserData.set(p, UserValue.skyPvpDeaths, deaths);
    }

    public static double getSkyPvPKD(Player p) {
        return getKD(getSkyPvPKills(p), getSkyPvPDeaths(p));
    }

    public static int getKnockItKills(Player p) {
        return UserData.getInt(p, UserValue.knockItKills);
    }

    public static int getKnockItDeaths(Player p) {
        return UserData.getInt(p, UserValue.knockItDeaths);
    }

    public static void setKnockItKills(Player p, int kills) {
        UserData.set(p, UserValue.knockItKills, kills);
    }

    public static void setKnockItDeaths(Player p, int deaths) {
        UserData.set(p, UserValue.knockItDeaths, deaths);
    }

    public static double getKnockItKD(Player p) {
        return getKD(getKnockItKills(p), getKnockItDeaths(p));
    }
}