package me.toaster.ultimatethemeparks.entities;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.minecraft.server.v1_12_R1.EntityBat;
import net.minecraft.server.v1_12_R1.World;

public class CEntityBat extends EntityBat implements CEntity{

	public CEntityBat(World world) {
		super(world);
		setSilent(true);
		setNoAI(true);
		CEntityManager.add(this);
	}
	
	public CEntityBat(Location l) {
		super(((CraftWorld)l.getWorld()).getHandle());
		this.setPositionRotation(l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
		((CraftWorld)l.getWorld()).getHandle().addEntity(this);
		setSilent(true);
		setNoAI(true);
	}
	
	public void setLeashHolder(Entity e) {
		Bat bat = (Bat) this.getBukkitEntity();
		bat.setLeashHolder(e);
	}

	@Override
	public void eject() {
		// TODO Auto-generated method stub
		
	}
	
	public void setBatInvisible() {
		this.getBukkitBat().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
	}
	
	public Bat getBukkitBat() {
		return (Bat)this.getBukkitEntity();
	}

	@Override
	public void remove() {
		this.die();
	}

	@Override
	public void setPositionRotation(Location l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Location getStarting() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location getLocation() {
		return this.getBukkitEntity().getLocation();
	}
}
