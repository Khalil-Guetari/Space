package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player extends GameObject { 	//Classe désignant le vaisseau
	
	Planete Orbiting;										//Attributs pour le design du vaisseau spatiale
	
	
	
	Player(Image image) {								//Constructeur --> un petit cercle rouge
		
		super(new ImageView(image));
	}
	
	
	public Point2D computeVelocity(Planete planete, int t) { //calcul des vitesses cf fiche de préparation
		
		double constante1 = 6.67*planete.getMasse()/Math.pow(this.distance(planete), 2);
		double constante2 = Math.atan(Math.abs(this.getView().getTranslateY()-planete.getView().getTranslateY())/Math.abs(this.getView().getTranslateX()-planete.getView().getTranslateX()));

//		double newVx = (t - planete.getT0())*constante1*Math.cos(constante2)*(planete.getView().getTranslateX() - this.getView().getTranslateX())/Math.abs(planete.getView().getTranslateX() - this.getView().getTranslateX());
//		double newVy = (t - planete.getT0())*constante1*Math.sin(constante2)*(planete.getView().getTranslateY() - this.getView().getTranslateY())/Math.abs(planete.getView().getTranslateY() - this.getView().getTranslateY());
		
		
		double newVx = 500*constante1*Math.cos(constante2)*(planete.getView().getTranslateX() - this.getView().getTranslateX())/Math.abs(planete.getView().getTranslateX() - this.getView().getTranslateX());
		double newVy = 500*constante1*Math.sin(constante2)*(planete.getView().getTranslateY() - this.getView().getTranslateY())/Math.abs(planete.getView().getTranslateY() - this.getView().getTranslateY());
		
		return new Point2D(newVx, newVy);
//		this.setVelocity(new Point2D(this.getVelocity().getX() + newVx,this.getVelocity().getY() + newVy));
		
	}
	
	public Planete getOrbiting() {
		return this.Orbiting;
	}
	
	public void setOrbiting(Planete Orbiting) {
		this.Orbiting = Orbiting;
	}
	
	public void updateVelocity(Planete[] planetes, int t ) {
		Point2D velocity = this.getVelocity(); 
		Point2D update;
//		System.out.println("1vx="+velocity.getX());
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
	
	public void updateVelocityOrbiting(Planete[] planetes, int t ) {
		this.updateVelocity(planetes, t);
		Point2D vitesse = this.getVelocity();
		Point2D vecteurVersPlanete = this.computeVelocity(this.Orbiting, t);
		
		double alpha = vecteurVersPlanete.angle(vitesse);
		System.out.println("alpha = "+alpha);
		
		double theta = - (90 - alpha) * Math.PI / 180;
		
		this.setVelocity(new Point2D(Math.cos(theta)*vitesse.getX() - Math.sin(theta)*vitesse.getY(), Math.sin(theta)*vitesse.getX() + Math.cos(theta)*vitesse.getY() ));
		
		
	}
	
	
	public boolean isOrbiting(Planete[] planetes) {
		for ( int i = 0; i < planetes.length; i++ ) {
			if (planetes[i].isAlive()) {
				if (this.distance(planetes[i]) <= 2*planetes[i].getRadius()) {
					System.out.println("j'orbite maintenant autour de "+i);
					this.setOrbiting(planetes[i]);
					return true;
				}
			}
		}
		return false;
	}
	
	
	public Rectangle[] visuVitesse(Pane root) {
		
		System.out.println("ok je commence");
		
		Rectangle vitesseX = new Rectangle();
		Rectangle vitesseY = new Rectangle();
		
		
//		vitesseX.setX(this.getView().getTranslateX());
//		vitesseX.setY(this.getView().getTranslateY());
		vitesseX.setX(200);
		vitesseX.setY(200);
		vitesseX.setWidth(2);
		vitesseX.setHeight(100*this.getVelocity().getX());
		vitesseX.setOpacity(0.5);
		vitesseX.setFill(javafx.scene.paint.Color.CHARTREUSE);
		
		
			
		
//		vitesseY.setX(this.getView().getTranslateX());
//		vitesseY.setY(this.getView().getTranslateY());
		vitesseY.setX(200 + 50*this.getVelocity().getY());
		vitesseY.setY(200);
		vitesseY.setWidth(2);
		vitesseY.setHeight(100*this.getVelocity().getY());
		vitesseY.setOpacity(0.5);
		vitesseY.setRotate(90);
		vitesseY.setFill(javafx.scene.paint.Color.AQUA);		
		
		root.getChildren().add(vitesseX);
		root.getChildren().add(vitesseY);
		
		Rectangle[] vitesse = new Rectangle[2];
		vitesse[0] = vitesseX;
		vitesse[1] = vitesseY;
		
		
		return vitesse;
	}
	
}
