package Engine;

import java.util.HashMap;

import Exception.PanicException;
import Parsing.ParseException;
import Visual.Barre;
import Visual.Boite;
import Visual.FinalScreen;
import Visual.PersonnagesVisual;
import Visual.Plateau;
import Visual.RobotVisual;
import Visual.Terrain;
import Visual.Test;
import Visual.Tuile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Classe representant un personnage, avec des coordonees, un inventaire
 * d'objet, et un inventaire de robots.
 * 
 * @version Version 1.0
 */
public class Personnages implements Vivante {
	private HashMap<Character, Integer> Inventory = new HashMap<>();
	private Robots Units[] = new Robots[3];
	private int x, y, numberRobots;
	int equipe;
	int PV = maxPV;
	Plateau plateau;
	PersonnagesVisual visuel;
	private boolean base;
	Terrain t;
	private Rectangle healthbar;
	private ImageView healthbarBG;

	/**
	 * Contructeur de personnage de l'équipe e
	 * 
	 * @param e
	 *            l'equipe dans laquelle ajouter le personnage
	 * @require e == 0 || e == 1
	 */
	public Personnages(Terrain t, int e, PersonnagesVisual visuel) {
		this.t = t;
		this.plateau = t.getPlateau();
		this.base = false;
		if (e == 0) {
			x = 0;
			y = plateau.nbLignes() / 2;
		} else if (e == 1) {
			x = plateau.nbColonnes() - 1;
			y = plateau.nbLignes() / 2;
		} else
			throw new PanicException("Numéro d'équipe incorrect");
		equipe = e;
		numberRobots = 0;
		initInventory();
		this.visuel = visuel;
		plateau.put(x, y, this);
		healthbar = new Rectangle(10 * 0.49 * Tuile.getTaille(), 0.5 * Tuile.getTaille(), Color.GREEN);
		healthbarBG = new ImageView(new Image(FinalScreen.class.getResourceAsStream("images/Empty_hb.png")));
		healthbarBG.setFitWidth((10 * 0.49 * Tuile.getTaille()) + (Tuile.getTaille() / 5));
		healthbarBG.setFitHeight((0.5 * Tuile.getTaille() + (Tuile.getTaille() / 5)));
		healthbar.setY(15 * Tuile.getTaille());
		healthbarBG.setTranslateY((15 * Tuile.getTaille()) - (Tuile.getTaille() / 10));
		if (e == 0) {
			healthbar.setX(0.5 * Tuile.getTaille());
			healthbarBG.setTranslateX((0.5 * Tuile.getTaille()) - (Tuile.getTaille() / 10));
		} else {
			healthbar.setX(27.43 * Tuile.getTaille());
			healthbarBG.setTranslateX((27.43 * Tuile.getTaille()) - (Tuile.getTaille() / 10));
		}
		// Image = dimensions + taille tuile / 5
	}

	/**
	 * Methode qui initialise l'inventaire d'operateurs
	 * 
	 * @since Version 1.0
	 */
	private void initInventory() {
		Inventory.clear();
		Inventory.put('*', 2); // Loop
		Inventory.put('>', 0); // Preference
		Inventory.put('S', 0); // Split
		Inventory.put('H', 0); // Hit
		Inventory.put('E', 0); // Escape
		Inventory.put('K', 0); // Kamikaze
		Inventory.put('P', 0); // Protect
		Inventory.put('F', 0); // Follow
		Inventory.put(':', 0); // Repeat
		Inventory.put('J', 0); // Rapporte
		Inventory.put('X', 0); // Explore
		Inventory.put('C', 0); // Closest
		Inventory.put('B', 0); // Best
		Inventory.put('W', 0); // Where
		Inventory.put('O', 0); // Others
		Inventory.put('{', 2); // AccoladeO
		Inventory.put('}', 2); // AccoladeO
		Inventory.put(';', 0); // And
		Inventory.put('|', 0); // Or
	}

	/**
	 * Getter de position x.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Getter de postion y.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Methode permettant d'incrementer le nombre d'un operateur connu par
	 * l'inventaire.
	 * 
	 * @param op
	 *            Operateur dont le nombre est � augmenter.
	 * @require op est un op�rateur connu de l'inventaire.
	 * @since version 1.0
	 */
	public void addOperator(char op) {
		if (!(Inventory.containsKey(op))) {
			throw new PanicException("Ajout d'objet a l'inventaire du personnage : Objet inconnu");
		}
		Inventory.put(op, Inventory.get(op) + 1);
	}

	/**
	 * Methode permettant de décrementer le nombre d'un operateur connu par
	 * l'inventaire.
	 * 
	 * @param op
	 *            Operateur dont le nombre est à décrementer.
	 * @require op est un opérateur connu de l'inventaire.
	 */
	public void removeOperator(char op) {
		/*
		 * if (!(Inventory.containsKey(op))) { throw new
		 * PanicException("Suppression d'objet de l'inventaire du personnage : Objet inconnu"
		 * ); } if (Inventory.get(op) == 0) { throw new PanicException(
		 * "Suppression d'objet de l'inventaire du personnage : Il y a dja 0 objets de ce type dans l'inventaire."
		 * ); }
		 */
		if (!isEmpty(op)) {
			Inventory.put(op, Inventory.get(op) - 1);
		}
	}

	public boolean isEmpty(char op) {
		if (Inventory.get(op) == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Methode qui permet l'ajout d'un robots a l'equipe du personnage.
	 * 
	 * @param robot
	 *            Robot a ajouter a l'inventaire de robot du personnage.
	 * @param room
	 *            Case dans laquelle mettre le robot.
	 * @require room comprit entre 1 et 3. Pas plus de 3 robots dans
	 *          l'inventaire. room ne contient pas deja un robot.
	 * @since Version 1.0
	 */
	public void addRobot(Robots robot, int room) {
		int indexUnit = room - 1;
		if (numberRobots > 3) {
			throw new PanicException("Ajout d'un robot au personnage : Limite atteinte.");
		}
		if (indexUnit >= 3 || indexUnit < 0) {
			throw new PanicException("Ajout d'un robot au personnage : Case invalide");
		}
		if (Units[indexUnit] != null) {
			throw new PanicException("Ajout d'un robot au personnage : Case occupee");
		}
		Units[indexUnit] = robot;
		numberRobots++;
	}

	public void addRobot(String behave) {
		if (numberRobots > 3) {
			throw new PanicException("Ajout d'un robot au personnage : Limite atteinte.");
		}
		try {
			Robots robot = new Robots(t, this, getEquipe(), behave);
			Units[numberRobots] = robot;
			numberRobots++;
		} catch (ParseException e) {

		}
	}

	/**
	 * Methode qui permet l'ajout d'un robots a l'equipe du personnage.
	 * 
	 * @param behavior
	 *            le comportement du Robot a ajouter a l'inventaire du
	 *            personnage.
	 * @param room
	 *            case dans laquelle mettre le robot.
	 * @require room comprit entre 1 et 3
	 * 
	 * @since Version 3.0
	 */
	public void addRobot(String behavior, int room) {
		int indexUnit = room - 1;
		try {
			Robots robot = new Robots(t, this, equipe, behavior);
			Units[indexUnit] = robot;
			numberRobots++;
		} catch (Exception ex) {
			throw new PanicException("Automate d'ajout du robot incorrect");
		}
	}

	/**
	 * Methode qui permet la suppression d'un robots � l'�quipe du personnage.
	 * 
	 * @param room
	 *            case ou se trouve le robot � supprimer
	 * @require L'equipe n'est pas vide. La case est comprise entre 1 et 3.
	 * @since Version 1.0
	 */
	public void removeRobot(int room) {
		int indexUnit = room - 1;
		if (numberRobots == 0) {
			throw new PanicException("Suppression d'un robot au personnage : Equipe deja vide.");
		}
		if (indexUnit >= 3 || indexUnit < 0) {
			throw new PanicException("Suppression d'un robot au personnage : Case invalide");
		}
		Units[indexUnit] = null;
		numberRobots--;
	}

	@Override
	/**
	 * Methode qui permet de faire avancer le personnage d'un certain pas dans
	 * une certaine direction.
	 * 
	 * @param p
	 *            Direction dans laquelle on avance : NORD, SUD, EST, OUEST.
	 * @param nb
	 *            Distance/Pas duquel on avance.
	 * @since Version 1.0
	 */
	public void mouvement(PointCardinal p) {
//		plateau.toString();
		switch (p) {
		case NORD:
			if (y > 0 && !(plateau.unsafeGet(x, y - 1) instanceof Vivante)) {
				if (plateau.unsafeGet(x, y - 1) instanceof Operateurs)
					((Operateurs) plateau.unsafeGet(x, y - 1)).stock(this);
				y--;
				plateau.move(x, y + 1, x, y);
				visuel.Haut();
				visuel.requestFocus();
			}
			break;
		case SUD:
			if (y < plateau.nbLignes() - 1 && !(plateau.unsafeGet(x, y + 1) instanceof Vivante)) {
				if (plateau.unsafeGet(x, y + 1) instanceof Operateurs)
					((Operateurs) plateau.unsafeGet(x, y + 1)).stock(this);
				y++;
				plateau.move(x, y - 1, x, y);
				visuel.Bas();
				visuel.requestFocus();
			}
			break;
		case EST:
			if (x < plateau.nbColonnes() - 1 && !(plateau.unsafeGet(x + 1, y) instanceof Vivante)) {
				if (plateau.unsafeGet(x + 1, y) instanceof Operateurs)
					((Operateurs) plateau.unsafeGet(x + 1, y)).stock(this);
				x++;
				plateau.move(x - 1, y, x, y);
				visuel.Droite();
				visuel.requestFocus();
			}
			break;
		case OUEST:
			if (x > 0 && !(plateau.unsafeGet(x - 1, y) instanceof Vivante)) {
				if (plateau.unsafeGet(x - 1, y) instanceof Operateurs)
					((Operateurs) plateau.unsafeGet(x - 1, y)).stock(this);
				x--;
				plateau.move(x + 1, y, x, y);
				visuel.Gauche();
				visuel.requestFocus();
			}
			break;
		default:
			throw new PanicException("Deplacement Personnage : Point Cardinal incorrect.");
		}
	}

	/**
	 * Fonction de creation d'une chaine d'affichage de la classe Personnages.
	 * 
	 * @return La chaine de caracteres correspondant a l'affichage. Fonction
	 *         d'affichage de la classe Personnages.
	 * 
	 * @return La chaine de caract�re correspondant � l'affichage.
	 * 
	 * @since Version 1.0
	 */
	public String toString() {
		String retour = new String();
		retour = "Position : (" + x + "," + y + ")\n";
		retour += "Nombre de robots : " + numberRobots + "\n";
		retour += Units[0].toString() + " " + Units[2].toString() + " " + Units[2].toString() + "\n";
		retour += "Inventaire d'operateurs : \n";
		for (HashMap.Entry<Character, Integer> entry : Inventory.entrySet()) {
			retour = retour + entry.getKey().toString() + " " + entry.getValue().toString() + "\n";
		}
		return retour;
	}

	/**
	 * Renvoie le numero d'equipe du personnage
	 */
	public int getEquipe() {
		return equipe;
	}

	/**
	 * Indique si le personnage est de la meme equipe que la Vivante v
	 * 
	 * @param v
	 *            la vivante a tester
	 */
	public boolean memeEquipe(Vivante v) {
		return equipe == v.getEquipe();
	}

	/**
	 * On reçoit nbHits coup(s) d'un adversaire
	 * 
	 * @param nbHits
	 *            le nombre de coups reçus
	 */
	public void isHit() {
		PV--;
	}

	/**
	 * Accesseur de visuel associe au personnage
	 * 
	 * @return
	 */
	public PersonnagesVisual getVisual() {
		return visuel;
	}

	/**
	 * Renvoie l'un des robots associes au joueur, s'il existe
	 * 
	 * @param num
	 *            le numero du robot recerche
	 * @return le robot numero num associe au joueur, ou null s'il ne dispose
	 *         pas de robot numero num
	 * @require num == 1 || num == 2 || num == 3
	 */
	public Robots getRobot(int num) {
		try {
			return Units[num - 1];
		} catch (Exception e) {
			throw new PanicException("getRobots personnage equipe " + equipe + " mauvais indice");
		}
	}

	/**
	 * Getter de PV du personnage
	 * 
	 * @return le nombre de PV restants du personnages
	 */
	public int getHealth() {
		return PV;
	}

	/**
	 * Indique si le personnage dipose encoure de PV ou non
	 */
	public boolean estEnVie() {
		if (Test.getMode() == Test.TRIAL && PV <= 0)
			PV = 10;
		return PV > 0;
	}

	/**
	 * Indique si le personnage est dans sa base
	 */
	public boolean dansBase() {
		return base;
	}

	/**
	 * Fait rentrer le personnage dans sa base
	 */
	public void rentrer() {
		this.base = true;
	}

	/**
	 * Fait sortir le personnage de sa base
	 */
	public void sortir() {
		this.base = false;
	}

	public HashMap<Character, Integer> getInventory() {
		return Inventory;
	}

	public Rectangle getHealthBar() {
		return healthbar;
	}

	public ImageView getHealthBarBG() {
		return healthbarBG;
	}

	/**
	 * Met a jour la barre dev ies en fonction des PV
	 */
	public void updateHealthBar() {
		if (PV > 6)
			healthbar.setFill(Color.GREEN);
		if (PV <= 6)
			healthbar.setFill(Color.ORANGE);
		if (PV <= 3)
			healthbar.setFill(Color.RED);
		if (equipe == 0)
			healthbar.setWidth(PV * 0.49 * Tuile.getTaille());
		else {
			healthbar.setWidth(PV * 0.49 * Tuile.getTaille());
			healthbar.setX((27.43 * Tuile.getTaille()) + ((10 - PV) * 0.49 * Tuile.getTaille()));
		}
	}

}