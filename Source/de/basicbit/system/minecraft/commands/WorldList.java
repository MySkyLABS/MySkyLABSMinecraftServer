package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import de.basicbit.system.database.UserData;
import de.basicbit.system.database.UserValue;
import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;
import de.basicbit.system.minecraft.listeners.npc.guis.SkyBlock;

public class WorldList extends Command {

    @Override
    public String getUsage(Player p) {
        return "";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("worldlist");
        names.add("worlds");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isInTeam(p);
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length != 0) {
            return CommandResult.InvalidUsage;
        }

        sendMessage(p, "§a§lGeladene Welten:");

        for (World w : Bukkit.getWorlds()) {
            if (SkyBlock.isSkyblockWorld(w)) {
                UUID id = SkyBlock.getUUIDFromSkyBlockWorld(w);
                int number = SkyBlock.getNumberFromSkyBlockWorld(w);
                String name = UserData.get(id, UserValue.playerName);

                sendMessage(p, "§7- " + name + ":");
                sendMessage(p, "§7- - UUID: " + id);
                sendMessage(p, "§7- - Number: " + number);
            } else {
                sendMessage(p, "§7- " + w.getName());
            }
        }

        return CommandResult.None;
    }

    @Override
    public String getDescription(Player p) {
        return "Listet alle geladenen Welten auf.";
    }

    @Override
    public boolean isAsync() {
        return true;
    }
}