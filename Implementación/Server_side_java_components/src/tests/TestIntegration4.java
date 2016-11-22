package tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import mainengine.BhattacharyyaHandler;
import mainengine.HistogramProcessor;
import mainengine.NumericalDataCalculator;
import mainengine.StadisticalCalculator;
import mainengine.VideoSegmenter;

public class TestIntegration4 {


  /** The Video Segmenter assign to the treatment of video files and image processing.. */
  private static VideoSegmenter vidProc;
  /** The Processor of histograms and all the operations related. */
  private static HistogramProcessor histProc;
  private static StadisticalCalculator calculator;
  private static BhattacharyyaHandler distanceObtainer;

  /* Route of the video file used for testing. */
  private String fileRoute;


  public TestIntegration4(String fileRoute) {
    this.fileRoute = fileRoute;

  }

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
    calculator = new NumericalDataCalculator();
    distanceObtainer = new BhattacharyyaHandler();

  }

  @Test
  public void testObtentionOfAvgOfHist() throws IOException {
    // Obtengo frames hsv del video los guardo en el frames
    LinkedList<Mat> frames = vidProc.splitVideosToHSV(this.fileRoute);
    // Obtengo los histogramas de la capa H de cada frame
    LinkedList<Mat> histogramList = histProc.calculateHistoOfHueVideo(frames);
    for (int i = 5; i < histogramList.size(); i++) {
      // Me quedo sólo con los primeros cinco frames
      histogramList.get(i).release();
      frames.get(i).release();
    }
    for (int i = 0; i < 5; i++) {
      // Pruebo sólo los primeros cinco frames
      // Obtengo el coeficiente de normalización entre cada frame
      if (i == 4) {
        // Es el último
        continue;
      }
      double avgObtainedByMethodHist1;
      double avgObtainedByFormulaHist1 = 0;
      double avgObtainedByMethodHist2;
      double avgObtainedByFormulaHist2 = 0;

      LinkedList<Double> hist1Values = new LinkedList<Double>();
      LinkedList<Double> hist2Values = new LinkedList<Double>();
      for (int bin = 0; bin < histogramList.get(i).total(); bin++) {
        hist1Values.add(histogramList.get(i + 1).get(bin, 0)[0]);
      }
      for (int bin = 0; bin < 256; bin++) {
        hist1Values.add(histogramList.get(i).get(bin, 0)[0]);
        hist2Values.add(histogramList.get(i + 1).get(bin, 0)[0]);
      }



      avgObtainedByMethodHist1 = calculator.calculateAverage(hist1Values);
      avgObtainedByMethodHist2 = calculator.calculateAverage(hist2Values);

      for (int j = 0; j < hist1Values.size(); j++) {
        avgObtainedByFormulaHist1 += hist1Values.get(j);
      }
      avgObtainedByFormulaHist1 = avgObtainedByFormulaHist1 / hist1Values.size();

      for (int j = 0; j < hist2Values.size(); j++) {
        avgObtainedByFormulaHist2 += hist2Values.get(j);
      }
      avgObtainedByFormulaHist2 = avgObtainedByFormulaHist2 / hist2Values.size();

      assertEquals(avgObtainedByFormulaHist1, avgObtainedByMethodHist1, 0.005);
      assertEquals(avgObtainedByFormulaHist2, avgObtainedByMethodHist2, 0.005);
    }
    for (int i = 0; i < 5; i++) {
      // Termino de liberar la memoria de los arreglos de Mat
      histogramList.get(i).release();
      frames.get(i).release();
    }

  }

}
