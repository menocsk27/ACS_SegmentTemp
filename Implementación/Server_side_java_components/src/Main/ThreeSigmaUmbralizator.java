package Main;

import java.util.LinkedList;


public class ThreeSigmaUmbralizator implements UmbralizationProcessor {

	public ThreeSigmaUmbralizator(){}
	
	
	private boolean isCut(double avg, double stdDev, double distance){
		if (distance >= (avg + stdDev)){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public LinkedList<Boolean> obtainCuts(LinkedList<Double> distanceHistArray) {
		LinkedList<Boolean> isDistanceAtPosCut = new LinkedList<Boolean>();
		StadisticalCalculator calculator = new NumericalDataCalculator();
		
		double avg = calculator.calculateStdDeviation(distanceHistArray);
		double stdDev = calculator.calculateStdDeviation(distanceHistArray);
		
		for (int i = 0; i < distanceHistArray.size(); i++){
			if (this.isCut(avg, stdDev, distanceHistArray.get(i) ) ){
				isDistanceAtPosCut.add(true);
			}else{
				isDistanceAtPosCut.add(false);
			}
		}
		return isDistanceAtPosCut;
	}

}
