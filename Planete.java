package application;

import javafx.scene.shape.Circle;

public class Planete extends GameObject {	//Classe désignant les planètes
	
	private int masse;						//Masse pour appliquer les lois de la physique
	
	Planete() {															//Constructeur --> un gros cercle jaune
		super(new Circle(0, 0, 50, javafx.scene.paint.Color.YELLOW));
		masse = 0;														//Masse nulle --> aucun effet physique
	}
	public void setMasse(int masse) {
		this.masse = masse;
	}
	public int getMasse() {
		return masse;
	}
} 
