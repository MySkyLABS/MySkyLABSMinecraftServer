package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;
import de.basicbit.system.minecraft.tasks.LoopTask;
import de.basicbit.system.minecraft.tasks.TaskManager;

public class Loops extends Command {

    @Override
    public String getUsage(Player p) {
        return "";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("loops");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isAdmin(p);
    }

    @Override
    public String getDescription(Player p) {
        return "Zeigt alle laufenden Aufgaben an.";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 0) {
            LoopTask[] loops = TaskManager.getLoops();

            sendMessage(p, "§dLoops: §e" + loops.length);
            
            ArrayList<LoopTask> syncLoops = new ArrayList<LoopTask>();
            ArrayList<LoopTask> asyncLoops = new ArrayList<LoopTask>();

            for (LoopTask loop : loops) {
                (loop.isSync() ? syncLoops : asyncLoops).add(loop);
            }

            sendMessage(p, "§d|  Synchrone Loops: §e" + syncLoops.size());

            for (LoopTask loop : syncLoops) {
                sendMessage(p, "§d|  |  §e" + loop.getName() + ":");
                sendMessage(p, "§d|  |  §e|  §7ID: §e" + loop.getId());
                sendMessage(p, "§d|  |  §e|  §9Synchron: §cJa");
                sendMessage(p, "§d|  |  §e|  §bIntervall: §7" + loop.getIntervalMillis() + "ms");
                sendMessage(p, "§d|  |  §e|  §cSchwere: §7" + loop.getLastDuration() + "ms");
            }

            sendMessage(p, "§d|  Asynchrone Loops: §e" + asyncLoops.size());

            for (LoopTask loop : asyncLoops) {
                sendMessage(p, "§d|  |  §e" + loop.getName() + ":");
                sendMessage(p, "§d|  |  §e|  §7ID: §e" + loop.getId());
                sendMessage(p, "§d|  |  §e|  §9Synchron: §aNein");
                sendMessage(p, "§d|  |  §e|  §bIntervall: §7" + loop.getIntervalMillis() + "ms");
                sendMessage(p, "§d|  |  §e|  §cSchwere: §7" + loop.getLastDuration() + "ms");
            }

            return CommandResult.None;
        } else {
            return CommandResult.InvalidUsage;
        }
    }
}