package me.toaster.mcamusementcore.events;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CEntityLoadEvent extends Event{

	private static final HandlerList handlers = new HandlerList();
    private Entity entity;

    public CEntityLoadEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }

}
