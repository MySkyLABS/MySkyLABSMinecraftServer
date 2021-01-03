package de.basicbit.system.minecraft.listeners.npc.guis;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.events.PlayerNPCRightClickEvent;
import de.basicbit.system.minecraft.npc.NPC;
import de.basicbit.system.minecraft.npc.NPCType;
import de.basicbit.system.minecraft.tasks.TaskManager;

public class KnockIt extends Listener {
    
    @EventHandler
    public void onInteractAtNPC(PlayerNPCRightClickEvent e) {
        NPC npc = e.getNPC();
        Player p = e.getPlayer();

        if (npc.getType().equals(NPCType.KnockIt)) {
            TaskManager.runSyncTask("KnockItJoin", new Runnable(){
            
                @Override
                public void run() {
                    de.basicbit.system.minecraft.KnockIt.onJoin(p);
                }
            });
        }

        if (npc.getType().equals(NPCType.KnockItBackToSpawn)) {
            TaskManager.runSyncTask("KnockItJoin", new Runnable(){
            
                @Override
                public void run() {
                    p.teleport(getSpawn());
                }
            });
        }
    }
}