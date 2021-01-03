package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;
import de.basicbit.system.minecraft.CommandSystem;
import de.basicbit.system.minecraft.MySkyLABS;

public class Help extends Command {

    @Override
    public String getUsage(Player p) {
        return "";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("help");
        names.add("?");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return true;
    }

    @Override
    public String getDescription(Player p) {
        return "Zeigt eine Liste von allen verfügbaren Commands.";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {

        List<Command> commands = CommandSystem.getCommands();
        commands.sort((obj1, obj2) -> obj1.getNames().get(0).compareTo(obj2.getNames().get(0)));

        sendMessage(p, "§aCommands:");

        for (Command command : commands) {
            if (command.hasPermissions(p)) {
                String name = command.getNames().get(0);
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                sendChatBaseComponent(p, "[{'text':'" + MySkyLABS.prefix + "'},{'text':'§e/" + name + " " + command.getUsage(p) + "','hoverEvent':{'action':'show_text','value':'" + command.getDescription(p) + "'}}]");
            }
        }

        return CommandResult.None;
    }
}