package application;

import javafx.scene.shape.Circle;

public class Planete extends GameObject {											//Classe désignant les planètes
	
	private double masse;		//Masse pour appliquer les lois de la physique
	private int radius;
	private int t0; 			// Date d'activation de la planete 
	
	
	Planete(double masse, int radius) {																		//Constructeur --> un gros cercle jaune
		super(new Circle(0, 0, radius, javafx.scene.paint.Color.YELLOW));
		this.masse = masse;			//Masse nulle --> aucun effet physique	
		this.radius = radius;
		System.out.println(radius);
		setAlive(false);															//On initialise la planete morte
		this.getView().setOpacity(0.3); 											//Planete morte : on la grise (transparence)
	}
	public void setMasse(double masse) {
		this.masse = masse;
	}
	public double getMasse() {
		return masse;
	}
	
	public void setT0(int t0) {
		this.t0 = t0;
	}
	
	public void setRadius(int rad) {
		this.radius = rad;
	}
	
	public int getRadius() {
		return this.radius;
	}
	
	public int getT0() {
		return this.t0;
	}
	
	public void onOff() {
		if (this.isAlive()) {
			this.setAlive(false);
			this.getView().setOpacity(0.2);											// la planete meurt : on la grise
		} else {
			this.setAlive(true);
			this.getView().setOpacity(1);											//la planete née : on la met bien opaque 
		}
	
	}
}
