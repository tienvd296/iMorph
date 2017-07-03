package application;



import java.awt.FileDialog;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import businesslogic.*;
import facade.Facade;
import helper.Keyboard;
import ij.ImagePlus;
import ij.io.Opener;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	private Folder[] folderTab = null;
	private Map<String, ImageWing> imageMap = new HashMap<String, ImageWing>();
	private ArrayList<ImageView> selected = new ArrayList<ImageView>();
	private Folder currentFolder = null;

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





	@FXML
	void loadImages(ActionEvent event) {

		JFrame jFrame = new JFrame();
		FileDialog fd = new FileDialog(jFrame,  "Choose a file", FileDialog.LOAD);
		fd.setDirectory("C:\\");
		fd.setFile("*.tif");
		fd.setMultipleMode(true);
		fd.setVisible(true);
		File[] files = fd.getFiles();
		if (files.length == 0)
			writeConsole("Open command cancelled by user.", "ImageBrowser"); 
		else
		{
			for(int i = 0; i < files.length; i++)
			{
				writeConsole("STEP5", "DEBUG");
				String result = files[i].getAbsolutePath().toString();
				writeConsole("STEP6", "DEBUG");
				ImagePlus im = new Opener().openTiff(result, "");
				writeConsole("STEP7 " + im.getHeight(), "DEBUG");
				this.addImage(result, im.getHeight(), im.getWidth());
				writeConsole("STEP8", "DEBUG");
			}
			writeConsole(files.length + " images added to the project", "ImageBrowser");
			this.initImage(this.currentFolder);

		} 
		this.reloadView();


	} 

	private void reloadView() {
		if(this.currentView == 1)
		{
			this.view1();
		}
		else
		{
			this.view((int) Math.sqrt(currentView));
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
		if(this.page <= ((this.imageTab.length - 1) / (Math.sqrt(this.currentView))-1))
		{
			this.page++;
		}		
		this.reloadView();
	}


	@FXML
	void previous(MouseEvent event) {

		if(this.page > 0)
		{
			this.page--;
		}

		this.reloadView();

	}


	@FXML
	void view1(ActionEvent event) {
		this.view1();
		this.page = 0;
		this.viewChoice.setText("Simple");

	}


	@FXML
	void view4(ActionEvent event) {
		this.view(2);
		this.page = 0;
		this.viewChoice.setText("4");
	}


	@FXML
	void view9(ActionEvent event) {
		this.view(3);
		this.page = 0;
		this.viewChoice.setText("9");
	}

	@FXML
	void view16(ActionEvent event) {
		this.view(4);
		this.page = 0;
		this.viewChoice.setText("16");
	}

	@FXML
	void view25(ActionEvent event) {
		this.view(5);
		this.page = 0;
		this.viewChoice.setText("25");
	}



	void initImage(Folder folder) {

		int nbImage = 0;
		ArrayList<ImageWing> images = new ArrayList<ImageWing>();
		int nbFolder = 0;
		ArrayList<Folder> folders = new ArrayList<Folder>();

		if(folder == null)
		{
			nbImage = Facade.currentProject.getImages().size();
			images = Facade.getImages();
			nbFolder = Facade.currentProject.getFolders().size();
			folders = Facade.currentProject.getFolders();
		}
		else
		{
			nbImage = folder.getImages().size();
			images = folder.getImages();
			nbFolder = folder.getFolders().size();
			folders = folder.getFolders();
		}


		Image[] imageTab = new Image[nbImage];
		String[] nameTab = new String[nbImage];
		String[] pathTab = new String[nbImage];
		Folder[] folderTab = new Folder[nbFolder];


		Iterator<Folder> it2 = folders.iterator();
		int x = 0;
		while(it2.hasNext())
		{
			Folder fd = it2.next();
			folderTab[x] = fd;
		}

		int i = 0;
		Iterator<ImageWing> it = images.iterator();
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
		this.folderTab = folderTab;
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


	public void view(int nbImg)
	{

		Image[] images = this.imageTab;
		String[] names = this.nameTab;
		Folder[] folder = this.folderTab;
		int sizeFolder = 0;

		if(folderTab != null)
		{
			sizeFolder = this.folderTab.length;
		}


		this.currentView = (int) Math.pow(nbImg, 2);


		double width = (this.table.getWidth() - 120) / nbImg;
		double height = (this.table.getHeight() - 40) / nbImg;


		GridPane grid = new GridPane();

		int marge = this.page*nbImg;


		for(int i = marge; i<marge+Math.pow(nbImg, 2); i++)
		{
			Pane pane = new Pane();
			if(sizeFolder > i)
			{
				ImageView im1 = new ImageView();

				final int y = i;
				im1.setOnMouseClicked(e -> clickFolder(folder[y]));
				im1.setPreserveRatio(true);
				Image folderImage = new Image("open-folder-outline.png");
				im1.setImage(folderImage);

				double ratioImg = folderImage.getHeight()/folderImage.getWidth();
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
			}
			else if(images.length + sizeFolder > i)
			{
				ImageView im1 = new ImageView();

				final int y = i;
				final int s = sizeFolder;
				im1.setOnMouseClicked(e -> imageEditor(pathTab[y - s], im1));
				im1.setPreserveRatio(true);
				im1.setImage(images[i - sizeFolder]);

				double ratioImg = images[i - sizeFolder].getHeight()/images[i - sizeFolder].getWidth();
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


				this.displayLandmark(pane, im1, ratioImg, height/width, i-s);
			}
			grid.add(pane, i/nbImg, i%nbImg);

		}

		this.table.setCenter(grid);




	}

	private void clickFolder(Folder folder) {
		//INFO
		//double click => open
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
		displayMetadata(image);
		displayProperties(image);
		displayLandmarks(image);

	}

	private void displayMetadata(ImageWing image) {

		this.metadataPane.getChildren().clear();

		HashMap<String, String> map = Facade.metadataExtractor(new File(image.getPath()));

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

		if(map != null)
		{

			for (Map.Entry<String, String> entry : map.entrySet())
			{

				Label key = new Label(entry.getKey());
				Label value = new Label(entry.getValue());

				grid.add(key, 0, i);
				grid.add(value, 1, i);

				i++;

			}
		}
		else{
			Label mes = new Label("No metadata for this image.");
			grid.add(mes, 0, i);
		}
		this.metadataPane.getChildren().add(0, grid);
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


		this.console.appendText(auteur + ">> " + msg + "\n");
	}



	@FXML
	void newFolder(ActionEvent event) {
		Facade.addFolder("test1");
		writeConsole("1 folder added to the project", "ImageBrowser");
		this.initImage(this.currentFolder);
	}


	public void initialize() {

		if(Facade.currentProject != null)
		{
			this.initImage(this.currentFolder);
			writeConsole("Opening of the project: " + Facade.currentProject.name, "Project");
		}


	}



}