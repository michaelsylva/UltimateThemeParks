package me.toaster.ultimatethemeparks.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import me.toaster.ultimatethemeparks.UTPPermission;
import me.toaster.ultimatethemeparks.items.UTPItem.ItemGiveResult;
import me.toaster.ultimatethemeparks.utils.ParseUtils;

public class ItemListener implements Listener{

	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		Player p = e.getPlayer();
		if(e.getLine(0).equalsIgnoreCase("[Buy]")) {
			if(p.hasPermission(UTPPermission.UTP_ITEM_BUY_SIGNPLACE.getValue())) {
				if(ParseUtils.isInt(e.getLine(3))) {
					int price = Integer.parseInt(e.getLine(3));
					e.setLine(0, ChatColor.DARK_PURPLE+"[BUY]");
					String type = e.getLine(1);
					String name = e.getLine(2);
					e.setLine(2, ChatColor.BOLD+name);
				}else {
					p.sendMessage(ChatColor.RED+"Invalid price!");
				}
			}
		}else if(e.getLine(0).equalsIgnoreCase("[Free]")) {
			if(p.hasPermission(UTPPermission.UTP_ITEM_FREE_SIGNPLACE.getValue())) {
				e.setLine(0, ChatColor.DARK_PURPLE+"[FREE]");
				String type = e.getLine(1);
				String name = e.getLine(2);
				e.setLine(2, ChatColor.BOLD+name);
			}
		}

	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(e.getAction()==Action.RIGHT_CLICK_BLOCK) {
			Block blk = e.getClickedBlock();
			if(blk.getType()==Material.WALL_SIGN || blk.getType()==Material.SIGN_POST) {
				if(blk.getState() instanceof Sign) {
					Sign s = (Sign) blk.getState();
					if(s.getLine(0).equalsIgnoreCase(ChatColor.DARK_PURPLE+"[BUY]")) {
						
					}else if(s.getLine(0).equalsIgnoreCase(ChatColor.DARK_PURPLE+"[FREE]")) {
						String type = s.getLine(1);
						String name = ChatColor.stripColor(s.getLine(2));
						if(UTPItem.isValidType(type)) {
							UTPItem item = UTPItem.createByType(type);
							ItemGiveResult result = item.give(p, name);
							p.sendMessage(result.getMessage());
						}
					}
				}
			}
		}
	}

}
