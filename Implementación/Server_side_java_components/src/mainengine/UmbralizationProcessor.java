/*
 * @author Daniel Troyo
 * 
 * @version 0.1.0
 */
package mainengine;

import java.util.LinkedList;

// TODO: Auto-generated Javadoc
/**
 * The Interface UmbralizationProcessor. It provides functions useful for detecting very high
 * difference between histogram using different methods.
 */
public interface UmbralizationProcessor {


  /**
   * This function returns a collections of boolean value base of the preposition of the existence
   * of a cut or not between two frames based on the distance collection passed as parameter. If a
   * true value stands at the index n, it means that there is a cut between the frame at index n and
   * the frame at the index n+1.
   *
   * @param distanceHistArray The collection of dissimilarity values between frames. A collection of
   *        n dissimilarity values means that there are n+1 frames.
   * @return Collection of boolean values which represent the existence of a cut or not. Same size
   *         as the distanceHistArray parameter.
   */
  public LinkedList<Boolean> obtainCuts(LinkedList<Double> distanceHistArray);


}
