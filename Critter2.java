/* CRITTERS Critter2.java
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

/**Critter 2 never moves and always tries to fight. It calls look when it tries to fight*/

package assignment5;

public class Critter2 extends Critter{
	@Override
	public String toString() {
		return "2";
	}
	@Override
	public void doTimeStep() {
		// TODO Auto-generated method stub
	}
	@Override
	public boolean fight(String oponent) {
		// TODO Auto-generated method stub
		this.look(Critter.getRandomInt(8), false);
		return true;
	}
	@Override
	public CritterShape viewShape() {
		// TODO Auto-generated method stub
		return assignment5.Critter.CritterShape.SQUARE;
	}
	@Override
	public javafx.scene.paint.Color viewColor() {
		return javafx.scene.paint.Color.RED;
	}
}