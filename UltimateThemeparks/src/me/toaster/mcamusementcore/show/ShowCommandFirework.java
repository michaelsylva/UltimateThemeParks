package me.toaster.mcamusementcore.show;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;

import me.toaster.mcamusementcore.MCACore;
import me.toaster.mcamusementcore.utils.ParseUtils;

public class ShowCommandFirework extends ShowCommand{

	FireworkEffect effect;
	Location l;
	int det = -1;
	int power;

	public ShowCommandFirework(Location l, String[] data) {
		super(ShowCommandType.FIREWORK, data);
		this.l = l;
	}

	@Override
	public void execute() {
		if(hasParsed()) {
			final Firework fw = (Firework) this.l.getWorld().spawnEntity(this.l, EntityType.FIREWORK);
			FireworkMeta fwm = fw.getFireworkMeta();
			fwm.addEffect(this.effect);
			fw.setFireworkMeta(fwm);

			if(this.power==0) {
				Bukkit.getScheduler().runTaskLater(MCACore.MCA_CORE, new Runnable() {
					@Override
					public void run() {
						fw.detonate();
					}
				}, 1); 
			}else if(det!=-1){
				Bukkit.getScheduler().runTaskLater(MCACore.MCA_CORE, new Runnable() {
					@Override
					public void run() {
						fw.detonate();
					}
				}, det); 
			}
		}
	}

	public boolean hasParsed() {
		if(this.effect!=null && this.l!=null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean parse() {

		try {
			String color = this.getData()[1];
			String power = this.getData()[2];
			String type = this.getData()[3];
			String twinkle = this.getData()[4];
			String trail = this.getData()[5];

			String velx = "0";
			String vely = "0";
			String velz = "0";
			String det = null;

			if(this.getData().length>=11) {velx=this.getData()[6];}
			if(this.getData().length>=12) {vely=this.getData()[7];}
			if(this.getData().length>=13) {velz=this.getData()[8];}
			if(this.getData().length>=14) {det=this.getData()[9];}

			ArrayList<Color> colorsF = ParseUtils.parseColors(color);
			int powerF = Integer.parseInt(power);
			Type typeF = ParseUtils.typeByName(type);
			boolean twinkleF = Boolean.parseBoolean(twinkle);
			boolean trailF = Boolean.parseBoolean(trail);

			Vector vel = new Vector(Double.parseDouble(velx),Double.parseDouble(vely),Double.parseDouble(velz));

			if(type!=null && colorsF.size()>0 && this.l!=null) {
				this.effect = FireworkEffect.builder()
						.flicker(twinkleF)
						.trail(trailF)
						.withColor(colorsF)
						.with(typeF)
						.build();
				this.power = powerF;
				if(det!=null && ParseUtils.isInt(det)) {
					this.det = Integer.parseInt(det);
				}

				return true;
			}

		}catch(Exception e) {
			return false;
		}

		return false;
	}

}
