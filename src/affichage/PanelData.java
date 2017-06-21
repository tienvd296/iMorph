package affichage;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.WindowConstants;

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

	private void creerPanel() {
		
		this.setBackground(Color.BLUE);
		
		
	}
	
}
