package me.toaster.mcamusementcore.scheduler;

public class ActionBarMessage {
	String message = "";
	int priority = 0;
	int timeTicks = 5; //-1 for infinite
	int count = 0;
	String source = "";
	
	public ActionBarMessage(String message, int priority, int timeTicks, String source) {
		this.message = message;
		this.priority = priority;
		this.timeTicks = timeTicks;
		this.count = 0;
		this.source=source;
	}
	
	public void incrementCount(int amount) {
		this.count+=amount;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public int getPriority() {
		return this.priority;
	}
}
