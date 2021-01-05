package de.basicbit.system.minecraft.commands;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.entity.Player;

import de.basicbit.system.database.UserData;
import de.basicbit.system.database.UserValue;
import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

public class Unban extends Command {

    @Override
    public String getUsage(Player p) {
        return "<Spieler>";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("unban");
        names.add("pardon");
        return names;
    }

    @Override
    public String getDescription(Player p) {
        return "Entsperrt einen Spieler";
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isAdmin(p);
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 1) {

            UUID id = UserData.getUUIDFromName(args[0]);

            if (id == null) {
                sendMessage(p, "§cDieser Spieler ist nicht in der Datenbank.");
            }

            if (UserData.getLong(id, UserValue.bannedUntil) < Instant.now().getEpochSecond()) {
                sendMessage(p, "§cDieser Spieler ist nicht gesperrt.");
            } else {
                UserData.set(id, UserValue.bannedUntil, 0);
                sendMessage(p, "§aDieser Spieler ist jetzt entsperrt.");
            }

            return CommandResult.None;
        } else {
            return CommandResult.InvalidUsage;
        }
    }

    @Override
    public boolean isAsync() {
        return true;
    }
}