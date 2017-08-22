package businesslogic;

/**
 * <b>This class represents the Landmark object.</b>
 * 
 * <p>
 * A Landmark is an object who represents a landmark in the image.
 * </p>
 * <p>
 * A ImageWing is characteristic by:
 * <ul>
 * <li>A X position</li>
 * <li>A Y position</li>
 * <li>A boolean if the landmark is a good one or not</li>
 * </ul>
 *
 */
public class Landmark {

   

    @Override
	public String toString() {
    	String X = String.valueOf(posX);
    	String Y = String.valueOf(posY);
    	String isLand = String.valueOf(isLandmark);
    	//String[] tab = {" X ", " Y ", "isLand"};
    	String test = X   +   "     "   +   Y   +   "     "   +   isLand   + "\n";
    	return test;
	}

	/**
     * X position of the landmark
     * 
     * @see Landmark#getPosX()
     * @see Landmark#Landmark(float, float, Boolean)
     */
    public float posX = 0;

    /**
     * X position of the landmark
     * 
     * @see Landmark#getPosY()
     * @see Landmark#Landmark(float, float, Boolean)
     */
    public float posY = 0;

    /**
     * True : this crosspoint is a landmark
     * False : this crosspoint is not a landmark
     * 
     * @see Landmark#getIsLandmark()
     * @see Landmark#Landmark(float, float, Boolean)
     */
    public Boolean isLandmark = false;
    
    
	/**
	 * New image builder.
	 * <p>
	 * This builder create an new image without landmark and property.
	 * </p>
	 * 
	 * @param posX2
	 *            The X position of the landmark
	 * @param posY2
	 *            The Y position of the landmark           
	 * @param isLandmark2
	 *            if the landmark is a good one or not
	 *            
	 */
    public Landmark(float posX2, float posY2, Boolean isLandmark2) {
    	this.posX = posX2;
    	this.posY = posY2;
    	this.isLandmark = isLandmark2;
    }
    
	/**
	 * Return the X position of the landmark.
	 * 
	 * @return a float with the X position of the landmark
	 */
    public float getPosX()
    {
    	return this.posX;
    }

	/**
	 * Return the Y position of the landmark.
	 * 
	 * @return a float with the Y position of the landmark
	 */
    public float getPosY()
    {
    	return this.posY;
    }
    
	/**
	 * Return true if the landmark is good, false if is not good.
	 * 
	 * @return a boolean with the state of the landmark
	 */    
    public Boolean getIsLandmark()
    {
    	return this.isLandmark;
    }

	public void setPosX(int x) {
		this.posX = x;
		
	}

	public void setPosY(int y) {
		this.posY = y;
	}

	public Landmark clonage() {
		// TODO Auto-generated method stub
		return new Landmark(this.getPosX(), this.getPosY(), this.getIsLandmark());
	}

	public void setIsLandmark(boolean b) {
this.isLandmark = b;		
	}
}