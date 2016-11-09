package mainengine;

import java.io.IOException;

public class MainProgramTemp {

  public static void main(String[] args) {
    // TODO Auto-generated method stub

    String videoRoute = "Dissolve1-15.mp4";
    String groundTruth = "groundtruth.csv";
    MainProcessor programaPrincipal = new MainProcessor();
    try {
      programaPrincipal.startMainflow(videoRoute, groundTruth);
    } catch (IllegalArgumentException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
