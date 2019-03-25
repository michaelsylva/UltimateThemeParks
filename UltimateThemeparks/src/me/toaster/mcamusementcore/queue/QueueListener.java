package me.toaster.mcamusementcore.queue;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import me.toaster.mcamusementcore.MCAPermission;
import me.toaster.mcamusementcore.rides.Ride;
import me.toaster.mcamusementcore.utils.ParseUtils;

/**
 * Queue sign listener
 * Placing a sign in the format:
 * [Queue]
 * {ride}
 * {time_seconds}
 * {players_in:max_players}
 * 
 * Creates a queue sign that can later be editted to do certain tasks, such as teleport players,
 * or start a ride...
 * 
 * @author Michael Sylva
 *
 */

public class QueueListener implements Listener{

	/**
	 * Event handler for placing a Queue sign
	 *
	 * Required Permission: mcamusement.queue.signplace
	 * @param e SignChangeEvent instance
	 */
	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		if(e.getPlayer().hasPermission(MCAPermission.UTP_QUEUE_SIGNPLACE.getValue())) {
			if(e.getLine(0) != null && e.getLine(1) !=null && e.getLine(2)!=null && e.getLine(3)!=null) {
				if(e.getLine(0).equalsIgnoreCase("[Queue]")) {
					String name = e.getLine(1);
					Ride r = Ride.getRideByName(name);
					if(QueueObject.getQueue(name)==null) {
						if(r!=null) {
							if(ParseUtils.isInt(e.getLine(2))) {
								int seconds = Integer.parseInt(e.getLine(2));
								if(seconds>0) {
									String[] split = e.getLine(3).split(":");
									if(split.length==2) {
										if(ParseUtils.isInt(split[0]) && ParseUtils.isInt(split[1])) {
											int playersIn = Integer.parseInt(split[0]);
											int playersMax = Integer.parseInt(split[1]);

											e.setLine(0, ChatColor.BLUE+"[QUEUE]");

											QueueObject object = new QueueObject(r, e.getBlock().getLocation(), seconds, playersIn, playersMax);
											e.getPlayer().sendMessage(ChatColor.GREEN + "Created Queue for " + name + " t:"+seconds+" pIn:"+playersIn+" pMax:"+playersMax);

										}else {
											e.getPlayer().sendMessage(ChatColor.RED + "Incorrect players format! Must be integers...");
										}
									}else {
										e.getPlayer().sendMessage(ChatColor.RED + "Incorrect players format");
									}
								}else {
									e.getPlayer().sendMessage(ChatColor.RED+"Incorrect time format");
								}
							}else {
								e.getPlayer().sendMessage(ChatColor.RED+"Incorrect time format");
							}
						}else {
							e.getPlayer().sendMessage(ChatColor.RED+"That ride doesnt exist!");
						}
					}else {
						e.getPlayer().sendMessage(ChatColor.RED+"There is already a queue for this ride!");
					}
				}
			}
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction()==Action.RIGHT_CLICK_BLOCK) {
			Block blk = e.getClickedBlock();
			//TODO fix legacy
			if(blk.getType()==Material.WALL_SIGN || blk.getType()==Material.LEGACY_SIGN_POST) {
				if(blk.getState() instanceof Sign) {
					Sign s = (Sign) blk.getState();
					if(s.getLine(0)!=null && s.getLine(1)!=null) {
						if(s.getLine(0).equalsIgnoreCase(ChatColor.BLUE+"[QUEUE]")) {
							String name = s.getLine(1);
							QueueObject queue = QueueObject.getQueue(name);
							if(queue!=null) {
								if(queue.isInQueue(e.getPlayer())) {
									queue.removeFromQueue(e.getPlayer());
								}else {
									if(queue.canAdd()) {
										queue.addPlayer(e.getPlayer());
									}else {
										e.getPlayer().sendMessage(ChatColor.RED+"This Queue is full!");
									}
								}
							}else {
								e.getPlayer().sendMessage(ChatColor.RED+"This queue has been deactivated. Contact a staff member");
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Block blk = e.getBlock();
		QueueObject qo = QueueObject.getQueueAtLocation(blk);
		if(qo!=null) {
			qo.removeQueue();
		}
	}

}
