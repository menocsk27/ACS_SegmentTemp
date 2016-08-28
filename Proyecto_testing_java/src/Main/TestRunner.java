package Main;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.*;
public class TestRunner {
   public static void main(String[] args) {
      Result result = JUnitCore.runClasses(HelloCV.class);
    
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
    
      System.out.println(result.wasSuccessful());
   }
}