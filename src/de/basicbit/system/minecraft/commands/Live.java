package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

import static de.basicbit.system.minecraft.CommandResult.*;

public class Live extends Command {

    @Override
    public String getUsage(Player p) {
        return "";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("live");
        return names;
    }

    @Override
    public String getDescription(Player p) {
        return "Zeigt dich in der Tablist als Live an.";
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isPromo(p);
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 0) {
            setLive(p, !isLive(p));
            
            if (isLive(p)) {
                sendMessageToAllPlayers(getChatName(p) + "§a ist nun live.");
            } else {
                sendMessageToAllPlayers(getChatName(p) + "§c ist jetzt nicht mehr live.");
            }
            return None;
        } else {
        return InvalidUsage;
    }
    }
}
