package de.basicbit.system.minecraft.commands;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;

public class Ram extends Command {

    @Override
    public String getUsage(Player p) {
        return "";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("ram");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isInTeam(p);
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 0) {

            Runtime r = Runtime.getRuntime();

            sendMessage(p, "§r");
            sendMessage(p, "§eMaximaler RAM: §7" + r.maxMemory() + " Bytes");
            sendMessage(p, "§eFreier RAM: §7" + r.freeMemory() + " Bytes");
            sendMessage(p, "§eRAM-Nuntzung: §7" + r.totalMemory() + " Bytes (§b" + new DecimalFormat("0.00").format(((double)r.freeMemory() * 100.0 / (double)r.maxMemory())) + "%§7)\n");
            sendMessage(p, "§r");

            return CommandResult.None;
        } else {
            return CommandResult.InvalidUsage;
        }
    }

    @Override
    public boolean isAsync() {
        return true;
    }

    @Override
    public String getDescription(Player p) {
        return "Gibt Informationen über die RAM-Auslastung.";
    }
}