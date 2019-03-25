package me.toaster.mcamusementcore.entities;

import java.util.ArrayList;

public class CEntityManager {

	public static ArrayList<CEntity> allEntities = new ArrayList<>();
	
	public static void twoFactorClear() {
		if(allEntities.size()>0) {
			for(CEntity e : allEntities) {
				e.remove();
			}
		}
	}
	
	public static void add(CEntity ce) {
		allEntities.add(ce);
	}

}
