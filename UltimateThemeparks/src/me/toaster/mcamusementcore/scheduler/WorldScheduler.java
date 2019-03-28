package me.toaster.mcamusementcore.scheduler;

import java.util.Calendar;
import java.util.TimeZone;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitRunnable;

import me.toaster.mcamusementcore.MCACore;
import me.toaster.mcamusementcore.entities.CEntityInstance;
import me.toaster.mcamusementcore.entities.CEntityManager;

public class WorldScheduler extends BukkitRunnable{

	@Override
	public void run() {
		
		if(MCACore.time_keepers.size()>0) {
			TimeZone tz = TimeZone.getTimeZone("PST");
			Calendar c = Calendar.getInstance(tz);
			for(ArmorStand time : MCACore.time_keepers) {
				time.setCustomName(ChatColor.GREEN+""+ChatColor.BOLD+"TIME: "+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND) + "  " + tz.getDisplayName());
			}
		}
		
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
