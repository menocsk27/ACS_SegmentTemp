package junit;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.Parameterized;
import static org.junit.Assert.*;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import futbol.AbstractFileManager;
import futbol.AbstractFrame;
import futbol.AbstractProcesadorImagenes;
import futbol.AbstractProcesadorVideo;
import futbol.AbstractVideo;
import futbol.FootballVideo;
import futbol.FutbolFileManager;
import futbol.ProcesadorImagenesFutbol;
import futbol.ProcesadorVideoFutbol;

public class TestUnit1 {

  
  
  /**
   * This method creates a LinkedList instance of the Collection objects defined in the function
   * that defines the parameters.
   *
   * @param listValues The Collection object containing the decimal values of the sets to test.
   * @return A Linked List object containing the decimal values of the sets to test.
   */
  
  private static LinkedList<Double> parameter;
  private static ProcesadorVideoFutbol videoProcesador;
  private static int tiempoProcesamiento;
  private static int framesProcesados;
  private static AbstractVideo video;
  private static AbstractFileManager fileManager;
  private static ProcesadorImagenesFutbol procesadorImagenes;
  
  private static LinkedList<Double> createLinkedlist(int[] listValues) {
    LinkedList<Double> list = new LinkedList<Double>();
    for (int i = 0; i < listValues.length; i++) {
      list.add((double) listValues[i]);
    }
    return list;
  }

  @BeforeClass
  public static void beforeTests(){
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    FutbolFileManager TestfileManager = new FutbolFileManager();
    
    TestUnit1.tiempoProcesamiento = TestUnit1.framesProcesados = 0;
    TestUnit1.fileManager = new FutbolFileManager();
    
    TestUnit1.procesadorImagenes = new ProcesadorImagenesFutbol();
    
  }

  @Before
  public  void beforeTest(){
    File file = TestUnit1.fileManager.open("cut1_360.mp4");
    TestUnit1.video = new FootballVideo(file);
    TestUnit1.videoProcesador = new ProcesadorVideoFutbol(file);
  }

 



  
  @Test
  public  void testobtentionOfHueChannel(){
    /*Se asegura que la obtención del canal de tono de cada imagen sea correcta, validando que sólo se obtenga una 
     * matriz de un canal y que el valor de dicho canal corresponda al del tono (Hue).*/
    AbstractFrame imagen;
    int counter = 0;
    int cantFrames = video.getCantFrames();
    while (counter < cantFrames) {
      imagen = video.obtenerCuadro();
      //System.out.println(imagen.getAncho());
      videoProcesador.convertirMat(imagen);
      if ( TestUnit1.videoProcesador.esValida(imagen)) {
        Mat imagenTemp = TestUnit1.procesadorImagenes.convertirMat(imagen);
        imagenTemp =  TestUnit1.procesadorImagenes.convertirHsv(imagenTemp);
        double hueValue;
        Mat imagenTemp1= TestUnit1.procesadorImagenes.obtenerHue(imagenTemp);
        assertEquals(imagenTemp1.channels(), 1);
        for (int i = 0; i < imagenTemp.rows(); i++){
          for (int j = 0; j < imagenTemp.cols(); j++){
            hueValue= imagenTemp.get(i, j)[0];
            assertEquals(imagenTemp1.get(i, j)[0], hueValue, 0.001);
          }
        }
      }
 
      counter++;
    }
  }
  
  @Test
  public  void testNormalization(){
    /*Realiza todos los procesos hasta llegar a la normalización de la imagen. Luego se asegura que los rangos
     * de los pixeles sean válidos.*/
    AbstractFrame imagen;
    int counter = 0;
    int cantFrames = video.getCantFrames();
    while (counter < cantFrames) {
      imagen = video.obtenerCuadro();
      if (((ProcesadorVideoFutbol) TestUnit1.videoProcesador).esValida(imagen)) {
        Mat imagenTemp = ((ProcesadorImagenesFutbol) TestUnit1.procesadorImagenes).convertirMat(imagen);
        imagenTemp = ((ProcesadorImagenesFutbol) TestUnit1.procesadorImagenes).convertirHsv(imagenTemp);
        imagenTemp=((ProcesadorImagenesFutbol) TestUnit1.procesadorImagenes).obtenerHue(imagenTemp);
        imagenTemp=((ProcesadorImagenesFutbol) TestUnit1.procesadorImagenes).normalizar(imagenTemp);
        for (int i = 0; i < imagenTemp.rows(); i++){
          for (int j = 0; j < imagenTemp.cols(); j++){
            assertTrue(imagenTemp.get(i, j)[0] < 260.0);
            assertTrue(imagenTemp.get(i, j)[0] >= 0.0);
          }
        }
       
      }
 
      counter++;
    }
  }
  
  @Test
  public  void testobtentionMask(){
    /*Realiza todos los procesos necesarios hasta llegar al punto en donde
     * la obtención de la máscara de la imagen sucede. y luego se asegura que los 
     * valores de los pixeles sean 0 o 255.*/
    AbstractFrame imagen;
    int counter = 0;
    int cantFrames = video.getCantFrames();
    while (counter < cantFrames) {
      imagen = video.obtenerCuadro();
      if (((ProcesadorVideoFutbol) TestUnit1.videoProcesador).esValida(imagen)) {
        Mat imagenTemp = ((ProcesadorImagenesFutbol) TestUnit1.procesadorImagenes).convertirMat(imagen);
        imagenTemp = ((ProcesadorImagenesFutbol) TestUnit1.procesadorImagenes).convertirHsv(imagenTemp);
        imagenTemp=((ProcesadorImagenesFutbol) TestUnit1.procesadorImagenes).obtenerHue(imagenTemp);
        imagenTemp=((ProcesadorImagenesFutbol) TestUnit1.procesadorImagenes).normalizar(imagenTemp);
        imagenTemp=((ProcesadorImagenesFutbol) TestUnit1.procesadorImagenes).obtenerMascara(imagenTemp, 30);
        for (int i = 0; i < imagenTemp.rows(); i++){
          for (int j = 0; j < imagenTemp.cols(); j++){
            assertTrue(imagenTemp.get(i, j)[0] == 0.0 || imagenTemp.get(i, j)[0] == 255.0);
          }
        }
       
      }
 
      counter++;
    }
  }
  
  @Test
  public  void testUmbralizationImage(){
    /*Realiza todos los procesos necesarios hasta llegar al punto en donde
     * la umbralización es realizada. Se asegura de que los valores de la imagen
     * se umbralicen de 0 a 255*/
    AbstractFrame imagen;
    int counter = 0;
    int cantFrames = video.getCantFrames();
    while (counter < cantFrames) {
      imagen = video.obtenerCuadro();
      if (((ProcesadorVideoFutbol) TestUnit1.videoProcesador).esValida(imagen)) {
        Mat imagenTemp = ((ProcesadorImagenesFutbol) TestUnit1.procesadorImagenes).convertirMat(imagen);
        imagenTemp = ((ProcesadorImagenesFutbol) TestUnit1.procesadorImagenes).convertirHsv(imagenTemp);
        imagenTemp=((ProcesadorImagenesFutbol) TestUnit1.procesadorImagenes).obtenerHue(imagenTemp);
        imagenTemp=((ProcesadorImagenesFutbol) TestUnit1.procesadorImagenes).normalizar(imagenTemp);
        imagenTemp=((ProcesadorImagenesFutbol) TestUnit1.procesadorImagenes).obtenerVarianza(imagenTemp, 10);
        imagenTemp=((ProcesadorImagenesFutbol) TestUnit1.procesadorImagenes).umbralizarImagen(imagenTemp);
        for (int i = 0; i < imagenTemp.rows(); i++){
          for (int j = 0; j < imagenTemp.cols(); j++){
            assertTrue(imagenTemp.get(i, j)[0] == 0.0 || imagenTemp.get(i, j)[0] == 255.0);
          }
        }
       
      }
 
      counter++;
    }
  }
  
}


  




