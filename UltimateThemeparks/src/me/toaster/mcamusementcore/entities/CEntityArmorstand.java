package me.toaster.mcamusementcore.entities;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_13_R2.EntityArmorStand;
import net.minecraft.server.v1_13_R2.World;

public class CEntityArmorstand extends EntityArmorStand implements CEntity{

	public boolean allowPassengers = true;
	private Location starting;
	
	public CEntityArmorstand(World world) {
		super(world);
		CEntityManager.add(this);
	}

	public CEntityArmorstand(Location l) {
		super(((CraftWorld)l.getWorld()).getHandle());
		this.setNoGravity(true);
		this.starting = l;
		this.setPositionRotation(l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
		((CraftWorld)l.getWorld()).getHandle().addEntity(this);
	}
	
	public CEntityArmorstand(Location l, Material head) {
		super(((CraftWorld)l.getWorld()).getHandle());
		this.setNoGravity(true);
		ArmorStand as = (ArmorStand) getBukkitEntity();
		as.setHelmet(new org.bukkit.inventory.ItemStack(head));
		this.starting = l;
		this.setPositionRotation(l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
		((CraftWorld)l.getWorld()).getHandle().addEntity(this);
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
	
}
