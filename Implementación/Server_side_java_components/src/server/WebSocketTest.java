/*
 * @author Carlos Girón
 * 
 * @version 0.1.0
 */
package server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class WebSocketTest.
 */
public class WebSocketTest {
  /**
   * Main that the server should run to allow the web application to communicate
   * with it.
   * 
   * @param args
   *          Default main arguments.
   * @throws Exception
   *           Can throw exceptions because of socket connections.
   */
  public static void main(String[] args) throws Exception {
    Server server = new Server(9090);
    WebSocketHandler wsHandler = new WebSocketHandler() {
      @Override
      public void configure(WebSocketServletFactory factory) {
        factory.register(MyWebSocketHandler.class);
      }
    };
    server.setHandler(wsHandler);
    server.start();
    server.join();
  }
}
