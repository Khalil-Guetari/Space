package application;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

public class Player extends GameObject { // Classe désignant le vaisseau

	Planete Orbiting; // Attributs pour le design du vaisseau spatiale
	private int sens;

	Player() { // Constructeur --> un petit cercle rouge

		super(new Circle(0, 0, 15, javafx.scene.paint.Color.RED));
		sens = 0;
	}

	public Point2D computeVelocity(Planete planete, double t) { // calcul des vitesses cf fiche de préparation

		double constante1 = 6.67 * planete.getMasse() / Math.pow(this.distance(planete), 2);
		double constante2 = Math.atan(Math.abs(this.getView().getTranslateY() - planete.getView().getTranslateY())
				/ Math.abs(this.getView().getTranslateX() - planete.getView().getTranslateX()));

		double newVx = (t - planete.getT0()) * constante1 * Math.cos(constante2)
				* (planete.getView().getTranslateX() - this.getView().getTranslateX())
				/ Math.abs(planete.getView().getTranslateX() - this.getView().getTranslateX());
		double newVy = (t - planete.getT0()) * constante1 * Math.sin(constante2)
				* (planete.getView().getTranslateY() - this.getView().getTranslateY())
				/ Math.abs(planete.getView().getTranslateY() - this.getView().getTranslateY());

		// double newVx = 500 * constante1 * Math.cos(constante2)
		// * (planete.getView().getTranslateX() - this.getView().getTranslateX())
		// / Math.abs(planete.getView().getTranslateX() -
		// this.getView().getTranslateX());
		// double newVy = 500 * constante1 * Math.sin(constante2)
		// * (planete.getView().getTranslateY() - this.getView().getTranslateY())
		// / Math.abs(planete.getView().getTranslateY() -
		// this.getView().getTranslateY());

		return new Point2D(newVx, newVy);
		// this.setVelocity(new Point2D(this.getVelocity().getX() +
		// newVx,this.getVelocity().getY() + newVy));

	}
	
	public boolean isIn(double xmin, double xmax, double ymax, double ymin) {
		if (xmin <= this.getView().getTranslateX() && this.getView().getTranslateX() <= xmax && ymin <= this.getView().getTranslateY() && this.getView().getTranslateY() <= ymax) {
			return true;
		}
		return false;
	}


	public void updateVelocity(Planete[] planetes, double t) {
		Point2D velocity = this.getVelocity();
		Point2D update;
		double x = velocity.getX();
		double y = velocity.getY();
		for (int i = 0; i < planetes.length; i++) {
			if (planetes[i].isAlive()) {
				update = this.computeVelocity(planetes[i], t);

				x += update.getX();
				y += update.getY();
			}
		}
		this.setVelocity(new Point2D(x, y));
	}

	public int getSens() {
		return sens;
	}

	public void setSens(int sens) {
		this.sens = sens;
	}

	public void computeSens(Planete[] planetes, double t) {

		double deltaX = (this.getView().getTranslateX() - this.Orbiting.getView().getTranslateX())
				/ Math.abs(this.Orbiting.getView().getTranslateX() - this.getView().getTranslateX());
		double deltaY = (this.getView().getTranslateY() - this.Orbiting.getView().getTranslateY())
				/ Math.abs(this.Orbiting.getView().getTranslateY() - this.getView().getTranslateY());

		if (this.getSens() == 0) {
			System.out.println("coucou");
			if (deltaX == 1.0 && deltaY == 1.0) {
				System.out.println("En bas a doite");
				if (this.getVelocity().getX() <= 0) {
					this.setSens(1);
				}else {
					this.setSens(-1);
				}
			}
			if (deltaX == 1.0 && deltaY == -1.0) {
				System.out.println("en haut a droite");
				if (this.getVelocity().getY() >= 0) {
					this.setSens(1);
					
				}else {
					this.setSens(-1);
				}
			}
			if (deltaX == -1.0 && deltaY == -1.0) {
				System.out.println("en haut a gauche");
				if (this.getVelocity().getX() >= 0) {
					this.setSens(1);
				}else {
					this.setSens(-1);
				}
			}
			if (deltaX == -1.0 && deltaY == 1.0) {
				System.out.println("en bas a gauche");
				if (this.getVelocity().getY() <= 0) {
					this.setSens(1);
				}else {
					this.setSens(-1);
				}
			} 
		}
	}
	
	public Planete getOrbiting() {
		return this.Orbiting;
	}
	
	public void setOrbiting(Planete Orbiting) {
		this.Orbiting = Orbiting;
	}
	
	public boolean isOrbiting(Planete[] planetes, double t) {
		for (int i = 0; i < planetes.length; i++) {
			if (planetes[i].isAlive()) {
				if (this.distance(planetes[i]) <= 3 * planetes[i].getRadius()) {
					System.out.println("j'orbite maintenant autour de " + i);
					this.setOrbiting(planetes[i]);
					this.computeSens(planetes, t);
					System.out.println("sens =" + this.sens);
					return true;
				}
			}
		}
		this.setSens(0);
		return false;
	}

	public void updateVelocityOrbiting(Planete[] planetes, double t) {
		
		
		

		this.updateVelocity(planetes, t);

		Point2D vitesse = this.getVelocity();
		Point2D vecteurVersPlanete = this.computeVelocity(this.Orbiting, t);

		double alpha = vecteurVersPlanete.angle(vitesse);

		double gradiant = 0.05; //permet d'adoucir la mise en orbite

//		System.out.println("gradiant =" + gradiant);
//		System.out.println("alpha =" + alpha);

		double theta =  gradiant * (90 - alpha) * Math.PI / 180 ;
//		System.out.println("theta =" + theta);
		
		

		
		

		this.setVelocity(new Point2D(Math.cos(theta) * vitesse.getX() - Math.sin(theta) * vitesse.getY(),
				Math.sin(theta) * vitesse.getX() + Math.cos(theta) * vitesse.getY()));

	}


	public Line visuTraj(Pane root) {

		Line vitesse = new Line();

		vitesse.setStartX(this.getView().getTranslateX());
		vitesse.setStartY(this.getView().getTranslateY());

		vitesse.setEndX(this.getView().getTranslateX());
		vitesse.setEndY(this.getView().getTranslateY());
		
		vitesse.setStroke(Color.RED);
		vitesse.setStrokeWidth(3);
		
		root.getChildren().add(vitesse);
		
		return vitesse;
	}
	
	
	public Arrow visuArrow(Pane root) {
		
		double x = this.getView().getTranslateX();
		double y = this.getView().getTranslateY();

		double xmax = 1000;
		double xmin = 0;
		double ymax = 1000;
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
	
	
	public Line visuVitesse(Pane root) {
		
		Line vitesse = new Line();
		
		vitesse.setStartX(this.getView().getTranslateX());
		vitesse.setStartY(this.getView().getTranslateY());
		
		vitesse.setEndX(this.getView().getTranslateX() + 10*this.getVelocity().getX());
		vitesse.setEndY(this.getView().getTranslateY() + 10*this.getVelocity().getY());
		
		vitesse.setStroke(Color.BLUE);
		vitesse.setStrokeWidth(3);
		vitesse.setStrokeLineCap(StrokeLineCap.BUTT);
		
		
		return vitesse;
	}
	
//	public Arrow visuVitesse(Pane root) {
//		
//		Arrow vitesse = new Arrow(this.getView().getTranslateX(), this.getView().getTranslateY(), this.getView().getTranslateX() + 10*this.getVelocity().getX(),this.getView().getTranslateY() + 10*this.getVelocity().getY());
//				
//		return vitesse;
//	}
	

}
