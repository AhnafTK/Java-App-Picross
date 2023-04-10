package game;

//Imports
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JTextArea;

/**
 * This is the GameClient class that creates a client thread, can connect and
 * communicate with the GameServer.
 * 
 * @author Skylar Phanenhour, Ahnaf Kamal
 *
 */
public class GameClient {
	// Declarations
	private Socket clientSock; // holds client sock
	private boolean isConnected = false; // whether or not the client is connected
	private PrintStream toServer = null; // for server
	private BufferedReader fromClient = null; // for client
	private String consoleData = "", clientID; // for client and console data
	private JTextArea log; // for network traffic dislay
	private String userName; // client username
	private GameModel clientModel; // GameModel instance
	private GameController clientController; // GameController instance

	/**
	 * Overloaded constructor to create the client
	 * 
	 * @param hostName   - Server IP that the client connects to
	 * @param port       - Port number that the client connects to
	 * @param userName   - Client's username that is entered, stored in the model
	 * @param log        - Text area to display messages
	 * @param model      - GameModel to store/access variables within
	 * @param controller - Gives access to controller
	 */
	public GameClient(String hostName, int port, String userName, JTextArea log, GameModel model,
			GameController controller) {
		try {
			// Client variables
			clientSock = new Socket(hostName, port);
			this.clientModel = model;
			this.clientController = controller;
			clientModel.setUsername(userName);
			this.userName = userName;
			this.toServer = new PrintStream(clientSock.getOutputStream(), true);
			this.fromClient = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
			this.log = log;

			toServer.println(userName);
			clientID = fromClient.readLine();
			log.append("Connected successfully..\n");
			setConnected(true);

		} catch (UnknownHostException e) {
			log.append("Unknown Server IP on: " + hostName + " on port: " + port + "\n");
		} catch (IOException e) {
			log.append("Failed to connect to: " + hostName + " on port: " + port + "\n");
		}
	}

	/**
	 * Method that is used to receive messages that have been sent from the server.
	 */
	protected synchronized void messageFromServer() {
		// Infinitely loop while the client is connected
		while (isConnected()) {
			try {
				String serverMessage = fromClient.readLine();

				if (serverMessage != null) {

					// Disconnect protocol
					if (serverMessage.equals("#Disconnect")) {
						log.append("Server closed..\n");
						setConnected(false);
						clientSock.close();
					}
					// If there is no game board to receive from the server
					else if (serverMessage.equals("#Empty")) {
						log.append("Server does not have a game, maybe I can send mine?\n");
					}
					// Receive a game board from the server
					else {
						log.append(
								"Successfully received board from server. Make sure that \"Play\" window is open when receiving\n");
						processGame(serverMessage);
					}
				} else {
					log.append("Lost connection to server..\n");
					setConnected(false);
					closeReaders();
				}

			} catch (SocketException e) {
				closeReaders();
			} catch (IOException e) {
				closeReaders();
			}

		}
	}

	/**
	 * This message is used to get the text chat and send it to the server. Acts
	 * like commands basically.
	 * 
	 * @param message - Message from the text chat
	 */
	protected void sendMessage(String message) {
		if (message.equals("SendGame")) {
			log.append("Sending game...\n");
			sendGame();
		} else if (message.equals("SendData")) {
			log.append("Sending data...\n");
			sendData();
		} else if (message.equals("Disconnect")) {
			log.append("Disconnecting...\n");
			disconnectClient();
		} else { // normal message
			consoleData = clientID + "#" + message;
			toServer.println(consoleData);
			log.append(userName + ": " + message + "\n");
		}
	}

	/**
	 * disconnecClient method that disconnects the client
	 */
	protected void disconnectClient() {
		try {
			toServer.println(clientID + "#Disconnect"); // lets the server know that client is leaving
			toServer.println(userName + " on " + clientSock.getInetAddress() + " at port " + clientSock.getPort());
			clientSock.close(); // closes client sock
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to store the game board that was received from the
	 * server. It gets loaded in the client's game/model.
	 * 
	 * @param gameBoard - String configuration of the board received from the server
	 */
	private void processGame(String gameBoard) {
		String subParts = gameBoard.substring(0);
		String parts[] = subParts.split(","); // splits into parts, separated by commas
		String gridSize = parts[0]; // the value before the first comma is the dimension of the board

		// Sets the grid size
		clientModel.gridSize = Integer.valueOf(gridSize);
		String[] newBoardRows = new String[clientModel.gridSize]; // the new board

		// Loops through the String and stores each row, separated from commas
		for (int i = 0; i < clientModel.gridSize; i++) {
			newBoardRows[i] = parts[i + 1];
		}
		// Sets the row in the model
		clientModel.setRow(newBoardRows);
		// Creates a new play board received from the server
		clientController.newPlayBoard(gridSize, false, true);
	}

	/**
	 * sendGame method that sends the board configuration that the client is using
	 * in play or design.
	 */
	protected void sendGame() {
		// Gets the board configuration from the model
		String gameBoard = clientModel.sendGameToServer();

		// If a game hasn't been created
		if (gameBoard == null) {
			log.append("You need to play a new game first...\n");
		} else {
			toServer.println(clientID + "#SendGame"); // Sends the protocol
			toServer.println(gameBoard);
		}
	}

	/**
	 * sendData method that sends the username, best time, best score to the server,
	 * where it gets stored in a leaderboard.
	 */
	protected void sendData() {
		// Gets the game data from the model
		String gameData = clientModel.sendDataToServer();
		toServer.println(clientID + "#SendData"); // Sends the protocol
		toServer.println(gameData);

	}

	/**
	 * closeReaders method that is used to close the reader and print stream that
	 * the client is using.
	 */
	protected void closeReaders() {
		try {
			if (fromClient != null) {
				fromClient.close();
			}
			if (toServer != null) {
				toServer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Getter for isConnected variable
	 * 
	 * @return state of isConnected
	 */
	public boolean isConnected() {
		return isConnected;
	}

	/**
	 * Sets isConnected to true/false
	 * 
	 * @param isConnected Sets the variable
	 */
	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}
}