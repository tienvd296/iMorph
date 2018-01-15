package application;

import com.jfoenix.controls.JFXTextField;

import facade.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControlSkeleton {


    @FXML
    private JFXTextField length;


    @FXML
    void cancelButton(MouseEvent event) {
		Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		stage.close();
    }

    @FXML
    void runButton(MouseEvent event) {
		Facade.skeletonPP(length.getText());
		Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		stage.close();
    }
    
    public void initialize() {
		length.setText("10");
		}
}
