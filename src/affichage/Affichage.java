package affichage;

import businesslogic.Landmark;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSlider;
import javax.swing.JToolBar;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import drawing.IDrawable;
import ij.ImagePlus;
import ij.io.Opener;





public class Affichage extends JPanel implements MouseListener, ActionListener {

	 private JPopupMenu jpm = new JPopupMenu();
	 private JMenuItem trueLandmark = new JMenuItem("True Landmark");      
	 private JMenuItem falseLandmark = new JMenuItem("False Landmark");
	  
	 
	 private TrueLandMark TlandMark=new TrueLandMark();
	  
	private JToolBar toolBar = new JToolBar();
	
	public static List<IDrawable> drawables = new LinkedList();
	BufferedImage monImage;
	MouseEvent e;
	
	
	
	public Affichage() {
		

		
		
		this.addMouseListener(this);
		  trueLandmark.addActionListener((ActionListener)this);
		

		//falseLandmark.addActionListener(startAnimation);
	
		  this.addMouseListener(new MouseAdapter(){
		      public void mouseReleased(MouseEvent event){
		   
		        if(event.isPopupTrigger()){       
		        	
		     // jpm.add(new Landmark(event.getX(), event.getY(), true));
		          jpm.add(trueLandmark);
		          jpm.add(falseLandmark);
		          // jpm.add(new LandMark(e.getX(), e.getY(), true));  
		          
			    
		          
		         
		          jpm.show(Cadre2.panneau, event.getX(), event.getY());
		        }
		      }
		    });

	}
	
	class TrueLandMark implements ActionListener, MouseListener{
		MouseEvent event;
		@Override
		
		
		public void actionPerformed(ActionEvent a) {
			System.out.println("Action Performed TrueLandMark");
			
		
		
			
		}
		


		@Override
		public void mouseClicked(MouseEvent e) {
			//addLandMark(e.getX(), e.getY(), true);
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			Point position = e.getPoint();
			addLandMark(position.getX(), position.getY(),true);
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	
	public void draw(Graphics g, int x, int y, MouseEvent e) {
		super.paint(g);
		Color c = g.getColor();
		
		IDrawable circl = createDrawable2(e);
		this.addDrawable(circl);
		g.setColor(Color.BLUE);
		
		
		g.fillOval(e.getX(),e.getY(),80,80);
		g.setColor(c);
		
		
	}
	
	
	public void addDrawable(IDrawable d) {
        drawables.add(d);
        repaint();
    }
	
	
	private static IDrawable createDrawable2(MouseEvent e) {
	//	Double i = (double) affichage.Cadre2.slide.getValue();
		Point p = e.getPoint();
		Dimension dim = new Dimension();
		dim.setSize(10, 10);
		return new drawing.CircleDrawable(Color.white, p, dim);

	}
	 public static void addLandMark(double x, double y , boolean B){
		 B = true;
		 String texte = PanelData.jText.getText();
			PanelData.jText.setText(texte+ "\n X : "+x+ " Y : "+y+" "+B);
			
		 }
		 
			
		
	

	protected void correctionImage() {
		
		
		
	

	
		/*pan.add(toolBar);
		pan.add(slide);
		
		this.add(pan, BorderLayout.NORTH);
		
	  
	   
		 pan.setVisible(true);
		
		initToolBar(square, circle);
		this.add(toolBar, BorderLayout.NORTH);
			
		 this.setVisible(true);
		 
		*/
	    }
	



	
	 private void initToolBar(JButton square, JButton circle){
		   
		//    this.square.addActionListener();     Ajout de l'apparition d'un carré
		 
		    this.toolBar.add(square);
		    this.toolBar.add(circle);
		   
		    this.toolBar.addSeparator();

		    //Ajout des Listeners
		//    this.circle.addActionListener();
	      
		  }

	

	public int getValue(JSlider slide) {
		int val = slide.getValue();
		
		
		return val;
		
	}


	
	

	protected void paintComponent(Graphics g)
	{
		System.out.println("IMAGE1 "+monImage);
		super.paintComponent(g);
		System.out.println("paintComponent de la classe Affichage 1");
		g.drawImage(monImage, 0, 0,1100, 650, null);
		
		System.out.println("IMAGE "+monImage);
		System.out.println("paintComponent de la classe Affichage 2");
	}


	protected void reduireImage()
	{
		BufferedImage imageReduite = new BufferedImage((int)(monImage.getWidth()*0.5),(int)( monImage.getHeight()*0.5), monImage.getType());
		AffineTransform reduire = AffineTransform.getScaleInstance(0.5, 0.5);
		int interpolation = AffineTransformOp.TYPE_BICUBIC;
		AffineTransformOp retaillerImage = new AffineTransformOp(reduire, interpolation);
		retaillerImage.filter(monImage, imageReduite );
		monImage = imageReduite ;
		repaint();
	}


	protected void agrandirImage()
	{
		BufferedImage imageZoomer = new BufferedImage((int)(monImage.getWidth()*1.5),(int)( monImage.getHeight()*1.5), monImage.getType());
		AffineTransform agrandir = AffineTransform.getScaleInstance(1.5, 1.5);
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







	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Point a = getMousePosition();
		if (e.getSource().equals(trueLandmark))
		{
		
			addLandMark(a.getX(), a.getY(), true);
				
			
			}
		
	}

	
}

