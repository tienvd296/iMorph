package application;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSlider;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import businesslogic.ImageWing;
import businesslogic.Landmark;
import facade.Facade;
import ij.ImagePlus;
import ij.io.Opener;



public class Affichage extends JPanel implements MouseListener, ActionListener, MouseMotionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPopupMenu jpm = new JPopupMenu();

	private JMenuItem trueLandmark = new JMenuItem("Add True Landmark");      
	private JMenuItem falseLandmark = new JMenuItem("Add False Landmark");

	private JMenuItem trueLandmark2 = new JMenuItem("Set True Landmark");      
	private JMenuItem falseLandmark2 = new JMenuItem("Set False Landmark");
	public JMenuItem suppLandmark = new JMenuItem("Delete Landmark");

	public JMenu imgFunction = new JMenu("Function");
	public JMenuItem Normal = new JMenuItem("Normal");
	public JMenuItem black = new JMenuItem("black and white");
	

	public static ArrayList<Landmark> ListLandmark = new ArrayList<Landmark>();

	public static ArrayList<Landmark> SelectionLandmark = new ArrayList<Landmark>();
	public static ArrayList<Landmark> UndoListLandmark = new ArrayList<Landmark>();

	public static List<Graphics> graphic = new LinkedList<Graphics>();
	public static HashSet<drawCircle> set = new HashSet<drawCircle>() ; // Utile pour eliminer les doublons
	public static ArrayList<drawCircle> ListCircle = new ArrayList<drawCircle>();
	public static ArrayList<drawCircle> ListTempCircle = new ArrayList<drawCircle>();


	private double X = 0;
	private double Y = 0;

	public static int WIDTH = 0;
	public static int HEIGHT = 0;
	public int indexOfSelectedLandmark = 0;
	public int indexOfSelectedCircle = 0;
	public static int SelectionLandmarkSize = 0;
	public int size = ListLandmark.size();
	public static int displayLandmark = 0;
	public static int nbTempUndoList = 0;
	public static int UndoListSize = UndoListLandmark.size();
	public static int compteur = SelectionLandmarkSize;

	public static JMenu fileOpencv = new JMenu();
	public static JMenuItem blackNwhite = new JMenuItem();
	public static JMenuItem normalImage = new JMenuItem();
	public static JMenuItem squeleton = new JMenuItem("Squelton");
	public static JMenuItem functionTest = new JMenuItem();

	public float WIDTH2;
	public float HEIGHT2;
	public float temp = 0;
	public static float sliderValue = 100;
	public static float getSliderValue() {
		return sliderValue;
	}

	public String test;
	public String test2;
	public Landmark selectedLandmark;
	public Landmark selectedLandmark2;
	public boolean isCtrlDown = false;


	public  int onlyOnceForconnection = 0;
	public drawCircle selectedCircle;

	private ImageWing im;
	public static String imHeight;
	public static float imHEIGHT;
	public static float imWIDTH;

	BufferedImage monImage;
	BufferedImage imgTemp;
	public static BufferedImage imgTest;
	MouseEvent e;

	public JScrollPane scroll = new JScrollPane();
	public JLabel lab = new JLabel();

	public Affichage(ImageWing im) {


		this.im = im;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setFocusable(true);



		String imHeight = im.getProperties().get("HEIGHT");
		imHEIGHT = Float.parseFloat(imHeight);

		String imWidth = im.getProperties().get("WIDTH");
		imWIDTH = Float.parseFloat(imWidth);



		this.setPreferredSize(new Dimension((int) imWIDTH,(int) imHEIGHT));

		//this.add(Cadre2.scrollBar_1);

		this.addKeyListener( new KeyListener() {


			public void keyTyped(KeyEvent e) {
				System.out.println("TEST CONTROL");

			}

			public void keyReleased(KeyEvent e) {

				if(e.getKeyCode() == KeyEvent.VK_CONTROL){
					isCtrlDown=false;
					System.out.println("TEST CONTROL Release");
				}
			}

			public void keyPressed(KeyEvent e) {

				if(e.getKeyCode() == KeyEvent.VK_CONTROL){
					isCtrlDown=true;
					System.out.println("TEST CONTROL");
				}

			}
		});

		ListLandmark = im.getLandmarks();

		sliderValue = JSlidePanel.sliderValue;
		trueLandmark.addActionListener((ActionListener)this);
		falseLandmark.addActionListener((ActionListener)this);
		trueLandmark2.addActionListener((ActionListener)this);
		falseLandmark2.addActionListener((ActionListener)this);
		suppLandmark.addActionListener((ActionListener)this);
		black.addActionListener((ActionListener)this);
		Normal.addActionListener((ActionListener)this);
		imgFunction.addActionListener((ActionListener)this);
		setLayout(null);

		Cadre2.menuBar.add(Affichage.fileOpencv);
		Affichage.fileOpencv.setText("OpenCV's Methods");

		Affichage.fileOpencv.add(Affichage.normalImage);
		Affichage.normalImage.setText("Image Normal");
		Affichage.normalImage.addActionListener((ActionListener)this);

		Affichage.fileOpencv.add(Affichage.blackNwhite);
		Affichage.blackNwhite.setText("Black & White");
		Affichage.blackNwhite.addActionListener((ActionListener)this);
		
		Affichage.fileOpencv.add(Affichage.squeleton);
		Affichage.squeleton.setText("Squeleton");
		Affichage.squeleton.addActionListener((ActionListener)this);

		Affichage.fileOpencv.add(Affichage.functionTest);
		Affichage.functionTest.setText("Function Test");
		Affichage.functionTest.addActionListener((ActionListener)this);

		
		

		this.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent event){

				if(event.isPopupTrigger()){       

					jpm.add(trueLandmark);
					jpm.add(falseLandmark);
					jpm.add(trueLandmark2);
					jpm.add(falseLandmark2);
					jpm.add(suppLandmark);
					jpm.add(imgFunction);
					imgFunction.add(black);
					imgFunction.add(Normal);


					if(SelectionLandmark.size() != 0 ){

						jpm.remove(trueLandmark);
						jpm.remove(falseLandmark);
						X = event.getX();
						Y = event.getY(); 

						jpm.show(Cadre2.panneau, event.getX(), event.getY());

					}else {

						jpm.remove(trueLandmark2);
						jpm.remove(falseLandmark2);
						jpm.remove(suppLandmark);
						X = event.getX();
						Y = event.getY(); 

						jpm.show(Cadre2.panneau, event.getX(), event.getY());
					}
				}
			}
		});

		imgTest = monImage;
	}

	public static void UndoLandmark(){

		if(nbTempUndoList < UndoListSize ){

			int tmp = UndoListSize - nbTempUndoList;

			for(int i =0 ; i< tmp; i++){

				UndoListLandmark.add(ListLandmark.get(ListLandmark.size() -1));
				ListLandmark.remove(ListLandmark.get(ListLandmark.size() -1));

				nbTempUndoList--;
			}
		}

		else if(SelectionLandmark.size() != 0){

			ListLandmark.add(SelectionLandmark.get(SelectionLandmark.size() -1));
			UndoListLandmark.add(SelectionLandmark.get(SelectionLandmark.size() -1));

			SelectionLandmark.remove(SelectionLandmark.get(SelectionLandmark.size() -1));
			System.out.println("SelectionLandmarkSize = "+SelectionLandmarkSize);
		}
		else {

			if(ListLandmark.size() ==0 && SelectionLandmark.size() == 0){
				JOptionPane.showMessageDialog(null, "You have nothing to Undo", "Warning", JOptionPane.WARNING_MESSAGE);

			} else

				UndoListLandmark.add(ListLandmark.get(ListLandmark.size() -1));
			ListLandmark.remove(ListLandmark.get(ListLandmark.size() -1));
			System.out.println("SelectionLandmarkSize = "+SelectionLandmarkSize);
		}


	}



	public static void RedoLandmark() {

		if(UndoListLandmark.size() == 0){
			JOptionPane.showMessageDialog(null, "You have nothing to Redo", "Warning", JOptionPane.WARNING_MESSAGE);

		}
		else{
			if(SelectionLandmarkSize != 0 && SelectionLandmarkSize > UndoListLandmark.size() ){

				while (compteur != 0) {
					// Tant que la liste de selection n'est pas vide on la decremente 
					//Sinon on sortirai trop tot de ce cas 
					SelectionLandmark.add(UndoListLandmark.get(SelectionLandmarkSize));
					UndoListLandmark.remove(UndoListLandmark.get(UndoListLandmark.size() -1));
					compteur  = compteur-1;
					System.out.println("SelectionLandmarkSize = "+SelectionLandmarkSize);
				} 
				SelectionLandmarkSize = 0;
			}
			else if ( UndoListLandmark.size() - SelectionLandmarkSize > 0){

				ListLandmark.add(UndoListLandmark.get(UndoListLandmark.size() -1));
				UndoListLandmark.remove(UndoListLandmark.get(UndoListLandmark.size() -1));
				System.out.println("SelectionLandmarkSize = "+SelectionLandmarkSize);
			}
			else {

				ListLandmark.add(UndoListLandmark.get(UndoListLandmark.size() -1));
				UndoListLandmark.remove(UndoListLandmark.get(UndoListLandmark.size() -1));
				System.out.println("SelectionLandmarkSize = "+SelectionLandmarkSize);
			}
		}
	}

	public static void changeCoordinates(){

		for(int i = 0; i < ListLandmark.size(); i++){

			float j = ListLandmark.get(i).getPosY();
			ListLandmark.get(i).setPosY( (int) (imHEIGHT - j) );
		}
	}


	public void printCoordinates() {



		for(int i = 0; i < ListLandmark.size(); i++){

			float j = ListLandmark.get(i).getPosY();
			ListLandmark.get(i).setPosY( (int) (imHEIGHT - j) );
		}


		PanelData.jText.setText(" "+ListLandmark.toString().replace('[', ' ').replace(',', ' ').replace(']', ' ')+  "\n");

		for(int i = 0; i < ListLandmark.size(); i++){

			float j = ListLandmark.get(i).getPosY();
			ListLandmark.get(i).setPosY( (int) (imHEIGHT - j) );
		}



	}




	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		repaint();
		test = im.getProperties().get("WIDTH");
		test2 = im.getProperties().get("HEIGHT");
		WIDTH2 = Float.parseFloat(test);
		HEIGHT2 = Float.parseFloat(test2);

		printCoordinates();
		g.drawImage(monImage,0 ,0 , null);


		int longeur =  Cadre2.longueur;
		int hauteur =  Cadre2.hauteur;


		WIDTH = monImage.getWidth();
		HEIGHT = monImage.getHeight();


		if(onlyOnceForconnection == 0){


			while( WIDTH > longeur || HEIGHT > hauteur ){
				zoomOut();
				WIDTH = monImage.getWidth();
				HEIGHT = monImage.getHeight();
			}

			onlyOnceForconnection = 1;
		}

		if( ListLandmark.size() != size ){

			for(int i = 0; i<ListLandmark.size() ; i++){

				float XX = ListLandmark.get(i).getPosX();
				float YY = ListLandmark.get(i).getPosY();

				// Recuperer le poucentage de difference entre l'image redimensionne et l'image originale

				float width = WIDTH / WIDTH2;
				float height = HEIGHT / HEIGHT2;

				if ( width != 1 && height !=1) {

					XX = XX * width;      // Utile pour conserver les position malgre un redimensionnement
					YY = YY * height;


					boolean isLandmark = ListLandmark.get(i).getIsLandmark();
					ListTempCircle.add(new drawCircle(g,(int) XX,(int) YY, 5, isLandmark,0,displayLandmark));

					set.addAll(ListTempCircle) ;
					set.clone();

					ListCircle = new ArrayList<drawCircle>(set);

					repaint();
				}
				else {

					XX = (XX / WIDTH2)*100; 
					YY = (YY / HEIGHT2)*100;

					float X2 = (XX * WIDTH)/100;
					float Y2 = (YY * HEIGHT)/100;

					boolean isLandmark = ListLandmark.get(i).getIsLandmark();


					ListTempCircle.add(new drawCircle(g,(int) X2,(int) Y2, 5, isLandmark,0,displayLandmark));
					set.addAll(ListTempCircle) ;

					set.clone();
					ListCircle = new ArrayList<drawCircle>(set);
					repaint();
				}
			}

			size = ListLandmark.size();

		}

		int rayon = circleSize();

		for(int i = 0; i<ListLandmark.size() ; i++){

			float XX = ListLandmark.get(i).getPosX();
			float YY = ListLandmark.get(i).getPosY();

			XX = (XX / WIDTH2)*100; 
			YY = (YY / HEIGHT2)*100;

			float X2 = (XX * WIDTH)/100;
			float Y2 = (YY * HEIGHT)/100;

			boolean isLandmark = ListLandmark.get(i).getIsLandmark();

			new drawCircle(g,(int) X2,(int) Y2, rayon, isLandmark,0,displayLandmark);

		}



		for(int i = 0 ; i<SelectionLandmark.size(); i++) {

			float X = SelectionLandmark.get(i).getPosX();
			float Y = SelectionLandmark.get(i).getPosY();

			float width = WIDTH / WIDTH2;
			float height = HEIGHT / HEIGHT2;

			if ( width != 1 && height !=1) {
				X = X * width;
				Y = Y * height;


				boolean isLandmark = SelectionLandmark.get(i).getIsLandmark();


				ListTempCircle.add(new drawCircle(g,(int) X,(int) Y, 5, isLandmark, 1 , displayLandmark));
				set.addAll(ListTempCircle) ;
				set.clone();
				ListCircle = new ArrayList<drawCircle>(set);
				repaint();
			}
			else {

				X = (X / WIDTH2)*100; 
				Y = (Y / HEIGHT2)*100;

				float X2 = (X * WIDTH)/100;
				float Y2 = (Y * HEIGHT)/100;

				boolean isLandmark = SelectionLandmark.get(i).getIsLandmark();

				ListTempCircle.add(new drawCircle(g,(int) X2,(int) Y2, 5, isLandmark,1,displayLandmark));
				set.addAll(ListTempCircle) ;
				set.clone();
				ListCircle = new ArrayList<drawCircle>(set);
				repaint();
			}

		}
		repaint();

	}




	private int circleSize() {

		int W = monImage.getWidth();
		int H = monImage.getHeight();

		if(W < 300 || H < 300){

			return 2;

		}else if(W < 750 && W > 500 || H < 500){

			return 3;

		}else if(W < 1000 && W > 750 || H <750 && H > 500){

			return 4;

		}else if(W < 1250 && W > 1000 || H <1000 && H > 750){

			return 5;

		}else if (W < 1500 && W > 1250 ||H <1250 && H > 1000 ){

			return 6;

		}else if(W < 2000 && W > 1500 || H <1500 && H > 1250){

			return 7;
		}
		return 5;
	}



	public int getValue(JSlider slide) {
		int val = slide.getValue();
		return val;
	}



	protected void zoomOut()
	{

		BufferedImage imageReduite = new BufferedImage((int) (monImage.getWidth()*0.9) ,(int) (monImage.getHeight()*0.9), monImage.getType());
		AffineTransform reduire = AffineTransform.getScaleInstance(0.9, 0.9);
		int interpolation = AffineTransformOp.TYPE_BICUBIC;
		AffineTransformOp retaillerImage = new AffineTransformOp(reduire, interpolation);
		retaillerImage.filter(monImage, imageReduite );


		monImage = imageReduite ;

		
		repaint();
		
		

	}


	protected void zoomIn()
	{

		BufferedImage imageZoomer = new BufferedImage((int) (monImage.getWidth()*1.1)  ,(int)(monImage.getHeight()*1.1), monImage.getType());
		AffineTransform agrandir = AffineTransform.getScaleInstance(1.1,1.1);
		int interpolation = AffineTransformOp.TYPE_BICUBIC;
		AffineTransformOp retaillerImage = new AffineTransformOp(agrandir, interpolation);
		retaillerImage.filter(monImage, imageZoomer );
		monImage = imageZoomer ;
		repaint();
	}



	public void BlackAndWhite(){

		BufferedImage blackWhite = new BufferedImage(monImage.getWidth(), monImage.getHeight(), BufferedImage.TYPE_BYTE_BINARY);

		monImage = blackWhite;
		repaint();
	}


	public void GreyScale(){
		imgTemp = monImage;
		ColorConvertOp op = new ColorConvertOp( ColorSpace.getInstance(ColorSpace.CS_GRAY), null); 
		BufferedImage imageGrise = op.filter(monImage,null);
		monImage = imageGrise;
		repaint();
	}

	public void imageNormal() {

		monImage = imgTemp;
		onlyOnceForconnection = 0;
		repaint();
	}
	protected void printImageOnScreen(File fichierImage)
	{  

		String result = fichierImage.getAbsolutePath().toString();
		ImagePlus imagePlus = new Opener().openTiff(result, "");
		BufferedImage bufferedImage = imagePlus.getBufferedImage();


		System.out.println("Chargement image dans la fonction Ajouter Image");
		System.out.println("File printImageOnScreen : "+fichierImage);
		monImage = bufferedImage;

		imgTemp = monImage;
		System.out.println("Image 2 : "+monImage);
	}



	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("MouseClicked  X  : "+e.getX()+ " Y = "+e.getY());


	}

	public int TESTX;
	public int TESTY;

	@Override
	public void mouseReleased(MouseEvent e) {
		float width = WIDTH / WIDTH2;
		float height = HEIGHT / HEIGHT2;
		if(selectedCircle != null || selectedLandmark != null || selectedLandmark2 != null) {



			selectedCircle.setxCenter((int) (e.getX()/ width));
			selectedCircle.setyCenter((int) (e.getY()/ height));

			selectedLandmark.setPosX((int) (e.getX()/ width));
			selectedLandmark.setPosY((int) (e.getY()/ height));

		}

		TESTX = (int) (e.getX()/width);
		TESTY = (int) (e.getY() / height);
		System.out.println(" mouseRelease X = "+e.getX()+ " Y = "+e.getY());
		selectedCircle = null;
		selectedLandmark = null;
		selectedLandmark2 = null;


	}



	@Override
	public void mouseMoved(MouseEvent e) {



	}


	@Override
	public void mouseEntered(MouseEvent e) {


	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {

		//System.out.println(" mouseDragged X = "+e.getX()+ " Y = "+e.getY());
		if(selectedCircle != null || selectedLandmark != null || selectedLandmark2 != null) {

			float width = WIDTH / WIDTH2;
			float height = HEIGHT / HEIGHT2;

			selectedCircle.setxCenter((int) (e.getX()/ width));
			selectedCircle.setyCenter((int) (e.getY()/ height));

			selectedLandmark.setPosX((int) (e.getX()/ width));
			selectedLandmark.setPosY((int) (e.getY()/ height));

		}
		repaint();

	}





	@Override
	public void mousePressed(MouseEvent e) {

		if(isCtrlDown == true) {

			System.out.println("CTRL PRESSED !!!!!");
			for(int j = 0; j<ListLandmark.size() ; j++){

				float width = WIDTH / WIDTH2;
				float height = HEIGHT / HEIGHT2;

				float moinsX = e.getX()-9;
				float plusX = e.getX()+9;

				float moinsY = e.getY()-9;
				float plusY = e.getY()+9;

				float LAND = ListLandmark.get(j).getPosX();
				float LAND2 = ListLandmark.get(j).getPosY();


				if( (moinsX/width) < LAND && LAND < (plusX/width)  && (moinsY / height) < LAND2 && LAND2 < (plusY/height) ){
					nbTempUndoList = ListLandmark.size();

					SelectionLandmark.add(ListLandmark.get(j));
					SelectionLandmarkSize = SelectionLandmarkSize+1;
					System.out.println("SelectionLandmarkSize = "+SelectionLandmarkSize);
					ListLandmark.remove(j);

					repaint();
				}

			}

		}
		else {
			float width = WIDTH / WIDTH2;
			float height = HEIGHT / HEIGHT2;	
			int i = 0;

			for(int j = 0; j<ListCircle.size() ; j++){

				for( i = 0; i<ListLandmark.size(); i++){



					float moinsX = e.getX()-9;
					float plusX = e.getX()+9;

					//System.out.println("Size ListLandmark : " +ListLandmark.size());

					float moinsY = e.getY()-9;
					float plusY = e.getY()+9;

					float LAND = ListLandmark.get(i).getPosX();
					float LAND2 = ListLandmark.get(i).getPosY();


					if( (moinsX/ width) < LAND && LAND < (plusX/height)  &&( moinsY/ width) < LAND2 && LAND2 < (plusY/height) ){

						selectedCircle = ListCircle.get(j);
						selectedLandmark = ListLandmark.get(i);


						indexOfSelectedLandmark = i;
						indexOfSelectedCircle = j;

						ListLandmark.get(i).setPosX(TESTX);
						ListLandmark.get(i).setPosY(TESTY);

						ListCircle.get(j).setxCenter(TESTX);
						ListCircle.get(j).setyCenter(TESTY);
						repaint();


					}
				}
			}
		}
	}

	public static void ChangeTypeLandmark(boolean b ){

		System.out.println(" SALUUUUUUT");
		for(int j = 0; j<SelectionLandmark.size() ; j++){
			nbTempUndoList = ListLandmark.size();
			ListLandmark.add(new Landmark((float)SelectionLandmark.get(j).getPosX(),(float) SelectionLandmark.get(j).getPosY(), b));
		}
		SelectionLandmark.removeAll(SelectionLandmark);
	} 



	@Override
	public void actionPerformed(ActionEvent e) {
		WIDTH = monImage.getWidth();
		HEIGHT = monImage.getHeight();


		test = im.getProperties().get("WIDTH");
		test2 = im.getProperties().get("HEIGHT");
		// Les donnees sont des String donc on les convertis
		float WIDTHREAL = Float.parseFloat(test);
		float HEIGHTREAL = Float.parseFloat(test2);


		if (e.getSource().equals(trueLandmark))
		{
			/*
			boolean type = ChangeTypeLandmark(true);
			System.out.println(" Type = "+type);
			if (type == true){
				System.out.println("State changed");

			}else {
			 */
			//	X = (X/WIDTH);
			//	Y = (Y/HEIGHT);

			X = (X/WIDTH);
			Y = (Y/HEIGHT);
			if(X> 1 || Y > 1) {

				JOptionPane.showMessageDialog(null, "You are not on the image", "Attention", JOptionPane.WARNING_MESSAGE);
			}

			else if( X <=1 || Y <= 1){

				if ( WIDTH != WIDTHREAL && HEIGHT != HEIGHTREAL){

					float tempX = WIDTH / WIDTHREAL;
					float tempY = HEIGHT / HEIGHTREAL;

					X = (X * WIDTH);     //Passage en pixels
					Y = (Y * HEIGHT);	 //Passage en pixels

					X = X /tempX;		 
					Y = Y /tempY;
					System.out.println(" ADD LANDMARK ");
					ListLandmark.add(new Landmark((float)X,(float) Y, true));

					Facade.addLandmark(im, ListLandmark);

				}else{


					X = (X * WIDTH); 
					Y = (Y * HEIGHT);

					System.out.println("2eme : " +X );
					ListLandmark.add(new Landmark((float)X,(float) Y, true));



					Facade.addLandmark(im, ListLandmark);

					System.out.println("True = "+X+ "  "+Y);
				}
			}
		}else if (e.getSource().equals(falseLandmark))
		{
			System.out.println("FALSE ");
			/*boolean type = ChangeTypeLandmark(false);
			System.out.println(" Type = "+type);
			if (type == false){
				System.out.println("State changed");

			}else {
			 */
			X = (X/WIDTH);
			Y = (Y/HEIGHT); 

			if(X> 1 || Y > 1) {


				JOptionPane.showMessageDialog(null, "You are not in the image", "Attention", JOptionPane.WARNING_MESSAGE);

			}
			else if( X <=1 || Y <= 1){

				if ( WIDTH != WIDTHREAL && HEIGHT != HEIGHTREAL){

					float tempX = WIDTH / WIDTHREAL;
					float tempY = HEIGHT / HEIGHTREAL;

					X = (X * WIDTH); 
					Y = (Y * HEIGHT);

					X = X /tempX;
					Y = Y /tempY;
					ListLandmark.add(new Landmark((float)X,(float) Y, false));

					Facade.addLandmark(im, ListLandmark);

				}else{


					X = (X * WIDTH); 
					Y = (Y * HEIGHT);

					ListLandmark.add(new Landmark((float) X, (float) Y, false));	

					Facade.addLandmark(im, ListLandmark);

					System.out.println("False = "+X+ "  "+Y);

				}
			}
		}else if(e.getSource().equals(trueLandmark2)){

			System.out.println(" LOL TRUE");
			ChangeTypeLandmark(true);

		}
		else if(e.getSource().equals(falseLandmark2)){
			System.out.println(" LOL FALSE");
			ChangeTypeLandmark(false);


		}else if (e.getSource().equals(suppLandmark)){

			System.out.println("Delete");
			if(SelectionLandmark.size() != 0){

				for(int i=0; i<SelectionLandmark.size(); i++){
					SelectionLandmark.remove(i);
					SelectionLandmark.removeAll(SelectionLandmark);
					SelectionLandmarkSize =0;
					System.out.println("SelectionLandmarkSize = "+SelectionLandmarkSize);
					repaint();

				}
			}
		}else if(e.getSource().equals(squeleton)){
			System.out.println(" Skeleton");
			String result = Cadre2.fileImage.getAbsolutePath().toString();
			ImagePlus imagePlus = new Opener().openTiff(result, "");
			BufferedImage bufferedImage = imagePlus.getBufferedImage();

			Mat Mat = Facade.bufferedImageToMat(bufferedImage, im);

			Mat mat2 = Mat;
			Imgproc.cvtColor(Mat, mat2, Imgproc.COLOR_Lab2BGR);
			bufferedImage = null;

			bufferedImage = Facade.mat2Img(mat2, im);

			monImage = bufferedImage;
			onlyOnceForconnection = 0;

			System.out.println("Finis");
			

		}else if(e.getSource().equals(Normal)){
			System.out.println(" Normal");
			imageNormal();

		}else if(e.getSource().equals(black)){
			System.out.println(" Normal");
			GreyScale();

		}else if(e.getSource().equals(normalImage)){
			System.out.println(" Normal");
			imageNormal();

		}else if(e.getSource().equals(blackNwhite)){
			System.out.println(" blackNwhite ");

			String result = Cadre2.fileImage.getAbsolutePath().toString();
			ImagePlus imagePlus = new Opener().openTiff(result, "");
			BufferedImage bufferedImage = imagePlus.getBufferedImage();

			Mat Mat = Facade.bufferedImageToMat(bufferedImage, im);

			Mat mat2 = Mat;
			Imgproc.cvtColor(Mat, mat2, Imgproc.COLOR_BGR2GRAY);
			bufferedImage = null;

			bufferedImage = Facade.mat2Img(mat2, im);

			monImage = bufferedImage;
			onlyOnceForconnection = 0;

			System.out.println("Finis");

		}else if(e.getSource().equals(functionTest)){
			System.out.println(" Skeleton");
			String result = Cadre2.fileImage.getAbsolutePath().toString();
			ImagePlus imagePlus = new Opener().openTiff(result, "");
			BufferedImage bufferedImage = imagePlus.getBufferedImage();

			Mat Mat = Facade.bufferedImageToMat(bufferedImage, im);

			Mat mat2 = Mat;
			Imgproc.cvtColor(Mat, mat2, Imgproc.COLOR_RGB2YUV);
		
			bufferedImage = null;

			bufferedImage = Facade.mat2Img(mat2, im);

			monImage = bufferedImage;
			onlyOnceForconnection = 0;

			System.out.println("Finis");
			

		}
		
		


	}
}

