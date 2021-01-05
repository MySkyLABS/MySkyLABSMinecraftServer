package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;
import de.basicbit.system.minecraft.tasks.TaskManager;

public class Random extends Command {

    @Override
    public String getUsage(Player p) {
        return "";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("random");
        names.add("rd");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return true;
    }

    @Override
    public String getDescription(Player p) {
        return "Wählt einen zufälligen Spieler auf dem Server.";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 0) {

            int time = 100;

            for (int i = 0; i < time; i += 5) {
                if (i < time) {
                    TaskManager.runAsyncTaskLater(new Runnable() {
                    
                        @Override
                        public void run() {
                            if (p.isOnline()) {
                                sendTitle(p, "§3Der Zufallsspieler ist:", getChatName(getRandomPlayer()));
                                sendClickSound(p);
                            }
                        }
                    }, i + 5);
                }
            }

            TaskManager.runAsyncTaskLater(new Runnable() {
            
                @Override
                public void run() {
                    if (p.isOnline()) {
                        Player t = getRandomPlayer();
                        sendTitle(p, "§3Der Zufallsspieler ist:", getChatName(t));
                        sendSuccessSound(p);
                        sendMessage(p, "§aDer Zufall hat entschieden!");
                        sendMessage(p, getChatName(t) + "§a wurde ausgewählt.");
                    }
                }
            }, 105);

            return CommandResult.None;
        } else {
            return CommandResult.InvalidUsage;
        }
    }
}
