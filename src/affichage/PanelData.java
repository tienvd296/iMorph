package affichage;

import java.awt.Color;
import java.awt.Font;


import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PanelData extends JPanel{

	static JTextArea jText = new JTextArea();
	
	
	public PanelData() {
		super();
		//setBounds(100, 100, 500, 375);
		
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
		 Font police = new Font("Arial", Font.BOLD, 16);
		    jText.setFont(police);
		    
		    
		  
		    
		    
		/*    jText.addKeyListener(new KeyAdapter(){ 
		    	public void keyPressed(KeyEvent e){ 
		    	JTextField jtf = (JTextField) e.getSource(); 
		    	String texteNouvellementInserer = jtf.getText(); 
		    	int nouvelleTaille = texteNouvellementInserer.length(); 
		    	jText.setSize(nouvelleTaille); 
		    	} 
		    	}); */
		    
		    jText.setText("LandMarks :");
		    jText.setForeground(Color.BLACK);
		    jText.setEnabled(false);
		   
		    jText.setForeground(Color.black);
		   
		   
		  
		    GroupLayout groupLayout = new GroupLayout(this);
		   groupLayout.setHorizontalGroup(
		   	groupLayout.createParallelGroup(Alignment.LEADING)
		   		.addComponent(jText, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
		   );
		   groupLayout.setVerticalGroup(
		   	groupLayout.createParallelGroup(Alignment.LEADING)
		   		.addComponent(jText, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
		   );
		   setLayout(groupLayout);
		   
		
	
		
	}
	
	 
	 
	
	/* public void paintComponent(Graphics g){
		 super.paintComponent(g);
		 Font font = new Font("Courier", Font.BOLD, 20);
		   this.setFont(font);
		   g.setColor(Color.red);          
		   g.drawString("Test de texte dans panData", 10, 20); 
		   g.drawString(" test", 10, 20);
	 }
	 */
	 
	 

	 
	 
}
