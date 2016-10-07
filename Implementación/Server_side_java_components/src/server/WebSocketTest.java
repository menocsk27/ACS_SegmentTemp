package server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class WebSocketTest {
  /**Clase de websocket para conectarse con la aplicacion web. 
   * @param args parametro de entrada por defecto del main.
   * @throws Exception Puede tirar excepciones por conexion del socket.
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