package me.toaster.ultimatethemeparks.balloons;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.toaster.ultimatethemeparks.balloons.Balloon.ActiveBalloon;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_12_R1.EnumMoveType;

public class BalloonManager extends BukkitRunnable{

	/** All available balloons */
	private static ArrayList<Balloon> balloons = new ArrayList<Balloon>();

	/** All balloons in the air */
	private static HashMap<Player,ActiveBalloon> activeBalloons = new HashMap<Player,ActiveBalloon>();

	/** Balloon constants */
	final static double SPIN_SPEED = 0.025;
	final static double HEIGHT = 2;

	public static Balloon getBalloonFromItem(ItemStack is) {
		if(is!=null) {
			if(is.getType()==Material.SKULL_ITEM) {
				if(is.hasItemMeta()) {
					ItemMeta meta = is.getItemMeta();
					String itemName = ChatColor.stripColor(meta.getDisplayName());
					if(itemName.endsWith("balloon")) {
						String[] delim = itemName.split(" ");
						String name = delim[0];
						return BalloonManager.getBalloonByName(name);
					}
				}
			}
		}
		return null;
	}

	public static Balloon getBalloonByName(String name) {
		for(Balloon b : balloons) {
			if(b.name.equals(name)) {
				return b;
			}
		}
		return null;
	}
	
	public static void clearAllBalloons() {
		if(activeBalloons.keySet().size()>0) {
			for(Player p : activeBalloons.keySet()) {
				ActiveBalloon balloon = activeBalloons.get(p);
				balloon.despawn();
			}
		}
	}
	
	public static HashMap<Player,ActiveBalloon> getActiveBalloons(){
		return activeBalloons;
	}

	public static boolean hasActiveBalloon(Player p) {
		return activeBalloons.containsKey(p);
	}
	
	public static ActiveBalloon getActiveBalloon(Player p) {
		return activeBalloons.get(p);
	}
	
	public static ArrayList<Balloon> getBalloons(){
		return balloons;
	}

	public static void loadBalloons() {
		addBalloon(new Balloon("toast", "Mr_toaster111"));
	}

	public static void saveBalloons() {

	}

	public static void addBalloon(Balloon b) {
		System.out.println("added balloon : " + b);
		balloons.add(b);
	}

	public static void spawnedBalloon(Player p, ActiveBalloon b) {
		activeBalloons.put(p, b);
	}

	public static void despawnedBalloon(Player p) {
		activeBalloons.remove(p);
	}

	@Override
	public void run() {
		if(activeBalloons!=null) {
			if(activeBalloons.size()>0) {
				for(Player p : activeBalloons.keySet()) {
					ActiveBalloon balloon = activeBalloons.get(p);
					Vector to = p.getLocation().add(0, BalloonManager.HEIGHT, 0).toVector();
					Vector from = balloon.getLocation().toVector();
					Vector move = to.subtract(from);
					
					balloon.twistHead();
					balloon.balloon.move(EnumMoveType.SELF, move.getX(), move.getY(), move.getZ());
					balloon.joinHolderAndBalloon();
				}
			}
		}
	}
}
