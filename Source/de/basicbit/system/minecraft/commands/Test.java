package de.basicbit.system.minecraft.commands;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;
import de.basicbit.system.minecraft.simpleblockarea.SimpleBlockAreaFile;

public class Test extends Command {

    @Override
    public String getUsage(Player p) {
        return "";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("test");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isDev(p);
    }

    @Override
    public String getDescription(Player p) {
        return "Ein Command zum Testen.";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        try {
            if (args[0].equalsIgnoreCase("A")) {
                SimpleBlockAreaFile sbaFile;
                sbaFile = new SimpleBlockAreaFile(new File("island0;0.sba"));
                sbaFile.paste(p.getLocation());
                sendMessage(p, "yes");
            } else {
                SimpleBlockAreaFile.create(new File("island0;0.sba"), p.getLocation());
            }
        } catch (Exception e) {
            sendMessage(p, "no1");
            e.printStackTrace();
        }
        return CommandResult.None;
    }
}
