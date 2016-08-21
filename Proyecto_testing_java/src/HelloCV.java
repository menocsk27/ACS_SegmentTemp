 
import java.util.*;  

import org.opencv.core.*;
import org.junit.*;
import org.junit.runner.notification.*;
import org.junit.Assert;
public class HelloCV {  
 
    
	static VideoSegmenter vidProc;
    static HistogramProcessor histProc;  

    @Before
    public void setEnvironment(){
    	vidProc = new VideoSegmenter();
        histProc = new HistogramProcessor(); 
    }
    
    @Test 
	public void prueba() {
		for(int i = 0; i < 5; i++){
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);  
			 
			 
	    	LinkedList<Mat> frames = vidProc.splitVideoToFramesHSV("Dissolve1-15 480x320 25 fps.mp4");
	    	Assert.assertTrue(frames.size()>15);
	    	
	    	LinkedList<Mat> g =histProc.calculateHistoOfHueVideo(frames);
	    	Assert.assertTrue(g.size()>15);
	    	
			 for (int j=0; i<4; i++){
				 String name = "histograma"+Integer.toString(j)+".jpg";
				 histProc.drawHistogram(g.get(i), name, 300, 300);
			 }
		}
	}
}  
 
