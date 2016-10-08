package main;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.*;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import java.util.LinkedList;

public class HistogramProcessor {
  private final MatOfFloat histRange = new MatOfFloat(0, 256);
  private final MatOfInt histSize = new MatOfInt(256);
  private final MatOfInt histChannels = new MatOfInt(0); 
  
  HistogramProcessor(){}
  
  
  /*
   @arg1 images Estructura de datos que contiene los objetos Mat que representa los frames 
   del video. 
   @return linkedList Estructura de datos que contiene los objetos Mat que representa los 
   histogramas del valor de matiz de cada frame.
   */
  public  LinkedList<Mat> calculateHistoOfHueVideo(LinkedList<Mat> images) {
    LinkedList<Mat> linkedList = new LinkedList();
    for (int i = 0; i < images.size(); i++) {
      Mat histograma = calculateHistoOfHueImage(images.get(i));
      linkedList.add(histograma);
    }
    return linkedList;
  }
  
  private Mat calculateHistoOfHueImage(Mat image) {
    Mat histogramH = new Mat(); 
    LinkedList<Mat> channelsImage = new LinkedList();  
    Core.split(image, channelsImage);
    Imgproc.calcHist(channelsImage, histChannels, new Mat(),
        histogramH, histSize, histRange, false);
    //Se normaliza por el número de pixeles (resolución)
    Core.normalize(histogramH, histogramH, 0, histogramH.rows() * histogramH.cols(),Core.NORM_L2, -1, new Mat()); 
    /*
    for (int i=0; i < histogramH.rows(); i++){
    	histogramH histogramH.get(i, 0)[0]
    }
    */	
    //histogramH.put
    return histogramH;
  }
  

  
  public void drawHistogram(Mat histogramH, String name, int width , int height) {
    int counter;
    Mat histogramHimage = new Mat(width, height, CvType.CV_8UC3, new Scalar(0,0,0));
    int binW = (int) Math.round(width / histSize.get(0, 0)[0]);
    Core.normalize(histogramH, histogramH, 0, histogramHimage.rows(),
        Core.NORM_MINMAX, -1, new Mat());  
    for ( counter = 1; counter < histSize.get(0, 0)[0]; counter++) {
      //B component or gray image
      Core.line(histogramHimage, new Point(binW * (counter - 1), 
          height - Math.round(histogramH.get(counter - 1, 0)[0])),  
          new Point(binW * (counter), height - Math.round(histogramH.get(counter, 0)[0])),
          new Scalar(255, 0, 0), 2, 8, 0);  
    }
    Highgui.imwrite(name, histogramHimage);  
  }
}