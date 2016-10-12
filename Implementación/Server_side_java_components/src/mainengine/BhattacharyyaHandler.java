/*
 * @author Daniel Troyo
 */
package mainengine;

import java.util.LinkedList;

import org.opencv.core.Mat;

// TODO: Auto-generated Javadoc
/**
 * The Class BhattacharyyaHandler.
 */
public class BhattacharyyaHandler implements DistanceHistogramGenerator {

  /** The calculator. */
  private StadisticalCalculator calculator = new NumericalDataCalculator();

  /**
   * Instantiates a new bhattacharyya handler.
   */
  public BhattacharyyaHandler() {}


  /**
   * Obtain bhattacharyya's coefficient.
   *
   * @param hist1 First histogram, represented as a MAT object.
   * @param hist2 Second histogram, represented as a MAT object.
   * @return the double
   */
  private double obtainBhattacharyyaCoef(Mat hist1, Mat hist2) {
    // Por cada bin de los histogramas
    double coef = 0;
    for (int bin = 0; bin < 256; bin++) {
      // System.out.println("Hist 1 bin: "+hist1.get(bin, 0)[0] );
      coef += Math.sqrt(hist1.get(bin, 0)[0] * hist2.get(bin, 0)[0]);
    }
    return coef;
  }

  /**
   * Obtain normalization coefficient for the bhattcharyya's distance calculation.
   *
   * @param hist1 First histogram, represented as a MAT object.
   * @param hist2 Second histogram, represented as a MAT object.
   * @return the double
   */
  private double obtainNormalizationCoef(Mat hist1, Mat hist2) {

    LinkedList<Double> hist1Values = new LinkedList<Double>();
    LinkedList<Double> hist2Values = new LinkedList<Double>();
    for (int bin = 0; bin < hist1.total(); bin++) {
      hist1Values.add(hist1.get(bin, 0)[0]);
    }
    for (int bin = 0; bin < 256; bin++) {
      hist2Values.add(hist1.get(bin, 0)[0]);
    }

    double denom; // denominador de la división
    double avgHist1;
    double avgHist2;

    avgHist1 = calculator.calculateAverage(hist1Values);
    avgHist2 = calculator.calculateAverage(hist2Values);
    // 256 = M
    denom = Math.sqrt(avgHist1 * avgHist2 * Math.pow(256, 2));
    double coef = 1 / denom;

    return coef;


  }

  /**
   * Function that returns the bhattacharyya distance between two histograms.
   *
   * @param hist1 First histogram, represented as a MAT object.
   * @param hist2 Second histogram, represented as a MAT object.
   * @return The distance, must be a value in the range [0,1]. It doesn't work if it is in another
   *         range,
   */
  private double obtainBhattacharyyaDistance(Mat hist1, Mat hist2) {
    double coefBhattacharyya;
    double coefNormalized;

    coefBhattacharyya = this.obtainBhattacharyyaCoef(hist1, hist2);
    coefNormalized = this.obtainNormalizationCoef(hist1, hist2);

    double distance = 1 / Math.sqrt(1 - (coefNormalized * coefBhattacharyya));
    // double distance = Math.sqrt(1 - (coefBhattacharyya * coefNormalized));
    return distance;
  }

  /*
   * (non-Javadoc)
   * 
   * @see mainengine.DistanceHistogramGenerator#generateDistanceArray(java.util.LinkedList)
   */
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
