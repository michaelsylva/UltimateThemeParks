package me.toaster.ultimatethemeparks.flatrides;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.World;

public class FlatrideUtils {
	
	public static ArrayList<Location> getCircle(Location center, double radius, int amount){
        World world = center.getWorld();
        double increment = (2*Math.PI)/amount;
        ArrayList<Location> locations = new ArrayList<Location>();
        for(int i = 0;i < amount; i++){
        double angle = i*increment;
        float yaw = (float) Math.toDegrees(angle);
        double x = center.getX() + (radius * Math.cos(angle));
        double z = center.getZ() + (radius * Math.sin(angle));
        locations.add(new Location(world, x, center.getY(), z, (float) yaw, 0.0f));
        }
        return locations;
    }
	
	public static ArrayList<Location> getCircle(Location center, double radius, int amount, double degStart){
        World world = center.getWorld();
        double increment = (2*Math.PI)/amount;
        ArrayList<Location> locations = new ArrayList<Location>();
        for(int i = 0;i < amount; i++){
        double angle = (i*increment)+degStart;
        double x = center.getX() + (radius * Math.cos(angle));
        double z = center.getZ() + (radius * Math.sin(angle));
        locations.add(new Location(world, x, center.getY(), z, (float) angle, 0.0f));
        }
        return locations;
    }
	
}
