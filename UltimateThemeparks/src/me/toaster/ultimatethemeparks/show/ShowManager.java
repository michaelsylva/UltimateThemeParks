package me.toaster.ultimatethemeparks.show;

import java.util.ArrayList;

/**
 * Manages all the Show objects, and the running show objects.
 * @author Michael Sylva
 */

public class ShowManager {

	/** Show Objects */
	public static ArrayList<Show> shows = new ArrayList<>();
	
	/** Running show Objects */
	public static ArrayList<ShowScheduler> runningShows = new ArrayList<>();
	
	public static void addShow(Show s) {
		shows.add(s);
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
