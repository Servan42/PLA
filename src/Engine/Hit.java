package Engine;

import Exception.PanicException;
import Visual.OperateursVisual;
import javafx.scene.Parent;
import Visual.*;

/**
 * Classe de l'operateur Hit. Lorsque un robot rencontre un ennemi il le frappe.
 */
public class Hit implements Operateurs {

	private int x, y;
	Plateau plateau;
	OperateursVisual visuel;

	/**
	 * Constructeur de hit
	 * 
	 * @disclamer not sure of this constructor
	 */
	public Hit() {
		x = 0;
		y = 0;
	}

	/**
	 * Constructeur de hit
	 */
	public Hit(Terrain t, int x, int y, Plateau plateau, OperateursVisual visuel) {
		this.x = x;
		this.y = y;
		this.plateau = plateau;
		this.visuel = visuel;
		plateau.put(x, y, this);
		t.addVisual(visuel);
	}

	/**
	 * Getter de x
	 * 
	 * @return x;
	 */
	public int getX() {
		return x;
	}

	/**
	 * Getter de y
	 * 
	 * @return y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Ajoute l'operateur a l'inventaire du personnage et le retire du plateau
	 * 
	 * @since Version 1.0
	 */
	public void stock(Personnages p) {
		p.addOperator('H');
		plateau.remove(x, y, this);
		visuel.remove();
	}

	/**
	 * Methode qui fait executer l'action Hit à un robot.
	 * 
	 * @param nono
	 *            Robot qui va executer l'action.
	 */
	public void action(Robots nono) {
		if(isPossible(nono))
		nono.hit();
	}

	public String toString() {
		return "H";
	}

	/**
	 * Methode qui teste si l'action est possible ou efficace a un moment donné.
	 * 
	 * @param nono
	 *            Robot qui doit executer l'action.
	 * @return true si l'action est possible false sinon.
	 */
	public boolean isPossible(Robots nono) {
		return (nono.ennemiAdjacent() != PointCardinal.NONE);
	}

	/**
	 * Getter de visuel
	 */
	public Parent getVisual() {
		return visuel;
	}

}
