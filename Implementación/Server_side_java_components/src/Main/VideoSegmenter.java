package Main;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
//import org.opencv.highgui.Highgui;

import java.util.LinkedList;  

public class VideoSegmenter {
  //private Imgproc imgProcessor = new Imgproc();
  private int hsvScale = Imgproc.COLOR_BGR2HSV;
  
  public VideoSegmenter() {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);  
  }
  
  /** Function that creates HSV files from the frames of the video sent.
   * @param filename String directing to the location of the video file.
   * @return List of all the HSV files made from all the frames of the video.
   */
  public LinkedList<Mat> split_video_to_frames_hsv(String filename) {
    VideoCapture capture;
    LinkedList<Mat> images = new LinkedList();
    Mat image = new Mat();
    capture = new VideoCapture(filename);
    if (capture.isOpened() == false) {
      System.out.println("Video no leido");  
    }
    int counter = 0;
    while (capture.read(image)) {
      Mat hsvImage = new Mat();
      //Mat newrgbImage = new Mat();
      Imgproc.cvtColor(image, hsvImage, hsvScale); //Transformar a escala hsv
      images.add(hsvImage);  
      counter++;
    }
    capture.release(); 
    return images;
  }
}