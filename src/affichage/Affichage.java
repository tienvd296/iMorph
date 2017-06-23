package affichage;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToolBar;





public class Affichage extends JPanel implements MouseListener {

	
	private JToolBar toolBar = new JToolBar();
	
	
	BufferedImage monImage = null;
	public Affichage() {
		
		this.setBackground(Color.red);
		
		
		this.addMouseListener(this);
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
		super.paintComponent(g);
		if(monImage != null)
			g.drawImage(monImage, 0, 0, null);
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
	{   // dessiner une image à l'ecran	
		try {
			System.out.println("Chargement image");
			monImage = ImageIO.read(fichierImage);
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Erreur chargement image");
		}
		repaint(); 
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

		System.out.println("X : "+e.getX()+ " Y = "+getY());
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

	
}

