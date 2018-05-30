package application;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
//import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


public class Planete extends GameObject {	//Classe désignant les planètes
	
	private double masse;		//Masse pour appliquer les lois de la physique
	private int radius;
	private double t0; 			// Date d'activation de la planete 
	
	
	Planete(double masse, int radius, Image image) {															//Constructeur --> un gros cercle jaune
		super(new ImageView(image));													
		this.masse = masse;
		this.radius = radius;
		setAlive(false);															//On initialise la planete morte
		this.getView().setOpacity(0.3); 											//Planete morte : on la grise (transparence)
	}
	public void setMasse(double masse) {
		this.masse = masse;
	}
	public double getMasse() {
		return masse;
	}
	
	public void setT0(double t0) {
		this.t0 = t0;
	}
	
	public void setRadius(int rad) {
		this.radius = rad;
	}
	
	public int getRadius() {
		return this.radius;
	}
	
	public double getT0() {
		return this.t0;
	}
	
	public void onOff() {
		if (this.isAlive()) {
			this.setAlive(false);
			this.getView().setOpacity(0.2);											// la planete meurt : on la grise
		} else {
			this.setAlive(true);
			this.getView().setOpacity(1);											//la planète : on la met bien opaque 
		}
	}
	
	public void onClick(GameObject player, double t) {
	
		this.getView().setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				//player.setVelocity(new Point2D(0,0));
				setT0(t);									//quand on clique on donne a la planete une date d'activation
				onOff();									//Si la planete etait morte, on la passe vivante et on lui attribue la masse en argument dans le cas contraire, on la tue et passons sa masse a 0 
			
			}
		});
	}
} 
