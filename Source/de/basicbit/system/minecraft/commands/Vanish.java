package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

import de.basicbit.system.GlobalValues;
import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

import static de.basicbit.system.minecraft.CommandResult.*;

public class Vanish extends Command {

    @Override
    public String getUsage(Player p) {
        return "<Spieler>";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("vanish");
        names.add("v");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isInTeam(p);
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 0) {
            setVanish(p, !isVanish(p));

            if (isVanish(p)) {
                sendMessage(p, "§aDu bist nun unsichtbar.");
                Wolf wolf = GlobalValues.dogPets.get(p.getUniqueId());
                if (wolf != null && !wolf.isDead()) {
                    wolf.remove();
                    GlobalValues.dogPets.remove(p.getUniqueId(), wolf);
                    sendMessage(p, "§cDein Hund wurde entfernt.");
                }
            } else {
                sendMessage(p, "§cDu bist nun sichtbar.");
            }

            return None;
        }

        return InvalidUsage;
    }

    @Override
    public String getDescription(Player p) {
        return "Macht dich oder andere Spieler unsichtbar.";
    }
}
