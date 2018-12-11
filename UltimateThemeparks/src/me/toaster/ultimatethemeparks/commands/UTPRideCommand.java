package me.toaster.ultimatethemeparks.commands;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.toaster.ultimatethemeparks.UTPPermission;
import me.toaster.ultimatethemeparks.libs.BasicMenu;
import me.toaster.ultimatethemeparks.rides.Ride;
import me.toaster.ultimatethemeparks.utils.ColorUtils;
import me.toaster.ultimatethemeparks.utils.ItemBuilder;

public class UTPRideCommand extends CommandHandler{

	UTPPermission permission = UTPPermission.UTP_COMMANDS_RIDE;
	
	public void openOperatorGUI(Player p) {

		BasicMenu menu = new BasicMenu(p, 45, ChatColor.BOLD+"Ride Operator Menu");

		for(int i = 0; i<Ride.rides.size(); i++) {
			Ride ride = Ride.rides.get(i);
			ItemStack buttonItem;
			if(ride.isRunning()) {
				buttonItem = new ItemBuilder(Material.WOOL).setWoolColor(DyeColor.LIME).setLore(ChatColor.GREEN+"Ride is in motion...").addLoreLine("Session ID: " + ride.getSessionID()).toItemStack();
			}else {
				buttonItem = new ItemBuilder(Material.WOOL).setWoolColor(DyeColor.RED).setLore(ChatColor.RED+"Ride is stopped in the station...").toItemStack();
			}

			menu.setLocked(true);
			menu.AddOption(i, buttonItem, ChatColor.AQUA+ride.getRideName(), new Runnable() {

				@Override
				public void run() {
					if(!ride.isRunning()) {
						ride.start();
					}
				}

			});
		}

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
