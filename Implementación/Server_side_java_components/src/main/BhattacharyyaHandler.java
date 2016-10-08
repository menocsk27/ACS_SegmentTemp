package main;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import java.lang.Math;
import java.util.LinkedList;

public class BhattacharyyaHandler {
	
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
		
		LinkedList<Double> hist1Values = new LinkedList();
		LinkedList<Double> hist2Values = new LinkedList();
		for (int bin=0; bin < hist1.size().width; bin++){
			hist1Values.add(hist1.get(bin,0)[0]);
			hist2Values.add(hist2.get(bin,0)[0]);
		}
		avgHist1 = calculator.calculateAverage(hist1Values);
		avgHist2 = calculator.calculateAverage(hist2Values);

		denom = Math.sqrt(avgHist1 * avgHist2 * Math.pow(256, 2));
		coef = 1/denom;
		
		return coef;
		
		
	}
	
	private double obtainBhattacharyyaDistance(Mat hist1, Mat hist2){
		
	}
	
	public LinkedList<double> generateDistanceArray(){
		
	}
	
	

}
