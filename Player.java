package application;

import javafx.scene.shape.Circle;

public class Player extends GameObject { 	//Classe d�signant le vaisseau
	
											//Attributs pour le design du vaisseau spatiale
	
	
	
	Player() {								//Constructeur --> un petit cercle rouge
		
		super(new Circle(0, 0, 15, javafx.scene.paint.Color.RED));
	}
}
