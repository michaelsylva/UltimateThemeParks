package me.toaster.mcamusementcore.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.world.ChunkLoadEvent;

public class WorldEvents implements Listener{

	//Will not work...
	/*@EventHandler 
	public void onChunkUnload(ChunkUnloadEvent e){
		for(CEntity c : CEntityManager.allEntities) {
			if(c.getLocation().getChunk().equals(e.getChunk())) {
				e.setCancelled(true);
			}
		}
	}*/
	
	//TODO FIX LEASH FROM DROPPING FROM BALLOONS WHEN UNRENDERED.
	//BELIEVED TO BE FIXED 3/26/19

	@EventHandler
	public void onChunkLoad(ChunkLoadEvent e) {
		for(Entity ent : e.getChunk().getEntities()) {
			if(ent.hasMetadata("centity")) {
				CEntityLoadEvent event = new CEntityLoadEvent(ent);
				Bukkit.getPluginManager().callEvent(event);
			}
		}
	}
}
