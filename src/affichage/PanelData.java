package affichage;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class PanelData extends JPanel{

	
	
	public PanelData() {
		super();
		setBounds(100, 100, 500, 375);
		
		try {
			
			
			creerPanel();
			
			
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		//
	}

	 void creerPanel() {
		
		/*
		 * Partie du Panel qui va accepter les Metadata et la matrice des positions des landmarks
		 */
			
		 
		this.setBackground(Color.BLUE);
		
		
	
		
	}
	
}
