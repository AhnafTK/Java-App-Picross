package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JTextArea;

public class GameServer implements Runnable {
	static ServerSocket servsock;
	private Socket sock;
	static int nclient = 0, nclients = 0;
	String data;
	PrintStream fromServer = null;
	BufferedReader fromClient;
	JTextArea log;
	/**
	 * Integers for client and positions.
	 */
	int clientid, protocolSeperator;

	/**
	 * String for data.
	 */
	String clientStrID, dataConfig;

	public GameServer(JTextArea log) {
		this.log = log;
	}

	public GameServer(int port, JTextArea log) {
		System.out.println("Starting server");
		this.log = log;

		try {
			servsock = new ServerSocket(port);
			Thread servDaemon = new Thread(new GameServer(log));
			servDaemon.start();
			System.out.println("Server running on " + " at port " + port + "!");
		} catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}

	}

	@Override
	public void run() {
		System.out.println("RUNNING");
		// TODO Auto-generated method stub
		for (;;) {
			try {
				sock = servsock.accept();
				nclient += 1;
				nclients += 1;
				System.out.println("Connecting " + sock.getInetAddress() + " at port " + servsock.getLocalPort() + ".");
				// System.out.println("Connecting " + sock.getInetAddress() + " at port " +
				// sock.getPort() + ".");
			} catch (IOException ioe) {
				System.out.println(ioe);
			}
			ClientThread w = new ClientThread(sock, nclient, log);
			w.start();
		}

	}

	class ClientThread extends Thread {

		/**
		 * Socket variable.
		 */
		Socket sock;
		JTextArea log;
		
		/**
		 * Default constructor.
		 * 
		 * @param s       Socket
		 * @param nclient Number of client.
		 */
		public ClientThread(Socket s, int nclient, JTextArea log) {
			sock = s;
			clientid = nclient;
			this.log = log;
		}

		/**
		 * Run method.
		 */
		public void run() {
			try {
				fromClient = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				fromServer = new PrintStream(sock.getOutputStream(), true);
				fromServer.println(clientid);
				data = fromClient.readLine();
				
				protocolSeperator = data.indexOf("#");
				clientStrID = data.substring(0, protocolSeperator);
				dataConfig = data.substring(protocolSeperator + 1, data.length());

				while (!dataConfig.equals("end")) {

					if (!dataConfig.equals("null")) {
						if (dataConfig.equals("Disconnecting")) {
							//data = fromClient.readLine();
							System.out.println("disconnect method");
							disconnectClient();
							break;
						}

						/*
						 * This block is only for the chat communication
						 */

						log.append("Client[" + clientStrID + "]: " + data + "\n");
						//fromServer.println("String \"" + data + "\" received.");
						data = fromClient.readLine();
						protocolSeperator = data.indexOf("#");
						clientStrID = data.substring(0, protocolSeperator);
						dataConfig = data.substring(protocolSeperator + 1, data.length());
					}
				}
//				System.out.println("Disconecting " + sock.getInetAddress() + "!");
//				nclients -= 1;
//				System.out.println("Current client number: " + nclients);
//				if (nclients == 0) {
//					System.out.println("Ending server...");
//					sock.close();
//					System.exit(0);
//				}
			} catch (

			IOException ioe) {
				System.out.println(ioe);
			}
		}

	}

	public void disconnectClient() {
		nclients -= 1;
		nclient -= 1;
		System.out.println("Current number of clients: " + nclients + "\n");
		System.out.println("Disconnecting " + data + "\n");
	}

	// disconnect clients
	public void disconnectServer() {
		try {
			System.out.println("Ending server...");
			// Problem somewhere with this stuff
			// sock.close();
			// servsock.close();
			System.exit(0);

//		} catch (SocketException e) {
//			log.append("Socket Exception...\n");
//		} catch (IOException e) {
//			log.append("I/O Exception...\n");
		} catch (NullPointerException e) {
			log.append("Null Pointer Exception...\n");
		}
	}

	public void endConnections() {
//		try {
//			fromServer = new PrintStream(sock.getOutputStream(), true);
//			System.out.println("here");
//    		fromServer.println("#EndConnections");
//    		System.out.println("past");
//			log.append("Ending all client connections...\n");
//			sock.close();		
//			fromClient.close();
//			fromServer.close();

//		} catch (SocketException e) {
//			log.append("Socket Exception...\n");
//		} catch (IOException e) {
//			log.append("I/O Exception...\n");
//		} catch (NullPointerException e) {
//			log.append("Null Pointer Exception...\n");
//		} catch (IOException e) {
//			log.append("I/O Exception...\n");
//		}
	}
}
