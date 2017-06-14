package helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import businesslogic.*;

public class ProjectFile {
	
	public static Project reader(File file)
	{
		// Read objects
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			/*FileInputStream fi = new FileInputStream(file);
			ObjectInputStream oi = new ObjectInputStream(fi);
			String content = oi.readObject().toString();
			oi.close();*/
			
			String content = reader.readLine();
			String[] skeleton = content.split("IMAGES;");
			
			String projectSection = skeleton[0];
			
			String[] tab = projectSection.split(";");
			String[] nameID = tab[0].split("=");
			String name = nameID[1];
			String path = file.getAbsolutePath().toString();
			ArrayList<ImageWing> images = new ArrayList<ImageWing>();
			if(skeleton.length>1)
			{
				images = ProjectFile.readImage(skeleton[1]);				
				
			}
					
			return new Project(name, path, images);
        
        
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
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
			Map<String, String> properties = new HashMap();
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
		String dataSave = "PROJECT_NAME=" + p.name + ";";
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
					while(it.hasNext())
					{
						Landmark landmark = it2.next();
						dataSave = dataSave + landmark.getPosX() + " " + landmark.getPosY() + " " + landmark.getIsLandmark() + ";"; 
					}
				}
			}
		}

		if(p.pathProject == "")
		{
			p.pathProject = "D:\\Documents\\" + p.name + ".project";
		}
		BufferedWriter writer = null;
		try {
			//create a temporary file
			File logFile = new File(p.pathProject);

			writer = new BufferedWriter(new FileWriter(logFile));
			writer.write(dataSave);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// Close the writer regardless of what happens...
				writer.close();
			} catch (Exception e) {
			}
		}

	}
	
}
