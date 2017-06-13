package application;

import java.io.File;
import facade.Facade;

/**
 * 
 */
public class ControlHome {

    /**
     * Default constructor
     */
    public ControlHome() {
    }

    /**
     * Create a new empty project.
     * @param projectName
     */
    public void newProject(String projectName) {
    	Facade.newProject(projectName);
    }

    /**
     * Load an existing project
     * @param project
     */
    public void loadProject(File project) {
        // TODO implement here
    }

}