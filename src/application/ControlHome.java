package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import businesslogic.Project;
import facade.Facade;
import helper.Keyboard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
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

	final ContextMenu contextMenuProject = new ContextMenu();

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

		FileChooser chooser = new FileChooser();
		chooser.setTitle("Open File");
		File file = chooser.showSaveDialog(new Stage());
		if (file == null)
			System.out.println("You cancelled the choice");
		else {
			this.newProject(file.getAbsolutePath() + ".project", event);
		}
	}

	@FXML
	void openProject(MouseEvent event) {
		if (this.loadProject()) {
			moveToDashboard();
			Stage stage2 = (Stage) ((Button) event.getSource()).getScene().getWindow();
			stage2.close();
		}

	}

	/**
	 * Create a new empty project.
	 * 
	 * @param event
	 * @param projectName
	 */
	public void newProject(String path, MouseEvent event) {
		Facade.newProject(path);
		Stage stage2 = (Stage) ((Button) event.getSource()).getScene().getWindow();
		stage2.close();
		this.moveToDashboard();
	}

	/**
	 * Load an existing project
	 * 
	 * @param project
	 */
	public boolean loadProject() {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Open project");
		chooser.getExtensionFilters().addAll(new ExtensionFilter("Project Files", "*.project"));
		File file = chooser.showOpenDialog(new Stage());
		if (file == null) {
			System.out.println("You cancelled the choice");
			return false;
		} else {
			Facade.loadProject(file);
			return true;
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
		contextMenuProject.getItems().clear();
		final MenuItem rightClickItemRename = new MenuItem("Rename");
		rightClickItemRename.setOnAction(e -> renameProject());
		final MenuItem rightClickItemDelete = new MenuItem("Delete");
		rightClickItemDelete.setOnAction(e -> deleteProject());
		contextMenuProject.getItems().addAll(rightClickItemRename, rightClickItemDelete);

	}

	public void deleteProject() {

	}

	public void renameProject() {
		TextInputDialog dialog = new TextInputDialog("UNDEFINED FOLDER");
		dialog.setTitle("Image browser");
		dialog.setHeaderText("Create a new folder.");
		dialog.setContentText("Please enter folder name:");

		// Traditional way to get the response value.
	}

	@FXML
	public void clickItem(MouseEvent event) {
		// Avoid click on empty cell
		if (this.lastProject.getSelectionModel().getSelectedItem() != null) {
			if (event.getButton() == MouseButton.PRIMARY) {
				if (Facade.loadProject(
						new File(this.lastProject.getSelectionModel().getSelectedItem().getPathProject()))) {
					this.moveToDashboard();
					Stage stage2 = (Stage) ((TableView) event.getSource()).getScene().getWindow();
					stage2.close();
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText(null);
					alert.setContentText("Unable to open project !");
					alert.showAndWait();
				}
			} else if (event.getButton() == MouseButton.SECONDARY) {
				this.lastProject.setOnContextMenuRequested(
						e -> contextMenuProject.show(this.lastProject, event.getScreenX(), event.getScreenY()));
			}
		}
	}

	void moveToDashboard() {
		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("UIDashboard.fxml"));
			root = loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add("caspian.css");
			Keyboard k = new Keyboard(scene);
			Stage stage = new Stage();
			stage.setMaximized(true);
			stage.setScene(scene);
			stage.show();

			ControlDashboard myController = loader.getController();
			myController.refresh();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	void clearHistoric(ActionEvent event) {
		Facade.clearHistoric();

	}

}