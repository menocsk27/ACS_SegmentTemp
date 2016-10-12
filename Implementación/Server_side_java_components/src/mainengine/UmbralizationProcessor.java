/*
 * @author Daniel Troyo
 */
package mainengine;

import java.util.LinkedList;

/**
 * The Interface UmbralizationProcessor.
 */
public interface UmbralizationProcessor {


  /**
   * Obtain cuts.
   *
   * @param distanceHistArray the distance hist array
   * @return the linked list
   */
  public LinkedList<Boolean> obtainCuts(LinkedList<Double> distanceHistArray);


}
