package me.toaster.mcamusementcore.scheduler;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import me.toaster.mcamusementcore.entities.CEntityArmorstand;
import me.toaster.mcamusementcore.entities.CEntityBat;
import me.toaster.mcamusementcore.entities.CEntityInstance;
import me.toaster.mcamusementcore.entities.CEntityManager;
import me.toaster.mcamusementcore.entities.CEntityMinecart;
import me.toaster.mcamusementcore.utils.WorldUtils;
import net.minecraft.server.v1_13_R2.MinecraftServer;

public class WorldScheduler extends BukkitRunnable{

	@Override
	public void run() {
		if(CEntityManager.allEntities.size()>0) {
			for(CEntityInstance ci : CEntityManager.allEntities) {
				Bukkit.getEntity(ci.entityUUID);
				/*if(c instanceof CEntityArmorstand) {
					((CEntityArmorstand)c).activatedTick = MinecraftServer.currentTick;
					//((CEntityArmorstand)c).tick();
				}
				else if(c instanceof CEntityMinecart) {
					((CEntityMinecart)c).activatedTick = MinecraftServer.currentTick;
					//((CEntityMinecart)c).tick();
				}
				else if(c instanceof CEntityBat) {
					((CEntityBat)c).activatedTick = MinecraftServer.currentTick;
					//((CEntityBat)c).tick();
				}*/
			}
		}else {
			System.out.println("Gone...");
		}
		
		/*for(CEntityInstance ci : CEntityManager.allEntities) {
			WorldUtils.loadChunks(ci.entity.getLocation(), 10);
		}*/
		
	}

}
