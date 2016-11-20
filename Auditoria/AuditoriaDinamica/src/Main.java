import futbol.FutbolFileManager;
import futbol.ProcesadorVideoFutbol;
import futbol.AbstractFrame;
import futbol.FootballVideo;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.io.File;
import java.io.IOException;

public class Main {

  /**
   * Funci�n principal para correr el programa.
   * 
   * @param args argumentos enviados por consola.
   */
  public static void main(String[] args) {
    /*
    //Main normal
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    FutbolFileManager fileManager = new FutbolFileManager();
    File file = fileManager.open("cut1_360.mp4");
    ProcesadorVideoFutbol videoProcesador;
    try {
      videoProcesador = new ProcesadorVideoFutbol(file);
      videoProcesador.analizar();
    } catch (IOException error) {
      System.out.println(error.getMessage());
    }*/
    
    /*
    //Integracion1
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    FutbolFileManager fileManager = new FutbolFileManager();
    File file = fileManager.open("cut1_360.mp4");
    FootballVideo video = new FootballVideo(file);
    AbstractFrame frame = video.obtenerCuadro();
    System.out.println("datos del frame:\nancho: " 
    + frame.getAncho() + "\nalto: " + frame.getAlto() + "\ntipo: " + frame.getTipo()
    + "\ndatos: ");
    for(byte dato: frame.getDatos()){
      System.out.print(" " + dato + " ");
    }
    */
    
    /*
    //Integarcion2
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    byte[] data = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    Mat mat = new Mat(3,3,1);
    mat.put(0, 0, data);
    
    for(int i = 0; i < 3; i++){
      for(int j = 0; j < 3; j++){
        System.out.print(mat.get(i, j)[0] + " ");
      }
    }
    */
    
    
    //Integracion3
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    FutbolFileManager manager = new FutbolFileManager();
    File file = manager.open("cut1_360.mp4");
    FootballVideo fv = new FootballVideo(file);
    System.out.println(fv.getAlto() + " " + fv.getAncho() + " " + fv.getCantFrames() + " " + fv.getFps());
  }

}
