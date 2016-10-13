/*
 * @author Daniel Troyo
 * 
 * @version 0.1.0
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
 * The Class HistogramProcessor. It deals with all the processing between MAT objets. These include
 * all the histogram processing and dealing with MAT objects which represent frames or channels of
 * frames. * @see <a href=
 * "http://docs.opencv.org/2.4.13/doc/tutorials/core/mat_the_basic_image_container/mat_the_basic_image_container.html?highlight=mat">
 * MAT Object - Official Doc</a>
 * 
 */
public class HistogramProcessor {

  /** The range of the histogram obtained from the normalized Hue channel of each frame. */
  private final MatOfFloat histRange = new MatOfFloat(0, 256);

  /**
   * The size or number of bins of the histogram obtained from the normalized Hue channel of each
   * frame.
   */
  private final MatOfInt histSize = new MatOfInt(256);

  /**
   * The number of channels of the histogram obtained from the normalized Hue channel of each frame.
   * In this case, only one for the hue channel.
   */
  private final MatOfInt histChannels = new MatOfInt(0);

  /** The max hue channel. */
  private final double maxHueChannel = 255;

  /** The max hue channel after split. */
  private final double maxHueChannelAfterSplit = 360;


  /**
   * Splits a frame passed as parameter into three channels and returns the first one of these,
   * being the hue channel MAT object.
   *
   * @see <a href=
   *      "http://docs.opencv.org/2.4.13/doc/tutorials/core/mat_the_basic_image_container/mat_the_basic_image_container.html?highlight=mat">
   *      MAT Object - Official Doc</a>
   * @param frameHSV The Mat object that represents the frame passed as parameter.
   * @return The MAT object that represents the HUE channel of the frame passed as parameter
   */
  private Mat obtainHchannel(Mat frameHSV) {
    LinkedList<Mat> channelsImage = new LinkedList<Mat>();
    Core.split(frameHSV, channelsImage);
    Mat capaH;
    capaH = channelsImage.get(0);
    return capaH;
  }

  /**
   * It returns the normalized [0-256] image or MAT OBJECT of the hue channel of a frame passed as
   * parameter.
   *
   * @param frameHSV the frame HSV
   * @return the MAT object that represents the normalized channel of the hue channel
   * @see <a href=
   *      "http://docs.opencv.org/2.4.13/doc/tutorials/core/mat_the_basic_image_container/mat_the_basic_image_container.html?highlight=mat">
   *      MAT Object - Official Doc</a>
   * 
   */
  private Mat obtainNormalizedhChannel(Mat frameHSV) {
    Mat capaHnormalized = this.obtainHchannel(frameHSV);

    // Se recorre la matriz en dos dimensiones, normalizándola.
    double valTemp;
    for (int i = 0; i < capaHnormalized.rows(); i++) {
      for (int j = 0; j < capaHnormalized.cols(); j++) {
        valTemp = capaHnormalized.get(i, j)[0];
        valTemp = valTemp / maxHueChannelAfterSplit; // Entre 360 -> queda [0,1]
        valTemp = valTemp * maxHueChannel; // Por 255 -> queda [0,255]
        capaHnormalized.put(i, j, valTemp); // Queda [0,1]
        // System.out.println(capaHnormalized.get(i, j)[0]);
      }
    }
    return capaHnormalized;
  }

  /**
   * It normalizes the histogram obtained from the hue channel and leaves it with [0,1] values. It
   * is supposed to received a histogram with values within the range [0-255[ This function only
   * receives these kind of histograms. Any other will be tre
   * 
   * @param normHueHist The histogram obtained from the normalized hue channel MAT Object
   * @param pixelNumber the pixel number
   * @return The normalized histogram with values within the range [0, 1]
   * @see <a href=
   *      "http://docs.opencv.org/2.4.13/doc/tutorials/core/mat_the_basic_image_container/mat_the_basic_image_container.html?highlight=mat">
   *      MAT Object - Official Doc</a>
   */
  private Mat normHistHueChannel(Mat normHueHist, double pixelNumber) {
    double valTemp;
    double valorSuma = 0;
    for (int i = 0; i < normHueHist.total(); i++) {
      valTemp = normHueHist.get(i, 0)[0];
      valTemp = valTemp / pixelNumber; // Entre número de pixeles de la imagen
      normHueHist.put(i, 0, valTemp); // Queda [0,1]
      valorSuma += normHueHist.get(i, 0)[0];
    }
    System.out.println("Suma Histograma normalizado:" + valorSuma);
    return normHueHist;
  }

  /**
   * Calculates the histogram of hue channel of a hsv image. It requires that the histograms passed
   * as parameters be in hsv format.
   *
   * @param image the image
   * @return Hue channel's histogram, normalized between [0, 256] range.
   */
  private Mat calculateHistoOfHueImage(Mat image) {

    Mat capaHnormalized = new Mat();

    // Capa h normalizada
    // capaHnormalized = this.obtainNormalizedhChannel(image);
    // Valores para el nuevo histograma a generar a partir de dicha capa
    Mat histogramH = new Mat();
    LinkedList<Mat> hValueframe = new LinkedList<Mat>();
    hValueframe.add(capaHnormalized);
    // Se genera el histograma
    Imgproc.calcHist(hValueframe, histChannels, new Mat(), histogramH, histSize, histRange, false);
    // this.drawHistogram(histogramH, "histograma.jpg", 300, 400);
    histogramH = normHistHueChannel(histogramH, capaHnormalized.total());
    return histogramH;
  }


  /**
   * Instantiates a new histogram processor.
   */
  public HistogramProcessor() {}


  /**
   * Calculates the hue channel's histogram of each frame passed as parameter.
   * 
   * @param images Collection of hsv frames of the video.
   * @return listOfHueHistograms Collection of the histogram of the hue channel of each frame.
   * @see <a href=
   *      "http://docs.opencv.org/2.4.13/doc/tutorials/core/mat_the_basic_image_container/mat_the_basic_image_container.html?highlight=mat">
   *      MAT Object - Official Doc</a>
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
   * Creates an image of a histogram represented as a MAT object. It does all the normalization and
   * linearization process for representing the histogram as an image.
   *
   * @param histogramH Histogram instance
   * @param name of the file, including the extension of the file. Let's say: "image.jpg" JPG, JPEG
   *        and PNG formats are preferred.
   * @param width The number of pixels in each row of the image of the histogram. Resolution.
   * @param height The number of rows of the image of the histogram, represented as rows of pixels.
   *        Resolution.
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
