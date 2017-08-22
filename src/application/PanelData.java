package application;

import java.awt.Color;
import java.awt.Font;


import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import javax.swing.DropMode;
import java.awt.GridLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.CardLayout;

public class PanelData extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JTextArea jText = new JTextArea();


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
		Font police = new Font("Arial", Font.BOLD, 18);
		setLayout(new CardLayout(0, 0));
		jText.setRows(10);
		jText.setColumns(1);
		jText.setEditable(false);
		jText.setToolTipText("");
		jText.setTabSize(50);
		jText.setBackground(SystemColor.inactiveCaptionBorder);
		jText.setFont(new Font("Ebrima", Font.PLAIN, 18));
	
		jText.setForeground(new Color(0, 0, 0));
	
		jText.setForeground(Color.black);
		add(jText, "name_2266615044668");




	}
}
