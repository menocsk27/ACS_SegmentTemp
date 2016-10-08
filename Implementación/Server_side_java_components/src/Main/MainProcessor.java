package Main;

import org.junit.Assert;
import org.opencv.core.Core;
import org.opencv.core.Mat;
//import org.junit.runner.notification.Failure;
//import org.junit.Assert;

import java.util.LinkedList;

public class MainProcessor {
  private static VideoSegmenter vidProc;
  private static HistogramProcessor histProc;
  private static UmbralizationProcessor cutValidator;
  private static DistanceHistogramGenerator distanceObtainer;
  
  public MainProcessor() {
    setEnvironment();
  }
  
  public void setEnvironment() {
    vidProc = new VideoSegmenter();
    histProc = new HistogramProcessor();
    cutValidator = new ThreeSigmaUmbralizator();
    distanceObtainer = new BhattacharyyaHandler();
  }
  
  /** Function that receives the local route to a video file and validates if it contains a video.
   * @param videoRoute Route to a local video file.
   * @return Boolean saying if a route to a video file is valid.
   */
  public Boolean validate(String videoRoute) {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    LinkedList<Mat> frames = vidProc.split_video_to_frames_hsv(videoRoute);
    Assert.assertTrue(frames.size() > 15);
    
    LinkedList<Mat> histogramList = histProc.calculateHistoOfHueVideo(frames);
    Assert.assertTrue(histogramList.size() > 15);
    
    LinkedList<Double> distanceArray = distanceObtainer.generateDistanceArray(histogramList);
    Assert.assertTrue(distanceArray.size() == histogramList.size() - 1);
    
    LinkedList<Boolean> cutsArray = cutValidator.obtainCuts(distanceArray);
    Assert.assertTrue(cutsArray.size() == distanceArray.size() );
    
    for (int i = 0; i < cutsArray.size(); i++){
    	if (cutsArray.get(i) == true){
    		System.out.println( distanceArray.get(i).toString() );
    	}
    }
    
    
    return true;
  }
}