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


public class Game2 extends Application {
	
	public Pane root;
	private Exit_Level exit_level;
	public AnimationTimer timer;
	private Player player;
	private Planete planete;
	private Planete planete1;
	private Planete[] planetes;
	private Arrow visuArrow;
	public int t = 0;

	public Parent createContent() throws Exception {			//Cr�ation du contenu de la sc�ne
		root = new Pane();
		root.setPrefSize(600, 600);								//Dimension de la sc�ne
		root.setStyle("-fx-background-color: black;");
		player = new Player(new Image("/img/vaisseau.png"));
		planete = new Planete(5, 20, new Image("/img/terre.png"));
		planete1 = new Planete(3, 15,  new Image("/img/verte.png"));
		planetes = new Planete[2];
		planetes[0] = planete;
		planetes[1] = planete1;
		exit_level = new Exit_Level(new Image("/img/exit.png"));
		player.setVelocity(new Point2D(0,0));
		player.addGameObject(root, 275, 100);
		planete.addGameObject(root, 450, 450);
		planete1.addGameObject(root, 50, 450);
		exit_level.addGameObject(root, 280, 300);
		
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
	private void onUpdate() throws Exception {					//Mise � jour de l'affichage
		
		if (player.isOrbiting(planetes)) {
			player.updateVelocityOrbiting(planetes, t);
		} else {
			player.updateVelocity(planetes, t);	
		}

		if (t == 0) {
			visuArrow = player.visuArrow(root,600,600);
		} else {
			root.getChildren().remove(visuArrow);
			visuArrow = player.visuArrow(root,600,600);
		}
		
		root.getChildren().add(visuArrow);
		player.update();
		
		planete.onClick(player, t);			
		planete1.onClick(player, t);
		
		t++; 									// on incremente le temps a chaque fin de boucle
		
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
	
		if (player.isCollidingExit(exit_level)) {
			player.setAlive(false);
			root.getChildren().remove(player.getView());
			timer.stop();
			root.getChildren().removeAll();
			result("sucess");
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