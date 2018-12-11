package me.toaster.ultimatethemeparks;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import me.toaster.ultimatethemeparks.balloons.Balloon.ActiveBalloon;
import me.toaster.ultimatethemeparks.builder.BuilderListener;
import me.toaster.ultimatethemeparks.balloons.BalloonListener;
import me.toaster.ultimatethemeparks.balloons.BalloonManager;
import me.toaster.ultimatethemeparks.commands.UTPCommand;
import me.toaster.ultimatethemeparks.entities.CEntityBat;
import me.toaster.ultimatethemeparks.events.UTPPlayerJoin;
import me.toaster.ultimatethemeparks.flatrides.FRFrisbee;
import me.toaster.ultimatethemeparks.items.ItemListener;
import me.toaster.ultimatethemeparks.queue.QueueListener;
import me.toaster.ultimatethemeparks.queue.QueueObject;
import me.toaster.ultimatethemeparks.rides.Ride;
import me.toaster.ultimatethemeparks.rides.RollercoasterAuto;
import me.toaster.ultimatethemeparks.rides.RollercoasterAuto.BlockDirection;
import me.toaster.ultimatethemeparks.scheduler.ActionBarScheduler;
import me.toaster.ultimatethemeparks.utils.InventoryUtils;
import me.toaster.ultimatethemeparks.utils.NMSUtils;

public class UTPCore extends JavaPlugin{
	
	public static File QUEUE_DIR;
	
	public static JavaPlugin UTP_CORE;
	
	public static String serverName = "McAmusement";
	public static String serverMotto = "Live the magic in Minecraft!";
	
	public static ArrayList<BukkitTask> tasks = new ArrayList<BukkitTask>();
	
	public void onEnable() {
		this.makeDirectories();
		this.initCustomEntities();
		this.initCommands();
		this.initEvents();
		this.initRides();
		this.initBalloons();
		
		this.spawnRides();
		this.loadData();
		
		this.initSchedulers();
		
		UTP_CORE = this;
		
		Bukkit.broadcastMessage(this.getDescription().getFullName()+" loaded successfully");
	}
	
	public void onDisable() {
		this.clearUnwantedEntities();
		this.disableRides();
		this.fixAllInventories();
		this.saveData();
	}
	
	public static boolean isTestServer() {
		if(Bukkit.getServer().getIp().equals("")) {
			return true;
		}else {
			return false;
		}
	}
	
	public void fixAllInventories() {
		if(Bukkit.getServer().getOnlinePlayers().size()>0) {
			for(Player all : Bukkit.getOnlinePlayers()) {
				if(InventoryUtils.containsPlayer(all)) {
					InventoryUtils.restoreInventory(all);
				}
			}
		}
	}
	
	public void initEvents() {
		Bukkit.getPluginManager().registerEvents(new QueueListener(), this);
		Bukkit.getPluginManager().registerEvents(new BalloonListener(), this);
		Bukkit.getPluginManager().registerEvents(new BuilderListener(), this);
		Bukkit.getPluginManager().registerEvents(new UTPPlayerJoin(), this);
		Bukkit.getPluginManager().registerEvents(new ItemListener(), this);
	}
	
	public void initSchedulers() {
		BalloonManager balloonManager = new BalloonManager();
		balloonManager.runTaskTimer(this, 1, 1);
		
		ActionBarScheduler actionBar = new ActionBarScheduler();
		int delay = ActionBarScheduler.DELAY;
		actionBar.runTaskTimer(this, delay, delay);
	}
	
	public void initCommands() {
		getCommand("utp").setExecutor(new UTPCommand());
	}
	
	public void initRides() {
		Ride.rides.add(new FRFrisbee());
		Ride.rides.add(new RollercoasterAuto(4, Material.LAPIS_BLOCK ,BlockDirection.WEST));
	}
	
	public void initBalloons() {
		BalloonManager.loadBalloons();
	}
	
	public void initCustomEntities() {
		NMSUtils.registerEntity(NMSUtils.Type.BAT, CEntityBat.class, false);
	}
	
	public void spawnRides() {
		for(Ride r : Ride.rides) {
			r.spawn();
		}
	}
	
	public void clearUnwantedEntities() {
		for(Ride r : Ride.rides) {
			if(r.isSpawned()) {
				r.despawn();
			}
		}
		
		BalloonManager.clearAllBalloons();
	}
	
	public void disableRides() {
		for(Ride r : Ride.rides) {
			r.despawn();
		}
	}
	
	public void saveData() {
		//Queues
		for(QueueObject q : QueueObject.queues) {
			q.save();
		}
	}
	
	public void loadData() {
		//Queues
		for(File f : QUEUE_DIR.listFiles()) {
			if(f.getName().endsWith("utp")) {
				Object o = QueueObject.load(f);
				if(o!=null) {
					if(o instanceof QueueObject) {
						QueueObject qo = (QueueObject) o;
						Bukkit.getLogger().info("Loaded Queue for " + qo.getRide().getRideName());
					}
				}
			}
		}
		
		
	}
	
	public void makeDirectories() {
		
		if(!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}
		
		File queues = new File(getDataFolder().getPath()+File.separator+"queues");
		if(!rootFolderExists("queues")) {
			queues.mkdir();
		}
		QUEUE_DIR = queues;
	}
	
	public boolean rootFolderExists(String folderName) {
		return new File(getDataFolder().getPath()+File.separator+folderName).exists();
	}
}
