package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;
import de.basicbit.system.minecraft.Gender;

public class Man extends Command {

    @Override
    public String getUsage(Player p) {
        return isAdmin(p) ? "[Spieler]" : "";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("man");
        names.add("boy");
        names.add("mann");
        names.add("junge");
        return names;
    }

    @Override
    public String getDescription(Player p) {
        return isAdmin(p) ? "Setzt dein oder das Geschlecht eines anderen auf männlich." : "Setzt dein Geschlecht auf männlich.";
    }

    @Override
    public boolean hasPermissions(Player p) {
        return true;
    }

    @Override
    public boolean isAsync() {
        return true;
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 1 && isAdmin(p)) {
            Player t = getPlayer(args[0]);

            if (t == null) {
                return CommandResult.PlayerNotOnline;
            }

            setGender(t, Gender.MAN);
            sendMessage(t, "§aDu bist nun als männliches Wesen gekennzeichnet.");
            sendMessage(p, "§aDas Geschlecht des angegebenen Spielers wurde geändert.");
            return CommandResult.None;
        } else if (args.length == 0) {
            setGender(p, Gender.MAN);
            sendMessage(p, "§aDu bist nun als männliches Wesen gekennzeichnet.");
            return CommandResult.None;
        }
        
        return CommandResult.None;
    }
}