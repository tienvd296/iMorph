package application;

import java.io.IOException;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;

import facade.Facade;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControlRandomForest {

	@FXML
	private JFXTextField ntree;

	@FXML
	private JFXCheckBox proximity;

	@FXML
	void cancelButton(MouseEvent event) {
		Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		stage.close();
	}

	@FXML
	void runButton(MouseEvent event) {
		String a = "false";
		if(proximity.isSelected())
		{
			a = "true";
		}

		Facade.randomForest(ntree.getText(), a);
		Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		stage.close();
	}
	
	public void initialize() {
		ntree.setText("10");
		}

}
