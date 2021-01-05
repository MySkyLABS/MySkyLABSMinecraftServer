package de.basicbit.system.minecraft.listeners.npc;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.npc.NPC;

public class NPCBounceListener extends Listener {
    
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Location playerPos = p.getLocation();

        for (NPC npc : NPC.getNPCs()) {
            Location npcPos = npc.getLocation();
            if (p.getWorld().getName().contentEquals(npcPos.getWorld().getName())) {
                if (npcPos.distance(playerPos) <= 1) {
                    p.setVelocity(playerPos.toVector().subtract(npcPos.toVector()));
                }
            }
        }
    }
}