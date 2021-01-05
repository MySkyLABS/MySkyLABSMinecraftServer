package de.basicbit.system.minecraft;

import java.io.File;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

import de.basicbit.system.minecraft.skyblock.SkyBlock;

public class WorldManager extends Utils {
    
    public static World loadWorld(UUID id, int number) {
        WorldCreator worldCreator = new WorldCreator(SkyBlock.getWorldName(id, number));
        worldCreator.type(WorldType.FLAT);
        worldCreator.generateStructures(false);
        worldCreator.generatorSettings("3;minecraft:air;1");
        return getServer().createWorld(worldCreator);
    }
    
    public static void unloadWorld(UUID id, int number) {
        getServer().unloadWorld(SkyBlock.getWorldName(id, number), true);
    }

    public static void mvCreateWorld(UUID id, int number) {
        //runCommand("mv create " + SkyBlock.getWorldName(id, number) + " normal -g CleanroomGenerator:. -a false");

        World world = loadWorld(id, number);
        world.setSpawnLocation(0, 70, 0);
        pasteSchematic(getMainIslandSchematic(), new Location(world, 0, 69, 0, 0, 0));

        log("CREATE!!!");
    }

    private static File getMainIslandSchematic() {
        return new File("skyblock/island_templates/island_main.schematic");
    }

    public static void mvRemoveWorld(UUID id, int number) {
        //mvRemoveWorld(SkyBlock.getWorldName(id, number));
        
        unloadWorld(id, number);
    }
}