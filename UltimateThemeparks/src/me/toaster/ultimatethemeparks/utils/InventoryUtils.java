package me.toaster.ultimatethemeparks.utils;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtils {

	private static HashMap<String, ItemStack[]> mySavedItems = new HashMap<String, ItemStack[]>();
	 
	public static void saveInventory(Player player)
	{
	    mySavedItems.put(player.getName(), copyInventory(player.getInventory()));
	}
	 
	public static boolean containsPlayer(Player p) {
		return mySavedItems.containsKey(p);
	}
	
	/**
	* This removes the saved inventory from our HashMap, and restores it to the player if it existed.
	* @return true iff success
	*/
	public static boolean restoreInventory(Player player)
	{
	    ItemStack[] savedInventory = mySavedItems.remove(player.getName());
	    if(savedInventory == null)
	        return false;
	    restoreInventory(player, savedInventory);
	    return true;
	}
	 
	private static ItemStack[] copyInventory(Inventory inv)
	{
	    ItemStack[] original = inv.getContents();
	    ItemStack[] copy = new ItemStack[original.length];
	    for(int i = 0; i < original.length; ++i)
	        if(original != null)
	            copy[i] = new ItemStack(original[i]);
	    return copy;
	}
	 
	private static void restoreInventory(Player p, ItemStack[] inventory)
	{
	    p.getInventory().setContents(inventory);
	}
	
}
