package me.toaster.ultimatethemeparks.rides;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.toaster.ultimatethemeparks.UTPCore;

public class Train {

	Car[] cars;
	int length;
	TrainType type;
	double speed = 3;
	
	int trainDistance = 850;
	int backIndex = 0;
	
	int stationStart = 0;
	int stationEnd = 0;
	
	enum TrainType{
		MODEL,
		MINECART;
	}
	
	public Train(int length, TrainType type) {
		if(type==TrainType.MODEL) {
			this.cars = new ModelCar[length];
		}else if(type==TrainType.MINECART) {
			this.cars = new MinecartCar[length];
		}else {
			//Default
			this.cars = new MinecartCar[length];
		}
		this.type = type;
		this.stationEnd = (length-1)*trainDistance;
	}
	
	/**
	 * Check if the Vector is going downwards
	 * @param v Vector to check
	 * @return boolean if going down
	 */
	public boolean isGoingDown() {
		Vector v = getFront().going;
		if(v.getY()<0) {
			return true;
		}
		return false;
	}

	/**
	 * Check if the Vector is going up
	 * @param v Vector to check
	 * @return boolean if going up
	 */
	public boolean isGoingUp() {
		Vector v = getFront().going;
		if(v.getY()>0) {
			return true;
		}
		return false;
	}
	
	public Car getFront() {
		return cars[cars.length-1];
	}
	
	public int getIndexOfCar(Car c) {
		for(int i = 0; i<cars.length; i++) {
			if(cars[i].equals(c)) {
				return i;
			}
		}
		return -1;
	}
	
	public boolean isInStation() {
		for(Car c : cars) {
			if(getPositionOfCar(c)>=stationStart && getPositionOfCar(c)<=stationEnd) {
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public void updateMessage(String message) {
		for(Car c : cars) {
			c.entity.getBukkitEntity().setCustomNameVisible(true);
			c.entity.getBukkitEntity().setCustomName(message);
		}
		
		Bukkit.getScheduler().runTaskLater(UTPCore.getProvidingPlugin(UTPCore.class), new BukkitRunnable() {

			@Override
			public void run() {
				for(Car c : cars) {
					c.entity.getBukkitEntity().setCustomNameVisible(false);
					c.entity.getBukkitEntity().setCustomName("");
				}
			}
			
		}, 40);
	}
	
	public boolean isCarInTrain(Car c) {
		return getIndexOfCar(c)!=-1;
	}
	
	public int getPositionOfCar(Car c) {
		int idx = getIndexOfCar(c);
		if(idx!=-1) {
			return idx*trainDistance+backIndex;
		}
		return 0;
	}
	
	public void update() {
		this.backIndex+=(int)this.speed;
	}
	
	public Car getBack() {
		return cars[0];
	}
	
	public void increaseSpeed(double amount) {
		this.speed+=amount;
	}
	
	public void decreaseSpeed(double amount) {
		this.speed-=amount;
	}
}