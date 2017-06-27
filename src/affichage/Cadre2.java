package affichage;






import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import  java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import drawing.JCanvas;







public class Cadre2 extends JFrame implements ActionListener {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu fichierMenu = new JMenu();
	private final JMenuItem ouvrirMenu = new JMenuItem();
	private final JMenu landMarkMenu = new JMenu();
	
	private Container c;
	
	
	private final JMenu removeLandMarkMenu = new JMenu();
	private final JMenu modifyLandMarkMenu = new JMenu();
	
	public static Affichage panneau;
	
	private final JMenuItem enregistrerMenu = new JMenuItem();
	
	
	private final JMenuItem addLandMarkMenu = new JMenuItem();
	private final JMenu EditingMenu = new JMenu();
	private final JMenuItem editSubMenu = new JMenuItem();
	private final JMenuItem cropMenu = new JMenuItem();
	
	
	private final JMenuItem resizeMenu = new JMenu();
	private final JMenuItem zoomIn = new JMenuItem();
	private final JMenuItem zoomOut = new JMenuItem();
	
	
	private JToolBar toolBar = new JToolBar();
	
	 static JCanvas jc ;	
	
	
	public static JSlider slide = new JSlider();
	//Redimmensionnement d'images pour faire une toolBar fine
	
	ImageIcon squareT = new ImageIcon("assets/Carre_Blanc.jpg");
	 
	ImageIcon square = new ImageIcon(squareT.getImage().getScaledInstance(40, 40,java.awt.Image.SCALE_SMOOTH));

	ImageIcon circleT = new ImageIcon("assets/Rond_Blanc.jpg");
	 
	ImageIcon circle = new ImageIcon(circleT.getImage().getScaledInstance(40,40,java.awt.Image.SCALE_SMOOTH));
	
	
//	 public static Cadre2 frame = new Cadre2();
	 static JPanel panShape = new JPanel();
	
	 
	 public  JComboBox combo = new JComboBox();
	 public static JComboBox combo2 = new JComboBox();
	 public static int Value ;
	
	  private static List drawables = new LinkedList();
	
	  
	public static PanelData panData;	
	 
	private JButton squareButton = new JButton(square);
	private JButton circleButton = new JButton(circle);

	BufferedImage monImage = null;
	  

	public static JSplitPane split;
	
	
	
	

	public Cadre2() {
		super();
		System.out.println("lololo");
		Go();
		try {
			
			
			creerMenu();
			
			
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		//
	}
	
	
	
	
	private void creerMenu() throws Exception {

		try {
			Robot robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
		// construction du menu
		setJMenuBar(menuBar);	
		menuBar.add(fichierMenu);
		fichierMenu.setText("Fichier");
		fichierMenu.add(ouvrirMenu);
		ouvrirMenu.addActionListener((ActionListener)this);
		ouvrirMenu.setText("ouvrir");

		fichierMenu.add(enregistrerMenu);
		enregistrerMenu.addActionListener((ActionListener)this);
		enregistrerMenu.setText("enregistrer");
		menuBar.add(landMarkMenu);	
		landMarkMenu.setText("LandMark");

	
		
		landMarkMenu.add(addLandMarkMenu);
		addLandMarkMenu.addActionListener((ActionListener)this);
		addLandMarkMenu.setText("Add LandMark");

		landMarkMenu.add(removeLandMarkMenu);
		removeLandMarkMenu.addActionListener((ActionListener)this);
		removeLandMarkMenu.setText("Remove LandMark");
		
		landMarkMenu.add(modifyLandMarkMenu);
		modifyLandMarkMenu.addActionListener((ActionListener)this);
		modifyLandMarkMenu.setText("Modify LandMark");
		
		menuBar.add(EditingMenu);
		EditingMenu.setText("Image Editing");

		EditingMenu.add(editSubMenu);
		editSubMenu.addActionListener((ActionListener)this);
		editSubMenu.setText("Edit Image");
	
		
		EditingMenu.add(cropMenu);
		cropMenu.addActionListener((ActionListener)this);
		cropMenu.setText("Crop Image");
		

	
		EditingMenu.add(resizeMenu);
		resizeMenu.addActionListener((ActionListener)this);
		resizeMenu.setText("Resize Image");
	
		
		resizeMenu.add(zoomIn);
		zoomIn.addActionListener((ActionListener)this);
		zoomIn.setText("Zoom In");
		zoomIn.setAccelerator(KeyStroke.getKeyStroke('p'));
		
		
		resizeMenu.add(zoomOut);
		zoomOut.addActionListener((ActionListener)this);
		zoomOut.setText("Zoom Out");
		zoomOut.setAccelerator(KeyStroke.getKeyStroke('m'));
	
		
		
		this.add(toolBar, BorderLayout.PAGE_START);
	
		
		toolBar.add(squareButton);
		toolBar.add(circleButton);

		
		
		slide.setMaximum(100);
	    slide.setMinimum(0);
	    slide.setValue(70);
	    slide.setPaintTicks(true);
	    slide.setPaintLabels(true);
	    slide.setMinorTickSpacing(10);
	    slide.setMajorTickSpacing(20);
	    slide.setPreferredSize(new Dimension(180,50));
	    slide.setMinimumSize(new Dimension(180,50));
	    slide.setMaximumSize(new Dimension(180,50));
	    slide.addChangeListener(new ChangeListener(){
	    	 
	         public void stateChanged(ChangeEvent event){
	        	 double i = slide.getValue();
	          System.out.println("La valeur du Jslide "+i);
	         	}
	         });      
		
	    
	    toolBar.add(slide);

	    combo.addItem("CIRCLE");
	    combo.addItem("SQUARE");
	    combo.addItem("ERASE");
	    combo.addActionListener(new FormeListener());
	    
	     
	    combo.addActionListener(new FormeListener());
	    combo.setPreferredSize(new Dimension(180,50));
	    combo.setMinimumSize(new Dimension(180,50));
	    combo.setMaximumSize(new Dimension(180,50));
		 
	   
	    toolBar.add(combo);
	    
	    
	    
	    combo.setFocusable(false);
	    slide.setFocusable(true);
		toolBar.setFloatable(false);
		
		this.add(panneau);
		
		panData.setBounds(panneau.getX(), panneau.getY(), panneau.getWidth(), panneau.getHeight());
	
		
		split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panneau, panData);
		split.setOneTouchExpandable(true);
		this.getContentPane().add(split, BorderLayout.CENTER);
		
	
		
		
		
		
		toolBar.setVisible(false);
		
		
		
		squareButton.addActionListener(this);
		circleButton.addActionListener(this);
	}
	
	
	public void toolBarLandMark(Boolean b){
		
		if(b==true) {
			System.out.println("toolBarLandMark(true)");

		//	panneau.setVisible(true);
			panData.setVisible(true);
			split.setVisible(true);
			
			toolBarEditing(false);
			
		
			
		} else if(b == false) {
			System.out.println("toolBarEditing False");

			panData.setVisible(false);
			split.setVisible(false);
		}
		
		
	}
	
	public void toolBarEditing(Boolean b) {
		
		
		if(b==true){
			
			System.out.println("toolBarEditing true");
			toolBarLandMark(false);
			//c.add(panneau);
			toolBar.setVisible(true);
			
		    slide.setVisible(true);
		    combo.setVisible(true);
		    
		
		
			
		} else if(b == false)  {
			
			toolBar.setVisible(false);
			
			System.out.println("Toolbar Correction refusée");
			
		}
		
	}
	

	  class FormeListener implements ActionListener{
		    public void actionPerformed(ActionEvent e) {
		    	if(combo.getSelectedItem() == "CIRCLE"){
		    		System.out.println("CIRCLE");
		    		combo2.addItem("CIRCLE");
		    		Value = 0;
		    		//mouseAction(e, 1);
		    	}
		    	else if(combo.getSelectedItem() == "SQUARE"){
		    		System.out.println("SQUARE");
		    		
		    		combo2.addItem("SQUARE");
		    		Value=1;
		    	}
		    	else if(combo.getSelectedItem() == "ERASE"){
		    		System.out.println("ERASE");
		    		
		    		combo2.addItem("ERASE");
		    		Value=2;
		    	}
		    }  
		  }    
	
	  
	 
	
	public void actionPerformed(ActionEvent cliqueMenu) {
		if (cliqueMenu.getSource().equals(ouvrirMenu))
		{
			JFileChooser fileOuvrirImage = new JFileChooser();
			if (fileOuvrirImage.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				panneau.ajouterImage(new File(fileOuvrirImage.getSelectedFile().getAbsolutePath()));
				
				panneau.setBounds(0, 0, this.getWidth(), this.getHeight());
			
			}
			
			
		} else if (cliqueMenu.getSource().equals(enregistrerMenu)) {
			JFileChooser fileEnregistrerImage = new JFileChooser();
			if (fileEnregistrerImage.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				File fichierEnregistrement = new File(fileEnregistrerImage.getSelectedFile().getAbsolutePath()+ ".JPG");
				panneau.enregistrerImage(fichierEnregistrement);
			}
		} else
			
			if (cliqueMenu.getSource().equals(editSubMenu)) {
			//	panneau.agrandirImage();
			// Lance la correction, toolbar	
				toolBarEditing(true);
				
			} else if (cliqueMenu.getSource().equals(cropMenu)) {
				panneau.reduireImage();
			
			}else if(cliqueMenu.getSource().equals(addLandMarkMenu)){
				
				toolBarLandMark(true);
				
			}
				
		
		if(cliqueMenu.getSource() == squareButton) {
			System.out.println("squareButton");
			
		/*	combo.setVisible(true);
			slide.setVisible(true);
			
			split.setVisible(false);
			panData.setVisible(false);
			frame.add(panneau);
		//	frame.CreerSplit(false);
			*/
		} 
		if(cliqueMenu.getSource() == circleButton) {
			System.out.println("circleButton");
		
			
			
		}
		
		
		
	}
	
	


	
	public void sauverImage() throws IOException, AWTException 
	{ 
		Robot robot = new Robot();
		BufferedImage image21 = robot.createScreenCapture(new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight()));
		JFileChooser fileEnregistrerImage = new JFileChooser();
		if (fileEnregistrerImage.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			File fichierEnregistrement = new File(fileEnregistrerImage.getSelectedFile().getAbsolutePath()+ ".JPG");
			panneau.enregistrerImage2(image21, fichierEnregistrement);
			}
	} 



	private void Go() {
		
		
		
		c = this.getContentPane();
		
	    jc = new JCanvas();
	    panData = new PanelData();
	    panneau = new Affichage();
	    
	    panData.setVisible(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1100,650);
		jc.setBounds(this.getX(), this.getY(),950, 650);
		
		new TestML(jc);
		
	
		panneau.add(jc);
		
		//panData.setVisible(true);
		
		jc.setBackground(Color.green);
		jc.setVisible(true);
		panneau.setVisible(true);
		
		c.add(panneau);
	
		System.out.println("lol");
		this.setVisible(true);
		
		
		
		
	}





	public static void main(String args[]) 
	{
		try {
			new Cadre2();
		
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}












	
	
}












