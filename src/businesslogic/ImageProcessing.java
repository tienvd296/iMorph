package businesslogic;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class ImageProcessing {
	
	//#TO-DO: Convert original image to gray level
	public void rgb2gray(String path) {
//		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//		File inputImg = new File(path);
//		try {
//			BufferedImage image = ImageIO.read(inputImg);
//			byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
//	         Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC1);
//	         mat.put(0, 0, data);
//
//	         Mat mat1 = new Mat(image.getHeight(),image.getWidth(),CvType.CV_8UC1);
//	         Imgproc.cvtColor(mat, mat1, Imgproc.COLOR_RGB2GRAY);
//
//	         byte[] data1 = new byte[mat1.rows() * mat1.cols() * (int)(mat1.elemSize())];
//	         mat1.get(0, 0, data1);
//	         BufferedImage image1 = new BufferedImage(mat1.cols(),mat1.rows(), BufferedImage.TYPE_BYTE_GRAY);
//	         image1.getRaster().setDataElements(0, 0, mat1.cols(), mat1.rows(), data1);
//
//	         File ouptut = new File(path+"_grayscale.jpg");
//	         ImageIO.write(image1, "jpg", ouptut);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		String separator = System.getProperty("file.separator");
		String originalPath = new java.io.File("").getAbsolutePath();
		String pathAPI = originalPath + separator + "image_processing.py";
		System.out.println(pathAPI);
		ProcessBuilder pb = new ProcessBuilder("python",pathAPI,path,"rgb2gray");
		try {
			Process p = pb.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//#TO-DO: Convert image to binary
	public void rgb2bin(String imgPath) {
//		#BUG: 		Unable to retrieve libopencv_java2413.so from opencv
//		#CAUSE: 		Brew can't retrieve any opencv with java (all options has been removed)
//					Manual Download won't do either
//		#TODO: Call python with opencv
		
		
//		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//		File inputImg = new File(imgPath);
//		try {
//			BufferedImage image = ImageIO.read(inputImg);
//			byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
//	         Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
//	         mat.put(0, 0, data);
//	         
//	         Mat mat1 = new Mat(image.getHeight(),image.getWidth(),CvType.CV_8UC1);
//	         Imgproc.threshold(mat, mat1,127,255, Imgproc.COLOR_RGB2GRAY);
//	         
//	         byte[] data1 = new byte[mat1.rows() * mat1.cols() * (int)(mat1.elemSize())];
//	         mat1.get(0, 0, data1);
//	         BufferedImage image1 = new BufferedImage(mat1.cols(),mat1.rows(), BufferedImage.TYPE_BYTE_GRAY);
//	         image1.getRaster().setDataElements(0, 0, mat1.cols(), mat1.rows(), data1);
//
//	         File ouptut = new File(imgPath+"binary.jpg");
//	         ImageIO.write(image1, "jpg", ouptut);
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
	}
}
