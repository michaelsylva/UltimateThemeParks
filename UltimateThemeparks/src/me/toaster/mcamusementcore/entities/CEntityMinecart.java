package me.toaster.mcamusementcore.entities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftMinecart;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import me.toaster.mcamusementcore.MCACore;
import me.toaster.mcamusementcore.events.CEntityLoadEvent;
import me.toaster.mcamusementcore.events.CEntityUnloadEvent;
import me.toaster.mcamusementcore.rides.Ride;
import net.minecraft.server.v1_13_R2.ChatComponentText;
import net.minecraft.server.v1_13_R2.DamageSource;
import net.minecraft.server.v1_13_R2.Entity;
import net.minecraft.server.v1_13_R2.EntityMinecartAbstract;
import net.minecraft.server.v1_13_R2.EntityMinecartRideable;
import net.minecraft.server.v1_13_R2.NBTTagCompound;
import net.minecraft.server.v1_13_R2.World;

public class CEntityMinecart extends EntityMinecartRideable implements CEntity{
	
	//TODO fix minecarts colliding with each other.
	//FIXED. This happened when an CEntity turned into a EntityMinecart
	public boolean allowPassengers = true;
	private Location starting;

	public CEntityMinecart(World world) {
		super(world);
	}

	public CEntityMinecart(Location l) {
		super(((CraftWorld)l.getWorld()).getHandle());
		this.setNoGravity(true);
		this.starting = l;
		this.setPositionRotation(l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
		((CraftWorld)l.getWorld()).getHandle().addEntity(this);
		this.setCustomNameVisible(true);
		CEntityManager.add(new CEntityInstance(this));
		this.persist = true;
		this.getBukkitEntity().setMetadata("centity", new FixedMetadataValue(MCACore.getPlugin(MCACore.class), true));
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
	public boolean damageEntity(DamageSource source, float f) {
		return false;
	}

	@Override
	public void collide(Entity entity) {}

	//TODO Overrided in the entity class the "NBT Save" that saves the entity when a chunk is unloaded or
	// a player is not nearby
	@Override
	public boolean c(NBTTagCompound nbttagcompound) {
		CEntityUnloadEvent event = new CEntityUnloadEvent(this);
		Bukkit.getPluginManager().callEvent(event);
		return super.c(nbttagcompound);
	}

	@Override
	public void setStarting(Location starting) {
		this.starting=starting;
	}
}
