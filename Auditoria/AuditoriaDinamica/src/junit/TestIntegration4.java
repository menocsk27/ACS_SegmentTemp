package junit;

import futbol.*;

import static org.junit.Assert.*;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;

public class TestIntegration4 {
  /*
   * Prueba la integracion entre ProcesadorImagenesFutbol/AbstractProcesadorImagenes y FutbolFrame/AbstractFrame
   * por medio de la funcion convertirAbstractFrame de ProcesadorImagenesFutbol.
   * Las pruebas se realizan a partir de un frame creado por la funcion, el cual se espera
   * que tenga los mismos datos que los usados originalmente para su creacion, los cuales
   * son: alto 3, ancho 3, tipo 1, datos [1,2,3,4,5,6,7,8,9] 
   */
  @Test
  public void test() {
    int alto = 3;
    int ancho = 3;
    int tipo = 1;
    byte[] data = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    
    ProcesadorImagenesFutbol procesador;
    AbstractFrame frame;
   
    Mat mat = new Mat(alto, ancho, tipo);
    mat.put(0, 0, data);
    
    try{
      procesador = new ProcesadorImagenesFutbol();
      frame = procesador.convertirAbstractFrame(mat);
      
      assertEquals(frame.getAlto(), alto);
      assertEquals(frame.getAncho(), ancho);
      assertEquals(frame.getTipo(), tipo);
      for(int i = 0; i < 9; i++){
        assertEquals(frame.getDatos()[i], data[i]);
      }
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}