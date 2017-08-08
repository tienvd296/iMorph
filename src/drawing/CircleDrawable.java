package drawing;

import affichage.TestML;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import drawing.FormDrawable;


public class CircleDrawable extends FormDrawable{

	private int X = 0; 
	private int Y = 0;
	
	public CircleDrawable(Color color, double x, double y, Dimension dim) {
		super(color, x,y, dim);
		
		X = (int) x;
		Y = (int) y;
	}

	
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(color.RED);
		g.fillOval(X,Y,20,20);
		g.setColor(c);
	}

}
