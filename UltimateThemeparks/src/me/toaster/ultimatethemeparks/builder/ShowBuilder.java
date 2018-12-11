package me.toaster.ultimatethemeparks.builder;

import org.bukkit.entity.Player;

public class ShowBuilder extends UTPBuilder{
	
	Player p;
	
	public ShowBuilder(Player p) {
		super(p,"Show");
		this.p = p;
		
		//TODO below...
		//Ask show name
		//Ask show type
		//Ask ...
		//this.questions.add(arg0)
	}
	

	@Override
	public void ask() {}

	@Override
	public void answer(String message) {
		System.out.println("answer");
	}


	@Override
	public void build() {
		startBuilding(p, this);
		isWaiting=true;
	}
	
	
}
