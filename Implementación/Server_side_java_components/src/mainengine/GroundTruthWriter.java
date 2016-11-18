/*
 * @author Daniel Troyo
 * 
 * @version 0.1.0
 */


package mainengine;

import java.io.IOException;
import java.util.LinkedList;

import javafx.util.Pair;

public interface GroundTruthWriter {


  /**
   * Generates a ground truth file containing the
   * 
   *
   * @param fileRoute the file route of the ground truth file to be generated.
   * @param LinkedList<Pair<Integer, Integer>> data A collection of beginning and ending frames of
   *        each cut.
   * @return A collection of beginning and ending frames of each cut.
   * @throws IOException The inner format of the file is not correct or the file route is not valid.
   * @throws IllegalArgumentException The data input is not correct or it is not in the correct
   *         format.
   */


  void writeGroundTruth(String fileRoute, LinkedList<Pair<Integer, Integer>> data)
      throws IOException, IllegalArgumentException;


}
