package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;
import net.minecraft.server.v1_8_R1.PacketPlayOutGameStateChange;

public class Win extends Command {
    
    @Override
    public String getUsage(Player p) {
        return "<Spieler>";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("win");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isDev(p);
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 1) {
            Player t = getPlayer(args[0]);

			if (t == null) {
				return CommandResult.PlayerNotOnline;
            }

            sendPacket(t, new PacketPlayOutGameStateChange(4, 0));

            return CommandResult.None;
		} else {
            return CommandResult.InvalidUsage;
        }
    }

    @Override
    public String getDescription(Player p) {
        return "Zeigt dem angegebenen Spieler den Abspann an.";
    }
}