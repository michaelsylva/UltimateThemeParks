package me.toaster.mcamusementcore.commands;

import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import me.toaster.mcamusementcore.MCACore;
import me.toaster.mcamusementcore.MCAPermission;
import me.toaster.mcamusementcore.utils.MessageUtils;

public class MCABackupCommand extends CommandHandler{

	@Override
	public void commandReceivedPlayer(Player p, Command cmd, String label, String[] args) {
		if(p.hasPermission(MCAPermission.UTP_COMMANDS_BACKUP.getValue())) {
			File dataFolder = MCACore.UTP_CORE.getDataFolder();
			World w = p.getWorld();
			Format formatter = new SimpleDateFormat("MM-dd-yyyy");
			String s = formatter.format(new Date());
			final File srcDir = w.getWorldFolder();
			final File destDir = new File(dataFolder.getPath() + File.separator + "Backups" + File.separator + s);
			MessageUtils.messageStaff(ChatColor.LIGHT_PURPLE+ "" + p.getName() + " is backing up the map! Please do not reload!");
			
			Bukkit.getScheduler().runTaskAsynchronously(MCACore.UTP_CORE, new Runnable() {
				@Override
				public void run() {

					try {
						FileUtils.copyDirectory(srcDir, destDir);
					} catch (IOException e) {
						p.sendMessage(ChatColor.RED + "Failed to backup map!");
						Bukkit.getLogger().info("Failed to backup map!");
						e.printStackTrace();
					}
					MessageUtils.messageStaff(ChatColor.LIGHT_PURPLE+"Backup complete!");
				}
			});

			
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
