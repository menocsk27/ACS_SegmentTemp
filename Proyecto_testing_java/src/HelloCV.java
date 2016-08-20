import java.awt.*;  
import java.awt.event.*;  
import javax.swing.*;  
import java.awt.image.BufferedImage;  
import  java.awt.image.DataBufferByte;  
 
import java.awt.image.*;  
import java.io.File;  
import java.lang.Math;  
import java.io.IOException;  
import java.util.*;  
import javax.imageio.ImageIO;

import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgproc.*;  
import org.opencv.highgui.Highgui;  
import org.opencv.highgui.VideoCapture;  
 
public class HelloCV {  
 
    
    
    
  public static void toBufferedImage(Mat m, String name, String format){  
        int type = BufferedImage.TYPE_BYTE_GRAY;  
        if ( m.channels() > 1 ) {  
            type = BufferedImage.TYPE_3BYTE_BGR;  
        }  
        int bufferSize = m.channels()*m.cols()*m.rows();  
        byte [] b = new byte[bufferSize];  
        m.get(0,0,b); // get all the pixels  
        BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);  
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();  
        System.arraycopy(b, 0, targetPixels, 0, b.length);   
        String nameFile = name+"."+format;  
          
        try {  
      ImageIO.write(image, format, new File(nameFile));  
      } catch (IOException e) {  
        // TODO Auto-generated catch block  
        e.printStackTrace();  
      }  
 
    }  
    
 
  public static void main(String[] args) {  
       
       
     System.loadLibrary(Core.NATIVE_LIBRARY_NAME);  
     VideoSegmenter vidProc = new VideoSegmenter();
     HistogramProcessor histProc = new HistogramProcessor();
     
     LinkedList<Mat> frames = vidProc.splitVideoToFramesHSV("Dissolve1-15 480x320 25 fps.mp4");
     LinkedList<Mat> g =histProc.calculateHistoOfHueVideo(frames);
           
     for (int i=0; i<4; i++){
    	 String name = "histograma"+Integer.toString(i)+".jpg";
    	 histProc.drawHistogram(g.get(i), name, 300, 300);
     }
           
           
 
   }  
}  
 
