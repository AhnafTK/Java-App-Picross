package game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class GameClient {
	/**
	 * Default port.
	 */
	static int PORT = 3000;
	
	/**
	 * Number of port.
	 */
	static int portNumber = 0;
	
	/**
	 * Default hostname.
	 */
	static String HOSTNAME = "localhost";
	
	/**
	 * Variable for hostname.
	 */
	static String hostName = "";

	/**
	 * Default constructor.
	 */

	public static void main(String args[]) {
		if (args == null) {
			// System.err.println("Usage: java EchoClient <host name> <port number>");
			hostName = HOSTNAME;
			portNumber = PORT;
		} else if (args.length != 2) {
			// System.err.println("Usage: java EchoClient <host name> <port number>");
			hostName = HOSTNAME;
			portNumber = PORT;
		} else {
			hostName = args[0];
			portNumber = Integer.parseInt(args[1]);
		}
		System.out.println("Connecting with server on " + hostName + " at port " + portNumber);
		System.out.println("Starting Server Thread on port " + portNumber);
		try {
			Socket sock = new Socket(hostName, portNumber);
			BufferedReader dis = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			PrintStream dat = new PrintStream(sock.getOutputStream());
			String strcliid = dis.readLine();
			System.out.println("Client no." + strcliid + "...");
			String consoleData;
			String serverData;
			/// DataInputStream inConsole = new DataInputStream(System.in);
			BufferedReader inConsole = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Client[" + strcliid + "]: ");
			consoleData = inConsole.readLine();
			while (!consoleData.equals("end")) {
				consoleData = strcliid + "#" + consoleData;
				dat.println(consoleData);
				dat.flush();
				serverData = dis.readLine();
				System.out.println("Server: " + serverData);
				System.out.print("Client[" + strcliid + "]: ");
				consoleData = inConsole.readLine();
			}
			consoleData = strcliid + "#" + consoleData;
			dat.println(consoleData);
			dat.flush();
			sock.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
