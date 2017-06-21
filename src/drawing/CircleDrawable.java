package drawing;

import affichage.TestML;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import drawing.FormDrawable;


public class CircleDrawable extends FormDrawable{

	public CircleDrawable(Color color, Point pos, Dimension dim) {
		super(color, pos, dim);
		
	}

	
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(color);
		g.fillOval(rect.x,rect.y,rect.height,rect.width);
		g.setColor(c);
	}

}
