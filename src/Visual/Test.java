package Visual;

import Engine.Timer;
import UserInterface.Keyboard;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Test extends Application {

	static int marge = Tuile.getTaille() / 5;
	
	public static void main(String[] args) {
		Application.launch(Test.class, args);
	}

	public void start(Stage primaryStage) {

		primaryStage.setTitle("ARF - Autonomous Robot Fight");
		Group root = new Group();
		int dimX = Terrain.getTuileX() * Tuile.getTaille() + 2 * Barre.getDimX() + 3 * marge;
		int dimY = Barre.getDimX() + Boite.getHeight() + 4 * marge;
		Scene scene = new Scene(root, dimX, dimY);

		Terrain monTerrain = new Terrain();
		monTerrain.setTranslateX(2 * marge + Barre.getDimX());
		monTerrain.setTranslateY(marge);

		Keyboard keyboard = new Keyboard(monTerrain.getpersonnage1(), monTerrain.getpersonnage2());

		scene.setOnKeyPressed(keyboard);

//		OperateursVisual operateur;
//		operateur = monTerrain.getoperateur();

//		Timeline blinker = operateur.Blinker(monTerrain.getImageOperateur());
//		FadeTransition fader = operateur.Fader(monTerrain.getImageOperateur());

//		SequentialTransition blinkThenFade = new SequentialTransition(monTerrain.getImageOperateur(), blinker, fader);

//		blinkThenFade.play();

		Boite boiteGauche = new Boite();
		boiteGauche.setTranslateX(marge);
		boiteGauche.setTranslateY(marge);

		Boite boiteDroite = new Boite();
		boiteDroite.setTranslateX(Tuile.getTaille() * Terrain.getTuileX() + Barre.getDimX() + 3 * marge);
		boiteDroite.setTranslateY(marge);

		root.getChildren().add(monTerrain);
		root.getChildren().add(boiteDroite);
		root.getChildren().add(boiteGauche);

		ImageView PersoRouge = new ImageView(new Image(Test.class.getResourceAsStream("images/PersoRouge.png")));
		PersoRouge.setFitWidth(Barre.getDimX());
		PersoRouge.setFitHeight(Barre.getDimX());
		PersoRouge.setTranslateX(Tuile.getTaille() * Terrain.getTuileX() + Barre.getDimX() + 3 * marge);
		PersoRouge.setTranslateY(Boite.getHeight() + 4 * marge);
		root.getChildren().add(PersoRouge);

		ImageView PersoBleu = new ImageView(new Image(Test.class.getResourceAsStream("images/PersoBleu.png")));
		PersoBleu.setFitWidth(Barre.getDimX());
		PersoBleu.setFitHeight(Barre.getDimX());
		PersoBleu.setTranslateX(marge);
		PersoBleu.setTranslateY(Boite.getHeight() + 4 * marge);
		root.getChildren().add(PersoBleu);

		Rectangle champBleu = new Rectangle();
		champBleu.setHeight(Tuile.getTaille());
		champBleu.setWidth((Terrain.getTuileX() / 2) * Tuile.getTaille());
		champBleu.setTranslateX(2 * marge + Barre.getDimX());
		champBleu.setTranslateY(2 * marge + Terrain.getTuileY() * Tuile.getTaille());
		root.getChildren().add(champBleu);
		Text expr_bleue = new Text("EXPRESSION{|>;}KIPJ:HO");
		expr_bleue.setFont(new Font(Tuile.getTaille() - marge));
		expr_bleue.setFill(Color.rgb(72, 145, 220, 1.0));
		expr_bleue.setX(3 * marge + Barre.getDimX());
		expr_bleue.setY(marge + (Terrain.getTuileY() + 1) * Tuile.getTaille());
		root.getChildren().add(expr_bleue);

		Rectangle champRouge = new Rectangle();
		champRouge.setHeight(Tuile.getTaille());
		champRouge.setWidth((Terrain.getTuileX() / 2) * Tuile.getTaille());
		champRouge.setTranslateX(2 * marge + Barre.getDimX() + ((Terrain.getTuileX() + 1) / 2) * Tuile.getTaille());
		champRouge.setTranslateY(2 * marge + Terrain.getTuileY() * Tuile.getTaille());
		root.getChildren().add(champRouge);
		Text expr_rouge = new Text("EXPRESSION{|>;}KIPJ:HO");
		expr_rouge.setFont(new Font(Tuile.getTaille() - marge));
		expr_rouge.setFill(Color.rgb(220, 41, 30, 1.0));
		expr_rouge.setX(3 * marge + Barre.getDimX() + ((Terrain.getTuileX() + 1) / 2) * Tuile.getTaille());
		expr_rouge.setY(marge + (Terrain.getTuileY() + 1) * Tuile.getTaille());
		root.getChildren().add(expr_rouge);

		Team team1 = new Team(0);
		team1.setTranslateX(2 * marge + Barre.getDimX());
		team1.setTranslateY(4 * marge + Terrain.getTuileY() * Tuile.getTaille() + Tuile.getTaille());
		root.getChildren().add(team1);

		Team team2 = new Team(1);
		team2.setTranslateX(2 * marge + Barre.getDimX() + ((Terrain.getTuileX() + 1) / 2) * Tuile.getTaille());
		team2.setTranslateY(4 * marge + Terrain.getTuileY() * Tuile.getTaille() + Tuile.getTaille());
		root.getChildren().add(team2);

		scene.setFill(Color.rgb(210, 200, 190, 1.0));
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		
		Timer game = new Timer(monTerrain);
		game.start();

	}

}
