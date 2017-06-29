package helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.jdom2.*;
import org.jdom2.output.*;

import businesslogic.Folder;
import businesslogic.ImageWing;
import businesslogic.Landmark;
import businesslogic.Project;

public class XML {


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
	       return true;
	    }
	    catch (java.io.IOException e){ return false; }
	}
	
	
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

	
	
	public static Project readProject(String projectPath){
		
		
	
	}
	
	

/* old method
	public static Project readProject(String projectPath){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			File fileXML = new File(projectPath);
			Document xml;

			xml = builder.parse(fileXML);
			Element projectXML = xml.getDocumentElement();

			NamedNodeMap attProject = projectXML.getAttributes();

			String projectName = attProject.item(0).getNodeValue();
			String projectLastSave = attProject.item(1).getNodeValue();

			NodeList nodesProject = projectXML.getChildNodes();

			ArrayList<Folder> folderList = new ArrayList<Folder>();
			ArrayList<ImageWing> imageList = new ArrayList<ImageWing>();
			for(int i = 0; i < nodesProject.getLength(); i++)
			{
				if(nodesProject.item(i).getNodeName() == "folder")
				{
					Folder f = XML.readFolder(nodesProject.item(i));
					folderList.add(f);
				}
				else 
				{
					System.out.println(nodesProject.getLength());
					NamedNodeMap attImage = nodesProject.item(i).getAttributes();
					String imagePath = attImage.item(0).getNodeValue();
					NodeList nodesImage = nodesProject.item(i).getChildNodes();
					ArrayList<Landmark> landmarkList = new ArrayList<Landmark>();
					HashMap<String, String> propertyList = new HashMap<String, String>();
					for(int y = 0; y < nodesImage.getLength(); y++)
					{
						if(nodesImage.item(y).getNodeName() == "property")
						{
							NamedNodeMap attProperty = nodesImage.item(y).getAttributes();
							propertyList.put(attProperty.item(0).getNodeValue(), attProperty.item(0).getNodeValue());
						}
						else 
						{
							NamedNodeMap attLandmark = nodesImage.item(y).getAttributes();
							Landmark l = new Landmark(Float.parseFloat(attLandmark.item(0).getNodeValue()), Float.parseFloat(attLandmark.item(1).getNodeValue()), Boolean.parseBoolean(attLandmark.item(2).getNodeValue()));
							landmarkList.add(l);
						}


					}
					ImageWing imWing = new ImageWing(imagePath, propertyList, landmarkList);
					imageList.add(imWing);
				}

			}
			Project p = new Project(projectName, projectLastSave, projectPath, folderList, imageList);
			return p;

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	private static Folder readFolder(Node nodesProject) {

		NamedNodeMap attFolder = nodesProject.getAttributes();

		String folderName = attFolder.item(0).getNodeValue();

		NodeList nodesFolder = nodesProject.getChildNodes();

		ArrayList<Folder> folderList = new ArrayList<Folder>();
		ArrayList<ImageWing> imageList = new ArrayList<ImageWing>();
		for(int i = 0; i < nodesFolder.getLength(); i++)
		{
			if(nodesFolder.item(i).getNodeName() == "folder")
			{
				Folder f = XML.readFolder(nodesFolder.item(i));
				folderList.add(f);
			}
			else 
			{
				NamedNodeMap attImage = nodesFolder.item(i).getAttributes();
				String imagePath = attImage.item(0).getNodeValue();
				NodeList nodesImage = nodesFolder.item(i).getChildNodes();
				ArrayList<Landmark> landmarkList = new ArrayList<Landmark>();
				HashMap<String, String> propertyList = new HashMap<String, String>();
				for(int y = 0; y < nodesImage.getLength(); y++)
				{
					if(nodesImage.item(y).getNodeName() == "property")
					{
						NamedNodeMap attProperty = nodesImage.item(y).getAttributes();
						propertyList.put(attProperty.item(0).getNodeValue(), attProperty.item(0).getNodeValue());
					}
					else 
					{
						NamedNodeMap attLandmark = nodesImage.item(y).getAttributes();
						Landmark l = new Landmark(Float.parseFloat(attLandmark.item(0).getNodeValue()), Float.parseFloat(attLandmark.item(1).getNodeValue()), Boolean.parseBoolean(attLandmark.item(2).getNodeValue()));
						landmarkList.add(l);
					}


				}
				ImageWing imWing = new ImageWing(imagePath, propertyList, landmarkList);
				imageList.add(imWing);
			}

		}
		Folder f = new Folder(folderName, folderList, imageList);
		return f;
	}
*/


}
