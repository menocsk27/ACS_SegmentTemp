package Main;

import java.util.LinkedList;

import org.opencv.core.Mat;

public interface DistanceHistogramGenerator {

	
	public LinkedList<Double> generateDistanceArray(LinkedList<Mat> histCollection);
}
