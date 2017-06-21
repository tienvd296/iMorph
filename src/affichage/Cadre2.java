package affichage;






import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import  java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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







@SuppressWarnings("restriction")
public class Cadre2 extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu fichierMenu = new JMenu();
	private final JMenuItem ouvrirMenu = new JMenuItem();
	private final JMenu filtreMenu = new JMenu();
	public final static Affichage panneau = new Affichage();
	private final JMenuItem enregistrerMenu = new JMenuItem();
	
	private final JMenuItem correctionImageMenu = new JMenuItem();
	private final JMenu retaillerMenu = new JMenu();
	private final JMenuItem agrandirMenu = new JMenuItem();
	private final JMenuItem reduireMenu = new JMenuItem();
	private JToolBar toolBar = new JToolBar();
	
	public static JSlider slide = new JSlider();
	//Redimmensionnement d'images pour faire une toolBar fine
	
	ImageIcon squareT = new ImageIcon("assets/Carre_Blanc.jpg");
	 
	ImageIcon square = new ImageIcon(squareT.getImage().getScaledInstance(40, 40,java.awt.Image.SCALE_SMOOTH));

	ImageIcon circleT = new ImageIcon("assets/Rond_Blanc.jpg");
	 
	ImageIcon circle = new ImageIcon(circleT.getImage().getScaledInstance(40,40,java.awt.Image.SCALE_SMOOTH));
	
	
	 public static Cadre2 frame = new Cadre2();
	 static JPanel panShape = new JPanel();
	 static JCanvas jc = new JCanvas();
	 
	 public  JComboBox combo = new JComboBox();
	 public static JComboBox combo2 = new JComboBox();
	 public static int Value ;
	
	  private static List drawables = new LinkedList();
	
	  
	public PanelData panData = new PanelData();	
	 
	private JButton squareButton = new JButton(square);
	private JButton circleButton = new JButton(circle);

	BufferedImage monImage = null;
	  private JSplitPane split;


	public Cadre2() {
		super();
		setBounds(100, 100, 500, 375);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
		menuBar.add(filtreMenu);	
		filtreMenu.setText("Filtre");

	
		
		filtreMenu.add(correctionImageMenu);
		correctionImageMenu.addActionListener((ActionListener)this);
		correctionImageMenu.setText("Correction");

		menuBar.add(retaillerMenu);
		retaillerMenu.setText("retailler");

		retaillerMenu.add(agrandirMenu);
		agrandirMenu.addActionListener((ActionListener)this);
		agrandirMenu.setText("agrandir");
		agrandirMenu.setAccelerator(KeyStroke.getKeyStroke('p'));
		
		retaillerMenu.add(reduireMenu);
		reduireMenu.addActionListener((ActionListener)this);
		reduireMenu.setText("reduire");
		reduireMenu.setAccelerator(KeyStroke.getKeyStroke('m'));

	
		
		
		
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
	    slide.setVisible(false);
	    

	    combo.addItem("CIRCLE");
	    combo.addItem("SQUARE");
	    combo.addItem("ERASE");
	    combo.addActionListener(new FormeListener());
	    
	     
	    combo.addActionListener(new FormeListener());
	    combo.setPreferredSize(new Dimension(180,50));
	    combo.setMinimumSize(new Dimension(180,50));
	    combo.setMaximumSize(new Dimension(180,50));
	    combo.setVisible(false);
	    
	    toolBar.add(slide);
	    toolBar.add(combo);
	    combo.setFocusable(false);
	    slide.setFocusable(true);
		toolBar.setFloatable(false);
		
		
		add(toolBar, BorderLayout.PAGE_START);
		
		
		
		
		
		
		
		// ajouter le panneau de dessin
		getContentPane().add(panneau);
		
		
		
		squareButton.addActionListener(this);
		circleButton.addActionListener(this);
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
				
				panneau.setBounds(0, 0, frame.getWidth(), frame.getHeight());
			
			}
			
			
		} else if (cliqueMenu.getSource().equals(enregistrerMenu)) {
			JFileChooser fileEnregistrerImage = new JFileChooser();
			if (fileEnregistrerImage.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				File fichierEnregistrement = new File(fileEnregistrerImage.getSelectedFile().getAbsolutePath()+ ".JPG");
				panneau.enregistrerImage(fichierEnregistrement);
			}
		} else
			
			if (cliqueMenu.getSource().equals(agrandirMenu)) {
				panneau.agrandirImage();
			} else if (cliqueMenu.getSource().equals(reduireMenu)) {
				panneau.reduireImage();
			
			}else if(cliqueMenu.getSource().equals(correctionImageMenu)){
				
				try {
					sauverImage();
					
				} catch (IOException e) {
					
					e.printStackTrace();
				} catch (AWTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				
		
		if(squareButton.isEnabled()) {
			System.out.println("squareButton");
			combo.setVisible(true);
			slide.setVisible(true);
			
			
		} 
		if(circleButton.isEnabled()) {
			System.out.println("circleButton");
			panneau.add(panData, BorderLayout.EAST);
			split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panneau, panData);
			split.setOneTouchExpandable(true);
			
			frame.add(split);
			
		}
		
	}
	
	public void sauverImage() throws IOException, AWTException 
	{ 
		Robot robot = new Robot();
		BufferedImage image21 = robot.createScreenCapture(new Rectangle(frame.getX(), frame.getY(), frame.getWidth(), frame.getHeight()));
		JFileChooser fileEnregistrerImage = new JFileChooser();
		if (fileEnregistrerImage.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			File fichierEnregistrement = new File(fileEnregistrerImage.getSelectedFile().getAbsolutePath()+ ".JPG");
			panneau.enregistrerImage2(image21, fichierEnregistrement);
			}
	} 



	





	public static void main(String args[]) 
	{
		try {
			
			 
			 
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(1100,650);
			jc.setSize(950, 650);
			
		
			/*panShape.setBackground(Color.RED);
			panShape.setPreferredSize(new Dimension(550, panShape.getHeight() + 100));
			panShape.setVisible(true);
			*/
			
		//	GUIHelper.showOnFrame(jc,"test JCanvas");
			
			new TestML(jc);
			
	
			panneau.setVisible(true);
			
			jc.add(panneau);
			

			
			jc.setVisible(true);
			
			frame.add(jc);
		
			
		
			frame.setVisible(true);
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}




	
	
}












