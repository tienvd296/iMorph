package application;



import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import businesslogic.*;
import facade.Facade;
import helper.Keyboard;
import ij.ImageJ;
import ij.ImagePlus;
import ij.io.Opener;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.DropShadow;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

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
	private int currentView=1;
	private Image[] imageTab = null;
	private String[] nameTab = null;
	private String[] pathTab = null;
	private Map<String, ImageWing> imageMap = new HashMap();
	private ArrayList<ImageView> selected = new ArrayList<ImageView>();

	@FXML
	private BorderPane table;
	
    @FXML
    private Menu viewChoice;

	@FXML
	private TextArea console;

	@FXML
	private AnchorPane metadataPane;

	@FXML
	private AnchorPane propertiesPane;

	@FXML
	private AnchorPane landmarksPane;


/*    @FXML
    void reloadView(MouseEvent event) {
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
    }*/

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
				ImagePlus im = new Opener().openTiff(result, "");
				this.addImage(result, im.getHeight(), im.getWidth());
			}
			writeConsole(files.length + " images added to the project", "ImageBrowser");
			this.initImage();

		} else { 
			writeConsole("Open command cancelled by user.", "ImageBrowser"); 
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

		if(this.page < (this.imageTab.length - 1))
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
	void view1(ActionEvent event) {
		this.view1();
		this.viewChoice.setText("Simple");

	}


	@FXML
	void view4(ActionEvent event) {
		this.view4();
		this.viewChoice.setText("4");
	}


	@FXML
	void view9(ActionEvent event) {
		this.view9();
		this.viewChoice.setText("9");
	}



	void initImage() {

		int nbImage = Facade.currentProject.getImages().size();

		Image[] imageTab = new Image[nbImage];
		String[] nameTab = new String[nbImage];
		String[] pathTab = new String[nbImage];


		ArrayList<ImageWing> images = Facade.getImages();
		Iterator<ImageWing> it = images.iterator();
		int i = 0;
		while(it.hasNext())
		{

			ImageWing im = it.next();
			ImagePlus imagePlus = new Opener().openTiff(im.getPath(), "");
			BufferedImage bufferedImage = imagePlus.getBufferedImage();
			Image temp = SwingFXUtils.toFXImage(bufferedImage, null);
			pathTab[i] = im.getPath();
			this.imageMap.put(pathTab[i], im);
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
		writeConsole("Save project: Success", "Project");
	}

	/**
	 * Add image to the current project
	 * @param path
	 */
	public void addImage(String path, double height, double width) {
		Facade.addImage(path, height, width);
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
		im1.setOnMouseClicked(e -> imageEditor(pathTab[y], im1));
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


		this.displayLandmark(pane, im1, ratioImg, height/width, this.page);
		this.imageEditor(pathTab[y], im1);
		this.table.setCenter(grid);
		grid.add(pane, 0, 0);	

	}  

	private void displayLandmark(Pane pane, ImageView image, double ratio1, double ratio2, int i) {

		ImageWing im = this.imageMap.get(pathTab[i]);
		ArrayList<Landmark> landmarks = Facade.getAllLandmark(im);
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
			c.setCenterX(originX + Facade.getX_ratio(landmark, im)*width);
			c.setCenterY(originY + Facade.getY_ratio(landmark, im)*height);
			c.setRadius(3.0);
			c.setFill(Color.RED);
			pane.getChildren().add(y+1, c);
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
				im1.setOnMouseClicked(e -> imageEditor(pathTab[y], im1));
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
				//pane.getChildren().add(1, lb1);
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
				im1.setOnMouseClicked(e -> imageEditor(pathTab[y], im1));
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
				//pane.getChildren().add(1, lb1);
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

	public void imageEditor(String path, ImageView imageView)
	{
		if(!Keyboard.isCtrl())
		{
	    	deselectAll();
	  		this.selected.clear();
	  		selected.add(imageView);
		}
		else
		{
			if(selected.contains(imageView))
			{
				selected.remove(imageView);
				imageView.setEffect(null);
			}
			else
			{
				selected.add(imageView);
			}
		}
		displaySelected();

		
		ImageWing image = this.imageMap.get(path);
		displayProperties(image);
		displayLandmarks(image);
		
	}
	
	private void deselectAll() {
		Iterator<ImageView> it = this.selected.iterator();
		while(it.hasNext())
		{
			it.next().setEffect( null );
		}
	}

	private void displaySelected() {
		Iterator<ImageView> it = this.selected.iterator();
		while(it.hasNext())
		{
			DropShadow ds = new DropShadow( 20, Color.AQUA );
			it.next().setEffect( ds );
		}
		
		
	}

	private void displayProperties(ImageWing image)
	{
		this.propertiesPane.getChildren().clear();
		if(Facade.hasProperties(image))
		{
			int i = 0;
			GridPane grid = new GridPane();

			// Setting columns size in percent
			ColumnConstraints column = new ColumnConstraints();
			column.setPercentWidth(50);
			grid.getColumnConstraints().add(column);

			column = new ColumnConstraints();
			column.setPercentWidth(50);
			grid.getColumnConstraints().add(column);

			grid.setPrefSize(this.propertiesPane.getWidth(), this.propertiesPane.getHeight()); // Default width and height

			for (Map.Entry<String, String> entry : image.getProperties().entrySet())
			{

				Label key = new Label(entry.getKey());
				Label value = new Label(entry.getValue());
				value.setOnMouseClicked(e -> propertiesEditor(image, entry.getKey()));

				grid.add(key, 0, i);
				grid.add(value, 1, i);

				i++;

			}

			Label add = new Label("Add a new property");
			add.setOnMouseClicked(e -> propertiesAdd(image));

			grid.add(add, 0, i);

			this.propertiesPane.getChildren().add(0, grid);


		}
	}
	
	private void displayLandmarks(ImageWing image)
	{
		this.landmarksPane.getChildren().clear();
		if(Facade.hasLandmarks(image))
		{
			int i = 1;
			GridPane grid = new GridPane();

			// Setting columns size in percent
			ColumnConstraints column = new ColumnConstraints();
			column.setPercentWidth(10);
			grid.getColumnConstraints().add(column);

			column = new ColumnConstraints();
			column.setPercentWidth(30);
			grid.getColumnConstraints().add(column);
			
			column = new ColumnConstraints();
			column.setPercentWidth(30);
			grid.getColumnConstraints().add(column);
			
			column = new ColumnConstraints();
			column.setPercentWidth(30);
			grid.getColumnConstraints().add(column);

			grid.setPrefSize(this.landmarksPane.getWidth(), this.landmarksPane.getHeight()); // Default width and height

			Iterator<Landmark> it = Facade.getAllLandmark(image).iterator();
						
			while (it.hasNext())
			{

				Landmark l = it.next();
				
				Label nb = new Label(Integer.toString(i));
				Label x = new Label(Float.toString(l.getPosX()));
				Label y = new Label(Float.toString(l.getPosY()));
				Label bool = new Label(Boolean.toString(l.getIsLandmark()));

				grid.add(nb, 0, i-1);
				grid.add(x, 1, i-1);
				grid.add(y, 2, i-1);
				grid.add(bool, 3, i-1);

				i++;

			}

			this.landmarksPane.getChildren().add(0, grid);


		}
	}

	
	
	
	private void propertiesAdd(ImageWing image) {
		TextInputDialog dialog = new TextInputDialog("Name of property");
		dialog.setTitle("Add property");
		dialog.setHeaderText("Add property");
		dialog.setContentText("Write a new property name :");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();

		if (result.isPresent())
		{

			TextInputDialog dialog2 = new TextInputDialog("Value of "+ result.get());
			dialog2.setTitle("Add property");
			dialog2.setHeaderText("Add property");
			dialog2.setContentText("Write a property value for "+ result.get() +":");

			// Traditional way to get the response value.
			Optional<String> result2 = dialog2.showAndWait();

			if (result2.isPresent()){
				Facade.addProperties(image, result.get(), result2.get());

			}	
		}
	}

	private void propertiesEditor(ImageWing image, String key) {
		TextInputDialog dialog = new TextInputDialog("properties");
		dialog.setTitle("Edit properties");
		dialog.setHeaderText("Edit " + key);
		dialog.setContentText("Write a new value for " + key + " :");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			Facade.setProperties(image, key, result.get());
			writeConsole("Editing to the properties of " + image.getProperties().get("FILENAME"), "Project");
		}
	}
	
	public void writeConsole(String msg, String auteur)
	{
		String old = this.console.getText();
		this.console.setText(old + auteur + ">> " + msg + "\n");
	}

	public void initialize() {
		
		if(Facade.currentProject != null)
		{
			this.initImage();
			writeConsole("Opening of the project: " + Facade.currentProject.name, "Project");
		}
		

	}



}