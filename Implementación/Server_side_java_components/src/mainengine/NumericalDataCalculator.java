/*
 * @author Daniel Troyo
 */
package mainengine;

import java.util.LinkedList;

// TODO: Auto-generated Javadoc
/**
 * The Class NumericalDataCalculator.
 */
public class NumericalDataCalculator implements StadisticalCalculator {


  /**
   * Calculate variance.
   *
   * @param set the set
   * @return the double
   */
  private double calculateVariance(LinkedList<Double> set) {
    double variance = 0;
    double avg = this.calculateAverage(set);

    for (int i = 0; i < set.size(); i++) {
      variance += Math.pow(set.get(i) - avg, 2);
    }
    variance = variance / set.size();
    return variance;
  }

  /**
   * Instantiates a new numerical data calculator.
   */
  public NumericalDataCalculator() {}

  /* (non-Javadoc)
   * @see mainengine.StadisticalCalculator#calculateAverage(java.util.LinkedList)
   */
  @Override
  public double calculateAverage(LinkedList<Double> set) {
    double average = 0;
    for (int i = 0; i < set.size(); i++) {
      average += (double) set.get(i);
    }
    average = average / set.size();
    return average;
  }

  /* (non-Javadoc)
   * @see mainengine.StadisticalCalculator#calculateStdDeviation(java.util.LinkedList)
   */
  @Override
  public double calculateStdDeviation(LinkedList<Double> set) {
    double stdDev;
    double variance = this.calculateVariance(set);
    stdDev = Math.sqrt(variance);
    return stdDev;
  }

}
