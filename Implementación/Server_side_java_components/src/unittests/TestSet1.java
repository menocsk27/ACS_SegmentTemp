/*
 * @author Daniel Troyo
 * 
 * @version 0.1.0
 */
package unittests;


import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.opencv.core.Mat;

import mainengine.VideoSegmenter;

// TODO: Auto-generated Javadoc
/**
 * The Class Testjunit0.
 */
@RunWith(Parameterized.class)

public class TestSet1 {

  /** The video route. */
  private String videoRoute;

  /** The vid proc test. */
  private static VideoSegmenter vidProcTest = new VideoSegmenter();



  // Each parameter should be placed as an argument here
  // Every time runner triggers, it will pass the arguments
  // from parameters we defined in primeNumbers() method



  public TestSet1(String videoRoute) {
    this.videoRoute = videoRoute;
  }


  /**
   * Initialization of components. This method executes before all the tests.
   */
  @BeforeClass
  public static void beforeClass() {
    vidProcTest = new VideoSegmenter();
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



  /**
   * Test #1 Validates correct reading of video. Then compares that the resolution of the frames is
   * the same of the video.
   */
  @Test
  public void validateCorrectReadingOfVideo() {
    try {
      LinkedList<Mat> frames = vidProcTest.splitVideosToHSV(this.videoRoute);
      LinkedList<Integer> resVideo = vidProcTest.obtainResOfLastVideoRead();
      int widthFrame = frames.get(1).cols();
      int heightFrame = frames.get(1).rows();
      Assert.assertEquals(resVideo.get(0), widthFrame, 0.1);
      Assert.assertEquals(resVideo.get(1), heightFrame, 0.1);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }



}
