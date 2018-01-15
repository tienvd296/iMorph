package application;

import com.jfoenix.controls.JFXTextField;

import facade.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControlDotAndNoise {
	
	@FXML
    private JFXTextField dotSize;


    @FXML
    void cancelButton(MouseEvent event) {
		Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		stage.close();
    }

    @FXML
    void runButton(MouseEvent event) {
		Facade.dotAndNoise(dotSize.getText());
		Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		stage.close();
    }
    
    
}
