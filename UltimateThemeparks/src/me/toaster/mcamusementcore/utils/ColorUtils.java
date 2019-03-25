package me.toaster.mcamusementcore.utils;

import org.bukkit.ChatColor;

public class ColorUtils {

	public static String colorStringWithBool(String message, boolean t) {
		if(t) {
			return ChatColor.GREEN+message;
		}else {
			return ChatColor.RED+message;
		}
	}
	
}
