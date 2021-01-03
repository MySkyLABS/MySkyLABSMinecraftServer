package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.GlobalValues;
import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

import static de.basicbit.system.minecraft.CommandResult.*;

public class Msg extends Command {

    @Override
    public String getUsage(Player p) {
        return "<Spieler> <Nachricht>";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("msg");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return true;
    }

    @Override
    public String getDescription(Player p) {
        return "Ermöglicht private Nachrichten.";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length > 1) {

            Player t = getPlayer(args[0]);

            if (t == null || isVanish(t)) {
                return PlayerNotOnline;
            } else if (t == p) {
                sendMessage(p, "§cDu kannst dir selbst keine Nachricht schreiben.");
            } else {
                t.sendMessage("" + getChatName(p) + " §7➡ §eDir §8» §e" + allArgs.substring(args[0].length() + 1));
                p.sendMessage("§eDu §7➡ " + getChatName(t) + " §8» §e" + allArgs.substring(args[0].length() + 1));
                GlobalValues.lastMessage.put(p.getUniqueId(), t.getUniqueId());
                GlobalValues.lastMessage.put(t.getUniqueId(), p.getUniqueId());
            }
            
            return None;
        } else {
            return InvalidUsage;
        }
    }
}
