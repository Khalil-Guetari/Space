package application;


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;


public class Game extends Application {
	public Pane root;
	private Exit_Level exit_level;
	public AnimationTimer timer;
	private Player player;
	private Planete planete;
	private Planete[] planetes;
	public int t = 0;
	
	
	public Parent createContent() throws Exception {			//Création du contenu de la scène
		root = new Pane();
		root.setPrefSize(600, 600);								//Dimension de la scène
		root.setStyle("-fx-background-color: black;");
		player = new Player(new Image("/img/vaisseau.png"));
		planete = new Planete(1, 20, new Image("/img/terre.png"));
		planetes = new Planete[1];
		planetes[0] = planete;
		exit_level = new Exit_Level(new Image("/img/exit.png"));
		player.setVelocity(new Point2D(0,0));
		player.addGameObject(root, 100, 100);
		planete.addGameObject(root, 450, 450);
		exit_level.addGameObject(root, 300, 300);
		
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
		
		t++; 									// on incremente le temps a chaque fin de boucle
		
		if (player.isCollidingPlanete(planete)) {
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
	public void start(Stage stage) throws Exception {			//Gère le remplissage de la fenêtre
		stage.setScene(new Scene(createContent()));
		stage.show();
		
	}	
}
