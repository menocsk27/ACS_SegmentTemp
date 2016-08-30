package Unit_Tests;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import Main.MainProcessor;

import org.junit.runner.*;
public class TestRunner {
   public static void main(String[] args) {
      Result result = JUnitCore.runClasses(MainProcessor.class);
      
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
    
      System.out.println(result.wasSuccessful());
   }
}