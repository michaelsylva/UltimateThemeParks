package me.toaster.mcamusementcore.utils;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect.Type;
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
	
	public static Location getLocationStrings(String world, String x, String y, String z) {
		Location l;
		try {
			World worldE = Bukkit.getWorld(world);
			if(world==null) {
				return null;
			}
			double xE = Double.parseDouble(x);
			double yE = Double.parseDouble(y);
			double zE = Double.parseDouble(z);
			l = new Location(worldE,xE,yE,zE);
		}catch(NumberFormatException e) {
			return null;
		}
		return l;
	}
	
	public static ArrayList<Color> parseColors(String str){
		ArrayList<Color> colors = new ArrayList<>();
		if(str.contains(",")) {
			String delim[] = str.split(",");
			for(String s : delim) {
				Color c = parseColor(s);
				if(c!=null) {
					colors.add(c);
				}
			}
		}else {
			Color c = parseColor(str);
			if(c!=null) {
				colors.add(c);
			}
		}
		return colors;
	}
	
	public static Color parseColor(String s) {
		if(s.contains("-")) {
			if(s.split("-").length==3) {
				String[] split = s.split("-");
				if(isInt(split[0]) && isInt(split[1]) && isInt(split[2])) {
					return Color.fromRGB(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
				}
			}
		}else {
			return colorByName(s);
		}
		return null;
	}
	
	public static Color colorByName(String colorname)
	{
		Color[] clr = { Color.WHITE, Color.SILVER, Color.GRAY, Color.BLACK, 
				Color.RED, Color.MAROON, Color.YELLOW, Color.OLIVE, 
				Color.LIME, Color.GREEN, Color.AQUA, Color.TEAL, 
				Color.BLUE, Color.NAVY, Color.FUCHSIA, Color.PURPLE };
		String[] clrs = { "WHITE", "SILVER", "GRAY", "BLACK", 
				"RED", "MAROON", "YELLOW", "OLIVE", 
				"LIME", "GREEN", "AQUA", "TEAL", 
				"BLUE", "NAVY", "FUCHSIA", "PURPLE" };
		for (int i = 0; i < clrs.length; i++) {
			if (colorname.equalsIgnoreCase(clrs[i])) {
				return clr[i];
			}
		}
		return Color.WHITE;
	}

	public static Type typeByName(String type)
	{
		Type[] tp = {Type.BALL, Type.BALL_LARGE, Type.BURST,
				Type.CREEPER, Type.STAR};
		String[] typs = {"ball", "ball_large", "burst", "creeper", "star"};

		for(int i = 0; i < typs.length; i++)
		{
			if(type.equalsIgnoreCase(typs[i]))
			{
				return tp[i];
			}
		}

		return null;

	}


}
