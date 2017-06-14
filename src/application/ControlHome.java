package application;

import java.io.File;
import java.util.Optional;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import facade.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;

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
    private TableView<String> lastProject;
    

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
			this.newProject(proj.getAbsolutePath().toString());
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
			Facade.loadProject(proj);
			System.out.println("OK" + Facade.currentProject.name);
		}
		else{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Please, try again !");
			alert.showAndWait();
		}
    }

}