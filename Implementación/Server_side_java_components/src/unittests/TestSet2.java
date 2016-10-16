/*
 * @author Daniel Troyo
 * 
 * @version 0.1.0
 */
package unittests;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import mainengine.NumericalDataCalculator;
import mainengine.StadisticalCalculator;

// TODO: Auto-generated Javadoc
/**
 * The Class TestSet2.
 */
@RunWith(Parameterized.class)

public class TestSet2 {

  /** The parameter. */
  private LinkedList<Double> parameter;

  /** The expected result std dev. */
  private double expectedResultStdDev;

  /** The expected result avg. */
  private double expectedResultAvg;

  /** The calculator. */
  private static StadisticalCalculator calculator = new NumericalDataCalculator();


  /**
   * This method creates a LinkedList instance of the Collection objects defined in the function
   * that defines the parameters.
   *
   * @param listValues The Collection object containing the decimal values of the sets to test.
   * @return A Linked List object containing the decimal values of the sets to test.
   */
  private static LinkedList<Double> createLinkedlist(int[] listValues) {
    LinkedList<Double> list = new LinkedList<Double>();
    for (int i = 0; i < listValues.length; i++) {
      list.add((double) listValues[i]);
    }
    return list;
  }

  /**
   * Instantiates a new testjunit 1.
   *
   * @param parameter the parameter
   * @param expectedResultStdDev the expected result std dev
   * @param expectedResultAvg the expected result avg
   */
  public TestSet2(LinkedList<Double> parameter, double expectedResultStdDev,
      double expectedResultAvg) {
    this.parameter = parameter;
    this.expectedResultStdDev = expectedResultStdDev;
    this.expectedResultAvg = expectedResultAvg;
  }



  /**
   * Parameters to test. These parameters are different sets of decimal values and their expected
   * average and standard deviation, respectively.
   *
   * @return the collection
   */
  @Parameterized.Parameters
  public static Collection parametersToTest() {
    LinkedList<Double> parametro1 = new LinkedList<Double>();
    int[] paramTest1 = {4, 2, 5, 7, 32, 23, 11, 5, 16, 23};
    parametro1 = createLinkedlist(paramTest1);
    double stdDevC = 9.693296;
    double avgC = 12.8;

    LinkedList<Double> parametro2 = new LinkedList<Double>();
    int[] paramTest2 = {4, 2, 5, 7, 12, 2, 11, 7, 16, 23, 30};
    parametro2 = createLinkedlist(paramTest2);
    double stdDevD = 8.5792369;
    double avgD = 10.81818182;

    LinkedList<Double> parametro3 = new LinkedList<Double>();
    int[] paramTest3 = {1, 21, 5, 17, 41, 21, 5, 5, 10, 19, 4};
    parametro3 = createLinkedlist(paramTest3);
    double stdDevE = 11.2279168;
    double avgE = 13.54545455;

    return Arrays.asList(new Object[][] {{parametro1, stdDevC, avgC}, {parametro2, stdDevD, avgD},
        {parametro3, stdDevE, avgE}});
  }


  /**
   * Test 3. It compares the returned standard deviation to the expected value.
   */
  @SuppressWarnings({"deprecation"})
  @Test
  public void testStdDevCalc() {
    Assert.assertEquals(expectedResultStdDev, calculator.calculateStdDeviation(parameter), 0.001);

  }

  /**
   * Test 3. It compares the returned average to the expected value.
   */
  @SuppressWarnings("deprecation")
  @Test
  public void testAvgCalc() {
    Assert.assertEquals(expectedResultAvg, calculator.calculateAverage(parameter), 0.001);
  }
}
