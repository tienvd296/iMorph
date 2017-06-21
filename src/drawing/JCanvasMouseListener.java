package drawing;


import affichage.TestML;
import java.awt.event.*;

import javax.swing.SwingUtilities;

import affichage.Cadre2;

/**
 * 
 * @author Christophe
 * @version
 */
public class JCanvasMouseListener extends MouseAdapter implements ActionListener 
		 {
	protected static JCanvas canvas;

	public JCanvasMouseListener(JCanvas jc) {
		super();
		this.canvas = jc;
		jc.addMouseListener(this);
	}
		
	public JCanvas getCanvas() {
		return canvas;
	}

	public void mousePressed(MouseEvent e) {
		
		if(affichage.Cadre2.combo2.getSelectedItem() == "SQUARE") {
		
			TestML.mouseAction(e,1);	
		

		}
		else if(affichage.Cadre2.combo2.getSelectedItem() == "CIRCLE") {
		TestML.mouseAction(e,0);
		}
		else if(affichage.Cadre2.combo2.getSelectedItem() == "ERASE"){
			TestML.mouseAction(e,2);
		}
	}
	
/* 


if(SwingUtilities.isLeftMouseButton(e)==true) {
	
	leftClickAction(e);		

}
else {
rightClickAction(e);
}
*/
	

	

		

	/**
	 * @pre: @post:
	 */
	protected void rightClickAction(MouseEvent e) {
		JCanvas.clear();
	
	}

	/**
	 * @pre: @post:
	 */
	protected void leftClickAction(MouseEvent e) {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}



	

}