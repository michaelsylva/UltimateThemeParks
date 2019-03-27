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
import me.toaster.mcamusementcore.events.CEntityEvents;
import me.toaster.mcamusementcore.events.MCAPlayerEvents;
import me.toaster.mcamusementcore.events.WorldEvents;
import me.toaster.mcamusementcore.flatrides.FRFrisbee;
import me.toaster.mcamusementcore.items.ItemListener;
import me.toaster.mcamusementcore.queue.QueueListener;
import me.toaster.mcamusementcore.queue.QueueObject;
import me.toaster.mcamusementcore.rides.Ride;
import me.toaster.mcamusementcore.rides.RollercoasterAuto;
import me.toaster.mcamusementcore.rides.RollercoasterAuto.BlockDirection;
import me.toaster.mcamusementcore.scheduler.ActionBarScheduler;
import me.toaster.mcamusementcore.scheduler.WorldScheduler;
import me.toaster.mcamusementcore.utils.InventoryUtils;
import me.toaster.mcamusementcore.utils.nms.CustomEntityRegistry;
import me.toaster.mcamusementcore.utils.nms.NMSUtils;

/**
 * Main class for UltimateThemeParks...
 * @author Michael Sylva
 *
 */
public class MCACore extends JavaPlugin{
	
	/**
	 * Features List:
	 * 
	 * Queue System
	 * Create queues for established rides
	 * The Queue sign will sit at 10 seconds if no one is in the queue and the queue wasnt activated <queue_interval> before now. This
	 * fixes the issue of people getting in a queue and having to wait over a minute when they are
	 * the only one waiting.
	 * 
	 * Balloon System
	 * Able to manage balloons creation/deletion
	 * The balloons are saved as [Items] which can be used on buy/free signs as type balloon.
	 * Balloons will fly away if you press "q" (letting go of the balloon)
	 * 
	 * Show System
	 * Not much functionality yet
	 * 
	 * Ride System
	 * Can create rides either Rollercoaster(Manual or Automatic) or a flatride. Manual rollercoaster creation will
	 * accept input from an NLTC file and create the ride with specified properties you give.
	 * Every ride is created and saved as a Ride Object. The Objects are loaded and unloaded when the plugin is loaded/unloaded.
	 * This allows rides and trains to always be spawned as long as they are in the render distance of a player. To give the effect
	 * that the train is waiting in the station for a passenger. Administrators have the ability to access a ride operator panel
	 * that will allow them to check the status of rides and the trains of the rides. High power Administrators will be able to manage
	 * the spawning and de-spawning of rides in emergencies
	 * 
	 * Ride Break Detection
	 * Each ride is monitored by the plugin with knowledge of where each train is and where it should be, who is riding the plugin, and other
	 * specifics about each ride. It can accurately acknowledge a broken ride and attempt to fix it or report it.
	 * 
	 * Flatride Framework
	 * The functions and the math all created, you can create a flatride just like a normal ride.
	 * 
	 * ActionBar prioritizing and easy usage.
	 * Custom ArmorStand Entity for models
	 * Custom Bat Entity for additional functionality such as a leash
	 * 
	 * Items Buy/Sell/Manage functionality
	 * Dynamic Permissions
	 * 
	 */
	
	/**
	 * Notes...
	 * Don't always have the carts spawned... remove them and add them when necessary...
	 * That will reduce every issue of un tracking / activation problems...
	 * 
	 */
	
	/*
	 * The Entity object is not persistent, when you logout the chunk might get unloaded and the entity object will be released. When you log back in a new entity object will be created based on the saved state of the entity.
If you want to keep track of entities across chunk reloads and server restarts you need to use the UUID of the entity.
	 */
	
	
	//TODO ADD TRASH TO THROW ITEMS TO
	
	public static File QUEUE_DIR;
	
	public static JavaPlugin MCA_CORE;
	
	public final static String serverName = "McAmusement";
	public final static String mainCmd = "mca";
	public final static String serverMotto = "Live the magic in Minecraft!";
	
	public static ArrayList<BukkitTask> tasks = new ArrayList<BukkitTask>();
	
	public void onEnable() {
		
		MCA_CORE = this;
		
		this.makeDirectories();
		this.initCustomEntities();
		this.initCommands();
		this.initEvents();
		this.initRides();
		this.initBalloons();
		
		this.spawnRides();
		this.loadData();
		
		this.initSchedulers();
		
		
		Bukkit.broadcastMessage(this.getDescription().getFullName()+" loaded successfully");
	}
	
	public void onDisable() {
		
		/*
		 * TODO CERTAIN CENTITYS WILL NOT CLEAR...
		 */
		
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
		Bukkit.getPluginManager().registerEvents(new WorldEvents(), this);
		Bukkit.getPluginManager().registerEvents(new CEntityEvents(), this);
	}
	
	public void initSchedulers() {
		BalloonManager balloonManager = new BalloonManager();
		balloonManager.runTaskTimer(this, 1, 1);
		
		WorldScheduler ws = new WorldScheduler();
		ws.runTaskTimer(this, 5, 5);
		
		ActionBarScheduler actionBar = new ActionBarScheduler();
		int delay = ActionBarScheduler.DELAY;
		actionBar.runTaskTimer(this, delay, delay);
	}
	
	public void initCommands() {
		getCommand("mca").setExecutor(new MCACommand());
	}
	
	public void initRides() {
		if(isTestServer()) {
			Ride.rides.add(new FRFrisbee());
			//Ride.rides.add(new RollercoasterAuto(4, Material.LAPIS_BLOCK ,BlockDirection.WEST));
		}
	}
	
	public void initBalloons() {
		BalloonManager.loadBalloons();
	}
	
	public void initCustomEntities() {
		//NMSUtils.registerEntity(NMSUtils.Type.BAT, CEntityBat.class, false);
		/*NMSUtils.registerCustomEntity(CEntityBat.class, CEntityBat::new, "cbat");
		NMSUtils.registerCustomEntity(CEntityArmorstand.class, CEntityArmorstand::new, "carmorstand");
		NMSUtils.registerCustomEntity(CEntityMinecart.class, CEntityMinecart::new, "cminecart");
		*/
		//
		CustomEntityRegistry.injectNewEntityTypes("cbat", "bat", CEntityBat.class, CEntityBat::new);
		CustomEntityRegistry.injectNewEntityTypes("cminecart", "minecart", CEntityMinecart.class, CEntityMinecart::new);
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
