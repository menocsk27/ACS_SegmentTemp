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
  private static AbstractProcesadorVideo videoProcesador;
  private static int tiempoProcesamiento;
  private static int framesProcesados;
  private static AbstractVideo video;
  private static AbstractFileManager fileManager;
  private static AbstractProcesadorImagenes procesadorImagenes;
  
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
  }

  @Before
  public  void beforeTest(){
    FutbolFileManager fileManager = new FutbolFileManager();
    File file = fileManager.open("cut1_360.mp4");
    tiempoProcesamiento = framesProcesados = 0;
    fileManager = new FutbolFileManager();
    procesadorImagenes = new ProcesadorImagenesFutbol();
    video = new FootballVideo(file);
  }

 


  /**
   * Parameters to test. These parameters are different sets of decimal values and their expected
   * average and standard deviation, respectively.
   *
   * @return the collection
   */
  @Parameterized.Parameters
  public static Collection parametersToTest() {
    LinkedList<Double> parametro1 = new LinkedList<Double>();
    int[] paramTest1 = {4, 2, 5, 7, 32, 23, 11, 5, 16, 23};
    parametro1 = createLinkedlist(paramTest1);
    double stdDevC = 9.693296;
    double avgC = 12.8;

    LinkedList<Double> parametro2 = new LinkedList<Double>();
    int[] paramTest2 = {4, 2, 5, 7, 12, 2, 11, 7, 16, 23, 30};
    parametro2 = createLinkedlist(paramTest2);
    double stdDevD = 8.5792369;
    double avgD = 10.81818182;

    LinkedList<Double> parametro3 = new LinkedList<Double>();
    int[] paramTest3 = {1, 21, 5, 17, 41, 21, 5, 5, 10, 19, 4};
    parametro3 = createLinkedlist(paramTest3);
    double stdDevE = 11.2279168;
    double avgE = 13.54545455;

    return Arrays.asList(new Object[][] {{parametro1, stdDevC, avgC}, {parametro2, stdDevD, avgD},
        {parametro3, stdDevE, avgE}});
  }
  
  
  @Test
  public  void testOne(){
    AbstractFrame imagen;
    int counter = 0;
    int cantFrames = video.getCantFrames();
    while (counter < cantFrames) {
      imagen = video.obtenerCuadro();
      if (((ProcesadorVideoFutbol) videoProcesador).esValida(imagen)) {
        Mat imagenTemp = ((ProcesadorImagenesFutbol) procesadorImagenes).convertirMat(imagen);
        imagenTemp = ((ProcesadorImagenesFutbol) procesadorImagenes).convertirHsv(imagenTemp);
        imagenTemp=((ProcesadorImagenesFutbol) procesadorImagenes).obtenerHue(imagenTemp);
        assertEquals(imagenTemp.channels(), 1);
      }
 
      counter++;
    }
  }
  
}


  




