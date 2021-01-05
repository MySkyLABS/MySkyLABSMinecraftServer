package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.database.Database;
import de.basicbit.system.database.Var;
import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

public class LoginPassword extends Command {

    @Override
    public String getUsage(Player p) {
        return "<Passwort>";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("loginpw");
        names.add("loginpassword");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isDev(p);
    }

    @Override
    public String getDescription(Player p) {
        return "Ändert das Loginpasswort.";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 1) {

            if (args[0].length() >= 8) {
                Database.setVar(Var.password, args[0]);
                sendMessage(p, "§aDas Login-Passwort wurde geändert.");
            } else {
                sendMessage(p, "§cDas Passwort ist unsicher.");
            }

            return CommandResult.None;
        } else {
            return CommandResult.InvalidUsage;
        }
    }
}