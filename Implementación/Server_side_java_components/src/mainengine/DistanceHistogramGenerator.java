/*
 * @author Daniel Troyo
 * 
 * @version 0.1.0
 */
package mainengine;

import java.util.LinkedList;

import org.opencv.core.Mat;



// TODO: Auto-generated Javadoc
/**
 * The Interface DistanceHistogramGenerator.
 */
public interface DistanceHistogramGenerator {


  /**
   * Generate distance array from a collection of histograms, which in this context should represent
   * a frame each histogram. If the histogram collection's size is n, the distance collection's size
   * should be n-1. The distance calculation can be implemented with different methods, such as
   * Bhattacharyya. The histograms MUST HAVE the same number of bins and values within the range
   * [0,1]
   *
   * @param histCollection Collection of histograms, which represent in this context a collection of
   *        histograms of the h channel of hsv frames.
   * @return the linked list, or collection of distance between each histogram.
   * 
   * @see <a href=
   *      "http://docs.opencv.org/2.4.13/doc/tutorials/imgproc/histograms/histogram_comparison/histogram_comparison.html?highlight=histograms">
   *      Comparación de histogramas</a>
   */
  public LinkedList<Double> generateDistanceArray(LinkedList<Mat> histCollection);
}
