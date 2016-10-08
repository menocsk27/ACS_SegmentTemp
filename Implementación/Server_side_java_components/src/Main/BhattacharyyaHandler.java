package Main;

import org.opencv.core.Mat;

import java.lang.Math;
import java.util.LinkedList;

public class BhattacharyyaHandler implements DistanceHistogramGenerator{
	
	private StadisticalCalculator calculator = new NumericalDataCalculator();
	
	public BhattacharyyaHandler(){}
	
	
	private double obtainBhattacharyyaCoef(Mat hist1, Mat hist2){
		//Por cada bin de los histogramas
		double coef = 0;
		for (int bin=0; bin < hist1.size().width; bin++){
			coef+= Math.sqrt(hist1.get(bin,0)[0] * hist2.get(bin,0)[0]);
		}
		return coef;
	}
	
	private double obtainNormalizationCoef(Mat hist1, Mat hist2){
		double coef = 0;
		double denom = 0; //denominador de la división
		double avgHist1;
		double avgHist2;
		
		LinkedList<Double> hist1Values = new LinkedList<Double>();
		LinkedList<Double> hist2Values = new LinkedList<Double>();
		for (int bin=0; bin < hist1.size().width; bin++){
			hist1Values.add(hist1.get(bin,0)[0]);
			hist2Values.add(hist2.get(bin,0)[0]);
		}
		avgHist1 = calculator.calculateAverage(hist1Values);
		avgHist2 = calculator.calculateAverage(hist2Values);
		//256 = M
		denom = Math.sqrt(avgHist1 * avgHist2 * Math.pow(256, 2));
		coef = 1/denom;
		
		return coef;
		
		
	}
	
	private double obtainBhattacharyyaDistance(Mat hist1, Mat hist2){
		double coefBhattacharyya;
		double coefNormalized;
		
		coefBhattacharyya = this.obtainBhattacharyyaCoef(hist1, hist2);
		coefNormalized = this.obtainNormalizationCoef(hist1, hist2);
		
		double distance  = 1 / Math.sqrt(1 - (coefNormalized * coefBhattacharyya));
		
		return distance;
	}
	
	@Override
	public LinkedList<Double> generateDistanceArray(LinkedList<Mat> histCollection){
		LinkedList<Double> distanceHist = new LinkedList<Double>();
		double distanceCycle;
		Mat histogram1;
		Mat histogram2;
		for (int i = 0; i < histCollection.size()-1; i++){
			histogram1 = histCollection.get(i);
			histogram2 = histCollection.get(i+1);
			
			distanceCycle = obtainBhattacharyyaDistance(histogram1, histogram2);
			
			distanceHist.add(distanceCycle);
		}
		
		return distanceHist;
	}
	
	

}
