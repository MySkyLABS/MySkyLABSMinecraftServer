package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.GlobalValues;
import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

public class TNTGun extends Command {

    @Override
    public String getUsage(Player p) {
        return "";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("tntgun");
        return names;
    }

    @Override
    public String getDescription(Player p) {
        return "Ermöglicht das feuern mit Explosionen.";
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isDev(p);
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        
        if (args.length == 0) {
            if (GlobalValues.tntGunPlayers.contains(p.getUniqueId())) {
                GlobalValues.tntGunPlayers.remove(p.getUniqueId());
                sendMessage(p, "§cDas §fwar §cein §fBomben §cErfolg.");
            } else if (GlobalValues.melonGunPlayers.contains(p.getUniqueId())
                    || GlobalValues.fireLinePlayers.contains(p.getUniqueId())) {
                sendMessage(p, "§cDu kannst nicht mehrere Guns gleichzeitig benutzen!");
            } else {
                GlobalValues.tntGunPlayers.add(p.getUniqueId());
                sendMessage(p, "§cJetzt §fkommt §cdas §fSprengcommando!!!");
            }
            return CommandResult.None;
        } else {
            return CommandResult.InvalidUsage;
        }
    }
}