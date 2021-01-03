package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;
import java.util.Timer;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;
import de.basicbit.system.minecraft.HologramSystem;

@SuppressWarnings("all")
public class Firework extends Command {

    private HologramSystem hologram = null;
    static int interval;
    static Timer timer;

    @Override
    public String getUsage(Player p) {
        return "";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("firework");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isDev(p);
    }

    @Override
    public String getDescription(Player p) {
        return "Spawnt ein Feuerwerk";
    }

    @SuppressWarnings("all")
    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 0) {
            Location loc = p.getLocation();
            World w = p.getWorld();

            w.spawnEntity(loc.add(4, 0, 4), EntityType.FIREWORK); // +4 0 +4
            w.spawnEntity(loc.add(-8, 0, 0), EntityType.FIREWORK); // -4 0 4
            w.spawnEntity(loc.add(8, 0, -8), EntityType.FIREWORK); // 4 0 -4
            w.spawnEntity(loc.add(-8, 0, 0), EntityType.FIREWORK); // -4 0 -4
            loc.add(4, 0, 4);

            w.strikeLightning(loc);
            w.spawnFallingBlock(loc, Material.CHEST, (byte) 0x0);

            double x = loc.getBlockX() + 0.5;
            double y = loc.getBlockY() + 0.5;
            double z = loc.getBlockZ() + 0.5;

            long timestamp = System.currentTimeMillis();

            Firework.this.hologram = new HologramSystem(new Location(w, x, y + 1, z), "§aKiste öffnen in: §e5§7:§e00");

            // 1 Loop für alle Holos
            // Liste für Holos
            // Counter nicht mehr anzeigen wenn 0
            /*
            TaskManager.runAsyncLoop("Spawn Chest", new Runnable() {
                @Override
                public void run() {
                    long diff = (5 * 1000 * 60) - (System.currentTimeMillis() - timestamp);
                    Date date = new Date(diff);
                    if (Firework.this.hologram != null) {
                        Firework.this.hologram.destroy();
                    }
                    String seconds = String.valueOf(date.getSeconds());
                    Firework.this.hologram.setText("§aKiste öffnen in: §e" + (date.getMinutes()) + "§7:§e" + (seconds.length() == 1 ? "0" : "") + seconds);
                }
            }, 20);
            */

            return CommandResult.None;
        } else {
            return CommandResult.InvalidUsage;
        }
    }
}
