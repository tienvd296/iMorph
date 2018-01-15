package application;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import facade.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControlBinary {

    @FXML
    private JFXComboBox<String> filter;

    @FXML
    private JFXTextField threshold;

    @FXML
    private JFXRadioButton fixedThreshold;

    @FXML
    private ToggleGroup g1;

    @FXML
    private JFXRadioButton adaptiveThreshold;

    @FXML
    void cancelButton(MouseEvent event) {
		Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		stage.close();
    }

    @FXML
    void runButton(MouseEvent event) {
		String a = "adaptive";
		if(fixedThreshold.isSelected())
		{
			a = "fixed";
		}

		Facade.binaryPP(threshold.getText(), filter.getValue(), a);
		Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		stage.close();
    }

    public void initialize() {
		filter.getItems().addAll("1*1", "2*2", "3*3", "4*4", "5*5", "6*6");
		filter.setValue("3*3");
		threshold.setText("150");
		}
}
