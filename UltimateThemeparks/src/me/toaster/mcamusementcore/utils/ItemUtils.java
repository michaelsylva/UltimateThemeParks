package me.toaster.mcamusementcore.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemUtils {

	public static ItemStack getItemWithData(Material item, short data) {
		ItemStack is = new ItemStack(item);
		is.setDurability(data);
		return is;
	}

	public static boolean isSimilar(ItemStack i1, ItemStack i2) {
		if(i1.hasItemMeta() && i2.hasItemMeta()) {
			ItemMeta m1 = i1.getItemMeta();
			ItemMeta m2 = i2.getItemMeta();
			if(m1.getDisplayName()!= null && m2.getDisplayName()!=null) {
				if(m1.getDisplayName().equals(m2.getDisplayName())) {
					if(i1.getType()==i1.getType()) {
						if(i1.getDurability()==i2.getDurability()) {
							if(m1.hasLore() && m2.hasLore()) {
								if(m1.getLore().equals(m2.getLore())) {
									return true;
								}
							}else {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	public static ItemStack getHeadItem(String name) {
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1, (short)3);
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		meta.setOwner(name);
		meta.setDisplayName(ChatColor.LIGHT_PURPLE + name + " balloon");
		skull.setItemMeta(meta);
		return skull;
	}
}
