package me.toaster.ultimatethemeparks.entities;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;

public interface CEntity {

	//TODO allow passengers boolean

	public abstract void eject();
	
	public abstract void remove();
	
	public abstract void setPositionRotation(Location l);
	
	public abstract Location getStarting();
	
	public abstract Location getLocation();
	
	public abstract CraftEntity getBukkitEntity();
}
