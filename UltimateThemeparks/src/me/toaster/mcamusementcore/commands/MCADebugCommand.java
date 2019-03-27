package me.toaster.mcamusementcore.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import me.toaster.mcamusementcore.DebugMode;
import me.toaster.mcamusementcore.entities.CEntity;
import me.toaster.mcamusementcore.entities.CEntityInstance;
import me.toaster.mcamusementcore.entities.CEntityManager;
import me.toaster.mcamusementcore.rides.Ride;

public class MCADebugCommand extends CommandHandler{

	@Override
	public void commandReceivedPlayer(Player p, Command cmd, String label, String[] args) {
		if(p.isOp()) {
			if(args.length>=2) {
				if(args[1].equalsIgnoreCase("kill5")) {
					List<Entity> e = p.getNearbyEntities(10, 10, 10);
					for(Entity ent : e) {
						ent.remove();
					}
					p.sendMessage(ChatColor.GREEN+"Killed entities in radius of 5");
				}else if(args[1].equalsIgnoreCase("print(rides)")){
					for(Ride r : Ride.rides) {
						p.sendMessage(r.getRideName());
					}
				}else if(args[1].equalsIgnoreCase("print(cars)")){
					String nameofride = args[2];
					Ride r = Ride.getRideByName(nameofride);
					if(r!=null) {
						for(CEntity e : r.entities) {
							p.sendMessage(ChatColor.GOLD+e.toString());
						}
					}else {
						p.sendMessage("No such ride...");
					}
				}else if(args[1].equalsIgnoreCase("float(cars)")) {
					String nameofride = args[2];
					Ride r = Ride.getRideByName(nameofride);
					if(r!=null) {
						for(CEntity e : r.entities) {
							e.setPositionRotation(e.getLocation().add(0, 2, 0));
						}
					}
				}else if(args[1].equalsIgnoreCase("uuid(cars)")) {
					String nameofride = args[2];
					Ride r = Ride.getRideByName(nameofride);
					if(r!=null) {
						for(CEntity e : r.entities) {
							e.getBukkitEntity().setCustomName(e.getBukkitEntity().getUniqueId()+"");
							e.getBukkitEntity().setCustomNameVisible(true);
						}
					}
				}else if(args[1].equalsIgnoreCase("print(all_centities)")) {
					for(CEntityInstance c : CEntityManager.allEntities) {
						p.sendMessage(c.entity+""+c.entity.getBukkitEntity().getUniqueId());
					}
				}else if(args[1].equalsIgnoreCase("cleartext()")) {

				}
			}else {
				if(p.isOp()) {
					DebugMode.toggleDebug(p);
					p.sendMessage(ChatColor.GOLD+ "DebugMode set to : " + DebugMode.statusPlayer(p));
				}
			}
		}
	}

	@Override
	public void commandReceivedCommandBlock(BlockCommandSender p, Command cmd, String label, String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void commandReceivedConsole(ConsoleCommandSender p, Command cmd, String label, String[] args) {
		// TODO Auto-generated method stub

	}

}
