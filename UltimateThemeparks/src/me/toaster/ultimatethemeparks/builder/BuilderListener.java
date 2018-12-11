package me.toaster.ultimatethemeparks.builder;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class BuilderListener implements Listener{

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if(UTPBuilder.isBuilding(p)) {
			String message = e.getMessage();
			UTPBuilder builder = UTPBuilder.getBuilder(p);
			if(builder.isWaiting) {
				builder.answer(message);
				e.setCancelled(true);
			}
		}
	}
	
}
