package tests;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import javafx.util.Pair;
import mainengine.BhattacharyyaHandler;
import mainengine.CsvGroundtruthreader;
import mainengine.DistanceHistogramGenerator;
import mainengine.GroundtruthReader;
import mainengine.HistogramProcessor;
import mainengine.PrecisionAnalyzer;
import mainengine.ThreeSigmaUmbralizator;
import mainengine.UmbralizationProcessor;
import mainengine.VideoSegmenter;
import mainengine.falseValuesProcessor;

@RunWith(Parameterized.class)

public class TestIntegration1 {


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

  private static PrecisionAnalyzer metricAnalyzer;

  /** The csv route. */
  private String fileRoute;

  private String videoRoute;

  /** Expected result of reading */
  private boolean expectedResult;

  /** The test junit test. */
  private static GroundtruthReader reader = new CsvGroundtruthreader();

  private static LinkedList<Pair<Integer, Integer>> realCutsGT;

  /**
   * Read lines of CSV.
   *
   * @param fileRoute the file route
   * @return true, if successful
   */
  private boolean readLinesOfCSV(String fileRoute) {
    try {
      realCutsGT = reader.getAbsolutecuts(fileRoute);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    } catch (IOException e) {
      return false;
    }
  }


  private boolean areTheMetricsValid() {
    // Obtengo frames hsv del video los guardo en el frames
    LinkedList<Mat> frames;
    try {
      frames = vidProc.splitVideosToHSV(videoRoute);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      return false;
    }
    // Obtengo los histogramas de la capa H de cada frame
    LinkedList<Mat> histogramList = histProc.calculateHistoOfHueVideo(frames);
    // histProc.drawHistogram(histogramList.get(0), "Histograma
    // normalizado.png", 300, 300);
    for (int i = 0; i < frames.size(); i++) {
      frames.get(i).release();
    }


    LinkedList<Double> distanceArray = distanceObtainer.generateDistanceArray(histogramList);
    for (int j = 0; j < histogramList.size(); j++) {
      histogramList.get(j).release();
    }
    LinkedList<Boolean> cutsArray = cutValidator.obtainCuts(distanceArray);
    GroundtruthReader lectorGt = new CsvGroundtruthreader();
    LinkedList<Pair<Integer, Integer>> sceneCuts;
    try {
      sceneCuts = lectorGt.getAbsolutecuts(this.fileRoute);
    } catch (IllegalArgumentException | IOException e) {
      // TODO Auto-generated catch block
      return false;
    }

    Assert.assertTrue(metricAnalyzer.getFalsepositives(sceneCuts, cutsArray) >= 2);
    Assert.assertTrue(metricAnalyzer.getFalsenegatives(sceneCuts, cutsArray) >= 2);
    return true;
  }

  /**
   * Instantiates a new test set 3.
   *
   * @param fileRoute The file route of the csv ground truth
   */
  public TestIntegration1(String fileRoute, String videoRoute, boolean expectedResult) {
    this.fileRoute = fileRoute;
    this.videoRoute = videoRoute;
    this.expectedResult = expectedResult;
  }


  @BeforeClass
  public static void initializeOpenCV() {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // Carga la librería
    vidProc = new VideoSegmenter();
    histProc = new HistogramProcessor();
    cutValidator = new ThreeSigmaUmbralizator();
    distanceObtainer = new BhattacharyyaHandler();
    metricAnalyzer = new falseValuesProcessor();
  }

  /**
   * Parameters to test.
   *
   * @return Collection of parameters,
   */
  @Parameterized.Parameters
  public static Collection parametersToTest() {
    return Arrays.asList(new Object[][] {{"groundtruth1.csv", "Dissolve1-15.mp4", true},
        {"groundtruth.csv", "Dissolve1-15.mp4", true},
        {"groundtruth1.csv", "Dissolve1-15 480x320 25 fps.mp4", true},
        {"groundtruth.csv", "Dissolve1-15 480x320 25 fps.mp4", true},
        {"videoNoExistente.mp4", "Dissolve1-15 480x320 25 fps.fmepg", false}});
  }



  /**
   * Test 2. Validate correct reading of CSV files.
   */
  @Test
  public void validateCorrectReadingOfCSVFilesAndMetricsGeneration() {
    Assert.assertEquals(this.expectedResult, readLinesOfCSV(this.fileRoute));
    Assert.assertEquals(this.expectedResult, areTheMetricsValid());
  }



}
