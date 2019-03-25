package me.toaster.mcamusementcore.entities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;

import net.minecraft.server.v1_13_R2.Entity;
import net.minecraft.server.v1_13_R2.EntityMinecartRideable;
import net.minecraft.server.v1_13_R2.World;

public class CEntityMinecart extends EntityMinecartRideable implements CEntity{

	public boolean allowPassengers = true;
	private Location starting;
	
	public CEntityMinecart(World world) {
		super(world);
		CEntityManager.add(this);
	}
	
	public CEntityMinecart(Location l) {
		super(((CraftWorld)l.getWorld()).getHandle());
		this.setNoGravity(true);
		this.starting = l;
		this.setPositionRotation(l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
		((CraftWorld)l.getWorld()).getHandle().addEntity(this);
		this.setCustomNameVisible(true);
	}
	
	@Override
	public void setPositionRotation(Location l) {
		this.setPositionRotation(l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
	}
	
	@Override
	public void remove() {
		this.killEntity();
	}
	
	@Override
	public void eject() {
		this.ejectPassengers();
	}

	@Override
	public Location getLocation() {
		return this.getBukkitEntity().getLocation();
	}
	
	@Override
	public Location getStarting() {
		return this.starting;
	}
	
	@Override
	public void collide(Entity entity) {}
	
}