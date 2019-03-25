package me.toaster.mcamusementcore;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import me.toaster.mcamusementcore.balloons.BalloonListener;
import me.toaster.mcamusementcore.balloons.BalloonManager;
import me.toaster.mcamusementcore.builder.BuilderListener;
import me.toaster.mcamusementcore.commands.MCACommand;
import me.toaster.mcamusementcore.entities.CEntityArmorstand;
import me.toaster.mcamusementcore.entities.CEntityBat;
import me.toaster.mcamusementcore.entities.CEntityManager;
import me.toaster.mcamusementcore.entities.CEntityMinecart;
import me.toaster.mcamusementcore.events.MCAPlayerEvents;
import me.toaster.mcamusementcore.flatrides.FRFrisbee;
import me.toaster.mcamusementcore.items.ItemListener;
import me.toaster.mcamusementcore.queue.QueueListener;
import me.toaster.mcamusementcore.queue.QueueObject;
import me.toaster.mcamusementcore.rides.Ride;
import me.toaster.mcamusementcore.rides.RollercoasterAuto;
import me.toaster.mcamusementcore.rides.RollercoasterAuto.BlockDirection;
import me.toaster.mcamusementcore.scheduler.ActionBarScheduler;
import me.toaster.mcamusementcore.utils.InventoryUtils;
import me.toaster.mcamusementcore.utils.nms.NMSUtils;

/**
 * Main class for UltimateThemeParks...
 * @author Michael Sylva
 *
 */
public class MCACore extends JavaPlugin{
	
	
	
	//TODO ADD TRASH TO THROW ITEMS TO
	
	public static File QUEUE_DIR;
	
	public static JavaPlugin UTP_CORE;
	
	public final static String serverName = "McAmusement";
	public final static String mainCmd = "mca";
	public final static String serverMotto = "Live the magic in Minecraft!";
	
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
		CEntityManager.twoFactorClear();
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
		Bukkit.getPluginManager().registerEvents(new MCAPlayerEvents(), this);
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
		getCommand(MCACore.mainCmd).setExecutor(new MCACommand());
	}
	
	public void initRides() {
		Ride.rides.add(new FRFrisbee());
		Ride.rides.add(new RollercoasterAuto(4, Material.LAPIS_BLOCK ,BlockDirection.WEST));
	}
	
	public void initBalloons() {
		BalloonManager.loadBalloons();
	}
	
	public void initCustomEntities() {
		//NMSUtils.registerEntity(NMSUtils.Type.BAT, CEntityBat.class, false);
		NMSUtils.registerCustomEntity(CEntityBat.class, CEntityBat::new, "cbat");
		NMSUtils.registerCustomEntity(CEntityArmorstand.class, CEntityArmorstand::new, "carmorstand");
		NMSUtils.registerCustomEntity(CEntityMinecart.class, CEntityMinecart::new, "cminecart");
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
	
	/*
	 * TODO A constant schedule that checks if rides that are supposed to be spawned and all their trains are still spawned. If not
	 * we need a way to handle this...
	 */
}
