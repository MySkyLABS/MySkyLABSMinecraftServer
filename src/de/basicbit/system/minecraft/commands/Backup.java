package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;
import de.basicbit.system.minecraft.tasks.TaskManager;

public class Backup extends Command {

    @Override
    public String getUsage(Player p) {
        return "";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("backup"); 
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isModerator(p);
    }

    @Override
    public String getDescription(Player p) {
        return "Macht ein Backup.";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        sendMessage(p, "§aDas Backup wird generiert...");
        sendMessage(p, "§aBitte warte kurz!");
        safeBackup();
        TaskManager.runAsyncTaskLater(new Runnable() {
            @Override
            public void run() {
                sendMessage(p, "§aDas Backup wurde erstellt!");
                sendMessage(p, "§cGeladene Welten werden §l§nNICHT §cberücksichtigt!");
            }
        }, (20 * 20));
        return CommandResult.None;
    }
}