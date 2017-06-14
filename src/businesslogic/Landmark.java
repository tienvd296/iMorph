package businesslogic;

/**
 * 
 */
public class Landmark {

    /**
     * Default constructor
     * @param isLandmark2 
     * @param posY2 
     * @param posX2 
     */
    public Landmark(float posX2, float posY2, Boolean isLandmark2) {
    	this.posX = posX2;
    	this.posY = posY2;
    	this.isLandmark = isLandmark2;
    }

    /**
     * X position of the landmark
     */
    public float posX = 0;

    /**
     * Y position of the landmark
     */
    public float posY = 0;

    /**
     * True : this crosspoint is a landmark
     * False : this crosspoint is not a landmark
     */
    public Boolean isLandmark = false;
    
    public float getPosX()
    {
    	return this.posX;
    }

    public float getPosY()
    {
    	return this.posY;
    }
    
    public Boolean getIsLandmark()
    {
    	return this.isLandmark;
    }
}