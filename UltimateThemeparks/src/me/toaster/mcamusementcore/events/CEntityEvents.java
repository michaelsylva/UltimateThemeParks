package me.toaster.mcamusementcore.events;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.toaster.mcamusementcore.DebugMode;
import me.toaster.mcamusementcore.entities.CEntityMinecart;
import me.toaster.mcamusementcore.entities.CEntityReferences;

public class CEntityEvents implements Listener{

	/**
	 * TODO load event getting called for both load / unload scenarios
	 * @param e
	 */
	@EventHandler
	public void CEntityLoadEvent(CEntityLoadEvent e) {
		DebugMode.sendDebugInfo("CEntityLoad: " + e.getEntity());
		Location l = e.getEntity().getLocation().clone();
		UUID id = e.getEntity().getUniqueId();
		if(e.getEntity().getType()==EntityType.MINECART) {
			e.getEntity().remove();
			CEntityMinecart mc = new CEntityMinecart(l);
			CEntityReferences.updateReference(id, mc);
		}
	}
	
	@EventHandler
	public void CEntityUnloadEvent(CEntityUnloadEvent e) {
		DebugMode.sendDebugInfo("CEntityUnload: " + e.getCEntity());
	}
}
