package me.toaster.mcamusementcore.rides;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.entity.Player;

import me.toaster.mcamusementcore.entities.CEntity;

/**
 * Abstract Ride class. Encapsulates all rides with its general functions
 * @author Michael Sylva
 *
 */
public abstract class Ride{

	//TODO export and create rides
	
	public ArrayList<CEntity> entities = new ArrayList<CEntity>();
	
	/**
	 * Enumerator for ride type
	 * @author Michael Sylva
	 */
	
	//TODO add isOpen...
	private boolean isRunning = false;
	private boolean isSpawned = false;
	
	private RideType RIDE_TYPE;
	private UUID SESSION_ID;
	
	public enum RideType {
		ROLLERCOASTER,
		FLATRIDE;
	}
	
	public Ride() {}

	private String rideName;
	
	public static ArrayList<Ride> rides = new ArrayList<Ride>();
	
	/**
	 * Set the ride name of this ride instance
	 * @param name name of ride
	 */
	public void setRideName(String name) {
		this.rideName = name;
	}
	
	/**
	 * Get ride name of this ride instance
	 * @return name of this ride
	 */
	public String getRideName() {
		return this.rideName;
	}
	
	/**
	 * Span the ride. Called when the server starts up. Or when there is a malfunction
	 * and it is manually done
	 */
	public abstract boolean spawn();
	
	/**
	 * De-spawn the ride. Called when the server closes, or there is a malfunction and it is
	 * manually done
	 */
	public abstract void despawn();
	
	/**
	 * Setup the ride.
	 */
	public abstract void setup();
	
	/**
	 * Start the ride cycle
	 */
	public abstract void start();
	
	/**
	 * Complete the ride cycle
	 */
	public abstract void finish();
	
	/**
	 * Reset the ride
	 */
	public abstract void reset();

	
	public abstract void teleportPlayer(Player p);

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public boolean isSpawned() {
		return isSpawned;
	}

	public void setSpawned(boolean isSpawned) {
		this.isSpawned = isSpawned;
	}
	
	public static Ride getRideByName(String name) {
		for(Ride r : rides) {
			if(r.getRideName().equalsIgnoreCase(name)) {
				return r;
			}
		}
		return null;
	}

	public UUID getSessionID() {
		return SESSION_ID;
	}

	public void setSessionID(UUID sessionID) {
		SESSION_ID = sessionID;
	}
	
	public UUID createSessionID() {
		setSessionID(UUID.randomUUID());
		return this.SESSION_ID;
	}

	public RideType getRideType() {
		return RIDE_TYPE;
	}

	public void setRideType(RideType ride_type) {
		RIDE_TYPE = ride_type;
	}
	
	public ArrayList<Ride> getRides(){
		return this.rides;
	}
}

