/*
 * @author Daniel Troyo
 */
package unittests;


import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import mainengine.BhattacharyyaHandler;
import mainengine.DistanceHistogramGenerator;
import mainengine.HistogramProcessor;
import mainengine.MainProcessor;
import mainengine.ThreeSigmaUmbralizator;
import mainengine.UmbralizationProcessor;
import mainengine.VideoSegmenter;

@RunWith(Parameterized.class)

public class Testjunit0 {
  private String videoRoute;
  private static MainProcessor testJunitTest = new MainProcessor();
  private boolean expectedResult;
  private static VideoSegmenter vidProcTest = new VideoSegmenter();
  private static HistogramProcessor histProcTest = new HistogramProcessor();
  private static UmbralizationProcessor cutValidatorTest = new ThreeSigmaUmbralizator();
  private static DistanceHistogramGenerator distanceObtainerTest = new BhattacharyyaHandler();



  // Each parameter should be placed as an argument here
  // Every time runner triggers, it will pass the arguments
  // from parameters we defined in primeNumbers() method


  public Testjunit0(String videoRoute, boolean expectedresult) {
    this.videoRoute = videoRoute;
    this.expectedResult = expectedResult;
  }

  @Parameterized.Parameters
  public static Collection parametersToTest() {
    return Arrays.asList(new Object[][] {{"Dissolve1-15.mp4", true}
        // {"C:/Dissolve1-15 480x320 25 fps.mp4", true},
        // {"C:/windows-version.txt", false}
    });
  }

  // This test will run 4 times since we have 5 parameters defined
  @Test
  public void validateCompleteProcess() {
    System.out.println("String cargado como videofile: " + videoRoute);
    this.testJunitTest.startMainflow(videoRoute, "");
  }


}
