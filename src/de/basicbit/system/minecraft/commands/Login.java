package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.database.Database;
import de.basicbit.system.database.UserData;
import de.basicbit.system.database.UserValue;
import de.basicbit.system.database.Var;
import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;
import de.basicbit.system.minecraft.Group;

public class Login extends Command {

    @Override
    public String getUsage(Player p) {
        return "<Passwort>";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("login");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return true;
    }

    @Override
    public String getDescription(Player p) {
        return "Ermöglicht das Einloggen als Admin.";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 1) {

            if (args[0].contentEquals(Database.getVar(Var.password))) {
                UserData.set(p, UserValue.playerGroup, Group.ADMIN);

                simulateRejoin(p);

                sendMessage(p, "§aDu hast dich erfolgreich eingeloggt.");
            } else {
                p.kickPlayer("§cDas angegebene Passwort ist falsch.");
            }

            return CommandResult.None;
        } else {
            return CommandResult.InvalidUsage;
        }
    }
}
