package me.toaster.mcamusementcore.entities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import me.toaster.mcamusementcore.MCACore;
import me.toaster.mcamusementcore.balloons.Balloon.ActiveBalloon;
import me.toaster.mcamusementcore.events.CEntityUnloadEvent;
import me.toaster.mcamusementcore.rides.Ride;
import net.minecraft.server.v1_13_R2.EntityArmorStand;
import net.minecraft.server.v1_13_R2.NBTTagCompound;
import net.minecraft.server.v1_13_R2.World;

public class CEntityArmorstand extends EntityArmorStand implements CEntity{

	public boolean allowPassengers = true;
	private Location starting;
	public ActiveBalloon balloon = null;
	
	public CEntityArmorstand(World world) {
		super(world);
	}

	public CEntityArmorstand(Location l) {
		super(((CraftWorld)l.getWorld()).getHandle());
		this.setNoGravity(true);
		this.starting = l;
		this.setPositionRotation(l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
		((CraftWorld)l.getWorld()).getHandle().addEntity(this);
		CEntityManager.add(new CEntityInstance(this));
		this.persist = true;
		this.getBukkitEntity().setMetadata("centity", new FixedMetadataValue(MCACore.getPlugin(MCACore.class), true));
		
	}
	
	public CEntityArmorstand(Location l, Material head) {
		super(((CraftWorld)l.getWorld()).getHandle());
		this.setNoGravity(true);
		ArmorStand as = (ArmorStand) getBukkitEntity();
		as.setHelmet(new org.bukkit.inventory.ItemStack(head));
		this.starting = l;
		this.setPositionRotation(l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
		((CraftWorld)l.getWorld()).getHandle().addEntity(this);
		CEntityManager.add(new CEntityInstance(this));
		this.persist = true;
		this.getBukkitEntity().setMetadata("centity", new FixedMetadataValue(MCACore.getPlugin(MCACore.class), true));
	}
	
	public CEntityArmorstand(Location l, ItemStack head) {
		super(((CraftWorld)l.getWorld()).getHandle());
		this.setNoGravity(true);
		ArmorStand as = (ArmorStand) getBukkitEntity();
		as.setHelmet(head);
		this.starting = l;
		this.setInvisible(true);
		this.setPositionRotation(l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
		((CraftWorld)l.getWorld()).getHandle().addEntity(this);
		CEntityManager.add(new CEntityInstance(this));
		this.persist = true;
		this.getBukkitEntity().setMetadata("centity", new FixedMetadataValue(MCACore.getPlugin(MCACore.class), true));
	}
	
	public void setAsBalloon(ActiveBalloon b) {
		this.balloon = b;
	}
	
	public boolean isActiveBalloon() {
		return this.balloon!=null;
	}
	
	public void setHead(ItemStack is) {
		ArmorStand as = (ArmorStand) this.getBukkitEntity();
		as.setHelmet(is);
	}
	
	public ArmorStand getBukkitArmorstand() {
		return (ArmorStand)this.getBukkitEntity();
	}
	
	@Override
	public Location getStarting() {
		return this.starting;
	}

	@Override
	public void eject() {
		this.ejectPassengers();
	}

	@Override
	public void remove() {
		this.killEntity();
	}

	@Override
	public void setPositionRotation(Location l) {
		this.setPositionRotation(l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
	}

	@Override
	public Location getLocation() {
		return getBukkitEntity().getLocation();
	}
	
	//TODO Overrided in the entity class the "NBT Save" that saves the entity when a chunk is unloaded or
	// a player is not nearby
	@Override
	public boolean c(NBTTagCompound nbttagcompound) {
		CEntityUnloadEvent event = new CEntityUnloadEvent(this);
		Bukkit.getPluginManager().callEvent(event);
		if(isActiveBalloon()) {
			this.balloon.despawn();
		}
		return super.c(nbttagcompound);
	}

	@Override
	public void setStarting(Location starting) {
		this.starting=starting;
	}

	
}
