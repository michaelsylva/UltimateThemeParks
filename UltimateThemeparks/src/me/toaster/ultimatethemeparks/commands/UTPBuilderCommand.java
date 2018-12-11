package me.toaster.ultimatethemeparks.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import me.toaster.ultimatethemeparks.builder.RollercoasterBuilder;
import me.toaster.ultimatethemeparks.builder.ShowBuilder;
import me.toaster.ultimatethemeparks.builder.UTPBuilder;

public class UTPBuilderCommand extends CommandHandler{

	@Override
	public void commandReceivedPlayer(Player p, Command cmd, String label, String[] args) {
		if(args.length==2) {
			String type = args[1];
			if(type.equalsIgnoreCase("exit") || type.equalsIgnoreCase("leave")) {
				if(UTPBuilder.isBuilding(p)) {
				UTPBuilder.removeBuilding(p);
					p.sendMessage(ChatColor.GREEN+"You are no longer in the builder");
				}else {
					p.sendMessage(ChatColor.RED+"You were not in a builder!");
				}
			}else if(type.equalsIgnoreCase("rollercoaster") || type.equalsIgnoreCase("rc")) {
				if(!UTPBuilder.isBuilding(p)) {
					new RollercoasterBuilder(p).build();
				}else {
					p.sendMessage(ChatColor.RED+"You cannot enter another builder!");
				}
			}else if(type.equalsIgnoreCase("show")) {
				if(!UTPBuilder.isBuilding(p)) {
					new ShowBuilder(p).build();
				}else {
					p.sendMessage(ChatColor.RED+"You cannot enter another builder!");
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