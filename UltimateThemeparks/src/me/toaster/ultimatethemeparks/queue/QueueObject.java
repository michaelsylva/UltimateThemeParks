package me.toaster.ultimatethemeparks.queue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.toaster.ultimatethemeparks.UTPCore;
import me.toaster.ultimatethemeparks.rides.Ride;
import me.toaster.ultimatethemeparks.scheduler.ActionBarMessage;
import me.toaster.ultimatethemeparks.scheduler.ActionBarScheduler;
import me.toaster.ultimatethemeparks.utils.CompareUtils;
import me.toaster.ultimatethemeparks.utils.ParseUtils;
import me.toaster.ultimatethemeparks.utils.PlayerUtils;
import me.toaster.ultimatethemeparks.utils.UTPSerialize;

public class QueueObject extends BukkitRunnable implements UTPSerialize{

	public static ArrayList<QueueObject> queues = new ArrayList<QueueObject>();

	private Ride ride;
	private String rideName;
	Location signLocation;
	int seconds;
	int playersIn;
	int playersMax;
	private Queue<Player> players = new LinkedList<Player>();
	private long lastActivated = -1;
	private int secondsCount = -1;

	public QueueObject(Ride ride, Location signLocation, int seconds, int playersIn, int playersMax) {
		this.ride = ride;
		this.signLocation = signLocation;
		this.seconds = seconds;
		this.playersIn = playersIn;
		this.playersMax = playersMax;
		this.secondsCount=seconds;
		this.rideName = ride.getRideName();

		/** If this is a valid queue, start it and add it, otherwise it will be deleted */
		if(validateQueue()) {
			queues.add(this);
			startScheduler();
		}
	}

	public boolean validateQueue() {
		if(this!=null) {
			if(this.signLocation.getBlock().getType()==Material.WALL_SIGN || 
					this.signLocation.getBlock().getType()==Material.SIGN_POST) {
				if(this.ride!=null) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean removeQueue() {
		this.cancel();
		if(this.getQueueFile().exists()) {
			this.getQueueFile().delete();
		}
		return queues.remove(this);
	}

	public static QueueObject getQueueAtLocation(Block blk) {
		for(QueueObject qo : queues) {
			if(CompareUtils.isBlockLocationSame(qo.getSignLocation(), blk.getLocation())) {
				return qo;
			}
		}
		return null;
	}

	public int playersInQueue() {
		return players.size();
	}

	public boolean hasNext() {
		try {
			players.element();
		}catch(NoSuchElementException e) {
			return false;
		}
		return true;
	}

	public Player getNext() {
		return players.remove();
	}

	public void activateQueue() {
		int i = 0;
		while(hasNext() && i<playersIn) {
			Player p = getNext();
			this.ride.teleportPlayer(p);
			i++;
		}

		this.ride.start();
		this.lastActivated = System.currentTimeMillis();
	}

	public boolean canActivate() {
		long diff = System.currentTimeMillis() - this.lastActivated;
		diff/=1000;
		if(diff>this.seconds) {
			return true;
		}else {
			return false;
		}
	}

	public long timeToActivate() {
		long diff = System.currentTimeMillis() - this.lastActivated;
		return this.seconds - (diff/1000);
	}

	public boolean isInQueue(Player p) {
		if(players.size()>0) {
			for(Player player : players) {
				if(player.equals(p)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean canAdd() {
		if(this.playersInQueue()<playersMax) {
			return true;
		}
		return false;
	}

	public void removeFromQueue(Player p) {
		if(isInQueue(p)) {
			players.remove(p);
		}
		p.sendMessage(ChatColor.AQUA+"You have left the Queue for "+ride.getRideName());
	}

	public void addPlayer(Player p) {
		players.add(p);
		p.sendMessage(ChatColor.AQUA+"You have joined the Queue for " + ride.getRideName());
		if(canActivate() && secondsCount>10) {
			this.secondsCount=10;
		}
	}

	public Ride getRide() {
		return ride;
	}

	public Location getSignLocation() {
		return signLocation;
	}

	public void setSignLocation(Location signLocation) {
		this.signLocation = signLocation;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public int getPlayersIn() {
		return playersIn;
	}

	public void setPlayersIn(int playersIn) {
		this.playersIn = playersIn;
	}

	public int getPlayersMax() {
		return playersMax;
	}

	public void setPlayersMax(int playersMax) {
		this.playersMax = playersMax;
	}

	public void broadcastToQueue(String message) {
		if(players.size()>0) {
			for(Player p : players) {
				ActionBarMessage msg = new ActionBarMessage(message, 4, 20,"queue");
				ActionBarScheduler.setActionBar(p, msg);
			}
		}
	}

	public static QueueObject getQueue(String rideName) {
		for(QueueObject queue : queues) {
			if(queue.getRide().getRideName().equalsIgnoreCase(rideName)) {
				return queue;
			}
		}
		return null;
	}

	public void startScheduler() {
		this.runTaskTimer(UTPCore.getProvidingPlugin(UTPCore.class), 20L, 20L);
	}
	
	public void updateSign() {
		Location l = this.signLocation;
		Block blk = l.getBlock();
		if(blk.getType()==Material.SIGN_POST || blk.getType()==Material.WALL_SIGN) {
			Sign s = (Sign) blk.getState();
			if(s!=null) {
				s.setLine(2, ChatColor.BOLD+"Wait: "+this.secondsCount+"s");
				s.update();
			}
		}
	}

	@Override
	public void run() {
		if(this.playersInQueue()==0 && canActivate()) {
			secondsCount=10;
		}
		if(this.ride!=null) {
			updateSign();
			if(secondsCount>0) {
				broadcastToQueue(ChatColor.LIGHT_PURPLE+"["+ride.getRideName()+"] " + secondsCount + "s Wait time.");
				secondsCount--;
			}else {
				if(playersInQueue()!=0) {
					this.activateQueue();
				}
				this.secondsCount=this.seconds;
			}
		}
	}

	public File getQueueFile() {
		return new File(UTPCore.QUEUE_DIR.getPath()+File.separator+this.rideName+"-queue.utp");
	}

	@Override
	public String toString() {
		return this.rideName+"@"+ParseUtils.locationToString(this.signLocation)+"@"+this.playersIn+"@"+this.playersMax+"@"+this.seconds;
	}

	@Override
	public boolean save() {
		File toWrite = getQueueFile();
		try {
			BufferedWriter writer = new BufferedWriter(new PrintWriter(toWrite));
			writer.write(this.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static Object load(File f) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return null;
		}


		try {
			String s = reader.readLine();
			String[] delim = s.split("@");
			if(delim.length>0) {
				String rideName = delim[0];
				Location l = ParseUtils.stringToLocation(delim[1]);
				int playersIn = Integer.parseInt(delim[2]);
				int playersMax = Integer.parseInt(delim[3]);
				int seconds = Integer.parseInt(delim[4]);

				QueueObject qo = new QueueObject(Ride.getRideByName(rideName), l, seconds, playersIn, playersMax);
				reader.close();
				return qo;

			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}





}
