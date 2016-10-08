package main;

import java.util.LinkedList;

public interface StadisticalCalculator {

	
	public  double calculateAverage(LinkedList<Double> set);
	public double calculateStdDeviation(LinkedList<Double> set);
}
