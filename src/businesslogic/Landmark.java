package businesslogic;

/**
 * 
 */
public class Landmark {

    /**
     * Default constructor
     */
    public Landmark() {
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