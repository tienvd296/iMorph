package affichage;

 


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;

import drawing.CircleDrawable;
import drawing.IDrawable;
import drawing.JCanvas;
import drawing.JCanvasMouseListener;
import drawing.RectangleDrawable;

public class TestML extends JCanvasMouseListener {

	Double i = (double) affichage.Cadre2.slide.getValue(); 
	/**
	 * @wbp.parser.entryPoint
	 */
	public TestML(JCanvas canvas) {
		super(canvas);
		
		

	    
	 //  this.setLayout();
	   
	   
		
	}
	 public void removeDrawable(IDrawable d) {
	        JFrame drawables = null;
			drawables.remove((Component) d);
	        JCanvas.clear();
	    }
	protected void rightClickAction(MouseEvent e) {
		Point p = e.getPoint();
		IDrawable circl = createDrawable2(e);
			canvas.addDrawable(circl);
	}

	
	protected void leftClickAction(MouseEvent e) {

		
		Point p = e.getPoint();
		IDrawable rect = createDrawable(e);
			canvas.addDrawable(rect);
		
	}
	public static void mouseAction(MouseEvent e, int i){
			Point p = e.getPoint();
			i = Cadre2.Value;
			
			if(i == 0) {
				IDrawable shape = createDrawable2(e);
				// cercle
				canvas.addDrawable(shape);
			}
			else if (i == 1){
				IDrawable shape = createDrawable(e);
			//Square
				canvas.addDrawable(shape);
			}
			else if(i == 2){
				List selectedDrawables =  canvas.findDrawables(p);
				if (selectedDrawables.size() == 0) return;
				IDrawable drawable = (IDrawable) selectedDrawables.get(0);
					canvas.removeDrawable(drawable);

			}
		
			
	}
	

	private static IDrawable createDrawable(MouseEvent e) {
		Double i = (double) affichage.Cadre2.slide.getValue();
		Point p = e.getPoint();
		Dimension dim = new Dimension();
		dim.setSize(i, i);
		return new RectangleDrawable(Color.white, p, dim);

	}

	private static IDrawable createDrawable2(MouseEvent e) {
		Double i = (double) affichage.Cadre2.slide.getValue();
		Point p = e.getPoint();
		Dimension dim = new Dimension();
		dim.setSize(i, i);
		return new CircleDrawable(Color.white, p, dim);

	}
}
