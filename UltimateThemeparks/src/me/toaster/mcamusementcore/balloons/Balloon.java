package me.toaster.mcamusementcore.balloons;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.Vector;

import me.toaster.mcamusementcore.entities.CEntityArmorstand;
import me.toaster.mcamusementcore.entities.CEntityBat;
import me.toaster.mcamusementcore.utils.Cooldown;
import me.toaster.mcamusementcore.utils.ItemUtils;
import me.toaster.mcamusementcore.utils.PlayerUtils;

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
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1, (short)3);
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

	public boolean hasBalloon(Player p) {
		for(ItemStack is : p.getInventory().getContents()) {
			if(is!=null) {
				if(ItemUtils.isSimilar(is,this.getItem())) {
					System.out.println("Its similar!");
					return true;
				}
			}
		}
		return false;
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
		CEntityBat holder2;
		public boolean isFlying = false;
		public int flyingTicks = 0;

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
		
		public void fly() {
			this.holder2 = new CEntityBat(p.getLocation());
			this.holder2.setBatInvisible();
			this.holder2.setNoAI(true);
			this.holder2.setInvisible(true);
			this.holder.setLeashHolder(this.holder2.getBukkitBat());
			this.isFlying = true;
		}

		public Location getLocation() {
			return this.balloon.getLocation();
		}

		public void setLocation(Location l) {
			this.balloon.setLocation(l.getX(), l.getY(), l.getZ(), 0.0f, 0.0f);
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
			if(holder2!=null) {
				holder2.getBukkitEntity().teleport(balloon.getBukkitArmorstand().getLocation().subtract(0, 2, 0));
			}
		}

		public void despawn(){
			if(holder!=null) {
				holder.remove();
			}

			if(balloon!=null) {
				balloon.remove();
			}
			
			if(holder2!=null) {
				holder2.remove();
			}
		}

	}

}
