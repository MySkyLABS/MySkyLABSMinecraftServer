package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;
import de.basicbit.system.minecraft.HologramSystem;

public class Hologram extends Command {

    private ArrayList<HologramSystem> holograms = new ArrayList<HologramSystem>();

    @Override
    public String getUsage(Player p) {
        return "<Text> [rm]";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("hologram");
        names.add("hologramm");
        names.add("holo");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isDev(p);
    }

    @Override
    public String getDescription(Player p) {
        return "Spawnt ein Hologram";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
         if (args.length == 1) {
            Location loc = p.getLocation();
            World w = p.getWorld();

            double x = loc.getX();
            double y = loc.getY() + 0.5;
            double z = loc.getZ();

            String text = allArgs.replace("&", "§");

            this.holograms.add(new HologramSystem(new Location(w, x, y, z), text));
            sendMessage(p, "§aDein Hologramm wird erstellt...");

            return CommandResult.None;
        } else if (args.length == 2) {
            for (HologramSystem hologram : holograms) {
                String text = allArgs;
                if (hologram.text.equals(text)) {
                    hologram.destroy();
                    sendMessage(p, "§cDein altes Hologramm wurde entfernt!");
                }
            }
            
            return CommandResult.None;
        } else {
            return CommandResult.InvalidUsage;
        }
    }
}
