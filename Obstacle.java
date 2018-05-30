package application;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Obstacle extends GameObject {

	private int radius;
	private double periode;

	
	public Obstacle(int radius, Image image, double periode) {
		super(new ImageView(image));
		this.radius = radius;
		this.periode = periode;
		
	}

	public int getRadius() {
		return this.radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}
	

	
	public void move(double t) {
		
		double periode = t%this.periode;
			if (periode < this.periode/2) {
			this.setVelocity(new Point2D(-3,0));
		}
			else {
				this.setVelocity(new Point2D(3,0));
			}
	}

}