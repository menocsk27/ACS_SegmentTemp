package unittests;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import mainengine.MainProcessor;
import mainengine.NumericalDataCalculator;
import mainengine.StadisticalCalculator;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

@RunWith(Parameterized.class)

public class Testjunit1 {

  private LinkedList<Double> parameter;
  private double expectedResultStdDev;
  private double expectedResultAvg;
  private static StadisticalCalculator calculator = new NumericalDataCalculator();
  
  
  private static LinkedList<Double> createLinkedlist(int[] listValues){
    LinkedList<Double> list = new LinkedList<Double>();
    for (int i = 0; i <listValues.length; i++){
      list.add((double) listValues[i]);
    }
    return list;
  }

  public Testjunit1(LinkedList<Double> parameter, double expectedResultStdDev, double expectedResultAvg) {
    this.parameter = parameter;
    this.expectedResultStdDev = expectedResultStdDev;
    this.expectedResultAvg = expectedResultAvg;
  }



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


  @SuppressWarnings({"deprecation"})
  @Test
  public void testStdDevCalc() {
    Assert.assertEquals(expectedResultStdDev, calculator.calculateStdDeviation(parameter),0.001);

  }

  @SuppressWarnings("deprecation")
  @Test
  public void testAvgCalc() {
    Assert.assertEquals(expectedResultAvg, calculator.calculateAverage(parameter), 0.001);
  }
}
