package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import javax.swing.JTextArea;

public class GameServer implements Runnable {
	static ServerSocket servsock;
	ClientThread w;
	private Socket sock;
	static int nclient = 0, nclients = 0;
	String data;
	PrintStream fromServer;
	BufferedReader fromClient;
	JTextArea log;
	ArrayList<ClientThread>listOfClients = new ArrayList<ClientThread>();
	ArrayList<Leaderboard>leaderboard = new ArrayList<Leaderboard>();

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
		for (;;) {
			try {
				sock = servsock.accept();
				nclient += 1;
				nclients += 1;
			} catch (IOException ioe) {
				System.out.println(ioe);
			}
			w = new ClientThread(sock, nclient, log);
			listOfClients.add(w);
			w.start();
		}

	}

	class ClientThread extends Thread {

		/**
		 * Socket variable.
		 */
		private Socket sock;
		JTextArea log;
		
		/**
		 * Default constructor.
		 * 
		 * @param s       Socket
		 * @param nclient Number of client.
		 */
		public ClientThread(Socket s, int nclient, JTextArea log) {
			sock = s;
			clientid = listOfClients.size()+1;
			this.log = log;
		}

		/**
		 * Run method.
		 */
		public void run() {
			try {
				fromClient = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				fromServer = new PrintStream(sock.getOutputStream(), true);
				data = fromClient.readLine();
				log.append("NEW USER JOINED: ID: " + clientid + "...\n");
				log.append(data + " is connecting " + sock.getInetAddress() + " at port " + servsock.getLocalPort() + ".\n");
				
				fromServer.println(clientid);
				data = fromClient.readLine();
				
				protocolSeperator = data.indexOf("#");
				clientStrID = data.substring(0, protocolSeperator);
				dataConfig = data.substring(protocolSeperator + 1, data.length());

				while (!dataConfig.equals("EndServer")) {

					if (!dataConfig.equals("null")) {
						if (dataConfig.equals("Disconnecting")) {
							data = fromClient.readLine();
							disconnectClient();
							break;
						}
						else if (dataConfig.equals("SendGame")) {
							data = fromClient.readLine();
							sendGame();
						}					
						else if (dataConfig.equals("SendData")) {
							data = fromClient.readLine();
							sendData();
							String username = fromClient.readLine();
							int time = Integer.parseInt(fromClient.readLine());
							int score = Integer.parseInt(fromClient.readLine());
							leaderboard.add(new Leaderboard(clientid, username, time, score));
						}
						else if (dataConfig.equals("EndConnections")) {
							endConnections();
						}

						/*
						 * This block is only for the chat communication
						 */

						log.append("Client [" + clientStrID + "]: " + data + "\n");
						//fromServer.println("String \"" + data + "\" received.");
						data = fromClient.readLine();
						
						protocolSeperator = data.indexOf("#");
						clientStrID = data.substring(0, protocolSeperator);
						dataConfig = data.substring(protocolSeperator + 1, data.length());
					}
				}
				disconnectServer();
//				System.out.println("Disconecting " + sock.getInetAddress() + "!");
//				nclients -= 1;
//				System.out.println("Current client number: " + nclients);
//				if (nclients == 0) {
//					System.out.println("Ending server...");
//					sock.close();
//					System.exit(0);
//				}
			} catch (IOException e) {
				System.out.println(e);
			} catch (NumberFormatException e) {
				System.out.println(e);
			}
		}

	}
	
	public void disconnectClient() {
		nclients -= 1;
		nclient -= 1;
		listOfClients.remove(clientid-1);
		log.append("Current number of clients: " + (listOfClients.size()) + "\n");
		log.append("Disconnecting " + data + "\n");
	}

	public void sendGame() {
		data = (" Sent this board configuration " + data);
	}
	
	public void sendData() {
		data = (" Sent player data " + data);
		
	}
	
	// disconnect clients
	public void disconnectServer() {
		try {
			System.out.println("Ending server...");
			sock.close();
			servsock.close();
			System.exit(0);

		} catch (SocketException e) {
			log.append("Socket Exception...\n");
		} catch (IOException e) {
			log.append("I/O Exception...\n");
		} catch (NullPointerException e) {
			log.append("Null Pointer Exception...\n");
		}
	}
	
	public void endConnections() {
		try {
			if (nclients == 0) {
				log.append("There are no clients connected to the server...\n");
			}
			else {
				fromServer = new PrintStream(sock.getOutputStream(), true);
				fromServer.println("#EndConnections");
				log.append("Ending all client connections...\n");
				fromClient.close();	
			}

		} catch (SocketException e) {
			log.append("Socket Exception...\n");
		} catch (IOException e) {
			log.append("I/O Exception...\n");
		} catch (NullPointerException e) {
			log.append("Null Pointer Exception...\n");
		}
	}
	
	class Leaderboard {
		int clientid;
		String userName;
		int time;
		int score;
		
		public Leaderboard(int clientid, String username, int time, int score) {
			this.clientid = clientid;
			this.userName = userName;
			this.time = time;
			this.score = score;
		}

		public int getClientid() {
			return clientid;
		}

		public String getUserName() {
			return userName;
		}

		public int getTime() {
			return time;
		}

		public int getScore() {
			return score;
		}
		
	}
}
