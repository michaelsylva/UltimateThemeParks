package me.toaster.mcamusementcore.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.inventory.ItemStack;

import me.toaster.mcamusementcore.MCACore;
import me.toaster.mcamusementcore.balloons.BalloonManager;
import me.toaster.mcamusementcore.balloons.Balloon.ActiveBalloon;
import me.toaster.mcamusementcore.items.MCAItem;
import me.toaster.mcamusementcore.scheduler.ActionBarMessage;
import me.toaster.mcamusementcore.scheduler.ActionBarScheduler;
import me.toaster.mcamusementcore.show.ShowManager;

public class MCAPlayerEvents implements Listener{

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		ActionBarMessage msg = new ActionBarMessage(ChatColor.AQUA+"Welcome to "+MCACore.serverName + "! - " + ChatColor.ITALIC+MCACore.serverMotto, 10, 80, "join");
		ActionBarScheduler.setActionBar(p, msg);
		if(p.isOp()) {
			p.sendMessage(ChatColor.GREEN + "You are running " + MCACore.MCA_CORE.getName() + " v" +MCACore.MCA_CORE.getDescription().getVersion() + " - " + ChatColor.BOLD+"Report any bugs to Amids_ or Mr_toaster111.");
		}
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
			if(MCAItem.isSpecialItem(is)) {
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
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if(ShowManager.isEditting(p)) {
			ShowManager.makeCommand(p, ChatColor.stripColor(e.getMessage()));
			e.setCancelled(true);
		}
	}
	
}
