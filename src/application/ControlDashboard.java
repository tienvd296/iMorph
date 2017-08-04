package application;



import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import affichage.Cadre2;
import businesslogic.*;
import facade.Facade;
import helper.Keyboard;
import ij.ImagePlus;
import ij.io.Opener;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Pair;

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
	private String[] pathTab = null;
	private Folder[] folderTab = null;
	private Map<String, ImageWing> imageMap = new HashMap<String, ImageWing>();
	private ArrayList<ImageView> selected = new ArrayList<ImageView>();
	public Folder currentFolder = null;
	private HashMap<ImageView, String> imageViewToPath = new HashMap<ImageView, String>();
	private HashMap<String, ImageWing> pathToImageWing = new HashMap<String, ImageWing>();



	@FXML
	private ImageView backFolder;

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
	private Folder activeFolder;

	final ContextMenu contextMenuFolder = new ContextMenu();

	final ContextMenu contextMenuImage = new ContextMenu();

	Menu itemImage2 = new Menu();


	@FXML
	void undo(ActionEvent event) {
		Facade.undo();
		this.refresh();
	}

	@FXML
	void redo(ActionEvent event) {
		Facade.redo();
		this.refresh();
	}

	@FXML
	void loadImages(ActionEvent event) {

		FileChooser chooser = new FileChooser();
		chooser.setTitle("Load images");
		chooser.getExtensionFilters().addAll(
				new ExtensionFilter("TIFF Files", "*.tif"));
		List<File> files = chooser.showOpenMultipleDialog(new Stage());
		if (files.size() == 0)
			writeConsole("Open command cancelled by user.", "ImageBrowser");
		else
		{
			int error = 0;
			ArrayList<String> listPath = new ArrayList<String>();
			for(int z = 0; z < this.pathTab.length; z++)
			{
				listPath.add(this.pathTab[z]);
			}
			for(int i = 0; i < files.size(); i++)
			{
				String result = files.get(i).getAbsolutePath().toString();
				if(!listPath.contains(result))
				{

					ImagePlus im = new Opener().openTiff(result, "");
					this.addImage(result, im.getHeight(), im.getWidth());
				}
				else
				{
					error++;
					writeConsole(result + " is already in the project.", "ImageBrowser");
				}
			}
			writeConsole((files.size() - error) + " images added to the project", "ImageBrowser");
			this.initImage(this.currentFolder);

		}
		this.reloadView();


	}

	/**
	 * delete image to the current project
	 */
	public void deleteImages() {
		Iterator<ImageView> it = selected.iterator();
		while(it.hasNext())
		{
			ImageView imV = it.next();
			ImageWing imW = this.pathToImageWing.get(this.imageViewToPath.get(imV));
			Facade.deleteImage(imW, currentFolder);
			writeConsole(imW.getPath() + " has just been deleted.", "ImageBrowser");
		}
		this.refresh();

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

	public void refresh()
	{
		this.initialize();
		this.initImage(this.currentFolder);
		this.view((int)Math.sqrt(this.currentView));
		this.page = 0;
	}

	@FXML
	void newProject(ActionEvent event) {

	}

	@FXML
	void openProject(ActionEvent event) {

	}

	@FXML
	void next(MouseEvent event) {

		this.selected.clear();

		if(this.page <= ((this.imageTab.length - 1) / (Math.sqrt(this.currentView))-1))
		{
			this.page++;
		}
		this.reloadView();
	}


	@FXML
	void previous(MouseEvent event) {

		this.selected.clear();

		if(this.page > 0)
		{
			this.page--;
		}
		this.reloadView();

	}


	@FXML
	void backFolder(MouseEvent event) {
		if(this.currentFolder != null)
		{
			this.currentFolder = this.currentFolder.getParent();
			this.initImage(this.currentFolder);
		}
		if(this.currentFolder == null)
		{
			this.backFolder.setVisible(false);
		}
		this.reloadView();
	}


	@FXML
	void view1(ActionEvent event) {
		this.view1();
		this.currentView = 1;
		this.page = 0;

	}


	@FXML
	void view4(ActionEvent event) {
		this.view(2);
		this.currentView = 4;
		this.page = 0;
	}


	@FXML
	void view9(ActionEvent event) {
		this.view(3);
		this.currentView = 9;
		this.page = 0;
	}

	@FXML
	void view16(ActionEvent event) {
		this.view(4);
		this.currentView = 16;
		this.page = 0;
	}

	@FXML
	void startView(MouseEvent event) {
		this.view(4);
		this.currentView = 16;
		this.page = 0;
	}

	@FXML
	void view25(ActionEvent event) {
		this.view(5);
		this.currentView = 25;
		this.page = 0;
	}



	void initImage(Folder folder) {
		this.selected.clear();
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
			x++;
		}

		int i = 0;
		Iterator<ImageWing> it = images.iterator();
		while(it.hasNext())
		{

			ImageWing im = it.next();

			this.pathToImageWing.put(im.getPath(), im);

			ImagePlus imagePlus = new Opener().openTiff(im.getPath(), "");
			BufferedImage bufferedImage = imagePlus.getBufferedImage();
			Image temp = SwingFXUtils.toFXImage(bufferedImage, null);
			pathTab[i] = im.getPath();
			this.imageMap.put(pathTab[i], im);
			imageTab[i] = temp;
			nameTab[i] = im.getProperties().get("FILENAME");
			i++;

		}
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

	}

	/**
	 * Add image to the current project
	 * @param path
	 */
	public void addImage(String path, double height, double width) {
		Facade.addImage(path, height, width, this.currentFolder);
	}


	/**
	 * Save a copy of the current project
	 */
	@FXML
	void saveAsProject(ActionEvent event) {
		this.saveAsProject();
	}

	public void saveAsProject()
	{
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Open File");
		File file = chooser.showSaveDialog(new Stage());
		if (file == null)
			this.writeConsole("You cancelled the choice", "Project");
		else
		{
			Facade.saveAsProject(file.getAbsolutePath() + ".project");

		}
	}



	public void view1()
	{
		Image[] images = this.imageTab;

		if(images.length != 0)
		{
			this.currentView = 1;

			GridPane grid = new GridPane();

			ImageView im1 = new ImageView();


			double width = this.table.getWidth() - 120;
			double height = this.table.getHeight() - 25;

			final int y = this.page;



			//------IMAGE-----//
			im1.setOnMouseClicked(e -> imageEditor(pathTab[y], im1, e, grid));
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
			this.imageEditor(pathTab[y], im1, null, pane);
			this.table.setCenter(grid);
			grid.add(pane, 0, 0);

		}
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
			c.setCenterX(originX + (Facade.getX_ratio(landmark, im)*width));
			c.setCenterY(originY + (height - Facade.getY_ratio(landmark, im)*height));
			c.setRadius(3.0);
			c.setFill(Color.RED);
			pane.getChildren().add(y+1, c);
			y++;
		}



	}


	public void view(int nbImg)
	{
		Image[] images = this.imageTab;
		Folder[] folder = this.folderTab;

		GridPane grid = new GridPane();

		if(images.length != 0 || folder.length != 0)
		{
			int sizeFolder = 0;

			if(folderTab != null)
			{
				sizeFolder = this.folderTab.length;

			}


			//this.currentView = (int) Math.pow(nbImg, 2);


			double width = (this.table.getWidth() - 120) / nbImg;
			double height = (this.table.getHeight() - 40) / nbImg;



			int marge = this.page*nbImg;

			for(int i = marge; i<marge+Math.pow(nbImg, 2); i++)
			{
				Pane pane = new Pane();
				if(sizeFolder > i)
				{
					ImageView im1 = new ImageView();

					final int y = i;
					pane.setOnMouseClicked(e -> clickFolder(folder[y], e, pane));
					im1.setPreserveRatio(true);
					Image folderImage = new Image("open-folder-outline.png");
					im1.setImage(folderImage);
					Label l1 = new Label(folder[i].getName(), im1);
					l1.setContentDisplay(ContentDisplay.TOP);
					double ratioImg = folderImage.getHeight()/folderImage.getWidth();
					if(ratioImg > height/width)
					{
						im1.setFitHeight(height-50);
						im1.setX((width - im1.getFitHeight()/ratioImg) / 2);
						l1.setLayoutX((width - im1.getFitHeight()/ratioImg) / 2);
						im1.setY((height - im1.getFitHeight()) / 2);
						l1.setLayoutY((height - im1.getFitHeight()) / 2);
					}
					else
					{
						im1.setFitWidth(width-50);
						im1.setX((width - im1.getFitWidth()) / 2);
						l1.setLayoutX((width - im1.getFitWidth()) / 2);
						im1.setY((height - im1.getFitWidth()*ratioImg) / 2);
						l1.setLayoutY((height - im1.getFitWidth()*ratioImg) / 2);
					}

					l1.setStyle("-fx-text-fill: WHITE;");
					pane.setPrefHeight(height);
					pane.setPrefWidth(width);
					pane.getChildren().add(0, im1);
					pane.getChildren().add(1, l1);
				}
				else if(images.length + sizeFolder > i)
				{
					ImageView im1 = new ImageView();

					final int y = i;
					final int s = sizeFolder;
					im1.setOnMouseClicked(e -> imageEditor(pathTab[y - s], im1, e, pane));
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

					this.imageViewToPath.put(im1, pathTab[y - s]);

					this.displayLandmark(pane, im1, ratioImg, height/width, i-s);
				}
				grid.add(pane, i/nbImg, i%nbImg);

			}


		}

		this.table.setCenter(grid);
	}

	private void clickFolder(Folder folder, MouseEvent e, Pane pane) {
		this.activeFolder = folder;
		contextMenuFolder.show(pane, e.getScreenX(), e.getScreenY());
	}

	public void imageEditor(String path, ImageView imageView, MouseEvent e, Pane pane)
	{
		if (e == null)
		{
			this.activeFolder = null;
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
		else if (e.getButton() == MouseButton.SECONDARY) {
			this.activeFolder = null;
			if(!selected.contains(imageView))
			{
				selected.add(imageView);
			}

			contextMenuImage.show(pane, e.getScreenX(), e.getScreenY());
		}
		else
		{
			this.activeFolder = null;
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
		GridPane grid = new GridPane();
		int i = 1;

		if(Facade.hasLandmarks(image))
		{



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




		}
		this.propertiesPane.getChildren().add(0, grid);

		this.landmarksPane.getChildren().add(0, grid);
	}




	private void landmarkAdd() {

		ImageView imV = this.selected.get(this.selected.size() - 1);
		String path = this.imageViewToPath.get(imV);
		ImageWing imW = this.pathToImageWing.get(path);
		new Cadre2(new File(path), imW);


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



	public void newFolder() {
		TextInputDialog dialog = new TextInputDialog("UNDEFINED FOLDER");
		dialog.setTitle("Image browser");
		dialog.setHeaderText("Create a new folder.");
		dialog.setContentText("Please enter folder name:");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			Facade.addFolder(result.get(), this.currentFolder);
			writeConsole("1 folder added to the project", "ImageBrowser");
			this.refresh();
		}
	}

	public void changeFolder()
	{

		this.currentFolder = this.activeFolder;
		this.refresh();
		this.backFolder.setVisible(true);
	}



	public void initialize() {

		Facade.activeView = this;

		if(Facade.currentProject != null)
		{
			this.initImage(this.currentFolder);
			writeConsole("Opening of the project: " + Facade.currentProject.name, "Project");
		}

		final MenuItem item1 = new MenuItem("New collection");
		item1.setOnAction(e -> newFolder());

		final MenuItem item2 = new MenuItem("Open collection");
		item2.setOnAction(e -> changeFolder());

		final MenuItem item3 = new MenuItem("Rename collection");
		item3.setOnAction(e -> renameFolder());

		final MenuItem item4 = new MenuItem("Delete collection");
		item4.setOnAction(e -> deleteFolder());

		final MenuItem itemImage1 = new MenuItem("New collection");
		itemImage1.setOnAction(e -> newFolder());

		this.itemImage2 = getMoveTo();

		final MenuItem itemImage3 = new MenuItem("Landmark editor");
		itemImage3.setOnAction(e -> landmarkAdd());

		final MenuItem itemImage4 = new MenuItem("Delete image");
		itemImage4.setOnAction(e -> deleteImages());

		contextMenuFolder.getItems().clear();
		contextMenuFolder.getItems().addAll(item1, item2, item3, item4);
		contextMenuImage.getItems().clear();
		contextMenuImage.getItems().addAll(itemImage1, itemImage2, itemImage3, itemImage4);

	}

	private Menu getMoveTo(){
		Menu a = new Menu("Move image to...");

		final Menu folder = new Menu("ROOT");
		folder.setOnAction(e -> moveImageTo(null));
		a.getItems().add(folder);

		Iterator<Folder> it = Facade.currentProject.getFolders().iterator();
		while(it.hasNext())
		{
			Folder n = it.next();
			if(n.getFolders() == null)
			{
				final MenuItem folder1 = new MenuItem(n.getName());
				folder1.setOnAction(e -> moveImageTo(n));
				folder.getItems().add(folder1);
			}
			else
			{
				final Menu folder1 = this.getMenu(n);
				folder1.setOnAction(e -> moveImageTo(n));
				folder.getItems().add(folder1);
			}


		}
		return a;
	}

	private Menu getMenu(Folder folder) {

		final Menu itemImage2 = new Menu(folder.getName());
		Iterator<Folder> it = folder.getFolders().iterator();
		while(it.hasNext())
		{
			Folder n = it.next();
			if(n.getFolders() == null)
			{
				final MenuItem folder1 = new MenuItem(n.getName());
				folder1.setOnAction(e -> moveImageTo(n));
				itemImage2.getItems().add(folder1);
			}
			else
			{
				final Menu folder1 = this.getMenu(n);
				folder1.setOnAction(e -> moveImageTo(n));
				itemImage2.getItems().add(folder1);
			}


		}
		return itemImage2;
	}

	private void moveImageTo(Folder n) {

		Folder fold1 = n;
		Iterator<ImageView> it = selected.iterator();
		while(it.hasNext())
		{
			ImageView imV = it.next();
			ImageWing imW = this.pathToImageWing.get(this.imageViewToPath.get(imV));
			Facade.addImage(imW, fold1);
			Facade.deleteImage(imW, currentFolder);
			if(fold1 == null)
			{
				writeConsole(imW.getPath() + " is now in the root collection.", "ImageBrowser");
			}
			else
			{
				writeConsole(imW.getPath() + " is now in the " + fold1.getName() + " collection.", "ImageBrowser");
			}

		}
		this.refresh();
	}


	void setActiveFolder(Folder f)
	{
		this.activeFolder = f;
	}

	private void renameFolder() {
		String old = this.activeFolder.getName();
		TextInputDialog dialog = new TextInputDialog(old);
		dialog.setTitle("Image browser");
		dialog.setHeaderText("Change collection name");
		dialog.setContentText("Please enter collection name:");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			this.activeFolder.setName(result.get());
			writeConsole("The collection -"+ old +"-e has been rename", "ImageBrowser");
			this.initImage(this.currentFolder);
			this.reloadView();
		}
	}

	private void deleteFolder() {

		Facade.deleteFolder(this.currentFolder, this.activeFolder);
		this.refresh();
	}


	/* ------- START EXTERNAL FUNCTION ------- */
	@FXML
	void binaryPP(ActionEvent event) {
		this.writeConsole("Binary is executing...", "Image Preprocessing");
		ArrayList<String> listPath = new ArrayList<String>();
		Iterator<ImageView> it = this.selected.iterator();
		while(it.hasNext())
		{
			ImageView imV = it.next();
			String path = this.imageViewToPath.get(imV);
			listPath.add(path);
		}
		Facade.listPath = listPath;
		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("UIBinary.fxml"));
			root = loader.load();
			Scene scene = new Scene(root);			
			Stage stage = new Stage();
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();

			ControlBinary myController = loader.getController();



		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	void rgb2PP(ActionEvent event) {
		this.writeConsole("RGB2 Grey is executing...", "Image Preprocessing");
		ArrayList<String> listPath = new ArrayList<String>();
		Iterator<ImageView> it = this.selected.iterator();
		while(it.hasNext())
		{
			ImageView imV = it.next();
			String path = this.imageViewToPath.get(imV);
			listPath.add(path);
		}
		Facade.listPath = listPath;
		Facade.rgb2();
	}

	@FXML
	void skeletonPP(ActionEvent event) {
		this.writeConsole("Skeleton is executing...", "Image Preprocessing");
		ArrayList<String> listPath = new ArrayList<String>();
		Iterator<ImageView> it = this.selected.iterator();
		while(it.hasNext())
		{
			ImageView imV = it.next();
			String path = this.imageViewToPath.get(imV);
			listPath.add(path);
		}
		Facade.listPath = listPath;
		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("UISkeleton.fxml"));
			root = loader.load();
			Scene scene = new Scene(root);			
			Stage stage = new Stage();
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();

			ControlSkeleton myController = loader.getController();



		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	@FXML
	void crossPointDetection(ActionEvent event) {
		this.writeConsole("Cross point detection is executing...", "Cross point detection");
		ArrayList<String> listPath = new ArrayList<String>();
		Iterator<ImageView> it = this.selected.iterator();
		while(it.hasNext())
		{
			ImageView imV = it.next();
			String path = this.imageViewToPath.get(imV);
			listPath.add(path);
		}
		
		Facade.listPath = listPath;
		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("UICrossPointDetection.fxml"));
			root = loader.load();
			Scene scene = new Scene(root);			
			Stage stage = new Stage();
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();

			ControlCrossPointDetection myController = loader.getController();
			myController.dataLD(listPath, pathToImageWing);



		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	@FXML
	void dotAndNoise(ActionEvent event) {
		this.writeConsole("Dot & noise remover is executing...", "Image Preprocessing");
		ArrayList<String> listPath = new ArrayList<String>();
		Iterator<ImageView> it = this.selected.iterator();
		while(it.hasNext())
		{
			ImageView imV = it.next();
			String path = this.imageViewToPath.get(imV);
			listPath.add(path);
		}
		Facade.listPath = listPath;
		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("UIDotAndNoise.fxml"));
			root = loader.load();
			Scene scene = new Scene(root);			
			Stage stage = new Stage();
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();

			ControlDotAndNoise myController = loader.getController();



		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	@FXML
	void landmarkPrediction(ActionEvent event) {
		this.writeConsole("Landmark prediction is executing...", "LandmarkPrediction");
		ArrayList<String> listPath = new ArrayList<String>();
		Iterator<ImageView> it = this.selected.iterator();
		while(it.hasNext())
		{
			ImageView imV = it.next();
			String path = this.imageViewToPath.get(imV);
			listPath.add(path);
		}
		
		Facade.listPath = listPath;
		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("UILandmarkDetection.fxml"));
			root = loader.load();
			Scene scene = new Scene(root);			
			Stage stage = new Stage();
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();

			ControlLandmarkDetection myController = loader.getController();
			myController.dataLD(listPath, pathToImageWing);



		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void randomForest(ActionEvent event) {
		
		this.writeConsole("Random Forest is executing...", "Machine learning");
		ArrayList<String> listPath = new ArrayList<String>();
		Iterator<ImageView> it = this.selected.iterator();
		while(it.hasNext())
		{
			ImageView imV = it.next();
			String path = this.imageViewToPath.get(imV);
			listPath.add(path);
		}
		Facade.listPath = listPath;
		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("UIRandomForest.fxml"));
			root = loader.load();
			Scene scene = new Scene(root);			
			Stage stage = new Stage();
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();

			ControlRandomForest myController = loader.getController();



		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	@FXML
	void SVM(ActionEvent event) {
		
		this.writeConsole("SVM is executing...", "Machine learning");
		ArrayList<String> listPath = new ArrayList<String>();
		Iterator<ImageView> it = this.selected.iterator();
		while(it.hasNext())
		{
			ImageView imV = it.next();
			String path = this.imageViewToPath.get(imV);
			listPath.add(path);
		}
		Facade.listPath = listPath;
		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("UISVM.fxml"));
			root = loader.load();
			Scene scene = new Scene(root);			
			Stage stage = new Stage();
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();

			ControlSVM myController = loader.getController();



		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/* ------- END EXTERNAL FUNCTION ------- */


}