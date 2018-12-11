package me.toaster.ultimatethemeparks.utils;

import org.bukkit.Location;

public class CompareUtils {

	public static boolean isBlockLocationSame(Location l, Location l1) {
		return (l.getBlockX()==l1.getBlockX() && l.getBlockY()==l1.getBlockY() && l.getBlockZ()==l1.getBlockZ());
	}
	
}
