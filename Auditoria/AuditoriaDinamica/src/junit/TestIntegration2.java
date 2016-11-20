package junit;

import futbol.*;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;

public class TestIntegration2 {
  /*
   * Prueba la integracion entre FutbolFileManager, FutbolFrame/AbstractFrame
   * y ProcesadorVideoFutbol por medio de la funcion convertirMat de ProcesadorVideoFutbol.
   * Las pruebas que se realizan se basan en crear un Mat a partir de convertirMat (mat2) y esta
   * se compara, dato por dato, con un Mat con los mismos datos hecha manualmente (mat1). Los
   * datos de mat1 son: alto 3, ancho 3, tipo 1, datos [1,2,3,4,5,6,7,8,9]
   */
  @Test
  public void test() {
    int alto = 3;
    int ancho = 3;
    int tipo = 1;
    byte[] data = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    
    FutbolFileManager fileManager = new FutbolFileManager();
    File file = fileManager.open("cut1_360.mp4");
     
    AbstractFrame frame = new FutbolFrame(data, alto, ancho, tipo);
    ProcesadorVideoFutbol procesador;
    Mat mat1, mat2;
    mat1 = new Mat(alto, ancho, tipo);
    mat1.put(0, 0, data);
    try{
      procesador = new ProcesadorVideoFutbol(file);
      mat2 = procesador.convertirMat(frame);/////
      for(int i = 0; i < 3; i++){
        for(int j = 0; j < 3; j++){
          assertEquals(mat1.get(i, j)[0], mat2.get(i, j)[0], 0);
        }
      }
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}