/*
 * @author Daniel Troyo
 */
package mainengine;

import java.util.LinkedList;

import org.junit.Assert;
import org.opencv.core.Core;
import org.opencv.core.Mat;

// TODO: Auto-generated Javadoc
/**
 * The Class MainProcessor.
 */
public class MainProcessor {

  /** The vid proc. */
  private static VideoSegmenter vidProc;

  /** The hist proc. */
  private static HistogramProcessor histProc;

  /** The cut validator. */
  private static UmbralizationProcessor cutValidator;

  /** The distance obtainer. */
  private static DistanceHistogramGenerator distanceObtainer;

  /**
   * Sets the environment.
   */
  private void setEnvironment() {
    vidProc = new VideoSegmenter();
    histProc = new HistogramProcessor();
    cutValidator = new ThreeSigmaUmbralizator();
    distanceObtainer = new BhattacharyyaHandler();
  }

  /**
   * Instantiates a new main processor.
   */
  public MainProcessor() {
    setEnvironment();
  }



  /**
   * Function that receives the local route to a video file and validates if it contains a video.
   * 
   * @param videoRoute Route to a local video file.
   * @return Boolean saying if a route to a video file is valid.
   */
  public boolean startMainflow(String videoRoute, String groundTruth) {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    LinkedList<Mat> frames = vidProc.split_video_to_frames_hsv(videoRoute);
    Assert.assertTrue(frames.size() > 15);



    LinkedList<Mat> histogramList = histProc.calculateHistoOfHueVideo(frames);
    Assert.assertTrue(histogramList.size() > 15);

    // histProc.drawHistogram(histogramList.get(0), "Histograma normalizado.png", 300, 300);
    LinkedList<Double> distanceArray = distanceObtainer.generateDistanceArray(histogramList);
    Assert.assertTrue(distanceArray.size() == histogramList.size() - 1);
    System.out.print("[");
    for (int i = 0; i < distanceArray.size(); i++) {
      System.out.print(distanceArray.get(i) + ",");
    }
    System.out.print("]");
    System.out.println();
    LinkedList<Boolean> cutsArray = cutValidator.obtainCuts(distanceArray);
    Assert.assertTrue(cutsArray.size() == distanceArray.size());
    // value

    for (int i = 0; i < cutsArray.size(); i++) {
      System.out.println(i);
    }
    return true;
  }
}

