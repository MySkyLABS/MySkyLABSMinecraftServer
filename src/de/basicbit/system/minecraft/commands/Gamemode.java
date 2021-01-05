package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

public class Gamemode extends Command {

    @Override
    public String getUsage(Player p) {
        return "<0/1/2/3> [Spieler]";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("gamemode");
        names.add("gm");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isModerator(p);
    }

    @Override
    public String getDescription(Player p) {
        return "Ändert den Spielmodus eines Spielers.";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 1) {
            Player t = p;

            if (args[0].contentEquals("0")) {
                t.setGameMode(GameMode.SURVIVAL); 

                if (t == p) {
                    sendMessage(p, "§aDu befindest dich nun im Überlebensmodus.");
                } else {
                    sendMessage(p, getChatName(t) + " §abefindet sich nun im Überlebensmodus.");
                }
            } else if (args[0].contentEquals("1")) {
                t.setGameMode(GameMode.CREATIVE);

                if (t == p) {
                    sendMessage(p, "§aDu befindest dich nun im Kreativsmodus.");
                } else {
                    sendMessage(p, getChatName(t) + " §abefindet sich nun im Kreativsmodus.");
                }
            } else if (args[0].contentEquals("2")) {
                t.setGameMode(GameMode.ADVENTURE);

                if (t == p) {
                    sendMessage(p, "§aDu befindest dich nun im Abenteuermodus.");
                } else {
                    sendMessage(p, getChatName(t) + " §abefindet sich nun im Abenteuermodus.");
                }
            } else if (args[0].contentEquals("3")) {
                t.setGameMode(GameMode.SPECTATOR);

                if (t == p) {
                    sendMessage(p, "§aDu befindest dich nun im Zuschauermodus.");
                } else {
                    sendMessage(p, getChatName(t) + " §abefindet sich nun im Zuschauermodus.");
                }
            } else {
                sendMessage(p, "§cDieser Spielmodus existiert nicht.");
            }
            return CommandResult.None;
        } else {
            return CommandResult.InvalidUsage;
        }
    }
}
