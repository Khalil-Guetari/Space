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

public class Game6 extends Application {

	private Pane root;
	private Exit_Level exit_level;
	private AnimationTimer timer;
	private Player player;
	private Planete planete;
	private Planete planete1; 
	private Planete planete2;
	private Planete planete3; 
	private Trou_noir trou_noir;
	private Planete[] planetes;
	private Obstacle asteroid;
	private Arrow visuArrow;
	public double t = 0;

	public Parent createContent() throws Exception {			//Création du contenu de la scène
		root = new Pane();
		root.setPrefSize(800, 700);								//Dimension de la scène
		root.setStyle("-fx-background-color: black;");
		player = new Player(new Image("/img/vaisseau.png"));
		planete = new Planete(2, 10, new Image("/img/etoile_bleu.png"));
		planete1 = new Planete(5, 15, new Image("/img/verte.png"));
		planete2 = new Planete(5, 15, new Image("/img/bleu.png"));
		planete3 = new Planete(5, 15, new Image("/img/mars.png"));
		planetes = new Planete[4];
		planetes[0] = planete;
		planetes[1] = planete1;
		planetes[2] = planete2;
		planetes[3] = planete3;
		asteroid = new Obstacle(0, new Image("/img/rose.png"), 100);
		exit_level = new Exit_Level(new Image("/img/exit.png"));
		trou_noir = new Trou_noir();
		player.setVelocity(new Point2D(0,0));
		player.addGameObject(root, 20, 20);
		planete.addGameObject(root, 50, 302);
		planete1.addGameObject(root, 400, 200);
		planete2.addGameObject(root, 700, 50);
		planete3.addGameObject(root, 375, 575);
		exit_level.addGameObject(root, 600, 575);
		trou_noir.addGameObject(root, 250, 320);
		asteroid.addGameObject(root, 700, 450);
		
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
		
//		player.updateVelocity(planetes, t);
		
		if (t == 0) {
			visuArrow = player.visuArrow(root,800,700);
		} else {
			root.getChildren().remove(visuArrow);
			visuArrow = player.visuArrow(root,800,700);
		}
		
		root.getChildren().add(visuArrow);

		player.update();
		asteroid.move(t);
		asteroid.update();
					
		planete.onClick(player, t);
		planete1.onClick(player, t);
		planete2.onClick(player, t);
		planete3.onClick(player, t);
		
		t+=0.5; 											// on incremente le temps a chaque fin de boucle
		
		if (player.isCollidingPlanete(planete)) {
			player.setAlive(false);
			root.getChildren().remove(player.getView());
			timer.stop();
			root.getChildren().removeAll();
			result("failed");
		
		}
		
		if (player.isCollidingPlanete(planete1)) {
			player.setAlive(false);
			root.getChildren().remove(player.getView());
			timer.stop();
			root.getChildren().removeAll();
			result("failed");
		
		}
		if (player.isCollidingPlanete(planete2)) {
			player.setAlive(false);
			root.getChildren().remove(player.getView());
			timer.stop();
			root.getChildren().removeAll();
			result("failed");
		
		}
		if (player.isCollidingPlanete(planete3)) {
			player.setAlive(false);
			root.getChildren().remove(player.getView());
			timer.stop();
			root.getChildren().removeAll();
			result("failed");
		
		}
		if (player.isCollidingObstacle(asteroid)) {
			player.setAlive(false);
			root.getChildren().remove(player.getView());
			timer.stop();
			root.getChildren().removeAll();
			result("failed");
		
		}
	
		if (player.isCollidingExit(exit_level)) {
			player.setAlive(false);
			root.getChildren().remove(player.getView());
			timer.stop();
			root.getChildren().removeAll();
			result("sucess");
			
		}
		if (player.isCollidingTrou_noir(trou_noir)) {
			player.setAlive(false);
			root.getChildren().remove(player.getView());
			root.getChildren().removeAll();
			timer.stop();
			result("failed");
		}
	}
	public void result(String result) throws Exception {
		root = FXMLLoader.load(getClass().getResource("/application/Level_"+ result + ".fxml"));
		Scene scene = new Scene(root,600,600);
		Stage primaryStage = new Stage();
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());				
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		stage.setScene(new Scene(createContent()));
		stage.show();
	}
}