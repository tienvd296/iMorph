package affichage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
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
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSlider;
import javax.swing.JToolBar;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import businesslogic.ImageWing;
import businesslogic.Landmark;
import drawing.IDrawable;
import facade.Facade;
import ij.ImagePlus;
import ij.io.Opener;



public class Affichage extends JPanel implements MouseListener, ActionListener, MouseMotionListener {

	private JPopupMenu jpm = new JPopupMenu();
	private JMenuItem trueLandmark = new JMenuItem("True Landmark");      
	private JMenuItem falseLandmark = new JMenuItem("False Landmark");
	private JMenuItem suppLandmark = new JMenuItem("Supprimer Landmark");


	private double X = 0;
	private double Y = 0;

	public int WIDTH = 0;
	public int HEIGHT = 0;
	private JToolBar toolBar = new JToolBar();

	public static List<IDrawable> drawables = new LinkedList();
	public static float getSliderValue() {
		return sliderValue;
	}

	public static List<Graphics> graphic = new LinkedList();


	public Landmark selectedLandmark;
	public int indexOfSelectedLandmark = 0;


	public static ArrayList<Landmark> ListLandmark = new ArrayList<Landmark>();
	public static ArrayList<Landmark> SelectionLandmark = new ArrayList<Landmark>();
	public static ArrayList<Landmark> UndoListLandmark = new ArrayList<Landmark>();

	public static int SelectionLandmarkSize = 0;


	public static float sliderValue = 100;
	public float temp = 0;

	public static ArrayList<drawCircle> ListCircle = new ArrayList<drawCircle>();

	BufferedImage monImage;
	MouseEvent e;
	private ImageWing im;

	public static int displayLandmark = 0;

	public String test;
	public String test2;
	public float WIDTH2;
	public float HEIGHT2;

	public boolean isCtrlDown = false;

	public Affichage(ImageWing im) {

		this.im = im;
		ListLandmark = im.getLandmarks();

		sliderValue = JSlidePanel.sliderValue;
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);

		this.setFocusable(true);
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


		trueLandmark.addActionListener((ActionListener)this);
		falseLandmark.addActionListener((ActionListener)this);
		suppLandmark.addActionListener((ActionListener)this);
		setLayout(null);

		//falseLandmark.addActionListener(startAnimation);

		this.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent event){

				if(event.isPopupTrigger()){       


					// jpm.add(new Landmark(event.getX(), event.getY(), true));
					jpm.add(trueLandmark);
					jpm.add(falseLandmark);
					jpm.add(suppLandmark);
					// jpm.add(new LandMark(e.getX(), e.getY(), true));  

					X = event.getX();
					Y = event.getY(); 

					jpm.show(Cadre2.panneau, event.getX(), event.getY());
				}
			}
		});



	}


	public static void UndoLandmark(){

		if(SelectionLandmark.size() != 0){

			ListLandmark.add(SelectionLandmark.get(SelectionLandmark.size() -1));
			UndoListLandmark.add(SelectionLandmark.get(SelectionLandmark.size() -1));

			SelectionLandmark.remove(SelectionLandmark.get(SelectionLandmark.size() -1));
			System.out.println("SelectionLandmarkSize = "+SelectionLandmarkSize);
		}
		else {

			if(ListLandmark.size() ==0 && SelectionLandmark.size() == 0){
				JOptionPane jop2 = new JOptionPane();
				jop2.showMessageDialog(null, "You have nothing to Undo", "Attention", JOptionPane.WARNING_MESSAGE);

			} else

				UndoListLandmark.add(ListLandmark.get(ListLandmark.size() -1));
			ListLandmark.remove(ListLandmark.get(ListLandmark.size() -1));
			System.out.println("SelectionLandmarkSize = "+SelectionLandmarkSize);
		}
		

	}

/*
	public static void RedoLandmark() {

		if(UndoListLandmark.size() == 0){
			JOptionPane jop2 = new JOptionPane();
			jop2.showMessageDialog(null, "You have nothing to Redo", "Attention", JOptionPane.WARNING_MESSAGE);
		}
		else{
			if(SelectionLandmarkSize != 0 && SelectionLandmarkSize > UndoListLandmark.size() ){
				
				SelectionLandmark.add(UndoListLandmark.get(SelectionLandmarkSize));
				UndoListLandmark.remove(UndoListLandmark.get(UndoListLandmark.size() -1));
				SelectionLandmarkSize = SelectionLandmarkSize-1;
				System.out.println("SelectionLandmarkSize = "+SelectionLandmarkSize);

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

*/

	public void addDrawable(IDrawable d) {
		drawables.add(d);
		repaint();
	}




	public static void addLandMark(double x, double y , boolean B){

		//String texte = PanelData.jText.getText();
		//PanelData.jText.setText(texte+ "\n X : "+x+ " Y : "+y+" "+B);



		//	String texte = PanelData.jText.getText();
			PanelData.jText.setText("" +ListLandmark.toString()+ "\n");
			//PanelData.jText.setText(texte+"\n Landmark n°"+i+" X = "+ListLandmark.get(i).getPosX()+ " Y = "+ListLandmark.get(i).getPosY()+ " Type = " +ListLandmark.get(i).getIsLandmark());
		

	}



	protected void paintComponent(Graphics g)
	{
		
		

		super.paintComponent(g);
		
		
		test = im.getProperties().get("WIDTH");
		test2 = im.getProperties().get("HEIGHT");
		// Les données sont des String donc on les convertis
		 WIDTH2 = Float.parseFloat(test);
		 HEIGHT2 = Float.parseFloat(test2);

		/*	
		for(int j =0; j<SelectionLandmark.size(); j++){

			if(SelectionLandmark.size() == 0){
				System.out.println("LISTE SELECTION VIDE");
			} else{

				System.out.println(" SelectionLandmark n°"+j+" X = "+SelectionLandmark.get(j).getPosX()+ " Y = "+SelectionLandmark.get(j).getPosY());

			}
		}
		



		for(int j =0; j<ListLandmark.size(); j++){

			if(ListLandmark.size() == 0){
				System.out.println("LISTE VIDE");
			} else
				System.out.println(" ListLandmark n°"+j+" X = "+ListLandmark.get(j).getPosX()+ " Y = "+ListLandmark.get(j).getPosY());

		}

		 */	
		
		float value = 0;
		float Test = 0;
		value = JSlidePanel.getSliderValue();
		
		if( value != temp){
			if(value < temp){
			
				System.out.println("Test : "+Test+ " value : "+value+ " REDUIRE IMAGE");
				reduireImage(value);
				temp = value;
				
			}else if(value > temp)
			{
				
				
				System.out.println("Test : "+Test+ " value : "+value+" AGRANDIR IMAGE");
				agrandirImage(value);
				temp= value;
				
			}
		
		
		}
		
		WIDTH = monImage.getWidth();
		HEIGHT = monImage.getHeight();
		
		addLandMark(0,0,true);
		g.drawImage(monImage, 0 ,0 , null);

		// On récupère la Taille de l'image Via ses métadata

	

		// On récupère la taille de l'image affichée à l'écran, redimensionnée ou non

	


		for(int i = 0; i<ListLandmark.size() ; i++){

			float XX = ListLandmark.get(i).getPosX();
			float YY = ListLandmark.get(i).getPosY();

			// Récupérer le poucentage de différence entre l'image redimensionné et l'image originale

			float width = WIDTH / WIDTH2;
			float height = HEIGHT / HEIGHT2;

			if ( width != 1 && height !=1) {

				System.out.println(" width " +width);
				System.out.println("XX : "+XX);
				XX = XX * width;
				YY = YY * height;


				boolean isLandmark = ListLandmark.get(i).getIsLandmark();


				ListCircle.add(new drawCircle(g,(int) XX,(int) YY, 7, isLandmark,0,displayLandmark));
				repaint();

			}
			else {

				XX = (XX / WIDTH2)*100; 
				YY = (YY / HEIGHT2)*100;

				float X2 = (XX * WIDTH)/100;
				float Y2 = (YY * HEIGHT)/100;

				boolean isLandmark = ListLandmark.get(i).getIsLandmark();
				ListCircle.add(new drawCircle(g,(int) X2,(int) Y2, 7, isLandmark,0,displayLandmark));
				repaint();
			}
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


				ListCircle.add(new drawCircle(g,(int) X,(int) Y, 7, isLandmark, 1 , displayLandmark));
				repaint();
			}
			else {

				X = (X / WIDTH2)*100; 
				Y = (Y / HEIGHT2)*100;

				float X2 = (X * WIDTH)/100;
				float Y2 = (Y * HEIGHT)/100;

				boolean isLandmark = SelectionLandmark.get(i).getIsLandmark();
				ListCircle.add(new drawCircle(g,(int) X2,(int) Y2, 7, isLandmark,1,displayLandmark));
				repaint();
			}

		}
		repaint();

	}




	public int getValue(JSlider slide) {
		int val = slide.getValue();


		return val;

	}

	public static Mat bufferedImageToMat(BufferedImage bi) {
		Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
		byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
		mat.put(0, 0, data);
		return mat;
	}
	public static BufferedImage mat2Img(Mat in)
	{
		BufferedImage out;
		byte[] data = new byte[320 * 240 * (int)in.elemSize()];
		int type;
		in.get(0, 0, data);

		if(in.channels() == 1)
			type = BufferedImage.TYPE_BYTE_GRAY;
		else
			type = BufferedImage.TYPE_3BYTE_BGR;

		out = new BufferedImage(320, 240, type);

		out.getRaster().setDataElements(0, 0, 320, 240, data);
		return out;
	} 
	
	

	protected void reduireImage(float sliderValue)
	{
		
		
	/*	System.out.println("R2DUIRE");
		System.out.println("WIDTH2 "+WIDTH2+ "  HEINGHT2  "+HEIGHT2);
		
		sliderValue = sliderValue/100;
		System.out.println("slidervalue "+sliderValue);
		*/
		if(sliderValue > 0){
		BufferedImage imageReduite = new BufferedImage((int) (monImage.getWidth()*0.9) ,(int) (monImage.getHeight()*0.9), monImage.getType());
		AffineTransform reduire = AffineTransform.getScaleInstance(0.9, 0.9);
		int interpolation = AffineTransformOp.TYPE_BICUBIC;
		AffineTransformOp retaillerImage = new AffineTransformOp(reduire, interpolation);
		retaillerImage.filter(monImage, imageReduite );


		monImage = imageReduite ;
		repaint();
		}
	
		
	}


	protected void agrandirImage(float sliderValue)
	{
		
		sliderValue = 0;
		
		
		BufferedImage imageZoomer = new BufferedImage((int) (monImage.getWidth()*1.1)  ,(int)(monImage.getHeight()*1.1), monImage.getType());
		AffineTransform agrandir = AffineTransform.getScaleInstance(1.1,1.1);
		int interpolation = AffineTransformOp.TYPE_BICUBIC;
		AffineTransformOp retaillerImage = new AffineTransformOp(agrandir, interpolation);
		retaillerImage.filter(monImage, imageZoomer );
		monImage = imageZoomer ;
		repaint();
	}




	protected void ajouterImage(File fichierImage)
	{  

		String result = fichierImage.getAbsolutePath().toString();
		ImagePlus imagePlus = new Opener().openTiff(result, "");
		BufferedImage bufferedImage = imagePlus.getBufferedImage();


		System.out.println("Chargement image dans la fonction Ajouter Image");
		System.out.println("File ajouterImage : "+fichierImage);
		monImage = bufferedImage;

		System.out.println("Image 2 : "+monImage);
	}



	static boolean resizeIfNeeded(Mat img, int desiredWidth, int desiredHeight) {
		Size size = img.size();
		Size desiredSize = new Size(desiredWidth, desiredHeight);
		if (size.width != desiredWidth || size.height != desiredHeight) {
			Imgproc.resize(img, img, desiredSize);
			return true;
		}
		return false;
	}

	protected BufferedImage getImagePanneau()
	{      // récupérer une image du panneau
		int width  = this.getWidth();
		int height = this.getHeight();
		BufferedImage image = new BufferedImage(width, height,  BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();

		this.paintAll(g);
		g.dispose();
		return image;
	}

	protected void enregistrerImage(File fichierImage)
	{
		String format ="JPG";
		BufferedImage image = getImagePanneau();
		try {
			ImageIO.write(image, format, fichierImage);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	protected void enregistrerImage2(BufferedImage image2, File fichierImage)
	{
		String format ="JPG";

		BufferedImage image = image2;
		try {
			ImageIO.write(image, format, fichierImage);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}





	@Override
	public void mouseClicked(MouseEvent e) {


		addLandMark(e.getX(), e.getY(), true);
		System.out.println("X  : "+e.getX()+ " Y = "+e.getY());

		new Landmark(e.getX(), e.getY(), true);
		//draw(null, e.getX(), e.getY(), 1,1);

	}

	public int TESTX;
	public int TESTY;

	@Override
	public void mouseReleased(MouseEvent e) {

		TESTX = e.getX();
		TESTY = e.getY();
		System.out.println(" mouseRelease X = "+e.getX()+ " Y = "+e.getY());
		selectedLandmark = null;


	}



	@Override
	public void mouseMoved(MouseEvent e) {



	}




	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {

		System.out.println(" mouseDragged X = "+e.getX()+ " Y = "+e.getY());
		if(selectedLandmark != null) {
			selectedLandmark.setPosX(e.getX());
			selectedLandmark.setPosY(e.getY());
		}
		repaint();

	}





	@Override
	public void mousePressed(MouseEvent e) {

		if(isCtrlDown == true) {
			for(int j = 0; j<ListLandmark.size() ; j++){

				float moinsX = e.getX()-9;
				float plusX = e.getX()+9;

				float moinsY = e.getY()-9;
				float plusY = e.getY()+9;

				float LAND = ListLandmark.get(j).getPosX();
				float LAND2 = ListLandmark.get(j).getPosY();


				if( moinsX < LAND && LAND < plusX  && moinsY < LAND2 && LAND2 < plusY ){

					SelectionLandmark.add(ListLandmark.get(j));
					SelectionLandmarkSize = SelectionLandmarkSize+1;
					System.out.println("SelectionLandmarkSize = "+SelectionLandmarkSize);
					ListLandmark.remove(j);
					repaint();
				}

			}

		}
		else {

			for(int j = 0; j<ListLandmark.size() ; j++){

				float moinsX = e.getX()-9;
				float plusX = e.getX()+9;

				float moinsY = e.getY()-9;
				float plusY = e.getY()+9;

				float LAND = ListLandmark.get(j).getPosX();
				float LAND2 = ListLandmark.get(j).getPosY();



				if( moinsX < LAND && LAND < plusX  && moinsY < LAND2 && LAND2 < plusY ){

					System.out.println(" mousePressed2 X = "+e.getX()+ " Y = "+e.getY());
					selectedLandmark = ListLandmark.get(j);
					indexOfSelectedLandmark = j;
					ListLandmark.get(j).setPosX(TESTX);
					ListLandmark.get(j).setPosY(TESTY);
					repaint();


				}

			}

		}
	}




	@Override
	public void actionPerformed(ActionEvent e) {
		Point a = getMousePosition();
		WIDTH = monImage.getWidth();
		HEIGHT = monImage.getHeight();
		JOptionPane  jop2;

		if (e.getSource().equals(trueLandmark))
		{
			System.out.println("1er : "+X);
			X = (X/WIDTH);
			Y = (Y/HEIGHT); 

			if(X> 1 || Y > 1) {
				jop2 = new JOptionPane();
				jop2.showMessageDialog(null, "You are not on the image", "Attention", JOptionPane.WARNING_MESSAGE);
			}

			else if( X <=1 || Y <= 1){

				X = (X * WIDTH); 
				Y = (Y * HEIGHT);
				addLandMark(X, Y, true);
				System.out.println("2eme : " +X );
				ListLandmark.add(new Landmark((float)X,(float) Y, true));

				Facade.addLandmark(im, ListLandmark);

				System.out.println("True = "+X+ "  "+Y);
			}
		}

		if (e.getSource().equals(falseLandmark))
		{

			X = (X/WIDTH);
			Y = (Y/HEIGHT); 

			if(X> 1 || Y > 1) {

				jop2 = new JOptionPane();
				jop2.showMessageDialog(null, "You are not in the image", "Attention", JOptionPane.WARNING_MESSAGE);

			}
			else if( X <=1 || Y <= 1){

				addLandMark(X, Y,false);

				X = (X * WIDTH); 
				Y = (Y * HEIGHT);
				ListLandmark.add(new Landmark((float) X, (float) Y, false));	

				Facade.addLandmark(im, ListLandmark);

				System.out.println("False = "+X+ "  "+Y);

			}
		} 

		if (e.getSource().equals(suppLandmark)){

			if(SelectionLandmark.size() != 0){

				for(int i=0; i<SelectionLandmark.size(); i++){
					SelectionLandmark.remove(i);
					SelectionLandmark.removeAll(SelectionLandmark);
					SelectionLandmarkSize =0;
					System.out.println("SelectionLandmarkSize = "+SelectionLandmarkSize);
					repaint();

				}

			}else{

				ListLandmark.remove(indexOfSelectedLandmark);
				SelectionLandmarkSize = SelectionLandmarkSize -1;
				System.out.println("SelectionLandmarkSize = "+SelectionLandmarkSize);
				repaint();
			}
		}

	}









}

