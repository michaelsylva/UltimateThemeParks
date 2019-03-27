package me.toaster.mcamusementcore.entities;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public class CEntityManager {

	
	/*
	 * TODO: Save UUID to list instead of CEntity instance to ensure the entities are removed
	 * properly.
	 */
	public static ArrayList<CEntityInstance> allEntities = new ArrayList<>();
	
	public static void twoFactorClear() {
		if(allEntities.size()>0) {
			for(CEntityInstance e : allEntities) {
				for(World w : Bukkit.getServer().getWorlds()) {
					for(Entity ent : w.getEntities()) {
						if(ent.getUniqueId()==e.entityUUID) {
							ent.remove();
						}
					}
				}
			}
		}
	}
	
	public static void add(CEntityInstance ce) {
		allEntities.add(ce);
	}

}
