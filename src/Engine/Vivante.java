package Engine;

public interface Vivante extends Entite {

	public static int maxPV = 10;

	void mouvement(PointCardinal p);

	public int getEquipe();

	public boolean memeEquipe(Vivante ent);

	public void isHit();

	public boolean estEnVie();

}