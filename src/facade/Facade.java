package facade;

import java.io.File;
import businesslogic.*;

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
     * Open the project who is in parameters
     * @param project 
     * @return
     */
    public static void openProject(File project) {
        // TODO implement here
    }

    /**
     * Create a new empty project. Just the project's path is available.
     * @param name 
     * @return
     */
    public static void newProject(String name) {
        Project p = new Project(name);
        Facade.currentProject = p;
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
    	Facade.currentProject.save();
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
    public static void loadProject(String path) {
        // TODO implement here
    }

}