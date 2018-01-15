package application;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import facade.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControlSVM {

	@FXML
	private JFXComboBox<String> kernel;

	@FXML
	private JFXTextField cost;

	@FXML
	private JFXTextField gamma;

	@FXML
	private JFXTextField cross;

	@FXML
	void cancelButton(MouseEvent event) {
		Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		stage.close();
	}

	@FXML
	void runButton(MouseEvent event) {

		Facade.SVM(kernel.getValue(), cost.getText(), gamma.getText(), cross.getText());
		Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		stage.close();
	}
	
	public void initialize() {
		kernel.getItems().addAll("radical");
		kernel.setValue("radical");
		cost.setText("0");
		gamma.setText("0");
		cross.setText("0");
	}

}
