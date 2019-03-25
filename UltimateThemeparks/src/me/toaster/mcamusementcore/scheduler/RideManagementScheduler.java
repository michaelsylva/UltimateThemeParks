package me.toaster.mcamusementcore.scheduler;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import me.toaster.mcamusementcore.entities.CEntity;
import me.toaster.mcamusementcore.rides.Ride;

/**
 * TODO unimplemented
 * A scheduler run throughout the server running checking all the rides to
 * see if they are running correctly. Will detect malfunctions in rides and
 * attempt to fix them, or report them to staff members
 * @author Michael Sylva
 */

public class RideManagementScheduler extends BukkitRunnable{

	@Override
	public void run() {
		
		if(Ride.rides.size()>0) {
			for(Ride r : Ride.rides) {
				ArrayList<CEntity> entities = r.entities;
				for(int i = 0; i<entities.size(); i++) {
					if(entities.get(i).getBukkitEntity().isDead()) {
						//Do something
					}
				}
			}
		}
		
	}
	
}
