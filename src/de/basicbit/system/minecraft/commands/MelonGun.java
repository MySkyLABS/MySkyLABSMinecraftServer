package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.GlobalValues;
import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

public class MelonGun extends Command {

    @Override
    public String getUsage(Player p) {
        return "";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("melongun");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isAdmin(p);
    }

    @Override
    public String getDescription(Player p) {
        return "Ermöglicht das feuern mit Melonen.";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        
        if (args.length == 0) {
            if (GlobalValues.melonGunPlayers.contains(p.getUniqueId())) {
                GlobalValues.melonGunPlayers.remove(p.getUniqueId());
                sendMessage(p, "§cDeine Melonen sind jetzt weg. :(");
            } else if (GlobalValues.fireLinePlayers.contains(p.getUniqueId())
                    || GlobalValues.tntGunPlayers.contains(p.getUniqueId())) {
                sendMessage(p, "§cDu kannst nicht mehrere Guns gleichzeitig benutzen!");
            } else {
                GlobalValues.melonGunPlayers.add(p.getUniqueId());
                sendMessage(p, "§aMelonen!!!");
            }
            return CommandResult.None;
        } else {
            return CommandResult.InvalidUsage;
        }
    }
}