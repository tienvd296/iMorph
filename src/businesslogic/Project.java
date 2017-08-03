package businesslogic;

import java.util.*;

/**
 * <b>This class represents the Project object.</b>
 * 
 * <p>
 * Exactly like a file explorer, a folder is composed of 
 * files and folders. Here files are images.
 * </p>
 * <p>
 * A Project is characteristic by:
 * <ul>
 * <li>A name</li>
 * <li>The path to the project's backup file</li>
 * <li>A date of its last save</li>
 * <li>A list of folders</li>
 * <li>A list of images</li>
 * </ul>
 *
 */
public class Project {
	
	
	/**
	 * The folder ID is his name.
	 * 
	 * @see Project#getName()
	 * @see Project#setName(String)
	 * @see Project#Project(String)
	 * @see Project#Project(String, String, String)
	 * @see Project#Project(String, String, String, ArrayList, ArrayList)
	 */
	public String name = null;
	
	/**
	 * The date of the last save of the project.
	 * 
	 * @see Project#getLastSave()
	 * @see Project#setLastSave(String)
	 * @see Project#Project(String, String, String)
	 * @see Project#Project(String, String, String, ArrayList, ArrayList)
	 */
	public String lastSave = null;
	
	/**
	 * The path to the project's backup file.
	 * 
	 * @see Project#getPathProject()
	 * @see Project#setPathProject(String)
	 * @see Project#Project(String, String, String)
	 * @see Project#Project(String, String, String, ArrayList, ArrayList)
	 */
	public String pathProject = "";

	/**
	 * A project is composed of many images.
	 * 
	 * @see ImageWing
	 * 
	 * @see Project#getImages()
	 * @see Project#setImages(ArrayList)
	 * @see Project#addImage(ImageWing)
	 * @see Project#deleteImage(ImageWing)
	 * @see Project#Project(String, String, String, ArrayList, ArrayList)
	 */
	public ArrayList<ImageWing> images = new ArrayList<ImageWing>();

	/**
	 * A project is composed of many folders.
	 * 
	 * @see Folder
	 * 
	 * @see Project#getFolders()
	 * @see Project#setFolders(ArrayList)
	 * @see Project#Project(String, String, String, ArrayList, ArrayList)
	 */
	public ArrayList<Folder> folders = new ArrayList<Folder>();

	/**
	 * Empty project constructor.
	 * <p>
	 * This constructor create an empty project.
	 * </p>
	 * 
	 * @param name
	 *            The folder name
	 */
	public Project(String name) {
		this.name = name;
	}

	/**
	 * Project constructor.
	 * <p>
	 * This constructor create a project with images and folders.
	 * </p>
	 * 
	 * @param name
	 *            The folder name
	 * @param lastSave
	 *            The date the project was last saved
	 * @param path
	 *            The path to the project's backup file
	 * @param images
	 *            The folder is composed of this list of image           
	 * @param folders
	 *            The folder is composed of this list of folder
	 *            
	 * @see ImageWing
	 * @see Folder
	 * 
	 */
	public Project(String name, String lastSave, String path, ArrayList<Folder> folders, ArrayList<ImageWing> images) {
		this.name = name;
		this.pathProject = path;
		this.images = images;
		this.folders = folders;
		this.lastSave = lastSave;
	}
	
	/**
	 * Project constructor bis.
	 * <p>
	 * This constructor create a project.
	 * </p>
	 * 
	 * @param name
	 * @param lastSave
	 * @param path
	 */
	public Project(String name, String path, String lastSave) {
		this.name = name;
		this.pathProject = path;
		this.lastSave = lastSave;
	}
	
	
	public Project clonage()
	{
		ArrayList<Folder> foldersClone = new ArrayList<Folder>();
		Iterator<Folder> it = this.folders.iterator();
		while(it.hasNext())
		{
			foldersClone.add(it.next().clonage());
		}
		
		ArrayList<ImageWing> imagesClone = new ArrayList<ImageWing>();
		Iterator<ImageWing> it2 = this.images.iterator();
		while(it2.hasNext())
		{
			imagesClone.add(it2.next().clonage());
		}
		
		return new Project(this.name, this.lastSave, this.pathProject, foldersClone, imagesClone);
	}
	
	
	/**
	 * Return the name of the project
	 * 
	 * @return a String with the name of the project
	 */
	public String getName() {
		return name;
	}

    /**
     * Set the name of the project
     * 
     * @param name
     *            String with the new project name.
     * 
     */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Return the date the project was last saved
	 * 
	 * @return a String with the date the project was last saved
	 */
	public String getLastSave() {
		return lastSave;
	}

    /**
     * Set the date the project was last saved
     * 
     * @param lastSave
     *            String with the date the project was last saved.
     * 
     */
	public void setLastSave(String lastSave) {
		this.lastSave = lastSave;
	}

	/**
	 * Return images include in the current project.
	 * 
	 * @return a ArrayList of ImageWing
	 * 
	 * @see ImageWing
	 */
	public ArrayList<ImageWing> getImages() {
		return images;
	}

    /**
     * Set the image list
     * 
     * @param images
     *            ArrayList of ImageWing.
     *             
     * @see ImageWing
     */
	public void setImages(ArrayList<ImageWing> images) {
		this.images = images;
	}

	/**
	 * Return The path to the project's backup file
	 * 
	 * @return a String with the path to the project's backup file
	 * 
	 */
	public String getPathProject() {
		return pathProject;
	}

    /**
     * Set the date the path of the project
     * 
     * @param pathProject
     *            String with the path of the project.
     * 
     */
	public void setPathProject(String pathProject) {
		this.pathProject = pathProject;
	}

	
	/**
	 * Return folders include in the current project.
	 * 
	 * @return a ArrayList of folders
	 */
	public ArrayList<Folder> getFolders() {
		return folders;
	}

    /**
     * Set the folder list
     * 
     * @param folders
     *            ArrayList of Folder.
     *            
     * @see Folder
     * 
     */
	public void setFolders(ArrayList<Folder> folders) {
		this.folders = folders;
	}





	/**
	 * Add a new ImageWing to the image list.
	 * 
	 * @param image
	 *            The new image
	 * 
	 * @see ImageWing
	 */
	public void addImage(ImageWing image) {
		this.images.add(image);
	}

	/**
	 * Remove the ImageWing to the image list.
	 * 
	 * @param image
	 *            The deleted image
	 * 
	 * @see ImageWing
	 */
	public void deleteImage(ImageWing image) {
		this.images.remove(image);
	}


}