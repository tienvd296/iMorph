package helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.*;

import businesslogic.Folder;
import businesslogic.ImageWing;
import businesslogic.Landmark;
import businesslogic.Project;

/**
 * <b>This class is an helper for using XML file</b>
 * 
 * <p>
 * There are only static methods for read and write an XML file.
 *
 */
public class XML {

	/**
	 * Create a new project XML file with all informations about the project.
	 * 
	 * @param p 
	 * 			The project to be saved
	 * 
	 * @return true if the save is ok
	 * 
	 * @see Project
	 */
	public static boolean saveProject(Project p){

		ArrayList<Folder> folders = p.getFolders();
		Iterator<Folder> f = folders.iterator();
		ArrayList<ImageWing> images = p.getImages();
		Iterator<ImageWing> im = images.iterator();
		String pathProject = p.getPathProject();


		org.jdom2.Element project = new org.jdom2.Element("project");
		org.jdom2.Document document = new org.jdom2.Document(project);

		Attribute projectName = new Attribute("name",p.getName());
		Date d = new Date();
		SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Attribute projectLastSave = new Attribute("lastsave",sm.format(d));
		project.setAttribute(projectName);
		project.setAttribute(projectLastSave);

		while(f.hasNext())
		{
			org.jdom2.Element folder = new org.jdom2.Element("folder");
			folder = writeFolder(folder, f.next());
			project.addContent(folder);
		}

		while(im.hasNext())
		{
			ImageWing im1 = im.next();
			org.jdom2.Element image = new org.jdom2.Element("image");
			Attribute pathImage = new Attribute("path",im1.getPath());
			image.setAttribute(pathImage);

			for (Map.Entry<String, String> entry : im1.getProperties().entrySet())
			{
				org.jdom2.Element property = new org.jdom2.Element("property");
				Attribute keyProperty = new Attribute("key",entry.getKey());
				property.setAttribute(keyProperty);
				Attribute valueProperty = new Attribute("value",entry.getValue());
				property.setAttribute(valueProperty);
				image.addContent(property);
			}

			ArrayList<Landmark> landmarks = im1.getLandmarks();
			Iterator<Landmark> land = landmarks.iterator();
			while(land.hasNext())
			{
				Landmark land1 = land.next();
				org.jdom2.Element landmark = new org.jdom2.Element("landmark");
				Attribute posXLandmark = new Attribute("posX", Float.toString(land1.getPosX()));
				landmark.setAttribute(posXLandmark);
				Attribute posYLandmark = new Attribute("posY", Float.toString(land1.getPosY()));
				landmark.setAttribute(posYLandmark);
				Attribute isLandmark = new Attribute("isLandmark", Boolean.toString(land1.getIsLandmark()));
				landmark.setAttribute(isLandmark);
				image.addContent(landmark);
			}

			project.addContent(image);
		}

		try
		{

			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			sortie.output(document, new FileOutputStream(pathProject));
			editLastSave(pathProject, sm.format(d));
			return true;
		}
		catch (java.io.IOException e){ return false; }
	}


	/**
	 * Recursively called function for save folder
	 * 
	 * @param folderParent 
	 * 			The parent folder
	 * @param folder2 
	 * 			The folder to be saved
	 * 
	 * @see Folder
	 */
	private static org.jdom2.Element writeFolder(org.jdom2.Element folderParent, Folder folder2)
	{
		ArrayList<Folder> folders = folder2.getFolders();
		Iterator<Folder> f = folders.iterator();
		ArrayList<ImageWing> images = folder2.getImages();
		Iterator<ImageWing> im = images.iterator();

		Attribute folderName = new Attribute("name",folder2.getName());
		folderParent.setAttribute(folderName);

		while(f.hasNext())
		{
			org.jdom2.Element folder = new org.jdom2.Element("folder");
			folder = writeFolder(folder, f.next());
			folderParent.addContent(folder);
		}

		while(im.hasNext())
		{
			ImageWing im1 = im.next();
			org.jdom2.Element image = new org.jdom2.Element("image");
			Attribute pathImage = new Attribute("path",im1.getPath());
			image.setAttribute(pathImage);

			for (Map.Entry<String, String> entry : im1.getProperties().entrySet())
			{
				org.jdom2.Element property = new org.jdom2.Element("property");
				Attribute keyProperty = new Attribute("key",entry.getKey());
				property.setAttribute(keyProperty);
				Attribute valueProperty = new Attribute("value",entry.getValue());
				property.setAttribute(valueProperty);
				image.addContent(property);
			}

			ArrayList<Landmark> landmarks = im1.getLandmarks();
			Iterator<Landmark> land = landmarks.iterator();
			while(land.hasNext())
			{
				Landmark land1 = land.next();
				org.jdom2.Element landmark = new org.jdom2.Element("landmark");
				Attribute posXLandmark = new Attribute("posX", Float.toString(land1.getPosX()));
				landmark.setAttribute(posXLandmark);
				Attribute posYLandmark = new Attribute("posY", Float.toString(land1.getPosY()));
				landmark.setAttribute(posYLandmark);
				Attribute isLandmark = new Attribute("isLandmark", Boolean.toString(land1.getIsLandmark()));
				landmark.setAttribute(isLandmark);
				image.addContent(landmark);
			}

			folderParent.addContent(image);
		}

		return folderParent;
	}


	/**
	 * Read an existing project XML file with all informations about the project.
	 * 
	 * @param file 
	 * 			The file to be read
	 * 
	 * @return An existing project
	 * 
	 * @see Project
	 */
	public static Project readProject(File file){

		SAXBuilder sxb = new SAXBuilder();

		org.jdom2.Element project = new org.jdom2.Element("project");
		org.jdom2.Document document = new org.jdom2.Document(project);

		String projectName = "";
		String projectLastSave = "";
		ArrayList<Folder> folders = new ArrayList<Folder>();
		ArrayList<ImageWing> images = new ArrayList<ImageWing>();

		try
		{
			document = sxb.build(file);
		}
		catch(Exception e){}

		project = document.getRootElement();

		projectName = project.getAttributeValue("name");
		projectLastSave = project.getAttributeValue("lastsave");

		List<org.jdom2.Element> listFolders = project.getChildren("folder");
		List<org.jdom2.Element> listImages = project.getChildren("image");

		Iterator<org.jdom2.Element> itF = listFolders.iterator();
		Iterator<org.jdom2.Element> itI = listImages.iterator();

		while(itF.hasNext())
		{
			org.jdom2.Element folder = (org.jdom2.Element)itF.next();
			Folder f = readFolder(folder, null);
			folders.add(f);
		}

		while(itI.hasNext())
		{
			org.jdom2.Element image = (org.jdom2.Element)itI.next();
			String imagePath = image.getAttributeValue("path");
			ArrayList<Landmark> lnds = new ArrayList<Landmark>();
			HashMap<String, String> ppts = new HashMap<String, String>();
			List<org.jdom2.Element> listLandmarks = image.getChildren("landmark");
			List<org.jdom2.Element> listProperties = image.getChildren("property");
			Iterator<org.jdom2.Element> itL = listLandmarks.iterator();
			Iterator<org.jdom2.Element> itP = listProperties.iterator();

			while(itL.hasNext())
			{
				org.jdom2.Element landmark = (org.jdom2.Element)itL.next();
				Float posX = Float.parseFloat(landmark.getAttributeValue("posX"));
				Float posY = Float.parseFloat(landmark.getAttributeValue("posY"));
				Boolean isLandmark = Boolean.parseBoolean(landmark.getAttributeValue("isLandmark"));
				Landmark ld = new Landmark(posX, posY, isLandmark);
				lnds.add(ld);

			}

			while(itP.hasNext())
			{
				org.jdom2.Element landmark = (org.jdom2.Element)itP.next();
				String key = landmark.getAttributeValue("key");
				String value = landmark.getAttributeValue("value");
				ppts.put(key, value);

			}
			ImageWing im1 = new ImageWing(imagePath, ppts, lnds);
			images.add(im1);
		}

		return new Project(projectName, projectLastSave, file.getAbsolutePath(), folders, images);

	}

	
	/**
	 * Recursively called function for read folder
	 * 
	 * @param folder2 
	 * 			The folder to be saved
	 * @param parent 
	 * 			The parent folder
	 * 
	 * @see Folder
	 */
	private static Folder readFolder(org.jdom2.Element folder2, Folder parent) 
	{
		Folder ret = new Folder(null, parent);
		String name = folder2.getAttributeValue("name");
		ArrayList<Folder> folders = new ArrayList<Folder>();
		ArrayList<ImageWing> images = new ArrayList<ImageWing>();

		List<org.jdom2.Element> listFolders = folder2.getChildren("folder");
		List<org.jdom2.Element> listImages = folder2.getChildren("image");

		Iterator<org.jdom2.Element> itF = listFolders.iterator();
		Iterator<org.jdom2.Element> itI = listImages.iterator();

		while(itF.hasNext())
		{
			org.jdom2.Element folder = (org.jdom2.Element)itF.next();
			Folder f = readFolder(folder, ret);
			folders.add(f);
		}

		while(itI.hasNext())
		{
			org.jdom2.Element image = (org.jdom2.Element)itI.next();
			String imagePath = image.getAttributeValue("path");
			ArrayList<Landmark> lnds = new ArrayList<Landmark>();
			HashMap<String, String> ppts = new HashMap<String, String>();
			List<org.jdom2.Element> listLandmarks = image.getChildren("landmark");
			List<org.jdom2.Element> listProperties = image.getChildren("property");
			Iterator<org.jdom2.Element> itL = listLandmarks.iterator();
			Iterator<org.jdom2.Element> itP = listProperties.iterator();

			while(itL.hasNext())
			{
				org.jdom2.Element landmark = (org.jdom2.Element)itL.next();
				Float posX = Float.parseFloat(landmark.getAttributeValue("posX"));
				Float posY = Float.parseFloat(landmark.getAttributeValue("posY"));
				Boolean isLandmark = Boolean.parseBoolean(landmark.getAttributeValue("isLandmark"));
				Landmark ld = new Landmark(posX, posY, isLandmark);
				lnds.add(ld);

			}

			while(itP.hasNext())
			{
				org.jdom2.Element landmark = (org.jdom2.Element)itP.next();
				String key = landmark.getAttributeValue("key");
				String value = landmark.getAttributeValue("value");
				ppts.put(key, value);

			}
			ImageWing im1 = new ImageWing(imagePath, ppts, lnds);
			images.add(im1);
		}
		ret.setName(name);
		ret.setFolders(folders);
		ret.setImages(images);
		return ret;


	}


	private static void editLastSave(String path, String dateSave){
		String separator = System.getProperty("file.separator");
		org.jdom2.Document document;
		org.jdom2.Element racine;
		SAXBuilder sxb = new SAXBuilder();
		Boolean stop = false;
		try {
			document = sxb.build(new File("lastSave"));
			racine = document.getRootElement();


			List<Element> listProject = racine.getChildren("project");
			Iterator<Element> i = listProject.iterator();
			while(i.hasNext() && !stop)
			{

				org.jdom2.Element courant = i.next();
				System.out.println(courant.getAttributeValue("path")+ " = "+path);
				if(courant.getAttributeValue("path").equals(path))
				{
					System.out.println("2");
					courant.getAttribute("dateSave").setValue(dateSave);
					stop = true;
				}
			}
			if(!stop)
			{
				org.jdom2.Element project = new org.jdom2.Element("project");
				Attribute pathAtt = new Attribute("path", path);
				project.setAttribute(pathAtt);
				Attribute dateAtt = new Attribute("dateSave", dateSave);
				project.setAttribute(dateAtt);
				racine.addContent(project);

			}





			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			sortie.output(document, new FileOutputStream("lastSave"));

		} catch (JDOMException | IOException e) {
			org.jdom2.Element listProject = new org.jdom2.Element("listProject");
			document = new org.jdom2.Document(listProject);
			org.jdom2.Element project = new org.jdom2.Element("project");
			Attribute pathAtt = new Attribute("path", path);
			project.setAttribute(pathAtt);
			Attribute dateAtt = new Attribute("dateSave", dateSave);
			project.setAttribute(dateAtt);
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			listProject.addContent(project);
			try {
				sortie.output(document, new FileOutputStream("lastSave"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}


	}

	public static ArrayList<Project> readHist()
	{
		ArrayList<Project> listP = new ArrayList<Project>();
		String separator = System.getProperty("file.separator");
		String originalPath = new java.io.File("").getAbsolutePath();
		String pathFile = originalPath + separator +  "lastSave";

		org.jdom2.Document document;
		org.jdom2.Element racine;
		SAXBuilder sxb = new SAXBuilder();

		try {
			document = sxb.build(new File(pathFile));

			racine = document.getRootElement();


			List<Element> listProject = racine.getChildren("project");
			Iterator<Element> i = listProject.iterator();
			while(i.hasNext())
			{
				org.jdom2.Element courant = (Element)i.next();
				String path = courant.getAttributeValue("path");
				System.out.println(path);
				String dateSave = courant.getAttributeValue("dateSave");

				String name = path;


				listP.add(new Project(name, path, dateSave));
			}
		} catch (JDOMException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listP;
	}

}
