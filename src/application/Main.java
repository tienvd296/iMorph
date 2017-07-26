package application;


import java.io.IOException;

import helper.Keyboard;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {

		primaryStage.setTitle("PhleboMorph");
		Parent root = FXMLLoader.load(getClass().getResource("UIHome.fxml"));



		Scene scene = new Scene(root);
		scene.getStylesheets().add("JMetroDarkTheme.css");

		primaryStage.setResizable(false);

		primaryStage.setScene(scene);
		primaryStage.show();


	}


}
