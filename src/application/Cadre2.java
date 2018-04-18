package application;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import businesslogic.ImageWing;
import businesslogic.Landmark;
import facade.Facade;
import helper.landmarkFile;
import javafx.application.Platform;

public class Cadre2 extends JDialog implements ActionListener, WindowListener {

	private static final long serialVersionUID = 1L;
	final static JMenuBar menuBar = new JMenuBar();
	public final JMenu fichierMenu = new JMenu();
	public final JMenu landMarkMenu = new JMenu();

	private final JMenuItem Undo = new JMenuItem();
	private final JMenuItem Redo = new JMenuItem();

	public static Affichage displayPanel;

	private final JMenuItem btnSaveLandmark = new JMenuItem();
	private final JRadioButtonMenuItem addLandMarkMenu = new JRadioButtonMenuItem();

	private final JMenu editMenu = new JMenu();
	private final JMenuItem editSubMenu = new JMenuItem();
	// private final JMenuItem cropMenu = new JMenuItem();
	// private final JMenuItem resizeMenu = new JMenu();
	private final JMenuItem zoomIn = new JMenuItem();
	private final JMenuItem zoomOut = new JMenuItem();

	private final JMenu imageProcessing = new JMenu();
	private final JMenuItem convertImage = new JMenu();
	private final JMenuItem btnGray = new JMenuItem();
	private final JMenuItem btnConvert2Bin = new JMenuItem();
	private final JMenuItem btnReverseOrigin = new JMenuItem();
	private final JMenuItem skeleton = new JMenuItem();
	// private final JMenuItem cornerDetection = new JMenuItem();
	private final JMenuItem resize = new JMenuItem();
	private final JMenuItem featureExtraction = new JMenuItem();
	//
	// private final JMenu landmarkProcessing = new JMenu();
	// private final JMenuItem landmarkPrediction = new JMenuItem();
	private JToolBar toolBar = new JToolBar();

	public static Container c;
	public static JSlider slide = new JSlider();

	ImageIcon squareT = new ImageIcon("assets/Carre_Blanc.jpg");
	ImageIcon square = new ImageIcon(squareT.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH));
	ImageIcon circleT = new ImageIcon("assets/Rond_Blanc.jpg");
	ImageIcon circle = new ImageIcon(circleT.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH));

	public JComboBox<String> combo = new JComboBox<String>();
	public static JComboBox<String> combo2 = new JComboBox<String>();
	private JButton squareButton = new JButton(square);
	private JButton circleButton = new JButton(circle);

	// private JFrame instance_fenetre;
	// private JFrame instance_fenetre2;
	private final JScrollPane scrollPane = new JScrollPane();
	private final JScrollBar scrollBar = new JScrollBar();

	static JPanel panShape = new JPanel();
	public static PanelData dataPanel;
	public static JSplitPane split;

	public static ArrayList<Landmark> listLandmarkCadre = new ArrayList<Landmark>();
	protected static ArrayList<Landmark> listLandmarkTemp = new ArrayList<Landmark>();
	public static ArrayList<Landmark> savedLandmark = new ArrayList<Landmark>();

	public static int windowHeight;
	public static int windowWidth;
	public static int shapeType;
	public static float imHEIGHT;
	public static float imWIDTH;
	public static int startWidth;
	public static int startHeight;
	public static File fileImage;

	BufferedImage monImage = null;
	public ImageWing im;
	public ImageWing wing;
	public File file2;
	// Edited Parameter:
	public static String cursorShapeType = null;
	public static int cursorSize = slide.getValue();
	public static boolean isToolbarEnabled = false;
	public static boolean isCtrlDown = false;
	public static boolean isShiftDown = false;

	//

	// TODO: Create the Frame
	// @param
	// current: fileImage File, im ImageWing,
	// added: createMenu boolean - create menu or not
	public Cadre2(File fileImage, ImageWing im, boolean createMenu) {
		super();
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		this.im = im;
		Cadre2.fileImage = fileImage;
		this.setTitle("Image Processing Window");
		this.addWindowListener(this);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		wing = im;
		file2 = fileImage;
		listLandmarkCadre = im.getLandmarks();
		Go(im);
		try {
			displayPanel.printImageOnScreen(fileImage);
			displayPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
			creerMenu(!createMenu);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private void creerMenu(boolean isMenuExist) {
		setJMenuBar(menuBar);
		if (!isMenuExist) {
			menuBar.add(fichierMenu);
			fichierMenu.setText("File");
			fichierMenu.add(btnSaveLandmark);
			btnSaveLandmark.addActionListener(this);
			btnSaveLandmark.setText("Save");

			menuBar.add(editMenu);
			editMenu.setText("Edit");
			editMenu.add(editSubMenu);
			editSubMenu.addActionListener(this);
			editSubMenu.setText("Image Editing");

			editMenu.add(Undo);
			Undo.addActionListener(this);
			Undo.setText("Undo");

			editMenu.add(Redo);
			Redo.addActionListener(this);
			Redo.setText("Redo");

			menuBar.add(imageProcessing);
			imageProcessing.setText("Image Processing");
			imageProcessing.add(convertImage);
			convertImage.setText("Convert Image to");
			convertImage.addActionListener(this);

			convertImage.add(btnReverseOrigin);
			btnReverseOrigin.addActionListener(this);
			btnReverseOrigin.setText("Origin");

			convertImage.add(btnGray);
			btnGray.addActionListener(this);
			btnGray.setText("Gray");

			convertImage.add(btnConvert2Bin);
			btnConvert2Bin.addActionListener(this);
			btnConvert2Bin.setText("Binary");

			imageProcessing.add(skeleton);
			skeleton.addActionListener(this);
			skeleton.setText("Skeleton");

			imageProcessing.add(featureExtraction);
			featureExtraction.addActionListener(this);
			featureExtraction.setText("Feature Extraction");

			getContentPane().add(toolBar, BorderLayout.PAGE_START);
			toolBar.add(squareButton);
			squareButton.setFocusable(false);
			toolBar.add(circleButton);

			slide.setMaximum(100);
			slide.setMinimum(0);
			slide.setValue(70);
			slide.setPaintTicks(true);
			slide.setPaintLabels(true);
			slide.setMinorTickSpacing(10);
			slide.setMajorTickSpacing(20);
			slide.setPreferredSize(new Dimension(180, 50));
			slide.setMinimumSize(new Dimension(180, 50));
			slide.setMaximumSize(new Dimension(180, 50));
			slide.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					// TODO Auto-generated method stub
					cursorSize = ((JSlider) e.getSource()).getValue();
					slide.setFocusable(false);
				}
			});

			toolBar.add(slide);
			slide.setFocusable(true);
			toolBar.setFloatable(false);

			// EDIT: SCrollAblePanel
			this.setSize(new Dimension(1000, 650));
			Dimension dataPanelMinSize = new Dimension(200, 600);
			dataPanel.setSize(dataPanelMinSize);
			displayPanel.setSize(displayPanel.getPreferredSize());
			JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, displayPanel, dataPanel);

			splitPane.setOneTouchExpandable(true);
			splitPane.setResizeWeight(0.8);
			getContentPane().add(splitPane);
			dataPanel.setVisible(true);
			toolBar.setVisible(false);
			squareButton.addActionListener(this);
			circleButton.addActionListener(this);
		}
	}

	public void toolBarEditing(Boolean b) {
		if (b == true) {
			toolBar.setVisible(true);
			slide.setVisible(true);
			combo.setVisible(true);
			// toolBarLandMark(true);
			addLandMarkMenu.setSelected(false);
		} else if (b == false) {
			toolBar.setVisible(false);
		}
	}

	// NOTE: Value added but not used
	class FormeListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (combo.getSelectedItem() == "CIRCLE") {
				combo2.addItem("CIRCLE");
				shapeType = 0;
				// mouseAction(e, 1);
			} else if (combo.getSelectedItem() == "SQUARE") {
				combo2.addItem("SQUARE");
				shapeType = 1;
			} else if (combo.getSelectedItem() == "ERASE") {
				combo2.addItem("ERASE");
				shapeType = 2;
			}
			slide.getValue();
		}
	}

	@Override
	public void actionPerformed(ActionEvent cliqueMenu) {

		if (cliqueMenu.getSource().equals(btnSaveLandmark)) {
			if (savedLandmark == null)
				savedLandmark = new ArrayList<Landmark>();
			savedLandmark.addAll(Affichage.listLandmark);
			// listLandmarkCadre.get(i).setPosY( (int) (HEIGHT - j) );
			Facade.addLandmark(im, savedLandmark);
			landmarkFile.saveImage(im);
			listLandmarkTemp = savedLandmark;
			JOptionPane.showMessageDialog(this, "Landmarks have been updated");
		} else if (cliqueMenu.getSource().equals(editSubMenu)) {
			// displayPanel.agrandirImage();
			// Lance la correction, toolbar
			if (!isToolbarEnabled) {
				isToolbarEnabled = true;
				toolBarEditing(true);
			} else {
				toolBarEditing(false);
				isToolbarEnabled = false;
			}
			// getContentPane().add(displayPanel);
			displayPanel.setVisible(true);
		} else if (cliqueMenu.getSource().equals(zoomOut)) {
			displayPanel.zoomOut();
		} else if (cliqueMenu.getSource().equals(zoomIn)) {
			displayPanel.zoomIn();
		} else if (cliqueMenu.getSource().equals(addLandMarkMenu)) {

		}
		// CHECKME: CHange Shape of Mouse
		if (cliqueMenu.getSource() == squareButton) {
			cursorShapeType = "Square";
		}
		if (cliqueMenu.getSource() == circleButton) {
			cursorShapeType = "Circle";
		} else if (cliqueMenu.getSource().equals(Undo)) {
			undo();
		} else if (cliqueMenu.getSource().equals(Redo)) {
			redo();
		} else if (cliqueMenu.getSource().equals(btnReverseOrigin)) {
			if (displayPanel.backUpImage != null) {
				displayPanel.monImage = displayPanel.backUpImage;
				monImage = null;
				displayPanel.revalidate();
				displayPanel.repaint();
			}
		} else if (cliqueMenu.getSource().equals(btnGray)) {
			if (displayPanel.backUpImage == null) {
				displayPanel.backUpImage = displayPanel.monImage;
			}
			displayPanel.monImage = Facade.convert2Gray(Facade.img2Mat(displayPanel.backUpImage),
					displayPanel.monImage);
			monImage = Facade.convert2Gray(Facade.img2Mat(displayPanel.imgOriginal), displayPanel.imgOriginal);
			displayPanel.revalidate();
			displayPanel.repaint();

		} else if (cliqueMenu.getSource().equals(btnConvert2Bin)) {
			// TEST binary
			if (displayPanel.backUpImage == null) {
				displayPanel.backUpImage = displayPanel.monImage;
			}
			int newW = displayPanel.monImage.getWidth();
			int newH = displayPanel.monImage.getHeight();
			BufferedImage tempImage = Facade.convert2Bin(displayPanel.imgOriginal);
			monImage = tempImage;
			Image tmp = tempImage.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
			displayPanel.monImage = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
			Graphics2D gimp = displayPanel.monImage.createGraphics();
			gimp.drawImage(tmp, 0, 0, null);
			gimp.dispose();
			displayPanel.revalidate();
			displayPanel.repaint();
		} else if (cliqueMenu.getSource().equals(skeleton)) {
			if (displayPanel.backUpImage == null) {
				displayPanel.backUpImage = displayPanel.monImage;
			}
			System.out.println("Skeleton Checkpoint 1: " + displayPanel.imgOriginal.getHeight());
			Mat skel = Facade.findSkeleton(im);
			BufferedImage skelImg = Facade.matToBufferedImage(skel, displayPanel.monImage);
			monImage = Facade.matToBufferedImage(skel, displayPanel.imgOriginal);
			int newH = (int) (skelImg.getHeight() * (Affichage.currHeight / Affichage.DEFAULT_HEIGHT));
			int newW = (int) (skelImg.getWidth() * (Affichage.currWidth / Affichage.DEFAULT_WIDTH));
			Image tmp = skelImg.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
			displayPanel.monImage = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
			Graphics2D gimp = displayPanel.monImage.createGraphics();
			gimp.drawImage(tmp, 0, 0, null);
			gimp.dispose();
			repaint();
		} else if (cliqueMenu.getSource().equals(resize)) {

		}

	}

	private void undo() {
		if (Affichage.undoList != null && Affichage.undoList.size() > 0) {
			ArrayList<Landmark> arrTemp = Affichage.undoList.get(Affichage.undoList.size() - 1);
			// Remove from undoList
			Affichage.undoList.remove(Affichage.undoList.size() - 1);
			// Add to RedoList
			Affichage.redoList.add(arrTemp);
			String command = Affichage.undoListCommand.get(Affichage.undoListCommand.size() - 1);
			Affichage.undoListCommand.remove(Affichage.undoListCommand.size() - 1);
			Affichage.redoListCommand.add(command);
			if (command.equals("DELETE")) {
				for (Landmark l : arrTemp) {
					Affichage.listLandmark.add(l);
					PanelData.model.insertRow(0, new Object[] { l.getPosX(), l.getPosY(), l.getIsLandmark() });
				}
			} else if (command.equals("MOVE")) {
				int pos = displayPanel.draggedLandmark;
				for (Landmark l : arrTemp) {
					for (int i = 0; i < PanelData.model.getRowCount(); i++) {
						if (Float.parseFloat(PanelData.model.getValueAt(i, 0).toString()) == Affichage.listLandmark
								.get(pos).getPosX()
								&& Float.parseFloat(
										PanelData.model.getValueAt(i, 1).toString()) == Affichage.listLandmark.get(pos)
												.getPosY()) {
							PanelData.model.setValueAt(l.getPosX(), i, 0);
							PanelData.model.setValueAt(l.getPosY(), i, 1);
						}
					}
					Affichage.listLandmark.get(pos).setPosX((int) l.getPosX());
					Affichage.listLandmark.get(pos).setPosY((int) l.getPosY());
					int xCenter = (int) (l.getPosX() / (Affichage.currHeight / Affichage.DEFAULT_HEIGHT));
					int yCenter = (int) (l.getPosY() / (Affichage.currWidth / Affichage.DEFAULT_WIDTH));
					Affichage.listCircle.get(pos).setxCenter(xCenter);
					Affichage.listCircle.get(pos).setxCenter(yCenter);

				}
			} else if (command.equals("EDIT")) {
				for (Landmark l : arrTemp) {
					for (int i = 0; i < Affichage.listLandmark.size(); i++) {
						if (l.getPosX() == Affichage.listLandmark.get(i).getPosX()
								&& l.getPosY() == Affichage.listLandmark.get(i).getPosY()) {
							Affichage.listLandmark.get(i).setIsLandmark(!l.isLandmark);
						}
					}
					for (int i = 0; i < PanelData.model.getRowCount(); i++) {
						if (Float.parseFloat(PanelData.model.getValueAt(i, 0).toString()) == l.getPosX()
								&& Float.parseFloat(PanelData.model.getValueAt(i, 1).toString()) == l.getPosY()) {
							PanelData.model.setValueAt(l.getIsLandmark(), i, 2);
						}
					}
				}
			}

		}

	}

	private void redo() {
		if (Affichage.redoList != null && Affichage.redoList.size() > 0) {
			ArrayList<Landmark> arrTemp = Affichage.redoList.get(Affichage.redoList.size() - 1);
			// Remove from undoList
			Affichage.redoList.remove(Affichage.redoList.size() - 1);
			// Add to RedoList
			Affichage.undoList.add(arrTemp);
			String command = Affichage.redoListCommand.get(Affichage.redoListCommand.size() - 1);
			Affichage.redoListCommand.remove(Affichage.redoListCommand.size() - 1);
			Affichage.undoListCommand.add(command);
			if (command.equals("DELETE")) {

				for (int i = 1; i <= arrTemp.size(); i++) {

					Affichage.listLandmark.remove(Affichage.listLandmark.size() - 1);
					PanelData.model.removeRow(0);
				}
			} else if (command.equals("MOVE")) {
				int pos = displayPanel.draggedLandmark;
				int posX = (int) (Affichage.dragTo.getX() * (Affichage.DEFAULT_HEIGHT / Affichage.currHeight));
				int posY = (int) (Affichage.dragTo.getY() * (Affichage.DEFAULT_WIDTH / Affichage.currWidth));
				for (int i = 0; i < PanelData.model.getRowCount(); i++) {
					if (Float.parseFloat(PanelData.model.getValueAt(i, 0).toString()) == Affichage.listLandmark.get(pos)
							.getPosX()
							&& Float.parseFloat(PanelData.model.getValueAt(i, 1).toString()) == Affichage.listLandmark
									.get(pos).getPosY()) {
						PanelData.model.setValueAt(posX, i, 0);
						PanelData.model.setValueAt(posY, i, 1);
					}
				}
				Affichage.listCircle.get(pos).setxCenter(posX);
				Affichage.listCircle.get(pos).setxCenter(posY);
				Affichage.listLandmark.get(pos).setPosX(posX);
				Affichage.listLandmark.get(pos).setPosY(posY);
			} else if (command.equals("EDIT")) {
				for (Landmark l : arrTemp) {
					for (int i = 0; i < Affichage.listLandmark.size(); i++) {
						if (l.getPosX() == Affichage.listLandmark.get(i).getPosX()
								&& l.getPosY() == Affichage.listLandmark.get(i).getPosY()) {
							Affichage.listLandmark.get(i).setIsLandmark(!Affichage.listLandmark.get(i).isLandmark);
						}
					}
					for (int i = 0; i < PanelData.model.getRowCount(); i++) {
						if (Float.parseFloat(PanelData.model.getValueAt(i, 0).toString()) == l.getPosX()
								&& Float.parseFloat(PanelData.model.getValueAt(i, 1).toString()) == l.getPosY()) {
							PanelData.model.setValueAt(l.getIsLandmark(), i, 2);
						}
					}
				}
			}
			displayPanel.revalidate();
			displayPanel.repaint();
			dataPanel.revalidate();
			dataPanel.repaint();
		}
	}

	public boolean checkLandmarkChanged(ArrayList<Landmark> list1, ArrayList<Landmark> list2) {
		// FIXME: DELETE AFTER TEST

		boolean isSomethingChanged = false;
		if (list1.size() != list2.size()) {
			isSomethingChanged = true;
		} else {
			int count = 0;
			for (Landmark i : list1) {

				for (Landmark j : list2) {
					if (i.getPosX() == j.getPosX() && i.getPosY() == j.getPosY() && i.isLandmark == j.isLandmark) {
						count++;
						break;
					}
				}
			}
			if (count != list1.size()) {
				isSomethingChanged = true;
			}
		}
		return isSomethingChanged;
	}

	private void Go(ImageWing im) {
		c = this.getContentPane();
		dataPanel = new PanelData();
		displayPanel = new Affichage(im);

		Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize();
		// r�gler la taille de JFrame � 2/3 la taille de l'�cran
		windowHeight = tailleMoniteur.height * 2 / 3;
		windowWidth = tailleMoniteur.width * 2 / 3;
		this.setSize(windowWidth, windowHeight);
		this.setLocationRelativeTo(null);
		displayPanel.setVisible(true);
		String imHeight = im.getProperties().get("HEIGHT");
		imHEIGHT = Float.parseFloat(imHeight);
		String imWidth = im.getProperties().get("WIDTH");
		imWIDTH = Float.parseFloat(imWidth);

		c.add(displayPanel);
		displayPanel.setPreferredSize(new Dimension((int) imWIDTH, (int) imHEIGHT));

		getContentPane().add(scrollPane, BorderLayout.SOUTH);
		getContentPane().add(scrollBar, BorderLayout.WEST);
		scrollPane.setViewportView(displayPanel);

		this.getContentPane().add(scrollPane.add(displayPanel));

		// getContentPane().add(scrollBar_1, BorderLayout.WEST);
		// scrollBar_1.add(displayPanel);
		this.setVisible(true);
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// if (im != null)
		// displayPanel = new Affichage(im);
		listLandmarkTemp.removeAll(listLandmarkTemp);
		listLandmarkTemp = new ArrayList<Landmark>();// new ArrayList<Landmark>(listLandmarkCadre);

		for (Landmark a : listLandmarkCadre) {
			listLandmarkTemp.add(new Landmark(a.posX, a.posY, a.isLandmark));
		}

		if (listLandmarkTemp != null) {
			dataPanel.createTable(listLandmarkTemp);
		}
	}

	@Override
	public void windowClosing(WindowEvent e) {
		boolean isImageChanged = false;
		if (monImage != null) {
			isImageChanged = true;
		}
		Boolean isLandmarkChanged = false;
		if ((isLandmarkChanged = checkLandmarkChanged(Affichage.listLandmark, listLandmarkTemp)) || isImageChanged) {

			CadreExitCustomDialog cecd = new CadreExitCustomDialog();
			// JOptionPane confirmPane = new JOptionPane();
			int result = JOptionPane.showConfirmDialog(this, cecd.getPanel(isImageChanged, isLandmarkChanged),
					"Warning ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			System.out.println(cecd.saveLandmarkCheck.isSelected());
			if (result == 0) {
				if (cecd.saveLandmarkCheck.isSelected()) {
					Facade.addLandmark(im, Affichage.listLandmark);
					landmarkFile.saveImage(im);
					menuBar.removeAll();
					this.getContentPane().removeAll();
					BufferedImage imgOut = new BufferedImage(displayPanel.imgOriginal.getHeight(),
							displayPanel.imgOriginal.getWidth(), displayPanel.monImage.getType());
					Graphics2D g = imgOut.createGraphics();
					g.drawImage(displayPanel.monImage, 0, 0, displayPanel.imgOriginal.getWidth(),
							displayPanel.imgOriginal.getWidth(), null);
					g.dispose();
				} else {
					for (int i = 0; i < Affichage.undoList.size(); i++) {
						undo();
					}
				}
				if (cecd.saveImageCheck.isSelected()) {
					File myImage = new File(im.getPath());
					try {
						ImageIO.write(monImage, "TIFF", myImage);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				if (cecd.backupImageCheck.isSelected()) {
					String filename = "backup_" + im.getPath().substring(im.getPath().lastIndexOf(File.separator) + 1);
					// File myNewTIFF_File = new File(filename);
					try {
						File myNewImage = new File(System.getProperty("user.dir") + File.separator + "backup",
								filename);
						System.out.println(myNewImage.getPath());
						myNewImage.getParentFile().mkdirs();
						ImageIO.write(displayPanel.imgOriginal, "TIFF", myNewImage);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				displayPanel.areaSelected = false;
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						// javaFX operations should go here
						Facade.activeView.refresh();
					}
				});
				this.dispose();
			}
		} else {
			this.dispose();
			for (int i = 0; i < Affichage.undoList.size(); i++) {
				undo();
			}
			menuBar.removeAll();
			displayPanel.areaSelected = false;
		}
	}

	@Override
	public void windowClosed(WindowEvent e) {
		Affichage.undoList.clear();
		Affichage.redoList.clear();
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
