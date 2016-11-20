package junit;

import futbol.*;

import static org.junit.Assert.*;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;

public class TestIntegration4 {

  @Test
  public void test() {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    
    ProcesadorImagenesFutbol proc;
    AbstractFrame frame;
    
    byte[] data = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    Mat mat = new Mat(3,3,1);
    mat.put(0, 0, data);
    
    try{
      proc = new ProcesadorImagenesFutbol();
      frame = proc.convertirAbstractFrame(mat);
      
      assertEquals(frame.getAlto(), 3);
      assertEquals(frame.getAncho(), 3);
      assertEquals(frame.getTipo(), 1);
      for(int i = 0; i < 9; i++){
        assertEquals(frame.getDatos()[i], data[i]);
      }
    }catch(Exception e){
      e.printStackTrace();
    }
  }

}
