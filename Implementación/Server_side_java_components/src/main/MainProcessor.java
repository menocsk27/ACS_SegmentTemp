package main;
 
import java.util.*;  

import org.opencv.core.*;
import org.junit.*;
import org.junit.runner.notification.*;
import org.junit.Assert;
public class MainProcessor {  
 
    
	private static VideoSegmenter vidProc;
    private static HistogramProcessor histProc;  

    
    
    public MainProcessor(){
    	setEnvironment();
    };
    
    
    public  void setEnvironment(){
    	vidProc = new VideoSegmenter();
        histProc = new HistogramProcessor(); 
    }
    
    
	public  Boolean validate(String videoRoute) {
		
		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);  

	    LinkedList<Mat> frames = vidProc.splitVideoToFramesHSV(videoRoute);
	    Assert.assertTrue(frames.size()>15);
	    	
	    LinkedList<Mat> g =histProc.calculateHistoOfHueVideo(frames);
	    Assert.assertTrue(g.size()>15);
	    
		for (int j=0; j<4; j++){
			String name = "histograma"+Integer.toString(j)+".jpg";
			histProc.drawHistogram(g.get(j), name, 300, 300);
		}
		
		return true; 
	}
    

}  
 
