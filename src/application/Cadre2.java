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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import businesslogic.ImageWing;
import businesslogic.Landmark;
import facade.Facade;
import ij.IJ;
import ij.ImagePlus;

public class Cadre2 extends JDialog implements ActionListener, WindowListener {

	private static final long serialVersionUID = 1L;
	final static JMenuBar menuBar = new JMenuBar();
	public final JMenu fichierMenu = new JMenu();
	public final JMenu landMarkMenu = new JMenu();

	private final JMenuItem Undo = new JMenuItem();
	private final JMenuItem Redo = new JMenuItem();

	public static Affichage displayPanel;

	private final JMenuItem enregistrerMenu = new JMenuItem();
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
	private final JMenuItem binary = new JMenuItem();
	private final JMenuItem btnReverseOrigin = new JMenuItem();
	private final JMenuItem skeleton = new JMenuItem();
	private final JMenuItem resize = new JMenuItem();
	private final JMenuItem featureExtraction = new JMenuItem();

	private final JMenu landmarkProcessing = new JMenu();
	private final JMenuItem landmarkPrediction = new JMenuItem();
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

	public static ArrayList<Landmark> ListLandmarkCadre = new ArrayList<Landmark>();
	protected static ArrayList<Landmark> ListLandmarkTemp = new ArrayList<Landmark>();
	public static ArrayList<Landmark> SelectionLandmark = new ArrayList<Landmark>();

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
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		wing = im;
		file2 = fileImage;
		ListLandmarkCadre = im.getLandmarks();
		// SelectionLandmark.addAll(Affichage.SelectionLandmark);
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
			fichierMenu.add(enregistrerMenu);
			enregistrerMenu.addActionListener(this);
			enregistrerMenu.setText("Save");

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

			convertImage.add(binary);
			binary.addActionListener(this);
			binary.setText("Binary");

			imageProcessing.add(skeleton);
			skeleton.addActionListener(this);
			skeleton.setText("Skeleton");

			imageProcessing.add(featureExtraction);
			featureExtraction.addActionListener(this);
			featureExtraction.setText("Feature Extraction");

			menuBar.add(landmarkProcessing);
			landmarkProcessing.setText("Landmark");
			landmarkProcessing.add(landmarkPrediction);
			landmarkPrediction.addActionListener(this);
			landmarkPrediction.setText("Landmark Prediction");

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
			// NOTE : Unused Function
			// combo.addItem("CIRCLE");
			// combo.addItem("SQUARE");
			// combo.addItem("ERASE");
			// combo.addActionListener(new FormeListener());
			// combo.addActionListener(new FormeListener());
			// combo.setPreferredSize(new Dimension(180, 50));
			// combo.setMinimumSize(new Dimension(180, 50));
			// combo.setMaximumSize(new Dimension(180, 50));
			//
			// toolBar.add(combo);
			// combo.setFocusable(false);
			slide.setFocusable(true);
			toolBar.setFloatable(false);

			// EDIT: SCrollAblePanel
			this.setSize(new Dimension(1000, 650));
			Dimension dataPanelMinSize = new Dimension(200, 600);
			dataPanel.setSize(dataPanelMinSize);
			displayPanel.setSize(displayPanel.getPreferredSize());
			JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, displayPanel, dataPanel);

			splitPane.setOneTouchExpandable(true);
			// splitPane.setDividerLocation(1600);
			splitPane.setResizeWeight(0.8);
			getContentPane().add(splitPane);
			// dataPanel.setBounds(displayPanel.getX(), displayPanel.getY(),
			// displayPanel.getWidth(),
			// displayPanel.getHeight());
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

	public void initializeKeyBinding() {
		displayPanel.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "pressed");
		displayPanel.getInputMap().put(KeyStroke.getKeyStroke("released SPACE"), "released");
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

		if (cliqueMenu.getSource().equals(enregistrerMenu)) {
			ListLandmarkTemp.removeAll(ListLandmarkTemp);
			ListLandmarkTemp = new ArrayList<Landmark>();// new ArrayList<Landmark>(ListLandmarkCadre);
			for (Landmark a : ListLandmarkCadre) {
				ListLandmarkTemp.add(new Landmark(a.posX, a.posY, a.isLandmark));
			}
			Facade.saveProject();
			System.out.println("Saved");

			for (int i = 0; i < ListLandmarkCadre.size(); i++) {
				// ListLandmarkCadre.get(i).setPosY( (int) (HEIGHT - j) );
			}
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
						Affichage.ListLandmark.add(l);
						PanelData.model.insertRow(0,
								new Object[] { l.getPosX(), l.getPosY(), l.getIsLandmark(), "Undo" });
					}
				} else if (command.equals("MOVE")) {
					int pos = displayPanel.draggedLandmark;
					for (Landmark l : arrTemp) {
						for (int i = 0; i < PanelData.model.getRowCount(); i++) {
							if (Float.parseFloat(PanelData.model.getValueAt(i, 0).toString()) == Affichage.ListLandmark
									.get(pos).getPosX()
									&& Float.parseFloat(
											PanelData.model.getValueAt(i, 1).toString()) == Affichage.ListLandmark
													.get(pos).getPosY()) {
								PanelData.model.setValueAt(l.getPosX(), i, 0);
								PanelData.model.setValueAt(l.getPosY(), i, 1);
							}
						}
						Affichage.ListLandmark.get(pos).setPosX((int) l.getPosX());
						Affichage.ListLandmark.get(pos).setPosY((int) l.getPosY());
						int xCenter = (int) (l.getPosX() / (Affichage.currHeight / Affichage.defHeight));
						int yCenter = (int) (l.getPosY() / (Affichage.currWidth / Affichage.defWidth));
						Affichage.ListCircle.get(pos).setxCenter(xCenter);
						Affichage.ListCircle.get(pos).setxCenter(yCenter);

					}
				} else if (command.equals("EDIT")) {
					for (Landmark l : arrTemp) {
						for (int i = 0; i < Affichage.ListLandmark.size(); i++) {
							if (l.getPosX() == Affichage.ListLandmark.get(i).getPosX()
									&& l.getPosY() == Affichage.ListLandmark.get(i).getPosY()) {
								Affichage.ListLandmark.get(i).setIsLandmark(!l.isLandmark);
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

		} else if (cliqueMenu.getSource().equals(Redo)) {
			System.out.println("Redo Activities: " + Affichage.redoList.size());
			if (Affichage.redoList != null && Affichage.redoList.size() > 0) {
				ArrayList<Landmark> arrTemp = Affichage.redoList.get(Affichage.redoList.size() - 1);
				// Remove from undoList
				Affichage.redoList.remove(Affichage.redoList.size() - 1);
				// Add to RedoList
				Affichage.undoList.add(arrTemp);
				String command = Affichage.redoListCommand.get(Affichage.redoListCommand.size() - 1);
				Affichage.redoListCommand.remove(Affichage.redoListCommand.size() - 1);
				Affichage.undoListCommand.add(command);
				System.out.println(command);
				if (command.equals("DELETE")) {
					System.out.println("Here");
					for (int i = 1; i <= arrTemp.size(); i++) {

						Affichage.ListLandmark.remove(Affichage.ListLandmark.size() - 1);
						PanelData.model.removeRow(0);
					}
				} else if (command.equals("MOVE")) {
					int pos = displayPanel.draggedLandmark;
					int posX = (int) (Affichage.dragTo.getX() * (Affichage.defHeight / Affichage.currHeight));
					int posY = (int) (Affichage.dragTo.getY() * (Affichage.defWidth / Affichage.currWidth));
					for (int i = 0; i < PanelData.model.getRowCount(); i++) {
						if (Float.parseFloat(PanelData.model.getValueAt(i, 0).toString()) == Affichage.ListLandmark
								.get(pos).getPosX()
								&& Float.parseFloat(
										PanelData.model.getValueAt(i, 1).toString()) == Affichage.ListLandmark.get(pos)
												.getPosY()) {
							PanelData.model.setValueAt(posX, i, 0);
							PanelData.model.setValueAt(posY, i, 1);
						}
					}
					Affichage.ListCircle.get(pos).setxCenter(posX);
					Affichage.ListCircle.get(pos).setxCenter(posY);
					Affichage.ListLandmark.get(pos).setPosX(posX);
					Affichage.ListLandmark.get(pos).setPosY(posY);
				} else if (command.equals("EDIT")) {
					for (Landmark l : arrTemp) {
						for (int i = 0; i < Affichage.ListLandmark.size(); i++) {
							if (l.getPosX() == Affichage.ListLandmark.get(i).getPosX()
									&& l.getPosY() == Affichage.ListLandmark.get(i).getPosY()) {
								Affichage.ListLandmark.get(i).setIsLandmark(!Affichage.ListLandmark.get(i).isLandmark);
								System.out.println(i);
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
		} else if (cliqueMenu.getSource().equals(btnReverseOrigin)) {
			if (displayPanel.backUpImage == null) {
				displayPanel.backUpImage = displayPanel.monImage;
			}
			displayPanel.monImage = displayPanel.backUpImage;
		} else if (cliqueMenu.getSource().equals(btnGray)) {
			if (displayPanel.backUpImage == null) {
				displayPanel.backUpImage = displayPanel.monImage;
			}
			displayPanel.monImage = Facade.convert2Gray(Facade.img2Mat(displayPanel.backUpImage),
					displayPanel.monImage);

		} else if (cliqueMenu.getSource().equals(binary)) {
			// TEST binary
			if (displayPanel.backUpImage == null) {
				displayPanel.backUpImage = displayPanel.monImage;
			}
			displayPanel.monImage = Facade.convert2Bin(displayPanel.backUpImage);
		} else if (cliqueMenu.getSource().equals(skeleton)) {
			if (displayPanel.backUpImage == null) {
				displayPanel.backUpImage = displayPanel.monImage;
			}
			BufferedImage binImage = Facade.convert2Bin(displayPanel.imgOriginal);
			ImagePlus imgP = new ImagePlus();

			imgP.setImage(binImage);
			IJ.run(imgP, "Analyze Particles...", "size=50-Infinity circularity=0.00-1.00 show=Masks in_situ");
			binImage = imgP.getBufferedImage();
			Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, new Size(3, 3));
			boolean isFinished = false;

			Mat img = Facade.img2Mat(binImage);
			Mat skel = Mat.zeros(img.size(), CvType.CV_8U);
			while (isFinished) {
				Mat temp = img;
				Mat eroded = img;
				Imgproc.erode(temp, eroded, kernel);
				Imgproc.dilate(eroded, temp, kernel);
				Core.subtract(img, temp, temp);
				Core.bitwise_or(img, temp, img);
				Imgproc.erode(eroded, eroded, kernel);
				if (Core.countNonZero(eroded) == 0) {
					isFinished = !isFinished;
				}
			}
			int newH = (int) (binImage.getHeight() * (Affichage.currHeight / Affichage.defHeight));
			int newW = (int) (binImage.getWidth() * (Affichage.currWidth / Affichage.defWidth));
			binImage = Facade.matToBufferedImage(img, displayPanel.backUpImage);
			Image tmp = binImage.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
			displayPanel.monImage = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
			Graphics2D gimp = displayPanel.monImage.createGraphics();
			gimp.drawImage(tmp, 0, 0, null);
			gimp.dispose();
			repaint();
		} else if (cliqueMenu.getSource().equals(resize)) {
			System.out.println("Resize");
		} else if (cliqueMenu.getSource().equals(landmarkPrediction)) {
			// System.out.println("Landmark Prediction");
			String result = fileImage.getAbsolutePath().toString();
			try (BufferedReader br = new BufferedReader(new FileReader(result + ".txt"))) {
				for (String line; (line = br.readLine()) != null;) {
					String[] tab = line.split(" ");
					float t = Float.parseFloat(tab[1]);
					Landmark l = new Landmark(Float.parseFloat(tab[0]), t, true);
					ListLandmarkCadre.add(l);
					// dataPanel.landmarkTable.add(l);
				}
				dataPanel.tableData = ListLandmarkCadre;
				dataPanel.createTable(dataPanel.tableData);
				Affichage.ListLandmark = ListLandmarkCadre;
				displayPanel.revalidate();
				displayPanel.repaint();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("Landmark data was not found");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
		System.out.println("WINDOW OPENED");
		ListLandmarkTemp.removeAll(ListLandmarkTemp);
		ListLandmarkTemp = new ArrayList<Landmark>();// new ArrayList<Landmark>(ListLandmarkCadre);

		for (Landmark a : ListLandmarkCadre) {
			ListLandmarkTemp.add(new Landmark(a.posX, a.posY, a.isLandmark));
		}

		if (ListLandmarkTemp.size() > 0) {
			System.out.println("Landmark Loaded");
			dataPanel.createTable(ListLandmarkTemp);
		}
	}

	@Override
	public void windowClosing(WindowEvent e) {
		ListLandmarkCadre = Affichage.ListLandmark;
		for (int i = 0; i < ListLandmarkCadre.size(); i++) {
			if (ListLandmarkTemp.size() == 0 && ListLandmarkCadre.size() != 0) {
				int option = JOptionPane.showConfirmDialog(null, "Do you want to save before Leave?", "Attention",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (option == JOptionPane.YES_OPTION) {
					Facade.saveProject();
				}
				i = ListLandmarkCadre.size();
			}
		}
		menuBar.removeAll();
		displayPanel.areaSelected = false;
		this.dispose();

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
