/*
 * @author Daniel Troyo
 * 
 * @version 0.1.0
 */

package mainengine;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import javafx.util.Pair;

public class CsvGroundtruthwriter implements GroundTruthWriter {

  /**
   * Instantiates a new CsvGroungtruthwriter.
   */



  private static final char DEFAULT_SEPARATOR = ';';
  private static final String TITLE_FIRST_COLUMN = "initialFrame";
  private static final String TITLE_SECOND_COLUMN = "lastFrame";



  private StringBuilder writeDataToFile(StringBuilder sb, String firstColumn, String secondColumn) {
    sb.append(firstColumn);
    sb.append(DEFAULT_SEPARATOR);
    sb.append(secondColumn);
    sb.append("\n");
    return sb;
  }

  private StringBuilder writeTitle(StringBuilder sb) {
    sb = writeDataToFile(sb, TITLE_FIRST_COLUMN, TITLE_SECOND_COLUMN);
    return sb;
  }

  private StringBuilder writeData(StringBuilder sb, Pair<Integer, Integer> escena) {
    String initialFrame = escena.getKey().toString();
    String lastFrame = escena.getValue().toString();
    sb = writeDataToFile(sb, initialFrame, lastFrame);
    return sb;
  }


  public CsvGroundtruthwriter() {}

  @Override
  public void writeGroundTruth(String fileRoute, LinkedList<Pair<Integer, Integer>> data)
      throws IOException, IllegalArgumentException {
    PrintWriter pw = new PrintWriter(new File(fileRoute));
    StringBuilder sb = new StringBuilder();
    sb = writeTitle(sb);
    for (int i = 0; i < data.size(); i++) {
      writeData(sb, data.get(i));
    }
    pw.write(sb.toString());
    pw.close();
  }

}
