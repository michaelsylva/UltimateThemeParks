package me.toaster.ultimatethemeparks.balloons;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import me.toaster.ultimatethemeparks.utils.Cooldown;

public class BalloonListener implements Listener{

	@EventHandler
	public void changeInventory(PlayerItemHeldEvent e) {
		Player p = e.getPlayer();
		ItemStack newItem = p.getInventory().getItem(e.getNewSlot());
		ItemStack prevItem = p.getInventory().getItem(e.getPreviousSlot());

		Balloon b1 = BalloonManager.getBalloonFromItem(newItem);
		Balloon b2 = BalloonManager.getBalloonFromItem(prevItem);

		if(b2!=null) {
			b2.despawn(p);
		}

		
		if(b1!=null) {
			if(!p.isInsideVehicle()) {
				b1.spawn(p);
			}else {
				p.sendMessage(ChatColor.RED+"You cannot hold a balloon on a ride!");
			}
		}

	}
	
	//TODO player teleport event to move balloon...

}
