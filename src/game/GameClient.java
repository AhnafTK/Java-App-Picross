package game;

import java.io.*;
import java.net.*;

public class GameClient {
	static Socket sock;
	PrintStream out = null;
    BufferedReader in = null, stdIn = null;
	String consoleData, serverData, clientID;
	
	public GameClient() {

	}

	public GameClient(String hostName, String port) {
        try {
			sock = new Socket(hostName, Integer.valueOf(port));

            in = new BufferedReader(new InputStreamReader(sock.getInputStream())); // Reader for the socket communication
            out = new PrintStream(sock.getOutputStream());
			clientID = in.readLine();

			System.out.println("Client no." + clientID + "...");

			stdIn = new BufferedReader(new InputStreamReader(System.in)); // Reader for the client/server communication through the console
			consoleData = stdIn.readLine();

			System.out.println("test");
			while (!consoleData.equals("end")) {
        		System.out.println("in while loop");

				consoleData = clientID + "#" + consoleData;
				out.println(consoleData);
				out.flush();
				serverData = in.readLine();
				System.out.println("Server: " + serverData);
				System.out.print("Client[" + clientID + "]: ");
				consoleData = stdIn.readLine();
			}

        	
			consoleData = clientID + "#" + consoleData;
			out.println(consoleData);
			out.flush();
			
			in.close();
        	out.close();
        	stdIn.close();
        	sock.close();
        } 
        catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + hostName);
        } 
        catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + hostName);
        }
	}
}
