package application;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSlider;

import businesslogic.ImageWing;
import businesslogic.Landmark;
import ij.ImagePlus;
import ij.io.Opener;

public class Affichage extends JPanel implements MouseListener, ActionListener, MouseMotionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// NOTE : FRAME Init
	private JPopupMenu jpm = new JPopupMenu();

	private JMenuItem addTrueLandmark = new JMenuItem("Add True Landmark");
	private JMenuItem addFalseLandmark = new JMenuItem("Add False Landmark");
	private JMenuItem setLandmarkTrue = new JMenuItem("Set True Landmark");
	private JMenuItem setLandmarkFalse = new JMenuItem("Set False Landmark");
	public JMenuItem delLandmark = new JMenuItem("Delete Landmark");

	// NOTE: ArrayList Init
	public static ArrayList<Landmark> listLandmark = new ArrayList<Landmark>();
	public static List<Integer> selLandmark = new ArrayList<>();
	public static List<Graphics> graphic = new LinkedList<Graphics>();
	// Utile pour eliminer les doublons
	public static ArrayList<drawCircle> listCircle = new ArrayList<drawCircle>();
	public static ArrayList<drawCircle> ListTempCircle = new ArrayList<drawCircle>();
	public static ArrayList<ArrayList<Landmark>> undoList = new ArrayList<>();
	public static List<String> undoListCommand = new ArrayList<>();
	public static ArrayList<ArrayList<Landmark>> redoList = new ArrayList<>();
	public static List<String> redoListCommand = new ArrayList<String>();

	// NOTE: Static Global Variable
	public static float resizeRatioHeight = 0;
	public static float resizeRatioWidth = 0;
	public static int currWidth = 0;
	public static int currHeight = 0;
	public static int displayLandmark = 0;
	public static int nbTempUndoList = 0;
	public static int cursorSize = 0;

	public static int listLandmarkSize = 0;

	public static final Cursor DEF_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);

	// NOTE: Non-static Global Variables
	public int onlyOnceForconnection = 0;
	public int posMouseOverLandmark;
	public Point dragFrom;
	public static Point dragTo;
	public Rectangle dragRect = new Rectangle();
	// public float width2;
	// public float height2;
	public float temp = 0;
	public static float DEFAULT_WIDTH = 0;
	public static float DEFAULT_HEIGHT = 0;
	private double X = 0;
	private double Y = 0;

	public boolean isShiftDown = false;
	public boolean isCtrlDown = false;
	public boolean isMousePressed = false;
	public boolean isMouseOverLandmark = false;
	public boolean areaSelected = false;
	public BufferedImage preferedArea;
	BufferedImage draggedRectangle;
	public BufferedImage myGroupingImage;
	// NOTE: Other init
	public int draggedLandmark;
	private ImageWing im;
	Graphics2D g2d;
	public Rectangle rect = new Rectangle();
	BufferedImage monImageDef;
	BufferedImage monImage;
	BufferedImage imgOriginal;
	public BufferedImage backUpImage;
	MouseEvent e;

	// Init
	public Affichage(ImageWing im) {

		this.setIm(im);
		DEFAULT_WIDTH = Float.parseFloat(im.getProperties().get("WIDTH"));
		DEFAULT_HEIGHT = Float.parseFloat(im.getProperties().get("HEIGHT"));
		setCursor(Cursor.getDefaultCursor());
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		// initializeKeyEvent();
		setFocusable(true);
		requestFocusInWindow();
		this.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				isShiftDown = false;
				isCtrlDown = false;
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getKeyCode() == KeyEvent.VK_SHIFT) {
					isShiftDown = true;
				} else if (arg0.getKeyCode() == KeyEvent.VK_CONTROL) {
					isCtrlDown = true;
				}
				if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
					if (preferedArea != null && areaSelected) {
						preferedArea = null;
						areaSelected = !areaSelected;
						Cadre2.cursorShapeType = null;
						setCursor(DEF_CURSOR);
					}
				}
			}
		});
		this.setPreferredSize(new Dimension((int) Float.parseFloat(im.getProperties().get("HEIGHT")),
				(int) Float.parseFloat(im.getProperties().get("WIDTH"))));
		listLandmark = im.getLandmarks();
		addTrueLandmark.addActionListener(this);
		addFalseLandmark.addActionListener(this);
		setLandmarkTrue.addActionListener(this);
		setLandmarkFalse.addActionListener(this);
		delLandmark.addActionListener(this);
		setLayout(null);
		listLandmarkSize = listLandmark.size();
	}

	int noOfRepaint = 0;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(monImage, 0, 0, null);

		// Reduce size of image until it fit with Display Window
		if (onlyOnceForconnection == 0) {
			currWidth = (int) DEFAULT_WIDTH;
			currHeight = (int) DEFAULT_HEIGHT;
			while (currWidth > Cadre2.windowWidth || currHeight > Cadre2.windowHeight) {
				zoomOut();
				currWidth = monImage.getWidth();
				currHeight = monImage.getHeight();
			}
			onlyOnceForconnection = 1;
			resizeRatioHeight = currHeight / DEFAULT_HEIGHT;
			resizeRatioWidth = currWidth / DEFAULT_WIDTH;
		}
		if (listLandmark.size() != 0) {
			for (int i = 0; i < listLandmark.size(); i++) {
				// get absolute position of landmark
				float posX = listLandmark.get(i).getPosX();
				float posY = listLandmark.get(i).getPosY();
				// Recuperer le poucentage de difference entre l'image redimensionne et l'image
				// originale
				boolean isSelected = false;
				if (selLandmark != null && selLandmark.size() > 0) {
					for (int j = 0; j < selLandmark.size(); j++) {
						if (selLandmark.get(j) == i) {
							isSelected = true;
							break;
						}
					}
				}
				// Get the ratio between image and window

				// Correct the coordinates
				posX = posX * resizeRatioWidth; // Utile pour conserver les position malgre un redimensionnement
				posY = posY * resizeRatioHeight;
				boolean isLandmark = listLandmark.get(i).getIsLandmark();
				// Add to ListTempCircle
				if (!isSelected)
					ListTempCircle.add(
							new drawCircle(g, (int) posX, (int) posY, circleSize(), isLandmark, 0, displayLandmark));
				else
					ListTempCircle.add(
							new drawCircle(g, (int) posX, (int) posY, circleSize(), isLandmark, 1, displayLandmark));
				// Remove the duplicate points
				HashSet<drawCircle> set = new HashSet<drawCircle>();
				set.addAll(ListTempCircle);
				set.clone();
				listCircle = new ArrayList<drawCircle>(set);
				repaint();
			}
		}
	}

	private int circleSize() {
		int W = monImage.getWidth();
		int H = monImage.getHeight();

		if (W < 300 || H < 300) {
			return 2;
		} else if (W < 750 && W > 500 || H < 500) {
			return 3;
		} else if (W < 1000 && W > 750 || H < 750 && H > 500) {
			return 4;
		} else if (W < 1250 && W > 1000 || H < 1000 && H > 750) {
			return 5;
		} else if (W < 1500 && W > 1250 || H < 1250 && H > 1000) {
			return 6;
		} else if (W < 2000 && W > 1500 || H < 1500 && H > 1250) {
			return 7;
		}
		return 5;
	}

	public int getValue(JSlider slide) {
		int val = slide.getValue();
		return val;
	}

	protected void zoomOut() {
		BufferedImage imageReduite = new BufferedImage((int) (monImage.getWidth() * 0.9),
				(int) (monImage.getHeight() * 0.9), monImage.getType());
		AffineTransform reduire = AffineTransform.getScaleInstance(0.9, 0.9);
		int interpolation = AffineTransformOp.TYPE_BICUBIC;
		AffineTransformOp retaillerImage = new AffineTransformOp(reduire, interpolation);
		retaillerImage.filter(monImage, imageReduite);
		monImage = imageReduite;
		repaint();
	}

	protected void zoomIn() {
		BufferedImage imageZoomer = new BufferedImage((int) (monImage.getWidth() * 1.1),
				(int) (monImage.getHeight() * 1.1), monImage.getType());
		AffineTransform agrandir = AffineTransform.getScaleInstance(1.1, 1.1);
		int interpolation = AffineTransformOp.TYPE_BICUBIC;
		AffineTransformOp retaillerImage = new AffineTransformOp(agrandir, interpolation);
		retaillerImage.filter(monImage, imageZoomer);
		monImage = imageZoomer;
		repaint();
	}

	public void imageNormal() {
		monImage = imgOriginal;
		onlyOnceForconnection = 0;
		repaint();
	}

	protected void printImageOnScreen(File fichierImage) {
		String result = fichierImage.getAbsolutePath().toString();
		ImagePlus imagePlus = new Opener().openImage(result, "");
		BufferedImage bufferedImage = imagePlus.getBufferedImage();
		monImage = bufferedImage;
		imgOriginal = bufferedImage;
		monImageDef = bufferedImage;
	}

	public void showPopupMenu(MouseEvent event) {
		if (event.isPopupTrigger()) {
			jpm.removeAll();
			isMouseOverLandmark = false;
			float startX = (event.getX() - circleSize() - 2) / resizeRatioHeight;
			float endX = (event.getX() + circleSize() + 2) / resizeRatioWidth;
			float startY = (event.getY() - circleSize() - 2) / resizeRatioHeight;
			float endY = (event.getY() + circleSize() + 2) / resizeRatioWidth;
			for (Landmark l : listLandmark) {
				if (l.getPosX() > startX && l.getPosX() < endX && l.getPosY() > startY && l.getPosY() < endY) {
					isMouseOverLandmark = true;
					posMouseOverLandmark = listLandmark.indexOf(l);
					X = l.getPosX();
					Y = l.getPosY();
					break;
				}
			}
			if (selLandmark.size() != 0 || isMouseOverLandmark) {
				jpm.add(setLandmarkTrue);
				jpm.add(setLandmarkFalse);
				jpm.add(delLandmark);
			} else {
				jpm.add(addTrueLandmark);
				jpm.add(addFalseLandmark);
			}
			jpm.show(Cadre2.displayPanel, event.getX(), event.getY());
		}
	}

	// FIXME: Unimplemented methods
	public static void ChangeTypeLandmark(boolean b) {

	}

	public void drawDashedLine(Graphics g, int x1, int y1, int x2, int y2) {
		// creates a copy of the Graphics instance
		Graphics2D g2d = (Graphics2D) g.create();
		// set the stroke of the copy, not the original
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 3 }, 0);
		g2d.setStroke(dashed);
		g2d.drawLine(x1, y1, x2, y2);
		// gets rid of the copy
		g2d.dispose();
	}

	// FIXME: Messy Logic, Need improvement, Performance issues
	public Cursor changeCursor(int x, int y) {
		int startX = x - (Cadre2.cursorSize / 2);
		int startY = y - (Cadre2.cursorSize / 2);
		Cursor currCursor = DEF_CURSOR;
		if (preferedArea != null) {
			currCursor = Toolkit.getDefaultToolkit().createCustomCursor(preferedArea,
					new Point(cursorSize / 2, cursorSize / 2), Cadre2.cursorShapeType);
		}
		if (Cadre2.cursorShapeType.toLowerCase().trim().equals("square")) {
			// Avoid out of bound
			if (startX >= 0 && startY >= 0 && startX + Cadre2.cursorSize < currWidth
					&& startY + Cadre2.cursorSize < currHeight) {
				// CHange cursor when mouse moved and user haven't selected their prefered area
				if (!areaSelected) {
					cursorSize = Cadre2.cursorSize;
					BufferedImage currShape = monImage.getSubimage(startX, startY, Cadre2.cursorSize,
							Cadre2.cursorSize);
					Toolkit toolkit = Toolkit.getDefaultToolkit();
					Point cursorHotpot = new Point(Cadre2.cursorSize / 2, Cadre2.cursorSize / 2);
					currCursor = toolkit.createCustomCursor(currShape, cursorHotpot, "Square Cursor");
					preferedArea = currShape;
					return currCursor;
				} else {
					if (cursorSize != Cadre2.cursorSize && preferedArea != null) {
						double ratio = (double) Cadre2.cursorSize / cursorSize;
						cursorSize = Cadre2.cursorSize;
						BufferedImage newCursor = new BufferedImage((int) (preferedArea.getWidth() * ratio),
								(int) (preferedArea.getHeight() * ratio), preferedArea.getType());
						AffineTransform agrandir = AffineTransform.getScaleInstance(ratio, ratio);
						int interpolation = AffineTransformOp.TYPE_BICUBIC;
						AffineTransformOp retaillerImage = new AffineTransformOp(agrandir, interpolation);
						retaillerImage.filter(preferedArea, newCursor);
						preferedArea = newCursor;
						Point cursorHotpot = new Point(Cadre2.cursorSize / 2, Cadre2.cursorSize / 2);
						currCursor = Toolkit.getDefaultToolkit().createCustomCursor(newCursor, cursorHotpot,
								"Square Cursor");
					}
				}
			} else {
				return currCursor;
			}
		}
		return currCursor;
	}

	private AlphaComposite makeComposite(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		return (AlphaComposite.getInstance(type, alpha));
	}

	private void drawRect(Graphics2D g2d, Rectangle rect, float alpha) {
		Composite originalComposite = g2d.getComposite();
		g2d.setComposite(makeComposite(alpha));
		g2d.setPaint(Color.blue);
		g2d.fill(rect);
		g2d.setComposite(originalComposite);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Les donnees sont des String donc on les convertis
		if (e.getSource().equals(addTrueLandmark) || e.getSource().equals(addFalseLandmark)) {
			// TODO: Add New landmark
			// PhleboRedundant 2.
			boolean setLandmarkType = false;
			if (e.getSource().equals(addTrueLandmark))
				setLandmarkType = true;
			if ((X / currWidth) > 1 || (Y / currHeight) > 1) {
				JOptionPane.showMessageDialog(null, "You are not on the image", "Attention",
						JOptionPane.WARNING_MESSAGE);
			} else if ((X / currWidth) <= 1 || (Y / currHeight) <= 1) {
				if (currWidth != DEFAULT_WIDTH && currHeight != DEFAULT_HEIGHT) {
					X = X / resizeRatioWidth;
					Y = Y / resizeRatioHeight;
					listLandmark.add(new Landmark((float) X, (float) Y, setLandmarkType));
					listCircle.add(new drawCircle(this.getGraphics(), (int) X, (int) Y, circleSize(), setLandmarkType,
							0, displayLandmark));
					// NOTE: Add landmark without save option
					// Facade.addLandmark(im, listLandmark);

					PanelData.model.insertRow(0, new Object[] { X, Y, setLandmarkType });
				} else {
					listLandmark.add(new Landmark((float) X, (float) Y, setLandmarkType));
					PanelData.model.insertRow(0, new Object[] { X, Y, setLandmarkType });
					listCircle.add(new drawCircle(this.getGraphics(), (int) X, (int) Y, circleSize(), setLandmarkType,
							0, displayLandmark));
					// Facade.addLandmark(im, listLandmark);
				}
			}
		} else if (e.getSource().equals(setLandmarkTrue) || e.getSource().equals(setLandmarkFalse)) {
			// TODO: Change type of Landmark
			boolean setTrueLandmark = false;
			if (e.getSource().equals(setLandmarkTrue))
				setTrueLandmark = true;
			if (isMouseOverLandmark) {
				listLandmark.get(posMouseOverLandmark).setIsLandmark(setTrueLandmark);
				selLandmark.add(posMouseOverLandmark);
			}
			if (!selLandmark.isEmpty()) {
				ArrayList<Landmark> temp = new ArrayList<Landmark>();
				for (int pos : selLandmark) {
					listLandmark.get(pos).setIsLandmark(setTrueLandmark);
					temp.add(listLandmark.get(pos));
					for (int i = 0; i < PanelData.model.getRowCount(); i++) {
						float curX = Float.parseFloat(PanelData.model.getValueAt(i, 0).toString());
						float curY = Float.parseFloat(PanelData.model.getValueAt(i, 1).toString());
						if (curX == listLandmark.get(pos).getPosX() && curY == listLandmark.get(pos).getPosY()) {
							PanelData.model.setValueAt(setTrueLandmark, i, 2);
							PanelData.model.moveRow(i, i, 0);
						}
					}
				}
				undoList.add(temp);
				undoListCommand.add("EDIT");
				selLandmark.clear();
			}
			repaint();
		} else if (e.getSource().equals(delLandmark)) {
			// TODO Remove landmark
			if (isMouseOverLandmark) {
				selLandmark.add(posMouseOverLandmark);
			}
			Set<Integer> hs = new TreeSet<>();
			hs.addAll(selLandmark);
			selLandmark.clear();
			selLandmark.addAll(hs);
			hs = null;
			ArrayList<Landmark> temp = new ArrayList<>();
			for (int i = selLandmark.size() - 1; i >= 0; i--) {
				int selPos = selLandmark.get(i);
				for (int j = 0; j < PanelData.model.getRowCount(); j++) {
					float tempX = Float.parseFloat(PanelData.model.getValueAt(j, 0).toString());
					float tempY = Float.parseFloat(PanelData.model.getValueAt(j, 1).toString());
					if (tempX == listLandmark.get(selPos).getPosX() && tempY == listLandmark.get(selPos).getPosY()) {
						PanelData.model.removeRow(j);
						break;
					}
				}
				temp.add(listLandmark.get(selPos));
				listLandmark.remove(selPos);
				listCircle.remove(selPos);
			}
			undoListCommand.add("DELETE");
			undoList.add(temp);
			selLandmark.clear();
		}
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		if (event.getButton() == MouseEvent.BUTTON3) {
			X = event.getX();
			Y = event.getY();
			showPopupMenu(event);
		} else if (isShiftDown && Cadre2.isToolbarEnabled) {
			if (!areaSelected) {
				areaSelected = true;
			} else {
				int startX = event.getX() - (Cadre2.cursorSize / 2);
				int startY = event.getY() - (Cadre2.cursorSize / 2);
				Graphics g = monImage.getGraphics();
				g.drawImage(preferedArea, startX, startY, null);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			X = e.getX();
			Y = e.getY();
			showPopupMenu(e);
		} else {
			monImage.getGraphics().drawImage(myGroupingImage, rect.x, rect.y, null);
			isMousePressed = false;
			dragTo = new Point(e.getX(), e.getY());
			if (dragFrom != null && dragTo != null) {
				// monImage = backUpImage;
				int startX = (int) (dragFrom.getX() / resizeRatioWidth);
				int startY = (int) (dragFrom.getY() / resizeRatioHeight);
				int endX = (int) (dragTo.getX() / resizeRatioWidth);
				int endY = (int) (dragTo.getY() / resizeRatioHeight);
				for (int i = 0; i < listLandmark.size(); i++) {
					if (listLandmark.get(i).getPosX() < endX && listLandmark.get(i).getPosY() < endY
							&& listLandmark.get(i).getPosX() > startX && listLandmark.get(i).getPosY() > startY) {
						boolean isExisted = false;
						for (int selected : selLandmark) {
							if (selected == i) {
								isExisted = true;
								break;
							}
						}
						if (!isExisted)
							selLandmark.add(i);
					}
				}
				dragFrom = null;
				dragTo = null;
				draggedRectangle = null;
				// g2d.drawImage(monImage, 0, 0, null);
				// monImage.getGraphics().clearRect(0, 0, getWidth(), getHeight());
				repaint();
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int posX = (int) (e.getX() / resizeRatioWidth);
		int posY = (int) (e.getY() / resizeRatioHeight);
		if (isMousePressed) {
			PanelData.model.setValueAt(posX, 0, 0);
			PanelData.model.setValueAt(posY, 0, 1);
			listCircle.get(draggedLandmark).setxCenter(posX);
			listCircle.get(draggedLandmark).setyCenter(posY);
			listLandmark.get(draggedLandmark).setPosX(posX);
			listLandmark.get(draggedLandmark).setPosY(posY);
			repaint();
		} else if (isShiftDown && !Cadre2.isToolbarEnabled) {
			dragTo = new Point(e.getX(), e.getY());
			if (backUpImage == null) {
				backUpImage = monImage;
			}
			// g2d.clearRect(rect.x, rect.y, rect.width, rect.height);
			// if (myGroupingImage != null) {
			// System.out.println(myGroupingImage.getWidth() + " " + rect.getWidth());
			// g2d.drawImage(myGroupingImage, rect.x, rect.y, null);
			// }
			// rect = new Rectangle(dragFrom.x, dragFrom.y, e.getX() - dragFrom.x, e.getY()
			// - dragFrom.y);
			// myGroupingImage = backUpImage.getSubimage(rect.x, rect.y, e.getX() -
			// dragFrom.x, e.getY() - dragFrom.y);
			//
			// g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
			// g2d.fillRect(rect.x, rect.y, rect.width, rect.height);
			// repaint();
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			X = e.getX();
			Y = e.getY();
			showPopupMenu(e);
		} else {
			if (isShiftDown) {
				g2d = (Graphics2D) monImage.getGraphics();
				dragFrom = new Point(e.getX(), e.getY());
			} else {
				float startX = (e.getX() - circleSize() - 2) / resizeRatioHeight;
				float endX = (e.getX() + circleSize() + 2) / resizeRatioWidth;
				float startY = (e.getY() - circleSize() - 2) / resizeRatioHeight;
				float endY = (e.getY() + circleSize() + 2) / resizeRatioWidth;
				for (int i = 0; i < listLandmark.size(); i++) {
					float pointX = listLandmark.get(i).getPosX();
					float pointY = listLandmark.get(i).getPosY();
					if (startX < pointX && pointX < endX && startY < pointY && pointY < endY) {
						if (isCtrlDown) {
							// NOTE: Check if the landmark has already been selected.
							// nbTempUndoList = listLandmark.size();
							selLandmark.add(i);

						} else {
							ArrayList<Landmark> temp = new ArrayList<Landmark>();
							temp.add(new Landmark(pointX, pointY, listLandmark.get(i).isLandmark));
							undoList.add(temp);
							undoListCommand.add("MOVE");
							draggedLandmark = i;
							PanelData.model.moveRow(i, i, 0);
							isMousePressed = true;
						}
						if (selLandmark != null && selLandmark.size() > 0) {
							TreeSet<Integer> ts = new TreeSet<>();
							ts.addAll(selLandmark);
							selLandmark.clear();
							selLandmark.addAll(ts);
						}
						repaint();
					}
				}
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		// Check if user want to edit image
		if (Cadre2.cursorShapeType != null && Cadre2.cursorShapeType != "") {
			setCursor(changeCursor(e.getX(), e.getY()));
			// ELSE: Capture area of image based on size and shape which user defined
		} else {
			setCursor(DEF_CURSOR);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public ImageWing getIm() {
		return im;
	}

	public void setIm(ImageWing im) {
		this.im = im;
	}
}
