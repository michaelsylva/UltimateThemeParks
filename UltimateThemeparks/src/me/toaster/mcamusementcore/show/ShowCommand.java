package me.toaster.mcamusementcore.show;

public abstract class ShowCommand {

	private ShowCommandType type;
	private String data[];
	
	public ShowCommand(ShowCommandType type, String data[]) {
		this.type = type;
		this.data = data;
	}
	
	public ShowCommandType getType() {
		return this.type;
	}
	
	public String[] getData() {
		return this.data;
	}
	
	public abstract void execute();
	
	public abstract boolean parse();
	
}
