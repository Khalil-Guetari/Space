package application;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Game4 extends Application {
	
	private Pane root;
	private Exit_Level exit_level;
	private AnimationTimer timer;
	private Player player;
	private Planete planete;
	private Planete planete1; 
	private Planete[] planetes;
	public int t = 0;

	public Parent createContent() throws Exception {			//Création du contenu de la scène
		root = new Pane();
		root.setPrefSize(600, 600);								//Dimension de la scène
		root.setStyle("-fx-background-color: black;");
		player = new Player(new Image("/img/vaisseau.png"));
		planete = new Planete(2, 10, new Image("/img/etoile_bleu.png"));
		planete1 = new Planete(5, 15, new Image("/img/bleu.png"));
		planetes = new Planete[2];
		planetes[0] = planete;
		planetes[1] = planete1; 
		exit_level = new Exit_Level(new Image("/img/exit.png"));
		player.setVelocity(new Point2D(0,0));
		player.addGameObject(root, 10, 300);
		planete.addGameObject(root, 430, 301);
		planete1.addGameObject(root, 150, 275);
		exit_level.addGameObject(root, 550, 301);
		
		timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				try {
					onUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		timer.start();
		onUpdate();
		return root;
	}
	private void onUpdate() throws Exception {					//Mise à jour de l'affichage
		
		if (player.isOrbiting(planetes)) {
			player.updateVelocityOrbiting(planetes, t);
		} else {
			player.updateVelocity(planetes, t);	
		}
		
		player.updateVelocity(planetes, t);
		player.update();
					
		planete.onClick(player, t);
		planete1.onClick(player, t);
		
		t=t+2; 											// on incremente le temps a chaque fin de boucle
		
		if (player.isCollidingPlanete(planete)) {
			player.setAlive(false);
			root.getChildren().remove(player.getView());
			timer.stop();
			root.getChildren().removeAll();
			System.out.println("Level Failed");
			root = FXMLLoader.load(getClass().getResource("/application/Level_failed.fxml"));
			Scene scene = new Scene(root,600,600);
			Stage primaryStage = new Stage();
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());				
			primaryStage.setScene(scene);
			primaryStage.show();
		
		}
		
		if (player.isCollidingPlanete(planete1)) {
			player.setAlive(false);
			root.getChildren().remove(player.getView());
			timer.stop();
			root.getChildren().removeAll();
			System.out.println("Level Failed");
			root = FXMLLoader.load(getClass().getResource("/application/Level_failed.fxml"));
			Scene scene = new Scene(root,600,600);
			Stage primaryStage = new Stage();
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());				
			primaryStage.setScene(scene);
			primaryStage.show();
		
		}
	
		if (player.isCollidingExit(exit_level)) {
			player.setAlive(false);
			root.getChildren().remove(player.getView());
			timer.stop();
			root.getChildren().removeAll();
			System.out.println("Level Sucess");
			root = FXMLLoader.load(getClass().getResource("/application/Level_sucess.fxml"));
			Scene scene = new Scene(root,600,600);
			Stage primaryStage = new Stage();
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		}
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		stage.setScene(new Scene(createContent()));
		stage.show();
	}
}

