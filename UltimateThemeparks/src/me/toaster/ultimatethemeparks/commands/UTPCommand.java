package me.toaster.ultimatethemeparks.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UTPCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getLabel().equalsIgnoreCase("utp")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				if(args.length>0) {
					if(args[0].equalsIgnoreCase("ride")) {
						new UTPRideCommand().commandReceivedPlayer(p, cmd, label, args);
					}else if(args[0].equalsIgnoreCase("builder") || args[0].equalsIgnoreCase("build")) {
						new UTPBuilderCommand().commandReceivedPlayer(p, cmd, label, args);
					}else if(args[0].equalsIgnoreCase("balloon")) {
						new UTPBalloonCommand().commandReceivedPlayer(p,cmd,label,args);
					}
				}
			}
		}
		return false;
	}

}
