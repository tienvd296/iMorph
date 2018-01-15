package businesslogic;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * <b>This class represents the folder object.</b>
 * 
 * <p>
 * Exactly like a file explorer, a folder is composed of 
 * files and other folders. Here files are images.
 * </p>
 * <p>
 * A folder is characteristic by:
 * <ul>
 * <li>A name</li>
 * <li>A parent folder</li>
 * <li>A list of folder</li>
 * <li>A list of images</li>
 * </ul>
 *
 */

public class Folder {


	/**
	 * The folder ID is his name.
	 * 
	 * @see Folder#getName()
	 * @see Folder#setName(String)
	 * @see Folder#Folder(String, Folder)
	 * @see Folder#Folder(String, ArrayList, ArrayList, Folder)
	 */
	public String name = null;

	/**
	 * With the parent parameter, it is possible to return to the parent folder.
	 * 
	 * @see Folder
	 * 
	 * @see Folder#getParent()
	 * @see Folder#setParent(Folder)
	 * @see Folder#Folder(String, ArrayList, ArrayList, Folder)
	 */
	public Folder parent = null;

	/**
	 * A folder is composed of many folders.
	 * 
	 * @see Folder
	 * 
	 * @see Folder#addFolder(Folder)
	 * @see Folder#deleteFolder(Folder)
	 * @see Folder#setFolders(ArrayList)
	 * @see Folder#getFolders()
	 * @see Folder#Folder(String, ArrayList, ArrayList, Folder)
	 */
	public ArrayList<Folder> folders = new ArrayList<Folder>();


	/**
	 * A folder is composed of many images.
	 * 
	 * @see ImageWing
	 * 
	 * @see Folder#addImage(ImageWing)
	 * @see Folder#deleteImage(ImageWing)
	 * @see Folder#setImages(ArrayList)
	 * @see Folder#getImages()
	 * @see Folder#Folder(String, ArrayList, ArrayList, Folder)
	 */
	public ArrayList<ImageWing> images = new ArrayList<ImageWing>();



	/**
	 * Empty folder constructor.
	 * <p>
	 * This constructor create an empty folder.
	 * </p>
	 * 
	 * @param name
	 *            The folder name
	 * @param parent
	 *            The folder who include the new folder
	 * 
	 * @see Folder#name
	 * @see Folder#parent
	 */
	public Folder(String name, Folder parent)
	{
		this.name = name;
		this.parent = parent;
	}


	/**
	 * Folder constructor.
	 * <p>
	 * This constructor create a folder with images and other folders.
	 * </p>
	 * 
	 * @param folderName
	 *            The folder name
	 * @param folderList
	 *            The folder is composed of this list of folder
	 * @param imageList
	 *            The folder is composed of this list of image           
	 * @param parent
	 *            The folder who include the new folder
	 *            
	 * @see ImageWing
	 */
	public Folder(String folderName, ArrayList<Folder> folderList, ArrayList<ImageWing> imageList, Folder parent) {
		this.name = folderName;
		this.folders = folderList;
		this.images = imageList;
		this.parent = parent;
	}
	
	
	public Folder clonage()
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
		
		return new Folder(this.name, foldersClone, imagesClone, this.parent);
	}
	

    /**
     * Set the list of image
     * 
     * @param images
     *            The new image list.
     * 
     * @see ImageWing
     */
	public void setImages(ArrayList<ImageWing> images) {
		this.images = images;
	}


	/**
	 * Return the folder parent.
	 * 
	 * @return the Folder parent
	 */
	public Folder getParent() {
		return parent;
	}

    /**
     * Set the parent folder
     * 
     * @param parent
     *            The new folder parent.
     * 
     */
	public void setParent(Folder parent) {
		this.parent = parent;
	}

    /**
     * Set the list of folder
     * 
     * @param folders
     *            The new folder list.
     * 
     * @see Folder
     */
	public void setFolders(ArrayList<Folder> folders) {
		this.folders = folders;
	}


	/**
	 * Return the name of the folder
	 * 
	 * @return the name of the folder
	 */
	public String getName() {
		return name;
	}

    /**
     * Set the name of the folder
     * 
     * @param name
     *            The new folder name.
     * 
     */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * Return folders include in the current folder.
	 * 
	 * @return a ArrayList of folders
	 */
	public ArrayList<Folder> getFolders() {
		return folders;
	}

	/**
	 * Return images include in the current folder.
	 * 
	 * @return a ArrayList of images
	 */
	public ArrayList<ImageWing> getImages() {
		return images;
	}


	/**
	 * Add a new ImageWing to the image list.
	 * 
	 * @param im
	 *            The new image
	 * 
	 * @see ImageWing
	 * 
	 * @see Folder#images
	 */
	public void addImage(ImageWing im)
	{
		this.images.add(im);
	}

	/**
	 * Remove the ImageWing to the image list.
	 * 
	 * @param im
	 *            The deleted image
	 * 
	 * @see ImageWing
	 * 
	 * @see Folder#images
	 */
	public void deleteImage(ImageWing im)
	{
		this.images.remove(im);
	}

	/**
	 * Add a new folder to the folder list.
	 * 
	 * @param fold
	 *            The new folder
	 * 
	 * @see Folder
	 * 
	 * @see Folder#folders
	 */
	public void addFolder(Folder fold)
	{
		this.folders.add(fold);
	}

	/**
	 * Remove the folder to the folder list.
	 * 
	 * @param fold
	 *            The deleted folder
	 * 
	 * @see Folder
	 * 
	 * @see Folder#folders
	 */
	public void deleteFolder(Folder fold)
	{
		this.folders.remove(fold);
	}


}
