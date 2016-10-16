/*
 * @author Daniel Troyo
 * 
 * @version 0.1.0
 */
package mainengine;

import java.io.IOException;

// TODO: Auto-generated Javadoc
/**
 * The Class MainProgramTemp.
 */
public class MainProgramTemp {

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {

    System.out.println("x");
    MainProcessor program = new MainProcessor();
    try {
      program.startMainflow("Dissolve1-15.mp4", null);
    } catch (IllegalArgumentException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

}
