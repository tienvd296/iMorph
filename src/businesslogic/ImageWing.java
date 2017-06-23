package businesslogic;

import java.util.*;

/**
 * 
 */
public class ImageWing {

    /**
     * Default constructor
     */
    public ImageWing(String path) {
    	this.path = path;
    	String[] filename = path.split("\\\\");
    	this.properties.put("FILENAME", filename[filename.length-1]);
    }

    public ImageWing(String pathImage, Map<String,String> properties, ArrayList<Landmark> landmarks) {
		this.path = pathImage;
		this.properties = properties;
		this.landmarks = landmarks;
	}

	/**
     * This string is the path of the image.
     */
    public String path = null;

    /**
     * List of properties, key is the properties and value the value of the properties
     */
    public Map<String, String> properties = new HashMap<String, String>();

    /**
     * 
     */
    public ArrayList<Landmark> landmarks =new ArrayList<Landmark>();

    public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public ArrayList<Landmark> getLandmarks() {
		return landmarks;
	}

	public void setLandmarks(ArrayList<Landmark> landmarks) {
		this.landmarks = landmarks;
	}

	/**
     * Add landmark to the image who is given in parameter.
     * @param landmark
     */
    public void addLandmark(Landmark landmark) {
        this.landmarks.add(landmark);
    }

    /**
     * 
     */
    public void deleteLandmark(Landmark landmark) {
    	this.landmarks.remove(landmark);

    }

}