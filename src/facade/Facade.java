package facade;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import businesslogic.*;
import helper.ProjectFile;

/**
 * 
 */
public class Facade {

    /**
     * Default constructor
     */
    public Facade() {
    }

    /**
     * It is the current project
     */
    public static Project currentProject = null;

    /**
     * Create a new empty project. Just the project's path is available.
     * @param name 
     * @return
     */
    public static void newProject(String path) {
    	String separator = System.getProperty("file.separator");
    	String[] tab = path.split("\\\\");
    	String name = tab[tab.length - 1];
        Project p = new Project(name);
        p.setPathProject(path);
        Facade.currentProject = p;
        Facade.saveProject();
    }

    /**
     * Add a new Image to the current project. The image is given in parameter.
     * @param path 
     * @return
     */
    public static void addImage(String path) {
        ImageWing image = new ImageWing(path);
        Facade.currentProject.addImage(image);
        
        
    }

    /**
     * Delete image to the current project. The image is given in parameter.
     * @param image
     */
    public static void deleteImage(ImageWing image) {
    	Facade.currentProject.deleteImage(image);
        }

    /**
     * save the current Project
     */
    public static void saveProject() {
    	ProjectFile.saveProject(Facade.currentProject);
    }

    /**
     * Save a copy of the current project
     */
    public static void saveAsProject() {
        // TODO implement here
    }

    /**
     * Load an existing project, it becomes the current project
     * @param path
     */
    public static void loadProject(File file) {
    	

    	Project p = ProjectFile.reader(file);
    	Facade.currentProject = p;


        }

	public static ArrayList<Project> getHistProject() {
		// TODO Auto-generated method stub
		return ProjectFile.histProject();
	}

}