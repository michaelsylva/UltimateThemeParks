package me.toaster.mcamusementcore.show;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.toaster.mcamusementcore.scheduler.ActionBarMessage;
import me.toaster.mcamusementcore.scheduler.ActionBarScheduler;

/**
 * Manages all the Show objects, and the running show objects.
 * @author Michael Sylva
 */

public class ShowManager {

	/** Show Objects */
	public static ArrayList<Show> shows = new ArrayList<>();
	
	/** Players editting shows */
	public static HashMap<Player,Show> editting = new HashMap<>();
	
	/** Running show Objects */
	public static ArrayList<ShowScheduler> runningShows = new ArrayList<>();
	
	public static void addShow(Show s) {
		shows.add(s);
	}
	
	public static void startEditting(Player p, Show s) {
		editting.put(p, s);
		ActionBarMessage message = new ActionBarMessage(ChatColor.GOLD+"You are editting the show "+ChatColor.BOLD+s.getShowName(), 6, -1, "show");
		ActionBarScheduler.setActionBar(p, message);
		p.sendMessage(ChatColor.GREEN+"You have began editting the show " + ChatColor.BOLD+s.getShowName());
	}
	
	public static void makeCommand(Player p, String message) {
		if(isEditting(p)) {
			Show s = getEditting(p);
			String[] args = message.split(" ");
			if(args[0].equalsIgnoreCase("firework")) {
				ShowCommandFirework firework = new ShowCommandFirework(p.getLocation(),args);
				boolean b = firework.parse();
				if(!b) {
					p.sendMessage(ChatColor.RED+"Could not add command. Invalid format...");
				}else {
					s.addCommand(firework);
					p.sendMessage(ChatColor.GREEN+"Added!");
				}
			}else if(args[0].equalsIgnoreCase("fountain")) {
				
			}else {
				p.sendMessage(ChatColor.RED+"Unknown command type...");
			}
		}
	}
	
	public static Show getEditting(Player p) {
		return editting.get(p);
	}
	
	public static void startEditting(Player p, String showName) {
		if(doesShowExist(showName)) {
			startEditting(p, getShowByName(showName));
		}else {
			startEditting(p, new Show(showName));
		}
		
	}
	
	public static void stopEditting(Player p) {
		editting.remove(p);
		if(ActionBarScheduler.getCurrentSource(p).equals("show")) {
			ActionBarScheduler.clearActionBar(p);
		}
		p.sendMessage(ChatColor.RED+"You have stopped editting the show");
	}
	
	public static boolean isEditting(Player p) {
		return editting.containsKey(p);
	}
	
	public static void addRunningShow(ShowScheduler s) {
		runningShows.add(s);
	}
	
	public static void remRunningShow(ShowScheduler s) {
		runningShows.remove(s);
	}
	
	public static boolean doesShowExist(String name) {
		return getShowByName(name)!=null;
	}
	
	public static boolean startShow(String name) {
		Show s = getShowByName(name);
		if(s!=null) {
			s.start();
			return true;
		}
		return false;
	}
	
	public static boolean stopShow(String name) {
		return false;
	}
	
	public static Show getShowByName(String name) {
		for(Show s : shows) {
			if(s.getShowName().equalsIgnoreCase(name)) {
				return s;
			}
		}
		return null;
	}
	
}
