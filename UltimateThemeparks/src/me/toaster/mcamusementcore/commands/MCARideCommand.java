package me.toaster.mcamusementcore.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.toaster.mcamusementcore.MCAPermission;
import me.toaster.mcamusementcore.entities.CEntity;
import me.toaster.mcamusementcore.libs.BasicMenu;
import me.toaster.mcamusementcore.rides.Ride;
import me.toaster.mcamusementcore.utils.ColorUtils;
import me.toaster.mcamusementcore.utils.ItemBuilder;
import me.toaster.mcamusementcore.utils.ItemUtils;

public class MCARideCommand extends CommandHandler{

	MCAPermission permission = MCAPermission.UTP_COMMANDS_RIDE;

	public void openOperatorGUI(Player p) {

		BasicMenu menu = new BasicMenu(p, 45, ChatColor.BOLD+"Ride Operator Menu");

		for(int i = 0; i<Ride.rides.size(); i++) {
			Ride ride = Ride.rides.get(i);
			ItemStack buttonItem;

			buttonItem = new ItemBuilder(Material.GOLD_BLOCK).setLore(ChatColor.GOLD+""+ChatColor.BOLD+ride.getRideName()+" : " + ride.getRideType()).toItemStack();

			menu.setLocked(true);
			menu.AddOption(i, buttonItem, ChatColor.AQUA+ride.getRideName(), new Runnable() {

				@Override
				public void run() {
					openRideSpecificOperatorGUI(p, ride);
				}

			});
		}

		menu.open();
	}

	public void openRideSpecificOperatorGUI(Player p, Ride ride) {

		BasicMenu menu = new BasicMenu(p, 45, ChatColor.BOLD+ride.getRideName()+" Operator Menu");

		ItemStack buttonItem;
		if(ride.isRunning()) {
			buttonItem = new ItemBuilder(Material.LIME_WOOL).setLore(ChatColor.GREEN+"Ride is in motion...").addLoreLine("Session ID: " + ride.getSessionID()).toItemStack();
		}else {
			buttonItem = new ItemBuilder(Material.RED_WOOL).setLore(ChatColor.RED+"Ride is stopped in the station...").toItemStack();
		}

		ItemStack buttonItem2;
		if(ride.isSpawned()) {
			buttonItem2 = new ItemBuilder(Material.LIME_WOOL).setLore(ChatColor.GREEN+"Ride is spawned").addLoreLine("Session ID: " + ride.getSessionID()).toItemStack();
		}else {
			buttonItem2 = new ItemBuilder(Material.RED_WOOL).setLore(ChatColor.RED+"Ride is not spawned").toItemStack();
		}

		ItemStack buttonItem3;
		if(ride.isOpen()) {
			buttonItem3 = new ItemBuilder(Material.LIME_WOOL).setLore(ChatColor.GREEN+"Ride is open!").addLoreLine("Session ID: " + ride.getSessionID()).toItemStack();
		}else {
			buttonItem3 = new ItemBuilder(Material.RED_WOOL).setLore(ChatColor.RED+"Ride is closed :(").toItemStack();
		}

		menu.setLocked(true);
		
		int i = 9;
		int cart = 0;
		for(CEntity ent : ride.entities) {
			if(ent.getBukkitEntity().getPassenger()!=null) {
				if(ent.getBukkitEntity().getPassenger() instanceof Player) {
					Player p_in_car = (Player) ent.getBukkitEntity().getPassenger();
					ItemStack headItem = ItemUtils.getHeadItem(p_in_car.getName());
					headItem = new ItemBuilder(headItem).setLore(ChatColor.GREEN + p_in_car.getName() + " is in cart="+cart).toItemStack();
					menu.AddOption(i, headItem, p_in_car.getName(), null);
					i++;
				}
			}
			cart++;
		}
		
		menu.AddOption(2, buttonItem3, ChatColor.AQUA+ride.getRideName(), new Runnable() {

			@Override
			public void run() {
				if(ride.isOpen()) {
					ride.setOpen(false);
					p.sendMessage(ChatColor.RED+"You have closed " + ride.getRideName());
				}else {
					ride.setOpen(true);
					p.sendMessage(ChatColor.GREEN+"You have opened " + ride.getRideName());
				}
				openRideSpecificOperatorGUI(p, ride);
			}
			
		});
		
		menu.AddOption(1, buttonItem2, ChatColor.AQUA+ride.getRideName(), new Runnable() {

			@Override
			public void run() {
				if(!ride.isSpawned()) {
					ride.spawn();
					p.sendMessage(ChatColor.GREEN+"You have spawned " + ride.getRideName());
				}else {
					ride.despawn();
					p.sendMessage(ChatColor.GREEN+"You have despawned " + ride.getRideName());
				}
				openRideSpecificOperatorGUI(p, ride);
			}
		});
		
		menu.AddOption(0, buttonItem, ChatColor.AQUA+ride.getRideName(), new Runnable() {

			@Override
			public void run() {
				if(!ride.isRunning()) {
					ride.start();
					p.sendMessage(ChatColor.GREEN+"You have started " + ride.getRideName());
				}else {
					ride.finish();
					p.sendMessage(ChatColor.GREEN+"You have stopped " + ride.getRideName());
				}
				openRideSpecificOperatorGUI(p, ride);
			}

		});
		menu.open();
	}



public void printRideList(Player p) {
	p.sendMessage(ChatColor.AQUA+"--- List of rides ---");
	for(Ride r : Ride.rides) {
		p.sendMessage(ChatColor.BOLD+r.getRideName()+""+ChatColor.RESET+" | Spawned: "+ColorUtils.colorStringWithBool(String.valueOf(r.isSpawned()), r.isSpawned())+ChatColor.RESET+" | Running: "+ColorUtils.colorStringWithBool(String.valueOf(r.isRunning()), r.isRunning()));
	}
}

@Override
public void commandReceivedPlayer(Player p, Command cmd, String label, String[] args) {
	if(p.hasPermission(this.permission.getValue())) {
		if(args.length>1) {
			if(args[1].equalsIgnoreCase("operate")) {
				openOperatorGUI(p);
			}else if(args[1].equalsIgnoreCase("list")) {
				printRideList(p);
			}
		}else {
			p.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"Unrecognized "+ChatColor.RESET+ChatColor.GOLD+"Valid commands:");
			p.sendMessage(ChatColor.GOLD+"/mca ride operate - displays operator GUI");
			p.sendMessage(ChatColor.GOLD+"/mca ride list - lists current rides on the server");
		}
	}
}

@Override
public void commandReceivedCommandBlock(BlockCommandSender p, Command cmd, String label, String[] args) {
	return;
}

@Override
public void commandReceivedConsole(ConsoleCommandSender p, Command cmd, String label, String[] args) {
	return;
}


}
