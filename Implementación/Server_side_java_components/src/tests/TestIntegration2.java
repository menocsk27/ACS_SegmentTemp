package tests;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import mainengine.BhattacharyyaHandler;
import mainengine.DistanceHistogramGenerator;
import mainengine.HistogramProcessor;
import mainengine.NumericalDataCalculator;
import mainengine.StadisticalCalculator;
import mainengine.ThreeSigmaUmbralizator;
import mainengine.UmbralizationProcessor;
import mainengine.VideoSegmenter;

@RunWith(Parameterized.class)
public class TestIntegration2 {

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

  private static StadisticalCalculator calculator;

  private LinkedList<Double> valuesGeneratedByBhattacharyya;

  /* Route of the video file used for testing. */
  private String fileRoute;

  public TestIntegration2(String fileRoute) {
    this.fileRoute = fileRoute;
  }

  /**
   * Parameters to test.
   *
   * @return Collection of parameters,
   */
  @Parameterized.Parameters
  public static Collection parametersToTest() {
    return Arrays
        .asList(new Object[][] {{"Dissolve1-15.mp4"}, {"Dissolve1-15 480x320 25 fps.mp4"}});
  }

  @BeforeClass
  public static void initializeOpenCV() {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    vidProc = new VideoSegmenter();
    histProc = new HistogramProcessor();
    cutValidator = new ThreeSigmaUmbralizator();
    distanceObtainer = new BhattacharyyaHandler();
    calculator = new NumericalDataCalculator();
  }

  @Before
  public void generateArrayOfCorrectValues() throws IOException {
    // Obtengo frames hsv del video los guardo en el frames
    LinkedList<Mat> frames = vidProc.splitVideosToHSV(this.fileRoute);
    // Obtengo los histogramas de la capa H de cada frame
    LinkedList<Mat> histogramList = histProc.calculateHistoOfHueVideo(frames);
    // histProc.drawHistogram(histogramList.get(0), "Histograma
    // normalizado.png", 300, 300);
    this.valuesGeneratedByBhattacharyya = distanceObtainer.generateDistanceArray(histogramList);


  }



  @Test(expected = IOException.class)
  public void testUmbralizator() {
    double avg = calculator.calculateAverage(valuesGeneratedByBhattacharyya);
    double stdDev = calculator.calculateStdDeviation(valuesGeneratedByBhattacharyya);
    LinkedList<Boolean> isDistanceAtPosCutManual = new LinkedList<Boolean>();

    for (int i = 0; i < valuesGeneratedByBhattacharyya.size(); i++) {
      if (valuesGeneratedByBhattacharyya.get(i) >= (avg + stdDev)) {
        isDistanceAtPosCutManual.add(true);
      } else {
        isDistanceAtPosCutManual.add(false);
      }
    }

    LinkedList<Boolean> isDistanceAtPosCutMethod = new LinkedList<Boolean>();
    UmbralizationProcessor classToTest = new ThreeSigmaUmbralizator();
    isDistanceAtPosCutMethod = classToTest.obtainCuts(valuesGeneratedByBhattacharyya);

    Assert.assertEquals(isDistanceAtPosCutMethod.size(), isDistanceAtPosCutManual.size());

    for (int i = 0; i < isDistanceAtPosCutManual.size(); i++) {
      Assert.assertEquals(isDistanceAtPosCutManual.get(i), isDistanceAtPosCutMethod.get(i));


    }



  }
}
