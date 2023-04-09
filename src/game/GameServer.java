package game;

//Imports
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
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
	private ClientThread client;
	private static Socket sock;
	private static int nclient = 0, nclients = 0;
	private String data;
	private PrintStream fromServer;
	private BufferedReader fromClient;
	private JTextArea log;
	//private static boolean running = true;	dont need?
	private ArrayList<ClientThread> listOfClients = new ArrayList<ClientThread>();
	private ArrayList<Leaderboard> leaderboard = new ArrayList<Leaderboard>();

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
	public GameServer(JTextArea log, ServerSocket socket,  ArrayList<ClientThread> listOfClients, ArrayList<Leaderboard> leaderboard) {
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
	/**
	 * Runnable method for the servDaemon thread.
	 * Waits to accept a connection from the client.
	 */
	public void run() {
		System.out.println("RUNNING");
		try {
			//Infinitely loops while the server socket is open
			while (!servsock.isClosed()) {
				sock = servsock.accept();
				nclient += 1;
				nclients += 1;
				client = new ClientThread(sock, nclient, log);
				listOfClients.add(client);
				client.start();
				System.out.println("printing all client network info: ");
				for (int i = 0; i < listOfClients.size(); i++) {
					System.out.println(listOfClients.get(i).clientSock );
				}
			}
		} catch (IOException e) {
			System.out.println("I/O Exception");
		}

	}

	/**
	 * ClientThread class for creating a new thread for each client.
	 * This is where the main communication is done with the clients.
	 * 
	 * @author Skylar Phanenhour, Ahnaf Kamal
	 *
	 */
	class ClientThread extends Thread {
		//Declaration
		private Socket clientSock;
		private JTextArea log;
		private PrintStream outToServer;
		private BufferedReader inFromClient;
		
		/**
		 * Overloaded constructor.
		 * 
		 * @param s - Socket that the client is on
		 * @param nclient - The client number that is assigned to the client
		 * @param log - Server log that displays messages
		 */
		public ClientThread(Socket s, int nclient, JTextArea log) {
			this.clientSock = s;
			clientid = listOfClients.size() + 1;
			this.log = log;
		}


		@Override
		/**
		 * Runnable method that is called from the client.thread().
		 * Main communication between the client and server.
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
				
				//Infinitely loops while the client socket is open
				while (!clientSock.isClosed()) {
					System.out.println("Socket connected, in loop");
					data = inFromClient.readLine();
					protocolSeperator = data.indexOf("#");
					clientStrID = data.substring(0, protocolSeperator);
					dataConfig = data.substring(protocolSeperator + 1, data.length());
					System.out.println("data config: " + dataConfig);
					
					//Switch case statement for the server protocols
					switch (dataConfig) {
					
					//Send game protocol
					case "SendGame":
						data = inFromClient.readLine();
						log.append("Recieved game from Client [" + clientid + "]: " + data + "\n");
						break;
						
					//Send data protocol
					case "SendData":
						data = inFromClient.readLine();
						log.append("Recieved data from Client [" + clientid + "]: " + data + "\n");
						
						//Sets the variables for the client and stores them in the leaderboard
						String username = inFromClient.readLine();
						int time = Integer.parseInt(inFromClient.readLine());
						int score = Integer.parseInt(inFromClient.readLine());
						leaderboard.add(new Leaderboard(clientid, username, time, score));
						break;
						
					//Disconncting protocol
					case "Disconnecting":
						inFromClient.readLine();
						//System.out.println("Disconnecting you");
						System.out.println("DISCONNECTING: " + listOfClients.get(clientid - 1).clientSock);
						log.append("DISCONNECTING : Client [" + clientStrID + "]:" + listOfClients.get(clientid - 1).clientSock);
						listOfClients.get(clientid - 1).outToServer.close();
						listOfClients.get(clientid - 1).inFromClient.close();
						listOfClients.get(clientid - 1).clientSock.close();
						listOfClients.remove(clientid - 1);
						clientSock.close();
						break;
						
					//Default, for anything that isn't a protocol
					default:
						System.out.println("the rest");
						log.append("Client [" + clientStrID + "]: " + data + "\n");
					}
				}
				
			} catch (IOException e) {
				System.out.println(e);
			} catch (NumberFormatException e) {
				System.out.println(e);
			} catch (NullPointerException e) {
				System.out.println(e);
			}
		}

	}

	/**
	 * Method to disconnect the server connection 
	 * and all its clients connected.
	 */
	protected void disconnectServer() {
		try {
			System.out.println("Ending server...");
			for(int i = 0; i < listOfClients.size(); i++) {
				listOfClients.get(i).outToServer.close();
				listOfClients.get(i).inFromClient.close();
				listOfClients.get(i).clientSock.close();
			}
			listOfClients.clear();
			servsock.close();
			closeAllConnections();

		} catch (NullPointerException e) {
			log.append("Null Pointer Exception...\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to end all of the clients connected
	 * on the server.
	 */
	protected void endConnections() {
		try {
			//Checks if ther are no clients connected
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

	/**
	 * Method to print the leaderboards
	 */
	protected void printLeaderboard() {
		try {
			//Checks if there are no clients connected
			if (nclients == 0) {
				log.append("There are no clients connected to the server...\n");
			} 
			else {
				//Sorts the leaderboards by highest score
				Collections.sort(leaderboard, new Comparator<Leaderboard>() {
					@Override
					public int compare(Leaderboard L1, Leaderboard L2) {
						return L2.getScore() - L1.getScore();
					}
				});

				//Loops through the leaderboards and prints the clients
				for (int i = 0; i < leaderboard.size(); i++) {
					log.append("Printing leaderboards...\n");
					log.append(String.format("%s%s %10s%d %10s%d %n", "Username: ", leaderboard.get(i).username,
							"Score:", leaderboard.get(i).score, "Time: ", leaderboard.get(i).time));
				}
			}

		} catch (NullPointerException e) {
			log.append("Null Pointer Exception...\n");
		}
	}

	/**
	 * Method to close everything
	 */
	protected void closeAllConnections() {
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
			// e.printStackTrace();
		}
	}

	/**
	 * Leaderboard class that is stored in an object ArrayList.
	 * Stores the data that is sent from client's.
	 * 
	 * @author Skylar Phanenhour, Ahnaf Kamal
	 *
	 */
	class Leaderboard {
		//Declarations
		private int clientid;
		private String username;
		private int time;
		private int score;

		/**
		 * Overloaded constructor 
		 * 
		 * @param clientid - id of the client 
		 * @param username - username of the client
		 * @param time - best time of the client
		 * @param score - best score of the client
		 */
		public Leaderboard(int clientid, String username, int time, int score) {
			this.clientid = clientid;
			this.username = username;
			this.time = time;
			this.score = score;
		}

		/**
		 * Getter for clientid
		 * 
		 * @return - clientid
		 */
		public int getClientid() {
			return clientid;
		}

		/**
		 * Getter for username
		 * 
		 * @return - username
		 */
		public String getUsername() {
			return username;
		}

		/**
		 * Getter for time
		 * 
		 * @return - time
		 */
		public int getTime() {
			return time;
		}

		/**
		 * Getter for score
		 * 
		 * @return - score
		 */
		public int getScore() {
			return score;
		}

	}
}
