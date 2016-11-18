/*
 * @author Daniel Troyo
 * 
 * @version 0.1.0
 */
package mainengine;

import java.util.LinkedList;

import javafx.util.Pair;

// TODO: Auto-generated Javadoc
/**
 * The Interface FalseValuesAnalyzer.
 */
public interface PrecisionAnalyzer {



  /**
   * This function returns the number of false positives based on gTsceneCuts and cutsObtained. The
   * false positives are those frames considered as cuts based on the automatic video segmentation
   * but also NOT considered as cuts by the ground truth file submmited.
   *
   * @param gTsceneCuts Collection of tuples of the range of frames of the cuts. Being that the
   *        beginning of a scene cut and the end of the scene cut. All the frames located in this
   *        range should be considered as cuts.
   * @param cutsObtained Plain collection of boolean values that represent the existence of a cut
   *        between two frames. If the ith value of this collection is true, this would represent a
   *        cut between the ith cut and the i+1th cut.
   * @return The number of false positives according to the ground truth file submitted and the cuts
   *         obtained from the automatic video segmentation.
   */
  public int getFalsepositives(LinkedList<Pair<Integer, Integer>> gTsceneCuts,
      LinkedList<Boolean> cutsObtained);

  /**
   * This function returns the number of false negatives based on gTsceneCuts and cutsObtained. The
   * false negatives those frames NOT considered as cuts based on the automatic video segmentation
   * but also considered as cuts by the ground truth file submmited.
   *
   * @param gTsceneCuts Collection of tuples of the range of frames of the cuts. Being that the
   *        beginning of a scene cut and the end of the scene cut. All the frames located in this
   *        range should be considered as frames.
   * @param cutsObtained Plain collection of boolean values that represent the existence of a cut
   *        between two frames. If the ith value of this collection is true, this would represent a
   *        cut between the ith cut and the i+1th cut.
   * @return The number of false negatives according to the ground truth file submitted and the cuts
   *         obtained from the automatic video segmentation.
   */
  public int getFalsenegatives(LinkedList<Pair<Integer, Integer>> gTsceneCuts,
      LinkedList<Boolean> cutsObtained);

  /**
   * This function returns the ranges of the cuts obtained by the analyzer, which are going to be
   * stored inside the file to be sent to the client.
   *
   * @param cutsObtained Plain collection of boolean values that represent the existence of a cut
   *        between two frames. If the ith value of this collection is true, this would represent a
   *        cut between the ith cut and the i+1th cut.
   * @return A list of ranges, represented by pair of integers, which can tell the initial frame of
   *         a cut and the last frame of it.
   */
  public LinkedList<Pair<Integer, Integer>> getCutsScenes(LinkedList<Boolean> cutsObtained);
}
