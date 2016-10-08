package Main;

import java.util.LinkedList;

public class NumericalDataCalculator implements StadisticalCalculator{

	
	private double calculateVariance(LinkedList<Double> set){
		double variance = 0;
		double avg = this.calculateAverage(set);
		
		for (int i = 0; i < set.size(); i++){
			variance += Math.pow(set.get(i)-avg, 2);
		}
		variance = variance / set.size();
		return variance;
	}
	
	public NumericalDataCalculator(){}
	
	@Override
	public  double calculateAverage(LinkedList<Double> set) {
		double average = 0;
		for (int i=0; i < set.size(); i++){
			average+= (double) set.get(i);
		}
		average = average / set.size();
		return average;
	}

	@Override
	public double calculateStdDeviation(LinkedList<Double> set) {
		double stdDev;
		double variance = this.calculateVariance(set);
		stdDev = Math.sqrt(variance);
		return stdDev;
	}

}
