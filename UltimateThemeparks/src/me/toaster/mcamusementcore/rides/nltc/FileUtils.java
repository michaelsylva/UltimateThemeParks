package me.toaster.mcamusementcore.rides.nltc;

import java.io.File;
import java.io.IOException;

public class FileUtils {

	public static void initFile(File f){
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void initDirectory(File f){
		if(!f.exists()){
			f.mkdir();
		}
	}
	
	public static File getTracksFile(File dataFolder){
		return new File(dataFolder.getPath()+File.separator+"tracks");
	}
	
	public static File getLogsFile(File dataFolder){
		return new File(dataFolder.getPath()+File.separator+"logs");
	}

	
}
