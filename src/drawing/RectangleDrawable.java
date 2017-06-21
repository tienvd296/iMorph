package drawing;


import affichage.TestML;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;


public class RectangleDrawable extends FormDrawable{
	Double i = (double) affichage.Cadre2.slide.getValue();

	public RectangleDrawable(Color color, Point pos, Dimension dim) {
		super(color, pos, dim);
		
	}

	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(color);
		g.fillRect(rect.x,rect.y,rect.height,rect.width);
		g.setColor(c);
	}

}
