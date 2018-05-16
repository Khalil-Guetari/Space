package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Exit_Level extends GameObject { 	//Classe désignant le point de sortie du niveau
	
												//Attributs pour le design de la sortie
	
	Exit_Level(Image image) {								//Constructeur --> un petit cercle rouge
		super(new ImageView(image));
	}								
		
	

}
