package application;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;



public class Game extends Application {
	public Pane root;
	public Pane rootBeta;
	private Player player;
	private Planete planete;
	private Planete planete2;
	private Planete planete3;
	private Planete planete4;
	private Planete[] planetes;
//	private GameObject exit_level;
	public int t = 0;
	
	
	public Parent createContent() {			//Création du contenu de la scène
		root = new Pane();
		root.setPrefSize(1000, 1000);				//Dimension de la scène
		player = new Player(new Image("Vaisseau.png"));
		planete = new Planete(new Image("planete_bleue.png"));
		planete2 = new Planete(4, 50);
		planete3 = new Planete(1, 50);
		planete4 = new Planete(1, 50);
		planetes = new Planete[4];
		planetes[0] = planete;
		planetes[1] = planete2;
		planetes[2] = planete3;
		planetes[3] = planete4;
		
		exit_level = new Exit_Level(new Image("exit.png"));
		player.setVelocity(new Point2D(0,0));
		player.addGameObject(root, 500, 500);
		planete.addGameObject(root, 900, 100);
		planete2.addGameObject(root, 300, 700);
		planete3.addGameObject(root, 900, 900);
		planete4.addGameObject(root, 100, 100);
//		exit_level.addGameObject(root, 1200, 1200);
		
		
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				onUpdate();
			}
		};
		timer.start();
		return root;
	}
	
	
	
	private void onUpdate() {					//Mise à jour de l'affichage
		if (player.isOrbiting(planetes)) {
			player.updateVelocityOrbiting(planetes, t);
		} else {
			player.updateVelocity(planetes, t);	
		}
		
		player.updateVelocity(planetes, t);
//		player.visuVitesse(root);
		player.update();
		
		
//		if (player.isColliding(planete)) {
//			player.setAlive(false);
//			root.getChildren().remove(player.getView());
//		}
		
		planete.getView().setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				System.out.println("Click sur la planète jaune");
				planete.setT0(t);						//quand on clique on donne a la planete une date d'activation
				planete.onOff();						//Si la planete etait morte, on la passe vivante et on lui attribue la masse en argument dans le cas contraire, on la tue et passons sa masse a 0 
				
			}
		});
		
		planete2.getView().setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				System.out.println("Click sur la planète2 jaune");
				planete2.setT0(t);				
				planete2.onOff();

			}
		});
		planete3.getView().setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				System.out.println("Click sur la planète2 jaune");
				planete3.setT0(t);				
				planete3.onOff();
				
			}
		});
		planete4.getView().setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				System.out.println("Click sur la planète2 jaune");
				planete4.setT0(t);				
				planete4.onOff();
				
			}
		});
		
		
		
//		if (player.isColliding(exit_level)) {
//			player.setAlive(false);
//			root.getChildren().remove(player.getView());
//			System.out.println("Level Sucess");
//		}
		
		
		t++; // on incremente le temps a chaque fin de boucle
	}

	@Override
	public void start(Stage stage) {			//Gère le remplissage de la fenêtre
		stage.setScene(new Scene(createContent()));
		stage.show();	
	}
	
	public static void main(String[] args) {
		launch(args);
		
	}	
	
}
