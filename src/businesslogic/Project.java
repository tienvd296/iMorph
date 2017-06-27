package businesslogic;

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
	public Project(String name, String lastSave, String path, ArrayList<ImageWing> images) {
		this.name = name;
		this.pathProject = path;
		this.images = images;
		this.lastSave = lastSave;
	}
	
	/**
	 * @param name
	 * @param lastSave
	 * @param path
	 */
	public Project(String name, String path, String lastSave) {
		this.name = name;
		this.pathProject = path;
		this.lastSave = lastSave;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastSave() {
		return lastSave;
	}

	public void setLastSave(String lastSave) {
		this.lastSave = lastSave;
	}

	public ArrayList<ImageWing> getImages() {
		return images;
	}

	public void setImages(ArrayList<ImageWing> images) {
		this.images = images;
	}

	public String getPathProject() {
		return pathProject;
	}

	public void setPathProject(String pathProject) {
		this.pathProject = pathProject;
	}

	/**
	 * 
	 */
	public String name = null;
	
	/**
	 * 
	 */
	public String lastSave = null;

	/**
	 * 
	 */
	public ArrayList<ImageWing> images = new ArrayList<ImageWing>();


	public ArrayList<Folder> folders = new ArrayList<Folder>();

	public ArrayList<Folder> getFolders() {
		return folders;
	}

	public void setFolders(ArrayList<Folder> folders) {
		this.folders = folders;
	}

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