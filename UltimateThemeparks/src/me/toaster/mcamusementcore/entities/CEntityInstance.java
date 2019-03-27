package me.toaster.mcamusementcore.entities;

import java.util.UUID;

public class CEntityInstance {

	public UUID entityUUID;
	public CEntity entity;
	
	public CEntityInstance(CEntity e) {
		this.entity = e;
		this.entityUUID = e.getBukkitEntity().getUniqueId();
	}
	
}
