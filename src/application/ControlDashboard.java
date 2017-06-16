package application;



import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import businesslogic.*;
import facade.Facade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * 
 */
public class ControlDashboard {

    /**
     * Default constructor
     */
    public ControlDashboard() {
    }

    private int page=0;
    private int currentView=4;
    private Image[] imageTab = null;
	private String[] nameTab = null;
    
    @FXML
    private BorderPane table;
    
    
    
    @FXML
    void loadImages(ActionEvent event) {
    		
		JFileChooser image = new JFileChooser();
		image.setMultiSelectionEnabled(true);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"TIF, JPG & PNG Images", "jpg", "png", "jpeg", "tif");
		image.setFileFilter(filter);
		int returnVal = image.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			File[] files = image.getSelectedFiles();
			for(int i = 0; i < files.length; i++)
			{
				String result = files[i].getAbsolutePath().toString();
				this.addImage(result);
			}

		} else { 
			//writeConsole("Open command cancelled by user."); 
		} 

	} 

    @FXML
    void newProject(ActionEvent event) {
    		
    }

    @FXML
    void openProject(ActionEvent event) {
    		
    }
    
    @FXML
    void next(MouseEvent event) {
    	
    	System.out.println("NEXT");
    	
    	if(this.page < this.imageTab.length)
    	{
    		this.page++;
    	}
    	
    	if(this.currentView == 1)
    	{
    		this.view1();
    	}
    	if(this.currentView == 4)
    	{
    		this.view4();
    	}
    	else
    	{
    		this.view9();
    	}

    }


    @FXML
    void previous(MouseEvent event) {
    	if(this.page > 0)
    	{
    		this.page--;
    	}
    	
    	if(this.currentView == 1)
    	{
    		this.view1();
    	}
    	if(this.currentView == 4)
    	{
    		this.view4();
    	}
    	else
    	{
    		this.view9();
    	}

    }


    @FXML
    void view1(ActionEvent event) {
    	this.view1();

    }


    @FXML
    void view4(ActionEvent event) {
    	this.view4();
    }
    
    
    @FXML
    void view9(ActionEvent event) {
    	this.view9();
    }
    
    
    
    void initImage() {
    	Image[] imageTab = null;
    	String[] nameTab = null;
    	
		ArrayList<ImageWing> images = Facade.currentProject.getImages();
		Iterator<ImageWing> it = images.iterator();
		int i = 0;
		while(it.hasNext())
		{
			
			ImageWing im = it.next();
			File file = new File(im.getPath());
			Image temp = new Image(file.toURI().toString());
			imageTab[i] = temp;
			nameTab[i] = im.getProperties().get("FILENAME");
			i++;
			
		}
		this.nameTab = nameTab;
		this.imageTab = imageTab;
    	this.view4();
    }


    /**
     * Save the current project
     */
    @FXML
    void saveProject(ActionEvent event) {
    		Facade.saveProject();
    }
    
    /**
     * Add image to the current project
     * @param path
     */
    public void addImage(String path) {
        Facade.addImage(path);
    }

    /**
     * delete image to the current project
     * @param image
     */
    public void deleteImage(ImageWing image) {
        // TODO implement here
    }


    /**
     * Save a copy of the current project
     */
    public void saveAsProject() {
        // TODO implement here
    }

    /**
     * Load an existing project, it replaces the current one.
     * @param path
     */
    public void loadProject(String path) {
        // TODO implement here
    }
    
    
    
    public void view1()
    {
    	Image[] images = this.imageTab;
    	String[] names = this.nameTab;
    	
    	GridPane grid = new GridPane();
    	BorderPane border1 = new BorderPane();
    	
    	ImageView im1 = new ImageView();
    	im1.setPreserveRatio(true);
    	im1.setImage(images[this.page]);
    	im1.setFitWidth(this.table.getWidth() - 100);
    	border1.setCenter(im1);

    	Label lb1 = new Label();
    	lb1.setText(names[this.page]);
    	border1.setBottom(lb1);
    	BorderPane.setAlignment(lb1, Pos.CENTER);

    	this.table.setCenter(grid);
    	grid.add(border1, 0, 0);	
    	
    }  
    
    public void view4()
    {
    	
    	Image[] images = this.imageTab;
    	String[] names = this.nameTab;

    	
    	GridPane grid = new GridPane();
    	
    	for(int i = this.page*2; i<this.page*2 +4; i++)
    	{
    		BorderPane border = new BorderPane();
    		ImageView im1 = new ImageView();
        	im1.setPreserveRatio(true);
        	im1.setImage(images[i]);
        	im1.setFitWidth(this.table.getWidth()/2 - 60);
        	border.setCenter(im1);
        	//BorderPane.setMargin(im1, new Insets(0,10,0,0));
        	
        	Label lb1 = new Label();
        	lb1.setText(names[i]);
        	border.setBottom(lb1);
        	BorderPane.setAlignment(lb1, Pos.CENTER);
        	
        	if(i == 0)
        	{
        		grid.add(border, 0, 0);
        	}
        	else if(i == 1)
        	{
        		grid.add(border, 0, 1);
        	}
        	else if(i == 2)
        	{
        		grid.add(border, 1, 0);
        	}
        	else
        	{
        		grid.add(border, 1, 1);
        	}
   
    	}

    	this.table.setCenter(grid);
    	
    	
    	

    }
    
    public void view9()
    {
    	Image[] images = this.imageTab;
    	String[] names = this.nameTab;

    	
    	GridPane grid = new GridPane();
    	
    	for(int i = this.page*3; i<this.page*3 +9; i++)
    	{
    		BorderPane border = new BorderPane();
    		ImageView im1 = new ImageView();
        	im1.setPreserveRatio(true);
        	im1.setImage(images[i]);
        	im1.setFitWidth(this.table.getWidth()/3 - 60);
        	border.setCenter(im1);
        	//BorderPane.setMargin(im1, new Insets(0,10,0,0));
        	
        	Label lb1 = new Label();
        	lb1.setText(names[i]);
        	border.setBottom(lb1);
        	BorderPane.setAlignment(lb1, Pos.CENTER);
        	 	
        	if(i == 0)
        	{
        		grid.add(border, 0, 0);
        	}
        	else if(i == 1)
        	{
        		grid.add(border, 0, 1);
        	}
        	else if(i == 2)
        	{
        		grid.add(border, 0, 2);
        	}
        	else if(i == 3)
        	{
        		grid.add(border, 1, 0);
        	}
        	else if(i == 4)
        	{
        		grid.add(border, 1, 1);
        	}
        	else if(i == 5)
        	{
        		grid.add(border, 1, 2);
        	}
        	else if(i == 6)
        	{
        		grid.add(border, 2, 0);
        	}
        	else if(i == 7)
        	{
        		grid.add(border, 2, 1);
        	}
        	else
        	{
        		grid.add(border, 2, 2);
        	}
    	}

    	this.table.setCenter(grid);
    	    	

    }


    
}