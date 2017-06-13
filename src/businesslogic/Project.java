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

	public void save() {
		String dataSave = "PROJECT_NAME=" + this.name + ";";
		if(!this.images.isEmpty())
		{
			Iterator<ImageWing> it = this.images.iterator();

			while(it.hasNext())
			{
				ImageWing image = it.next();
				dataSave = dataSave + "IMAGE_PATH=" + image.getPath() + ";";
				if(!image.getLandmarks().isEmpty())
				{
					Iterator<Landmark> it2 = image.getLandmarks().iterator();
					while(it.hasNext())
					{
						Landmark landmark = it2.next();
						dataSave = dataSave + landmark.getPosX() + "-" + landmark.getPosY() + "-" + landmark.getIsLandmark() + ";"; 
					}
				}
			}
		}

		if(this.pathProject == "")
		{
			this.pathProject = "D:\\Documents\\" + this.name + ".project";
		}
		BufferedWriter writer = null;
		try {
			//create a temporary file
			File logFile = new File(this.pathProject);

			writer = new BufferedWriter(new FileWriter(logFile));
			writer.write(dataSave);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// Close the writer regardless of what happens...
				writer.close();
			} catch (Exception e) {
			}
		}

	}

}