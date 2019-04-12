package utils;

import java.awt.*;
import java.util.Set;

public class Ligne {
	private Point point1;
	private Point point2;

	public Ligne(Point p1, Point p2 ){
		point1=p1;
		point2=p2;
	}

	/**
	 * Calcul la distance entre un point et la ligne
	 * @param p
	 * @return renvoie la distance entre le point et la ligne
	 */
	public double distance(Point p){
		Point projection = p.projectionOrthogonale(this);
		return projection.distance(p);
	}
	
	Point getp1(){return point1;}
	Point getp2(){return point2;}

}
