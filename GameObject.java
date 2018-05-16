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
	
	public void update() {									   		//G�re le mouvement du GameObject
		view.setTranslateX(view.getTranslateX()+velocity.getX());	//Translation selon les x (si velocity.getX()=0 pas de mouvement)
		view.setTranslateY(view.getTranslateY()+velocity.getY());   //Translation selon les y (si velocity.getY()=0 pas de mouvement)
	
		
		
	
	}
	
	public void addGameObject(Pane root, double x, double y) { //Ajout d'un sprite "objet" aux coord x et y dans la fen�tre
		this.getView().setTranslateX(x);
		this.getView().setTranslateY(y);
		root.getChildren().add(this.getView());
	}
	
	public void setVelocity(Point2D velocity) {				   //Modifier l'attribut velocity
		this.velocity = velocity;
	}
	public double distance(GameObject other) {					   //Calcul de la distance entre deux GameObject
		return Math.sqrt(Math.pow(view.getTranslateX() - other.view.getTranslateX(), 2) + Math.pow(view.getTranslateY() - other.view.getTranslateY(), 2));
	}
	
	public Point2D getVelocity() {
		return velocity;
	}
	
	public Node getView() {
		return view;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void setAlive(boolean alive) {					    //Modifier l'�tat de vie de l'objet
		this.alive = alive;
	}
	
	
	public boolean isColliding(GameObject other) {       	    //Renvoie true s'il y a collision entre deux GameObject
		return getView().getBoundsInParent().intersects(other.getView().getBoundsInParent());
	}
	
	public double getRotate() {return view.getRotate();}
	public void rotateRight() {}								//Pour la rotation
	public void rotateLeft() {}
	
	

}
