package me.toaster.mcamusementcore.show;

import java.util.LinkedList;
import java.util.Queue;

public class Show {

	private String SHOW_NAME;
	private Queue<ShowCommand> commands = new LinkedList<>();
	
	public Show(String showName) {
		this.SHOW_NAME = showName;
		ShowManager.addShow(this);
	}
	
	public void addCommand(ShowCommand command) {
		this.commands.add(command);
	}
	
	public Queue<ShowCommand> getCommands(){
		return this.commands;
	}
	
	public String getShowName() {
		return this.SHOW_NAME;
	}
	
	public void start() {
		ShowScheduler sch = new ShowScheduler(this);
		sch.start();
	}
	
}
