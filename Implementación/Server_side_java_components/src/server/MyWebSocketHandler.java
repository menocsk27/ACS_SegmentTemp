/*
 * @author Carlos Gir�n
 * 
 * @version 0.1.0
 */
package server;

import java.io.IOException;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import mainengine.MainProcessor;

// TODO: Auto-generated Javadoc
/**
 * The Class MyWebSocketHandler.
 */
@WebSocket
public class MyWebSocketHandler {

  /** The observer. */
  private MainProcessor observer = new MainProcessor();

  /** The session. */
  private Session session;

  /** The accuracy metrics. */
  private String metrics;

  /** The ground truth. */
  private String groundTruth = "404";

  /** The video route. */
  private String videoRoute = "";

  /**
   * On close.
   *
   * @param statusCode
   *          the status code
   * @param reason
   *          the reason
   */
  @OnWebSocketClose
  public void onClose(int statusCode, String reason) {
    System.out.println("Close: statusCode=" + statusCode + ", reason=" + reason);
  }

  /**
   * On error.
   *
   * @param thrown
   *          the thrown
   */
  @OnWebSocketError
  public void onError(Throwable thrown) {
    System.out.println("Error: " + thrown.getMessage());
  }

  /**
   * Function that declares what happens when something connects to the socket.
   * 
   * @param session
   *          Parameter given by the socket class, not given by code.
   */
  @OnWebSocketConnect
  public void onConnect(Session session) {
    this.session = session;
    System.out.println("Connect: " + session.getRemoteAddress().getAddress());
    // try {
    // session.getRemote().sendString("Hello Webbrowser");
    // } catch (IOException exception) {
    // exception.printStackTrace();
    // }
  }

  /**
   * Function that declares what to answer to the client whenever the socket
   * receives a message.
   * 
   * @param message
   *          Parameter given by the socket class, not given by code. Contains
   *          the message received by the client.
   * @throws IOException
   * @throws IllegalArgumentException
   */
  @OnWebSocketMessage
  public void onMessage(String message) throws IllegalArgumentException, IOException {
    String type = message.substring(message.length() - 3);
    System.out.println("Message: " + message);
    if (type.equals("mp4") || type.equals("avi")) {
      videoRoute = message;
      metrics = notify(videoRoute, groundTruth);
      System.out.println("Histogramas creados en localhost");
      sendMetrics(metrics);
    } else {
      groundTruth = message;
    }
  }

  /**
   * Function that sends accuracy metrics given as a result of running the
   * system. Function that sends the files related to the routes passed as
   * parameters to the current client.
   *
   * @param metrics
   *          Values of the metrics returned by the system.
   */
  private void sendMetrics(String metrics) {
    System.out.println("test");
    System.out.println(metrics);
    try {
      session.getRemote().sendString(metrics);
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }

  /**
   * Function activated when some client sends a request with two string
   * parameters. The purpose of this request is to activate the main flow of the
   * automatic video segmentation.
   *
   * @param videoRoute
   *          A string containing the local file route to the video to be
   *          segmented. This local route must be valid and must contain a route
   *          to video avi or mp4.
   * @param groundTruth
   *          A string containing the local file route to the csv ground truth
   *          file which contains the position of the cuts. The ground truth
   *          file must be a four column file containing: Initial frame of the
   *          cut in the first column Last frame of the cut in the second column
   *          Type of the event. Not used in the segmentation nor the ground
   *          truth comparison. Type of the cut. Not used in the segmentation
   *          nor the ground truth comparison. The function only accepts avi or
   *          mp4 video files.
   * 
   * @return A collection of file routes of the scenes obtained from the
   *         segmentation.
   * @throws IOException
   * @throws IllegalArgumentException
   */
  private String notify(String videoRoute, String groundTruth) throws IllegalArgumentException, IOException {
    // observer.startMainflow(videoRoute, groundTruth);
    // return null;
    return observer.startMainflow(videoRoute, groundTruth);
    // return "13, 14";
  }
}
