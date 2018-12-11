package me.toaster.ultimatethemeparks.items;

import org.bukkit.entity.Player;

import me.toaster.ultimatethemeparks.balloons.Balloon;
import me.toaster.ultimatethemeparks.balloons.BalloonManager;

public class UTPItem {

	ItemType type;
	
	public UTPItem(ItemType type) {
		this.type = type;
	}
	
	public enum ItemType{
		BALLOON,
		WAND
	}
	
	public static boolean isValidType(String type) {
		for(ItemType t : ItemType.values()) {
			if(t.name().equalsIgnoreCase(type)) {
				return true;
			}
		}
		return false;
	}
	
	public static UTPItem createByType(String type) {
		for(ItemType t : ItemType.values()) {
			if(t.name().equalsIgnoreCase(type)) {
				return new UTPItem(t);
			}
		}
		return null;
	}
	
	//TODO Already have that item...
	public boolean give(Player p, String data) {
		if(type==ItemType.BALLOON) {
			if(BalloonManager.getBalloonByName(data)!=null) {
				Balloon b = BalloonManager.getBalloonByName(data);
				b.giveBalloon(p);
				return true;
			}
		}else if(type==ItemType.WAND) {
			//TODO wand
		}
		return false;
	}
	
}
