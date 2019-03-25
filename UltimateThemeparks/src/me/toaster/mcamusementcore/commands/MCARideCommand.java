package me.toaster.mcamusementcore.commands;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.toaster.mcamusementcore.MCAPermission;
import me.toaster.mcamusementcore.libs.BasicMenu;
import me.toaster.mcamusementcore.rides.Ride;
import me.toaster.mcamusementcore.utils.ColorUtils;
import me.toaster.mcamusementcore.utils.ItemBuilder;

public class MCARideCommand extends CommandHandler{

	MCAPermission permission = MCAPermission.UTP_COMMANDS_RIDE;
	
	public void openOperatorGUI(Player p) {

		BasicMenu menu = new BasicMenu(p, 45, ChatColor.BOLD+"Ride Operator Menu");

		for(int i = 0; i<Ride.rides.size(); i++) {
			Ride ride = Ride.rides.get(i);
			ItemStack buttonItem;
			if(ride.isRunning()) {
				buttonItem = new ItemBuilder(Material.LIME_WOOL).setLore(ChatColor.GREEN+"Ride is in motion...").addLoreLine("Session ID: " + ride.getSessionID()).toItemStack();
			}else {
				buttonItem = new ItemBuilder(Material.RED_WOOL).setLore(ChatColor.RED+"Ride is stopped in the station...").toItemStack();
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
			}else {
				p.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"Unrecognized "+ChatColor.RESET+ChatColor.GOLD+"Valid commands:");
				p.sendMessage(ChatColor.GOLD+"/utp ride operate - displays operator GUI");
				p.sendMessage(ChatColor.GOLD+"/utp ride list - lists current rides on the server");
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
