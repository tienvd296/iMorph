package application;

import java.util.ArrayList;
import java.util.HashMap;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import businesslogic.ImageWing;
import facade.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControlCrossPointDetection {

	private ArrayList<String> listPath;
	private HashMap<String, ImageWing> pathToImageWing;
	
    @FXML
    private JFXTextField neighbor;
    
    @FXML
    private JFXTextField windowSize;

	
	
	@FXML
	void cancelButton(MouseEvent event) {
		Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		stage.close();
	}

	@FXML
	void runButton(MouseEvent event) {
		Facade.crossPointDetection(listPath, pathToImageWing, windowSize.getText(), neighbor.getText());
		Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		stage.close();
	}

	public void dataLD(ArrayList<String> listPath, HashMap<String, ImageWing> pathToImageWing) {
		this.listPath = listPath;
		this.pathToImageWing = pathToImageWing;	
	}
	

}


