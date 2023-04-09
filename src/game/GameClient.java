package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
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
	private Socket clientSock;
	protected boolean isConnected = false;
	private PrintStream toServer = null;
	private BufferedReader fromClient = null;
	private String consoleData = "", clientID;
	private JTextArea log;
	private String userName;
	private GameModel clientModel;

	/**
	 * Overloaded constructor to create the client
	 * 
	 * @param hostName - Server IP that the client connects to
	 * @param port     - Port number that the client connects to
	 * @param userName - Client's username that is entered, stored in the model
	 * @param log      - Text area to display messages
	 * @param model    - GameModel to store/access variables within
	 */
	public GameClient(String hostName, int port, String userName, JTextArea log, GameModel model) {
		try {
			// Initializes the client's variables
			clientSock = new Socket(hostName, port);
			clientSock.setSoTimeout(5000);

			this.clientModel = model;
			clientModel.setUsername(userName);
			this.userName = userName;
			this.toServer = new PrintStream(clientSock.getOutputStream(), true);
			this.fromClient = new BufferedReader(new InputStreamReader(clientSock.getInputStream())); // Reader for the
																										// socket
			this.log = log;

			toServer.println(userName);
			clientID = fromClient.readLine();
			log.append("Connected successfully..\n");

			System.out.println("client socket is " + clientSock);
			System.out.print("Client[" + clientID + "] " + userName + ": ");

			// Thread thread = new Thread(new ReceiveMessage(log, chat));
			/// thread.start();

		} catch (UnknownHostException e) {
			log.append("Unknown Server IP on: " + hostName + " on port: " + port + "\n");
		} catch (IOException e) {
			log.append("Failed to connect to: " + hostName + " on port: " + port + "\n");
		}
	}

	/*
	 * chat.addActionListener(e -> { log.append(userName + ": " + chat.getText() +
	 * '\n'); consoleData = chat.getText(); chat.setText(""); consoleData = clientID
	 * + "#" + consoleData; toServer.println(consoleData); });
	 * 
	 */

	/**
	 * This message is used to get the text chat and send it to the server
	 * 
	 * @param message - Message from the text chat
	 */
	protected void sendMessage(String message) {
		consoleData = clientID + "#" + message;
		toServer.println(consoleData);
		log.append(userName + ": " + message + "\n");
	}

	/**
	 * disconnecClient method that disconnects the client
	 */
	protected void disconnectClient() {
		try {
			toServer.println(clientID + "#Disconnecting");
			toServer.println(userName + " on " + clientSock.getInetAddress() + " at port " + clientSock.getPort());
			clientSock.close();
		} catch (IOException e) {
			System.out.println("server close no!!!!!!!!!!!");
		}
	}

	/**
	 * sendGame method that sends the board configuration that the client is using
	 * in play or design.
	 */
	protected void sendGame() {
		String gameBoard = clientModel.sendGameToServer();
		System.out.println(gameBoard);

		if (gameBoard == null) {
			log.append("You need to play a new game first...\n");
		} else {
			toServer.println(clientID + "#SendGame");
			toServer.println(gameBoard);
		}
	}

	/**
	 * sendData method that sends the username, best time, best score to the server,
	 * where it gets stored in a leaderboard.
	 */
	protected void sendData() {

		String gameData = clientModel.sendDataToServer();
		System.out.println(gameData);

		toServer.println(clientID + "#SendData");
		toServer.println(gameData);
		toServer.println(clientModel.getUsername());
		toServer.println(clientModel.getBestTime());
		toServer.println(clientModel.getBestScore());
	}

	/**
	 * closeReaders method that is used to close the reader and print stream that
	 * the client is using.
	 */
	protected void closeReaders() {
		try {
			if (fromClient != null) {
				System.out.println("from client closed");
				fromClient.close();
			}
			if (toServer != null) {
				System.out.println("to server closed");
				toServer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}