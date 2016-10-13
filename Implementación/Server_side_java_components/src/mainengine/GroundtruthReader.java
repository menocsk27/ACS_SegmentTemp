/*
 * @author Daniel Troyo
 * 
 * @version 0.1.0
 */

package mainengine;

import java.util.LinkedList;

import javafx.util.Pair;

// TODO: Auto-generated Javadoc
/**
 * The Interface GroundtruthReader.
 */
public interface GroundtruthReader {


  /**
   * Gets the beginning frame and the ending frame of a collection of cuts.
   *
   * @param fileRoute the file route of the ground truth file.
   * @return A collection of beginning and ending frames of each cut.
   */
  LinkedList<Pair<Integer, Integer>> getAbsolutecuts(String fileRoute);

}
