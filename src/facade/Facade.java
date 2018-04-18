package facade;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.photo.Photo;

import application.AfficheurFlux;
import application.ControlDashboard;
import application.LandmarkPrediction;
import businesslogic.Folder;
import businesslogic.ImageProcessing;
import businesslogic.ImageWing;
import businesslogic.Landmark;
import businesslogic.Project;
import helper.MetadataExtractor;
import helper.XML;
import helper.landmarkFile;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * <b>This class is a facade</b>
 * 
 * <p>
 * It is a bridge between the UI (user interface) and the BL (business logic).
 * All methods are static and there is one field, the current project. When the
 * UI needs to interact with the BL, it has to use the facade.
 *
 */
public class Facade {

	/**
	 * It is the current project.
	 * 
	 * @see Project
	 */
	public static Project currentProject = null;

	public static ControlDashboard activeView = null;

	public static ArrayList<Project> undo = new ArrayList<Project>();

	public static ArrayList<Project> redo = new ArrayList<Project>();

	public static ArrayList<String> listPath = new ArrayList<String>();

	public static ImageProcessing myImgProc = new ImageProcessing();

	/**
	 * Create a new empty project. Just the project's path is available.
	 * 
	 * @param path
	 *            A String with the project path
	 * 
	 * @see Project
	 */
	public static void newProject(String path) {
		// String separator = System.getProperty("file.separator");
		String[] tab = path.split("\\\\");
		String name = tab[tab.length - 1];
		Project p = new Project(name);
		p.setPathProject(path);
		Facade.currentProject = p;
		// Facade.saveProject();
	}

	/**
	 * Add a new Image to the current folder. The image is given in parameter.
	 * 
	 * @param path
	 *            A String with the image path
	 * @param height
	 *            A Double equals to the height
	 * @param width
	 *            A Double equals to the width
	 * @param fold
	 *            The current folder, if the current folder is the root then fold =
	 *            null
	 */
	public static void addImage(String path, double height, double width, Folder fold) {

		ImageWing image = new ImageWing(path);
		ArrayList<Landmark> tmpListLandmark = new ArrayList<Landmark>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(path + ".csv"));
			String line;
			while ((line = br.readLine()) != null) {
				String[] pos = line.split(",");
				if (!pos[0].trim().toLowerCase().equals("x")) {
					int posX = Integer.parseInt(pos[0]);
					int posY = Integer.parseInt(pos[1]);
					Landmark tmpLandmark = new Landmark(posX, posY, true);
					tmpListLandmark.add(tmpLandmark);
				}
			}
			br.close();
			image.setLandmarks(tmpListLandmark);
		} catch (IOException ioe) {

		}

		image.getProperties().put("HEIGHT", Double.toString(height));
		image.getProperties().put("WIDTH", Double.toString(width));
		image.getProperties().put("ORIGINAL", "TRUE");
		if (fold == null) {
			Facade.currentProject.addImage(image);
		} else {
			fold.addImage(image);
		}
	}

	/**
	 * Add an existing image to the current folder. The image is given in parameter.
	 * 
	 * @param imW
	 *            An ImageWing
	 * @param fold1
	 *            The current folder, if the current folder is the root then fold =
	 *            null
	 */
	public static void addImage(ImageWing imW, Folder fold1) {
		ImageWing image = imW;
		if (fold1 == null) {
			Facade.currentProject.addImage(image);
		} else {
			fold1.addImage(image);
		}

	}

	/**
	 * Remove image to the current folder.
	 * 
	 * @param image
	 *            An ImageWing
	 * @param fold
	 *            The current folder, if the current folder is the root then fold =
	 *            null
	 */
	public static void deleteImage(ImageWing image, Folder fold) {
		Facade.undo.add(currentProject.clonage());
		if (fold == null) {
			Facade.currentProject.deleteImage(image);
		} else {
			fold.deleteImage(image);
		}
	}

	/**
	 * Save the current Project in the project's backup file
	 */
	public static void saveProject() {
		XML.saveProject(Facade.currentProject);
		Facade.activeView.writeConsole("Save project: Success", "Project");
	}

	/**
	 * Save a copy of the current project
	 * 
	 * @param string
	 */
	public static void saveAsProject(String path) {
		Project p = Facade.currentProject;
		Facade.newProject(path);
		Facade.currentProject.setFolders(p.getFolders());
		Facade.currentProject.setImages(p.getImages());
		Facade.saveProject();
	}

	/**
	 * Load an existing project, it becomes the current project
	 * 
	 * @param file
	 *            The project's backup file
	 * 
	 * @return true if the project's backup file is OK
	 */
	public static boolean loadProject(File file) {

		Project p = XML.readProject(file);
		if (p != null) {
			Facade.currentProject = p;
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Return the list of the latest open projects
	 * 
	 * @return ArrayList of Project
	 * 
	 * @see Project
	 */
	public static ArrayList<Project> getHistProject() {
		return XML.readHist();
	}

	/**
	 * Return images include in the current project.
	 * 
	 * @return a ArrayList of ImageWing
	 * 
	 * @see ImageWing
	 */
	public static ArrayList<ImageWing> getImages() {
		return Facade.currentProject.getImages();
	}

	/**
	 * Return image landmarks of the image passed as a parameter
	 * 
	 * @param im
	 *            the ImageWing
	 * 
	 * @return an ArrayList of Landmark
	 * 
	 * @see ImageWing
	 * @see Landmark
	 */
	public static ArrayList<Landmark> getAllLandmark(ImageWing im) {
		return im.getLandmarks();
	}

	/**
	 * Return if image has properties or not
	 * 
	 * @param image
	 *            the ImageWing
	 * 
	 * @return boolean, true if the image as properties
	 * 
	 * @see ImageWing
	 */
	public static boolean hasProperties(ImageWing image) {
		return !image.getProperties().isEmpty();

	}

	/**
	 * Set the value of the property
	 * 
	 * @param image
	 *            the ImageWing
	 * @param key
	 *            the name of the property
	 * @param value
	 *            the new value of the property
	 * 
	 * 
	 * @see ImageWing
	 */
	public static void setProperties(ImageWing image, String key, String value) {
		Facade.undo.add(currentProject.clonage());
		image.getProperties().replace(key, value);

	}

	/**
	 * Add a property to image as passed in parameter
	 * 
	 * @param image
	 *            the ImageWing
	 * @param key
	 *            the name of the new property
	 * @param value
	 *            the value of the new property
	 * 
	 * 
	 * @see ImageWing
	 */
	public static void addProperties(ImageWing image, String key, String value) {
		Facade.undo.add(currentProject.clonage());
		image.getProperties().put(key, value);

	}

	/**
	 * Return if image has landmarks or not
	 * 
	 * @param image
	 *            the ImageWing
	 * 
	 * @return boolean, true if the image as landmarks
	 * 
	 * @see ImageWing
	 */
	public static boolean hasLandmarks(ImageWing image) {
		return !image.getLandmarks().isEmpty();
	}

	/**
	 * Return the X value in percent of the landmark passed in parameter
	 * 
	 * @param landmark
	 *            the landmark
	 * @param im
	 *            the ImageWing
	 * 
	 * @return double with the X value in percent
	 * 
	 * @see ImageWing
	 * @see Landmark
	 */
	public static double getX_ratio(Landmark landmark, ImageWing im) {
		double a = landmark.getPosX();
		double b = Double.parseDouble(im.getProperties().get("WIDTH"));

		return a / b;
	}

	/**
	 * Return the Y value in percent of the landmark passed in parameter
	 * 
	 * @param landmark
	 *            the landmark
	 * @param im
	 *            the ImageWing
	 * 
	 * @return double with the Y value in percent
	 * 
	 * @see ImageWing
	 * @see Landmark
	 */
	public static double getY_ratio(Landmark landmark, ImageWing im) {
		double a = landmark.getPosY();
		double b = Double.parseDouble(im.getProperties().get("HEIGHT"));

		return a / b;
	}

	public static boolean getIsLandmark(Landmark landmark, ImageWing im) {
		boolean isLandMark = landmark.getIsLandmark();
		return isLandMark;
	}

	public static void clearHistoric() {
	}

	/**
	 * Return the metadata of the file passed in parameter
	 * 
	 * @param file
	 *            the file
	 * 
	 * @return HashMap with metadata
	 * 
	 */
	public static HashMap<String, String> metadataExtractor(File file) {
		return MetadataExtractor.metadataExtractor(file);
	}

	/**
	 * Add a new folder to folder as passed in parameter
	 * 
	 * @param string
	 *            name of the folder
	 * @param fold
	 *            the the parent folder of the new folder
	 * 
	 * 
	 * @see Folder
	 */
	public static void addFolder(String string, Folder fold) {
		Facade.undo.add(currentProject.clonage());
		if (fold == null) {
			Folder folder = new Folder(string, null);
			Facade.currentProject.getFolders().add(folder);
		} else {
			Folder folder = new Folder(string, fold);
			fold.addFolder(folder);
		}
	}

	/**
	 * Remove the folder as passed in parameter to the current one
	 * 
	 * @param current
	 *            the current folder
	 * @param folder
	 *            the deleted folder
	 * 
	 * 
	 * @see Folder
	 */
	public static void deleteFolder(Folder current, Folder folder) {
		Facade.undo.add(currentProject.clonage());
		if (current == null) {
			Facade.currentProject.getFolders().remove(folder);
		} else {
			current.deleteFolder(folder);
		}
	}

	/**
	 * Remove a landmark to image as passed in parameter
	 * 
	 * @param im
	 *            the ImageWing who is concerned by the deleted landmark
	 * @param land
	 *            the deleted landmark
	 * 
	 * 
	 * @see ImageWing
	 * @see Landmark
	 */
	public static void deleteLandmark(ImageWing im, Landmark land) {
		Facade.undo.add(currentProject.clonage());
		im.deleteLandmark(land);
		landmarkFile.saveImage(im);
	}

	/**
	 * Set the landmark to image as passed in parameter
	 * 
	 * @param im
	 *            the ImageWing who is concerned by the edited landmark
	 * 
	 * 
	 * @see ImageWing
	 * @see Landmark
	 */
	public static void editLandmark(ImageWing im) {

		landmarkFile.saveImage(im);
	}

	public static void binaryPP(String threshold, String filter, String thresholdType) {
		String separator = System.getProperty("file.separator");
		String originalPath = new java.io.File("").getAbsolutePath();
		String pathAPI = originalPath + separator + "binary.exe";

		for (int i = 0; i < listPath.size(); i++) {
			ProcessBuilder pb = new ProcessBuilder(pathAPI, listPath.get(i), threshold, filter, thresholdType);
			Process process;
			try {
				process = pb.start();
				BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line = null;

				while ((line = stdInput.readLine()) != null) {
					Facade.activeView.writeConsole(line, "Binary");
				}
			} catch (IOException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Error");
				alert.setHeaderText("function not available");
				alert.setContentText(e.getMessage());
				alert.showAndWait();
				e.printStackTrace();
			}
		}
		Facade.activeView.refresh();

	}

	public static void skeletonPP(String length) {
		String separator = System.getProperty("file.separator");
		String originalPath = new java.io.File("").getAbsolutePath();
		String pathAPI = originalPath + separator + "skeleton.exe";

		for (int i = 0; i < listPath.size(); i++) {

			ProcessBuilder pb = new ProcessBuilder(pathAPI, listPath.get(i), length);
			Process process;
			try {
				process = pb.start();
				BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line = null;

				while ((line = stdInput.readLine()) != null) {
					Facade.activeView.writeConsole(line, "Skeleton");
				}
			} catch (IOException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Error");
				alert.setHeaderText("function not available");
				alert.setContentText(e.getMessage());
				alert.showAndWait();
				e.printStackTrace();
			}
		}
		Facade.activeView.refresh();
	}

	public static boolean isGrayScale(BufferedImage image) {
		// Test the type
		if (image.getType() == BufferedImage.TYPE_BYTE_GRAY)
			return true;
		if (image.getType() == BufferedImage.TYPE_USHORT_GRAY)
			return true;
		// Test the number of channels / bands
		if (image.getRaster().getNumBands() == 1)
			return true; // Single channel => gray scale

		// Multi-channels image; then you have to test the color for each pixel.
		for (int y = 0; y < image.getHeight(); y++)
			for (int x = 0; x < image.getWidth(); x++)
				for (int c = 1; c < image.getRaster().getNumBands(); c++)
					if (image.getRaster().getSample(x, y, c - 1) != image.getRaster().getSample(x, y, c))
						return false;

		return true;
	}

	public static Mat gray2bin(Mat in) {
		Mat out = new Mat();
		Imgproc.adaptiveThreshold(in, out, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, 11, 2);
		return out;
	}

	public static Mat rgb2gray(BufferedImage image) {
		try {
			Mat mat1 = img2Mat(image);
			Imgproc.cvtColor(mat1, mat1, Imgproc.COLOR_RGB2GRAY);
			System.out.println(mat1.empty());
			return mat1;

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return null;
	}

	public static Mat findSkeleton(ImageWing im) {
		BufferedImage img;
		try {
			img = ImageIO.read(new File(im.path));
			byte[] data = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
			Mat in = new Mat(img.getHeight(), img.getWidth(), CvType.CV_8UC3);
			in.put(0, 0, data);
			Mat matOfImg = new Mat(img.getHeight(), img.getWidth(), CvType.CV_8UC1);
			Imgproc.cvtColor(in, matOfImg, Imgproc.COLOR_RGB2GRAY);
			byte[] data1 = new byte[matOfImg.rows() * matOfImg.cols() * (int) (matOfImg.elemSize())];
			matOfImg.get(0, 0, data1);

			BufferedImage image1 = new BufferedImage(matOfImg.cols(), matOfImg.rows(), BufferedImage.TYPE_BYTE_GRAY);
			image1.getRaster().setDataElements(0, 0, matOfImg.cols(), matOfImg.rows(), data1);
			Imgproc.GaussianBlur(matOfImg, matOfImg, new Size(3, 3), 1);
			Highgui.imwrite("img_gray.tif", matOfImg);

			Photo.fastNlMeansDenoising(matOfImg, matOfImg, 10, 7, 21);
			Mat imgBin = new Mat();
			Imgproc.adaptiveThreshold(matOfImg, imgBin, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV,
					11, 2);
			Highgui.imwrite("img_bin.tif", imgBin);

			Imgproc.dilate(imgBin, imgBin, Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, new Size(9, 9)));
			Mat skel = new Mat(imgBin.height(), imgBin.width(), CvType.CV_8UC1, Scalar.all(0));
			Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, new Size(3, 3));
			Mat image_inv = imgBin;
			Highgui.imwrite("Pre-Skeleton.tif", image_inv);

			boolean isFinished = false;
			while (!isFinished) {
				Mat eroded = new Mat();
				Mat temp = new Mat();
				Imgproc.erode(image_inv, eroded, kernel);
				Imgproc.dilate(eroded, temp, kernel);
				Core.subtract(image_inv, temp, temp);
				Core.bitwise_or(skel, temp, skel);
				eroded.copyTo(image_inv);
				if (Core.countNonZero(image_inv) == 0) {
					isFinished = true;
				}
			}
			Highgui.imwrite("test_landmark.tif", skel);
			// Mat kernel2 = Mat.ones(new Size(5, 5), CvType.CV_8UC1);
			// Imgproc.dilate(skel, skel, kernel2);
			// Imgproc.erode(skel, skel, kernel2);
			// Highgui.imwrite("test_landmark_find_holes.tif", skel);
			return skel;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static void landmarkDetection(ArrayList<String> listPath, HashMap<String, ImageWing> listImW,
			String features, String neighbor) {
		Facade.undo.add(currentProject.clonage());
		String separator = System.getProperty("file.separator");
		String originalPath = new java.io.File("").getAbsolutePath();
		String pathAPI = originalPath + separator + "landmarkPrediction.exe";
		Facade.activeView.writeConsole(pathAPI, "TEST");
		for (int i = 0; i < listPath.size(); i++) {

			ProcessBuilder pb = new ProcessBuilder(pathAPI, listPath.get(i), features, neighbor);
			Process process;
			try {
				process = pb.start();
				BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line = null;
				while ((line = stdInput.readLine()) != null) {
					Facade.activeView.writeConsole(line, "Image Preprocessing");
					String[] tab = line.split(" ");
					Boolean b;
					if (tab[2] == "true") {
						b = true;
					} else {
						b = false;
					}
					Landmark l = new Landmark(Float.parseFloat(tab[0]), Float.parseFloat(tab[1]), b);
					ImageWing imW = listImW.get(listPath.get(i));
					imW.addLandmark(l);
					landmarkFile.saveImage(imW);
				}
			} catch (IOException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Error");
				alert.setHeaderText("function not available");
				alert.setContentText(e.getMessage());
				alert.showAndWait();
				e.printStackTrace();
			}
		}
		Facade.activeView.refresh();

	}

	/**
	 * Add a new landmark to image as passed in parameter
	 * 
	 * @param im
	 *            ImageWing concerned by the edition
	 * 
	 * @param listLandark
	 *            ArrayList of landmarks
	 * 
	 * @see ImageWing
	 * @see Landmark
	 */
	public static void addLandmark(ImageWing im, ArrayList<Landmark> listLandmark) {
		landmarkFile.saveImage(im);
	}

	public static void undo() {
		if (undo.size() > 0) {
			Facade.redo.add(currentProject.clonage());
			Facade.currentProject = Facade.undo.get(undo.size() - 1);
			Facade.undo.remove(undo.size() - 1);
		}
	}

	public static void redo() {
		if (redo.size() > 0) {
			Facade.undo.add(currentProject.clonage());
			Facade.currentProject = Facade.redo.get(redo.size() - 1);
			Facade.redo.remove(redo.size() - 1);
		}
	}

	public static void randomForest(String ntree, String proximity) {

		String separator = System.getProperty("file.separator");
		String originalPath = new java.io.File("").getAbsolutePath();
		String pathAPI = originalPath + separator + "randomForest.exe";

		for (int i = 0; i < listPath.size(); i++) {

			ProcessBuilder pb = new ProcessBuilder(pathAPI, listPath.get(i), ntree, proximity);

			Process process;
			try {
				process = pb.start();
				BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line = null;

				while ((line = stdInput.readLine()) != null) {
					Facade.activeView.writeConsole(line, "RandomForest");
				}
			} catch (IOException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Error");
				alert.setHeaderText("function not available");
				alert.setContentText(e.getMessage());
				alert.showAndWait();
				e.printStackTrace();
			}
		}
		Facade.activeView.refresh();

	}

	public static void SVM(String kernel, String cost, String gamma, String cross) {

		String separator = System.getProperty("file.separator");
		String originalPath = new java.io.File("").getAbsolutePath();
		String pathAPI = originalPath + separator + "svm.exe";

		for (int i = 0; i < listPath.size(); i++) {

			ProcessBuilder pb = new ProcessBuilder(pathAPI, listPath.get(i), kernel, cost, gamma, cross);
			Process process;
			try {
				process = pb.start();
				BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line = null;

				while ((line = stdInput.readLine()) != null) {
					Facade.activeView.writeConsole(line, "SVM");
				}
			} catch (IOException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Error");
				alert.setHeaderText("function not available");
				alert.setContentText(e.getMessage());
				alert.showAndWait();
				e.printStackTrace();
			}
		}
		Facade.activeView.refresh();

	}

	public static void rgb2() {
		// String separator = System.getProperty("file.separator");
		// String originalPath = new java.io.File("").getAbsolutePath();
		// #Calling rgb2.exe: not working outside window
		// String pathAPI = originalPath + separator + "rgb2.exe";

		for (int i = 0; i < listPath.size(); i++) {
			// #Calling rgb2.exe: not working outside window
			//
			// ProcessBuilder pb = new ProcessBuilder(pathAPI, listPath.get(i));
			// Process process;
			// try {
			// process = pb.start();
			// BufferedReader stdInput = new BufferedReader(new
			// InputStreamReader(process.getInputStream()));
			// String line = null;
			//
			//
			// while ((line = stdInput.readLine()) != null) {
			// Facade.activeView.writeConsole(line, "RGB2");
			// }
			// } catch (IOException e) {
			// Alert alert = new Alert(AlertType.WARNING);
			// alert.setTitle("Error");
			// alert.setHeaderText("function not available");
			// alert.setContentText(e.getMessage());
			// alert.showAndWait();
			// e.printStackTrace();
			// }
			myImgProc.rgb2gray(listPath.get(i));
		}
		Facade.activeView.refresh();
	}

	public static void dotAndNoise(String dotSize) {
		String separator = System.getProperty("file.separator");
		String originalPath = new java.io.File("").getAbsolutePath();
		String pathAPI = originalPath + separator + "dotAndNoise.exe";

		for (int i = 0; i < listPath.size(); i++) {

			ProcessBuilder pb = new ProcessBuilder(pathAPI, listPath.get(i), dotSize);
			Process process;
			try {
				process = pb.start();
				BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line = null;

				while ((line = stdInput.readLine()) != null) {
					Facade.activeView.writeConsole(line, "Dot & noise remover");
				}
			} catch (IOException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Error");
				alert.setHeaderText("function not available");
				alert.setContentText(e.getMessage());
				alert.showAndWait();
				e.printStackTrace();
			}
		}
		Facade.activeView.refresh();
	}

	public static void crossPointDetection(ArrayList<String> listPath, HashMap<String, ImageWing> listImW,
			String windowSize, String neighbor) {
		Facade.undo.add(currentProject.clonage());
		String separator = System.getProperty("file.separator");
		String originalPath = new java.io.File("").getAbsolutePath();
		String pathAPI = originalPath + separator + "crossPointDetection.exe";

		for (int i = 0; i < listPath.size(); i++) {
			ProcessBuilder pb = new ProcessBuilder(pathAPI, listPath.get(i), neighbor, windowSize);
			Process process;
			try {
				process = pb.start();
				BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line = null;
				while ((line = stdInput.readLine()) != null) {
					Facade.activeView.writeConsole(line, "Cross Point Detection");
					String[] tab = line.split(" ");
					Boolean b;
					if (tab[2] == "true") {
						b = true;
					} else {
						b = false;
					}

					Landmark l = new Landmark(Float.parseFloat(tab[0]), Float.parseFloat(tab[1]), b);
					ImageWing imW = listImW.get(listPath.get(i));
					imW.addLandmark(l);
					landmarkFile.saveImage(imW);
				}
			} catch (IOException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Error");
				alert.setHeaderText("No image selected !");
				alert.setContentText(e.getMessage());
				alert.showAndWait();
				e.printStackTrace();
			}
		}
		Facade.activeView.refresh();
	}

	public static void saveOrignal(ImageWing imW, boolean b) throws IOException {
		String separator = System.getProperty("file.separator");
		String original = imW.getProperties().get("ORIGINAL");
		if ((original.compareTo("TRUE") == 0) || b) {
			String version = "original.tif";
			if (b) {
				Date d = new Date();
				SimpleDateFormat formatter;
				formatter = new SimpleDateFormat("yyyy_MM_dd_HH-mm");
				String date = formatter.format(d);
				version = date + ".version.tif";
			}

			File f = new File(imW.getPath());
			FileReader fr = new FileReader(f);
			String path = imW.getPath();
			File dir = new File(path + ".version");
			dir.mkdirs();
			File f2 = new File(path + ".version" + separator + version);
			FileWriter fw = new FileWriter(f2);
			int a;
			while ((a = fr.read()) != -1) {
				fw.write(a);
			}
			fw.close();
			fr.close();
		}
	}

	public static void lectureEXE(String[] commande) {

		System.out.println("Debut du programme");

		try {

			Process p = Runtime.getRuntime().exec(commande);

			AfficheurFlux fluxSortie = new AfficheurFlux(p.getInputStream());

			AfficheurFlux fluxErreur = new AfficheurFlux(p.getErrorStream());

			new Thread(fluxSortie).start();

			new Thread(fluxErreur).start();

			// p.waitFor();

		} catch (IOException e) {
			e.printStackTrace();
		} /*
			 * catch (InterruptedException e) { e.printStackTrace(); }
			 */

		// Facade.activeView.refresh();

		System.out.println("Fin du programme");
	}

	public static void landmarkPrediction(String[] commande) {
		System.out.println("Debut du programme");
		try {

			Process p = Runtime.getRuntime().exec(commande);

			LandmarkPrediction fluxSortie = new LandmarkPrediction(p.getInputStream());
			LandmarkPrediction fluxErreur = new LandmarkPrediction(p.getErrorStream());

			new Thread(fluxSortie).start();
			new Thread(fluxErreur).start();

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Fin du programme");
	}

	public static BufferedImage convert2Gray(Mat mat2, BufferedImage img) {
		try {
			Mat mat1 = mat2;
			Imgproc.cvtColor(mat2, mat1, Imgproc.COLOR_RGB2GRAY);
			byte[] data1 = new byte[mat1.rows() * mat1.cols() * (int) (mat1.elemSize())];
			mat1.get(0, 0, data1);
			BufferedImage image1 = Facade.matToBufferedImage(mat1, img);
			System.out.println(mat1.depth());
			return image1;

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return null;
	}

	public static BufferedImage convert2Bin(BufferedImage in) {
		Mat input = img2Mat(in);
		if (in.getType() == 1)
			convert2Gray(input, in);
		Mat output = new Mat();
		Imgproc.adaptiveThreshold(input, output, 200, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, 15, 1);
		BufferedImage out = Facade.matToBufferedImage(output, in);
		return out;
	}

	public static ArrayList<String> Harris(ImageWing im, int thresh) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		BufferedImage grayImage = null;
		try {
			grayImage = Facade.convert2Gray(Highgui.imread(im.path), ImageIO.read(new File(im.path)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<String> cornerList = new ArrayList<String>();

		Mat inputMat = img2Mat(grayImage);
		Mat Scene = new Mat(inputMat.rows(), inputMat.cols(), CvType.CV_8UC1);
		Scene.convertTo(inputMat, CvType.CV_8UC1);
		// This function implements the Harris Corner detection. The corners at
		// intensity > thresh
		// are drawn.
		Mat Harris_scene = new Mat(inputMat.rows(), inputMat.cols(), CvType.CV_8UC1);

		Mat harris_scene_norm = new Mat(), harris_scene_scaled = new Mat();
		int blockSize = 15;
		int apertureSize = 5;
		double k = 0.1;
		Imgproc.cornerHarris(Scene, Harris_scene, blockSize, apertureSize, k);
		Core.normalize(Harris_scene, harris_scene_norm, 0, 255, Core.NORM_MINMAX, CvType.CV_32FC1, new Mat());
		Core.convertScaleAbs(harris_scene_norm, harris_scene_scaled);

		for (int j = 0; j < harris_scene_norm.rows(); j++) {
			for (int i = 0; i < harris_scene_norm.cols(); i++) {
				if ((int) harris_scene_norm.get(j, i)[0] > thresh) {
					cornerList.add(i + " " + j);
				}
			}
		}
		return cornerList;
	}

	public static Mat img2Mat(BufferedImage in) {
		Mat out;
		out = new Mat(in.getHeight(), in.getWidth(), CvType.CV_8UC3);
		byte[] data = new byte[in.getWidth() * in.getHeight() * (int) out.elemSize()];
		int[] dataBuff = in.getRGB(0, 0, in.getWidth(), in.getHeight(), null, 0, in.getWidth());
		for (int i = 0; i < dataBuff.length; i++) {
			data[i * 3] = (byte) ((dataBuff[i]));
			data[i * 3 + 1] = (byte) ((dataBuff[i]));
			data[i * 3 + 2] = (byte) ((dataBuff[i]));
		}
		out.put(0, 0, data);
		return out;
	}

	public static BufferedImage matToBufferedImage(Mat matrix, BufferedImage mat2) {
		if (matrix != null) {
			int cols = matrix.cols();
			int rows = matrix.rows();
			int elemSize = (int) matrix.elemSize();
			byte[] data = new byte[cols * rows * elemSize];
			int type;
			matrix.get(0, 0, data);
			System.out.println("channels : " + img2Mat(mat2).channels());
			switch (matrix.channels()) {
			case 1:
				type = BufferedImage.TYPE_BYTE_GRAY;
				break;
			case 3:
				type = BufferedImage.TYPE_3BYTE_BGR;
				// bgr to rgb
				byte b;
				for (int i = 0; i < data.length; i = i + 3) {
					b = data[i];
					data[i] = data[i + 2];
					data[i + 2] = b;
				}
				break;
			default:
				return null;
			}

			// Reuse existing BufferedImage if possible
			if (mat2 == null || mat2.getWidth() != cols || mat2.getHeight() != rows || mat2.getType() != type) {
				mat2 = new BufferedImage(cols, rows, type);
			}
			mat2.getRaster().setDataElements(0, 0, cols, rows, data);
		} else { // mat was null
			mat2 = null;
		}
		return mat2;
	}

	public static boolean resizeIfNeeded(Mat img, int desiredWidth, int desiredHeight) {
		Size size = img.size();
		Size desiredSize = new Size(desiredWidth, desiredHeight);
		if (size.width != desiredWidth || size.height != desiredHeight) {
			Imgproc.resize(img, img, desiredSize);
			return true;
		}
		return false;
	}

}