/*
 * @author Daniel Troyo
 * 
 * @version 0.1.0
 */
package mainengine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import javafx.util.Pair;

// TODO: Auto-generated Javadoc
/**
 * The Class CsvGroundtruthreader.
 */
public class CsvGroundtruthreader implements GroundtruthReader {



  /**
   * Instantiates a new csv groundtruthreader.
   */
  public CsvGroundtruthreader() {}

  /**
   * Reads the csv file in the corresponding file route submiited
   *
   * @param fileRoute Local file route to the csv file.
   * @return the lines
   */
  private LinkedList<String[]> getLines(String fileRoute) throws IOException {
    String line = "";
    String csvSplitsymbol = ";";
    LinkedList<String[]> lines = new LinkedList<String[]>();
    if (!fileRoute.contains(".csv")) {
      throw new IOException("El archivo debe ser en formato CSV");
    }
    try (BufferedReader br = new BufferedReader(new FileReader(fileRoute))) {
      while ((line = br.readLine()) != null) {
        String[] columnValues = line.split(csvSplitsymbol);
        lines.add(columnValues);
      }
    } catch (IOException exception) {
      throw exception;
    }
    return lines;
  }

  /*
   * (non-Javadoc)
   * 
   * @see mainengine.GroundtruthReader#getAbsolutecuts(java.lang.String)
   */
  @Override
  public LinkedList<Pair<Integer, Integer>> getAbsolutecuts(String fileRoute)
      throws IOException, IllegalArgumentException {
    LinkedList<String[]> linesOfFile = this.getLines(fileRoute);
    LinkedList<Pair<Integer, Integer>> cuts = new LinkedList<Pair<Integer, Integer>>();
    Pair<Integer, Integer> cut;
    String initialFrame;
    String lastFrame;
    try {
      for (int i = 1; i < linesOfFile.size(); i++) { // No lee la fila de títulos
        initialFrame = linesOfFile.get(i)[0];
        lastFrame = linesOfFile.get(i)[1];
        cut =
            new Pair<Integer, Integer>(Integer.parseInt(initialFrame), Integer.parseInt(lastFrame));
        cuts.add(cut);
      }
      return cuts;
    } catch (IllegalArgumentException exception) {
      throw exception;
    }
  }

}
