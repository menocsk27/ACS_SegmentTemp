/*
 * @author Daniel Troyo
 * 
 * @version 0.1.0
 */
package mainengine;

import java.util.LinkedList;

import org.junit.Assert;
import org.opencv.core.Core;
import org.opencv.core.Mat;

// TODO: Auto-generated Javadoc
/**
 * The Class MainProcessor. Main class of the program and the one handling the main flow of the
 * program. Using the interface of the other classes.
 */
public class MainProcessor {

  /** The Video Segmenter assign to the treatment of video files and image processing.. */
  private static VideoSegmenter vidProc;

  /** The Processor of histograms and all the operations related. */
  private static HistogramProcessor histProc;

  /**
   * The instance assign with the tasks of verifying if there's a cut between two frames based on
   * the distance between the histograms of the frames.
   */
  private static UmbralizationProcessor cutValidator;

  /**
   * The instance assign with the tasks of obtaining the distance between the frames of the video.
   */
  private static DistanceHistogramGenerator distanceObtainer;

  /**
   * Sets the environment for the class, initializing its attributes.
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
   * Function that receives the local route to a video file and local route to a csv groundTruth
   * file. It starts the main time video segmentation and displays the results or comparisons with
   * the ground truth file. The ground truth file must be a four column file containing: Initial
   * frame of the cut in the first column Last frame of the cut in the second column Type of the
   * event. Not used in the segmentation nor the ground truth comparison. Type of the cut. Not used
   * in the segmentation nor the ground truth comparison. The function only accepts avi or mp4 video
   * files.
   * 
   * @param videoRoute Route to a local video file.
   * @param groundTruth the ground truth
   * @return Boolean saying if a route to a video file is valid.
   */
  public boolean startMainflow(String videoRoute, String groundTruth) {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    // Obtengo frames hsv del video los guardo en el frames
    LinkedList<Mat> frames = vidProc.split_video_to_frames_hsv(videoRoute);
    Assert.assertTrue(frames.size() > 15);
    // Obtengo los histogramas de la capa H de cada frame
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
    /*
     * for (int i = 0; i < cutsArray.size(); i++) { System.out.println(i); }
     */
    return true;
  }
}

