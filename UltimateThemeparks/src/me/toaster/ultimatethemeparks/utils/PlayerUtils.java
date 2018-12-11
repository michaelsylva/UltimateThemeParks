package me.toaster.ultimatethemeparks.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class PlayerUtils {

	public static void sendActionBar(Player p, String msg) {
		p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(msg));
    }
	
	public static void giveItem(Player p, ItemStack is) {
		p.getInventory().addItem(is);
	}
}
