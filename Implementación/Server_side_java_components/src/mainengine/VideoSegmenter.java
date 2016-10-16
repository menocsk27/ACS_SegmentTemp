/*
 * @author Daniel Troyo
 * 
 * @version 0.1.0
 */
package mainengine;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoWriter;
import org.opencv.videoio.Videoio;


// TODO: Auto-generated Javadoc
/**
 * The Class VideoSegmenter.
 */
public class VideoSegmenter {

  /** The hsv scale. */
  private final int hsvScale = Imgproc.COLOR_BGR2HSV;

  /** The width video code. */
  private static int widthVideoCode = Videoio.CV_CAP_PROP_FRAME_WIDTH;

  /** The heigh video code. */
  private static int heighVideoCode = Videoio.CV_CAP_PROP_FRAME_HEIGHT;

  /** The fps video code. */
  private static int fpsVideoCode = Videoio.CAP_PROP_FPS;

  /** The fourcc video code. */
  private static int fourccVideoCode = Videoio.CAP_PROP_FOURCC;

  /** The width last video read. */
  private static double widthLastVideoRead;

  /** The height last video read. */
  private static double heightLastVideoRead;

  /** The fps last video read. */
  private static double fpsLastVideoRead;

  /** The four CC video read. */
  private static double fourCCVideoRead;



  /**
   * Function that converts a MAT object representing a BGR image directly obtained from the file to
   * a MAT object representing an hsv image.
   * 
   * @param originalImage the original image
   * @return List of all the HSV files made from all the frames of the video.
   */
  private Mat obtainHsvimage(Mat originalImage) {
    Mat hsvImage = new Mat();
    Imgproc.cvtColor(originalImage, hsvImage, hsvScale); // Transformar a escala hsv
    return hsvImage;
  }

  /**
   * Set the static video properties of the video captured. Due to the creation of the video files
   * representing the scenes is inmediately after the reading, the properties shall be the same.
   * These properties are stored as static values of the current class.
   * 
   * @param reader The reader of the video sent to the system to be segmented.
   */
  private void setVideoproperties(VideoCapture reader) {
    widthLastVideoRead = reader.get(widthVideoCode);
    heightLastVideoRead = reader.get(heighVideoCode);
    fpsLastVideoRead = reader.get(fpsVideoCode);
    fourCCVideoRead = reader.get(fourccVideoCode);

    if (fpsLastVideoRead < 1) {
      fpsLastVideoRead = 24;
    }
  }

  /**
   * Instantiates a new video segmenter.
   */
  public VideoSegmenter() {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
  }

  /**
   * Function that obtains hsv frames represented as MAT Objects from the video sent to it as
   * parameter.
   *
   * @param filename String directing to the location of the video file.
   * @return List of all the HSV files made from all the frames of the video.
   * @throws IOException En caso que el formato de video no sea el correcto.
   */
  public LinkedList<Mat> split_video_to_frames_hsv(String filename) throws IOException {
    if (!filename.contains(".mp4") && !filename.contains(".avi")) {
      throw new IOException("Archivo de video debe ser mp4 o avi.");
    }
    VideoCapture capture;
    LinkedList<Mat> images = new LinkedList<Mat>();
    Mat image = new Mat();
    capture = new VideoCapture(filename);
    if (capture.isOpened() == false) {
      System.out.println("Video no leido");
    }
    LinkedList<Mat> escena = new LinkedList<Mat>();

    while (capture.read(image)) {
      images.add(obtainHsvimage(image));
    }

    setVideoproperties(capture);
    capture.release();


    return images;
  }

  /**
   * Creates the a video file from a set of frames. It creates the file in the root folder of the
   * project.
   *
   * @param frames Set of frames/MAT Objects.
   * @param filename The name of the file, including its extension (.avi preferred).
   */
  public void createVideofromframes(LinkedList<Mat> frames, String filename) {
    VideoWriter writer;
    Highgui.imwrite("imagen.jpg", frames.get(0));
    System.out.println("Frame size: " + frames.get(0).width() + frames.get(0).height());
    System.loadLibrary("opencv_java310");
    VideoWriter.fourcc('I', 'Y', 'U', 'V');
    Size resolution =
        new Size(VideoSegmenter.widthLastVideoRead, VideoSegmenter.heightLastVideoRead);
    writer = new VideoWriter(filename, -1, VideoSegmenter.fpsLastVideoRead, resolution, true);

    int counterFrames = 0;
    while (writer.isOpened() && counterFrames < frames.size()) {
      writer.write(frames.get(counterFrames));
      counterFrames++;
    }
    writer.release();
  }

  /**
   * Obtains the array of scenes (array of frames) based on the position of each cut.
   * 
   * @param frames The total array of frames originally obtained at the beginning of the program.
   * @param cutsPos It has len = frames.len() -1 . Array of cuts between frame t and frame t+1.
   * @return Array of scenes (array of frames).
   */
  private LinkedList<LinkedList<Mat>> obtainScenes(LinkedList<Mat> frames,
      LinkedList<Boolean> cutsPos) {
    LinkedList<LinkedList<Mat>> scenesSet = new LinkedList<LinkedList<Mat>>();
    LinkedList<Mat> firstScene = new LinkedList<Mat>();
    firstScene.add(frames.getFirst()); // Primera escena con el primer frame
    LinkedList<Mat> currentScene = firstScene;
    for (int i = 0; i <= cutsPos.size(); i++) {
      if (i == cutsPos.size()) { // Último frame
        currentScene.add(frames.get(i));
        scenesSet.add(currentScene);
        break; // Sale del ciclo
      }
      if (cutsPos.get(i) == true) { // Hay corte
        currentScene.add(frames.get(i));
        scenesSet.add(currentScene);
        currentScene = new LinkedList<Mat>(); // Nueva escena
      } else {
        currentScene.add(frames.get(i));
      }
    }
    return scenesSet;
  }



  /**
   * Obtains the array of scenes (array of frames) based on the position of each cut.
   * 
   * @param frames The total array of frames originally obtained at the beginning of the program.
   * @param cutsPos It has len = frames.len() -1 . Array of cuts between frame t and frame t+1.
   * @return Array of scenes (array of frames).
   */
  public LinkedList<String> createScenes(LinkedList<Mat> frames, LinkedList<Boolean> cutsPos) {
    LinkedList<String> routeOfvideos = new LinkedList<String>();
    LinkedList<LinkedList<Mat>> scenesGenerated = this.obtainScenes(frames, cutsPos);
    String baseFilename = "Escena";
    String currentFilename;
    for (int i = 0; i < scenesGenerated.size(); i++) {
      currentFilename = baseFilename;
      currentFilename += i + ".avi";
      this.createVideofromframes(scenesGenerated.get(i), currentFilename);
      File localRoute = new File(currentFilename);
      routeOfvideos.add(localRoute.getAbsolutePath());
    }
    return routeOfvideos;
  }



  /**
   * This function returns the resolution (Width, then Height) as a collection of two integer
   * values.
   *
   * @return Collection containing two integer values of the last video read. The first element is
   *         the width (# of pixels). The second one is the height.
   */
  public LinkedList<Integer> obtainResOfLastVideoRead() {
    LinkedList<Integer> resolution = new LinkedList<Integer>();
    resolution.add((int) this.widthLastVideoRead);
    resolution.add((int) this.heightLastVideoRead);
    return resolution;
  }

}

