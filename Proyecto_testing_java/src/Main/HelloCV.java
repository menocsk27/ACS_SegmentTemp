package Main;
 
import java.util.*;  

import org.opencv.core.*;
import org.junit.*;
import org.junit.runner.notification.*;
import org.junit.Assert;
public class HelloCV {  
 
    
	static VideoSegmenter vidProc;
    static HistogramProcessor histProc;  

    public static void setEnvironment(){
    	vidProc = new VideoSegmenter();
        histProc = new HistogramProcessor(); 
    }
    
	public static void prueba(String video) {
		setEnvironment();
		System.out.println("enta");
		for(int i = 0; i < 5; i++){
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);  
			 
			 
	    	LinkedList<Mat> frames = vidProc.splitVideoToFramesHSV(video);
	    	Assert.assertTrue(frames.size()>15);
	    	
	    	LinkedList<Mat> g =histProc.calculateHistoOfHueVideo(frames);
	    	Assert.assertTrue(g.size()>15);
	    	
			 for (int j=0; j<4; j++){
				 String name = "histograma"+Integer.toString(j)+".jpg";
				 histProc.drawHistogram(g.get(j), name, 300, 300);
			 }
		}
	}
}  
 
