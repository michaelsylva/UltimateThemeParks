package me.toaster.ultimatethemeparks.commands;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import me.toaster.ultimatethemeparks.balloons.Balloon;
import me.toaster.ultimatethemeparks.balloons.BalloonManager;

public class UTPBalloonCommand extends CommandHandler{

	@Override
	public void commandReceivedPlayer(Player p, Command cmd, String label, String[] args) {
		if(args.length==2) {
			String name = args[1];
			Balloon b = BalloonManager.getBalloonByName(name);
			if(b!=null) {
				b.giveBalloon(p);
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
