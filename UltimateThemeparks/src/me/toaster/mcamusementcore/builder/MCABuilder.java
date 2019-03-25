package me.toaster.mcamusementcore.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.toaster.mcamusementcore.scheduler.ActionBarMessage;
import me.toaster.mcamusementcore.scheduler.ActionBarScheduler;
import me.toaster.mcamusementcore.utils.InventoryUtils;

public abstract class MCABuilder{

	public static HashMap<Player,MCABuilder> building = new HashMap<>();
	private String builderName = "Unknown...";
	public boolean lock = false;

	public Queue<Question> questions = new LinkedList<Question>();
	public ArrayList<String> answers = new ArrayList<>();
	Player p;
	public boolean isWaiting = false;

	public MCABuilder(Player p, String builderName) {
		this.p = p;
		this.builderName=builderName;
	}

	public void startBuilding(Player p, MCABuilder builder) {
		addBuilding(p, builder);
		p.sendMessage(ChatColor.GREEN+"You have entered the " + this.builderName + " builder");
	}

	public String getName() {
		return this.builderName;
	}

	public boolean isValidAnswer(String message) {
		Question q = questions.element();
		if(q.validAnswers!= null && q.validAnswers.length>0) {
			for(String s : q.validAnswers) {
				if(message.equalsIgnoreCase(s)) {
					return true;
				}
			}
		}else {
			return true;
		}
		return false;
	}

	public abstract void ask();

	public abstract void answer(String message);

	public abstract void build();

	/** Static methods to be accessed from anywhere 
	 * @return */

	public static boolean isBuilding(Player p) {
		if(building.containsKey(p)) {
			return true;
		}
		return false;
	}

	public static MCABuilder getBuilder(Player p) {
		return building.get(p);
	}

	public static void addBuilding(Player p, MCABuilder builder) {
		building.put(p, builder);
		ActionBarMessage msg = new ActionBarMessage(ChatColor.GREEN+"You are in the " + builder.getName() + " editor (use /utp builder exit to quit)", 5, -1,"builder");
		ActionBarScheduler.setActionBar(p, msg);
	}

	public static void removeBuilding(Player p) {
		building.remove(p);
		if(ActionBarScheduler.getCurrentSource(p).equals("builder")) {
			ActionBarScheduler.clearActionBar(p);
		}
	}

	public void saveInventory(Player p) {
		if(!InventoryUtils.containsPlayer(p)) {
			InventoryUtils.saveInventory(p);
		}else {
			InventoryUtils.restoreInventory(p);
			InventoryUtils.saveInventory(p);
		}
	}

	public class Question {
		public String question;
		public String[] validAnswers;
		boolean displayOptions=true;

		public Question setQuestion(String q) {
			this.question = q;
			return this;
		}

		public void sendQuestion(Player p) {
			p.sendMessage(ChatColor.GOLD+question);
			if(validAnswers!=null) {
				if(validAnswers.length>0 && displayOptions) {
					for(String valid : validAnswers) {
						p.sendMessage(ChatColor.GRAY+""+ChatColor.BOLD+StringUtils.capitalise(valid));
					}
				}
			}
		}

		public Question setDisplayOptions(boolean t) {
			this.displayOptions = t;
			return this;
		}

		public Question setValidAnswers(String... s) {
			this.validAnswers = s;
			return this;
		}
	}

}
