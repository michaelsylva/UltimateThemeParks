package me.toaster.ultimatethemeparks.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.toaster.ultimatethemeparks.UTPCore;
import me.toaster.ultimatethemeparks.scheduler.ActionBarMessage;
import me.toaster.ultimatethemeparks.scheduler.ActionBarScheduler;

public class UTPPlayerJoin implements Listener{

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
	
}
