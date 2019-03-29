package me.toaster.mcamusementcore.rides.nltc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.EulerAngle;

import me.toaster.mcamusementcore.MCACore;

public class TrackRecording {

	public String trackName;
	public boolean isLoaded;
	
	static class RecordingStatement{
		String line;
		
		double x, y, z;
		
		public RecordingStatement(String ln){
			this.line=ln;
		}
		
		//TODO
		public Location getLocation() {
			return null;
			
		}
		
		//TODO 
		public EulerAngle getRotation() {
			return null;
		}
		
	}
	
	public List<RecordingStatement> statements = new ArrayList<RecordingStatement>();
	public String initialLine;
	
	/* Constructor with just name */
	public TrackRecording(String name){
		this.trackName = name;
		this.isLoaded = loadTrack();
		TrackRecordingStore.add(this);
	}
	
	/* Constructor with file as name initializer */
	public TrackRecording(File f){
		this.trackName = f.getName().replaceAll(".txt", "");
		this.isLoaded = loadTrack();
		TrackRecordingStore.add(this);
	}
	
	/* Returns based on if the file exists
	 * Will return false if file does not exist.
	 */
	public boolean loadTrack(){
		final File f = getFile(trackName);
		if(!f.exists()) return false;
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		try {
			fileReader = new FileReader(f);
			bufferedReader = new BufferedReader(fileReader);
			
			//Read lines
			int count = 0;
			String currLine;
			while((currLine = bufferedReader.readLine()) !=null){
				if(count==0) initialLine = currLine;
				else{
					RecordingStatement rStatement = new RecordingStatement(currLine);
					addStatement(rStatement);
				}
				count++;
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace(); //File not found
			Bukkit.getLogger().log(Level.SEVERE, "File not found when trying to load track (" + trackName + ") at [NLTC:TrackRecording.java]");
			return false;
		} catch (IOException e) {
			e.printStackTrace(); //IO Exception
			return false;
		} finally {
			try{
				if(fileReader!=null)fileReader.close();
				if(bufferedReader!=null)bufferedReader.close();
			}catch(IOException e){
				e.printStackTrace(); //IO Exception
				return false;
			}
		}
		
		return true;
	}
	
	public void addStatement(RecordingStatement rs){
		statements.add(rs);
	}
	
	public File getFile(String fileName){
		return new File(MCACore.MCA_CORE.getDataFolder()+File.separator+"tracks",fileName+".txt");
	}
	
	public String getStatement(int i){
		return statements.get(i).line;
	}
	
}


