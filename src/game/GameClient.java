package game;

import java.io.*;
import java.net.*;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GameClient {
	static Socket sock;
	PrintStream toServer = null;
	BufferedReader fromClient = null, consoleIn = null;
	String consoleData, serverData, clientID;
	private JTextArea log;

	public GameClient(JTextArea log) {
		this.log = log;
	}

	public GameClient(String hostName, int port, JTextArea log, JTextField chat) {
		this(log);

		try {
			sock = new Socket(hostName, port);
			toServer = new PrintStream(sock.getOutputStream(), true);
			fromClient = new BufferedReader(new InputStreamReader(sock.getInputStream())); // Reader for the socket
																							// communication
			clientID = fromClient.readLine();
			System.out.print("Client[" + clientID + "]: ");

			Thread thread = new Thread(new ReceiveMessage(log));
			thread.start();

			chat.addActionListener(e -> {
				log.append("Me: " + chat.getText() + '\n');
				consoleData = chat.getText();
				chat.setText("");
				consoleData = clientID + "#" + consoleData;
				System.out.println("console data " + consoleData);
				toServer.println(consoleData);
				toServer.flush();
			});

			if (consoleData != null) {
				consoleData = clientID + "#" + consoleData;
				System.out.println("console data " + consoleData);
				toServer.println(consoleData);
				toServer.flush();
				serverData = fromClient.readLine();
				log.append("Server: " + serverData);
				System.out.print("Client[" + clientID + "]: ");

			} else {
				consoleData = clientID + "#null";
				toServer.println(consoleData);
			}

			fromClient.close();
			toServer.close();
		} catch (UnknownHostException e) {
			System.out.println("Don't know about host: " + hostName + " on port: " + port + "\n");
		} catch (IOException e) {
			System.out.println("Couldn't get I/O for the connection to: " + hostName + " on port: " + port + "\n");
		}
	}

	class ReceiveMessage implements Runnable {
		private JTextArea log;

		public ReceiveMessage(JTextArea log) {
			this.log = log;
		}

		public void run() {
//			while (consoleData != null || consoleData.equals("null")) {
//				log.append("Server: " + consoleData + '\n');
//			}

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
}