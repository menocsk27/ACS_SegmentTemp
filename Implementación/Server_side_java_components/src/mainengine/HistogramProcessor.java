/*
 * @author Daniel Troyo
 */
package mainengine;

import java.util.LinkedList;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

// TODO: Auto-generated Javadoc
/**
 * The Class HistogramProcessor.
 */
public class HistogramProcessor {

  /** The histogram range. */
  private final MatOfFloat histRange = new MatOfFloat(0, 256);

  /** The histogram size or number of bins. */
  private final MatOfInt histSize = new MatOfInt(256);

  /** The histogram channels, only one for hue. */
  private final MatOfInt histChannels = new MatOfInt(0);

  /**
   * Instantiates a new histogram processor.
   */
  public HistogramProcessor() {}


  /**
   * Calculates the hue channel's histogram of each frame passed as parameter.
   * 
   * @param images Collection of hsv frames of the video.
   * @return listOfHueHistograms Collection of the histogram of the hue channel of each frame.
   */

  public LinkedList<Mat> calculateHistoOfHueVideo(LinkedList<Mat> images) {
    LinkedList<Mat> listOfHueHistograms = new LinkedList<Mat>();
    for (int i = 0; i < images.size(); i++) {
      Mat histograma = calculateHistoOfHueImage(images.get(i));
      listOfHueHistograms.add(histograma);
    }
    // Highgui.imwrite("histograma_normalizado_hue.jpg", listOfHueHistograms.get(10));
    return listOfHueHistograms;
  }



  /**
   * Calculates the histogram of hue channel of a hsv image.
   *
   * @param HSV image
   * @return Hue channel's histogram
   */
  private Mat calculateHistoOfHueImage(Mat image) {

    Mat capaH = new Mat();
    LinkedList<Mat> channelsImage = new LinkedList();

    // Se obtiene la capa del Hue del frame HSV
    Core.split(image, channelsImage);
    capaH = channelsImage.get(0);
    Core.normalize(capaH, capaH, 0, 256, Core.NORM_MINMAX, -1, new Mat());

    // Valores para el nuevo histograma a generar a partir de dicha capa
    Mat histogramH = new Mat();

    LinkedList<Mat> hValueframe = new LinkedList<Mat>();
    hValueframe.add(capaH);
    // Se genera el histograma
    Imgproc.calcHist(hValueframe, histChannels, new Mat(), histogramH, histSize, histRange, false);
    // Se normaliza por el número de pixeles (resolución)
    // Core.normalize(histogramH, histogramH, 0, histogramH.rows() * histogramH.cols(),Core.NORM_L2,
    // -1, new Mat());

    for (int i = 0; i < histogramH.total(); i++) {
      histogramH.put(i, 0, histogramH.get(i, 0)[0] / capaH.total());
    }

    return histogramH;
  }



  /**
   * Creates an image of a histogram represented as a MAT object.
   *
   * @param histogramH Histogram instance
   * @param name of the file, including the extension of the file.
   * @param width pixels of the image
   * @param height pixels of the image
   */
  public void drawHistogram(Mat histogramH, String name, int width, int height) {
    int counter;
    Mat histogramHimage = new Mat(width, height, CvType.CV_8UC3, new Scalar(0, 0, 0));
    int binW = (int) Math.round(width / histSize.get(0, 0)[0]);
    Core.normalize(histogramH, histogramH, 0, histogramHimage.rows(), Core.NORM_MINMAX, -1,
        new Mat());
    for (counter = 1; counter < histSize.get(0, 0)[0]; counter++) {
      // B component or gray image
      Core.line(histogramHimage,
          new Point(binW * (counter - 1), height - Math.round(histogramH.get(counter - 1, 0)[0])),
          new Point(binW * (counter), height - Math.round(histogramH.get(counter, 0)[0])),
          new Scalar(255, 0, 0), 2, 8, 0);
    }
    Highgui.imwrite(name, histogramHimage);
  }
}
