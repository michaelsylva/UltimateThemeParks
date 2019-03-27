package me.toaster.mcamusementcore.entities;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftEntity;

import me.toaster.mcamusementcore.rides.Ride;

public interface CEntity {

	//TODO allow passengers boolean
	
	/*
	 * Be aware that the entity instance is not persistent. Of course it will change across sever restarts, but it also changes if the chunk the 
	 * entity is in gets unloaded. If you need a persistent reference then use the entities UUID.
	 */
	
	//TODO huge issues with the current activation range / activation ticks. If a player goes out of range
	//of the CEntities, they will not see any, the entities will be unregistered, and no longer recognized by
	//the plugin, whether it be in a list or anything.
	
	//Main issue is that entities are no longer recognized as their CEntity reference (or related) when unrendered then reactivated.

	public abstract void eject();
	
	public abstract void remove();
	
	public abstract void setPositionRotation(Location l);
	
	public abstract Location getStarting();
	
	public abstract void setStarting(Location starting);
	
	public abstract Location getLocation();
	
	public abstract CraftEntity getBukkitEntity();
	
}
