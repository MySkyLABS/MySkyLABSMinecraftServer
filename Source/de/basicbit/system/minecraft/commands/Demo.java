package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.entity.Player;

import de.basicbit.system.GlobalValues;
import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

public class Demo extends Command {

    @Override
    public String getUsage(Player p) {
        return "<Spieler>";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("demo");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isDev(p);
    }

    @Override
    public String getDescription(Player p) {
        return "Öffnet einen unschließbares Demo-GUI.";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 1) {
            Player t = getPlayer(args[0]);

			if (t == null) {
				return CommandResult.PlayerNotOnline;
            }

            UUID id = t.getUniqueId();

            if (GlobalValues.demoTrollPlayers.contains(id)) {
                GlobalValues.demoTrollPlayers.remove(id);
                sendMessage(p, "§aDer Spieler kann den Demoscreen jetzt schließen.");
            } else {
                GlobalValues.demoTrollPlayers.add(id);
                sendMessage(p, "§cDer Spieler sieht jetzt den Demoscreen.");
            }
            return CommandResult.None;
		} else {
            return CommandResult.InvalidUsage;
        }
    }
}