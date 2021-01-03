package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.bukkit.entity.Player;

import de.basicbit.system.GlobalValues;
import de.basicbit.system.database.UserData;
import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;
import de.basicbit.system.minecraft.tasks.TaskManager;

public class Mute extends Command {

    @Override
    public String getUsage(Player p) {
        return "<Spieler> [Dauer <s/m/h>]";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("mute");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isTestSupporter(p) || isContent(p);
    }

    @Override
    public String getDescription(Player p) {
        return "Verbietet einem Spieler zu schreiben.";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 1) {
            UUID id = UserData.getUUIDFromName(args[0]);

            if (GlobalValues.muted.contains(id)) {
                sendMessage(p, "§a" + args[0] + " ist nun entmuted.");
                sendMessage(getPlayer(id), "§aDu bist nun entmuted.");
                GlobalValues.muted.remove(id);
            } else {
                sendMessage(p, "§c" + args[0] + " ist nun gemuted.");
                sendMessage(getPlayer(id), "§cDu bist nun gemuted.");
                GlobalValues.muted.add(id);
            }

            return CommandResult.None;
        } else if (args.length == 2) {
            String timeString = args[1].toLowerCase();
            TimeUnit timeFormat = null;
            String fromatName = "";
            switch (timeString.charAt(timeString.length() - 1)) {
                case 's':
                    timeFormat = TimeUnit.SECONDS;
                    fromatName = "Sekunde";
                break;
                case 'm':
                    timeFormat = TimeUnit.MINUTES;
                    fromatName = "Minute";
                break;
                case 'h':
                    timeFormat = TimeUnit.HOURS;
                    fromatName = "Stunde";
                break;
            }
            int time = 0;
            UUID id = UserData.getUUIDFromName(args[0]);

            try {
                String timeCroppedString = timeString.substring(0, timeString.length() - 1);
                time = (int) timeFormat.toSeconds(Integer.parseInt(timeCroppedString));
                if (time == 1) {
                    sendMessage(p, "§c" + args[0] + " ist nun für eine " + fromatName + " gemuted.");
                    sendMessage(getPlayer(id), "§cDu bist nun für eine " + fromatName + " gemuted.");
                } else {
                    sendMessage(p, "§c" + args[0] + " ist nun für " + timeCroppedString + " " + fromatName + "n gemuted.");
                    sendMessage(getPlayer(id), "§cDu bist nun für " + timeCroppedString + " " + fromatName + "n gemuted.");
                }
                GlobalValues.muted.add(id);

                TaskManager.runAsyncTaskLater(new Runnable() {
                    @Override
                    public void run() {
                        if (GlobalValues.muted.contains(id)) {
                            sendMessage(p, "§a" + args[0] + " ist nun entmuted.");
                            sendMessage(getPlayer(id), "§aDu bist nun entmuted.");
                            GlobalValues.muted.remove(id);
                        }
                    }
                }, (time * 20));
            } catch (Exception e) {
                sendMessage(p, "§cUngültige Zeit: \"" + args[1] + "\".");
            }
            return CommandResult.None;
        }
        return CommandResult.InvalidUsage;
    }
}