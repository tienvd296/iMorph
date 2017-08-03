package businesslogic;

import java.util.*;


/**
 * <b>This class represents the ImageWing object.</b>
 * 
 * <p>
 * A ImageWing is an object who represents the image of a wing.
 * It is not a image but an object who keep informations about the image.
 * </p>
 * <p>
 * A ImageWing is characteristic by:
 * <ul>
 * <li>A path</li>
 * <li>A list of properties</li>
 * <li>A list of landmarks</li>
 * </ul>
 *
 */
public class ImageWing {

	/**
	 * The image ID is his path.
	 * 
	 * @see ImageWing#getPath()
	 * @see ImageWing#setPath(String)
	 * @see ImageWing#ImageWing(String)
	 * @see ImageWing#ImageWing(String, Map, ArrayList)
	 */
	public String path = null;

	/**
	 * A image has many properties.
	 * 
	 * @see HashMap
	 * 
	 * @see ImageWing#getProperties()
	 * @see ImageWing#setProperties(HashMap)
	 * @see ImageWing#ImageWing(String, HashMap, ArrayList)
	 */
	public HashMap<String, String> properties = new HashMap<String, String>();

	/**
	 * A image has many landmarks.
	 * 
	 * @see Landmark
	 * 
	 * @see ImageWing#addLandmark(Landmark)
	 * @see ImageWing#deleteLandmark(Landmark)
	 * @see ImageWing#getLandmarks()
	 * @see ImageWing#setLandmarks(ArrayList)
	 * @see ImageWing#ImageWing(String, Map, ArrayList)
	 */
	public ArrayList<Landmark> landmarks =new ArrayList<Landmark>();

	/**
	 * New image constructor.
	 * <p>
	 * This constructor create an new image without landmark and property.
	 * </p>
	 * 
	 * @param path
	 *            The image path
	 * 
	 */
	public ImageWing(String path) {
		this.path = path;
		String[] filename = path.split("\\\\");
		this.properties.put("FILENAME", filename[filename.length-1]);
	}


	/**
	 * "Load image" constructor.
	 * <p>
	 * This constructor create an image with landmarks and properties.
	 * </p>
	 * 
	 * @param pathImage
	 *            The image path
	 * @param properties
	 *            A list of property
	 * @param landmarks
	 *            A list of landmark
	 * 
	 * @see Landmark
	 */
	public ImageWing(String pathImage, HashMap<String,String> properties, ArrayList<Landmark> landmarks) {
		this.path = pathImage;
		this.properties = properties;
		this.landmarks = landmarks;
	}
	
	
	public ImageWing clonage()
	{
		ArrayList<Landmark> landmarksClone = new ArrayList<Landmark>();
		Iterator<Landmark> it = this.landmarks.iterator();
		while(it.hasNext())
		{
			landmarksClone.add(it.next().clonage());
		}
		HashMap<String,String> propertiesClone = new HashMap<String, String>();
		propertiesClone = (HashMap<String, String>) properties.clone();
		
		return new ImageWing(this.getPath(), propertiesClone, landmarksClone);
	}


	/**
	 * Return the path of the image.
	 * 
	 * @return a String with the path of the image
	 */
	public String getPath() {
		return path;
	}

	/**
     * Set the path of the image
     * 
     * @param path
     *            A String with the new path.
     * 
     */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Return the properties of the image.
	 * 
	 * @return a Map with the properties
	 */
	public Map<String, String> getProperties() {
		return properties;
	}

	/**
     * Set the properties of the image
     * 
     * @param properties
     *            A Map with the new properties.
     * 
     */
	public void setProperties(HashMap<String, String> properties) {
		this.properties = properties;
	}

	/**
	 * Return the path of the image.
	 * 
	 * @return a ArrayList with the landmarks of the image
	 */
	public ArrayList<Landmark> getLandmarks() {
		return landmarks;
	}

	/**
     * Set the landmarks of the image
     * 
     * @param landmarks
     *            An ArrayList with the new landmarks.
     *            
     * @see Landmark
     */
	public void setLandmarks(ArrayList<Landmark> landmarks) {
		this.landmarks = landmarks;
	}

	/**
	 * Add landmark, who is given in parameter, to the image.
	 * 
	 * @param landmark
	 * 				The new landmark.
	 * 
	 * @see Landmark
	 */
	public void addLandmark(Landmark landmark) {
		this.landmarks.add(landmark);
	}

	/**
	 * Remove landmark, who is given in parameter, to the image.
	 * 
	 * @param landmark
	 * 				The deleted landmark.
	 * 
	 * @see Landmark
	 */
	public void deleteLandmark(Landmark landmark) {
		this.landmarks.remove(landmark);

	}

}