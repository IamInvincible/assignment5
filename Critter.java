package assignment5;

import java.util.List;

import assignment5.InvalidCritterException;
import assignment5.Critter;
import assignment5.Params;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class Critter {
	/* NEW FOR PROJECT 5 */
	public enum CritterShape {
		CIRCLE, SQUARE, TRIANGLE, DIAMOND, STAR
	}

	/*
	 * the default color is white, which I hope makes critters invisible by
	 * default If you change the background color of your View component, then
	 * update the default color to be the same as you background
	 * 
	 * critters must override at least one of the following three methods, it is
	 * not proper for critters to remain invisible in the view
	 * 
	 * If a critter only overrides the outline color, then it will look like a
	 * non-filled shape, at least, that's the intent. You can edit these default
	 * methods however you need to, but please preserve that intent as you
	 * implement them.
	 */
	public javafx.scene.paint.Color viewColor() {
		return javafx.scene.paint.Color.WHITE;
	}

	public javafx.scene.paint.Color viewOutlineColor() {
		return viewColor();
	}

	public javafx.scene.paint.Color viewFillColor() {
		return viewColor();
	}

	public abstract CritterShape viewShape();

	private static String myPackage;
	private static List<Critter> population = new ArrayList<Critter>();
	private static List<Critter> populationForLook = new ArrayList<Critter>();
	private static List<Critter> babies = new ArrayList<Critter>();
	private static List<String> critterTypes = new ArrayList<String>();
	private static int[][] worldMap1 = new int[Params.world_height][Params.world_width];
	private static List<Critter>[][] worldMap2 = new ArrayList[Params.world_height][Params.world_width];
	private static Critter[][] worldMap3 = new Critter[Params.world_height][Params.world_width];

	public String toString() {
		return "";
	}

	private int energy = 0;

	protected int getEnergy() {
		return energy;
	}

	private int x_coord;
	private int y_coord;
	private boolean hasMoved;
	private boolean isFighting;
	private boolean isFleeing;
	private int critterId;
	private static int idGenerator = 0;
	// Gets the package name. This assumes that Critter and its subclasses are
	// all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}

	protected final String look(int direction, boolean steps) {
		energy -= Params.look_energy_cost;
		if (energy <= 0) {
			worldMap1[y_coord][x_coord] -= 1;
			Iterator<Critter> iter = worldMap2[y_coord][x_coord].iterator();
			while (iter.hasNext()) {
				Critter c = iter.next();
				if (this.critterId == c.critterId) {
					iter.remove();
				}
			}
		}
		// invalid direction
		if ((direction < 0) || (direction > 7)) {
			return null;
		}

		// convert steps into corresponding number of spaces
		int stepsToSpaces;
		if (!steps) {
			stepsToSpaces = 1;
		} else {
			stepsToSpaces = 2;
		}

		// look for Critter occupying the space
		Iterator<Critter> itr;
		if (!isFighting) {
			itr = populationForLook.iterator();
		} else {
			itr = population.iterator();
		}

		switch (direction) {
		case 0:
			while (itr.hasNext()) {
				Critter temp = itr.next();
				if (temp.x_coord == this.x_coord + stepsToSpaces) {
					return temp.toString();
				}
			}
			break;
		case 1:
			while (itr.hasNext()) {
				Critter temp = itr.next();
				if ((temp.x_coord == this.x_coord + stepsToSpaces) && (temp.y_coord == this.y_coord - stepsToSpaces)) {
					return temp.toString();
				}
			}
			break;
		case 2:
			while (itr.hasNext()) {
				Critter temp = itr.next();
				if (temp.y_coord == this.y_coord - stepsToSpaces) {
					return temp.toString();
				}
			}
			break;
		case 3:
			while (itr.hasNext()) {
				Critter temp = itr.next();
				if ((temp.x_coord == this.x_coord - stepsToSpaces) && (temp.y_coord == this.y_coord - stepsToSpaces)) {
					return temp.toString();
				}
			}
			break;
		case 4:
			while (itr.hasNext()) {
				Critter temp = itr.next();
				if (temp.x_coord == this.x_coord - stepsToSpaces) {
					return temp.toString();
				}
			}
			break;
		case 5:
			while (itr.hasNext()) {
				Critter temp = itr.next();
				if ((temp.x_coord == this.x_coord - stepsToSpaces) && (temp.y_coord == this.y_coord + stepsToSpaces)) {
					return temp.toString();
				}
			}
			break;
		case 6:
			while (itr.hasNext()) {
				Critter temp = itr.next();
				if (temp.y_coord == this.y_coord + stepsToSpaces) {
					return temp.toString();
				}
			}
			break;
		case 7:
			while (itr.hasNext()) {
				Critter temp = itr.next();
				if ((temp.x_coord == this.x_coord + stepsToSpaces) && (temp.y_coord == this.y_coord + stepsToSpaces)) {
					return temp.toString();
				}
			}
			break;
		}
		return null;
	}

	/* rest is unchanged from Project 4 */

	private static java.util.Random rand = new java.util.Random();

	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}

	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}

	/*
	 * a one-character long string that visually depicts your critter in the
	 * ASCII interface
	 */

	protected final void walk(int direction) {
		energy = energy - Params.walk_energy_cost;
		if (energy <= 0) {
			worldMap1[y_coord][x_coord] -= 1;
			Iterator<Critter> iter = worldMap2[y_coord][x_coord].iterator();
			while (iter.hasNext()) {
				Critter r = iter.next();
				if (r.critterId == this.critterId) {
					iter.remove();
				}
			}
			return;
		}
		int orig_x_coord = x_coord;
		int orig_y_coord = y_coord;
		if (hasMoved == false) {
			switch (direction) {
			case 0:
				x_coord = (x_coord + 1) % (Params.world_width);
				break;
			case 1:
				x_coord = (x_coord + 1) % (Params.world_width);
				y_coord = (y_coord + Params.world_height - 1) % (Params.world_height);
				break;
			case 2:
				y_coord = (y_coord + Params.world_height - 1) % (Params.world_height);
				break;
			case 3:
				x_coord = (x_coord + Params.world_width - 1) % (Params.world_width);
				y_coord = (y_coord + Params.world_height - 1) % (Params.world_height);
				break;
			case 4:
				x_coord = (x_coord + Params.world_width - 1) % (Params.world_width);
				break;
			case 5:
				x_coord = (x_coord + Params.world_width - 1) % (Params.world_width);
				y_coord = (y_coord + 1) % (Params.world_height);
				break;
			case 6:
				y_coord = (y_coord + 1) % (Params.world_height);
				break;
			case 7:
				x_coord = (x_coord + 1) % (Params.world_width);
				y_coord = (y_coord + 1) % (Params.world_height);
				break;
			}
		}
		if (isFleeing && (worldMap1[y_coord][x_coord] > 0)) {
			x_coord = orig_x_coord;
			y_coord = orig_y_coord;
		}
		worldMap1[orig_y_coord][orig_x_coord] -= 1;
		Iterator<Critter> iter = worldMap2[orig_y_coord][orig_x_coord].iterator();
		while (iter.hasNext()) {
			Critter c = iter.next();
			if (c.critterId == this.critterId) {
				iter.remove();
			}
		}
		worldMap1[y_coord][x_coord] += 1;
		if (worldMap2[y_coord][x_coord] == null) {
			worldMap2[y_coord][x_coord] = new java.util.ArrayList<Critter>();
		}
		worldMap2[y_coord][x_coord].add(this);
		hasMoved = true;
	}

	protected final void run(int direction) {
		energy = energy - Params.run_energy_cost;
		if (energy <= 0) {
			worldMap1[y_coord][x_coord] -= 1;
			Iterator<Critter> iter = worldMap2[y_coord][x_coord].iterator();
			while (iter.hasNext()) {
				Critter r = iter.next();
				if (r.critterId == this.critterId) {
					iter.remove();
				}
			}
			return;
		}
		int orig_x_coord = x_coord;
		int orig_y_coord = y_coord;
		if (hasMoved == false) {
			switch (direction) {
			case 0:
				x_coord = (x_coord + 2) % (Params.world_width);
				break;
			case 1:
				x_coord = (x_coord + 2) % (Params.world_width);
				y_coord = (y_coord + Params.world_height - 2) % (Params.world_height);
				break;
			case 2:
				y_coord = (y_coord + Params.world_height - 2) % (Params.world_height);
				break;
			case 3:
				x_coord = (x_coord + Params.world_width - 2) % (Params.world_width);
				y_coord = (y_coord + Params.world_height - 2) % (Params.world_height);
				break;
			case 4:
				x_coord = (x_coord + Params.world_width - 2) % (Params.world_width);
				break;
			case 5:
				x_coord = (x_coord + Params.world_width - 2) % (Params.world_width);
				y_coord = (y_coord + 2) % (Params.world_height);
				break;
			case 6:
				y_coord = (y_coord + 2) % (Params.world_height);
				break;
			case 7:
				x_coord = (x_coord + 2) % (Params.world_width);
				y_coord = (y_coord + 2) % (Params.world_height);
				break;
			}
		}
		if (isFleeing && (worldMap1[y_coord][x_coord] > 0)) {
			x_coord = orig_x_coord;
			y_coord = orig_y_coord;
		}
		worldMap1[orig_y_coord][orig_x_coord] -= 1;
		Iterator<Critter> iter = worldMap2[orig_y_coord][orig_x_coord].iterator();
		while (iter.hasNext()) {
			Critter c = iter.next();
			if (c.critterId == this.critterId) {
				iter.remove();
			}
		}
		worldMap1[y_coord][x_coord] += 1;
		if (worldMap2[y_coord][x_coord] == null) {
			worldMap2[y_coord][x_coord] = new java.util.ArrayList<Critter>();
		}
		worldMap2[y_coord][x_coord].add(this);
		hasMoved = true;
	}

	protected final void reproduce(Critter offspring, int direction) {
		if (energy < Params.min_reproduce_energy) {
			return;
		}
		int energyCopy = energy;
		energy = (energy + 1) / 2;
		offspring.energy = energyCopy / 2;
		offspring.x_coord = x_coord;
		offspring.y_coord = y_coord;
		switch (direction) {
		case 0:
			offspring.x_coord = (offspring.x_coord + 1) % (Params.world_width);
			break;
		case 1:
			offspring.x_coord = (offspring.x_coord + 1) % (Params.world_width);
			offspring.y_coord = (offspring.y_coord + Params.world_height - 1) % (Params.world_height);
			break;
		case 2:
			offspring.y_coord = (offspring.y_coord + Params.world_height - 1) % (Params.world_height);
			break;
		case 3:
			offspring.x_coord = (offspring.x_coord + Params.world_width - 1) % (Params.world_width);
			offspring.y_coord = (offspring.y_coord + Params.world_height - 1) % (Params.world_height);
			break;
		case 4:
			offspring.x_coord = (offspring.x_coord + Params.world_width - 1) % (Params.world_width);
			break;
		case 5:
			offspring.x_coord = (offspring.x_coord + Params.world_width - 1) % (Params.world_width);
			offspring.y_coord = (offspring.y_coord + 1) % (Params.world_height);
			break;
		case 6:
			offspring.y_coord = (y_coord + 1) % (Params.world_height);
			break;
		case 7:
			offspring.x_coord = (x_coord + 1) % (Params.world_width);
			offspring.y_coord = (y_coord + 1) % (Params.world_height);
			break;
		}
		offspring.critterId = idGenerator;
		idGenerator++;
		babies.add(offspring);
	}

	public abstract void doTimeStep();

	public abstract boolean fight(String oponent);

	public static void worldTimeStep() {
		// Complete this method.

		// Make a copy of the world for the look function
		Iterator<Critter> itr = population.iterator();
		while (itr.hasNext()) {
			populationForLook.add(itr.next());
		}

		itr = population.iterator();
		while (itr.hasNext()) {
			Critter c = (Critter) itr.next();
			c.hasMoved = false;
			c.isFleeing = false;
			c.isFighting = false;
			c.doTimeStep();
			c.isFleeing = true;
		}
		itr = population.iterator();
		while (itr.hasNext()) {
			Critter c = (Critter) itr.next();
			if (c.energy <= 0) {
				itr.remove();
			}
		}
		// resolve encounters by searching worldMap1 for places where
		// 2 or more Critters occupy the same area
		for (int i = 0; i < Params.world_height; i++) {
			for (int j = 0; j < Params.world_width; j++) {
				while (worldMap1[i][j] > 1) {
					Critter a = worldMap2[i][j].get(0);
					Critter b = worldMap2[i][j].get(1);
					a.isFighting = true;
					boolean aFights = a.fight(b.toString());
					a.isFighting = false;
					b.isFighting = true;
					boolean bFights = b.fight(a.toString());
					b.isFighting = false;
					int aRoll = 0;
					int bRoll = 0;
					if (a.energy > 0 && b.energy > 0 && a.x_coord == b.x_coord && a.y_coord == b.y_coord) {
						if (aFights) {
							aRoll = Critter.getRandomInt(a.energy);
						} else {
							aRoll = 0;
						}
						if (bFights) {
							bRoll = Critter.getRandomInt(b.energy);
						} else {
							bRoll = 0;
						}
						if (aRoll >= bRoll) {
							Iterator<Critter> it = worldMap2[a.y_coord][a.x_coord].iterator();
							while (it.hasNext()) {
								Critter z = it.next();
								if (z.critterId == a.critterId) {
									z.energy = z.energy + (b.energy / 2);
								}
							}
							itr = population.iterator();
							while (itr.hasNext()) {
								Critter c = (Critter) itr.next();
								if (c.critterId == b.critterId) {
									itr.remove();
								}
							}
							worldMap1[b.y_coord][b.x_coord] -= 1;
							Iterator<Critter> iter = worldMap2[b.y_coord][b.x_coord].iterator();
							while (iter.hasNext()) {
								Critter r = iter.next();
								if (r.critterId == b.critterId) {
									iter.remove();
								}
							}
						} else {
							Iterator<Critter> it = worldMap2[b.y_coord][b.x_coord].iterator();
							while (it.hasNext()) {
								Critter z = it.next();
								if (z.critterId == b.critterId) {
									z.energy = z.energy + (a.energy / 2);
								}
							}
							itr = population.iterator();
							while (itr.hasNext()) {
								Critter c = (Critter) itr.next();
								if (c.critterId == a.critterId) {
									itr.remove();
								}
							}
							worldMap1[a.y_coord][a.x_coord] -= 1;
							Iterator<Critter> iter = worldMap2[a.y_coord][a.x_coord].iterator();
							while (iter.hasNext()) {
								Critter r = iter.next();
								if (r.critterId == a.critterId) {
									iter.remove();
								}
							}
						}
					}
				}
			}
		}
		// subtract rest energy
		itr = population.iterator();
		while (itr.hasNext()) {
			Critter c = (Critter) itr.next();
			c.energy = c.energy - Params.rest_energy_cost;
			if (c.energy <= 0) {
				itr.remove();
				worldMap1[c.y_coord][c.x_coord] -= 1;
				Iterator<Critter> iter = worldMap2[c.y_coord][c.x_coord].iterator();
				while (iter.hasNext()) {
					Critter r = iter.next();
					if (r.critterId == c.critterId) {
						iter.remove();
					}
				}
			}
		}
		// add babies to population
		Iterator<Critter> babyIter = babies.iterator();
		while (babyIter.hasNext()) {
			Critter b = babyIter.next();
			babyIter.remove();
			population.add(b);
			worldMap1[b.y_coord][b.x_coord] += 1;
			if (worldMap2[b.y_coord][b.x_coord] == null) {
				worldMap2[b.y_coord][b.x_coord] = new java.util.ArrayList<Critter>();
			}
			worldMap2[b.y_coord][b.x_coord].add(b);
		}
		// make Algae
		for (int i = 0; i < Params.refresh_algae_count; i++) {
			try {
				makeCritter("Algae");
			} catch (InvalidCritterException e) {
				System.out.println("Invalid Critter Exception: Algae not created");
			}
		}
	}

	public static void displayWorld(Object pane) {
		// draw world
		int canvas_width = 750;
		int canvas_height = 500;
		
		int grid_width = 0;
		int grid_height = 0;
		
		int width_multiplier = canvas_width/Params.world_width;
		int height_multiplier = canvas_width/Params.world_height;
		
		if(width_multiplier > height_multiplier){
			grid_width = grid_height = height_multiplier;
		} else {
			grid_width = grid_height = width_multiplier;
		}

		//grid_width = grid_height = 25;
		Canvas canvas = new Canvas(canvas_width, canvas_height);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, canvas_width, canvas_height);

		// iterate through population and draw each Critter on the world
		int x = 0;
		int y = 0;


		Iterator<Critter> itr = population.iterator();
		while (itr.hasNext()) {
			Critter c = itr.next();
			// change everything between later
			gc.setFill(Color.BLUE);
			gc.fillOval(x, y, grid_width, grid_height);
			if (x + grid_width < canvas_width) {
				x += grid_width;
			} else {
				x = 0;
				y += grid_height;
			}
			// change everything between later
		}
		((BorderPane) pane).setCenter(canvas);

		/*
		 * double[] x = {100,150,50}; double[] y = {100,150,150};
		 * gc.setFill(Color.WHITE); gc.fillOval(0, 0, 100, 100); //make triangle
		 * gc.fillPolygon(x,y, 3); // length,heightProperty
		 */
	}
	/*
	 * Alternate displayWorld, where you use Main.<pane> to reach into your
	 * display component. // public static void displayWorld() {}
	 */

	/*
	 * create and initialize a Critter subclass critter_class_name must be the
	 * name of a concrete subclass of Critter, if not an InvalidCritterException
	 * must be thrown
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {

		try {
			String qualified_critter_class_name = myPackage + "." + critter_class_name;
			Class<?> c = Class.forName(qualified_critter_class_name);
			boolean validCritter = Critter.class.isAssignableFrom(c);
			if (!validCritter) {
				throw new InvalidCritterException(critter_class_name);
			}
			Critter newCritter = (Critter) c.newInstance();
			newCritter.x_coord = rand.nextInt(Params.world_width);
			newCritter.y_coord = rand.nextInt(Params.world_height);
			newCritter.critterId = idGenerator;
			idGenerator++;
			worldMap1[newCritter.y_coord][newCritter.x_coord] += 1;
			if (worldMap2[newCritter.y_coord][newCritter.x_coord] == null) {
				worldMap2[newCritter.y_coord][newCritter.x_coord] = new java.util.ArrayList<Critter>();
			}
			worldMap2[newCritter.y_coord][newCritter.x_coord].add(newCritter);
			newCritter.energy = Params.start_energy;
			population.add(newCritter);

			if (critterTypes.isEmpty()) {
				critterTypes.add(critter_class_name);
			} else {
				Iterator<String> iterator = critterTypes.iterator();
				boolean repeat = false;
				while (iterator.hasNext()) {
					String critterType = iterator.next();
					if (critterType.equals(critter_class_name)) {
						repeat = true;
					}
				}
				if(!repeat){
					critterTypes.add(critter_class_name);
				}
			}
		} catch (ClassNotFoundException e) {
			throw new InvalidCritterException(critter_class_name);
		} catch (InstantiationException e) {
			throw new InvalidCritterException(critter_class_name);
		} catch (IllegalAccessException e) {
			throw new InvalidCritterException(critter_class_name);
		}
	}

	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		try {
			String qualified_critter_class_name = myPackage + "." + critter_class_name;
			Class<?> c = Class.forName(qualified_critter_class_name);
			boolean validCritter = Critter.class.isAssignableFrom(c);
			if (!validCritter) {
				throw new InvalidCritterException(critter_class_name);
			}
			Iterator<Critter> itr = population.iterator();
			while (itr.hasNext()) {
				Critter e = (Critter) itr.next();
				if (c.isInstance(e)) {
					result.add(e);
				}
			}
		} catch (ClassNotFoundException e) {
			throw new InvalidCritterException(critter_class_name);
		}
		return result;
	}

	/*
	 * public static void runStats(List<Critter> critters) { System.out.print(""
	 * + critters.size() + " critters as follows -- "); java.util.Map<String,
	 * Integer> critter_count = new java.util.HashMap<String, Integer>(); for
	 * (Critter crit : critters) { String crit_string = crit.toString(); Integer
	 * old_count = critter_count.get(crit_string); if (old_count == null) {
	 * critter_count.put(crit_string, 1); } else {
	 * critter_count.put(crit_string, old_count.intValue() + 1); } } String
	 * prefix = ""; for (String s : critter_count.keySet()) {
	 * System.out.print(prefix + s + ":" + critter_count.get(s)); prefix = ", ";
	 * } System.out.println(); }
	 */

	public static String runStats() {
		return "";

	}

	/*
	 * the TestCritter class allows some critters to "cheat". If you want to
	 * create tests of your Critter model, you can create subclasses of this
	 * class and then use the setter functions contained here.
	 * 
	 * NOTE: you must make sure that the setter functions work with your
	 * implementation of Critter. That means, if you're recording the positions
	 * of your critters using some sort of external grid or some other data
	 * structure in addition to the x_coord and y_coord functions, then you MUST
	 * update these setter functions so that they correct update your grid/data
	 * structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
			if (super.energy <= 0) {
				Iterator<Critter> itr = population.iterator();
				while (itr.hasNext()) {
					Critter c = (Critter) itr.next();
					if (c.critterId == super.critterId) {
						itr.remove();
					}
				}
				worldMap1[super.y_coord][super.x_coord] -= 1;
				Iterator<Critter> iter = worldMap2[super.y_coord][super.x_coord].iterator();
				while (iter.hasNext()) {
					Critter r = iter.next();
					if (r.critterId == super.critterId) {
						iter.remove();
					}
				}
			}
		}

		protected void setX_coord(int new_x_coord) {
			worldMap1[super.y_coord][super.x_coord] -= 1;
			Iterator<Critter> iter = worldMap2[super.y_coord][super.x_coord].iterator();
			while (iter.hasNext()) {
				Critter c = iter.next();
				if (c.critterId == super.critterId) {
					iter.remove();
				}
			}
			super.x_coord = new_x_coord;
			worldMap1[super.y_coord][super.x_coord] += 1;
			if (worldMap2[super.y_coord][super.x_coord] == null) {
				worldMap2[super.y_coord][super.x_coord] = new java.util.ArrayList<Critter>();
			}
			worldMap2[super.y_coord][super.x_coord].add(this);

		}

		protected void setY_coord(int new_y_coord) {
			worldMap1[super.y_coord][super.x_coord] -= 1;
			Iterator<Critter> iter = worldMap2[super.y_coord][super.x_coord].iterator();
			while (iter.hasNext()) {
				Critter c = iter.next();
				if (c.critterId == super.critterId) {
					iter.remove();
				}
			}
			super.y_coord = new_y_coord;
			worldMap1[super.y_coord][super.x_coord] += 1;
			if (worldMap2[super.y_coord][super.x_coord] == null) {
				worldMap2[super.y_coord][super.x_coord] = new java.util.ArrayList<Critter>();
			}
			worldMap2[super.y_coord][super.x_coord].add(this);
		}

		protected int getX_coord() {
			return super.x_coord;
		}

		protected int getY_coord() {
			return super.y_coord;
		}

		/*
		 * This method getPopulation has to be modified by you if you are not
		 * using the population ArrayList that has been provided in the starter
		 * code. In any case, it has to be implemented for grading tests to
		 * work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}

		/*
		 * This method getBabies has to be modified by you if you are not using
		 * the babies ArrayList that has been provided in the starter code. In
		 * any case, it has to be implemented for grading tests to work. Babies
		 * should be added to the general population at either the beginning OR
		 * the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		population.clear();
		for (int i = 0; i < Params.world_height; i++) {
			for (int j = 0; j < Params.world_width; j++) {
				worldMap1[i][j] = 0;
			}
		}
		for (int i = 0; i < Params.world_height; i++) {
			for (int j = 0; j < Params.world_width; j++) {
				worldMap2[i][j] = null;
			}
		}
	}

}
