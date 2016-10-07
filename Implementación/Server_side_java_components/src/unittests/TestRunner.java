package unittests;

import main.MainProcessor;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
  /**Main function for testing.
   * @param args Default main arguments.
   */
  public static void main(String[] args) {
    Result result = JUnitCore.runClasses(MainProcessor.class);
    for (Failure failure : result.getFailures()) {
      System.out.println(failure.toString());
    }
    System.out.println(result.wasSuccessful());
  }
}