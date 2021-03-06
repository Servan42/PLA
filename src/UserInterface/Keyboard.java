package UserInterface;

import Engine.*;
import Exception.PanicException;
import Visual.*;
import Visual.Barre;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

/**
 **************************************************************
 * Keyboard reagit a un evenement exterieur venant du clavier *
 **************************************************************
 */
public class Keyboard implements EventHandler<KeyEvent> {
	Personnages personnage1, personnage2;
	int ligne1 = 0; // Barre de selection de la boite du Player 1
	int ligne2 = 0; // Barre de selection de la boite du Player 2
	Group root;
	Boite boite1, boite2;
	Team team1, team2;
	Text expr_rouge, expr_bleue;
	int marge;
	Terrain terrain;

	private RobotVisual newRobot1, newRobot2;
	private String expression_courante;
	private String expression_rouge;
	private String expression_bleue;
	private String oldBehavior1, newBehavior1, oldBehavior2, newBehavior2;
	private int curseur;
	private int curseur_rouge = 0;
	private int curseur_bleu = 0;
	private int mate1 = 0; // selection des mates par les joueurs entre
	private int mate2 = 0; // -1 et 2. -1 => aucun mate selectionne

	public Keyboard(Terrain terrain, Group root, Text expr_bleue, Text expr_rouge, int marge, Boite boite1,
			Boite boite2, Team team1, Team team2) {
		this.terrain = terrain;
		this.personnage1 = terrain.getpersonnage1();
		this.personnage2 = terrain.getpersonnage2();
		this.root = root;
		this.expr_rouge = expr_rouge;
		this.expr_bleue = expr_bleue;
		this.marge = marge;
		this.boite1 = boite1;
		this.boite2 = boite2;
		this.team1 = team1;
		this.team2 = team2;
	}

	/**
	 * Recupere un evenement du clavier afin de l'associer au joueur
	 * correspondant Le joueur 1 se trouvant a droite du terrain, utilise les
	 * fleches pour deplacer son avatar. Le joueur 2, quant a lui, utilise les
	 * touches Q,Z,S,D pour se deplacer a l'interieur du terrain Lorsque le
	 * joueur 1 est dans sa base, la touche Q
	 */
	public void handle(KeyEvent event) {
		if (!(Test.getMode() == Test.TRIAL) && !Test.getMenu() && !Test.enPause()
				&& (event.getCode() == Test.getHaut2() || event.getCode() == Test.getBas2()
						|| event.getCode() == Test.getGauche2() || event.getCode() == Test.getDroite2()
						|| event.getCode() == Test.getChoix21() || event.getCode() == Test.getChoix22()
						|| event.getCode() == Test.getChoix23())) {
			// Player 2
			if (personnage2.dansBase()) {
				if (mate2 != -1) {
					if (event.getCode() == Test.getHaut2()) {
						root.getChildren().remove(boite2);
						boite2.invisible(ligne2);
						if (ligne2 == 0)
							ligne2 = 4;
						else
							ligne2--;
						boite2.visible(ligne2);
						root.getChildren().add(boite2);
					} else if (event.getCode() == Test.getBas2()) {
						root.getChildren().remove(boite2);
						boite2.invisible(ligne2);
						if (ligne2 == 4)
							ligne2 = 0;
						else
							ligne2++;
						boite2.visible(ligne2);
						root.getChildren().add(boite2);
					} else if (event.getCode() == Test.getGauche2()) {
						personnage2.sortir();
						newBehavior2 = expression_rouge.substring(0, curseur_rouge)
								+ expression_rouge.substring(curseur_rouge + 1, expression_rouge.length());
						if (personnage2.getRobot(mate2 + 1) == null) {
							try {
								personnage2.addRobot(newBehavior2, mate2 + 1);
								expression_rouge = "EXPRESSION";
							} catch (PanicException e) {
								for (int i = 0; i < expression_courante.length(); i++)
									updateCurseur(3);
								for (int i = 0; i < expression_courante.length(); i++)
									supprimeChar(personnage2);
								expression_rouge = "ERROR SYNTAXE";
							}
						} else {
							try {
								personnage2.getRobot(mate2 + 1).setBehavior(newBehavior2);
								expression_rouge = "EXPRESSION";
							} catch (PanicException e) {
								for (int i = 0; i < expression_courante.length(); i++)
									updateCurseur(3);
								for (int i = 0; i < expression_courante.length(); i++)
									supprimeChar(personnage2);
								expression_rouge = "ERROR SYNTAXE";
							}
						}
						root.getChildren().remove(boite2);
						boite2.invisible(ligne2);
						boite2 = new Boite(personnage2);
						root.getChildren().add(boite2);
						updateExpression_rouge();
						mate2 = -1;
						updateMates_rouges();
					} else if (event.getCode() == Test.getChoix21()) {
						root.getChildren().remove(boite2);
						getOperateur(ligne2, 1, personnage2, 2);
						updateExpression_rouge();
						boite2 = new Boite(personnage2);
						boite2.visible(ligne2);
						root.getChildren().add(boite2);
					} else if (event.getCode() == Test.getChoix22()) {
						root.getChildren().remove(boite2);
						getOperateur(ligne2, 2, personnage2, 2);
						updateExpression_rouge();
						boite2 = new Boite(personnage2);
						boite2.visible(ligne2);
						root.getChildren().add(boite2);
					} else if (event.getCode() == Test.getChoix23()) {
						root.getChildren().remove(boite2);
						getOperateur(ligne2, 3, personnage2, 2);
						updateExpression_rouge();
						boite2 = new Boite(personnage2);
						boite2.visible(ligne2);
						root.getChildren().add(boite2);
					}
				} else { // Selectionner un Robot
					if (event.getCode() == Test.getChoix21()) {
						if (personnage2.getRobot(1) != null) {
							expression_rouge = personnage2.getRobot(1).toString() + "I";
							curseur_rouge = expression_rouge.length() - 1;
						} else {
							expression_rouge = "I";
							curseur_rouge = 0;
						}
						updateExpression_rouge();
						mate2 = 0;
						updateMates_rouges();
					} else if (event.getCode() == Test.getChoix22()) {
						if (personnage2.getRobot(2) != null) {
							expression_rouge = personnage2.getRobot(2).toString() + "I";
							curseur_rouge = expression_rouge.length() - 1;
						} else {
							expression_rouge = "I";
							curseur_rouge = 0;
						}
						updateExpression_rouge();
						mate2 = 1;
						updateMates_rouges();
					} else if (event.getCode() == Test.getChoix23()) {
						if (personnage2.getRobot(3) != null) {
							expression_rouge = personnage2.getRobot(3).toString() + "I";
							curseur_rouge = expression_rouge.length() - 1;
						} else {
							expression_rouge = "I";
							curseur_rouge = 0;
						}
						updateExpression_rouge();
						mate2 = 2;
						updateMates_rouges();
					} else if (event.getCode() == Test.getGauche2()) {
						personnage2.sortir();
						root.getChildren().remove(boite2);
						boite2.invisible(ligne2);
						boite2 = new Boite(personnage2);
						root.getChildren().add(boite2);
					}
				}
			} else { // Player 2 sur le terrain
				if (event.getCode() == Test.getChoix21()) {
					if (team2.getVisible(0)) {
						expression_rouge = "EXPRESSION";
						updateExpression_rouge();
						mate2 = -1;
						updateMates_rouges();
					} else {
						if (personnage2.getRobot(1) != null) {
							expression_rouge = personnage2.getRobot(1).toString().substring(0,
									Math.min(20, personnage2.getRobot(1).toString().length()));
						} else {
							expression_rouge = "";
						}
						updateExpression_rouge();
						mate2 = 0;
						updateMates_rouges();
					}
				} else if (event.getCode() == Test.getChoix22()) {
					if (team2.getVisible(1)) {
						expression_rouge = "EXPRESSION";
						updateExpression_rouge();
						mate2 = -1;
						updateMates_rouges();
					} else {
						if (personnage2.getRobot(2) != null) {
							expression_rouge = personnage2.getRobot(2).toString().substring(0,
									Math.min(20, personnage2.getRobot(2).toString().length()));
						} else {
							expression_rouge = "";
						}
						updateExpression_rouge();
						mate2 = 1;
						updateMates_rouges();
					}
				} else if (event.getCode() == Test.getChoix23()) {
					if (team2.getVisible(2)) {
						expression_rouge = "EXPRESSION";
						updateExpression_rouge();
						mate2 = -1;
						updateMates_rouges();
					} else {
						if (personnage2.getRobot(3) != null) {
							expression_rouge = personnage2.getRobot(3).toString().substring(0,
									Math.min(20, personnage2.getRobot(3).toString().length()));
						} else {
							expression_rouge = "";
						}
						updateExpression_rouge();
						mate2 = 2;
						updateMates_rouges();
					}
				} else if (event.getCode() == Test.getHaut2()) {
					personnage2.mouvement(PointCardinal.NORD);
					root.getChildren().remove(boite2);
					boite2 = new Boite(personnage2);
					root.getChildren().add(boite2);
				} else if (event.getCode() == Test.getBas2()) {
					personnage2.mouvement(PointCardinal.SUD);
					root.getChildren().remove(boite2);
					boite2 = new Boite(personnage2);
					root.getChildren().add(boite2);
				} else if (event.getCode() == Test.getGauche2()) {
					personnage2.mouvement(PointCardinal.OUEST);
					root.getChildren().remove(boite2);
					boite2 = new Boite(personnage2);
					root.getChildren().add(boite2);
				} else {
					if (personnage2.getX() == 20
							&& (personnage2.getY() == 4 || personnage2.getY() == 5 || personnage2.getY() == 6)) {
						personnage2.rentrer();
						expression_rouge = "EXPRESSION";
						updateExpression_rouge();
						mate2 = -1;
						updateMates_rouges();
						ligne2 = 0;
						root.getChildren().remove(boite2);
						boite2.visible(ligne2);
						root.getChildren().add(boite2);
					} else {
						personnage2.mouvement(PointCardinal.EST);
						root.getChildren().remove(boite2);
						boite2 = new Boite(personnage2);
						root.getChildren().add(boite2);
					}
				}
			}

		} else if (!Test.getMenu() && !Test.enPause()
				&& (event.getCode() == Test.getGauche1() || event.getCode() == Test.getHaut1()
						|| event.getCode() == Test.getBas1() || event.getCode() == Test.getDroite1()
						|| event.getCode() == Test.getChoix11() || event.getCode() == Test.getChoix12()
						|| event.getCode() == Test.getChoix13())) {
			// Player 1
			if (personnage1.dansBase()) {
				if (mate1 != -1) {
					if (event.getCode() == Test.getHaut1()) {
						root.getChildren().remove(boite1);
						boite1.invisible(ligne1);
						if (ligne1 == 0)
							ligne1 = 4;
						else
							ligne1--;
						boite1.visible(ligne1);
						root.getChildren().add(boite1);
					} else if (event.getCode() == Test.getBas1()) {
						root.getChildren().remove(boite1);
						boite1.invisible(ligne1);
						if (ligne1 == 4)
							ligne1 = 0;
						else
							ligne1++;
						boite1.visible(ligne1);
						root.getChildren().add(boite1);
					} else if (event.getCode() == Test.getDroite1()) {
						personnage1.sortir();
						newBehavior1 = expression_bleue.substring(0, curseur_bleu)
								+ expression_bleue.substring(curseur_bleu + 1, expression_bleue.length());
						if (personnage1.getRobot(mate1 + 1) == null) {
							try {
								personnage1.addRobot(newBehavior1, mate1 + 1);
								expression_bleue = "EXPRESSION";
							} catch (PanicException e) {
								for (int i = 0; i < expression_courante.length(); i++)
									updateCurseur(3);
								for (int i = 0; i < expression_courante.length(); i++)
									supprimeChar(personnage1);
								expression_bleue = "ERROR SYNTAXE";
							}
						} else {
							try {
								personnage1.getRobot(mate1 + 1).setBehavior(newBehavior1);
								expression_bleue = "EXPRESSION";
							} catch (PanicException e) {
								for (int i = 0; i < expression_courante.length(); i++)
									updateCurseur(3);
								for (int i = 0; i < expression_courante.length(); i++)
									supprimeChar(personnage1);
								expression_bleue = "ERROR SYNTAXE";
							}
						}
						root.getChildren().remove(boite1);
						boite1.invisible(ligne1);
						boite1 = new Boite(personnage1);
						root.getChildren().add(boite1);
						updateExpression_bleue();
						mate1 = -1;
						updateMates_bleus();
					} else if (event.getCode() == Test.getChoix11()) {
						root.getChildren().remove(boite1);
						getOperateur(ligne1, 1, personnage1, 1);
						updateExpression_bleue();
						boite1 = new Boite(personnage1);
						boite1.visible(ligne1);
						root.getChildren().add(boite1);
					} else if (event.getCode() == Test.getChoix12()) {
						root.getChildren().remove(boite1);
						getOperateur(ligne1, 2, personnage1, 1);
						updateExpression_bleue();
						boite1 = new Boite(personnage1);
						boite1.visible(ligne1);
						root.getChildren().add(boite1);
					} else if (event.getCode() == Test.getChoix13()) {
						root.getChildren().remove(boite1);
						getOperateur(ligne1, 3, personnage1, 1);
						updateExpression_bleue();
						boite1 = new Boite(personnage1);
						boite1.visible(ligne1);
						root.getChildren().add(boite1);
					}
				} else { // Selectionner un Robot
					if (event.getCode() == Test.getChoix11()) {
						if (personnage1.getRobot(1) != null) {
							expression_bleue = personnage1.getRobot(1).toString() + "I";
							curseur_bleu = expression_bleue.length() - 1;
						} else {
							expression_bleue = "I";
							curseur_bleu = 0;
						}
						updateExpression_bleue();
						mate1 = 0;
						updateMates_bleus();
					} else if (event.getCode() == Test.getChoix12()) {
						if (personnage1.getRobot(2) != null) {
							expression_bleue = personnage1.getRobot(2).toString() + "I";
							curseur_bleu = expression_bleue.length() - 1;
						} else {
							expression_bleue = "I";
							curseur_bleu = 0;
						}
						updateExpression_bleue();
						mate1 = 1;
						updateMates_bleus();
					} else if (event.getCode() == Test.getChoix13()) {
						if (personnage1.getRobot(3) != null) {
							expression_bleue = personnage1.getRobot(3).toString() + "I";
							curseur_bleu = expression_bleue.length() - 1;
						} else {
							expression_bleue = "I";
							curseur_bleu = 0;
						}
						updateExpression_bleue();
						mate1 = 2;
						updateMates_bleus();
					} else if (event.getCode() == Test.getDroite1()) {
						personnage1.sortir();
						root.getChildren().remove(boite1);
						boite1.invisible(ligne1);
						boite1 = new Boite(personnage1);
						root.getChildren().add(boite1);
					}
				}
			} else { // Player 1 sur le terrain
				if (event.getCode() == Test.getChoix11()) {
					if (team1.getVisible(0)) {
						expression_bleue = "EXPRESSION";
						updateExpression_bleue();
						mate1 = -1;
						updateMates_bleus();
					} else {
						if (personnage1.getRobot(1) != null) {
							expression_bleue = personnage1.getRobot(1).toString().substring(0,
									Math.min(20, personnage1.getRobot(1).toString().length()));
						} else {
							expression_bleue = "";
						}
						updateExpression_bleue();
						mate1 = 0;
						updateMates_bleus();
					}
				} else if (event.getCode() == Test.getChoix12()) {
					if (team1.getVisible(1)) {
						expression_bleue = "EXPRESSION";
						updateExpression_bleue();
						mate1 = -1;
						updateMates_bleus();
					} else {
						if (personnage1.getRobot(2) != null) {
							expression_bleue = personnage1.getRobot(2).toString().substring(0,
									Math.min(20, personnage1.getRobot(2).toString().length()));
						} else {
							expression_bleue = "";
						}
						updateExpression_bleue();
						mate1 = 1;
						updateMates_bleus();
					}
				} else if (event.getCode() == Test.getChoix13()) {
					if (team1.getVisible(2)) {
						expression_bleue = "EXPRESSION";
						updateExpression_bleue();
						mate1 = -1;
						updateMates_bleus();
					} else {
						if (personnage1.getRobot(3) != null) {
							expression_bleue = personnage1.getRobot(3).toString().substring(0,
									Math.min(20, personnage1.getRobot(3).toString().length()));
						} else {
							expression_bleue = "";
						}
						updateExpression_bleue();
						mate1 = 2;
						updateMates_bleus();
					}
				} else if (event.getCode() == Test.getHaut1()) {
					personnage1.mouvement(PointCardinal.NORD);
					root.getChildren().remove(boite1);
					boite1 = new Boite(personnage1);
					root.getChildren().add(boite1);
				} else if (event.getCode() == Test.getBas1()) {
					personnage1.mouvement(PointCardinal.SUD);
					root.getChildren().remove(boite1);
					boite1 = new Boite(personnage1);
					root.getChildren().add(boite1);
				} else if (event.getCode() == Test.getGauche1()) {
					if (personnage1.getX() == 0
							&& (personnage1.getY() == 4 || personnage1.getY() == 5 || personnage1.getY() == 6)) {
						personnage1.rentrer();
						expression_bleue = "EXPRESSION";
						updateExpression_bleue();
						mate1 = -1;
						updateMates_bleus();
						ligne1 = 0;
						root.getChildren().remove(boite1);
						boite1.visible(ligne1);
						root.getChildren().add(boite1);
					} else {
						personnage1.mouvement(PointCardinal.OUEST);
						root.getChildren().remove(boite1);
						boite1 = new Boite(personnage1);
						root.getChildren().add(boite1);
					}
				} else {
					personnage1.mouvement(PointCardinal.EST);
					root.getChildren().remove(boite1);
					boite1 = new Boite(personnage1);
					root.getChildren().add(boite1);
				}
			}
		} else if (!FinalScreen.getIsFinish() && !Test.getMenu() && event.getCode() == Test.getGauche1().P)
			Test.PauseGame();
		else if (!Test.getMenu() && event.getCode() == Test.getGauche1().ESCAPE) {
			Test.setMenu(true);
		}
	}

	public void getOperateur(int ligne, int number, Personnages personnage, int team) {
		if (team == 1) {
			expression_courante = expression_bleue;
			curseur = curseur_bleu;
		} else if (team == 2) {
			expression_courante = expression_rouge;
			curseur = curseur_rouge;
		}
		switch (ligne) {
		case 0:
			if (number == 1 && !personnage.isEmpty('*')) {
				updateExpression("*");
				personnage.removeOperator('*');
			} else if (number == 2 && !personnage.isEmpty('{')) {
				updateExpression("{");
				personnage.removeOperator('{');
			} else if (number == 3 && !personnage.isEmpty('}')) {
				updateExpression("}");
				personnage.removeOperator('}');
			}
			break;
		case 1:
			if (number == 1 && !personnage.isEmpty(';')) {
				updateExpression(";");
				personnage.removeOperator(';');
			} else if (number == 2 && !personnage.isEmpty('|')) {
				updateExpression("|");
				personnage.removeOperator('|');
			} else if (number == 3 && !personnage.isEmpty(':')) {
				updateExpression(":");
				personnage.removeOperator(':');
			}
			break;
		case 2:
			if (number == 1 && !personnage.isEmpty('>')) {
				updateExpression(">");
				personnage.removeOperator('>');
			} else if (number == 2 && !personnage.isEmpty('H')) {
				updateExpression("H");
				personnage.removeOperator('H');
			} else if (number == 3 && !personnage.isEmpty('K')) {
				updateExpression("K");
				personnage.removeOperator('K');
			}
			break;
		case 3:
			if (number == 1 && !personnage.isEmpty('O')) {
				updateExpression("O");
				personnage.removeOperator('O');
			} else if (number == 2 && !personnage.isEmpty('J')) {
				updateExpression("J");
				personnage.removeOperator('J');
			} else if (number == 3 && !personnage.isEmpty('P')) {
				updateExpression("P");
				personnage.removeOperator('P');
			}
			break;
		case 4:
			if (number == 2) {
				// supprimer le caractere (a gauche du curseur)
				supprimeChar(personnage);
			} else {
				updateCurseur(number); // Decalage du curseur
			}
		}
		if (team == 1) {
			expression_bleue = expression_courante;
			curseur_bleu = curseur;
		} else if (team == 2) {
			expression_rouge = expression_courante;
			curseur_rouge = curseur;
		}
	}

	//
	// public void updateBoite_bleue() {
	// root.getChildren().remove(boite1);
	// boite1 = new Boite(personnage1);
	// root.getChildren().add(boite1);
	// }
	//
	// public void updateBoite_rouge() {
	// root.getChildren().remove(boite2);
	// boite2 = new Boite(personnage2);
	// root.getChildren().add(boite2);
	// }
	//
	public void updateMates_bleus() {
		root.getChildren().remove(team1);
		switch (mate1) {
		case 0:
			team1.invisible(1);
			team1.invisible(2);
			team1.visible(0);
			break;
		case 1:
			team1.invisible(0);
			team1.invisible(2);
			team1.visible(1);
			break;
		case 2:
			team1.invisible(0);
			team1.invisible(1);
			team1.visible(2);
			break;
		case -1:
			team1.invisible(0);
			team1.invisible(1);
			team1.invisible(2);
			break;
		}
		root.getChildren().add(team1);
	}

	public void updateMates_rouges() {
		root.getChildren().remove(team2);
		switch (mate2) {
		case 0:
			team2.invisible(1);
			team2.invisible(2);
			team2.visible(0);
			break;
		case 1:
			team2.invisible(0);
			team2.invisible(2);
			team2.visible(1);
			break;
		case 2:
			team2.invisible(0);
			team2.invisible(1);
			team2.visible(2);
			break;
		case -1:
			team2.invisible(0);
			team2.invisible(1);
			team2.invisible(2);
			break;
		}
		root.getChildren().add(team2);
	}

	public void updateExpression_bleue() {
		String affichable;
		root.getChildren().remove(expr_bleue);
		affichable = exprAffichable(expression_bleue);
		expr_bleue = new Text(affichable);
		expr_bleue.setFont(Font.font("Monospace", Tuile.getTaille() - marge));
		expr_bleue.setFill(Color.rgb(72, 145, 220, 1.0));
		expr_bleue.setX(3 * marge + Barre.getDimX());
		expr_bleue.setY(marge + (Terrain.getTuileY() + 1) * Tuile.getTaille());
		root.getChildren().add(expr_bleue);
	}

	public void updateExpression_rouge() {
		String affichable;
		root.getChildren().remove(expr_rouge);
		affichable = exprAffichable(expression_rouge);
		expr_rouge = new Text(affichable);
		expr_rouge.setFont(Font.font("Monospace", Tuile.getTaille() - marge));
		expr_rouge.setFill(Color.rgb(220, 41, 30, 1.0));
		expr_rouge.setX(3 * marge + Barre.getDimX() + ((Terrain.getTuileX() + 1) / 2) * Tuile.getTaille());
		expr_rouge.setY(marge + (Terrain.getTuileY() + 1) * Tuile.getTaille());
		root.getChildren().add(expr_rouge);
	}

	public void updateExpression(String new_c) {
		expression_courante = expression_courante.substring(0, curseur) + new_c
				+ expression_courante.substring(curseur, expression_courante.length());
		curseur++;
	}

	public void supprimeChar(Personnages p) {
		// Si curseur tout a gauche on ne suprimme rien
		if (curseur != 0) {
			char old_c = expression_courante.charAt(curseur - 1);
			expression_courante = expression_courante.substring(0, curseur - 1)
					+ expression_courante.substring(curseur, expression_courante.length());
			curseur--;
			p.addOperator(old_c);
		}
	}

	public void updateCurseur(int decalage) {
		if (decalage == 3) { // Decalage a droite
			// Curseur deja tout a droite, on ne fait rien
			if (curseur != expression_courante.length() - 1) {
				expression_courante = expression_courante.substring(0, curseur)
						+ expression_courante.charAt(curseur + 1) + expression_courante.charAt(curseur)
						+ expression_courante.substring(curseur + 2, expression_courante.length());
				curseur++;
			}
		} else if (decalage == 1) { // Decalage a gauche
			// Curseur deja tout a gauche, on ne fait rien
			if (curseur != 0) {
				expression_courante = expression_courante.substring(0, curseur - 1)
						+ expression_courante.charAt(curseur) + expression_courante.charAt(curseur - 1)
						+ expression_courante.substring(curseur + 1, expression_courante.length());
				curseur--;
			}
		}
	}

	public String exprAffichable(String s) {
		if (s.length() <= 19)
			return s;
		else if (curseur <= 19) {
			return s.substring(0, Math.min(20, s.length()));
		}
		return s.substring(curseur - 19, curseur + 1);
	}
}