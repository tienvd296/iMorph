package drawing;


import java.awt.Point;
import affichage.TestML;

public interface IMovableDrawable extends IDrawable{
	
	void setPosition(Point p);
	
	Point getPosition();

}
