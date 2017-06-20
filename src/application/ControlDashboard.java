package application;



import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import businesslogic.*;
import facade.Facade;
import ij.ImagePlus;
import ij.io.Opener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;

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
	private String[] pathTab = null;

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
				this.initImage();
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

		if(this.page < (this.imageTab.length))
		{
			this.page++;
		}

		if(this.currentView == 1)
		{
			this.view1();
		}
		else if(this.currentView == 4)
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

		int nbImage = Facade.currentProject.getImages().size();

		Image[] imageTab = new Image[nbImage];
		String[] nameTab = new String[nbImage];
		String[] pathTab = new String[nbImage];

		ArrayList<ImageWing> images = Facade.currentProject.getImages();
		Iterator<ImageWing> it = images.iterator();
		int i = 0;
		while(it.hasNext())
		{

			ImageWing im = it.next();
			ImagePlus imagePlus = new Opener().openTiff(im.getPath(), "");
			BufferedImage bufferedImage = imagePlus.getBufferedImage();
			Image temp = SwingFXUtils.toFXImage(bufferedImage, null);
			pathTab[i] = im.getPath();
			imageTab[i] = temp;
			nameTab[i] = im.getProperties().get("FILENAME");
			i++;

		}
		this.nameTab = nameTab;
		this.imageTab = imageTab;
		this.pathTab = pathTab;
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

		this.currentView = 1;

		GridPane grid = new GridPane();

		ImageView im1 = new ImageView();
		
		double width = this.table.getWidth() - 120;
		double height = this.table.getHeight() - 25;
		
		final int y = this.page;
		
		
		//------IMAGE-----//
		im1.setOnMouseClicked(e -> imageEditor(pathTab[y]));
		im1.setPreserveRatio(true);
		im1.setImage(images[this.page]);
		im1.setFitWidth(width - 10);
		Pane pane = new Pane();
		double ratioImg = images[this.page].getHeight()/images[this.page].getWidth();
		if(ratioImg > height/width)
		{
			im1.setFitHeight(height-20);
			im1.setX((width - im1.getFitHeight()/ratioImg) / 2);
			im1.setY((height - im1.getFitHeight()) / 2);
		}
		else
		{
			im1.setFitWidth(width - 20);
			im1.setX((width - im1.getFitWidth()) / 2);
			im1.setY((height - im1.getFitWidth()*ratioImg) / 2);
		}
		pane.setPrefHeight(height);
		pane.setPrefWidth(width);
		pane.getChildren().add(0, im1);

		
		Label lb1 = new Label();
		lb1.setText(names[this.page]);
		pane.getChildren().add(1, lb1);
		lb1.setLayoutX(im1.getX());
		if(ratioImg > height/width)
		{
			lb1.setLayoutY(im1.getY() + im1.getFitHeight());
		}
		else
		{
			lb1.setLayoutY(im1.getY() + im1.getFitWidth()*ratioImg);
		}

		
		this.displayLandmark(pane, im1, ratioImg, height/width, this.page);
		this.table.setCenter(grid);
		grid.add(pane, 0, 0);	

	}  

	private void displayLandmark(Pane pane, ImageView image, double ratio1, double ratio2, int i) {
		
		ImageWing im = Facade.currentProject.getImages().get(i);
		ArrayList<Landmark> landmarks = im.getLandmarks();
		Iterator<Landmark> it = landmarks.iterator();
		int y = 0;
		while(it.hasNext())
		{
			Landmark landmark = it.next();
			double originX = image.getX();
			
			double height = 0;
			double width = 0;
			if(ratio1 > ratio2)
			{
				height = image.getFitHeight();
				width = image.getFitHeight() / ratio1;
			}
			else
			{
				height = image.getFitWidth() * ratio1;
				width = image.getFitWidth();
			}
			double originY = image.getY();
		    Circle c = new Circle();
		    c.setCenterX(originX + landmark.getPosX()*width);
		    c.setCenterY(originY + landmark.getPosY()*height);
		    c.setRadius(3.0);
		    c.setFill(Color.RED);
		    pane.getChildren().add(y+2, c);
		    y++;
		}
		
	}

	public void view4()
	{

		Image[] images = this.imageTab;
		String[] names = this.nameTab;

		this.currentView = 4;
		
		double width = (this.table.getWidth() - 120) / 2;
		double height = (this.table.getHeight() - 40) / 2;


		GridPane grid = new GridPane();

		int marge = this.page*2;

		for(int i = marge; i<marge+4; i++)
		{
			Pane pane = new Pane();
			if(images.length > i)
			{
				ImageView im1 = new ImageView();
				
				final int y = i;
				im1.setOnMouseClicked(e -> imageEditor(pathTab[y]));
				im1.setPreserveRatio(true);
				im1.setImage(images[i]);
				
				double ratioImg = images[i].getHeight()/images[i].getWidth();
				if(ratioImg > height/width)
				{
					im1.setFitHeight(height-20);
					im1.setX((width - im1.getFitHeight()/ratioImg) / 2);
					im1.setY((height - im1.getFitHeight()) / 2);
				}
				else
				{
					im1.setFitWidth(width - 20);
					im1.setX((width - im1.getFitWidth()) / 2);
					im1.setY((height - im1.getFitWidth()*ratioImg) / 2);
				}
				pane.setPrefHeight(height);
				pane.setPrefWidth(width);
				pane.getChildren().add(0, im1);

				
				Label lb1 = new Label();
				lb1.setText(names[i]);
				pane.getChildren().add(1, lb1);
				lb1.setLayoutX(im1.getX());
				if(ratioImg > height/width)
				{
					lb1.setLayoutY(im1.getY() + im1.getFitHeight());
				}
				else
				{
					lb1.setLayoutY(im1.getY() + im1.getFitWidth()*ratioImg);
				}
				
				
				this.displayLandmark(pane, im1, ratioImg, height/width, i);
			}
			if(i == marge)
			{
				grid.add(pane, 0, 0);
			}
			else if(i == marge + 1)
			{
				grid.add(pane, 0, 1);
			}
			else if(i == marge + 2)
			{
				grid.add(pane, 1, 0);
			}
			else
			{
				grid.add(pane, 1, 1);
			}

		}

		this.table.setCenter(grid);




	}

	public void view9()
	{
		Image[] images = this.imageTab;
		String[] names = this.nameTab;

		this.currentView = 9;
		
		double width = (this.table.getWidth() - 120) / 3;
		double height = (this.table.getHeight() - 40) / 3;


		GridPane grid = new GridPane();

		int marge = this.page*3;

		for(int i = marge; i<marge+9; i++)
		{
			Pane pane = new Pane();
			if(images.length > i)
			{
				ImageView im1 = new ImageView();
				
				final int y = i;
				im1.setOnMouseClicked(e -> imageEditor(pathTab[y]));
				im1.setPreserveRatio(true);
				im1.setImage(images[i]);
				
				double ratioImg = images[i].getHeight()/images[i].getWidth();
				if(ratioImg > height/width)
				{
					im1.setFitHeight(height-20);
					im1.setX((width - im1.getFitHeight()/ratioImg) / 2);
					im1.setY((height - im1.getFitHeight()) / 2);
				}
				else
				{
					im1.setFitWidth(width - 20);
					im1.setX((width - im1.getFitWidth()) / 2);
					im1.setY((height - im1.getFitWidth()*ratioImg) / 2);
				}
				pane.setPrefHeight(height);
				pane.setPrefWidth(width);
				pane.getChildren().add(0, im1);

				
				Label lb1 = new Label();
				lb1.setText(names[i]);
				pane.getChildren().add(1, lb1);
				lb1.setLayoutX(im1.getX());
				if(ratioImg > height/width)
				{
					lb1.setLayoutY(im1.getY() + im1.getFitHeight());
				}
				else
				{
					lb1.setLayoutY(im1.getY() + im1.getFitWidth()*ratioImg);
				}
				
				
				this.displayLandmark(pane, im1, ratioImg, height/width, i);
			}
			if(i == marge)
			{
				grid.add(pane, 0, 0);
			}
			else if(i == marge + 1)
			{
				grid.add(pane, 0, 1);
			}
			else if(i == marge + 2)
			{
				grid.add(pane, 0, 2);
			}
			else if(i == marge + 3)
			{
				grid.add(pane, 1, 0);
			}
			else if(i == marge + 4)
			{
				grid.add(pane, 1, 1);
			}
			else if(i == marge + 5)
			{
				grid.add(pane, 1, 2);
			}
			else if(i == marge + 6)
			{
				grid.add(pane, 2, 0);
			}
			else if(i == marge + 7)
			{
				grid.add(pane, 2, 1);
			}
			else
			{
				grid.add(pane, 2, 2);
			}

		}

		this.table.setCenter(grid);






	}

	public void imageEditor(String path)
	{
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(path);
		alert.showAndWait();
	}

	public void initialize() {

		this.initImage();

	}



}