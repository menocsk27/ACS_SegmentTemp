package Main;
import java.util.LinkedList;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class HistogramProcessor {

	private final MatOfFloat histRange = new MatOfFloat(0, 256);  
	private final MatOfInt histSize = new MatOfInt(256);  
	private final MatOfInt histChannels = new MatOfInt(0); 
	
	HistogramProcessor(){}
	
	
	
	LinkedList<Mat> calculateHistoOfHueVideo(LinkedList<Mat> images){
		
		LinkedList<Mat> g =new LinkedList();
		for(int i=0; i<images.size();i++){
			Mat histograma = calculateHistoOfHueImage(images.get(i));
			g.add(histograma);
		}
		return g;
	}
	
	Mat calculateHistoOfHueImage(Mat image){
		Mat histogramH = new Mat(); 
        LinkedList<Mat> channelsImage = new LinkedList();  
        Core.split(image, channelsImage);   
        Imgproc.calcHist(channelsImage, histChannels, new Mat(), histogramH, histSize, histRange, false);
        return histogramH;
	}
	
	void drawHistogram(Mat histogramH, String name, int width , int height){
		
		int i;
		Mat histogramHimage = new Mat(width, height, CvType.CV_8UC3, new Scalar(0,0,0));  
        
        int bin_w = (int) Math.round(width/histSize.get(0, 0)[0]);  
        Core.normalize(histogramH, histogramH, 0, histogramHimage.rows(), Core.NORM_MINMAX, -1, new Mat());  

        for ( i = 1; i < histSize.get(0, 0)[0]; i++){  
          // B component or gray image  
       	 Core.line(histogramHimage, new Point(bin_w * (i - 1), height - Math.round(histogramH.get(i - 1, 0)[0])),  
   	 	 new Point(bin_w * (i), height - Math.round(histogramH.get(i, 0)[0])), new Scalar(255, 0, 0), 2, 8, 0);  
        }  
      
        Highgui.imwrite(name, histogramHimage);  
	}
}
