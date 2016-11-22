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
 * The Class falseValuesProcessor.
 *
 * @author Daniel
 */
public class falseValuesProcessor implements PrecisionAnalyzer {



  /**
   * This function returns an array of integers containing the frames that are considered cuts by
   * the ground truth file submitted.
   *
   * @param gTsceneCuts Ground truth scenes passed as ranges of frames.
   * @return A collection of the number of the frames that are not considered cuts
   */
  public LinkedList<Integer> obtainFramesCuts(LinkedList<Pair<Integer, Integer>> gTsceneCuts) {
    LinkedList<Integer> cutFrames = new LinkedList<Integer>();
    int beginFrameCutScene, endFrameCutScene;
    for (int i = 0; i < gTsceneCuts.size(); i++) {
      beginFrameCutScene = gTsceneCuts.get(i).getKey();
      endFrameCutScene = gTsceneCuts.get(i).getValue();
      for (int j = beginFrameCutScene; j <= endFrameCutScene; j++) {
        cutFrames.add(j);
      }
    }
    return cutFrames;
  }

  /**
   * This function returns an array of integers containing the frames that are not considered cuts
   * by the ground truth file submitted. It depends of the function obtainFramesCuts.
   *
   * @param gTsceneCuts Ground truth scenes passed as ranges of frames.
   * @param numberFrames The total number of frames of the video, obtained from adding 1 to the
   *        number of elements in the dissimilitude collection
   * @return A collection of the number of the frames that are not considered cuts.
   */
  private LinkedList<Integer> obtainFramesNotCuts(LinkedList<Pair<Integer, Integer>> gTsceneCuts,
      int numberFrames) {
    LinkedList<Integer> notCutFrames = new LinkedList<Integer>();
    LinkedList<Integer> framesCuts = obtainFramesCuts(gTsceneCuts);
    for (int i = 1; i < numberFrames; i++) { // El frame inicial según el GT base es 1
      if (framesCuts.contains(i)) { // Sí es un corte
        continue;
      } else { // No es un corte, se agrega.
        notCutFrames.add(i);
      }
    }
    return notCutFrames;
  }

  /**
   * This function returns a collection containing the position of each frame associated with a cut
   * obtained from the automatic temp video segmentation.
   *
   * @param cutsObtained Collection of booleans associated with the existence of a cut between two
   *        frames. If the ith element is true, that would mean there's a cut between the ith frame
   *        and the ith+1 frame
   * @return Collection of frames that are associated or interpreted as cuts by the automatic video
   *         segmentation.
   */
  private LinkedList<Integer> getCutsobtained(LinkedList<Boolean> cutsObtained) {
    LinkedList<Integer> cutPosObtained = new LinkedList<Integer>();
    for (int i = 0; i < cutsObtained.size(); i++) {
      if (cutsObtained.get(i) == true) { /*
                                          * Hay un corte entre el frame i y el frame i+1. Se asigna
                                          * en el frame i y frame i+1
                                          */
        cutPosObtained.add(i); // Porque los frames según el GT base dado por el profesor comienzan
      }
    }
    return cutPosObtained;
  }

  /**
   * This function returns a collection containing the position of each frame NOT associated with a
   * cut obtained from the automatic temp video segmentation. cut
   *
   * @param cutsObtained Collection of booleans associated with the existence of a cut between two
   *        frames. If the ith element is true, that would mean there's a cut between the ith frame
   *        and the ith+1 frame
   * @return Collection of frames that are NOT associated or interpreted as cuts by the automatic
   *         video segmentation.
   */
  private LinkedList<Integer> getNotcutsobtained(LinkedList<Boolean> cutsObtained) {
    LinkedList<Integer> cutPosObtained = new LinkedList<Integer>();
    cutPosObtained.add(1);
    for (int i = 0; i < cutsObtained.size(); i++) {
      if (cutsObtained.get(i) == true) { // Hay un corte entre el frame i y el frame i+1. Se asigna
                                         // en el frame i y frame i+1

      } else {
        cutPosObtained.add(i); // Porque los frames según el GT base dado por el profesor comienzan
      }
    }
    return cutPosObtained;
  }


  /**
   * Checks if the number of the obtained frame (frameObtained )is in the collectionExpectedFrames
   * collection or in it by a range of 5, give or take. The delta values should be five, so if some
   * value between the range [frameObtained-5, frameObtained+5] is in the collectionExpectedFrames,
   * it should return TRUE.
   *
   * @param collectionExpectedFrames Collection of positives or negatives, according to the ground
   *        truth file. It could be the
   * @param frameObtained The frame (or the position of it) currently evaluated.
   * @param numberFrames The total amount of frames in the video.
   * @return Boolean value associated with the existence of a match
   */
  private boolean ismatch(LinkedList<Integer> collectionExpectedFrames, int frameObtained,
      int numberFrames) {
    int deltaRangeBegin = frameObtained - 10;
    int deltaRangeEnd = frameObtained + 10;
    if (deltaRangeBegin <= 0) {
      deltaRangeBegin = 1;
    }
    if (deltaRangeEnd > numberFrames) {
      deltaRangeEnd = numberFrames;
    }
    for (int i = deltaRangeBegin; i <= deltaRangeEnd; i++) {
      if (collectionExpectedFrames.contains(i)) {
        return true;
      }
    }
    return false;

  }


  /**
   * Instantiates a new false values processor.
   */
  public falseValuesProcessor() {}

  /*
   * (non-Javadoc)
   * 
   * @see mainengine.FalseValuesAnalyzer#getFalsepositives(java.util.LinkedList,
   * java.util.LinkedList)
   */
  @Override
  public int getFalsepositives(LinkedList<Pair<Integer, Integer>> gTsceneCuts,
      LinkedList<Boolean> cutsObtained) {
    int counter = 0;
    int numberFrames = cutsObtained.size() + 1;
    LinkedList<Integer> cutsGt = this.obtainFramesCuts(gTsceneCuts);
    LinkedList<Integer> cutsObtainedFromSegTemp = this.getCutsobtained(cutsObtained);
    /*
     * System.out.println("CortesGT:" + cutsGt.toString()); System.out.println("CortesAnalyzer:" +
     * cutsObtainedFromSegTemp.toString());
     */
    for (int i = 0; i < cutsObtainedFromSegTemp.size(); i++) {
      if (this.ismatch(cutsGt, cutsObtainedFromSegTemp.get(i), numberFrames)) {

      } else {
        counter++;
      }
    }
    return counter;
  }

  /*
   * (non-Javadoc)
   * 
   * @see mainengine.FalseValuesAnalyzer#getFalsenegatives(java.util.LinkedList,
   * java.util.LinkedList)
   */
  @Override
  public int getFalsenegatives(LinkedList<Pair<Integer, Integer>> gTsceneCuts,
      LinkedList<Boolean> cutsObtained) {
    int counter = 0;
    int numberFrames = cutsObtained.size() + 1;
    LinkedList<Integer> notCutsGt = this.obtainFramesNotCuts(gTsceneCuts, numberFrames);
    LinkedList<Integer> notCutsObtainedFromSegTemp = this.getNotcutsobtained(cutsObtained);
    /*
     * System.out.println("NotCortesGT:" + notCutsGt.toString()); System.out.println("x");
     * System.out.println("NotCortesObtained:" + notCutsObtainedFromSegTemp.get(45));
     * System.out.println("Número de negativos obtenidos:  " + notCutsObtainedFromSegTemp.size());
     */
    for (int i = 0; i < notCutsObtainedFromSegTemp.size(); i++) {

      if (this.ismatch(notCutsGt, notCutsObtainedFromSegTemp.get(i), numberFrames)) {

      } else {
        counter++;
      }
    }
    return counter;
  }

  @Override
  public LinkedList<Pair<Integer, Integer>> getCutsScenes(LinkedList<Boolean> cutsObtained) {
    int counterInitialFrame = 0, counterLastFrame = 0;
    LinkedList<Pair<Integer, Integer>> cutsRanges = new LinkedList<Pair<Integer, Integer>>();
    Pair<Integer, Integer> currentCut;

    boolean areWeInsideACut = false;

    for (int i = 0; i < cutsObtained.size(); i++) {
      if (cutsObtained.get(i) == true && areWeInsideACut == false) {
        // Es un corte
        counterInitialFrame = i;
        areWeInsideACut = true;
        continue;
      } else if (cutsObtained.get(i) == false && areWeInsideACut == false) {
        continue;
      } else if (cutsObtained.get(i) == false && areWeInsideACut == true) {

        areWeInsideACut = false;
        counterLastFrame = i;
        cutsRanges.add(new Pair<Integer, Integer>(counterInitialFrame + 1, counterLastFrame + 1));
        continue;
      } else if (cutsObtained.get(i) == true && areWeInsideACut == true) {
        if (i + 1 == cutsObtained.size()) { // Es el último
          counterLastFrame = i;
          areWeInsideACut = false;
          cutsRanges.add(new Pair<Integer, Integer>(counterInitialFrame + 1, counterLastFrame + 1));
          continue;
        }
        counterLastFrame = i;
        continue;

      }
    }
    // System.out.println("Cortes añadidos: " + cutsRanges.size());
    return cutsRanges;
  }
}


