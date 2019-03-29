package me.toaster.mcamusementcore.show;

import java.util.LinkedList;
import java.util.Queue;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.toaster.mcamusementcore.MCACore;

public class ShowScheduler extends BukkitRunnable{

	Show show;
	BukkitTask task;
	int count = 0;
	Queue<ShowCommand> commands;
	ArmorStand as;
	int TICKS = 0;

	public ShowScheduler(Show show) {
		this.show = show;

		//Clone show commands to manipulate as we go
		this.commands = new LinkedList<>(show.getCommands());
	}

	public void start() {
		ShowManager.addRunningShow(this);
		this.task = this.runTaskTimer(MCACore.MCA_CORE, 1, 1);
		as = (ArmorStand) Bukkit.getWorlds().get(0).spawnEntity(new Location(Bukkit.getWorlds().get(0), 120, 91, 493), EntityType.ARMOR_STAND);
		as.setCustomNameVisible(true);
	}

	public void stop() {
		ShowManager.remRunningShow(this);
	}

	public Show getShow() {
		return this.show;
	}

	@Override
	public void run() {
		TICKS++;
		as.setCustomName(TICKS+"");
		if(!this.commands.isEmpty()) {
			if(this.commands.peek()!=null) {
				ShowCommand cmd = this.commands.peek();
				//Bukkit.broadcastMessage(cmd+"");
				if(cmd instanceof ShowCommandWait) {
					ShowCommandWait wait = (ShowCommandWait) cmd;
					if(!wait.isDone()) {
						wait.increase();
						return;
					}else {
						wait.reset();
						this.commands.remove();
					}
				}else {
					cmd.execute();
					this.commands.remove();
					count++;
				}
			}else {
				this.cancel();
			}
		}else {
			this.cancel();
		}
	}

}
