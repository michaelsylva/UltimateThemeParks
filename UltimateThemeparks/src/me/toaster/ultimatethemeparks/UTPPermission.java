package me.toaster.ultimatethemeparks;

public enum UTPPermission {

	/** Events */
	UTP_QUEUE_SIGNPLACE("utp.queue.signplace"),
	UTP_QUEUE_SIGNBREAK("utp.queue.signbreak"),
	
	UTP_ITEM_FREE_SIGNPLACE("utp.item.free.signplace"),
	UTP_ITEM_BUY_SIGNPLACE("utp.item.buy.signplace"),
	
	/** Commands */
	UTP_COMMANDS_RIDE("utp.commands.ride"),
	UTP_COMMANDS_BACKUP("utp.commands.backup"),
	
	/** General staff */
	UTP_STAFF("utp.staff");
	
	private String val;
	UTPPermission(String val) {
		this.val = val;
	}
	
	public String getValue() {
		return this.val;
	}
	
}
