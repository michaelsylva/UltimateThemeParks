package me.toaster.ultimatethemeparks.commands;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import me.toaster.ultimatethemeparks.builder.ShowBuilder;

public class UTPShowCommand extends CommandHandler{

	@Override
	public void commandReceivedPlayer(Player p, Command cmd, String label, String[] args) {
		if(args.length>=2) {
			String todo = args[1];
			if(todo.equalsIgnoreCase("create")) {
				//create show...
			}else if(todo.equalsIgnoreCase("edit")) {
				new ShowBuilder(p).build();
			}else if(todo.equalsIgnoreCase("delete")) {
				//...
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
