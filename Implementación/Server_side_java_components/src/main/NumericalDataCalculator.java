package main;

import java.util.LinkedList;

public class NumericalDataCalculator implements main.StadisticalCalculator{

	
	public NumericalDataCalculator(){}
	
	@Override
	public double calculateAverage(LinkedList<Double> set) {
		double average = 0;
		for (int i=0; i < set.size(); i++){
			average+= (double) set.get(i);
		}
		average = average / set.size();
		return average;
	}

	@Override
	public double calculateStdDeviation(LinkedList<Double> set) {
		// TODO Auto-generated method stub
		return 0;
	}

}
