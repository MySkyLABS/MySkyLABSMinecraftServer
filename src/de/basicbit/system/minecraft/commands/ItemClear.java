package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;
import de.basicbit.system.minecraft.tasks.TaskManager;

import static de.basicbit.system.minecraft.CommandResult.*;

public class ItemClear extends Command {

    @Override
    public String getUsage(Player p) {
        return "";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("itemclear");
        names.add("ic");
        return names;
    }

    @Override
    public String getDescription(Player p) {
        return "Löscht alle Items auf dem Server.";
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isAdmin(p);
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 0) {
            sendMessageToAllPlayers("§cAlle Items werden in 10 Sekunden entfernt!");

            TaskManager.runAsyncTaskLater(new Runnable() {
            
                @Override
                public void run() {
                    removeAllItems();
                }
            }, 200);

            return None;
        } else {
            return InvalidUsage;
        }
    }
}
