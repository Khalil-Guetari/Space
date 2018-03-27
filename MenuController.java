package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuController {
	//@FXML					//2 lignes inutiles
	//private Button exit;
	
	
	public void quit(ActionEvent event) {							//Quitter le jeu
		Platform.exit();
		System.exit(0);
	}
	public void play(ActionEvent event) throws Exception {			//Bouton play
		((Node)(event.getSource())).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/application/Play.fxml"));
		Scene scene = new Scene(root,600,600);
		Stage primaryStage = new Stage();
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	public void return_menu(ActionEvent event) throws Exception {    //Bouton retour_menu
		((Node)(event.getSource())).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/application/Menu.fxml"));
		Scene scene = new Scene(root,600,600);
		Stage primaryStage = new Stage();
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public void level1(ActionEvent event) throws Exception {  //Ouverture du premier niveau à partir du menu "select a level"
		((Node)(event.getSource())).getScene().getWindow().hide();
		Game game = new Game();
		Scene scene = new Scene(game.createContent(),600,600);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}
}
