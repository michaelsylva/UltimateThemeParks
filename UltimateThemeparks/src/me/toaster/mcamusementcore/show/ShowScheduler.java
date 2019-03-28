package me.toaster.mcamusementcore.show;

import java.util.LinkedList;
import java.util.Queue;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.toaster.mcamusementcore.MCACore;

public class ShowScheduler extends BukkitRunnable{

	Show show;
	BukkitTask task;
	int count = 0;
	Queue<ShowCommand> commands;

	public ShowScheduler(Show show) {
		this.show = show;

		//Clone show commands to manipulate as we go
		this.commands = new LinkedList<>(show.getCommands());
	}

	public void start() {
		ShowManager.addRunningShow(this);
		this.task = this.runTaskTimer(MCACore.MCA_CORE, 1, 1);
	}

	public void stop() {
		ShowManager.remRunningShow(this);
	}

	public Show getShow() {
		return this.show;
	}

	@Override
	public void run() {
		if(!this.commands.isEmpty()) {
			if(this.commands.peek()!=null) {
				ShowCommand cmd = this.commands.peek();
				if(cmd instanceof ShowCommandWait) {
					ShowCommandWait wait = (ShowCommandWait) cmd;
					if(!wait.isDone()) {
						wait.increase();
						return;
					}else {
						this.commands.remove();
					}
				}else {
					cmd.execute();
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
