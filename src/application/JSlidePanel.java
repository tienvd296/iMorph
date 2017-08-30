package application;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JTextArea;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JTextPane;
import java.awt.Font;


public class JSlidePanel implements ActionListener {

	private JFrame frame;
	private JTextArea textField;
	public static float sliderValue = 0;
	private JTextPane textPane;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public JSlidePanel() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 306, 76);
		
		frame.getContentPane().setLayout(null);
		
		
		JSlider slider = new JSlider();
		slider.setPaintTicks(true);
		slider.setValue(100);
		slider.setMaximum(300);
		slider.setSnapToTicks(true);
		slider.setToolTipText("\r\n");
		slider.setBounds(10, 6, 200, 27);
		frame.getContentPane().add(slider);
		slider.setMinorTickSpacing(20);
		slider.setMajorTickSpacing(20);
		textField = new JTextArea();
		textField.setFont(new Font("Monospaced", Font.PLAIN, 13));
		textField.setToolTipText("\r\n\r\n\r\n");
		textField.setBounds(220, 6, 48, 20);
		String texte = textField.getText();
		
		
		slider.addChangeListener(new ChangeListener(){

			public void stateChanged(ChangeEvent event){
				 sliderValue = slider.getValue();
				 textField.setText(""+sliderValue+"");
			//	System.out.println("La valeur du Jslide "+sliderValue);
				
				Affichage.sliderValue =sliderValue;
				
			}
		});   
		
		
		
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textPane = new JTextPane();
		textPane.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textPane.setEnabled(false);
		textPane.setEditable(false);
		textPane.setText("%");
		textPane.setBounds(264, 6, 41, 20);
		frame.getContentPane().add(textPane);
		
		frame.setVisible(true);
	}

	public static float getSliderValue() {
		return sliderValue;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
