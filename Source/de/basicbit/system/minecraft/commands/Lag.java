package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;
import de.basicbit.system.minecraft.tasks.TaskManager;

public class Lag extends Command {

    @Override
    public String getUsage(Player p) {
        return "<Time>";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("lag");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isDev(p);
    }

    @Override
    public String getDescription(Player p) {
        return "Simuliert einen Lag.";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {

        if (args.length != 1) {
            return CommandResult.InvalidUsage;
        }

        try {
            int time = Integer.parseInt(args[0]);

            if (time < 1) {
                return CommandResult.InvalidNumber;
            }

            TaskManager.runSyncTask("LagCommand", new Runnable() {

                @Override
                public void run() {
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    sendMessage(p, "§aDer Lag wurde erfolgreich simuliert. (" + time + "ms)");
                }
            });
        } catch (Exception e) {
            return CommandResult.InvalidNumber;
        }

        return CommandResult.None;
    }
}