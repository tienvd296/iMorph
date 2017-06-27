package helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import businesslogic.*;

public class ProjectFile {
	
	public static Project reader(File file)
	{
		// Read objects
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			String content = reader.readLine();
			String[] skeleton = content.split("IMAGES;");
			
			String projectSection = skeleton[0];
			
			String[] tab = projectSection.split(";");
			String[] nameID = tab[0].split("=");
			String name = nameID[1];
			String[] ls = tab[1].split("=");
			String lastSave = ls[1];
			String path = file.getAbsolutePath().toString();
			ArrayList<ImageWing> images = new ArrayList<ImageWing>();
			if(skeleton.length>1)
			{
				images = ProjectFile.readImage(skeleton[1]);				
				
			}
			reader.close();
					
			return new Project(name, lastSave, path, images);
        
        
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.println("ERREUR0");
		return null;
	} catch (NullPointerException e){
		e.printStackTrace();
		System.out.println("ERREUR0");
		return null;
	}
	}

	private static ArrayList<ImageWing> readImage (String imageSection)
	{
		ArrayList<ImageWing> images = new ArrayList<ImageWing>();
		String[] tab = imageSection.split("IMAGE_PATH=");
		for(int i = 1; i < tab.length; i++)
		{
			String pathImage = "UN";
			Map<String, String> properties = new HashMap<String, String>();
			ArrayList<Landmark> landmarks = new ArrayList<Landmark>();
			
			String[] tab2 = tab[i].split(";");
			pathImage = tab2[0];
			
			int n = 1;
			
			while (!tab2[n].equals("LANDMARKS"))
			{
				System.out.println(tab2[n]);
				String[] prop = tab2[n].split("=");
				properties.put(prop[0], prop[1]);
				n++;
			}
			n++;
			
			while (n < tab2.length)
			{
				String tab4[] = tab2[n].split(" ");
				float posX = Float.parseFloat(tab4[0]);
				float posY = Float.parseFloat(tab4[1]);
				Boolean isLandmark = false;
				if (tab4[2] == "1")
				{
						isLandmark = true;
				}
				Landmark l = new Landmark(posX, posY, isLandmark);
				landmarks.add(l);
				n++;
			}
			ImageWing im = new ImageWing(pathImage, properties, landmarks);
			images.add(im);
		}
		
		return images;
		
	}

	public static void saveProject(Project p)
	{
		String separator = System.getProperty("file.separator");
		String originalPath = System.getProperty("user.dir");
		Date d = new Date();
		SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		String dataSave = "PROJECT_NAME=" + p.getName() + ";";
		p.setLastSave(sm.format(d));
		dataSave = dataSave + "LAST_EDIT=" + sm.format(d) + ";";
		dataSave = dataSave + "IMAGES;";
		if(!p.images.isEmpty())
		{
			Iterator<ImageWing> it = p.images.iterator();

			while(it.hasNext())
			{
				ImageWing image = it.next();
				dataSave = dataSave + "IMAGE_PATH=" + image.getPath() + ";";
				if(!image.properties.isEmpty())
				{
				for (Map.Entry<String, String> entry : image.properties.entrySet())
				{
				    dataSave = dataSave + entry.getKey() + "=" + entry.getValue() + ";";
				}
				}
				dataSave = dataSave + "LANDMARKS;";
				if(!image.getLandmarks().isEmpty())
				{
					Iterator<Landmark> it2 = image.getLandmarks().iterator();
					while(it2.hasNext())
					{
						Landmark landmark = it2.next();
						dataSave = dataSave + landmark.getPosX() + " " + landmark.getPosY() + " " + landmark.getIsLandmark() + ";"; 
					}
				}
			}
		}

		if(p.pathProject == "")
		{
			p.pathProject = "D:"+ separator +"Documents"+ separator + p.getName() + ".project";
		}
		
		BufferedWriter writer = null;
		BufferedWriter writer2 = null;
		try {
			//create a temporary file
			File logFile = new File(p.getPathProject());
			File lastSaveFile = new File(originalPath + separator + "assets" + separator + "lastProject.data");

			writer = new BufferedWriter(new FileWriter(logFile));
			writer2 = new BufferedWriter(new FileWriter(lastSaveFile, true));
			writer.write(dataSave);	
			writer2.append("\n" + p.getName() + "#" + p.getPathProject() + "#" + p.getLastSave());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// Close the writer regardless of what happens...
				writer.close();
				writer2.close();
			} catch (Exception e) {
			}
		}

	}
	
	public static ArrayList<Project> histProject()
	{
		String separator = System.getProperty("file.separator");
		String originalPath = System.getProperty("user.dir");
		String path = originalPath + separator + "assets" + separator + "lastProject.data";
		File file = new File(path);
		BufferedReader reader;
		ArrayList<Project> listProj = new ArrayList<Project>();

			
			try {
				reader = new BufferedReader(new FileReader(file));

			try {
				String content;
				reader.readLine();
				while((content = reader.readLine()) != null)
				{
					Project p1 = new Project(content.split("#")[0], content.split("#")[1], content.split("#")[2]);
					listProj.add(p1);
				}
				reader.close();
				return listProj;
				
			
			
			
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return listProj;
				
			}
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return listProj;
			}
			
			
		
	}

	public static void clearHistoric() {
		String separator = System.getProperty("file.separator");
		String originalPath = System.getProperty("user.dir");
		File lastSaveFile = new File(originalPath + separator + "assets" + separator + "lastProject.data");
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(lastSaveFile));
			writer.write("");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
