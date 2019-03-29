package me.toaster.mcamusementcore.rides.nltc;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.toaster.mcamusementcore.MCACore;
import me.toaster.mcamusementcore.rides.Ride;
import me.toaster.mcamusementcore.rides.Ride.RideType;

public class RollercoasterNLTC extends Ride{

	Location starting;
	TrackRecording recording;
	int length = 4;
	
	public RollercoasterNLTC(Location starting, String rideName) {
		this.starting=starting;
		setRideName(rideName);
		setRideType(RideType.ROLLERCOASTER_NLTC);
		this.recording = new TrackRecording(rideName);
		this.length = Integer.parseInt(this.recording.initialLine); 
	}
	
	@Override
	public boolean spawn() {
		for(int i = 0; i<this.length; i++) {
			String line = this.recording.getStatement(i);
			//
		}
		return false;
	}

	@Override
	public void despawn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void teleportPlayer(Player p) {
		// TODO Auto-generated method stub
		
	}

	
	
}
