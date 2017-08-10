package drawing;

import affichage.TestML;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * @author duj
 *
 * 
 */
public interface IDrawable {
	/**
	 * @pre: g!=null
	 * @post: configuration de g inchangee
	 */
	public abstract void draw(Graphics g);

	/**
	 * @pre:
	 * @return le rectangle  qui contient completement this.
	 */
	public abstract Rectangle getRectangle();
}