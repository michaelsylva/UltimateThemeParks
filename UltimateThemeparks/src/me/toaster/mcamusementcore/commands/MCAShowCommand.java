package me.toaster.mcamusementcore.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import me.toaster.mcamusementcore.show.Show;
import me.toaster.mcamusementcore.show.ShowManager;

public class MCAShowCommand extends CommandHandler{

	@Override
	public void commandReceivedPlayer(Player p, Command cmd, String label, String[] args) {
		if(args.length>=2) {
			String todo = args[1];
			if(todo.equalsIgnoreCase("create")) {
				//create show...
				if(args.length==3) {
					if(!ShowManager.doesShowExist(args[2])) {
						new Show(args[2]);
						p.sendMessage(ChatColor.GREEN+"Created show " + ChatColor.BOLD+args[2]+ChatColor.RESET+""+ChatColor.GREEN+" successfully!");
					}else {
						p.sendMessage(ChatColor.RED+"That show already exists!");
					}
				}else {
					p.sendMessage(ChatColor.RED+"Incorrect arguments. /utp show create <showname>");
				}
			}else if(todo.equalsIgnoreCase("edit")) {
				if(ShowManager.isEditting(p)) {
					//stop editting
					ShowManager.stopEditting(p);
				}else {
					//start editting
					if(args.length==3) {
						if(ShowManager.doesShowExist(args[2])) {
							ShowManager.startEditting(p, args[2]);
						}else {
							p.sendMessage(ChatColor.RED+"This show doesnt exist. Create it using /utp show create <showname>");
						}
					}else {
						p.sendMessage(ChatColor.RED+"Incorrect arguments: /utp show edit <showname>");
					}
				}
			}else if(todo.equalsIgnoreCase("delete")) {
				//...
			}else if(todo.equalsIgnoreCase("start")) {
				if(args.length==3) {
					String name = args[2];
					if(ShowManager.doesShowExist(name)) {
						Show s = ShowManager.getShowByName(name);
						s.start();
					}
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
