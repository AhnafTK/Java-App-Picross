package game;

import java.io.*;
import java.net.*;

import javax.swing.JTextArea;

public class GameClient {
	static Socket sock;
	PrintStream toServer = null;
    BufferedReader fromClient = null, consoleIn = null;
	String consoleData, serverData, clientID;
	
	public GameClient() {

	}

	public GameClient(String hostName, String port) {
        try {
			sock = new Socket(hostName, Integer.valueOf(port));

			fromClient = new BufferedReader(new InputStreamReader(sock.getInputStream())); // Reader for the socket communication
            toServer = new PrintStream(sock.getOutputStream(), true);
			clientID = fromClient.readLine();

			System.out.print("Client[" + clientID + "]: ");

			/*
			 * This block is only for the chat communication 
			 */
			
//			consoleIn = new BufferedReader(new InputStreamReader(System.in)); // Reader for the client/server communication through the console
//			consoleData = consoleIn.readLine();
//				
//			
//			while (!consoleData.equals("end")) { 
//				/* 
//				 * GUI still freezes because it is endlessly stuck in the server/client console connection to read input
//				 * but is never broken out
//				 */
//        		System.out.println("in while loop");
//
//				consoleData = clientID + "#" + consoleData;
//				toServer.println(consoleData);
//				toServer.flush();
//				serverData = fromClient.readLine();
//				System.out.println("Server: " + serverData);
//				System.out.print("Client[" + clientID + "]: ");
//				consoleData = consoleIn.readLine();
//			}
//
//        	System.out.println("outside while loop");
//			consoleData = clientID + "#" + consoleData;
//			toServer.println(consoleData);
//			toServer.flush();
//			
//			fromClient.close();
//			toServer.close();
//			consoleIn.close();
        } 
        catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + hostName);
        } 
        catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + hostName);
        }
	}
	
	public void clientEnd() {
    	try {
    		toServer.println(clientID + "#Disconnecting");
    		toServer.println(sock.getInetAddress() + " at port " + sock.getPort());
    		toServer.close();
			sock.close();
		} catch (IOException e) {
			System.out.println("The connection to the server has not been started...");
		}
	}
}
