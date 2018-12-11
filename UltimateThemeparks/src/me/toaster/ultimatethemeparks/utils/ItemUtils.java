package me.toaster.ultimatethemeparks.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemUtils {

	public static ItemStack getItemWithData(Material item, short data) {
		ItemStack is = new ItemStack(item);
		is.setDurability(data);
		return is;
	}
	
}
