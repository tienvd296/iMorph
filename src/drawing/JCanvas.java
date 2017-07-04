package drawing;

import affichage.TestML;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.sun.prism.Image;

import drawing.IDrawable;


public class JCanvas extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static List drawables = new LinkedList();
    BufferedImage monImage = null;
    
    
	public void addDrawable(IDrawable d) {
        drawables.add(d);
        repaint();
    }
    
 
    public void removeDrawable(IDrawable d) {
        drawables.remove(d);
        repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);
        for (Iterator iter = drawables.iterator(); iter.hasNext();) {
            ((IDrawable) iter.next()).draw(g);
        }
    }

    public static void clear() {
        drawables.clear();
           }

    public List findDrawables(Point p) {
        List l = new ArrayList();
        for (Iterator iter = drawables.iterator(); iter.hasNext();) {
            IDrawable element = (IDrawable) iter.next();
            if (element.getRectangle().contains(p)) {
                l.add(element);
            }
        }
        return l;
    }



    public boolean isAlone(IDrawable drawable) {
        Rectangle rect = drawable.getRectangle();
        for (Iterator iter = drawables.iterator(); iter.hasNext();) {
            IDrawable element = (IDrawable) iter.next();
            System.out.println(element.getRectangle());
            if (!element.equals(drawable)
                    && element.getRectangle().intersects(rect)) {
                return false;
            }
        }
        return true;
    }

	public void setContentPane(BufferedImage test) {
		
	}


	



	

}