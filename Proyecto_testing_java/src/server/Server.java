package server;

import java.io.*;
import java.net.*;
import java.util.Date;

public class Server extends Thread{
	public ServerSocket listener;
	//private Socket receiver;
	private final String CODIFICACION = "UTF-8";
	
	public Server () throws IOException{
		listener = new ServerSocket(9090);
		listener.setSoTimeout(10000);
	}
	public void run(){
        try {
            while (true) {
            	System.out.println("Se espera un mensaje.");
                Socket receiver = listener.accept();
                System.out.println("Just connected to " + receiver.getRemoteSocketAddress());
                try {
                	BufferedReader in =
                            new BufferedReader(new InputStreamReader(receiver.getInputStream(), CODIFICACION));
                    
                    PrintWriter outD =
                        new PrintWriter(receiver.getOutputStream(), true);
                    outD.println(new Date().toString());
                    
                    DataOutputStream out = new DataOutputStream(receiver.getOutputStream());
                    out.writeUTF("Thank you for connecting to " + receiver.getLocalSocketAddress() + "\nGoodbye!");
                    System.out.println(in.readLine());
                    
                    receiver.close();
                } finally {
                    //socket.close();
                }

                //listener.close();
            }
        }catch(SocketTimeoutException s){
        	System.out.println("Socket timeout.");
        }catch(IOException e){
        	e.printStackTrace();
        }
    }
	public static void main(String [] args){
		try{
			Thread t = new Server();
	        t.start();
	    } catch(IOException e){
	    	e.printStackTrace();
	    }
	}
}