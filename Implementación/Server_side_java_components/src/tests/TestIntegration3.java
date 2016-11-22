package tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;

import mainengine.HistogramProcessor;
import mainengine.VideoSegmenter;

@RunWith(Parameterized.class)
public class TestIntegration3 {



  /**
   * The Video Segmenter assign to the treatment of video files and image processing..
   */
  private static VideoSegmenter vidProc;

  /** The Processor of histograms and all the operations related. */
  private static HistogramProcessor histProc;



  private LinkedList<Mat> framesObtained;
  private Size resolutionExpected;

  /* Route of the video file used for testing. */
  private String fileRoute;

  public TestIntegration3(String fileRoute) {
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
  }

  @Before
  public void generateArrayOfFrames() throws IOException {


    // Obtengo frames hsv del video los guardo en el frames
    this.framesObtained = vidProc.splitVideosToHSV(this.fileRoute);
    resolutionExpected = this.framesObtained.get(0).size();
  }


  @Test
  public void testStructureOfHistogramGeneratorReturnedValued() {

    // Obtengo los histogramas de la capa H de cada frame
    LinkedList<Mat> histogramList = histProc.calculateHistoOfHueVideo(this.framesObtained);
    for (int i = 0; i < histogramList.size(); i++) {
      assertEquals(histogramList.get(i).size().area(), 256.0, 0.02);
      histogramList.get(i).release();
      this.framesObtained.get(i).release();
    }
  }
}
