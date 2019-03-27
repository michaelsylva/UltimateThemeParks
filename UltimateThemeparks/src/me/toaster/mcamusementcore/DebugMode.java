package me.toaster.mcamusementcore;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class DebugMode {

	private static ArrayList<Player> inDebugMode = new ArrayList<>();

	public static void addPlayer(Player p) {
		inDebugMode.add(p);
	}

	public static void removePlayer(Player p) {
		if(inDebugMode.contains(p)) {
			inDebugMode.remove(p);
		}
	}
	
	public static void toggleDebug(Player p) {
		if(inDebugMode.contains(p)) {
			removePlayer(p);
		}else {
			addPlayer(p);
		}
	}
	
	public static void sendDebugInfo(String msg) {
		for(Player p : inDebugMode) {
			p.sendMessage(msg);
		}
	}
	
	public static boolean statusPlayer(Player p) {
		return inDebugMode.contains(p);
	}

}
