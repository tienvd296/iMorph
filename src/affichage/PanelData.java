package affichage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

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
	
	 
	 public void paintComponent(Graphics g){
		 super.paintComponent(g);
		 Font font = new Font("Courier", Font.BOLD, 20);
		   this.setFont(font);
		   g.setColor(Color.red);          
		   g.drawString("Test de texte dans panData", 10, 20); 
	 }
	 
	 
	 
	 
	 
	 
}
