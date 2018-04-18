package application;

import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class CadreExitCustomDialog {

	public JCheckBox saveImageCheck = new JCheckBox("Do you want to save Image?");
	public JCheckBox backupImageCheck = new JCheckBox("back up image?");
	public JCheckBox saveLandmarkCheck = new JCheckBox("Do you want to save Landmark");

	public JPanel getPanel(boolean isImageChanged, boolean isLandmarkChanged) {
		JPanel panel = new JPanel();

		Font myFont = new Font("Aria", Font.ITALIC, 13);
		//
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		saveImageCheck.setHorizontalTextPosition(SwingConstants.LEFT);
		backupImageCheck.setHorizontalTextPosition(SwingConstants.LEFT);
		saveLandmarkCheck.setHorizontalTextPosition(SwingConstants.LEFT);
		saveImageCheck.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
		backupImageCheck.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
		saveLandmarkCheck.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
		saveImageCheck.setFont(myFont);
		backupImageCheck.setFont(myFont);
		saveLandmarkCheck.setFont(myFont);
		//
		saveImageCheck.setVisible(false);
		panel.add(saveImageCheck);
		if (isImageChanged) {
			saveImageCheck.setVisible(true);
		}
		panel.add(backupImageCheck);
		backupImageCheck.setVisible(false);
		saveImageCheck.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
				backupImageCheck.setVisible(saveImageCheck.isSelected());
			}
		});

		panel.add(saveLandmarkCheck);
		saveLandmarkCheck.setVisible(false);
		if (isLandmarkChanged) {
			saveLandmarkCheck.setVisible(true);
		}
		return panel;
	}

}
