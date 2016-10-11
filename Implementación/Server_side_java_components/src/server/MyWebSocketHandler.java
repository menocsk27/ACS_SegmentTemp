package server;

import main.MainProcessor;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

@WebSocket
public class MyWebSocketHandler {
  private MainProcessor observer = new MainProcessor();
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
    System.out.println("Connect: " + session.getRemoteAddress().getAddress());
    try {
      session.getRemote().sendString("Hello Webbrowser");
    } catch (IOException exception) {
      exception.printStackTrace();
    }
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
      notify(videoRoute, groundTruth);
      System.out.println("Histogramas creados en localhost");
    } else {
      groundTruth = message;
    }
  }
  
  private void notify(String videoRoute, String groundTruth) {
    observer.validate(videoRoute, groundTruth);
  }
}