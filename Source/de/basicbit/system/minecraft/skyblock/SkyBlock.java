package de.basicbit.system.minecraft.skyblock;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import de.basicbit.system.GlobalValues;
import de.basicbit.system.database.UserData;
import de.basicbit.system.database.UserValue;
import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.ListenerSystem;
import de.basicbit.system.minecraft.WorldManager;
import de.basicbit.system.minecraft.commands.Is;
import de.basicbit.system.minecraft.events.ServerCommunicationSystemMessageEvent;
import de.basicbit.system.minecraft.skyblock.worldgenerator.WorldGenerator;
import de.basicbit.system.minecraft.tasks.TaskManager;

public class SkyBlock extends Listener {

    public static void init() {
        WorldGenerator.init();

        ListenerSystem.register(new SkyBlock());

        File root = new File("./");
        for (File file : root.listFiles()) {
            String name = file.getName();
            if (name.contains("#")) {
                moveFolder(new File(name), new File("skyblock/player_worlds/" + name));
            }
        }

        TaskManager.runAsyncLoop("SkyBlockWorldUnloader", new Runnable() {

            @Override
            public void run() {
                for (World w : createCopy(Bukkit.getWorlds())) {
                    if (isSkyblockWorld(w) && w.getPlayers().size() == 0) {
                        unloadWorld(w);
                    }
                }
            }
        }, 100);
    }

    public static void joinWorld(Player p, UUID id, int number) {
        TaskManager.runSyncTask("SkyBlockJoinWorld", new Runnable() {

            @Override
            public void run() {
                log("joinWorld: " + p.getName() + ", " + getWorldName(id, number));

                if (isWorldLoaded(id, number)) {
                    TaskManager.runAsyncTask(new Runnable() {

                        @Override
                        public void run() {
                            boolean allowVisit = UserData.getBoolean(id,
                                    UserValue.valueOf("island" + number + "UsersVisit"));

                            if (isBanned(id, number, p) && !isInTeam(p)) {
                                sendMessage(p, "§cDu bist auf dieser Insel gebannt.");
                                return;
                            }

                            if (p.getUniqueId().equals(id) || allowVisit || isInTeam(p) || isTrusted(id, number, p)) {
                                // int unixTimeStart = (int) (System.currentTimeMillis() / 1000L);
                                TaskManager.runSyncTask("SkyBlockJoinWorldTeleport", new Runnable() {

                                    @Override
                                    public void run() {
                                        p.teleport(isInTeam(p) || isTrusted(id, number, p) || p.getUniqueId().equals(id)
                                                ? getSpawnTrust(id, number)
                                                : getSpawnVisit(id, number));
                                        try {
                                            loadHolograms(p, id, number);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } else {
                                sendMessage(p, "§cAuf dieser Insel sind Besucher deaktiviert.");
                            }
                        }
                    });
                } else {
                    if (existsWorld(id, number)) {
                        loadWorld(id, number);
        
                        joinWorld(p, id, number);
                    } else {
                        createWorld(p.getUniqueId(), id, number, false);
                    }
                }
            }
        });
    }

    public static void setSpawnTrust(UUID id, int number, Location spawn, float yaw, float pitch) {
        UserData.set(id, UserValue.valueOf("island" + number + "SpawnTrust"), spawn.getBlockX() + ";" + spawn.getBlockY() + ";" + spawn.getBlockZ() + ";" + spawn.getYaw() + ";" + spawn.getPitch());
    }

    public static Location getSpawnTrust(UUID id, int number) {
        try {
            String[] pos = UserData.get(id, UserValue.valueOf("island" + number + "SpawnTrust")).split(";");
            int x = Integer.parseInt(pos[0]);
            int y = Integer.parseInt(pos[1]);
            int z = Integer.parseInt(pos[2]);
            float yaw = Float.parseFloat(pos[3]);
            float pitch = Float.parseFloat(pos[4]);
            return new Location(getWorld(id, number), x + 0.5, y + 0.5, z + 0.5, yaw, pitch);
        } catch (Exception e) {
            return new Location(getWorld(id, number), 0.5, 69.5, 0.5, 0f, 0f);
        }
    }

    public static void setSpawnVisit(UUID id, int number, Location spawn, float yaw, float pitch) {
        UserData.set(id, UserValue.valueOf("island" + number + "SpawnVisit"), spawn.getBlockX() + ";" + spawn.getBlockY() + ";" + spawn.getBlockZ() + ";" + spawn.getYaw() + ";" + spawn.getPitch());
    }

    public static Location getSpawnVisit(UUID id, int number) {
        try {
            String[] pos = UserData.get(id, UserValue.valueOf("island" + number + "SpawnVisit")).split(";");
            int x = Integer.parseInt(pos[0]);
            int y = Integer.parseInt(pos[1]);
            int z = Integer.parseInt(pos[2]);
            float yaw = Float.parseFloat(pos[3]);
            float pitch = Float.parseFloat(pos[4]);
            return new Location(getWorld(id, number), x + 0.5, y + 0.5, z + 0.5, yaw, pitch);
        } catch (Exception e) {
            return new Location(getWorld(id, number), 0.5, 69.5, 0.5, 0f, 0f);
        }
    }

    public static boolean isTrusted(UUID id, int number, Player p) {
        for (IslandTrust trust : Is.getTrustsFromDatabase(id, number)) {
            if (trust.id.equals(p.getUniqueId())) {
                return true;
            }
        }

        return false;
    }

    public static boolean isTrusted(World w, Player p) {
        return isSkyblockWorld(w) && isTrusted(getUUIDFromSkyBlockWorld(w), getNumberFromSkyBlockWorld(w), p);
    }

    public static boolean isBanned(World w, Player p) {
        return isSkyblockWorld(w) && isBanned(getUUIDFromSkyBlockWorld(w), getNumberFromSkyBlockWorld(w), p);
    }

    public static boolean isBanned(UUID id, int number, Player p) {
        for (IslandBan ban : Is.getBansFromDatabase(id, number)) {
            if (ban.id.equals(p.getUniqueId())) {
                return true;
            }
        }

        return false;
    }

    public static void deleteWorld(UUID id, int number) {
        TaskManager.runAsyncTask(new Runnable() {
        
            @Override
            public void run() {
                UserData.set(id, UserValue.valueOf("island" + number + "SpawnTrust"), "0;69;0");
                UserData.set(id, UserValue.valueOf("island" + number + "SpawnVisit"), "0;69;0");
            }
        });

        TaskManager.runSyncTask("SkyBlockDeleteWorld", new Runnable() {
        
            @Override
            public void run() {
                if (isWorldLoaded(id, number)) {
                    for (Player p : getWorld(id, number).getPlayers()) {
                        sendMessage(p, "§cDie Insel auf der du dich befindest wurde gelöscht.");
                        sendMessage(p, "§aDu wurdest zum Spawn teleportiert.");
        
                        p.teleport(getSpawn());
                    }
        
                    WorldManager.mvRemoveWorld(id, number);
        
                    deleteFolder(new File(getWorldName(id, number)));
                } else if (existsWorld(id, number)) {
                    deleteFolder(new File("skyblock/player_worlds/" + getWorldName(id, number)));
                }
            }
        });
    }
    
    private static void createWorld(UUID pid, UUID id, int number, boolean worldGenerator) {
        WorldManager.mvCreateWorld(id, number);
        Player p = getPlayer(pid);

        if (p != null) {
            SkyBlock.joinWorld(p, id, number);
        }

        /*log("createWorld: " + toTrimmed(pid) + ", " + getWorldName(id, number));

        if (worldGenerator) {
            WorldManager.mvCreateWorld(id, number);

            World w = getWorld(id, number);
            w.setSpawnLocation(0, 70, 0);
            pasteSchematic(getMainIslandSchematic(), new Location(w, 0, 69, 0));
        } else {
            String worldName = getWorldName(id, number);
            if (worldNames.contains(worldName)) {
                Player p = getPlayer(pid);
                if (p != null) {
                    sendMessage(p, "§cDiese Insel wird gerade generiert.");
                    sendMessage(p, "§cBitte warte einen Moment.");
                }
            } else {
                worldNames.add(worldName);
                ServerCommunicationSystem.sendCommand("map create " + toTrimmed(pid) + ";" + worldName);

                Player p = getPlayer(pid);
                if (p != null) {
                    sendMessage(p, "§aDie Welt wird jetzt generiert.");
                    sendMessage(p, "§aDu wirst teleportiert, wenn sie geladen ist.");
                }
            }
        }*/
    }

    @EventHandler
    public static void onServerCommunicationSystemMessage(ServerCommunicationSystemMessageEvent e) {
        /*String msg = e.getMessage();

        if (msg.startsWith("map created ")) {
            String[] args = msg.substring(12).split(";");
            UUID pid = getUUIDFromTrimmed(args[0]);
            String worldName = args[1];

            moveFolder(new File("skyblock/worldgenerator/" + worldName), new File("skyblock/player_worlds/" + worldName));

            Player p = getPlayer(pid);
            if (p != null) {
                joinWorld(p, getUUIDFromSkyBlockWorldName(worldName), getNumberFromSkyBlockWorldName(worldName));
            }

            if (worldName.contains(worldName)) {
                worldNames.remove(worldName);
            }
        }*/
    }

    public static void loadWorld(UUID id, int number) {
        log("loadWorld: " + getWorldName(id, number));
        
        moveFolder(new File("skyblock/player_worlds/" + getWorldName(id, number)), new File(getWorldName(id, number)));

        WorldManager.loadWorld(id, number);

        if (getPlayer(id) == null) {
            UserData.load(id);
        }
    }

    public static void unloadWorld(World w) {
        UUID id = getUUIDFromSkyBlockWorld(w);
        int number = getNumberFromSkyBlockWorld(w);

        log("unloadWorld: " + getWorldName(id, number));

        WorldManager.mvRemoveWorld(id, number);

        moveFolder(new File(w.getName()), new File("skyblock/player_worlds/" + w.getName()));

        if (getPlayer(id) == null) {
            boolean unload = true;

            for (World world : Bukkit.getWorlds()) {
                if (world.getName().startsWith(toTrimmed(id))) {
                    unload = false;
                    break;
                }
            }

            if (unload) {
                UserData.unload(id);
            }
        }
    }

    private static World getWorld(UUID id, int number) {
        return Bukkit.getWorld(getWorldName(id, number));
    }

    private static boolean existsWorld(UUID id, int number) {
        return new File("skyblock/player_worlds/" + getWorldName(id, number)).exists();
    }

    private static boolean isWorldLoaded(UUID id, int number) {
        return isWorldLoaded(getWorldName(id, number));
    }

    private static boolean isWorldLoaded(String worldName) {
        return Bukkit.getWorld(worldName) != null;
    }

    public static String getWorldName(UUID id, int number) {
        return toTrimmed(id) + "#" + number;
    }

    public static void deleteFolder(Path path) {
        try {
            if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
                try (DirectoryStream<Path> entries = Files.newDirectoryStream(path)) {
                    for (Path entry : entries) {
                        deleteFolder(entry);
                    }
                }
            }
            
            Files.delete(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteFolder(File file) {
        deleteFolder(file.toPath());
    }

    private static void moveFolder(File sourceFolder, File destinationFolder) {
        if (destinationFolder.exists()) {
            deleteFolder(destinationFolder);
        }

        try {
            FileUtils.moveDirectory(sourceFolder, destinationFolder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File getRandomIslandSchematic() {
        return getIslandSchematic(GlobalValues.random.nextInt(getIslandSchematicCount()));
    }

    private static File getIslandSchematic(int i) {
        return new File("skyblock/island_templates/other_islands/island (" + (i + 1) + ").schematic");
    }

	private static int getIslandSchematicCount() {
        int count = 0;

		for (File file : new File("skyblock/island_templates/other_islands").listFiles()) {
			if (file.isFile()) {
				count++;
			}
		}

		return count;
	}

    public static File getRandomIslandSba() {
        return getIslandSba(GlobalValues.random.nextInt(getIslandSbaCount()));
    }

    private static File getIslandSba(int i) {
        return getIslandsSba()[i];
    }

	private static int getIslandSbaCount() {
        return getIslandsSba().length;
    }
    
    private static File[] getIslandsSba() {
		return new File("skyblock/island_templates_sba/other_islands").listFiles();
	}
}
