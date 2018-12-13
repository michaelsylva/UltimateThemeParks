package me.toaster.ultimatethemeparks.show;

import org.bukkit.Location;

public class ShowCommand {

	ShowCommandType type;
	Location l;
	String data;
	
	public ShowCommand(ShowCommandType type, Location l, String data) {
		this.l = l;
		this.type = type;
		this.data = data;
	}
	
	public boolean canParse() {
		return false;
	}
	
	public void execute() {
		
	}
	
}
