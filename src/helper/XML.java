package helper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import businesslogic.Folder;
import businesslogic.ImageWing;
import businesslogic.Landmark;
import businesslogic.Project;

public class XML {

	public static boolean saveProject(){
		return true;
	}

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
					Folder f = XML.folderXML(nodesProject.item(i));
					folderList.add(f);
				}
				else 
				{
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

	private static Folder folderXML(Node nodesProject) {
		
		NamedNodeMap attFolder = nodesProject.getAttributes();

		String folderName = attFolder.item(0).getNodeValue();

		NodeList nodesFolder = nodesProject.getChildNodes();
		
		ArrayList<Folder> folderList = new ArrayList<Folder>();
		ArrayList<ImageWing> imageList = new ArrayList<ImageWing>();
		for(int i = 0; i < nodesFolder.getLength(); i++)
		{
			if(nodesFolder.item(i).getNodeName() == "folder")
			{
				Folder f = XML.folderXML(nodesFolder.item(i));
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

}
