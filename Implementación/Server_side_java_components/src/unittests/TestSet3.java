/*
 * @author Daniel Troyo
 * 
 * @version 0.1.0
 */
package unittests;


import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import mainengine.CsvGroundtruthreader;
import mainengine.GroundtruthReader;

// TODO: Auto-generated Javadoc
/**
 * The Class Testjunit0.
 */
@RunWith(Parameterized.class)

public class TestSet3 {

  /** The csv route. */
  private String fileRoute;

  /** The test junit test. */
  private static GroundtruthReader reader = new CsvGroundtruthreader();


  /**
   * Read lines of CSV.
   *
   * @param fileRoute the file route
   * @return true, if successful
   */
  private boolean readLinesOfCSV(String fileRoute) {
    try {
      reader.getAbsolutecuts(fileRoute);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    } catch (IOException e) {
      return false;
    }
  }

  /**
   * Instantiates a new test set 3.
   *
   * @param fileRoute The file route of the csv ground truth
   */
  public TestSet3(String fileRoute) {
    this.fileRoute = fileRoute;
  }



  /**
   * Parameters to test.
   *
   * @return Collection of parameters,
   */
  @Parameterized.Parameters
  public static Collection parametersToTest() {
    return Arrays.asList(new Object[][] {{"groundtruth1.csv"}, {"groundtruth.csv"}});
  }



  /**
   * Test 2. Validate correct reading of CSV files.
   */
  @Test
  public void validateCorrectReadingOfCSVFiles() {
    Assert.assertEquals(true, readLinesOfCSV(this.fileRoute));
  }



}
