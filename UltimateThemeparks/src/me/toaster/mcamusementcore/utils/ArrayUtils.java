package me.toaster.mcamusementcore.utils;

import java.util.ArrayList;

import me.toaster.mcamusementcore.entities.CEntity;
import me.toaster.mcamusementcore.entities.CEntityMinecart;

public class ArrayUtils {

	public static int[] zero_arr(int[] a) {
		for(int i = 0; i<a.length; i++) {
			a[i] = 0;
		}
		return a;
	}
	
	public static double[] zero_arr(double[] a) {
		for(int i = 0; i<a.length; i++) {
			a[i] = 0.0d;
		}
		return a;
	}
	
	public static void setAllowPassengers(boolean b, ArrayList<CEntity> entities) {
		for(int i = 0; i<entities.size(); i++) {
			CEntityMinecart cem = (CEntityMinecart) entities.get(i);
			cem.allowPassengers=b;
		}
	}
	
}
