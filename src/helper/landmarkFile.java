package helper;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import businesslogic.ImageWing;
import businesslogic.Landmark;

public class landmarkFile {

	public static void saveImage(ImageWing im)
	{
		String separator = System.getProperty("file.separator");
		String originalPath = System.getProperty("user.dir");

		String dataSave = "";

		if(!im.getLandmarks().isEmpty())
		{
			Iterator<Landmark> it = im.getLandmarks().iterator();

			while(it.hasNext())
			{
				Landmark land = it.next();
				dataSave = dataSave + land.getPosX() + " " + land.getPosY() + "\n";
			}

		}

		BufferedWriter writer = null;
		try {
			//create a temporary file
			File logFile = new File(im.getPath() + ".TPS");

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
