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
   * Checks if is cut.
   *
   * @param avg the avg
   * @param stdDev the std dev
   * @param distance the distance
   * @return true, if is cut
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
  public LinkedList<Boolean> obtainCuts(LinkedList<Double> distanceHistArray) {
    LinkedList<Boolean> isDistanceAtPosCut = new LinkedList<Boolean>();
    StadisticalCalculator calculator = new NumericalDataCalculator();

    double avg = calculator.calculateAverage(distanceHistArray);
    double stdDev = calculator.calculateStdDeviation(distanceHistArray);
    System.out.println("Esperanza: " + avg + "Desviación Estándar: " + stdDev);
    for (int i = 0; i < distanceHistArray.size(); i++) {
      if (this.isCut(avg, stdDev, distanceHistArray.get(i))) {
        isDistanceAtPosCut.add(true);
      } else {
        isDistanceAtPosCut.add(false);
      }
    }
    return isDistanceAtPosCut;
  }

}
