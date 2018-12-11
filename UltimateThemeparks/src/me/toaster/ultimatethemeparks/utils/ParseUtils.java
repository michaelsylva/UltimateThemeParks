package me.toaster.ultimatethemeparks.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class ParseUtils {

	public static boolean isInt(String s) {
		try {
			Integer.parseInt(s);
		}catch(Exception e) {
			return false;
		}
		return true;
	}
	
	public static String locationToString(Location l) {
		return l.getWorld().getName()+","+l.getX()+","+l.getY()+","+l.getZ()+","+l.getYaw()+","+l.getPitch();
	}
	
	public static Location stringToLocation(String s) {
		String[] delim = s.split(",");
		World world = Bukkit.getWorld(delim[0]);
		double x = Double.parseDouble(delim[1]);
		double y = Double.parseDouble(delim[2]);
		double z = Double.parseDouble(delim[3]);
		float yaw = Float.parseFloat(delim[4]);
		float pitch = Float.parseFloat(delim[5]);
		
		return new Location(world, x, y, z,yaw,pitch);
	}
}
