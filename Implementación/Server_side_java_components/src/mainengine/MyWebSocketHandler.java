package server;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import mainengine.MainProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@WebSocket
public class MyWebSocketHandler {
  private MainProcessor observer = new MainProcessor();
  private Session session;
  private ArrayList<String> videos = new ArrayList<>();
  private String groundTruth = "404";
  private String videoRoute = "";
  
  @OnWebSocketClose
  public void onClose(int statusCode, String reason) {
    System.out.println("Close: statusCode=" + statusCode + ", reason=" + reason);
  }
  
  @OnWebSocketError
  public void onError(Throwable thrown) {
    System.out.println("Error: " + thrown.getMessage());
  }
  
  /** Function that declares what happens when something connects to the socket.
   * @param session Parameter given by the socket class, not given by code.
   */
  @OnWebSocketConnect
  public void onConnect(Session session) {
    this.session = session;
    System.out.println("Connect: " + session.getRemoteAddress().getAddress());
    //try {
    //  session.getRemote().sendString("Hello Webbrowser");
    //} catch (IOException exception) {
    //  exception.printStackTrace();
    //}
  }
  
  /** Function that declares what to answer to the client whenever the socket receives a message.
   * @param message Parameter given by the socket class, not given by code.
   *                Contains the message received by the client.
   */
  @OnWebSocketMessage
  public void onMessage(String message) {
    String type = message.substring(message.length() - 3);
    System.out.println("Message: " + message);
    if (type.equals("mp4") || type.equals("avi")) {
      videoRoute = message;
      videos = notify(videoRoute, groundTruth);
      System.out.println("Histogramas creados en localhost");
      sendFiles(videos);
    } else {
      groundTruth = message;
    }
  }
  
  private void sendFiles(ArrayList<String> videos) {
    for (String file : videos) {
      try {
        session.getRemote().sendString(file);
      } catch (IOException exception) {
        exception.printStackTrace();
      }
    }
  }
  
<<<<<<< HEAD:Implementación/Server_side_java_components/src/server/MyWebSocketHandler.java
  private ArrayList<String> notify(String videoRoute, String groundTruth) {
    return observer.validate(videoRoute, groundTruth);
    //return new ArrayList<String>(Arrays.asList("assets/temp/Dissolve1-15.mp4",
    //    "assets/temp/Dissolve1-15.mp4", "assets/temp/Dissolve1-15.mp4"));
=======
  private void notify(String message) {
    observer.startMainflow(message);
>>>>>>> refs/remotes/origin/Avance2_Sprint3_JavaFeatures:Implementación/Server_side_java_components/src/mainengine/MyWebSocketHandler.java
  }
}