import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.core.*;
import org.opencv.imgcodecs.*;
import org.opencv.imgproc.*;


public class HelloCV {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		VideoCapture capture;
		 System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
         Mat mat = Mat.eye(3, 3, CvType.CV_8UC1);
         System.out.println("mat = " + mat.dump());
         
         capture = new VideoCapture("input/Dissolve1-15.mp4");
         
         if(capture.isOpened()==false){
        	 System.out.println("Video no leido");
         }
         Mat edges;
         LinkedList<Mat> images=new LinkedList();
         Mat image = new Mat();
         while(capture.read(image)){
        	 capture.read(image);
        	 images.add(image);
         }
         Imgproc imgProcessor = new Imgproc();
         for (int i=0; i< images.size(); i++){
        	 
        	 Mat currentImage = images.get(i);
        	 
        	 System.out.println(currentImage.dims());
        	 System.out.println("Channels: "+(Integer.toString(currentImage.channels())));
         
        	 int grayScale = imgProcessor.COLOR_RGB2GRAY;
        	 Imgproc.cvtColor(currentImage, currentImage, grayScale); //Transformar a escala de grises
        	 BufferedImage gray = new BufferedImage(currentImage.width(), currentImage.height(), BufferedImage.TYPE_BYTE_GRAY);
        	 byte[] data = ((DataBufferByte) gray.getRaster().getDataBuffer()).getData();
        	 currentImage.get(0, 0, data);
        	 
        	 String name = "frame"+Integer.toString(i)+".jpg";
        	 File outputfile = new File(name);
        	   
        	 try {
				ImageIO.write(gray, "jpg", outputfile);
			 } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
         }
         
	}

}
