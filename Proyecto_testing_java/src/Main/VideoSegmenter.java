package Main;
import org.opencv.core.*;
import org.opencv.imgproc.*;  
import org.opencv.highgui.Highgui;  
import org.opencv.highgui.VideoCapture;  
import java.util.*;  

public class VideoSegmenter {

	private Imgproc imgProcessor = new Imgproc(); 
	private int hsvScale = imgProcessor.COLOR_BGR2HSV;  
	
	public VideoSegmenter(){
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);  
	}
	
	public LinkedList<Mat> splitVideoToFramesHSV(String filename){
		VideoCapture capture;
		LinkedList<Mat> images=new LinkedList();  
		Mat image = new Mat(); 
		
		capture = new VideoCapture(filename);
		if(capture.isOpened()==false){  
			System.out.println("Video no leido");  
		}  
		int i=0;     
        while(capture.read(image)){  
        	
        	Mat hsvImage = new Mat();  
	        Mat newrgbImage = new Mat();  
	           
	        Imgproc.cvtColor(image, hsvImage, hsvScale); //Transformar a escala hsv
	        images.add(hsvImage);  

           i++;  
         }  
         capture.release(); 
         return images;
       
	}
}
