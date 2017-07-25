package affichage;






import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import businesslogic.ImageWing;
import drawing.JCanvas;





public class Cadre2 extends JFrame implements ActionListener {




	private static final long serialVersionUID = 1L;
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu fichierMenu = new JMenu();
	private final JMenuItem ouvrirMenu = new JMenuItem();
	private final JMenu landMarkMenu = new JMenu();

	private Container c;


	private final JMenu displayLandMark = new JMenu();
	private final JMenuItem trueOnly = new JMenuItem();
	private final JMenuItem falseOnly = new JMenuItem();
	private final JMenuItem bothType = new JMenuItem();

	private final JMenu EditLandmark = new JMenu();
	private final JMenuItem Undo = new JMenuItem();
	private final JMenuItem Redo = new JMenuItem(); 

	public static Affichage panneau;

	private final JMenuItem enregistrerMenu = new JMenuItem();


	private final JMenuItem addLandMarkMenu = new JMenuItem();
	
	
	
	
	private final JMenu EditingMenu = new JMenu();
	private final JMenuItem editSubMenu = new JMenuItem();
	private final JMenuItem cropMenu = new JMenuItem();

	private final JMenuItem resizeMenu = new JMenu();
	private final JMenuItem zoomIn = new JMenuItem();
	private final JMenuItem zoomOut = new JMenuItem();

	private final JMenuItem applyMethods = new JMenu();
	private final JMenuItem blackWhite = new JMenuItem();
	private final JMenuItem binary = new JMenuItem();
	private final JMenuItem skeleton = new JMenuItem();
	private final JMenuItem resize = new JMenuItem();
	private final JMenuItem landmarkPrediction = new JMenuItem();
	
	private JToolBar toolBar = new JToolBar();

	static JCanvas jc ;	

	

	public static JSlider slide = new JSlider();
	//Redimmensionnement d'images pour faire une toolBar fine

	ImageIcon squareT = new ImageIcon("assets/Carre_Blanc.jpg");

	ImageIcon square = new ImageIcon(squareT.getImage().getScaledInstance(40, 40,java.awt.Image.SCALE_SMOOTH));

	ImageIcon circleT = new ImageIcon("assets/Rond_Blanc.jpg");

	ImageIcon circle = new ImageIcon(circleT.getImage().getScaledInstance(40,40,java.awt.Image.SCALE_SMOOTH));


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





	public Cadre2(File fileImage, ImageWing im) {
		super();
		System.out.println("Lancement de Cadre2");
		System.out.println("1st File : "+fileImage);

		
	

		Go(im);
		try {

			System.out.println("Avant ajout image");
			panneau.ajouterImage(fileImage);
			panneau.setBounds(0, 0, this.getWidth(), this.getHeight());
			System.out.println("Ajout de l'image après la fonction AjouterImage");
			System.out.println("File : "+fileImage);
			creerMenu();

			//panneau.ajouterImage(new File(fileOuvrirImage.getSelectedFile().getAbsolutePath()));

		} catch (Throwable e) {
			e.printStackTrace();
		}
		//
	}




	private void creerMenu() {




		// construction du menu
		setJMenuBar(menuBar);	
		menuBar.add(fichierMenu);
		fichierMenu.setText("File");
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

		landMarkMenu.add(displayLandMark);
		displayLandMark.addActionListener((ActionListener)this);
		displayLandMark.setText("Display LandMark");

		displayLandMark.add(trueOnly);
		trueOnly.addActionListener((ActionListener)this);
		trueOnly.setText("True Only");

		displayLandMark.add(falseOnly);
		falseOnly.addActionListener((ActionListener)this);
		falseOnly.setText("False Only");

		displayLandMark.add(bothType);
		bothType.addActionListener((ActionListener)this);
		bothType.setText("Both type");


		landMarkMenu.add(EditLandmark);
		EditLandmark.addActionListener((ActionListener)this);
		EditLandmark.setText("Edit LandMark");

		EditLandmark.add(Undo);
		Undo.addActionListener((ActionListener)this);
		Undo.setText("Undo");
		Undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_MASK));

		EditLandmark.add(Redo);
		Redo.addActionListener((ActionListener)this);
		Redo.setText("Redo");
		Redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_MASK));

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

		EditingMenu.add(applyMethods);
		applyMethods.addActionListener((ActionListener)this);
		applyMethods.setText("Apply Methods");

		applyMethods.add(blackWhite);
		blackWhite.addActionListener((ActionListener)this);
		blackWhite.setText("Black & White");
		
		applyMethods.add(binary);
		binary.addActionListener((ActionListener)this);
		binary.setText("Binary");
		
		applyMethods.add(skeleton);
		skeleton.addActionListener((ActionListener)this);
		skeleton.setText("Skeleton");
		
		applyMethods.add(resize);
		resize.addActionListener((ActionListener)this);
		resize.setText("Resize");
		
		EditingMenu.add(landmarkPrediction);
		landmarkPrediction.addActionListener((ActionListener)this);
		landmarkPrediction.setText("Landmark Prediction");
		
		
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

			panData.setVisible(true);
			split.setVisible(true);
			panneau.setVisible(true);
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
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			c.add(panneau);
			panneau.add(jc);
			jc.setBounds(c.getX(), c.getY(),c.getWidth(), c.getHeight());
			jc.setVisible(true);
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
				System.out.println("fileOuvrirImage = "+fileOuvrirImage.getSelectedFile().getAbsolutePath());
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
				panneau.setVisible(true);

			} else if (cliqueMenu.getSource().equals(zoomOut)) {
				//panneau.reduireImage();

			} else if (cliqueMenu.getSource().equals(zoomIn)) {
				//panneau.agrandirImage();

			}else if(cliqueMenu.getSource().equals(addLandMarkMenu)){

				toolBarLandMark(true);

			}


		if(cliqueMenu.getSource() == squareButton) {
			System.out.println("squareButton");


		} 
		if(cliqueMenu.getSource() == circleButton) {
			System.out.println("circleButton");



		}else if (cliqueMenu.getSource().equals(trueOnly)) {

			Affichage.displayLandmark = 1;

		} else if (cliqueMenu.getSource().equals(falseOnly)) {

			Affichage.displayLandmark = 2;

		} else if (cliqueMenu.getSource().equals(bothType)) {

			Affichage.displayLandmark = 0;

		} else if (cliqueMenu.getSource().equals(Undo)) {

			System.out.println("Undo");
			Affichage.UndoLandmark();

		} else if (cliqueMenu.getSource().equals(Redo)) {

			System.out.println("Redo");
			//Affichage.RedoLandmark();
			
		} else if (cliqueMenu.getSource().equals(blackWhite)) {

			System.out.println("Black & White");

		} else if (cliqueMenu.getSource().equals(binary)) {

			System.out.println("Binary");

		} else if (cliqueMenu.getSource().equals(skeleton)) {

			System.out.println("Skeleton");
			
		} else if (cliqueMenu.getSource().equals(resize)) {

			System.out.println("Resize");
			new JSlidePanel();
			

		} else if (cliqueMenu.getSource().equals(landmarkPrediction)) {

			System.out.println("Landmark Prediction");
		} 
		
		
		


	}







	private void Go(ImageWing im) {


		c = this.getContentPane();

		jc = new JCanvas();
		panData = new PanelData();
		panneau = new Affichage(im);

		panData.setVisible(false);

		this.setSize(1100,650);


		new TestML(jc);


		panneau.add(jc);

		//panData.setVisible(true);

		jc.setBackground(Color.green);
		jc.setVisible(false);
		panneau.setVisible(true);

		c.add(panneau);


		this.setVisible(true);


	}







}












