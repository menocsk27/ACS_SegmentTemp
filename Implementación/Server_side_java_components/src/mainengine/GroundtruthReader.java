/*
 * @author Daniel Troyo
 * 
 * @version 0.1.0
 */

package mainengine;

import java.io.IOException;
import java.util.LinkedList;

import javafx.util.Pair;

/**
 * The Interface GroundtruthReader.
 */
public interface GroundtruthReader {


  /**
   * Gets the beginning frame and the ending frame of a collection of cuts contained in a csv file
   * in the fileRoute specified. If the file located in the file route passed is not of a valid
   * format, or contains corrupted or invalid data or just simply doesn't exist, the program should
   * end or throw an exception.
   * 
   *
   * @param fileRoute the file route of the ground truth file.
   * @return A collection of beginning and ending frames of each cut.
   * @throws IOException If the file is not in csv format, or the inner format of the file is not
   *         correct or the file is not valid.
   */
  LinkedList<Pair<Integer, Integer>> getAbsolutecuts(String fileRoute)
      throws IOException, IllegalArgumentException;

}
