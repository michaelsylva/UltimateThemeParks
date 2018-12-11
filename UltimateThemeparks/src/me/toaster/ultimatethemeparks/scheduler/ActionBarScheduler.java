package me.toaster.ultimatethemeparks.scheduler;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.toaster.ultimatethemeparks.utils.PlayerUtils;

public class ActionBarScheduler extends BukkitRunnable{

	public static HashMap<Player,ActionBarMessage> message = new HashMap<>();

	public final static int DELAY = 20;

	@Override
	public void run() {
		if(message.size()>0) {
			for(Player p : message.keySet()) {
				ActionBarMessage msg = message.get(p);
				if(msg.count<msg.timeTicks || msg.timeTicks==-1) {
					PlayerUtils.sendActionBar(p, msg.getMessage());
					msg.incrementCount(ActionBarScheduler.DELAY);
				}else {
					clearActionBar(p);
				}

			}
		}
	}

	public static void clearActionBar(Player p) {
		message.remove(p);
	}
	
	public static String getCurrentSource(Player p) {
		if(message.containsKey(p)) {
			ActionBarMessage current = message.get(p);
			return current.source;
		}
		return "";
	}

	public static void setActionBar(Player p, ActionBarMessage msg) {
		if(message.containsKey(p)) {
			//Already has an actionbar
			ActionBarMessage current = message.get(p);
			if(current.getPriority()<=msg.getPriority() || current.source.equals(msg.source)) {
				message.put(p, msg);
			}
		}else {
			//doesnt have one...
			message.put(p, msg);
		}
	}

}
