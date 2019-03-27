package me.toaster.mcamusementcore.entities;

import java.util.ListIterator;
import java.util.UUID;

import me.toaster.mcamusementcore.DebugMode;
import me.toaster.mcamusementcore.rides.Ride;

public class CEntityReferences {

	public static void updateReference(UUID oldid, CEntity newEnt) {
		//Fix references in the allEntities list...
		for (final ListIterator<CEntityInstance> i = CEntityManager.allEntities.listIterator(); i.hasNext();) {
			final CEntityInstance cei = i.next();
			if(cei.entityUUID.equals(oldid)) {
				newEnt.setStarting(cei.entity.getStarting());
				i.set(new CEntityInstance(newEnt));
				DebugMode.sendDebugInfo("Updated references for + " + oldid + " in allentities");
			}
		}

		//Fix references in the ride list...
		for (int i = 0; i<Ride.rides.size(); i++) {
			final Ride r = Ride.rides.get(i);
			for (final ListIterator<CEntity> iter = r.entities.listIterator(); iter.hasNext();) {
				final CEntity ce = iter.next();
				if(ce.getBukkitEntity().getUniqueId().equals(oldid)) {
					newEnt.setStarting(ce.getStarting());
					iter.set(newEnt);
					DebugMode.sendDebugInfo("Updated references for " + oldid + " in rides");
				}
			}
		}
	}

}
