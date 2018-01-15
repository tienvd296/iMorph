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

public class ControlLandmarkDetection {

	private ArrayList<String> listPath;
	private HashMap<String, ImageWing> pathToImageWing;
	
	@FXML
    private JFXComboBox<String> features;

    @FXML
    private JFXTextField neighbor;

	
	
	@FXML
	void cancelButton(MouseEvent event) {
		Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		stage.close();
	}

	@FXML
	void runButton(MouseEvent event) {
		Facade.landmarkDetection(listPath, pathToImageWing, features.getValue(), neighbor.getText());
		Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		stage.close();
	}

	public void dataLD(ArrayList<String> listPath, HashMap<String, ImageWing> pathToImageWing) {
		this.listPath = listPath;
		this.pathToImageWing = pathToImageWing;	
	}
	
	public void initialize() {
		features.getItems().addAll("SIFT", "SURF", "CMI", "HOG");
		features.setValue("SIFT");
		neighbor.setText("10");
		}
	

}
