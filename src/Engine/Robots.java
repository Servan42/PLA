package Engine;

public class Robots implements Vivante {

	Automates behavior;
	int x, y;

	public String toString() {
		return "R(" + x + "," + y + ") : " + behavior.toString();
	}

	@Override
	public void detruire() {
		// TODO Auto-generated method stub

	}

	@Override
	public void apparaitre() {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouvement(PointCardinal p, int nb) {
		// TODO Auto-generated method stub

	}

	/**
	 * Simple getter de coordonée
	 */
	public int getX() {
		return x;
	}

	/**
	 * Simple getter de coordonée
	 */
	public int getY() {
		return y;
	}
}
