package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import businesslogic.Project;
import facade.Facade;
import helper.Keyboard;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * 
 */
public class ControlHome {

	/**
	 * Default constructor
	 */
	public ControlHome() {
	}


	@FXML
	private TableView<Project> lastProject;

	@FXML
	private TableColumn<Project, String> projectName;

	@FXML
	private TableColumn<Project, String> projectPath;

	@FXML
	private TableColumn<Project, Date> projectDate;


	@FXML
	void newProject(MouseEvent event) {
		JFileChooser file = new JFileChooser();
		file.setMultiSelectionEnabled(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"PhleboMorph Project", "project");
		file.setFileFilter(filter);
		int returnVal = file.showSaveDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			File proj = file.getSelectedFile();
			this.newProject(proj.getAbsolutePath().toString() + ".project");
		}

	}

	@FXML
	void openProject(MouseEvent event) {
		this.loadProject();
	}

	/**
	 * Create a new empty project.
	 * @param projectName
	 */
	public void newProject(String path) {
		Facade.newProject(path);
		this.moveToDashboard();
	}

	/**
	 * Load an existing project
	 * @param project
	 */
	public void loadProject() {
		JFileChooser file = new JFileChooser();
		file.setMultiSelectionEnabled(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"PhleboMorph Project", "project");
		file.setFileFilter(filter);
		int returnVal = file.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			File proj = file.getSelectedFile();
			if(Facade.loadProject(proj))
			{
				this.moveToDashboard();
			}
			else
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Unable to open project !");
				alert.showAndWait();
			}

		}
		else{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Please, try again !");
			alert.showAndWait();
		}
	}

	public void initialize() {

		ArrayList<Project> listProj = Facade.getHistProject();

		projectName.setCellValueFactory(new PropertyValueFactory<>("name"));
		projectPath.setCellValueFactory(new PropertyValueFactory<>("pathProject"));
		projectDate.setCellValueFactory(new PropertyValueFactory<>("lastSave"));
		projectDate.setSortType(TableColumn.SortType.DESCENDING);

		ObservableList<Project> list = FXCollections.observableArrayList(listProj);
		lastProject.setItems(list);

	}


	@FXML
	public void clickItem(MouseEvent event)
	{
		if(Facade.loadProject(new File (this.lastProject.getSelectionModel().getSelectedItem().getPathProject())))
		{
			this.moveToDashboard();
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Unable to open project !");
			alert.showAndWait();
		}

	}

	void moveToDashboard() {
		Parent root;	
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("UIDashboard.fxml"));
			root = loader.load();

			Scene scene = new Scene(root);

			scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
				if(key.getCode()==KeyCode.CONTROL && key.getCode()==KeyCode.A)
				{
					
				}
				else if(key.getCode()==KeyCode.CONTROL) {
					Keyboard.setCtrl();
				}
			});

			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();

			ControlDashboard myController = loader.getController();


			//myController.setDataOptions();

		} catch (IOException e) {
			e.printStackTrace();
		} 

	}

	@FXML
	void clearHistoric(ActionEvent event) {
		Facade.clearHistoric();

	}

}