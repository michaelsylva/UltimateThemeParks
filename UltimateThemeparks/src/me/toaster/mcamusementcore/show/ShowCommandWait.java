package me.toaster.mcamusementcore.show;

public class ShowCommandWait extends ShowCommand{

	int TICKS = 0;
	Integer wait_ticks;
	
	public ShowCommandWait(String[] data) {
		super(ShowCommandType.WAIT, data);
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
	
	public void reset() {
		this.TICKS = 0;
	}

	@Override
	public boolean parse() {
		try {
			this.wait_ticks = Integer.parseInt(this.getData()[1]);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean hasParsed() {
		return this.wait_ticks!=null;
	}

}
