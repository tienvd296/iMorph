package application;

import java.awt.Color;
import java.awt.Graphics;

public class drawCircle {

	private Graphics cg;
	private int xCenter;
	private int yCenter;
	private int r;
	private Boolean isLandmark; 
	private int isSelected;
	public int getIsSelected() {
		return isSelected;
	}



	public void setIsSelected(int isSelected) {
		this.isSelected = isSelected;
	}



	public drawCircle(Graphics cg, int xCenter, int yCenter, int r, boolean isLandmark, int isSelected, int displayLandmark){
		this.cg =cg;
		this.xCenter = xCenter;
		this.yCenter = yCenter;
		this.r = r;
		this.isLandmark = isLandmark;
		this.isSelected = isSelected;
		if(displayLandmark == 0 ) {


			if(isSelected == 0){
				
				if( isLandmark == true) {
					cg.setColor(Color.red);
					cg.fillOval(xCenter-r, yCenter-r, 2*r, 2*r);
					
				} 
				else if( isLandmark == false) {
					cg.setColor(Color.blue);
					cg.fillOval(xCenter-r, yCenter-r, 2*r, 2*r);
				}
			}else if(isSelected == 1){
				cg.setColor(Color.green);
				cg.fillOval(xCenter-r, yCenter-r, 2*r, 2*r);
			}

		}
		else if( displayLandmark == 1){

			if(isSelected == 0){
				if( isLandmark == true) {
					cg.setColor(Color.red);
					cg.fillOval(xCenter-r, yCenter-r, 2*r, 2*r);
				} 
				else if( isLandmark == false) {
					cg.setColor(Color.blue);
					//cg.fillOval(xCenter-r, yCenter-r, 2*r, 2*r);

				}
			}else if(isSelected == 1){
				cg.setColor(Color.green);
				cg.fillOval(xCenter-r, yCenter-r, 2*r, 2*r);
			}
		}
		else if ( displayLandmark == 2){

			if(isSelected == 0){
				if( isLandmark == true) {
					cg.setColor(Color.red);
					//cg.fillOval(xCenter-r, yCenter-r, 2*r, 2*r);


				} 
				else if( isLandmark == false) {
					cg.setColor(Color.blue);
					cg.fillOval(xCenter-r, yCenter-r, 2*r, 2*r);
				}
			}else if(isSelected == 1){
				cg.setColor(Color.green);
				cg.fillOval(xCenter-r, yCenter-r, 2*r, 2*r);
			}
		}


	}



	public Graphics getCg() {
		return cg;
	}


	public void setCg(Graphics cg) {
		this.cg = cg;
	}


	public int getxCenter() {
		return xCenter;
	}


	public void setxCenter(int xCenter) {
		this.xCenter = xCenter;
	}


	public int getyCenter() {
		return yCenter;
	}


	public void setyCenter(int yCenter) {
		this.yCenter = yCenter;
	}


	public int getR() {
		return r;
	}


	public void setR(int r) {
		this.r = r;
	}


	public Boolean getIsLandmark() {
		return isLandmark;
	}


	public void setIsLandmark(Boolean isLandmark) {
		this.isLandmark = isLandmark;
	}


}
