package affichage;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JSlider;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class WBuilder {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WBuilder window = new WBuilder();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WBuilder() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 863, 515);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);
		
		JMenuItem mntmEnregistrer = new JMenuItem("Enregistrer");
		mnFile.add(mntmEnregistrer);
		
		JMenu mnLandmark = new JMenu("Landmark");
		menuBar.add(mnLandmark);
		
		JMenu mnImageEditing = new JMenu("Image Editing");
		menuBar.add(mnImageEditing);
		frame.getContentPane().setLayout(null);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(0, 0, 847, 27);
		toolBar.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(toolBar);
		
		JButton btnSquare = new JButton("Square");
		btnSquare.setBackground(Color.LIGHT_GRAY);
		toolBar.add(btnSquare);
		
		JButton btnCircle = new JButton("Circle");
		btnCircle.setBackground(Color.LIGHT_GRAY);
		toolBar.add(btnCircle);
		
		JSlider slider = new JSlider();
		slider.setPaintLabels(true);
		toolBar.add(slider);
		slider.setMajorTickSpacing(20);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(661, 27, 186, 429);
		frame.getContentPane().add(panel_1);
		SpringLayout sl_panel_1 = new SpringLayout();
		panel_1.setLayout(sl_panel_1);
		
		textField = new JTextField();
		sl_panel_1.putConstraint(SpringLayout.NORTH, textField, 220, SpringLayout.NORTH, panel_1);
		sl_panel_1.putConstraint(SpringLayout.WEST, textField, 47, SpringLayout.WEST, panel_1);
		sl_panel_1.putConstraint(SpringLayout.SOUTH, textField, 429, SpringLayout.NORTH, panel_1);
		sl_panel_1.putConstraint(SpringLayout.EAST, textField, 0, SpringLayout.EAST, panel_1);
		panel_1.add(textField);
		textField.setEnabled(false);
		textField.setEditable(false);
		textField.setColumns(10);
	}
}
