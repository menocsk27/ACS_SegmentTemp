package junit;

import static org.junit.Assert.*;

import java.io.File;

import futbol.*;

import org.junit.Test;

public class TestIntegration1 {

  @Test
  public void test() {
    try{
      File file = new File("cut1_360.mp4");
      FootballVideo video = new FootballVideo(file);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
