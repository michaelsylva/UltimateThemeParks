package me.toaster.mcamusementcore.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.toaster.mcamusementcore.balloons.Balloon;
import me.toaster.mcamusementcore.balloons.BalloonManager;

public class MCAItem {

	ItemType type;
	
	public MCAItem(ItemType type) {
		this.type = type;
	}
	
	public enum ItemType{
		BALLOON,
		WAND;
	}
	
	public static boolean isSpecialItem(ItemStack is) {
		if(is!=null) {
			if(is.hasItemMeta()) {
				ItemMeta meta = is.getItemMeta();
				String name = ChatColor.stripColor(meta.getDisplayName());
				if(name.contains("balloon")) {
					if(is.getType()==Material.PLAYER_HEAD) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static boolean isValidType(String type) {
		for(ItemType t : ItemType.values()) {
			if(t.name().equalsIgnoreCase(type)) {
				return true;
			}
		}
		return false;
	}
	
	public static MCAItem createByType(String type) {
		for(ItemType t : ItemType.values()) {
			if(t.name().equalsIgnoreCase(type)) {
				return new MCAItem(t);
			}
		}
		return null;
	}
	
	//TODO Already have that item...
	public ItemGiveResult give(Player p, String data) {
		if(type==ItemType.BALLOON) {
			if(BalloonManager.getBalloonByName(data)!=null) {
				Balloon b = BalloonManager.getBalloonByName(data);
				if(!b.hasBalloon(p)) {
					b.giveBalloon(p);
					return ItemGiveResult.SUCCESS;
				}else {
					return ItemGiveResult.ALREADY_HAVE;
				}
			}
		}else if(type==ItemType.WAND) {
			//TODO wand
		}
		return ItemGiveResult.UNKNOWN_ITEM;
	}
	
	public enum ItemGiveResult{
		SUCCESS(ChatColor.GREEN+"Here you go!"),
		INVENTORY_FULL(ChatColor.RED+"Your inventory is full!"),
		UNKNOWN_ITEM(ChatColor.RED+"Unable to retrieve item"),
		ALREADY_HAVE(ChatColor.RED+"You already have this item!");
		
		String msg;
		ItemGiveResult(String msg) {
			this.msg = msg;
		}
		
		public String getMessage() {
			return this.msg;
		}
	}
}
