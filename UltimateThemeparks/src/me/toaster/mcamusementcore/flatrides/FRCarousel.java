package me.toaster.mcamusementcore.flatrides;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.toaster.mcamusementcore.MCACore;
import me.toaster.mcamusementcore.entities.CEntity;
import me.toaster.mcamusementcore.entities.CEntityMinecart;
import me.toaster.mcamusementcore.flatrides.FRFrisbee.FrisbeeRunnable;
import me.toaster.mcamusementcore.rides.Ride;
import me.toaster.mcamusementcore.utils.ArrayUtils;
import me.toaster.mcamusementcore.utils.PhysicsUtil;

public class FRCarousel extends Ride{

	final int RADIUS = 4;
	final int SEATS = 9;
	
	final Location center;
	BukkitTask task;
	
	int STARTING_TICKS = 180;
	int TICKS = 0;
	
	public FRCarousel(Location center) {
		this.center = center;
		this.setRideType(RideType.FLATRIDE);
		this.setRideName("Carousel");
	}
	
	@Override
	public boolean spawn() {
		System.out.println("spawn ride...");
		ArrayList<Location> circle = FlatrideUtils.getCircle(center, RADIUS, SEATS);
		for(Location l : circle) {
			CEntityMinecart car = new CEntityMinecart(l);
			this.entities.add(car);
		}
		setSpawned(true);
		return true;
	}

	@Override
	public void setup() {

	}

	@Override
	public void start() {
		this.createSessionID();
		Bukkit.broadcastMessage("Starting " + this.getRideType());
		setRunning(true);
		
		ArrayUtils.setAllowPassengers(false, entities);
		
		task = new CarouselRunnable(this).runTaskTimer(MCACore.getPlugin(MCACore.class), 1L, 1L);
	}

	@Override
	public void finish() {
		task.cancel();
		setRunning(false);
		for(CEntity mc : this.entities) {
			mc.eject();
		}

		reset();
		
		ArrayUtils.setAllowPassengers(true, entities);
		
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
	public void reset() {
		for(CEntity mc : this.entities) {
			mc.setPositionRotation(mc.getStarting());
		}
	}

	@Override
	public void teleportPlayer(Player p) {
		p.teleport(new Location(center.getWorld(),102,90,505));
	}
	
	class CarouselRunnable extends BukkitRunnable{

		double theta_Y = 0.0;
		double spin_speed = 0.2;
		
		FRCarousel carousel;
		double height_data[];
		double direction_data[];
		int moving = 0;
		
		int START_ENDING = 800;
		
		public CarouselRunnable(FRCarousel carousel) {
			this.carousel = carousel;
			this.height_data = ArrayUtils.zero_arr(new double[carousel.entities.size()]);
			this.direction_data = ArrayUtils.zero_arr(new double[carousel.entities.size()]);
		}
		
		@Override
		public void run() {
			if(isRunning()) {
				if(TICKS>STARTING_TICKS) {
					for(int i = 0; i<this.carousel.entities.size(); i++) {
						CEntity mc = this.carousel.entities.get(i);
						Location newLoc = PhysicsUtil.rotateAroundY(mc.getStarting(), center, theta_Y);
						newLoc.setY(mc.getStarting().getY()+height_data[i]);
						mc.setPositionRotation(newLoc);
					}
					
					//Accelerate at beginning
					if(TICKS>=200 && TICKS<=300) {
						spin_speed+=0.015;
						//to get to 1.5 speed in 5 seconds...
					}
					
					
					if(moving<this.carousel.entities.size()) {
						if(TICKS%9==0) {
							this.direction_data[moving] = 0.02;
							moving++;
						}
					}
					
					//Flip them
					for(int i = 0; i<this.height_data.length; i++) {
						if(height_data[i]>=1) {
							this.direction_data[i] = -0.02;
						}else if(height_data[i]<0) {
							//if ending dont change back to up
							if(TICKS>=START_ENDING) {
								this.direction_data[i] = 0;
							}else {
								this.direction_data[i] = 0.02;
							}
						}
					}
					
					//Add the direction to it's current height
					for(int i = 0; i<this.direction_data.length; i++) {
						this.height_data[i] += this.direction_data[i];
					}
					
					if(TICKS>=900 && TICKS<=1000) {
						spin_speed-=0.015;
					}
					
					if(TICKS>=1000) {
						spin_speed=0;
					}
					
					if(TICKS>=1050) {
						finish();
					}
					
					//While running
					theta_Y += spin_speed;
				}
			}
			TICKS++;
		}
		
	}

	
	
}
