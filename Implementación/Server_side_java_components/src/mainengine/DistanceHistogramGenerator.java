package mainengine;

import org.opencv.core.Mat;

import java.util.LinkedList;



public interface DistanceHistogramGenerator {


  public LinkedList<Double> generateDistanceArray(LinkedList<Mat> histCollection);
}
