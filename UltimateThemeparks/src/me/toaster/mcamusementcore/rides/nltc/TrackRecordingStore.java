package me.toaster.mcamusementcore.rides.nltc;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;

public class TrackRecordingStore {
	
	public static List<TrackRecording> recordings = new ArrayList<TrackRecording>();
	
	public static void add(TrackRecording tr){
		if(tr!=null){
			recordings.add(tr);
		}else{
			Bukkit.getLogger().log(Level.SEVERE, "Track is null [NLTC:TrackRecordingStore.java]");
		}
	}
	
	public static boolean isLoaded(String name){
		for(TrackRecording tr : recordings){
			if(tr.isLoaded && tr.trackName.equals(name)) return true;
		}
		return false;
	}
	
	public static int getAmountOfTracks(){
		return recordings.size();
	}
	
	public static void clear(){
		recordings.clear();
	}
}
