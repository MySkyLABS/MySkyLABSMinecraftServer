package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.GlobalValues;
import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

public class Fly extends Command {

    @Override
    public String getUsage(Player p) {
        return "[Spieler]";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("fly");
        names.add("f");
        names.add("fliegen");
        return names;
    }

    @Override
    public String getDescription(Player p) {
        return "Lässt einen Spieler fliegen.";
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isModerator(p);
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 0) {
            if (isInWorld(p, getKnockItWorld())) {
                if (GlobalValues.flyingPlayersKnockIt.contains(p.getUniqueId())) {
                    GlobalValues.flyingPlayersKnockIt.remove(p.getUniqueId());
                    sendMessage(p, "§cDu kannst jetzt nicht mehr fliegen.");
                } else {
                    GlobalValues.flyingPlayersKnockIt.add(p.getUniqueId());
                    sendMessage(p, "§aDu kannst nun fliegen.");
                }
            } else {
                p.setAllowFlight(!p.getAllowFlight());

                if (p.getAllowFlight()) {
                    sendMessage(p, "§aDu kannst nun fliegen.");
                } else {
                    sendMessage(p, "§cDu kannst jetzt nicht mehr fliegen.");
                }
            }
            
            return CommandResult.None;
        } else if (args.length == 1 && isAdmin(p)) {
            Player t = getPlayer(args[0]);

            if (t == null) {
                return CommandResult.PlayerNotOnline;
            } else {
                if (isInWorld(t, getKnockItWorld())) {
                    if (GlobalValues.flyingPlayersKnockIt.contains(t.getUniqueId())) {
                        GlobalValues.flyingPlayersKnockIt.remove(t.getUniqueId());
                        sendMessage(p, getChatName(t) + "§c kann jetzt nicht mehr fliegen.");
                    } else {
                        GlobalValues.flyingPlayersKnockIt.add(t.getUniqueId());
                        sendMessage(p, getChatName(t) + "§a kann nun fliegen.");
                    }
                } else {
                    t.setAllowFlight(!t.getAllowFlight());

                    if (t.getAllowFlight()) {
                        sendMessage(p, getChatName(t) + "§a kann nun fliegen.");
                    } else {
                        sendMessage(p, getChatName(t) + "§c kann jetzt nicht mehr fliegen.");
                    }
                }
            }
            return CommandResult.None;
        } else {
            return CommandResult.InvalidUsage;
        }
    }
}