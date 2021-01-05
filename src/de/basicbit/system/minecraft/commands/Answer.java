package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.GlobalValues;
import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;
import static de.basicbit.system.minecraft.CommandResult.*;

public class Answer extends Command {

    @Override
    public String getUsage(Player p) {
        return "<Nachricht>";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("r");
        names.add("answer");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return true;
    }

    @Override
    public String getDescription(Player p) {
        return "Antworte auf eine Nachricht.";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (GlobalValues.lastMessage.containsKey(p.getUniqueId())) {
            Player t = getPlayer(GlobalValues.lastMessage.get(p.getUniqueId()));
            if (t != null && !isVanish(t)) {
                t.sendMessage(getChatName(p) + " §7➡ §eDir §8» §e" + allArgs);
                p.sendMessage("§eDu §7➡ " + getChatName(t) + " §8» §e" + allArgs);
                return None;
            }
        }
        sendMessage(p, "§cDu hast bis her keine Unterhaltungen.");
        return None;
    }
}
