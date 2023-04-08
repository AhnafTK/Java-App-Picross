package game;

import java.io.*;
import java.net.*;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GameClient {
	
	private Socket clientSock;
	PrintStream toServer = null;
	BufferedReader fromClient = null, consoleIn = null;
	String consoleData = "", serverData, clientID;
	private JTextArea log;
	String userName;
	GameModel clientModel;

	public GameClient(JTextArea log) {
		this.log = log;

	}

	public GameClient(String hostName, int port, String userName, JTextArea log, JTextField chat, GameModel model) {
		this(log);
		this.clientModel = model;
		try {
			clientSock = new Socket(hostName, port);
			clientModel.setUsername(userName);
			this.userName = userName;
			this.toServer = new PrintStream(clientSock.getOutputStream(), true);
			this.fromClient = new BufferedReader(new InputStreamReader(clientSock.getInputStream())); // Reader for the socket
			this.log = log;

			toServer.println(userName);

			System.out.println("client socket is " + clientSock);
			// communication
			clientID = fromClient.readLine();
			System.out.print("Client[" + clientID + "] " + userName + ": ");

			log.append("Connected successfully..\n");
			Thread thread = new Thread(new ReceiveMessage(log, chat));
			thread.start();
			
		} catch (UnknownHostException e) {
			log.append("Unknown host: " + hostName + " on port: " + port + "\n");
			System.out.println("Don't know about host: " + hostName + " on port: " + port + "\n");
		} catch (IOException e) {
			log.append("Couldn't get I/O for the connection to: " + hostName + " on port: " + port + "\n");
			System.out.println("Couldn't get I/O for the connection to: " + hostName + " on port: " + port + "\n");

		}
	}

	class ReceiveMessage implements Runnable {
		private JTextArea log;
		private JTextField chat;

		public ReceiveMessage(JTextArea log, JTextField chat) {
			this.log = log;
			this.chat = chat;
		}

		
		public void sendMessage() {
			chat.addActionListener(e -> {
				log.append(userName + ": " + chat.getText() + '\n');
				consoleData = chat.getText();
				chat.setText("");
				consoleData = clientID + "#" + consoleData;
				toServer.println(consoleData);
			});

		}

		@Override
		public void run() {
			try {
				// wrong client socket?
				while (clientSock.isConnected() && fromClient!=null) {
					System.out.println("clicnet connected: " + clientSock );
					sendMessage();
					serverData = fromClient.readLine();
					log.append("Server: " + serverData);
					System.out.print("Client[" + clientID + "]" + userName + ": ");
				}
			} catch (UnknownHostException e) {
				System.out.println("Don't know about host\n");
			} catch (IOException e) {
				System.out.println("I/O Exception\n");
			}
		}
	}

	public void disconnectClient() {

		toServer.println(clientID + "#Disconnecting");
		toServer.println(userName + " on " + clientSock.getInetAddress() + " at port " + clientSock.getPort());

		/*
		try {
			toServer.println(clientID + "#Disconnecting");
			toServer.println(userName + " on " + sock.getInetAddress() + " at port " + sock.getPort());
			sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
	}

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

	public void sendData() {

		String gameData = clientModel.sendDataToServer();
		System.out.println(gameData);

		toServer.println(clientID + "#SendData");
		toServer.println(gameData);
		toServer.println(clientModel.getUsername());
		toServer.println(clientModel.getBestTime());
		toServer.println(clientModel.getBestScore());
	}

	public void endConnections() {
		// toServer.println(clientID + "#EndConnections");

	}

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