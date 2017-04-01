/* CRITTERS Critter1.java
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

/**Critter 1 calls look within doTimeStep and only walks in random directions and always tries to fight*/

package assignment5;

public class Critter1 extends Critter {
	@Override
	public String toString() {
		return "1";
	}
	@Override
	public void doTimeStep() {
		// TODO Auto-generated method stub
		//call reproduce here or in fight()
		this.look(Critter.getRandomInt(8), false);
		this.walk(Critter.getRandomInt(8));
	}

	@Override
	public boolean fight(String oponent) {
		// TODO Auto-generated method stub
		//call reproduce here or in doTimeStep()
		return true;
	}
	@Override
	public CritterShape viewShape() {
		// TODO Auto-generated method stub
		return assignment5.Critter.CritterShape.SQUARE;
	}
	@Override
	public javafx.scene.paint.Color viewColor() {
		return javafx.scene.paint.Color.BLUE;
	}
}