package me.toaster.ultimatethemeparks.builder;

import org.bukkit.entity.Player;

public class ShowBuilder extends UTPBuilder{

	public ShowBuilder(Player p) {
		super(p, "Show");
	}

	@Override
	public void ask() {}

	@Override
	public void answer(String message) {}

	@Override
	public void build() {
		startBuilding(p, this);
		this.isWaiting=true;
	}

}
