package me.toaster.mcamusementcore.builder;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class BuilderListener implements Listener{

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if(MCABuilder.isBuilding(p)) {
			String message = e.getMessage();
			MCABuilder builder = MCABuilder.getBuilder(p);
			if(builder.isWaiting) {
				builder.answer(message);
				e.setCancelled(true);
			}
		}
	}
	
}
