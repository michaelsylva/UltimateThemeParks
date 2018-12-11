package me.toaster.ultimatethemeparks.flatrides;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.toaster.ultimatethemeparks.UTPCore;
import me.toaster.ultimatethemeparks.entities.CEntity;
import me.toaster.ultimatethemeparks.entities.CEntityMinecart;
import me.toaster.ultimatethemeparks.rides.Ride;
import me.toaster.ultimatethemeparks.utils.PhysicsUtil;

public class FRFrisbee extends Ride{

	int TICKS = 0;

	final int RADIUS = 3;
	final int SEATS = 18;

	final int START_TICKS = 20*5;
	final int END_TICKS = 20*110;

	final int START_SWING_TICKS = 20*5;
	final int END_SWING_TICKS = 20*100;

	final int START_SPIN_TICKS = 20*15;
	final int END_SPIN_TICKS = 20*110;

	Location center = new Location(Bukkit.getWorld("world"),97,90,505);
	Location pivot = new Location(Bukkit.getWorld("world"),97,98,505);
	
	BukkitTask task;

	public FRFrisbee() {
		this.setRideType(RideType.FLATRIDE);
		this.setRideName("Frisbee");
	}

	public FRFrisbee(Location center, Location pivot) {
		this.center = center;
		this.pivot = pivot;
		this.setRideType(RideType.FLATRIDE);
		this.setRideName("Frisbee");
		this.spawn();
	}

	@Override
	public void setup() {

	}

	@Override
	public void start() {
		this.createSessionID();
		Bukkit.broadcastMessage("Starting " + this.getRideType());
		setRunning(true);
		task = new FrisbeeRunnable(this).runTaskTimer(UTPCore.getPlugin(UTPCore.class), 1L, 1L);
	}

	@Override
	public void finish() {
		task.cancel();
		setRunning(false);
		for(CEntity mc : this.entities) {
			mc.eject();
		}
		reset();
		setSessionID(null);
		TICKS = 0;
	}

	@Override
	public void despawn() {
		for(CEntity mc : this.entities) {
			mc.remove();
		}
		setSpawned(false);
	}

	@Override
	public boolean spawn() {
		ArrayList<Location> circle = FlatrideUtils.getCircle(center, RADIUS, SEATS);
		for(Location l : circle) {
			CEntityMinecart car = new CEntityMinecart(l);
			this.entities.add(car);
		}
		setSpawned(true);
		return true;
	}
	
	@Override
	public void reset() {
		for(CEntity mc : this.entities) {
			mc.setPositionRotation(mc.getStarting());
		}
	}

	@Override
	public void teleportPlayer(Player p) {
		p.teleport(new Location(center.getWorld(),102,90,505));
	}
	
	class FrisbeeRunnable extends BukkitRunnable{

		double theta_Y = 0.6;
		double theta_Z = 0.0;

		double swing_speed = 0.6;
		double spin_speed = 0.4;
		
		double max_swing_angle = 20;
		boolean swing_direction = true;
		boolean max_swing = false;
		
		FRFrisbee frisbee;
		
		public FrisbeeRunnable(FRFrisbee frisbee) {
			this.frisbee = frisbee;
			
		}
		
		@Override
		public void run() {
			if(isRunning()) {
				if(TICKS > START_TICKS && TICKS < END_TICKS) {
					for(CEntity mc : this.frisbee.entities) {
						Location newLoc = PhysicsUtil.rotateAroundY(mc.getStarting(), center, theta_Y);
						newLoc = PhysicsUtil.rotateAroundZ(newLoc, pivot, theta_Z);
						mc.setPositionRotation(newLoc);
					}

					if(TICKS > START_SWING_TICKS && TICKS < END_SWING_TICKS) {
						if(theta_Z>max_swing_angle || theta_Z<-max_swing_angle) {
							swing_direction=!swing_direction;
						}else {

						}
						if(swing_direction) theta_Z+=swing_speed;
						else theta_Z-=swing_speed;

					}

					if(TICKS > START_SPIN_TICKS && TICKS < END_SPIN_TICKS) {
						theta_Y+=spin_speed;
					}

					if(TICKS==200) {
						swing_speed+=0.4;
						spin_speed+=0.2;
						max_swing_angle+=15;
					}else if(TICKS==290) {
						swing_speed+=0.4;
						spin_speed+=0.2;
						max_swing_angle+=15;
					}else if(TICKS==380) {
						swing_speed+=0.5;
						spin_speed+=0.4;
						max_swing_angle+=20;
					}else if(TICKS==440) {
						swing_speed+=0.4;
						spin_speed+=0.6;
						max_swing_angle+=20;
					}else if(TICKS==680) {
						max_swing_angle+=20;
						spin_speed=0.0;
					}else if(TICKS==780) {
						spin_speed=-2.5;
					}else if(TICKS==990) {
						max_swing_angle+=10;
					}else if(TICKS==1400) {
						max_swing_angle-=20;
						spin_speed+=0.6;
						swing_speed-=0.2;
					}else if(TICKS==1580) {
						max_swing_angle-=30;
						spin_speed+=0.4;
						swing_speed-=0.4;
					}else if(TICKS==1780) {
						max_swing_angle-=20;
						spin_speed+=0.2;
						swing_speed-=0.4;
					}else if(TICKS==1830) {
						max_swing_angle-=10;	
						swing_speed-=0.2;
					}else if(TICKS>1950) {
						if(theta_Z==-3) {
							swing_speed=0;
						}
						if(TICKS>2030) {
							spin_speed=0;
						}
					}
				}

				if(TICKS > END_TICKS) {
					finish();
				}

				TICKS++;
			}
		}

	}

}