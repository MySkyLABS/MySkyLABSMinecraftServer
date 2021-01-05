package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

import static de.basicbit.system.minecraft.CommandResult.*;

public class Ping extends Command {

    @Override
    public String getUsage(Player p) {
        return "[Spieler]";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("ping");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return true;
    }

    @Override
    public String getDescription(Player p) {
        return "Gibt die Latenzzeit deiner Verbindung zu unserem Server zurück.";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 0) {
            sendMessage(p, "§7Du hast einen Ping von §e" + ((CraftPlayer) p).getHandle().ping + "ms§7.");
            return None;
		} else if (args.length == 1) {

            Player t = getPlayer(args[0]);

			if (t == null || isVanish(t)) {
				return PlayerNotOnline;
			}

			sendMessage(p,getChatName(t) + "§7 hat einen Ping von §e" + ((CraftPlayer) t).getHandle().ping + "§7.");
            return None;
		} else {
            return InvalidUsage;
        }
    }
}
