package me.toaster.ultimatethemeparks.balloons;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.Vector;

import me.toaster.ultimatethemeparks.entities.CEntityArmorstand;
import me.toaster.ultimatethemeparks.entities.CEntityBat;
import me.toaster.ultimatethemeparks.utils.Cooldown;
import me.toaster.ultimatethemeparks.utils.PlayerUtils;

public class Balloon {

	String name, skin;
	
	//TODO export and create balloons
	public Balloon(String name, String skin) {
		this.name = name;
		this.skin = skin;
	}

	public void giveBalloon(Player p) {
		ItemStack skull = getItem();
		PlayerUtils.giveItem(p, skull);
	}
	
	public ItemStack getItem() {
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		meta.setOwner(this.skin);
		meta.setDisplayName(ChatColor.LIGHT_PURPLE + this.name + " balloon");
		skull.setItemMeta(meta);
		return skull;
	}

	public void spawn(Player p) {
		if(!Cooldown.isInCooldown(p.getUniqueId(), "balloon")) {
			ActiveBalloon balloon = new ActiveBalloon(p, this);
			BalloonManager.spawnedBalloon(p, balloon);
			new Cooldown(p.getUniqueId(), "balloon", 10).start();
		}else {
			int secondsLeft = Cooldown.getTimeLeft(p.getUniqueId(), "balloon");
			p.sendMessage(ChatColor.RED+"You cannot use your balloon for another " + secondsLeft + " seconds");
		}
	}
	
	public void despawn(Player p) {
		if(BalloonManager.hasActiveBalloon(p)) {
			ActiveBalloon balloon = BalloonManager.getActiveBalloon(p);
			balloon.despawn();
			BalloonManager.despawnedBalloon(p);
		}
	}
	
	@Override
	public String toString() {
		return "Balloon{name:"+this.name+", skin:"+this.skin+"}";
	}
	
	public class ActiveBalloon{
		Balloon type;
		Player p;
		CEntityArmorstand balloon;
		CEntityBat holder;
		
		public ActiveBalloon(Player p, Balloon type) {
			this.p = p;
			this.type = type;
			this.create();
		}
		
		public void create() {
			holder = new CEntityBat(p.getLocation().clone().add(0, BalloonManager.HEIGHT+1, 0));
			balloon = new CEntityArmorstand(p.getLocation().clone().add(0, BalloonManager.HEIGHT, 0));
			
			holder.setBatInvisible();
			holder.setNoAI(true);
			balloon.setInvisible(true);
			holder.setLeashHolder(this.p);
			
			balloon.setHead(this.type.getItem());
		}
		
		public Location getLocation() {
			return this.balloon.getLocation();
		}

		public void setVelocity(Vector v) {
			balloon.getBukkitArmorstand().setVelocity(v);
		}
		
		public void twistHead() {
			ArmorStand bukkitAS = balloon.getBukkitArmorstand();
			bukkitAS.setHeadPose(bukkitAS.getHeadPose().setY(bukkitAS.getHeadPose().getY()+BalloonManager.SPIN_SPEED));
		}
		
		public void joinHolderAndBalloon() {
			holder.getBukkitEntity().teleport(balloon.getBukkitArmorstand().getLocation().add(0, 1, 0));
		}
		
		public void despawn(){
			if(holder!=null) {
				holder.remove();
			}
			
			if(balloon!=null) {
				balloon.remove();
			}
		}
		
	}
	
}
