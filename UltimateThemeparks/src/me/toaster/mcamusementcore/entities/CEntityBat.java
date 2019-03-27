package me.toaster.mcamusementcore.entities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.toaster.mcamusementcore.MCACore;
import me.toaster.mcamusementcore.events.CEntityUnloadEvent;
import net.minecraft.server.v1_13_R2.EntityBat;
import net.minecraft.server.v1_13_R2.NBTTagCompound;
import net.minecraft.server.v1_13_R2.World;

public class CEntityBat extends EntityBat implements CEntity{

	//FIXED ISSUE WITH NOT BEING ABLE TO REMOVE 3/26/19
	
	public CEntityBat(World world) {
		super(world);
		setSilent(true);
		setNoAI(true);
	}
	
	public CEntityBat(Location l) {
		super(((CraftWorld)l.getWorld()).getHandle());
		this.setPositionRotation(l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
		((CraftWorld)l.getWorld()).getHandle().addEntity(this);
		setSilent(true);
		setNoAI(true);
		CEntityManager.add(new CEntityInstance(this));
		this.getBukkitEntity().setMetadata("centity", new FixedMetadataValue(MCACore.getPlugin(MCACore.class), true));
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
	
	@Override
	public boolean c(NBTTagCompound nbttagcompound) {
		CEntityUnloadEvent event = new CEntityUnloadEvent(this);
		Bukkit.getPluginManager().callEvent(event);
		return super.c(nbttagcompound);
	}

	@Override
	public void setStarting(Location starting) {
		//
	}

}
