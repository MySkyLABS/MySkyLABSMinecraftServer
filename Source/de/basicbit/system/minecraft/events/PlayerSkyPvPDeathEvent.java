package de.basicbit.system.minecraft.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerEvent;

public class PlayerSkyPvPDeathEvent extends PlayerEvent {
    private static HandlerList l = new HandlerList();
    private Player d;
    private PlayerDeathEvent event;

    public PlayerSkyPvPDeathEvent(Player p, Player d, PlayerDeathEvent event) {
        super(p);
        this.d = d;
        this.event = event;
    }

    public Player getKiller() {
        return d;
    }

    public PlayerDeathEvent getEvent() {
        return event;
    }

    @Override
    public HandlerList getHandlers() {
        return l;
    }

    public static HandlerList getHandlerList() {
        return l;
    }
}