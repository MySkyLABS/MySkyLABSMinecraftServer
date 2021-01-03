package de.basicbit.system.minecraft.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import de.basicbit.system.minecraft.npc.NPC;

public class PlayerNPCRightClickEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();
    private NPC npc;

    public PlayerNPCRightClickEvent(Player who, NPC npc) {
        super(who);
        this.npc = npc;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public NPC getNPC() {
        return npc;
    }
}
