package de.basicbit.system.minecraft.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ServerCommunicationSystemMessageEvent extends Event {
    private static HandlerList l = new HandlerList();

    private String msg;

    public ServerCommunicationSystemMessageEvent(String msg) {
        this.msg = msg;
    }

	@Override
	public HandlerList getHandlers() {
		return l;
	}

    public static HandlerList getHandlerList() {
        return l;
    }

    public String getMessage() {
        return msg;
    }
}