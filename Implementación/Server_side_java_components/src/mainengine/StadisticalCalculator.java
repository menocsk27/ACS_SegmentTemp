/*
 * @author Daniel Troyo
 * 
 * @version 0.1.0
 */
package mainengine;

import java.util.LinkedList;

// TODO: Auto-generated Javadoc
/**
 * The Interface StadisticalCalculator. It provides methods useful when dealing with numerical data
 * sets.
 */
public interface StadisticalCalculator {


  /**
   * This function returns the average of a collection of decimal values passed as a LinkedList.
   *
   * @param set Collection of decimal values.
   * @return The average value of the set passed as parameter.
   */
  public double calculateAverage(LinkedList<Double> set);

  /**
   * This function returns the standard deviation of a collection of decimal values passed as a
   * LinkedList.
   *
   * @param set Collection of decimal values.
   * @return The standard deviation value of the set passed as parameter.
   */
  public double calculateStdDeviation(LinkedList<Double> set);
}
