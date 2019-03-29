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