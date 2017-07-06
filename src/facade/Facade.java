package facade;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import businesslogic.*;
import helper.MetadataExtractor;
import helper.XML;
import helper.landmarkFile;

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
	public static void addImage(String path, double height, double width, Folder fold) {
		ImageWing image = new ImageWing(path);
		image.getProperties().put("HEIGHT", Double.toString(height));
		image.getProperties().put("WIDTH", Double.toString(width));
		if(fold == null)
		{
			Facade.currentProject.addImage(image);
		}
		else
		{
			fold.addImage(image);
		}
	}
	
	public static void addImage(ImageWing imW, Folder fold1) {
		ImageWing image = imW;
		if(fold1 == null)
		{
			Facade.currentProject.addImage(image);
		}
		else
		{
			fold1.addImage(image);
		}
		
	}


	/**
	 * Delete image to the current project. The image is given in parameter.
	 * @param image
	 */
	public static void deleteImage(ImageWing image, Folder fold) {
		if(fold == null)
		{
			Facade.currentProject.deleteImage(image);
		}
		else
		{
			fold.deleteImage(image);
		}
	}

	/**
	 * save the current Project
	 */
	public static void saveProject() {
		XML.saveProject(Facade.currentProject);
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


		Project p = XML.readProject(file);
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
		return XML.readHist();
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
	}

	public static HashMap<String, String> metadataExtractor(File file)
	{
		return MetadataExtractor.metadataExtractor(file);
	}

	public static void addFolder(String string, Folder fold) {
		
		if(fold == null)
		{
			Folder folder = new Folder(string, null);
			Facade.currentProject.getFolders().add(folder);
		}
		else
		{
			Folder folder = new Folder(string, fold);
			fold.addFolder(folder);
		}
	}
	
	public static void deleteFolder(Folder current, Folder folder) {
		if(current == null)
		{
			Facade.currentProject.getFolders().remove(folder);
		}
		else
		{
			current.deleteFolder(folder);
		}
	}

	public static void addLandmark(ImageWing im, Landmark land)
	{
		im.addLandmark(land);
		landmarkFile.saveImage(im);
	}

	public static void deleteLandmark(ImageWing im, Landmark land)
	{
		im.deleteLandmark(land);
		landmarkFile.saveImage(im);
	}

	public static void editLandmark(ImageWing im)
	{
		landmarkFile.saveImage(im);
	}



}