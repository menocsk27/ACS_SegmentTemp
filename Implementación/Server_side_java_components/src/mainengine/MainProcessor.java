/*
 * @author Daniel Troyo
 * 
 * @version 0.1.0
 */
package mainengine;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import javafx.util.Pair;

// TODO: Auto-generated Javadoc
/**
 * The Class MainProcessor. Main class of the program and the one handling the main flow of the
 * program. Using the interface of the other classes.
 */
public class MainProcessor {

  /**
   * The Video Segmenter assign to the treatment of video files and image processing..
   */
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

  private static String routeGroundTruthGenerator = "cutsObtainedByAnalyzer.csv";

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
   * @param videoRoute Route to a local video file. If it isn't a video file, the whole segmentation
   *        will give erroneous results if it isn't stopped at the segmentation of this video.
   * @param groundTruth the grou0nd truth. If this file contains not numerical values in the first
   *        two columns or values, the lecture of the file will evoke in an error. If the file
   *        contains numerical values outside of the video context, such as negatives or floating
   *        points values, the false positives and negatives will augment substantially and they
   *        will no longer represent a valid metric for the precision of the system.
   * 
   * @return Boolean saying if the process completes successfully.
   * @throws IOException Error in the reading of the csv file or the video file
   * @throws IllegalArgumentException The values located in the ground truth csv file are not in the
   *         correct format.
   */
  public String startMainflow(String videoRoute, String groundTruth)
      throws IOException, IllegalArgumentException {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // Carga la librería
    // Obtengo frames hsv del video los guardo en el frames
    LinkedList<Mat> frames = vidProc.splitVideosToHSV(videoRoute);
    // Obtengo los histogramas de la capa H de cada frame
    LinkedList<Mat> histogramList = histProc.calculateHistoOfHueVideo(frames);
    // histProc.drawHistogram(histogramList.get(0), "Histograma
    // normalizado.png", 300, 300);
    LinkedList<Double> distanceArray = distanceObtainer.generateDistanceArray(histogramList);
    LinkedList<Boolean> cutsArray = cutValidator.obtainCuts(distanceArray);

    PrecisionAnalyzer metricAnalyzer = new falseValuesProcessor();
    for (int i = 0; i < frames.size(); i++) {
      frames.get(i).release();
    }
    for (int j = 0; j < histogramList.size(); j++) {
      histogramList.get(j).release();
    }

    GroundTruthWriter gtGenerator = new CsvGroundtruthwriter();
    LinkedList<Pair<Integer, Integer>> cutsToGTfile = metricAnalyzer.getCutsScenes(cutsArray);

    gtGenerator.writeGroundTruth(routeGroundTruthGenerator, cutsToGTfile);
    File f1 = new File(routeGroundTruthGenerator);
    String pathToCutsGenerated = f1.getAbsolutePath(); // CHARLIE, AQUÍ ESTÁ EL ABSOLUTE. POR SI LO
                                                       // QUIERE.


    if ((!groundTruth.equals("404"))) { // No es 404, so sí se envío GT
      GroundtruthReader lectorGt = new CsvGroundtruthreader();
      LinkedList<Pair<Integer, Integer>> sceneCuts = lectorGt.getAbsolutecuts(groundTruth);

      System.out
          .println("Falsos positivos: " + metricAnalyzer.getFalsepositives(sceneCuts, cutsArray));
      System.out
          .println("Falsos negativos: " + metricAnalyzer.getFalsenegatives(sceneCuts, cutsArray));

      String metrics = metricAnalyzer.getFalsepositives(sceneCuts, cutsArray) + ", "
          + metricAnalyzer.getFalsenegatives(sceneCuts, cutsArray) + ", "
          + System.getProperty("user.dir") + "\\" + routeGroundTruthGenerator;
      return metrics;
    } else { // No se envío GT.
      return ", , " + System.getProperty("user.dir") + "\\" + routeGroundTruthGenerator;
    }
  }
}
