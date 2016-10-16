/*
 * @author Daniel Troyo
 * 
 * @version 0.1.0
 */



package mainengine;

import java.util.LinkedList;


// TODO: Auto-generated Javadoc
/**
 * The Class ThreeSigmaUmbralizator.
 */
public class ThreeSigmaUmbralizator implements UmbralizationProcessor {

  /**
   * Instantiates a new three sigma umbralizator.
   */
  public ThreeSigmaUmbralizator() {}


  /**
   * Checks if the value of the disimilitude between the two histograms is above or equal to the sum
   * of the average and the standard deviation of all the disimilitude values. If true, then is
   * considered as a cut and return true.
   *
   * @param avg The average of all the disimilitude values between histograms of frames. It should
   *        be in the range [0,1]
   * @param stdDev The standard deviation of all the disimilitude values between histograms of
   *        frames. It should be in the range [0,1]
   * @param distance The disimilitude value between two histograms, calculated via bhattacharyya
   *        distance. It should be in the range [0,1]
   * @return true, If the distance is equal or superior to the sum of the average and standard
   *         deviation.
   */
  private boolean isCut(double avg, double stdDev, double distance) {
    if (distance >= (avg + stdDev)) {
      return true;
    } else {
      return false;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see mainengine.UmbralizationProcessor#obtainCuts(java.util.LinkedList)
   */
  @Override
  public LinkedList<Boolean> obtainCuts(LinkedList<Double> distanceHistArray)
      throws IllegalArgumentException {
    LinkedList<Boolean> isDistanceAtPosCut = new LinkedList<Boolean>();
    StadisticalCalculator calculator = new NumericalDataCalculator();
    double avg;
    double stdDev;
    try {
      avg = calculator.calculateAverage(distanceHistArray);
      stdDev = calculator.calculateStdDeviation(distanceHistArray);
      System.out.println("Esperanza: " + avg + "Desviación Estándar: " + stdDev);
      for (int i = 0; i < distanceHistArray.size(); i++) {
        if (this.isCut(avg, stdDev, distanceHistArray.get(i))) {
          isDistanceAtPosCut.add(true);
        } else {
          isDistanceAtPosCut.add(false);
        }
      }
      return isDistanceAtPosCut;
    } catch (IllegalArgumentException exception) {
      throw exception;
    }

  }

}
