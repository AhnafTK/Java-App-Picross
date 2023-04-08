package game;

import java.io.*;
import java.net.*;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * This is the GameClient class that creates a client thread,
 * can connect and communicate with the GameServer.
 * 
 * @author Skylar Phanenhour, Ahnaf Kamal
 *
 */
public class GameClient {
	//Declarations
	private Socket clientSock;
	boolean isConnected = false;
	PrintStream toServer = null;
	BufferedReader fromClient = null, consoleIn = null;
	String consoleData = "", serverData, clientID;
	private JTextArea log;
	private JTextField chat;
	String userName;
	GameModel clientModel;


	/**
	 * Overloaded constructor to create the client
	 * 
	 * @param hostName - Server IP that the client connects to
	 * @param port - Port number that the client connects to
	 * @param userName - Client's username that is entered, stored in the model
	 * @param log - Text area to display messages
	 * @param chat - Text field to send chats to the server
	 * @param model - GameModel to store/access variables within
	 */
	public GameClient(String hostName, int port, String userName, JTextArea log, JTextField chat, GameModel model) {
		try {
			//Initializes the client's variables
			clientSock = new Socket(hostName, port);
			clientSock.setSoTimeout(5000);

			this.clientModel = model;
			clientModel.setUsername(userName);
			this.userName = userName;
			this.toServer = new PrintStream(clientSock.getOutputStream(), true);
			this.fromClient = new BufferedReader(new InputStreamReader(clientSock.getInputStream())); // Reader for the socket
			this.log = log;

			toServer.println(userName);
			System.out.println("client socket is " + clientSock);
			clientID = fromClient.readLine();
			System.out.print("Client[" + clientID + "] " + userName + ": ");

			log.append("Connected successfully..\n");
			//Thread thread = new Thread(new ReceiveMessage(log, chat));
			///thread.start();
			
		} catch (UnknownHostException e) {
			log.append("Unknown host: " + hostName + " on port: " + port + "\n");
			System.out.println("Don't know about host: " + hostName + " on port: " + port + "\n");
		} catch (IOException e) {
			log.append("Couldn't get I/O for the connection to: " + hostName + " on port: " + port + "\n");
			System.out.println("Couldn't get I/O for the connection to: " + hostName + " on port: " + port + "\n");

		}
	}

	/*
	 * chat.addActionListener(e -> {
				log.append(userName + ": " + chat.getText() + '\n');
				consoleData = chat.getText();
				chat.setText("");
				consoleData = clientID + "#" + consoleData;
				toServer.println(consoleData);
			});

	 */
	
	public void sendMessage(String message) {
		consoleData = clientID + "#" + message;
		toServer.println(consoleData);
		log.append(userName + ": " +message+"\n");
	}
	
	/**
	 * disconnecClient method that disconnects the client
	 */
	public void disconnectClient() {

		toServer.println(clientID + "#Disconnecting");
		toServer.println(userName + " on " + clientSock.getInetAddress() + " at port " + clientSock.getPort());
		try {
			clientSock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("server close no!!!!!!!!!!!");
			e.printStackTrace();
		}
	}
	/**
	 * sendGame method that sends the board configuration
	 * that the client is using in play or design.
	 */
	public void sendGame() {

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
	 * sendData method that sends the username, best time, best score
	 * to the server, where it gets stored in a leaderboard.
	 */
	public void sendData() {

		String gameData = clientModel.sendDataToServer();
		System.out.println(gameData);

		toServer.println(clientID + "#SendData");
		toServer.println(gameData);
		toServer.println(clientModel.getUsername());
		toServer.println(clientModel.getBestTime());
		toServer.println(clientModel.getBestScore());
	}

	/**
	 * closeReaders method that is used to close the 
	 * reader and print stream that the client is using.
	 */
	public void closeReaders() {
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