package me.toaster.ultimatethemeparks.utils;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class MathUtils {

	public static float getLookAtYaw(Vector motion) {
        double dx = motion.getX();
        double dz = motion.getZ();
        double yaw = 0;
        // Set yaw
        if (dx != 0) {
            // Set yaw start value based on dx
            if (dx < 0) {
                yaw = 1.5 * Math.PI;
            } else {
                yaw = 0.5 * Math.PI;
            }
            yaw -= Math.atan(dz / dx);
        } else if (dz < 0) {
            yaw = Math.PI;
        }
        return (float) (-yaw * 180 / Math.PI - 90);
    }
	
	public static float getLookAtPitch(Location first, Location second) {
		double dx = second.getX() - first.getX();
        double dy = second.getY() - first.getY();
        double dz = second.getZ() - first.getZ();
		double dxz = Math.sqrt(Math.pow(dx, 2) + Math.pow(dz, 2));
		float pitch = (float) -Math.atan(dy / dxz);

	    pitch = pitch * 180f / (float) Math.PI;
	    return pitch;
	}
	
	public static float avg(float... nums) {
		float c = 0.0f;
		float total = 0.0f;
		for(int i = 0; i<nums.length; i++) {
			total+=nums[i];
			c++;
		}
		if(c!=0) {
			return total/c;
		}else {
			return -1;
		}
	}
	
	public static boolean blockLocationEqual(Location l1, Location l2) {
		if(l1.getBlockX()==l2.getBlockX() &&
				l1.getBlockY()==l2.getBlockY() &&
				l1.getBlockZ()==l2.getBlockZ()) {
			return true;
		}
		return false;
	}
	
}
