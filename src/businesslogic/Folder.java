package businesslogic;

import java.util.ArrayList;

public class Folder {
	
	public Folder(String name)
	{
		this.name = name;
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

	public void setFolders(ArrayList<Folder> folders) {
		this.folders = folders;
	}

	public ArrayList<ImageWing> getImages() {
		return images;
	}

	public void setImages(ArrayList<ImageWing> images) {
		this.images = images;
	}

}
