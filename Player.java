
package application;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Player extends GameObject { 	//Classe désignant le vaisseau
	
	Planete Orbiting;						
	
	Player(Image image) {							
		
		super(new ImageView(image));
	}
	

	
	public Point2D computeVelocity(Planete planete, double t) { 			//calcul des vitesses cf fiche de préparation
		
		double constante1 = 6.67*planete.getMasse()/Math.pow(this.distance(planete), 2);
		double constante2 = Math.atan(Math.abs(this.getView().getTranslateY()-planete.getView().getTranslateY())/Math.abs(this.getView().getTranslateX()-planete.getView().getTranslateX()));

		double newVx = (t - planete.getT0())*constante1*Math.cos(constante2)*(planete.getView().getTranslateX() - this.getView().getTranslateX())/Math.abs(planete.getView().getTranslateX() - this.getView().getTranslateX());
		double newVy = (t - planete.getT0())*constante1*Math.sin(constante2)*(planete.getView().getTranslateY() - this.getView().getTranslateY())/Math.abs(planete.getView().getTranslateY() - this.getView().getTranslateY());
		
		return new Point2D(newVx, newVy);
	}
	
	public Planete getOrbiting() {
		return this.Orbiting;
	}
	
	public void setOrbiting(Planete Orbiting) {
		this.Orbiting = Orbiting;
	}
	
	public void updateVelocity(Planete[] planetes, double t ) {
		Point2D velocity = this.getVelocity(); 
		Point2D update;
		double x = velocity.getX();
		double y = velocity.getY();
		for ( int i = 0; i < planetes.length; i++ ) {
			if (planetes[i].isAlive()) {
				update = this.computeVelocity(planetes[i], t);
				
				x+=update.getX();
				y+=update.getY();
			}
		}
		this.setVelocity(new Point2D(x,y));
	}
	
	public void updateVelocityOrbiting(Planete[] planetes, double t ) {
		this.updateVelocity(planetes, t);
		Point2D vitesse = this.getVelocity();
		double norme = Math.sqrt(Math.pow(vitesse.getX(), 2) + Math.pow(vitesse.getY(), 2));
		
		double reduction;
		
		if (norme >= 0.7) {
			reduction = 0.70 / norme;
		} else {
			reduction = 1;
		}
		Point2D vecteurVersPlanete = this.computeVelocity(this.Orbiting, t);
	
		double alpha = vecteurVersPlanete.angle(vitesse);
		double theta = - (90 - alpha) * Math.PI / 180;
		
		this.setVelocity(new Point2D(Math.cos(theta)*vitesse.getX()*reduction - Math.sin(theta)*vitesse.getY()*reduction, Math.sin(theta)*vitesse.getX()*reduction + Math.cos(theta)*vitesse.getY()*reduction ));
	}
	
	public boolean isOrbiting(Planete[] planetes) {
		for ( int i = 0; i < planetes.length; i++ ) {
			if (planetes[i].isAlive()) {
				if (this.distance(planetes[i]) <= 9*planetes[i].getRadius()) {
					this.setOrbiting(planetes[i]);
					return true;
				}
			}
		}
		return false;
	}
	public Arrow visuArrow(Pane root, double xmax, double ymax) {
		
		double x = this.getView().getTranslateX();
		double y = this.getView().getTranslateY();

		double xmin = 0;
		double ymin = 0;
		
		if (x >= xmax) {
			if(y >= ymax) {
				return new Arrow(xmax - 100, ymax - 100, xmax - 50, ymax - 50); 
			}
			if(y <= ymin) {
				return new Arrow(xmax - 100, 100, xmax - 50, 50);				
			} 
			else {
				return new Arrow(xmax - 100, y, xmax - 50, y );				
			}			
		}
		if (x <= xmax && x >= xmin) {
			if (y >= ymax) {
				return new Arrow (x, ymax - 100, x, ymax - 50);
			}
			if (y <= ymin) {
				return new Arrow (x, 100, x, 50);
				
			}
		}
		
		if (x <= xmin) {
			if(y >= ymax) {
				return new Arrow(100, ymax - 100, 50, ymax - 50); 
			}
			if(y <= ymin) {
				return new Arrow(100, 100, 50, 50);				
			} 
			else {
				return new Arrow(100, y, 50, y);	
			}
		}
		
		return new Arrow(0, 0, 0, 0);
	}
}
