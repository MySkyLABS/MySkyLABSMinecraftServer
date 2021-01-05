package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

import static de.basicbit.system.minecraft.CommandResult.*;

public class ChatClear extends Command {

    @Override
    public String getUsage(Player p) {
        return "";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("chatclear");
        names.add("cc");
        return names;
    }

    @Override
    public String getDescription(Player p) {
        return "Leert den Chatverlauf.";
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isInTeam(p);
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 0) {
            
            clearChat(p);

            return None;
        } else {
            return InvalidUsage;
        }
    }

    @Override
    public boolean isAsync() {
        return true;
    }
}
