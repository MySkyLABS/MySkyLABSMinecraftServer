package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

public class Invsee extends Command {

    @Override
    public String getUsage(Player p) {
        return "<Spieler>";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("invsee");
        return names;
    }

    @Override
    public String getDescription(Player p) {
        return "Öffnet das Inventar eines anderen Spielers.";
    }

    @Override
    public boolean hasPermissions(Player p) {
        return true;
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 1) {

            Player t = getPlayer(args[0]);

            if (t == null || isVanish(t)) {
                return CommandResult.PlayerNotOnline;
            } else if (t.getName().equalsIgnoreCase(p.getName())) {
                sendMessage(p, "§cDu kannst dein eigenes Inventar nicht mit §e/Invsee <Spieler>§c öffnen!");
                return CommandResult.None;
            }

            p.openInventory(t.getInventory());

            return CommandResult.None;
        } else {
            return CommandResult.InvalidUsage;
        }
    }
}
