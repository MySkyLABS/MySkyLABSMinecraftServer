package de.basicbit.system.minecraft.listeners.npc;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.npc.NPC;

public class NPCUpdate extends Listener {
    
	public static void onChunkChange(Player p, Chunk toChunk) {
		NPC.update(p, toChunk);
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		Chunk toChunk = e.getTo().getChunk();

		if (!e.getFrom().getChunk().equals(toChunk)) {
			onChunkChange(p, toChunk);
		}
    }
}