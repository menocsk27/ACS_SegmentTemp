package Main;

import java.util.LinkedList;

import org.opencv.core.Mat;

public interface UmbralizationProcessor {


	public LinkedList<Boolean> obtainCuts(LinkedList<Double> distanceHistArray);


}
