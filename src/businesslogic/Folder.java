package businesslogic;

import java.util.ArrayList;

public class Folder {
	
	public Folder(String name)
	{
		this.name = name;
	}
	
	public Folder(String folderName, ArrayList<Folder> folderList, ArrayList<ImageWing> imageList) {
		this.name = folderName;
		this.folders = folderList;
		this.images = imageList;
	}

	private String name = null;
	
	private ArrayList<Folder> folders = new ArrayList<Folder>();
	
	private ArrayList<ImageWing> images = new ArrayList<ImageWing>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Folder> getFolders() {
		return folders;
	}


	public ArrayList<ImageWing> getImages() {
		return images;
	}
	
	public void addImage(ImageWing im)
	{
		this.images.add(im);
	}
	
	public void deleteImage(ImageWing im)
	{
		this.images.remove(im);
	}
	
	public void addFolder(Folder fold)
	{
		this.folders.add(fold);
	}
	
	public void deleteFolder(Folder fold)
	{
		this.folders.remove(fold);
	}


}
