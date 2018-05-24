package application;



import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class GameObject {
	private Node view;
	private Point2D velocity;
	private boolean alive = true;
	
	
	public GameObject(Node view) {							   		//Constructeur
		this.view = view;
	}
	
	public void update() {									   		//Gère le mouvement du GameObject
		view.setTranslateX(view.getTranslateX()+velocity.getX());	//Translation selon les x (si velocity.getX()=0 pas de mouvement)
		view.setTranslateY(view.getTranslateY()+velocity.getY());   //Translation selon les y (si velocity.getY()=0 pas de mouvement)
	}
	
	public void addGameObject(Pane root, double x, double y) { 		//Ajout d'un sprite "objet" aux coord x et y dans la fenêtre
		this.getView().setTranslateX(x);
		this.getView().setTranslateY(y);
		root.getChildren().add(this.getView());
	}
	
	public void setVelocity(Point2D velocity) {				   		//Modifier l'attribut velocity
		this.velocity = velocity;
	}
	public double distance(Planete other) {					   	//Calcul de la distance entre deux GameObject
		return Math.sqrt(Math.pow(view.getTranslateX() - other.getView().getTranslateX(), 2) + Math.pow(view.getTranslateY() - other.getView().getTranslateY(), 2));
	}
	
	public Point2D getVelocity() {
		return velocity;
	}
	
	public Node getView() {
		return view;
	}
//	public double getX() {
//		return;
//	}
//	public double getY() {
//		return;
//	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void setAlive(boolean alive) {					    	//Modifier l'état de vie de l'objet
		this.alive = alive;
	}
	
	public boolean isCollidingPlanete(Planete other) {       	    	
		return this.distance(other)<other.getRadius()+65;
	}
	public boolean isCollidingTrou_noir(Trou_noir other) {       	    	
		return getView().getBoundsInParent().intersects(other.getView().getBoundsInParent());
	}
	public boolean isCollidingExit(Exit_Level other) {       	    	
		return getView().getBoundsInParent().intersects(other.getView().getBoundsInParent());
	}
}
