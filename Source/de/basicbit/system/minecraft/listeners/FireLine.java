package de.basicbit.system.minecraft.listeners;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import de.basicbit.system.GlobalValues;
import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.tasks.TaskManager;
import net.minecraft.server.v1_8_R1.EnumParticle;
import net.minecraft.server.v1_8_R1.PacketPlayOutWorldParticles;

public class FireLine extends Listener {
    
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Block b = p.getTargetBlock((Set<Material>)null, 100);
        Location end = new Location(b.getWorld(), b.getX() + 0.5, b.getY() + 0.5, b.getZ() + 0.5);

        if ((e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR) && GlobalValues.fireLinePlayers.contains(p.getUniqueId())) {
            TaskManager.runAsyncTask(new Runnable() {
        
                @Override
                public void run() {
                    Location start = p.getEyeLocation();
                    start = new Location(start.getWorld(), start.getX(), start.getY() - 0.25, start.getZ());

                    createFireLine(p, b.getType() != Material.AIR, start, end, 0.1);
                }
            });

            e.setCancelled(true);
        }
    }

    public void createFireLine(Player d, boolean endIsBlock, Location point1, Location point2, double space) {
        World world = point2.getWorld();
        double distance = point1.distance(point2);
        Vector p1 = point1.toVector();
        Vector p2 = point2.toVector();
        Vector vector = p2.clone().subtract(p1).normalize().multiply(space);
        double length = 0;
        d.playSound(p1.toLocation(world), Sound.FIRE, 10, 1);
        for (; length < distance; p1.add(vector)) {
            for (LivingEntity le : world.getLivingEntities()) {
                if (le instanceof Player) {
                    sendPacket((Player)le, new PacketPlayOutWorldParticles(EnumParticle.FLAME, true, (float)p1.getX(),
                    (float)p1.getY(), (float)p1.getZ(), 0, 0, 0, 0, 1));
                }
                
                if (((le instanceof Player && !((Player)le).getName().contentEquals(d.getName())) || !(le instanceof Player)) && (p1.toLocation(world).distance(le.getLocation()) <= 2 || p1.toLocation(world).distance(le.getEyeLocation()) <= 2)) {
                    TaskManager.runSyncTask("FireLine", new Runnable() {
                            
                        @Override
                        public void run() {
                            le.setFireTicks(100);
                            le.damage(10);
                            le.setVelocity(vector.normalize().multiply(2));
                        }
                    });
                    for (Player p : world.getPlayers()) {
                        sendPacket(p, new PacketPlayOutWorldParticles(EnumParticle.LAVA, true, (float)p1.getX(),
                            (float)p1.getY(), (float)p1.getZ(), 1, 1, 1, 0, 250));
                        p.playSound(p1.toLocation(world), Sound.EXPLODE, 10, 1);
                    }
                    return;
                }
            }
            length += space;
            try {
                Thread.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (endIsBlock) {
            for (Player p : world.getPlayers()) {
                sendPacket(p, new PacketPlayOutWorldParticles(EnumParticle.LAVA, true, (float)p1.getX(),
                    (float)p1.getY(), (float)p1.getZ(), 1, 1, 1, 0, 250));
                p.playSound(p1.toLocation(world), Sound.EXPLODE, 10, 1);
            }
        }
    }
}