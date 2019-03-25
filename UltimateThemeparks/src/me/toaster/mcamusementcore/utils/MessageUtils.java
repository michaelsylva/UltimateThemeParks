package me.toaster.mcamusementcore.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.toaster.mcamusementcore.MCAPermission;

public class MessageUtils {

	public static void messageStaff(String msg) {
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(p.hasPermission(MCAPermission.UTP_STAFF.getValue())) {
				p.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"[STAFF] "+ChatColor.RESET+msg);
			}
		}
	}
	
}
