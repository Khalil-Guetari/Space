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



public class Main extends Application {
	public Pane root;
	private GameObject player;
	private GameObject planete;
	private GameObject exit_level;
	
	
	
	private Parent createContent() {			//Création du contenu de la scène
		root = new Pane();
		root.setPrefSize(600, 600);				//Dimension de la scène
		player = new Player();
		planete = new Planete();
		exit_level = new Exit_Level();
		player.setVelocity(new Point2D(0,0));
		player.addGameObject(root, 100, 100);
		planete.addGameObject(root, 500, 500);
		exit_level.addGameObject(root, 400, 400);
		
		
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
		player.update();
		if (player.isColliding(planete)) {
			player.setAlive(false);
			root.getChildren().remove(player.getView());
		}
		planete.getView().setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				System.out.println("Click sur la planète jaune");
				player.setVelocity(new Point2D(1.2,1.2));
			}
		});
		if (player.isColliding(exit_level)) {
			player.setAlive(false);
			root.getChildren().remove(player.getView());
			System.out.println("Level Sucess");
		}
	}

	@Override
	public void start(Stage stage) {			//Gère le remplissage de la fenêtre
		stage.setScene(new Scene(createContent()));
		stage.show();	
	}
	
	public static void main(String[] args) {	//Démarrer l'application dans une unique fenêtre
		launch(args);
	}
}
