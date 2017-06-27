package facade;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import businesslogic.*;
import helper.MetadataExtractor;
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
    	//String separator = System.getProperty("file.separator");
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
    public static void addImage(String path, double height, double width) {
        ImageWing image = new ImageWing(path);
        image.getProperties().put("HEIGHT", Double.toString(height));
        image.getProperties().put("WIDTH", Double.toString(width));
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
    public static boolean loadProject(File file) {
    	

    	Project p = ProjectFile.reader(file);
    	if(p != null)
    	{
    		Facade.currentProject = p;
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    	


        }

	public static ArrayList<Project> getHistProject() {
		return ProjectFile.histProject();
	}

	public static ArrayList<ImageWing> getImages() {
		return Facade.currentProject.getImages();
	}

	public static ArrayList<Landmark> getAllLandmark(ImageWing im) {
		return im.getLandmarks();
	}

	public static boolean hasProperties(ImageWing image) {
		return !image.getProperties().isEmpty();

	}

	public static void setProperties(ImageWing image, String key, String value) {
		image.getProperties().replace(key, value);
		
	}

	public static void addProperties(ImageWing image, String key, String value) {
		image.getProperties().put(key, value);
		
		
	}

	public static boolean hasLandmarks(ImageWing image) {
		return !image.getLandmarks().isEmpty();
	}

	public static double getX_ratio(Landmark landmark, ImageWing im) {
		double a = landmark.getPosX();
		double b = Double.parseDouble(im.getProperties().get("WIDTH"));
		
		return a/b;
	}
	
	public static double getY_ratio(Landmark landmark, ImageWing im) {
		double a = landmark.getPosY();
		double b = Double.parseDouble(im.getProperties().get("HEIGHT"));
		
		return a/b;
	}

	public static void clearHistoric() {
		ProjectFile.clearHistoric();
		
	}
	
	public static HashMap<String, String> metadataExtractor(File file)
	{
		return MetadataExtractor.metadataExtractor(file);
	}

	public static void addFolder(String string) {
    	Folder folder = new Folder(string);
		Facade.currentProject.getFolders().add(folder);
	}

}