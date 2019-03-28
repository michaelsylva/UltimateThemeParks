package me.toaster.mcamusementcore.show;

public class ShowCommandWait extends ShowCommand{

	ShowCommandType type;
	String[] data;
	int TICKS = 0;
	int wait_ticks;
	
	public ShowCommandWait(String[] data) {
		super(ShowCommandType.WAIT, data);
		this.wait_ticks = Integer.parseInt(data[0]);
	}

	@Override
	public void execute() {
		//Unused
	}
	
	public void increase() {
		TICKS++;
	}
	
	public boolean isDone() {
		if(TICKS>wait_ticks) {
			return true;
		}
		return false;
	}

	@Override
	public boolean parse() {
		// TODO Auto-generated method stub
		return false;
	}

}
