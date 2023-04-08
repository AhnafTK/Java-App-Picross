package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JTextArea;
/**
 * This is the GameServer class that creates a thread 
 * for each new client.
 * 
 * @author Skylar Phanenhour, Ahnaf Kamal
 *
 */
public class GameServer implements Runnable {
	private ServerSocket servsock;
	ClientThread w;
	private static Socket sock;
	static int nclient = 0, nclients = 0;
	String data;
	PrintStream fromServer;
	BufferedReader fromClient;
	JTextArea log;
	private static boolean running = true;
	ArrayList<ClientThread> listOfClients = new ArrayList<ClientThread>();
	ArrayList<Leaderboard> leaderboard = new ArrayList<Leaderboard>();

	/**
	 * Integers for client and positions.
	 */
	int clientid, protocolSeperator;

	/**
	 * String for data.
	 */
	String clientStrID, dataConfig;
	/**
	 * Overloaded constructor to create the server
	 * 
	 * @param log - Text area to display messages
	 * @param socket - Server socket
	 * @param listOfClients - Array list for each connected client
	 * @param leaderboard - Array list for the user's on the leaderboard
	 */
	public GameServer(JTextArea log, ServerSocket socket,  ArrayList listOfClients, ArrayList leaderboard) {
		this.log = log;
		this.servsock = socket;
		this.listOfClients = listOfClients;
		this.leaderboard = leaderboard;
	}

	/**
	 * Constructor that accepts the client's connections on the socket
	 * 
	 * @param port - Port that the server socket gets created on
	 * @param log - Text area to display messages
	 */
	public GameServer(int port, JTextArea log) {
		System.out.println("Starting server");
		this.log = log;

		try {
			this.servsock = new ServerSocket(port);
			if (servsock != null) {
				Thread servDaemon = new Thread(new GameServer(log, servsock, listOfClients, leaderboard));
				servDaemon.start();
				System.out.println("Server running on " + " at port " + port + "!");
			} else {
				System.out.println("RUN");
			}

		} catch (Exception e) {
			// System.out.println("Error: " + e.toString());
		}

	}

	@Override
	public void run() {
		System.out.println("RUNNING");
		try {
			while (!servsock.isClosed()) {
				sock = servsock.accept();
				nclient += 1;
				nclients += 1;
				w = new ClientThread(sock, nclient, log);
				listOfClients.add(w);
				w.start();
				System.out.println("printing all client network info: ");
				for (int i = 0; i < listOfClients.size(); i++) {
					System.out.println(listOfClients.get(i).clientSock );
				}
			}
		} catch (IOException IhateNetworkingNow) {

		}

	}

	class ClientThread extends Thread {

		/**
		 * Socket variable.
		 */
		protected Socket clientSock;
		JTextArea log;
		PrintStream outToServer;
		BufferedReader inFromClient;
		/**
		 * Default constructor.
		 * 
		 * @param s       Socket
		 * @param nclient Number of client.
		 */
		public ClientThread(Socket s, int nclient, JTextArea log) {
			this.clientSock = s;
			clientid = listOfClients.size() + 1;
			this.log = log;
		}

		/**
		 * Run method.
		 */
		public void run() {
			try {
				
				inFromClient = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				outToServer = new PrintStream(sock.getOutputStream(), true);
				data = inFromClient.readLine();
				log.append("NEW USER JOINED: ID: " + clientid + "...\n");
				log.append(data + " is connecting " + sock.getInetAddress() + " at port " + servsock.getLocalPort()
						+ ".\n");

				outToServer.println(clientid);
				
				while (clientSock.isConnected()) {
					
					System.out.println("Socket connected, in loop");
					data = inFromClient.readLine();
					protocolSeperator = data.indexOf("#");
					clientStrID = data.substring(0, protocolSeperator);
					dataConfig = data.substring(protocolSeperator + 1, data.length());
					System.out.println("data config: " +dataConfig);
					
					//data = fromClient.readLine();

					switch (dataConfig) {
					case "SendGame":
						System.out.println("Received game");
						data = inFromClient.readLine();
						System.out.println(data);
						break;
					case "SendData":
						System.out.println("Received data");

						String username = inFromClient.readLine();
						int time = Integer.parseInt(inFromClient.readLine());
						int score = Integer.parseInt(inFromClient.readLine());
						leaderboard.add(new Leaderboard(clientid, username, time, score));
						System.out.println("got data");
						break;
					case "Disconnecting":
						inFromClient.readLine();
						System.out.println("Disconnecting you");
						System.out.println("DISCONNETING: " + listOfClients.get(Integer.valueOf(clientStrID) - 1).clientSock);

						listOfClients.get(Integer.valueOf(clientStrID) - 1).outToServer.close();
						listOfClients.get(Integer.valueOf(clientStrID) - 1).inFromClient.close();
						listOfClients.get(Integer.valueOf(clientStrID) - 1).clientSock.close();
						listOfClients.remove(Integer.valueOf(clientStrID) - 1);
						clientSock.close();
						//sock.close();
						break;
					default:
						System.out.println("the rest");
						log.append("Client [" + clientStrID + "]: " + data + "\n");
						//data = fromClient.readLine();
					}
				}
				
				
				// rest of the messages are received here by the server
				
				/*
				while (!dataConfig.equals("EndServer")) {
					System.out.println("dataConfig in loop: " + dataConfig);
					if (!dataConfig.equals("null")) {
						if (dataConfig.equals("Disconnecting")) {
							// data = fromClient.readLine();
							// disconnectClient();
							break;
						} else if (dataConfig.equals("SendGame")) {
							data = fromClient.readLine();
							sendGame();
						} else if (dataConfig.equals("SendData")) {
							data = fromClient.readLine();
							sendData();
							String username = fromClient.readLine();
							int time = Integer.parseInt(fromClient.readLine());
							int score = Integer.parseInt(fromClient.readLine());
							leaderboard.add(new Leaderboard(clientid, username, time, score));
						} else if (dataConfig.equals("EndConnections")) {
							endConnections();
						}

						log.append("Client [" + clientStrID + "]: " + data + "\n");
						// fromServer.println("String \"" + data + "\" received.");
						data = fromClient.readLine();

						protocolSeperator = data.indexOf("#");
						clientStrID = data.substring(0, protocolSeperator);
						dataConfig = data.substring(protocolSeperator + 1, data.length());
					}
				}
				 */
				
				/// disconnectServer();
			} catch (IOException e) {
				// System.out.println(e);
				//System.out.println("here");
			} catch (NumberFormatException e) {
				System.out.println(e);
			} catch (NullPointerException e) {

			}
		}

	}

	public void disconnectClient() {
		try {
			nclients -= 1;
			nclient -= 1;
			listOfClients.remove(clientid - 1);
			log.append("Current number of clients: " + (listOfClients.size()) + "\n");
			log.append("Disconnecting " + data + "\n");
			//sock.close();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} 
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
			servsock.close();
			closeAllConnections();

		} catch (NullPointerException e) {
			log.append("Null Pointer Exception...\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void endConnections() {
		try {
			if (nclients == 0) {
				log.append("There are no clients connected to the server...\n");
			} else {
				for (int i = 0; i < listOfClients.size(); i++) {
					fromServer = new PrintStream(listOfClients.get(i).clientSock.getOutputStream(), true);
					fromServer.println("#EndConnections");
				}
				log.append("Ending all client connections...\n");
			}

		} catch (SocketException e) {
			log.append("Socket Exception...\n");
		} catch (IOException e) {
			log.append("I/O Exception...\n");
		} catch (NullPointerException e) {
			log.append("Null Pointer Exception...\n");
		}
	}

	public void printLeaderboard() {
		try {
			if (nclients == 0) {
				log.append("There are no clients connected to the server...\n");
			} else {

				Collections.sort(leaderboard, new Comparator<Leaderboard>() {
					public int compare(Leaderboard L1, Leaderboard L2) {
						return L2.getScore() - L1.getScore();
					}
				});

				for (int i = 0; i < leaderboard.size(); i++) {
					log.append(String.format("%s%s %10s%d %10s%d %n", "Username: ", leaderboard.get(i).userName,
							"Score:", leaderboard.get(i).score, "Time: ", leaderboard.get(i).time));
				}
			}

		} catch (NullPointerException e) {
			log.append("Null Pointer Exception...\n");
		}
	}

	public void closeAllConnections() {
		try {
			if (fromClient != null) {
				fromClient.close();
			}
			if (fromServer != null) {
				fromServer.close();
			}
			if (sock != null) {
				sock.close();
			}
			if (servsock != null) {
				servsock.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
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
