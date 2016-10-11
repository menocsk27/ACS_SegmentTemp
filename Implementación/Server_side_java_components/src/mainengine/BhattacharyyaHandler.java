/*
 * @author Daniel Troyo
 */
package mainengine;

import java.util.LinkedList;

import org.opencv.core.Mat;

public class BhattacharyyaHandler implements DistanceHistogramGenerator {

  private StadisticalCalculator calculator = new NumericalDataCalculator();

  public BhattacharyyaHandler() {}


  private double obtainBhattacharyyaCoef(Mat hist1, Mat hist2) {
    // Por cada bin de los histogramas
    double coef = 0;
    for (int bin = 0; bin < 256; bin++) {
      // System.out.println("Hist 1 bin: "+hist1.get(bin, 0)[0] );
      coef += Math.sqrt(hist1.get(bin, 0)[0] * hist2.get(bin, 0)[0]);
    }
    return coef;
  }

  private double obtainNormalizationCoef(Mat hist1, Mat hist2) {

    LinkedList<Double> hist1Values = new LinkedList<Double>();
    LinkedList<Double> hist2Values = new LinkedList<Double>();
    for (int bin = 0; bin < hist1.total(); bin++) {
      hist1Values.add(hist1.get(bin, 0)[0]);
    }
    for (int bin = 0; bin < 256; bin++) {
      hist2Values.add(hist1.get(bin, 0)[0]);
    }

    double denom; // denominador de la divisi�n
    double avgHist1;
    double avgHist2;

    avgHist1 = calculator.calculateAverage(hist1Values);
    avgHist2 = calculator.calculateAverage(hist2Values);
    // 256 = M
    denom = Math.sqrt(avgHist1 * avgHist2 * Math.pow(256, 2));
    double coef = 1 / denom;

    return coef;


  }

  private double obtainBhattacharyyaDistance(Mat hist1, Mat hist2) {
    double coefBhattacharyya;
    double coefNormalized;

    coefBhattacharyya = this.obtainBhattacharyyaCoef(hist1, hist2);
    coefNormalized = this.obtainNormalizationCoef(hist1, hist2);

    // double distance = 1 / Math.sqrt(1 - (coefNormalized * coefBhattacharyya));
    double distance = Math.sqrt(1 - (coefBhattacharyya * coefNormalized));
    return distance;
  }

  @Override
  public LinkedList<Double> generateDistanceArray(LinkedList<Mat> histCollection) {
    LinkedList<Double> distanceHist = new LinkedList<Double>();
    double distanceCycle;
    Mat histogram1;
    Mat histogram2;
    for (int i = 0; i < histCollection.size() - 1; i++) {
      histogram1 = histCollection.get(i);
      histogram2 = histCollection.get(i + 1);

      distanceCycle = obtainBhattacharyyaDistance(histogram1, histogram2);

      distanceHist.add(distanceCycle);
    }

    return distanceHist;
  }



}
