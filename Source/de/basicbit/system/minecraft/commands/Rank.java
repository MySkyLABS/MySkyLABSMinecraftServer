package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.entity.Player;

import de.basicbit.system.database.UserValue;
import de.basicbit.system.database.UserData;
import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;
import de.basicbit.system.minecraft.Group;

public class Rank extends Command {

    @Override
    public String getUsage(Player p) {
        return "<Spieler> <Rang>";
    }

    @Override
    public String getDescription(Player p) {
        return "Ermöglicht die Verwaltung von Rängen.";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("rank");
        names.add("rang");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isContent(p);
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 2) {
            if (isAdmin(p)) {
                UUID id = UserData.getUUIDFromName(args[0]);
    
                if (id == null) {
                    return CommandResult.PlayerNotInDataBase;
                } else {
                    Group group;
    
                    try {
                        group = Group.valueOf(args[1].toUpperCase());
                    } catch (Exception e) {
                        sendMessage(p, "§cDer angegebene Rang existiert nicht.");
                        return CommandResult.None;
                    }
    
                    UserData.set(id, UserValue.playerGroup, group.toString());
    
                    Player t = getPlayer(id);
                    if (t != null) {
                        t.kickPlayer("§cDein Rang wurde geändert.");
                    }
    
                    sendMessage(p, "§aDie Gruppe des angegebenen Spielers wurde geändert.");
                }
            } else {
                Player t = getPlayer(args[0]);

                if (t == null) {
                    p.sendMessage("§cDu darfst den Rang nur ändern, wenn der Spieler online ist.");
                } else {
                    Group group;
    
                    try {
                        group = Group.valueOf(args[1].toUpperCase());
                    } catch (Exception e) {
                        sendMessage(p, "§cDer angegebene Rang existiert nicht.");
                        return CommandResult.None;
                    }

                    if (isInTeam(t)) {
                        p.sendMessage("§cDu darft den Rang dieses Spielers nicht ändern.");
                    } else if (isGroupAllowedContent(group) && isGroupAllowedContent(getGroup(t))) {
                        UserData.set(t, UserValue.playerGroup, group.toString());

                        t.kickPlayer("§cDein Rang wurde geändert.");
                        sendMessage(p, "§aDie Gruppe des angegebenen Spielers wurde geändert.");
                    }
                }
            }

            return CommandResult.None;
        } else {
            return CommandResult.InvalidUsage;
        }
    }

    private boolean isGroupAllowedContent(Group group) {
        return group == Group.MEMBER ||
            group == Group.STAMMI ||
            group == Group.HERO ||
            group == Group.SENPAI ||
            group == Group.PROMO ||
            group == Group.PROMO_PLUS;
    }
}
