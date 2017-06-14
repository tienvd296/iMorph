package application;


import businesslogic.*;
import facade.Facade;

/**
 * 
 */
public class ControlDashboard {

    /**
     * Default constructor
     */
    public ControlDashboard() {
    }

    /**
     * Add image to the current project
     * @param path
     */
    public void addImage(String path) {
        Facade.addImage(path);
    }

    /**
     * delete image to the current project
     * @param image
     */
    public void deleteImage(ImageWing image) {
        // TODO implement here
    }

    /**
     * Save the current project
     */
    public void saveProject() {
        Facade.saveProject();
        }

    /**
     * Save a copy of the current project
     */
    public void saveAsProject() {
        // TODO implement here
    }

    /**
     * Load an existing project, it replaces the current one.
     * @param path
     */
    public void loadProject(String path) {
        // TODO implement here
    }

}