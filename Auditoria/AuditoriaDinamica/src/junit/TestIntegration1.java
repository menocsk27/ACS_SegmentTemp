package junit;

import static org.junit.Assert.*;

import java.io.File;

import futbol.*;

import org.junit.Test;
import org.opencv.core.Core;

public class TestIntegration1 {
  /*
   * Prueba la integracion entre FutbolFileManager/AbstractFileManager, FootballVideo/AbstractVideo,
   * y FutbolFrame/AbstractFrame por medio de la funcion obtenerCuadro, la cual pertenece 
   * a FootballVideo.
   * Las pruebas que hace son para verificar que el frame que se consigue al llamar
   * obtenerCuadro tengan los datos esperados de un frame cualquiera del video
   * cut1_360.mp4, los cuales son: ancho 640, alto: 360, tipo: 16.
   */
  @Test
  public void test() {
    int anchoEsperado = 640;
    int altoEsperado = 360;
    int tipoEsperado = 16;
    
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    
    FutbolFileManager fileManager = new FutbolFileManager();
    File file = fileManager.open("cut1_360.mp4");
    
    FootballVideo video = new FootballVideo(file);
    AbstractFrame frame = video.obtenerCuadro();///
    try{
      assertEquals(frame.getAlto(), altoEsperado);
      assertEquals(frame.getAncho(), anchoEsperado);
      assertEquals(frame.getTipo(), tipoEsperado);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}