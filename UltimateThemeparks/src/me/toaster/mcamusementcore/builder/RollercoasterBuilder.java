package me.toaster.mcamusementcore.builder;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RollercoasterBuilder extends MCABuilder{

	Player p;
	
	public RollercoasterBuilder(Player p) {
		super(p,"Rollercoaster");
		this.p = p;
		
		questions.add(
				new Question()
				.setQuestion(ChatColor.GOLD+"How would you like to create this rollercoaster?")
				.setValidAnswers("auto","manual")
				.setDisplayOptions(true));
		
		questions.add(
				new Question()
				.setQuestion(ChatColor.GOLD+"Hi there")
				.setValidAnswers("test1")
				.setDisplayOptions(false));
	}
	
	@Override
	public void build() {
		startBuilding(p, this);
		ask();
	}
	
	@Override
	public void ask() {
		if(questions.size()>0) {
			Question q = questions.element();
			q.sendQuestion(p);
			isWaiting=true;
		}else {
			//TODO after completing
		}
	}
	
	@Override
	public void answer(String message) {
		if(isValidAnswer(message)) {
			answers.add(message);
			questions.poll();
			isWaiting=false;
			if(questions.size()>0) {
				ask();
			}
		}else {
			p.sendMessage(ChatColor.RED+"Invalid answer!");
			ask();
		}
	}
	
	
}