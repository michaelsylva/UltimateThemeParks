package me.toaster.mcamusementcore.commands;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public abstract class CommandHandler {
	
	public abstract void commandReceivedPlayer(Player p, Command cmd, String label, String[] args);
	
	public abstract void commandReceivedCommandBlock(BlockCommandSender p, Command cmd, String label, String[] args);
	
	public abstract void commandReceivedConsole(ConsoleCommandSender p, Command cmd, String label, String[] args);
}
