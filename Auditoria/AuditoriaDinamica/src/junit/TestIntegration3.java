package junit;

import futbol.*;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;
import org.opencv.core.Core;

public class TestIntegration3 {

  @Test
  public void test() {
    int fps = 25;
    int alto = 360;
    int ancho = 640;
    int cantFrames = 155;
    
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    FutbolFileManager manager = new FutbolFileManager();
    File file = manager.open("cut1_360.mp4");
    FootballVideo fv = new FootballVideo(file);
    
    try{
      assertEquals(fv.getFps(), fps);
      assertEquals(fv.getAlto(), alto);
      assertEquals(fv.getAncho(), ancho);
      assertEquals(fv.getCantFrames(), cantFrames);
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}
