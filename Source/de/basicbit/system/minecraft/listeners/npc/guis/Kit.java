package de.basicbit.system.minecraft.listeners.npc.guis;

import org.bukkit.event.EventHandler;

import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.events.PlayerNPCRightClickEvent;
import de.basicbit.system.minecraft.npc.NPC;
import de.basicbit.system.minecraft.npc.NPCType;

public class Kit extends Listener {
    
    @EventHandler
    public void onInteractAtNPC(PlayerNPCRightClickEvent e) {
        NPC npc = e.getNPC();

        if (npc.getType().equals(NPCType.Kit)) {
            de.basicbit.system.minecraft.commands.Kit.openKitGui(e.getPlayer());
        }
    }
}