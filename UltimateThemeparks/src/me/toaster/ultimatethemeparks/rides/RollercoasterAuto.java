package me.toaster.ultimatethemeparks.rides;

import java.util.LinkedList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import me.toaster.ultimatethemeparks.UTPCore;
import me.toaster.ultimatethemeparks.entities.CEntity;
import me.toaster.ultimatethemeparks.entities.CEntityMinecart;
import me.toaster.ultimatethemeparks.entities.CEntityArmorstand;
import me.toaster.ultimatethemeparks.rides.Train.TrainType;
import me.toaster.ultimatethemeparks.utils.ItemUtils;
import me.toaster.ultimatethemeparks.utils.MathUtils;
import net.minecraft.server.v1_12_R1.Vector3f;

/**
 * Rollercoaster ride type object
 * The rollercoaster ride type can map out a track from a starting position
 * and dynamically make a spline to follow for the track
 * 
 * {WIP}
 * 
 * @author Michael Sylva
 *
 */
public class RollercoasterAuto extends Ride{

	int length;
	Material track;
	Material controlTrack;
	Location backLoc;
	BlockDirection dir;
	BlockDirection cDir;
	Train train;

	final int STATION_WAIT = 20*5;

	BukkitTask task;
	TrackMap map;

	public RollercoasterAuto(int length, Material track, BlockDirection dir) {
		this.length=length;
		this.track=track;
		this.controlTrack=Material.WOOL;
		this.dir = dir;
		this.cDir=dir;
		this.backLoc = new Location(Bukkit.getWorlds().get(0),80.5,90.75,498.5,90,0);
		this.train = new Train(length, TrainType.MODEL);
		this.map = new TrackMap(this.backLoc);
		
		setRideType(RideType.ROLLERCOASTER);
		setRideName("Rollercoaster");
	}

	@Override
	public boolean spawn() {
		for(int i = 0; i<length; i++) {
			Location l = this.map.spline.get(i*train.trainDistance).location;
			Location lookAt = this.map.spline.get(i*train.trainDistance+1).location;
			l.setYaw(MathUtils.getLookAtYaw(lookAt.clone().subtract(l).toVector())+90);
			Car car;
			if(train.type==TrainType.MODEL) {
				car = new ModelCar(new CEntityArmorstand(l,ItemUtils.getItemWithData(Material.DIAMOND_HOE,(short)16)));
			}else {
				car = new MinecartCar(new CEntityMinecart(l.clone().add(0, 0.75, 0)));
			}
			//this.entities.add(car);
			train.cars[i] = car;
		}
		setSpawned(true);
		return true;
	}

	@Override
	public void despawn() {
		for(Car car : this.train.cars) {
			car.entity.remove();
		}
		setSpawned(false);
	}

	@Override
	public void setup() {
		// TODO Auto-generated method stub
	}

	@Override
	public void start() {
		for(int i = 0; i<length; i++) {
			Car c = train.cars[i];
			CEntity entity = c.entity;
		}
		task = new RollercoasterRunnable().runTaskTimer(UTPCore.getPlugin(UTPCore.class), 1L, 1L);
		this.createSessionID();
		this.setRunning(true);
	}

	@Override
	public void finish() {
		task.cancel();
		this.setRunning(false);
		setSessionID(null);
		this.reset();
	}

	@Override
	public void reset() {
		for(Car c : train.cars) {
			c.entity.setPositionRotation(c.entity.getStarting());
		}
	}

	@Override
	public void teleportPlayer(Player p) {
		// TODO Auto-generated method stub

	}

	public enum TrackDirection{
		STRAIGHT,
		LEFT,
		RIGHT,
		UP,
		DOWN,
		STRAIGHT_DOWN,
		STRAIGHT_UP;
	};

	/**
	 * Object to store pieces of the track, by Location
	 * @author Michael Sylva
	 */
	public class TrackPiece{
		/** Location of track piece */
		Location location;
		TrackDirection dirFromLast;

		public TrackPiece(Location l) {
			this.location = l;
		}

		public TrackPiece(Location l,TrackDirection dir) {
			this.location = l;
			this.dirFromLast = dir;
		}

	}

	/**
	 * TrackMap, holding a map of the track, or points along the track
	 * @author Michael Sylva
	 *
	 */
	public class TrackMap{
		LinkedList<TrackPiece> tracks;
		LinkedList<TrackPiece> controlPoints;
		LinkedList<TrackPiece> spline;
		BlockDirection cDir;
		final int CONTROL_POINT_DISTANCE = 5;
		boolean isLooping = true;

		public TrackMap(Location start) {
			this.cDir = dir;
			this.tracks = map(start);
			this.controlPoints = getControlPoints();
			this.spline = makeSpline();
		}

		public LinkedList<TrackPiece> makeSpline(){
			LinkedList<TrackPiece> spline = new LinkedList<TrackPiece>();
			int numSections = controlPoints.size() - 3;

			for(int i = 0; i<controlPoints.size(); i++) {
				if((i==0 || i==controlPoints.size()-2 || i== controlPoints.size()-1) && !isLooping) {
					continue;
				}

				Vector p0 = controlPoints.get(clampListPos(i-1)).location.toVector();
				Vector p1 = controlPoints.get(clampListPos(i)).location.toVector();
				Vector p2 = controlPoints.get(clampListPos(i+1)).location.toVector();
				Vector p3 = controlPoints.get(clampListPos(i+2)).location.toVector();

				//The spline's resolution
				//Make sure it's is adding up to 1, so 0.3 will give a gap, but 0.2 will work

				double dist = p2.distance(p1);

				float resolution = (float) (1.0/(dist*300));
				int loops = (int)Math.floor(1f / resolution);

				for(int j = 0; j<loops; j++) {
					float t = j*resolution;
					Vector newPos = getCatmulRomSplinePosition(t, p0, p1, p2, p3);
					spline.add(new TrackPiece(newPos.toLocation(backLoc.getWorld())));
				}
			}
			return spline;
		}


		/**
		 * Clamp the list position so we don't get an ArrayIndexOutOfBounds
		 * @param pos index trying to access
		 * @return safe index to access
		 */
		public int clampListPos(int pos) {
			if (pos < 0){
				pos = controlPoints.size() + pos;
			}

			if (pos > controlPoints.size()){
				pos = 1;
			}else if (pos > controlPoints.size() - 1){
				pos = 0;
			}
			return pos;
		}

		public Vector getCatmulRomSplinePosition(float t, Vector p0, Vector p1, Vector p2, Vector p3) {

			p0 = p0.clone();
			p1 = p1.clone();
			p2 = p2.clone();
			p3 = p3.clone();

			//coefficients of the cubic polynomial
			Vector a = p1.clone().multiply(2.0f);
			Vector b = p2.clone().subtract(p0);

			Vector c1 = p0.clone().multiply(2.0f);
			Vector c2 = p1.clone().multiply(5.0f);
			Vector c3 = p2.clone().multiply(4.0f);

			Vector c = c1.clone().subtract(c2).add(c3).subtract(p3);

			Vector d1 = p0.clone().multiply(-1.0f);
			Vector d2 = p1.clone().multiply(3.0f);
			Vector d3 = p2.clone().multiply(3.0f);

			Vector d = d1.clone().add(d2).subtract(d3).add(p3);

			b.multiply(t);
			c.multiply(t*t);
			d.multiply(t*t*t);

			Vector pos = (a.clone().add(b).add(c).add(d)).multiply(0.5);

			return pos;
		}

		public LinkedList<TrackPiece> getControlPoints() {
			LinkedList<TrackPiece> points = new LinkedList<TrackPiece>();
			for(int i = 0; i<tracks.size(); i++) {
				if(i%CONTROL_POINT_DISTANCE==0) {
					points.add(tracks.get(i));
				}
			}
			return points;
		}

		/**
		 * displays the LinkedList of TrackPieces with a material in-game
		 * @param pieces LinkedList of Track Pieces
		 * @param offset offset to place blocks
		 */
		@SuppressWarnings("deprecation")
		public void display(LinkedList<TrackPiece> pieces, Vector offset) {
			for(TrackPiece trackpiece : pieces) {
				trackpiece.location.clone().add(offset).getBlock().setType(Material.WOOD);
			}

			Bukkit.getScheduler().runTaskLater(UTPCore.getPlugin(UTPCore.class), new BukkitRunnable() {

				@Override
				public void run() {
					for(TrackPiece trackpiece : pieces) {
						trackpiece.location.clone().add(offset).getBlock().setType(Material.AIR);
					}
				}

			}, 800L);
		}

		/**
		 * displays the LinkedList of TrackPieces with entities
		 * @param pieces LinkedList of Track Pieces
		 * @param offset offset to place blocks
		 */
		@SuppressWarnings("deprecation")
		public void displayEntities(LinkedList<TrackPiece> pieces, Vector offset) {
			for(TrackPiece trackpiece : pieces) {
				new CEntityMinecart(trackpiece.location.clone().add(offset));
			}
		}


		/**
		 * Retrieve a List of Locations that maps the track block locations from
		 * the current point of the entity
		 * @param e Entity that you are mapping from
		 * @return LinkedList of Locations that map the track from the entities location
		 */
		public LinkedList<TrackPiece> map(Location start) {
			cDir = dir;
			LinkedList<TrackPiece> map = new LinkedList<TrackPiece>();
			Block original = start.getBlock();
			Location curr = start;
			int i = 0;
			while(getNextTrack(curr)!=null) {
				
				//Make sure we don't hit the original block in a continuous track...
				if(map.size()>0) {
					if(MathUtils.blockLocationEqual(map.get(0).location,getNextTrack(curr).location)) {
						break;
					}
				}
				
				TrackPiece now = getNextTrack(curr);
				now.location.add(0.5, 0, 0.5);
				Location og = now.location.clone();
				Vector go = now.location.clone().subtract(curr).toVector();
				if(now.dirFromLast==TrackDirection.DOWN || now.dirFromLast==TrackDirection.STRAIGHT_DOWN || now.dirFromLast==TrackDirection.STRAIGHT_UP) {
					double factor = 0.8;
					if(now.dirFromLast==TrackDirection.DOWN) factor = 0.4;
					if(now.dirFromLast==TrackDirection.STRAIGHT_DOWN) factor = 0.8;
					if(now.dirFromLast==TrackDirection.STRAIGHT_UP) factor = 0.5;
					Vector cross = go.crossProduct(cDir.getAdjacentLeft().getVector());
					now.location.add(cross.normalize().multiply(factor));
				}
				map.add(now);
				curr = og;
				i++;
			}
			return map;
		}

		/**
		 * Retrieves next track block from a location, i.e the current entities location
		 * @param l Location to look from
		 * @return The block of the next track piece
		 */
		private TrackPiece getNextTrack(Location l) {
			Location current = l;
			Block next = current.clone().add(cDir.getVector()).getBlock();
			Block adjLeft = current.clone().add(cDir.getAdjacentLeft().getVector()).getBlock();
			Block adjRight = current.clone().add(cDir.getAdjacentRight().getVector()).getBlock();
			Block up = current.clone().add(cDir.up()).getBlock();
			Block down = current.clone().add(cDir.down()).getBlock();
			Block straightDown = current.clone().add(0, -1, 0).getBlock();
			Block straightUp = current.clone().add(0, 1, 0).getBlock();
			if(next.getType()==track || next.getType()==controlTrack) {
				return new TrackPiece(next.getLocation(), TrackDirection.STRAIGHT);
			}else if(adjLeft.getType()==track || adjLeft.getType()==controlTrack) {
				cDir = cDir.getAdjacentLeft();
				return new TrackPiece(adjLeft.getLocation(),TrackDirection.LEFT);
			}else if(adjRight.getType()==track || adjRight.getType()==controlTrack) {
				cDir = cDir.getAdjacentRight();
				return new TrackPiece(adjRight.getLocation(),TrackDirection.RIGHT);
			}else if(up.getType()==track || up.getType()==controlTrack){
				return new TrackPiece(up.getLocation(),TrackDirection.UP);
			}else if(down.getType()==track || down.getType()==controlTrack){
				return new TrackPiece(down.getLocation(),TrackDirection.DOWN);
			}else if(straightDown.getType()==track || straightDown.getType()==controlTrack){
				return new TrackPiece(straightDown.getLocation(), TrackDirection.STRAIGHT_DOWN);
			}else if(straightUp.getType()==track || straightUp.getType()==controlTrack){
				return new TrackPiece(straightUp.getLocation(), TrackDirection.STRAIGHT_UP);
			}else {
				return null;
			}
		}

	}


	/**
	 * BlockDirection enumerator, handles all cardinal directions with vectors
	 * and manipulation
	 * @author Michael Sylva
	 */
	public enum BlockDirection{

		WEST(new Vector(-1,0,0)),
		NORTH(new Vector(0,0,-1)),
		EAST(new Vector(1,0,0)),
		SOUTH(new Vector(0,0,1));

		private Vector v;

		BlockDirection(Vector v) {
			this.v = v;
		}

		/**
		 * Get vector clone from direction
		 * @return the Vector
		 */
		public Vector getVector() {
			return this.v.clone();
		}

		/**
		 * Get opposite direction
		 * @return the opposite facing cardinal direction
		 */
		public BlockDirection getOpposite() {
			if(this==BlockDirection.EAST) {
				return BlockDirection.WEST;
			}else if(this==BlockDirection.WEST) {
				return BlockDirection.EAST;
			}else if(this==BlockDirection.NORTH) {
				return BlockDirection.SOUTH;
			}else if(this==BlockDirection.SOUTH) {
				return BlockDirection.NORTH;
			}
			return null;
		}

		/**
		 * Get the direction facing left from the current
		 * @return direction facing left
		 */
		public BlockDirection getAdjacentLeft() {
			if(this==NORTH) {
				return WEST;
			}else if(this==EAST) {
				return NORTH;
			}else if(this==SOUTH) {
				return EAST;
			}else if(this==WEST) {
				return SOUTH;
			}
			return null;
		}

		/**
		 * Get the direction facing right from the current direction
		 * @return direction facing right
		 */
		public BlockDirection getAdjacentRight() {
			if(this==NORTH) {
				return EAST;
			}else if(this==EAST) {
				return SOUTH;
			}else if(this==SOUTH) {
				return WEST;
			}else if(this==WEST) {
				return NORTH;
			}
			return null;
		}

		/**
		 * Get direction and add an upwards vector
		 * @return direction with up
		 */
		public Vector up() {
			return this.getVector().add(new Vector(0,1,0));
		}

		/**
		 * Get direction and add a downwards vector
		 * @return direction with down
		 */
		public Vector down() {
			return this.getVector().add(new Vector(0,-1,0));
		}

		/**
		 * Get general cardinal direction that the vector is going
		 * {WIP}
		 * @param v Vector moving at
		 * @return Cardinal direction it is going.
		 */
		public BlockDirection getDirectionGoing(Vector v) {

			if(v.getX()>0 && Math.abs(v.getZ())<0.1) {
				return BlockDirection.EAST;
			}else if(v.getZ()>0 && Math.abs(v.getX())<0.1) {
				return BlockDirection.SOUTH;
			}else if(v.getX()<0 && Math.abs(v.getZ())<0.1) {
				return BlockDirection.WEST;
			}else if(v.getZ()<0 && Math.abs(v.getX())<0.1) {
				return BlockDirection.NORTH;
			}
			return null;
		}

	}

	/**
	 * Runnable handling the run of the rollercoaster, runs every tick (1/20 second)
	 * @author Michael Sylva
	 */
	class RollercoasterRunnable extends BukkitRunnable{

		int TICKS = 0;
		int runningTicks = 0;
		Vector[] going = new Vector[length];
		double THRESHOLD = 0.05;
		int ahead = 0;

		@Override
		public void run() {
			//if(TICKS>STATION_WAIT) {
			
			//For each car in the train
			for(int i = 0; i<train.cars.length; i++) {
				
				//Car in current iteration
				Car car = train.cars[i];
				
				if(train.isInStation()) {
					train.updateMessage(ChatColor.RED+"IN STATION");
				}
				
				//Entity for the car
				CEntity ce = car.entity;
				
				//Index the car should be at, based on the back cars index
				int idx = train.getPositionOfCar(car);
				
				//If the idx+1 is past the highest point in the array, make it back to the zeroth position
				if(idx+1>map.spline.size()) {
					idx = idx-map.spline.size();
				}
				
				//If the idx+1 is not past the highest point in the array, normal ride loop.
				if(idx+1<map.spline.size()) {
					
					//Location to go to
					Location loc = map.spline.get(idx).location.clone();
					Location lookAt = map.spline.get(idx+1).location;
					Vector going = lookAt.clone().subtract(loc).toVector();
					
					//Update the cars going vector
					car.going = going;

					//update speeds
					if(i==0) {
						if(train.isGoingDown()) {
							train.increaseSpeed(0.35*Math.abs(going.clone().normalize().getY()));
						}

						if(train.isGoingUp()) {
							//train.decreaseSpeed(Math.abs(going.clone().normalize().getY()));
						}
					}

					//update yaw & pitch
					loc.setYaw(MathUtils.getLookAtYaw(going)+90);
					if(ce instanceof CEntityArmorstand) {
						CEntityArmorstand model = (CEntityArmorstand) ce;
						float pitch = MathUtils.getLookAtPitch(loc, lookAt);
						//float currPitch = model.get
						model.setHeadPose(new Vector3f(pitch,0.0f,0.0f));
					}


					//set position
					ce.setPositionRotation(loc);
					
					//update positions with speed
					train.update();
				}
				//always increase speed by 1
				//train.backIndex++;
			}
			//How long the ride is running
			runningTicks++;
			//}
			TICKS++;
		}

	}

}
