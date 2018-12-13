package me.toaster.ultimatethemeparks.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.inventory.ItemStack;

import me.toaster.ultimatethemeparks.UTPCore;
import me.toaster.ultimatethemeparks.balloons.Balloon.ActiveBalloon;
import me.toaster.ultimatethemeparks.balloons.BalloonManager;
import me.toaster.ultimatethemeparks.items.UTPItem;
import me.toaster.ultimatethemeparks.scheduler.ActionBarMessage;
import me.toaster.ultimatethemeparks.scheduler.ActionBarScheduler;

public class UTPPlayerEvents implements Listener{

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		ActionBarMessage msg = new ActionBarMessage(ChatColor.AQUA+"Welcome to "+UTPCore.serverName + "! - " + ChatColor.ITALIC+UTPCore.serverMotto, 10, 80, "join");
		ActionBarScheduler.setActionBar(p, msg);
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if(ActionBarScheduler.message.containsKey(p)) {
			ActionBarScheduler.clearActionBar(p);
		}
	}
	
	@EventHandler
	public void onPlayerDrop(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		Item i = e.getItemDrop();
		if(i!=null) {
			ItemStack is = i.getItemStack();
			if(UTPItem.isSpecialItem(is)) {
				if(BalloonManager.hasActiveBalloon(p)) {
					ActiveBalloon balloon = BalloonManager.getActiveBalloon(p);
					balloon.fly();
					i.remove();
				}else {
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerEnterVehicle(VehicleEnterEvent e) {
		if(e.getEntered() instanceof Player) {
			Player p = (Player) e.getEntered();
			if(BalloonManager.hasActiveBalloon(p)) {
				ActiveBalloon balloon = BalloonManager.getActiveBalloon(p);
				balloon.despawn();
				BalloonManager.despawnedBalloon(p);
			}
		}
	}
	
}
