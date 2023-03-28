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

	public GameClient(String hostName, int port, JTextArea log) {
        try {
			sock = new Socket(hostName, port);

			fromClient = new BufferedReader(new InputStreamReader(sock.getInputStream())); // Reader for the socket communication
            toServer = new PrintStream(sock.getOutputStream(), true);
            clientID = fromClient.readLine();

			System.out.print("Client[" + clientID + "]: ");

//			if (fromClient.readLine().equals("#EndConnections")) {
//				System.out.println("we are here");
//				sock.close();
//				fromClient.close();
//				toServer.close();
//				System.exit(0);
//			}

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
            log.append("Don't know about host: " + hostName + " on port: " + port + "\n");
        } 
        catch (IOException e) {
            log.append("Couldn't get I/O for the connection to: " + hostName + " on port: " + port + "\n");
        }
	}
	
	public void disconnectClient() {
    	try {
    		toServer.println(clientID + "#Disconnecting");
    		toServer.println(sock.getInetAddress() + " at port " + sock.getPort());
    		toServer.close();
			sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}