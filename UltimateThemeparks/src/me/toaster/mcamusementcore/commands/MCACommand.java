package me.toaster.mcamusementcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MCACommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getLabel().equalsIgnoreCase("mca")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				if(args.length>0) {
					if(args[0].equalsIgnoreCase("ride")) {
						new MCARideCommand().commandReceivedPlayer(p, cmd, label, args);
					}else if(args[0].equalsIgnoreCase("builder") || args[0].equalsIgnoreCase("build")) {
						new MCABuilderCommand().commandReceivedPlayer(p, cmd, label, args);
					}else if(args[0].equalsIgnoreCase("balloon")) {
						new MCABalloonCommand().commandReceivedPlayer(p,cmd,label,args);
					}else if(args[0].equalsIgnoreCase("backup")) {
						new MCABackupCommand().commandReceivedPlayer(p, cmd, label, args);
					}else if(args[0].equalsIgnoreCase("show")) {
						new MCAShowCommand().commandReceivedPlayer(p, cmd, label, args);
					}else if(args[0].equalsIgnoreCase("debug")) {
						new MCADebugCommand().commandReceivedPlayer(p, cmd, label, args);
					}
				}
			}
		}
		return false;
	}

}
