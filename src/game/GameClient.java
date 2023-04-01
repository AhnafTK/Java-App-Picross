package game;

import java.io.*;
import java.net.*;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GameClient {
	static Socket sock;
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
			this.userName = userName;
			this.sock = new Socket(hostName, port);
			this.toServer = new PrintStream(sock.getOutputStream(), true);
			this.fromClient = new BufferedReader(new InputStreamReader(sock.getInputStream())); // Reader for the socket
			// communication
			clientID = fromClient.readLine();
			System.out.print("Client[" + clientID + "] "+userName+": ");

			Thread thread = new Thread(new ReceiveMessage(log, chat));
			thread.start();
		} catch (UnknownHostException e) {
			System.out.println("Don't know about host: " + hostName + " on port: " + port + "\n");
		} catch (IOException e) {
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

		/*
		 * Commands: /nick
		 * 
		 * */
		public void sendMessage() {
				chat.addActionListener(e -> {
					log.append(userName+": " + chat.getText() + '\n');
					consoleData = chat.getText();
					chat.setText("");
					consoleData = clientID + "#" + consoleData;
					toServer.println(consoleData);
				});

		}
		

		public void run() {
			try {
				while (consoleData != null || consoleData.equals("null")) {
					sendMessage();
					serverData = fromClient.readLine();
					log.append("Server: " + serverData);
					System.out.print("Client[" + clientID + "]"+ userName + ": ");
				}
				fromClient.close();
				toServer.close();
			} catch (UnknownHostException e) {
				System.out.println("Don't know about host\n");
			} catch (IOException e) {
				System.out.println("I/O Exception\n");
			}
		}
	}

	public void disconnectClient() {
		try {
			toServer.println(clientID + "#Disconnecting");
			toServer.println(sock.getInetAddress() + " at port " + sock.getPort());
			toServer.close();
			sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendGame() {
		
		String gameBoard = clientModel.sendGameToServer();
		System.out.println(gameBoard);
		consoleData = clientID + "#" + gameBoard;
		
		toServer.println(consoleData);
		// TODO Auto-generated method stub
		
	}


}