package game;

//Imports
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
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
	private String serverBoard;
	private String data;
	private PrintStream fromServer;
	private BufferedReader fromClient;
	private JTextArea log;
	//private static boolean running = true;
	private ArrayList<ClientThread> listOfClients = new ArrayList<ClientThread>();
	private ArrayList<Leaderboard> leaderboard = new ArrayList<Leaderboard>();
	private int clientid, protocolSeperator;
	private String clientStrID, dataConfig;
	
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
		this.log = log; // dont need here?

		try {
			this.servsock = new ServerSocket(port);
			
			//Creates a new thread if the server socket isn't null
			if (servsock != null) {
				Thread servDaemon = new Thread(new GameServer(log, servsock, listOfClients, leaderboard));
				servDaemon.start();
				System.out.println("Server running on " + " at port " + port + "!");
			}
			//dont need
			else {
				System.out.println("RUN");
			}

		} catch (Exception e) {
			// System.out.println("Error: " + e.toString());
		}

	}

	@Override
	/**
	 * Runnable method that infinitely loops while the server socket is open.
	 * Waits to accept client sockets and adds them to the server.
	 */
	public void run() {
		System.out.println("RUNNING");
		try {
			//Infinitely loops while the server socket is open
			while (!servsock.isClosed()) {
				sock = servsock.accept();
				nclient += 1;
				nclients += 1;
				
				//Creates a new client and adds it to the ArrayList
				client = new ClientThread(sock, nclient, log);
				listOfClients.add(client);
				client.start();
				
				//Dont need
				System.out.println("printing all client network info: ");
				for (int i = 0; i < listOfClients.size(); i++) {
					System.out.println(listOfClients.get(i).clientSock );
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This class creates a client on a new thread.
	 * Communicates with each client.
	 * 
	 * @author Skylar Phanenhour, Ahnaf Kamal
	 *
	 */
	class ClientThread extends Thread {
		//Declarations
		private  Socket clientSock;
		//private String userName;
		private JTextArea log;
		private PrintStream outToServer;
		private BufferedReader inFromClient;
		

		/**
		 * Overloaded constructor
		 * 
		 * @param s - Socket that the client is created on
		 * @param nclient - Client id 
		 * @param log - Server log
		 */
		public ClientThread(Socket s, int nclient, JTextArea log) {
			this.clientSock = s;
			clientid = listOfClients.size() + 1;
			this.log = log;
		}

		@Override
		/**
		 * Runnable method that is used to communicate with the client.
		 */
		public void run() {
			try {
				//Creates the readers.
				inFromClient = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				outToServer = new PrintStream(sock.getOutputStream(), true);
				
				//Username of the client that joined
				data = inFromClient.readLine();
				log.append("NEW USER JOINED: ID: " + clientid + "...\n");
				log.append(data + " is connecting " + sock.getInetAddress() + " at port " + servsock.getLocalPort()
						+ ".\n");

				outToServer.println(clientid); //Sends the client id
				
				//Infinitely loops while the client socket is open
				while (!clientSock.isClosed()) {	
					System.out.println("Socket connected, in loop");
					//Reads the data sent from the client
					data = inFromClient.readLine();
					//Separates the data by the #
					protocolSeperator = data.indexOf("#");
					clientStrID = data.substring(0, protocolSeperator); //Client id
					dataConfig = data.substring(protocolSeperator + 1, data.length()); //Protocol
					System.out.println("data config: " + dataConfig);
					
					//Switch case statement to recognize the protocol that is sent
					switch (dataConfig) {
					
					//Send Game protocol
					case "SendGame":
						System.out.println("Received game");
						data = inFromClient.readLine();
						System.out.println(data);
						log.append("Recieved game from Client [" + clientStrID + "]: " + data + "\n");
						serverBoard = data;
						break;
						
					//Send Data protocol
					case "SendData":
						System.out.println("Received data");
						data = inFromClient.readLine();
						System.out.println("Im server received: " + data);
						processUserData(data);
						break;
						
					//Disconnecting protocol
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
						
					//Receive Game protocol
					case "ReceiveGame":
						log.append(clientStrID + " is requesting game..\n");
						//If there is no board stored in the server
						if (serverBoard != null) {
							System.out.println("sending client game");
							log.append("Sending game to " + clientStrID + "\n");
							listOfClients.get(clientid - 1).outToServer.println(serverBoard);
						}
						else {
							log.append("Game board is empty, nothing to send..\n");
							listOfClients.get(clientid - 1).outToServer.println("NA");
						}
						break;
						
					//Anything that is not a protocol
					default:
						System.out.println("the rest");
						//for(int i = 0; i < listOfClients.size(); i++) {
							//listOfClients.get(i).outToServer.println("Client [" + clientStrID + "]: " + data + "\n");
						//}
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
	 * Process user data method to set the variables
	 * when the client send's data.
	 * 
	 * @param data - the game data sent from the client
	 */
	private void processUserData(String data) {
		///String username = inFromClient.readLine();
		//int time = Integer.parseInt(inFromClient.readLine());
		//int score = Integer.parseInt(inFromClient.readLine());
		//System.out.println(username + time + score);
		//leaderboard.add(new Leaderboard(clientid, username, time, score));
		String subParts = data.substring(0);
		String parts[] = subParts.split(",");
		for (int i = 0; i < parts.length; i++) {
			System.out.println(parts[i]);
		}
		String userName = parts[0];
		int bestTime = Integer.valueOf(parts[1]);
		int score = Integer.valueOf(parts[2]);
		System.out.println(userName + bestTime + score);
		leaderboard.add(new Leaderboard(clientid, userName, bestTime, score));
		
	}

	/**
	 * Disconnects the server and end all of the client connections
	 */
	protected void disconnectServer() {
		try {
			System.out.println("Ending server...");
			log.append("Ending server...\n");
			System.out.println("Current size: " + listOfClients.size());
			
			//Loops through each client connected and ends their connections
			for(int i = 0; i < listOfClients.size(); i++) {
				System.out.println("DISCONNECTING: " + listOfClients.get(i).clientSock);
				listOfClients.get(i).outToServer.println("#Disconnect");
				listOfClients.get(i).inFromClient.close();
				listOfClients.get(i).outToServer.close();
				listOfClients.get(i).clientSock.close();
				listOfClients.remove(i);
				
				//clientSock.close();
			}
			//listOfClients.clear();
			servsock.close();
			//closeAllConnections();

		} catch (NullPointerException e) {
			log.append("Null Pointer Exception...\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//System.out.println("in disconnectServer");
			//e.printStackTrace();
		}
	}

//	public void endConnections() {
//		try {
//			if (nclients == 0) {
//				log.append("There are no clients connected to the server...\n");
//			} else {
//				for (int i = 0; i < listOfClients.size(); i++) {
//					fromServer = new PrintStream(listOfClients.get(i).clientSock.getOutputStream(), true);
//					fromServer.println("#EndConnections");
//				}
//				log.append("Ending all client connections...\n");
//			}
//
//		} catch (SocketException e) {
//			log.append("Socket Exception...\n");
//		} catch (IOException e) {
//			log.append("I/O Exception...\n");
//		} catch (NullPointerException e) {
//			log.append("Null Pointer Exception...\n");
//		}
//	}

	/**
	 * Prints the client's that are stored on the leaderboards
	 */
	protected void printLeaderboard() {
		try {
			//Checks if there are no clients
			if (nclients == 0) {
				log.append("There are no clients connected to the server...\n");
			} 
			else {
				//Sorts the leaderboards by highest score
				Collections.sort(leaderboard, new Comparator<Leaderboard>() {
					@Override
					/**
					 * Compare method that returns the highest score
					 */
					public int compare(Leaderboard L1, Leaderboard L2) {
						return L2.getScore() - L1.getScore();
					}
				});
				
				//Loops through the leaderboard and prints them
				log.append("===============LEADERBOARD==============\n");
				for (int i = 0; i < leaderboard.size(); i++) {
					log.append(String.format("%s%s %10s%d %10s%d %n", "Username: ", leaderboard.get(i).userName,
							"Score:", leaderboard.get(i).score, "Time: ", leaderboard.get(i).time));
				}
				log.append("===============END OF LEADERBOARD==============\n");

			}

		} catch (NullPointerException e) {
			log.append("Null Pointer Exception...\n");
		}
	}

	/**
	 * Closes all of the connections
	 */
	protected void closeAllConnections() {
		System.out.println("closing server I/O");
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
			 e.printStackTrace();
		}
	}

	/**
	 * Class for the leaderboards.
	 * This class gets stored in an object ArrayList.
	 * 
	 * @author Skylar Phanenhour, Ahnaf Kamal
	 *
	 */
	class Leaderboard {
		//Declarations
		private int clientid;
		private String userName;
		private int time;
		private int score;

		/**
		 * Overloaded constructor
		 * 
		 * @param clientid - Client id
		 * @param username - Client username
		 * @param time - Client's best time
		 * @param score - Client's best score
		 */
		public Leaderboard(int clientid, String username, int time, int score) {
			this.clientid = clientid;
			this.userName = username;
			this.time = time;
			this.score = score;
		}

		/**
		 * Gets the client's id
		 * 
		 * @return clientid
		 */
		protected int getClientid() {
			return clientid;
		}

		/**
		 * Gets the client's userName
		 * 
		 * @return userName
		 */
		protected String getUserName() {
			return userName;
		}

		/**
		 * Gets the client's best time
		 * 
		 * @return time
		 */
		protected int getTime() {
			return time;
		}

		/**
//		 * Gets the client's best score
		 * 
		 * @return score
		 */
		protected int getScore() {
			return score;
		}

	}
}
