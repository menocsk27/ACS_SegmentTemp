package Unit_Tests;

import java.util.Arrays;
import java.util.Collection;
 
import org.junit.Test;
import org.junit.Before;

import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import main.MainProcessor;

import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)

public class TestJunit {
   private String videoRoute;
   private MainProcessor testJunit;
   private Boolean expectedResult;
   
   @Before
   public void initialize() {
	   testJunit = new MainProcessor();
   }

   // Each parameter should be placed as an argument here
   // Every time runner triggers, it will pass the arguments
   // from parameters we defined in primeNumbers() method
	
   public TestJunit(String videofile, Boolean expectedResult) {
      this.videoRoute = videofile;
      this.expectedResult = expectedResult;
   }

   

@Parameterized.Parameters
   public static Collection primeNumbers() {
      return Arrays.asList(new Object[][] {
         {"C:/Dissolve1-15.mp4", true},
         { "C:/Dissolve1-15 480x320 25 fps.mp4", true},
         {"C:/windows-version.txt", false}
      });
   }

   // This test will run 4 times since we have 5 parameters defined
   @Test
   public void testPrimeNumberChecker() {
      System.out.println("String cargado como videofile: " + videoRoute);
      assertEquals(expectedResult, 
    		  testJunit.validate(videoRoute));
   }
}