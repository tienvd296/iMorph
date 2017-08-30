package application;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import businesslogic.ImageWing;
import businesslogic.Landmark;
import facade.Facade;
import ij.ImagePlus;
import ij.io.Opener;




public class Cadre2 extends JFrame implements ActionListener, WindowListener {




	private static final long serialVersionUID = 1L;
	final static JMenuBar menuBar = new JMenuBar();
	public final JMenu fichierMenu = new JMenu();
	public final JMenu landMarkMenu = new JMenu();



	public static final String CHEMIN = "C:\\Users\\Administrator\\Workspace_neon\\PM";


	private final JMenu displayLandMark = new JMenu();
	private final JMenuItem trueOnly = new JMenuItem();
	private final JMenuItem falseOnly = new JMenuItem();
	private final JMenuItem bothType = new JMenuItem();

	private final JMenu EditLandmark = new JMenu();
	private final JMenuItem Undo = new JMenuItem();
	private final JMenuItem Redo = new JMenuItem(); 

	public static Affichage panneau;

	private final JMenuItem enregistrerMenu = new JMenuItem();


	private final JRadioButtonMenuItem addLandMarkMenu = new JRadioButtonMenuItem();




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


	public static Container c;
	public static JSlider slide = new JSlider();

	ImageIcon squareT = new ImageIcon("assets/Carre_Blanc.jpg");

	ImageIcon square = new ImageIcon(squareT.getImage().getScaledInstance(40, 40,java.awt.Image.SCALE_SMOOTH));

	ImageIcon circleT = new ImageIcon("assets/Rond_Blanc.jpg");

	ImageIcon circle = new ImageIcon(circleT.getImage().getScaledInstance(40,40,java.awt.Image.SCALE_SMOOTH));


	static JPanel panShape = new JPanel();
	public  JComboBox<String> combo = new JComboBox<String>();
	public static JComboBox<String> combo2 = new JComboBox<String>();
	public static int Value ;

	public static PanelData panData;	

	private JButton squareButton = new JButton(square);
	private JButton circleButton = new JButton(circle);

	BufferedImage monImage = null;

	public static int startWidth;
	public static int startHeight;

	public static JSplitPane split;


	public static ArrayList<Landmark> ListLandmarkCadre = new ArrayList<Landmark>(); 
	protected static ArrayList<Landmark> ListLandmarkTemp = new ArrayList<Landmark>();
	public static ArrayList<drawCircle> ListCircle = new ArrayList<drawCircle>();
	public static ArrayList<drawCircle> ListCircleTemp = new ArrayList<drawCircle>();
	public static ArrayList<Landmark> SelectionLandmark = new ArrayList<Landmark>();
	public static int longueur;
	public static int hauteur;
	public ImageWing im;
	private JFrame instance_fenetre;
	private JFrame instance_fenetre2;

	
	public static float imHEIGHT;
	public static float imWIDTH;
	
	public static File fileImage;
	


	public ImageWing wing;
	public File file2;
	private final JScrollPane scrollPane = new JScrollPane();
	private final JScrollBar scrollBar = new JScrollBar();



	public Cadre2(File fileImage, ImageWing im){
		super();




		this.im = im;
		this.fileImage = fileImage;
		this.setTitle("Image Processing Window");
		this.addWindowListener(this);

		
		wing = im;
		file2 = fileImage;
	
		ListLandmarkCadre = im.getLandmarks();

		String test = im.getProperties().get("HEIGHT");
		float HEIGHT = Float.parseFloat(test);

		for(int i = 0; i < ListLandmarkCadre.size(); i++){

			float j = ListLandmarkCadre.get(i).getPosY();
			ListLandmarkCadre.get(i).setPosY( (int) (HEIGHT - j) );
		}



		SelectionLandmark.addAll(Affichage.SelectionLandmark);

		Go(im);

		try {

			panneau.printImageOnScreen(fileImage);

			panneau.setBounds(0, 0, this.getWidth(), this.getHeight());
			creerMenu();


		} catch (Throwable e) {
			e.printStackTrace();

		}

	}




	private void creerMenu() {

		setJMenuBar(menuBar);	
		menuBar.add(fichierMenu);
		fichierMenu.setText("File");


		fichierMenu.add(enregistrerMenu);
		enregistrerMenu.addActionListener((ActionListener)this);
		enregistrerMenu.setText("Save");
		enregistrerMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));
		menuBar.add(landMarkMenu);	
		landMarkMenu.setText("LandMark");

		landMarkMenu.add(addLandMarkMenu);
		addLandMarkMenu.addActionListener((ActionListener)this);
		addLandMarkMenu.setText("Show Coordinates");

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
		resizeMenu.setText("Zoom");


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

		
		
	
		getContentPane().add(toolBar, BorderLayout.PAGE_START);


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

		getContentPane().add(panneau);
		



		panData.setBounds(panneau.getX(), panneau.getY(), panneau.getWidth(), panneau.getHeight());
		panData.setVisible(false);

		toolBar.setVisible(false);



		squareButton.addActionListener(this);
		circleButton.addActionListener(this);






	}


	public void toolBarLandMark(Boolean b){

		if(b==true) {

			split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panneau, panData);
			split.setOneTouchExpandable(true);
			split.setDividerLocation(1600);
			this.getContentPane().add(split);


			panData.setVisible(true);
			split.setVisible(true);
			panneau.setVisible(true);

			toolBarEditing(false);


		} else if(b == false) {


			panData.setVisible(false);

			split.setVisible(true);
			this.getContentPane().remove(split);
			this.getContentPane().add(panneau);
			panneau.setVisible(true);
			repaint();
		}


	}

	public void toolBarEditing(Boolean b) {


		if(b==true){

			toolBar.setVisible(true);

			slide.setVisible(true);
			combo.setVisible(true);
			toolBarLandMark(false);
			addLandMarkMenu.setSelected(false);


		} else if(b == false)  {
			toolBar.setVisible(false);
		}

	}


	class FormeListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(combo.getSelectedItem() == "CIRCLE"){

				combo2.addItem("CIRCLE");
				Value = 0;
				//mouseAction(e, 1);
			}
			else if(combo.getSelectedItem() == "SQUARE"){


				combo2.addItem("SQUARE");
				Value=1;
			}
			else if(combo.getSelectedItem() == "ERASE"){


				combo2.addItem("ERASE");
				Value=2;
			}
		}  
	}    


	public void actionPerformed(ActionEvent cliqueMenu) {

		if (cliqueMenu.getSource().equals(enregistrerMenu)) {
			String test = im.getProperties().get("HEIGHT");
			float HEIGHT = Float.parseFloat(test);

			for(int i = 0; i < ListLandmarkCadre.size(); i++){

				float j = ListLandmarkCadre.get(i).getPosY();
				ListLandmarkCadre.get(i).setPosY( (int) (HEIGHT - j) );
			}
			
			ListLandmarkTemp.removeAll(ListLandmarkTemp);
			ListLandmarkTemp =new ArrayList<Landmark>();//new ArrayList<Landmark>(ListLandmarkCadre);

			for(Landmark a : ListLandmarkCadre)
			{
				ListLandmarkTemp.add(new Landmark(a.posX, a.posY,a.isLandmark));
			}
		
			Facade.saveProject();
			System.out.println("Saved");

			for(int i = 0; i < ListLandmarkCadre.size(); i++){

				float j = ListLandmarkCadre.get(i).getPosY();
				ListLandmarkCadre.get(i).setPosY( (int) (HEIGHT - j) );
			}


		} else

			if (cliqueMenu.getSource().equals(editSubMenu)) {
				//	panneau.agrandirImage();
				// Lance la correction, toolbar	
				toolBarEditing(true);
				getContentPane().add(panneau);
				panneau.setVisible(true);

			} else if (cliqueMenu.getSource().equals(zoomOut)) {
				panneau.zoomOut();

			} else if (cliqueMenu.getSource().equals(zoomIn)) {
				panneau.zoomIn();

			}else if(cliqueMenu.getSource().equals(addLandMarkMenu)){

				if(addLandMarkMenu.isSelected() == true){

					toolBarLandMark(true);

				}else 
				{
					toolBarLandMark(false);
					panData.setVisible(false);
					panneau.setVisible(true);

					repaint();

				}

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
			Affichage.RedoLandmark();

		} else if (cliqueMenu.getSource().equals(blackWhite)) {

			System.out.println("Black & White");
			

		} else if (cliqueMenu.getSource().equals(binary)) {

			System.out.println("Binary");
			this.dispose();
			
			String test = im.getProperties().get("HEIGHT");
			float HEIGHT = Float.parseFloat(test);

			System.out.println("fileImage path  "+ fileImage.getAbsolutePath().toString());
			
			String result = fileImage.getAbsolutePath().toString();
			String[] commande = {"OpenCV_Test.exe", CHEMIN, result};
			System.out.println("GetPath : "+result);
			Facade.lectureEXE(commande);
						
			System.out.println("Image Path "+ result);
			

			for(int i = 0; i < ListLandmarkCadre.size(); i++){

				float j = ListLandmarkCadre.get(i).getPosY();
				ListLandmarkCadre.get(i).setPosY( (int) (HEIGHT - j) );
			}
			
			new Cadre2(file2, wing);
			
			
		} else if (cliqueMenu.getSource().equals(skeleton)) {

			System.out.println("Skeleton");

		} else if (cliqueMenu.getSource().equals(resize)) {

			System.out.println("Resize");
		
			
			
		} else if (cliqueMenu.getSource().equals(landmarkPrediction)) {

			System.out.println("Landmark Prediction");
			String result = fileImage.getAbsolutePath().toString();
			String[] commande = {"landmarkPrediction.exe", result, "HOG", "10"};
			System.out.println("GetPath : "+result);
			Facade.landmarkPrediction(commande);
			
			
		} 
	}
	

	
	
	private void Go(ImageWing im) {


		c = this.getContentPane();

		panData = new PanelData();
		panneau = new Affichage(im);



		/*		String test = im.getProperties().get("WIDTH");
		String test2 = im.getProperties().get("HEIGHT");
		// Les donnees sont des String donc on les convertis
		float WIDTH2 = Float.parseFloat(test);
		float HEIGHT2 = Float.parseFloat(test2);
		 */	
		//récuperer la dimension de l'écran
		Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize();
		longueur = tailleMoniteur.width * 2/3;
		hauteur = tailleMoniteur.height * 2/3;
		//régler la taille de JFrame à 2/3 la taille de l'écran


		this.setSize(longueur,hauteur);

		this.setLocationRelativeTo(null);
		startWidth = this.getWidth();
		startHeight = this.getHeight();


		panneau.setVisible(true);
		
	
		String imHeight = im.getProperties().get("HEIGHT");
		imHEIGHT = Float.parseFloat(imHeight);
		
		String imWidth = im.getProperties().get("WIDTH");
		imWIDTH = Float.parseFloat(imWidth);
		
	
		
		c.add(panneau);
		panneau.setPreferredSize(new Dimension((int) imWIDTH,(int) imHEIGHT));
		
		getContentPane().add(scrollPane, BorderLayout.SOUTH);
		
		getContentPane().add(scrollBar, BorderLayout.WEST);
		scrollPane.setViewportView(panneau);
		
		this.getContentPane().add(scrollPane.add(panneau));
		
		//getContentPane().add(scrollBar_1, BorderLayout.WEST);
		//scrollBar_1.add(panneau);
		
		
		
		this.setVisible(true);


	}




	@Override
	public void windowOpened(WindowEvent e) {
		System.out.println("WINDOW OPENED");
		ListLandmarkTemp.removeAll(ListLandmarkTemp);
		ListLandmarkTemp =new ArrayList<Landmark>();//new ArrayList<Landmark>(ListLandmarkCadre);

		for(Landmark a : ListLandmarkCadre)
		{
			ListLandmarkTemp.add(new Landmark(a.posX, a.posY,a.isLandmark));
		}
		for(int i = 0 ; i< ListLandmarkTemp.size() ; i++){
			System.out.println(" LIST_TEMP IN WINDOWOPEN"+ListLandmarkTemp.get(i).toString() );
		}
	}




	@Override
	public void windowClosing(WindowEvent e) {

		ListLandmarkCadre = Affichage.ListLandmark;

		for(int i = 0 ; i< ListLandmarkCadre.size(); i++) {
		

			if(ListLandmarkTemp.size() == 0 && ListLandmarkCadre.size() !=0){

				int option =JOptionPane.showConfirmDialog(null, "Do you want to save before Leave?", "Attention",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

				if(option == JOptionPane.YES_OPTION){
					System.out.println("YES");
					Facade.saveProject();
					i = ListLandmarkCadre.size();
				}
				else if(option == JOptionPane.NO_OPTION ){
					System.out.println("NO");
					i = ListLandmarkCadre.size();
				}

				else if(option == JOptionPane.CANCEL_OPTION ){
					System.out.println("Cancel");
					instance_fenetre2 = null;
					instance_fenetre2.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					i = ListLandmarkCadre.size();
				}
				else if(option == JOptionPane.CLOSED_OPTION ){
					System.out.println("CLOSE");
					instance_fenetre = null;
					instance_fenetre.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					i = ListLandmarkCadre.size();
				}
				i = ListLandmarkCadre.size();

			}

			if(ListLandmarkCadre.size() != 0 && ListLandmarkTemp.size() != 0 ){

				System.out.println("ListLandmark = "+ListLandmarkCadre.size()+ " ListLandmarkTemp = " +ListLandmarkTemp.size());
				if( ListLandmarkCadre.get(i).getPosX() != ListLandmarkTemp.get(i).getPosX() || ListLandmarkCadre.size() != ListLandmarkTemp.size() || ListLandmarkCadre.get(i).getIsLandmark() != ListLandmarkTemp.get(i).getIsLandmark() || ListLandmarkTemp.size() ==0){

					if(SelectionLandmark != null){
						ListLandmarkCadre.addAll(SelectionLandmark);
						System.out.println("Selection -> ListLandmarkCadre");
					}
					int option =JOptionPane.showConfirmDialog(null, "Do you want to save before Leave?", "Attention",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

					if(option == JOptionPane.YES_OPTION){
						System.out.println("YES");
						Facade.saveProject();
						i = ListLandmarkCadre.size();
					}
					else if(option == JOptionPane.NO_OPTION ){
						System.out.println("NO");
						i = ListLandmarkCadre.size();
					}

					else if(option == JOptionPane.CANCEL_OPTION ){
						System.out.println("Cancel");
						instance_fenetre2 = null;
						instance_fenetre2.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					}
					else if(option == JOptionPane.CLOSED_OPTION ){
						System.out.println("CLOSE");
						instance_fenetre = null;
						instance_fenetre.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					}
				}else 
					System.out.println("Nothing to Do");
			}else
				System.out.println("ListLandmark = "+ListLandmarkCadre.size()+ " ListLandmarkTemp = " +ListLandmarkTemp.size());
		}


		String test = im.getProperties().get("HEIGHT");
		float HEIGHT = Float.parseFloat(test);

		for(int i = 0; i < ListLandmarkCadre.size(); i++){

			float j = ListLandmarkCadre.get(i).getPosY();
			ListLandmarkCadre.get(i).setPosY( (int) (HEIGHT - j) );
		}
		menuBar.removeAll();
		
	}



	@Override
	public void windowClosed(WindowEvent e) {

	}




	@Override
	public void windowIconified(WindowEvent e) {


	}




	@Override
	public void windowDeiconified(WindowEvent e) {

	}




	@Override
	public void windowActivated(WindowEvent e) {

	}




	@Override
	public void windowDeactivated(WindowEvent e) {


	}







}












