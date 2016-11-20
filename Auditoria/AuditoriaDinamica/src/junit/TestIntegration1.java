package junit;

import static org.junit.Assert.*;

import java.io.File;

import futbol.*;

import org.junit.Test;
import org.opencv.core.Core;

public class TestIntegration1 {
  //Prueba que los datos basicos del primer frame de cut1_360.mp4 sean los esperados
  @Test
  public void test() {
    int ancho = 640;
    int alto = 360;
    int tipo = 16;
    
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    
    FutbolFileManager fileManager = new FutbolFileManager();
    File file = fileManager.open("cut1_360.mp4");
    
    FootballVideo video = new FootballVideo(file);
    AbstractFrame frame = video.obtenerCuadro();///
    try{
      assertEquals(frame.getAlto(), alto);
      assertEquals(frame.getAncho(), ancho);
      assertEquals(frame.getTipo(), tipo);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
