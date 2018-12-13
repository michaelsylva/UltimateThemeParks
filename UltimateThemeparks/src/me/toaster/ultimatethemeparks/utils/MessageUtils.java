package me.toaster.ultimatethemeparks.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.toaster.ultimatethemeparks.UTPPermission;

public class MessageUtils {

	public static void messageStaff(String msg) {
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(p.hasPermission(UTPPermission.UTP_STAFF.getValue())) {
				p.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"[STAFF] "+ChatColor.RESET+msg);
			}
		}
	}
	
}
