package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer implements Runnable {
	static ServerSocket servsock;
	private Socket sock;
	static int nclient = 0, nclients = 0;
	String data;
	PrintStream fromServer = null;
	BufferedReader fromClient;
	
	public GameServer() {

	}

	public GameServer(String port) {
		System.out.println("Starting server");
		try {
			servsock = new ServerSocket(Integer.valueOf(port));
			Thread servDaemon = new Thread(new GameServer());
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
				//System.out.println("Connecting " + sock.getInetAddress() + " at port " + sock.getPort() + ".");
			} catch (IOException ioe) {
				System.out.println(ioe);
			}
			Worked w = new Worked(sock, nclient);
			w.start();
		}
		
	}
	
	class Worked extends Thread {

		/**
		 * Socket variable.
		 */
		Socket sock;

		/**
		 * Integers for client and positions.
		 */
		int clientid, protocolSeperator;

		/**
		 * String for data.
		 */
		String clientStrID, dataConfig;

		/**
		 * Default constructor.
		 * 
		 * @param s       Socket
		 * @param nclient Number of client.
		 */
		public Worked(Socket s, int nclient) {
			sock = s;
			clientid = nclient;
		}

		/**
		 * Run method.
		 */
		public void run() {
			try {
				fromClient = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				fromServer = new PrintStream(sock.getOutputStream());
				fromServer.println(clientid);
				data = fromClient.readLine();
				
				protocolSeperator = data.indexOf("#");
				clientStrID = data.substring(0, protocolSeperator);
				dataConfig = data.substring(protocolSeperator + 1, data.length());

				if (dataConfig.equals("Disconnecting")) {
					data = fromClient.readLine();
					disconnectClient();
				}
				System.out.println("server data : " + data);
				
				/*
				 * This block is only for the chat communication
				 */
				
//				while (!dataConfig.equals("end")) {
//					System.out.println("Client[" + clientStrID + "]: " + data);
//					fromServer.println("String \"" + data + "\" received.");
//					fromServer.flush();
//					data = fromClient.readLine();
//					protocolSeperator = data.indexOf("#");
//					clientStrID = data.substring(0, protocolSeperator);
//					dataConfig = data.substring(protocolSeperator + 1, data.length());
//				}
//				System.out.println("Disconecting " + sock.getInetAddress() + "!");
//				nclients -= 1;
//				System.out.println("Current client number: " + nclients);
//				if (nclients == 0) {
//					System.out.println("Ending server...");
//					sock.close();
//					System.exit(0);
//				}
			} catch (IOException ioe) {
				System.out.println(ioe);
			}
		}
	}

	public void disconnectClient() {
		nclients -= 1;
		System.out.println("Current number of clients: " + nclients);
		System.out.println("Disconnecting " + data);
	}
}

	

