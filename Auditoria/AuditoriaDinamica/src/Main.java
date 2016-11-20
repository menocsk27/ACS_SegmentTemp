import futbol.FutbolFileManager;
import futbol.ProcesadorVideoFutbol;

import org.opencv.core.Core;

import java.io.File;
import java.io.IOException;

public class Main {

  /**
   * Funci�n principal para correr el programa.
   * 
   * @param args argumentos enviados por consola.
   */
  public static void main(String[] args) {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    FutbolFileManager fileManager = new FutbolFileManager();
    System.out.println("test");
    File file = fileManager.open("cut1_360.mp4");
    System.out.println("test2");
    ProcesadorVideoFutbol videoProcesador;
    try {
      videoProcesador = new ProcesadorVideoFutbol(file);
      videoProcesador.analizar();
    } catch (IOException error) {
      System.out.println(error.getMessage());
    }
  }

}
