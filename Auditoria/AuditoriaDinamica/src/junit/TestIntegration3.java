package junit;

import futbol.*;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;
import org.opencv.core.Core;

public class TestIntegration3 {
  /*
   * Prueba la integracion entre FutbolFileManager/AbstractFileManager, FootballVideo/AbstractVideo
   * y funciones de Opencv por medio del constructor de FootballVideo, el cual llama funciones de Opencv
   * para settear algunos de sus datos.
   * Las pruebas se realizan con el archivo cut1_360.mp4, consisten en crear un FootballVideo a partir
   * de ese archivo, y se espera que el resultado tenga los datos de ese archivo, los cuales son: 
   * alto 360, ancho 640, frames por segundo (fps) 25, cantFrames 155.
   */
  @Test
  public void test() {
    int fps = 25;
    int alto = 360;
    int ancho = 640;
    int cantFrames = 155;
    
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    FutbolFileManager manager = new FutbolFileManager();
    File file = manager.open("cut1_360.mp4");
    FootballVideo footballVideo = new FootballVideo(file);
    
    try{
      assertEquals(footballVideo.getFps(), fps);
      assertEquals(footballVideo.getAlto(), alto);
      assertEquals(footballVideo.getAncho(), ancho);
      assertEquals(footballVideo.getCantFrames(), cantFrames);
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}