package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.GlobalValues;
import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

public class FireLine extends Command {

    @Override
    public String getUsage(Player p) {
        return "";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("fireline");
        return names;
    }

    @Override
    public String getDescription(Player p) {
        return "Ermöglicht das feuern mit Feuer.";
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isDev(p);
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        
        if (args.length == 0) {
            if (GlobalValues.fireLinePlayers.contains(p.getUniqueId())) {
                GlobalValues.fireLinePlayers.remove(p.getUniqueId());
                sendMessage(p, "§9Du bist jetzt wieder abgekühlt!");
            } else if (GlobalValues.melonGunPlayers.contains(p.getUniqueId()) 
                    || GlobalValues.tntGunPlayers.contains(p.getUniqueId())){
                sendMessage(p, "§cDu kannst nicht mehrere Guns gleichzeitig benutzen!");
            } else {
                GlobalValues.fireLinePlayers.add(p.getUniqueId());
                sendMessage(p, "§cDu bist jetzt der Burner!");
            }
            return CommandResult.None;
        } else {
            return CommandResult.InvalidUsage;
        }
    }
}