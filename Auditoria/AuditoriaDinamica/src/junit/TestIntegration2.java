package junit;

import futbol.*;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;

public class TestIntegration2 {

  @Test
  public void test() {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    
    FutbolFileManager fileManager = new FutbolFileManager();
    File file = fileManager.open("cut1_360.mp4");
     
    byte[] data = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    AbstractFrame frame = new FutbolFrame(data, 3, 3, 1);
    ProcesadorVideoFutbol proc;
    Mat mat, mat2;
    mat = new Mat(3,3,1);
    mat.put(0, 0, data);
    try{
      proc = new ProcesadorVideoFutbol(file);
      mat2 = proc.convertirMat(frame);/////
      for(int i = 0; i < 3; i++){
        for(int j = 0; j < 3; j++){
          assertEquals(mat.get(i, j)[0], mat2.get(i, j)[0], 0);
        }
      }
    }catch(Exception e){
      e.printStackTrace();
    }
  }

}
