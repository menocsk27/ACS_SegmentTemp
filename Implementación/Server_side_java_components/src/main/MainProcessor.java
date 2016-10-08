package main;

import org.junit.Assert;
import org.opencv.core.Core;
import org.opencv.core.Mat;
//import org.junit.runner.notification.Failure;
//import org.junit.Assert;

import java.util.LinkedList;

public class MainProcessor {
  private static VideoSegmenter vidProc;
  private static HistogramProcessor histProc;
  
  public MainProcessor() {
    setEnvironment();
  }
  
  public void setEnvironment() {
    vidProc = new VideoSegmenter();
    histProc = new HistogramProcessor();
  }
  
  /** Function that receives the local route to a video file and validates if it contains a video.
   * @param videoRoute Route to a local video file.
   * @return Boolean saying if a route to a video file is valid.
   */
  public Boolean validate(String videoRoute) {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    LinkedList<Mat> frames = vidProc.split_video_to_frames_hsv(videoRoute);
    Assert.assertTrue(frames.size() > 15);
    
    LinkedList<Mat> framesList = histProc.calculateHistoOfHueVideo(frames);
    Assert.assertTrue(framesList.size() > 15);
    
    for (int j = 0; j < 1/*framesList.size()*/; j++) {
    	histProc.drawHistogram(framesList.get(j), "Histograma normalizado", 300, 300);
    }
    
    return true;
  }
}