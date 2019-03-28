package me.toaster.mcamusementcore.scheduler;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import me.toaster.mcamusementcore.entities.CEntity;
import me.toaster.mcamusementcore.entities.CEntityMinecart;
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
				//Bukkit.broadcastMessage("Ride: " + r.getRideName() + " entities: " + r.entities.size());
				for(int i = 0; i<entities.size(); i++) {
					CEntity ent = entities.get(i);
					if(ent instanceof CEntityMinecart) {
						CEntityMinecart cem = (CEntityMinecart) ent;
						if(!cem.isAlive()) {
							if(r.isSpawned()) {
								//Bukkit.broadcastMessage("Something is very wrong with the ride : " + r);
							}
						}
					}
				}
			}
		}

	}

}
