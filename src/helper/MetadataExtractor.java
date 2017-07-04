package helper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

public class MetadataExtractor {
	
	public static HashMap<String, String> metadataExtractor(File file){


		Metadata metadata;
		HashMap<String, String> metaContent = new HashMap<String, String>();
		try {
			metadata = ImageMetadataReader.readMetadata(file);


			for (Directory directory : metadata.getDirectories()) {
				for (Tag tag : directory.getTags()) {
					metaContent.put(tag.getTagName(), tag.getDescription());
				}
				if (directory.hasErrors()) {
					for (String error : directory.getErrors()) {
						System.err.format("ERROR: %s", error);
					}
				}
			}
		} catch (ImageProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return metaContent;
		
	}

}
