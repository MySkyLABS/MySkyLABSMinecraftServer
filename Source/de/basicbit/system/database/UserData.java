package de.basicbit.system.database;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import de.basicbit.system.minecraft.Group;
import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.ListenerSystem;
import de.basicbit.system.minecraft.commands.DisableRanks;

public class UserData extends Listener {

    static ArrayList<UserData> loadedUsers = new ArrayList<UserData>();

    public String ip = "";
    public String teamspeakId = "";

    public String minecraftId = "";
    public String minecraftPlayerData = "";
    public String playerName = "";
    public String playerGroup = "MEMBER";
    public String tokens = "0";
    public String isWomen = "0";

    public String coins = "0";
    public String coinsBank = "0";
    public String coinsBankInvest = "0";

    public String dayBonusDay = "0";
    public String dayBonusOpened = "0";

    public String banReasons = "";
    public String bannedUntil = "0";

    public String skyPvpKills = "0";
    public String skyPvpDeaths = "0";
    
    public String knockItKills = "0";
    public String knockItDeaths = "0";

    public String enlistedBy = "";

    public String skullUsed = "0";
    public String shopUsed = "0";
    public String kitUsed = "0";
    
    public String canJoinMaintenance = "0";
    
    public String island0Monsters = "0";
    public String island0UsersVisit = "0";
    public String island0UsersPvp = "0";
    public String island0UsersInteract = "0";
    public String island0UsersBuild = "0";
    public String island0Trusts = "";
    public String island0Bans = "";
    public String island0SpawnVisit = "0;69;0;0f;0f";
    public String island0SpawnTrust = "0;69;0;0f;0f";

    public String island1Monsters = "0";
    public String island1UsersVisit = "0";
    public String island1UsersPvp = "0";
    public String island1UsersInteract = "0";
    public String island1UsersBuild = "0";
    public String island1Trusts = "";
    public String island1Bans = "";
    public String island1SpawnVisit = "0;69;0;0f;0f";
    public String island1SpawnTrust = "0;69;0;0f;0f";

    public String island2Monsters = "0";
    public String island2UsersVisit = "0";
    public String island2UsersPvp = "0";
    public String island2UsersInteract = "0";
    public String island2UsersBuild = "0";
    public String island2Trusts = "";
    public String island2Bans = "";
    public String island2SpawnVisit = "0;69;0;0f;0f";
    public String island2SpawnTrust = "0;69;0;0f;0f";

    public static void init() {
        ListenerSystem.register(new UserData());
    }

    private UserData() { }

    @EventHandler(priority = EventPriority.LOWEST)
    public static void onPreLogin(AsyncPlayerPreLoginEvent e) {
        UUID id = e.getUniqueId();

        if (!isPlayerLoaded(id)) {
            load(id);
        }

        UserData.set(id, UserValue.playerName, e.getName());
        UserData.set(id, UserValue.minecraftId, toTrimmed(id));
    }

    public static boolean isPlayerLoaded(UUID id) {
        for (int i = 0; i < loadedUsers.size(); i++) {
            UserData user = loadedUsers.get(i);
            if (user.minecraftId.contentEquals(id.toString().replace("-", ""))) {
                return true;
            }
        }

        return false;
    }

    public static boolean isPlayerLoaded(Player p) {
        return isPlayerLoaded(p.getUniqueId());
    }

    public static void unload(UUID id) {
        for (int i = 0; i < loadedUsers.size(); i++) {
            UserData user = loadedUsers.get(i);
            if (user.minecraftId.contentEquals(toTrimmed(id))) {
                loadedUsers.remove(i);
                user.close();
                return;
            }
        }
    }

    public static void load(UUID id) {
        if (!isPlayerLoaded(id)) {
            UserData user = new UserData(id);
            user.load();
            loadedUsers.add(user);
        }
    }

    private UserData(UUID id) {
        minecraftId = id.toString().replace("-", "");
    }

    private void load() {
        log("Loading user: " + minecraftId);

        ArrayList<HashMap<String, String>> rows = Database.fetchAll("SELECT * FROM users WHERE " + UserValue.minecraftId +  " = '" + minecraftId.replace("'", "") + "';");

        if (rows.size() != 0) {
            HashMap<String, String> row = rows.get(0);

            for (String key : row.keySet()) {
                setString(key, row.get(key));
            }
        }
    }

    public void close() {
        log("Unloading user: " + minecraftId);
        save();
    }

    private void save() {
        String columns = "";
        String values = "";

        for (String field : UserData.getFieldNames()) {
            columns += field + ", ";
            values += "'" + getString(field) + "', ";
        }

        if (columns.length() != 0) {
            columns = columns.substring(0, columns.length() - 2);
            values = values.substring(0, values.length() - 2);
        }

        Database.execute("DELETE FROM users WHERE minecraftId = '" + minecraftId + "'; INSERT INTO users (" + columns + ") VALUES (" + values + ");");
    }

    private String getString(String name) {
        try {
            Field field = UserData.class.getDeclaredField(name);
            field.setAccessible(true);
            return (String) field.get(this);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private void setString(String name, String value) {
        try {
            Field field = UserData.class.getDeclaredField(name);
            field.setAccessible(true);
            field.set(this, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getFieldNames() {
        ArrayList<String> names = new ArrayList<String>();
        Class<UserData> c = UserData.class;
        
        for (Field field : c.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) {
                names.add(field.getName());
            }
        }

        return names;
    }

    public static void set(UUID id, UserValue data, Object value) {
        if (value instanceof Boolean) {
            value = (boolean)value ? 1 : 0;
        }

        setValue(id, data.toString(), value.toString());
    }

    public static void set(Player p, UserValue data, Object value) {
        set(p.getUniqueId(), data, value);
    }

    private static void setValue(UUID id, String name, String value) {
        for (int i = 0; i < loadedUsers.size(); i++) {
            UserData user = loadedUsers.get(i);
            if (user.minecraftId.contentEquals(id.toString().replace("-", ""))) {
                user.setString(name, value);
                return;
            }
        }

        load(id);
        setValue(id, name, value);
    }

    public static UUID getUUIDFromName(String name) {
        Player p = getPlayer(name);

        if (p != null) {
            return p.getUniqueId();
        }

        ArrayList<HashMap<String, String>> rows = Database.fetchAll("SELECT minecraftId FROM users WHERE playerName LIKE '" + name.replace("'", "") + "';");

        if (rows.size() == 0) {
            return null;
        }

        return getUUIDFromTrimmed(rows.get(0).get("minecraftId"));
    }

    public static ArrayList<UUID> getAllUUIDs() {
        ArrayList<HashMap<String, String>> rows = Database.fetchAll("SELECT minecraftId FROM users;");
        ArrayList<UUID> uuids = new ArrayList<UUID>();
        int size = rows.size();

        for (int i = 0; i < size; i++) {
            uuids.add(getUUIDFromTrimmed(rows.get(i).get("minecraftId")));
        }

        return uuids;
    }

    public static String get(Player p, UserValue data) {
        return get(p.getUniqueId(), data);
    }

    public static String get(UUID id, UserValue data) {

        if (DisableRanks.disabled && data == UserValue.playerGroup) {
            return Group.MEMBER.toString();
        }

        return getValue(id, data.toString());
    }

    private static String getValue(UUID id, String name) {
        for (int i = 0; i < loadedUsers.size(); i++) {
            UserData user = loadedUsers.get(i);
            if (user.minecraftId.contentEquals(id.toString().replace("-", ""))) {
                return user.getString(name);
            }
        }

        load(id);
        return getValue(id, name);
    }

    public static UserData getLoadedUser(UUID id) {
        for (int i = 0; i < loadedUsers.size(); i++) {
            UserData user = loadedUsers.get(i);
            if (user.minecraftId.contentEquals(toTrimmed(id))) {
                return user;
            }
        }
        return null;
    }

    public static int getInt(Player p, UserValue data) {
        try {
            return Integer.parseInt(get(p, data));
        } catch (Exception e) {
            return 0;
        }
    }

    public static int getInt(UUID id, UserValue data) {
        try {
            return Integer.parseInt(get(id, data));
        } catch (Exception e) {
            return 0;
        }
    }

    public static long getLong(Player p, UserValue data) {
        return Long.parseLong(get(p, data));
    }

    public static long getLong(UUID id, UserValue data) {
        return Long.parseLong(get(id, data));
    }

	public static boolean getBoolean(Player p, UserValue data) {
		return getInt(p, data) != 0;
	}

	public static boolean getBoolean(UUID id, UserValue data) {
		return getInt(id, data) != 0;
    }
    
    public static ArrayList<UserData> getLoadedUsers() {
        return loadedUsers;
    }
}