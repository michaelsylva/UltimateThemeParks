package me.toaster.mcamusementcore.builder;

import org.bukkit.entity.Player;

public class ShowBuilder extends MCABuilder{

	public ShowBuilder(Player p) {
		super(p, "Show");
	}

	@Override
	public void ask() {}

	@Override
	public void answer(String message) {
		//each message coming in is a show command...
	}

	@Override
	public void build() {
		startBuilding(p, this);
		this.isWaiting=true;
	}

}
