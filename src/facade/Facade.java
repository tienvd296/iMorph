package facade;

import java.util.*;

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
    public Project currentProject;

    /**
     * Open the project who is in parameters
     * @param project 
     * @return
     */
    public void openProject(File project) {
        // TODO implement here
        return null;
    }

    /**
     * Create a new empty project. Just the project's path is available.
     * @param name 
     * @return
     */
    public void newProject(String name) {
        // TODO implement here
        return null;
    }

    /**
     * Add a new Image to the current project. The image is given in parameter.
     * @param path 
     * @return
     */
    public void addImage(String path) {
        // TODO implement here
        return null;
    }

    /**
     * Delete image to the current project. The image is given in parameter.
     * @param image
     */
    public void deleteImage(Image image) {
        // TODO implement here
    }

    /**
     * save the current Project
     */
    public void saveProject() {
        // TODO implement here
    }

    /**
     * Save a copy of the current project
     */
    public void saveAsProject() {
        // TODO implement here
    }

    /**
     * Load an existing project, it becomes the current project
     * @param path
     */
    public void loadProject(String path) {
        // TODO implement here
    }

}