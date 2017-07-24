package facade;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.ControlDashboard;
import businesslogic.*;
import helper.MetadataExtractor;
import helper.XML;
import helper.landmarkFile;

/**
 * <b>This class is a facade</b>
 * 
 * <p>
 * It is a bridge between the UI (user interface) and the BL (business logic).
 * All methods are static and there is one field, the current project.
 * When the UI needs to interact with the BL, it has to use the facade.
 *
 */
public class Facade {

	/**
	 * It is the current project.
	 * 
	 * @see Project
	 */
	public static Project currentProject = null;

	public static ImageWing editedImage = null;

	public static ControlDashboard activeView = null;

	/**
	 * Create a new empty project. Just the project's path is available.
	 * 
	 * @param path 
	 * 			A String with the project path
	 * 
	 * @see Project
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
	 * Add a new Image to the current folder. The image is given in parameter.
	 * 
	 * @param path
	 * 			A String with the image path
	 * @param height
	 * 			A Double equals to the height
	 * @param width
	 * 			A Double equals to the width
	 * @param fold
	 * 			The current folder, 
	 * 			if the current folder is the root then fold = null
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


	/**
	 * Add an existing image to the current folder. The image is given in parameter.
	 * 
	 * @param imW
	 * 			An ImageWing
	 * @param fold1
	 * 			The current folder, 
	 * 			if the current folder is the root then fold = null
	 */
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
	 * Remove image to the current folder.
	 * 
	 * @param image
	 * 			An ImageWing
	 * @param fold
	 * 			The current folder, 
	 * 			if the current folder is the root then fold = null
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
	 * Save the current Project in the project's backup file
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
	 * @param file
	 * 			The project's backup file
	 * 
	 * @return true if the project's backup file is OK
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

	/**
	 * Return the list of the latest open projects
	 * 
	 * @return ArrayList of Project
	 * 
	 * @see Project
	 */
	public static ArrayList<Project> getHistProject() {
		return XML.readHist();
	}

	/**
	 * Return images include in the current project.
	 * 
	 * @return a ArrayList of ImageWing
	 * 
	 * @see ImageWing
	 */
	public static ArrayList<ImageWing> getImages() {
		return Facade.currentProject.getImages();
	}


	/**
	 * Return image landmarks of the image passed as a parameter
	 * 
	 * @param im
	 * 			the ImageWing
	 * 
	 * @return an ArrayList of Landmark
	 * 
	 * @see ImageWing
	 * @see Landmark
	 */
	public static ArrayList<Landmark> getAllLandmark(ImageWing im) {
		return im.getLandmarks();
	}

	/**
	 * Return if image has properties or not
	 * 
	 * @param image
	 * 			the ImageWing
	 * 
	 * @return boolean, true if the image as properties
	 * 
	 * @see ImageWing
	 */
	public static boolean hasProperties(ImageWing image) {
		return !image.getProperties().isEmpty();

	}

	/**
	 * Set the value of the property
	 * 
	 * @param image
	 * 			the ImageWing
	 * @param key
	 * 			the name of the property
	 * @param value
	 * 			the new value of the property
	 * 
	 * 
	 * @see ImageWing
	 */
	public static void setProperties(ImageWing image, String key, String value) {
		image.getProperties().replace(key, value);

	}

	/**
	 * Add a property to image as passed in parameter
	 * 
	 * @param image
	 * 			the ImageWing
	 * @param key
	 * 			the name of the new property
	 * @param value
	 * 			the value of the new property
	 * 
	 * 
	 * @see ImageWing
	 */
	public static void addProperties(ImageWing image, String key, String value) {
		image.getProperties().put(key, value);


	}

	/**
	 * Return if image has landmarks or not
	 * 
	 * @param image
	 * 			the ImageWing
	 * 
	 * @return boolean, true if the image as landmarks
	 * 
	 * @see ImageWing
	 */
	public static boolean hasLandmarks(ImageWing image) {
		return !image.getLandmarks().isEmpty();
	}

	/**
	 * Return the X value in percent of the landmark passed in parameter
	 * 
	 * @param landmark
	 * 			the landmark
	 * @param im
	 * 			the ImageWing
	 * 
	 * @return double with the X value in percent
	 * 
	 * @see ImageWing
	 * @see Landmark
	 */
	public static double getX_ratio(Landmark landmark, ImageWing im) {
		double a = landmark.getPosX();
		double b = Double.parseDouble(im.getProperties().get("WIDTH"));

		return a/b;
	}

	/**
	 * Return the Y value in percent of the landmark passed in parameter
	 * 
	 * @param landmark
	 * 			the landmark
	 * @param im
	 * 			the ImageWing
	 * 
	 * @return double with the Y value in percent
	 * 
	 * @see ImageWing
	 * @see Landmark
	 */
	public static double getY_ratio(Landmark landmark, ImageWing im) {
		double a = landmark.getPosY();
		double b = Double.parseDouble(im.getProperties().get("HEIGHT"));

		return a/b;
	}


	public static void clearHistoric() {
	}

	/**
	 * Return the metadata of the file passed in parameter
	 * 
	 * @param file
	 * 			the file
	 * 
	 * @return HashMap with metadata
	 * 
	 */
	public static HashMap<String, String> metadataExtractor(File file)
	{
		return MetadataExtractor.metadataExtractor(file);
	}

	/**
	 * Add a new folder to folder as passed in parameter
	 * 
	 * @param string
	 * 			name of the folder
	 * @param fold
	 * 			the the parent folder of the new folder
	 * 
	 * 
	 * @see Folder
	 */
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

	/**
	 * Remove the folder as passed in parameter to the current one
	 * 
	 * @param current
	 * 			the current folder
	 * @param folder
	 * 			the deleted folder
	 * 
	 * 
	 * @see Folder
	 */
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

	/**
	 * Add a new landmark to image as passed in parameter
	 * 
	 * @param file
	 * 			
	 * @param X
	 * 
	 * @param Y
	 * 
	 * @param b
	 * 
	 * @see ImageWing
	 * @see Landmark
	 */
	public static void addLandmark(File file, float X, float Y, Boolean b)
	{
		ImageWing imW = Facade.editedImage;
		X = (int) (X*Float.parseFloat(imW.getProperties().get("WIDTH")));
		Y = (int)(Y*Float.parseFloat(imW.getProperties().get("HEIGHT")));
		Landmark land = new Landmark(X, Y, b);
		Facade.editedImage.addLandmark(land);
		landmarkFile.saveImage(Facade.editedImage);
		Facade.activeView.writeConsole(imW.getProperties().get("FILENAME") + " has a new landmark, X=" + X + "  Y=" + Y + "  isLandmark" + b , "POPUP Pierre");

	}

	/**
	 * Remove a landmark to image as passed in parameter
	 * 
	 * @param im
	 * 			the ImageWing who is concerned by the deleted landmark
	 * @param land
	 * 			the deleted landmark
	 * 
	 * 
	 * @see ImageWing
	 * @see Landmark
	 */
	public static void deleteLandmark(ImageWing im, Landmark land)
	{
		im.deleteLandmark(land);
		landmarkFile.saveImage(im);
	}

	/**
	 * Set the landmark to image as passed in parameter
	 * 
	 * @param im
	 * 			the ImageWing who is concerned by the edited landmark
	 * 
	 * 
	 * @see ImageWing
	 * @see Landmark
	 */
	public static void editLandmark(ImageWing im)
	{
		landmarkFile.saveImage(im);
	}

	public static String binaryPP(ArrayList<String> listPath) {
		return "OK";

	}

	public static String landmarkPrediction(ArrayList<String> listPath, HashMap<String, ImageWing> listImW, ControlDashboard CD) {
		String separator = System.getProperty("file.separator");
		String originalPath = System.getProperty("user.dir");
		String pathAPI = originalPath + separator + "landmarkPrediction.exe";

		ProcessBuilder pb = new ProcessBuilder(pathAPI, listPath.get(0));
		Process process;
		try {
			process = pb.start();
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream())); 
			String line = null;

			
			while ((line = stdInput.readLine()) != null) {
				CD.writeConsole(line, "Image Preprocessing");
				String[] tab = line.split(" ");
				Boolean b;
				if(tab[2] == "true"){
					b = true;
				}
				else
				{
					b = false;
				}
				Landmark l = new Landmark(Float.parseFloat(tab[0]), Float.parseFloat(tab[1]), b);
				listImW.get(listPath.get(0)).addLandmark(l);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Completed...");


		return "OK";
	}



}