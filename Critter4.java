/* CRITTERS Critter4.java
 * EE422C Project 5 submission by
 * Jason Fang
 * jhf649
 * 16238
 * Cejay Zhu
 * cz4723
 * 16238
 * Slip days used: 0
 * Fall 2016
 */

/**Critter4 always tries to reproduce and always tries to fight*/

package assignment5;

public class Critter4 extends Critter {
	@Override
	public String toString() {
		return "4";
	}

	@Override
	public void doTimeStep() {
		// TODO Auto-generated method stub
		reproduce(new Critter4(), Critter.getRandomInt(8));
	}

	@Override
	public boolean fight(String oponent) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public CritterShape viewShape() {
		// TODO Auto-generated method stub
		return assignment5.Critter.CritterShape.DIAMOND;
	}
	@Override
	public javafx.scene.paint.Color viewColor() {
		return javafx.scene.paint.Color.ORANGE;
	}

}