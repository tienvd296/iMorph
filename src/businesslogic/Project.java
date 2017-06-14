package businesslogic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

/**
 * 
 */
public class Project {

	/**
	 * @param name
	 */
	public Project(String name) {
		this.name = name;
	}

	/**
	 * @param name
	 * @param path
	 */
	public Project(String name, String path, ArrayList<ImageWing> images) {
		this.name = name;
		this.pathProject = path;
		this.images = images;
	}

	/**
	 * 
	 */
	public String name = null;

	/**
	 * 
	 */
	public ArrayList<ImageWing> images = new ArrayList<ImageWing>();

	/**
	 * 
	 */
	public String pathProject = "";



	/**
	 * add Image to the current project
	 * @param image
	 */
	public void addImage(ImageWing image) {
		this.images.add(image);
	}

	/**
	 * Delete image to the current project
	 * @param image
	 */
	public void deleteImage(ImageWing image) {
		this.images.remove(image);
	}


}