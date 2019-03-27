package me.toaster.mcamusementcore.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.toaster.mcamusementcore.entities.CEntity;

public class CEntityUnloadEvent extends Event{

	private static final HandlerList handlers = new HandlerList();
    private CEntity ce;

    public CEntityUnloadEvent(CEntity e) {
        this.ce = e;
    }

    public CEntity getCEntity() {
        return this.ce;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }
	
}
