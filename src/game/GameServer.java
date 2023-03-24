package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer implements Runnable {
	static ServerSocket servsock;
	private Socket sock;
	static int nclient = 0, nclients = 0;

	public GameServer() {

	}

	public GameServer(String port) {
		System.out.println("Starting server");
		try {
			servsock = new ServerSocket(Integer.valueOf(port));
			Thread servDaemon = new Thread(new GameServer());
			servDaemon.start();
			System.out.println("Server running on " + " at port " + port + "!");
		} catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}

	}

	@Override
	public void run() {
		System.out.println("RUNNING");
		// TODO Auto-generated method stub
		for (;;) {
			try {
				sock = servsock.accept();
				nclient += 1;
				nclients += 1;
				System.out.println("Connecting " + sock.getInetAddress() + " at port " + sock.getPort() + ".");
			} catch (IOException ioe) {
				System.out.println(ioe);
			}
			Worked w = new Worked(sock, nclient);
			w.start();
		}
		
	}
	
	class Worked extends Thread {

		/**
		 * Socket variable.
		 */
		Socket sock;

		/**
		 * Integers for client and positions.
		 */
		int clientid, poscerq;

		/**
		 * String for data.
		 */
		String strcliid, dadosCliente;

		/**
		 * Default constructor.
		 * 
		 * @param s       Socket
		 * @param nclient Number of client.
		 */
		public Worked(Socket s, int nclient) {
			sock = s;
			clientid = nclient;
		}

		/**
		 * Run method.
		 */
		public void run() {
			String data;
			PrintStream out = null;
			BufferedReader in;
			try {
				out = new PrintStream(sock.getOutputStream());
				in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				out.println(clientid);
				data = in.readLine();
				poscerq = data.indexOf("#");
				strcliid = data.substring(0, poscerq);
				dadosCliente = data.substring(poscerq + 1, data.length());
				while (!dadosCliente.equals("end")) {
					System.out.println("Cli[" + strcliid + "]: " + data);
					out.println("String \"" + data + "\" received.");
					out.flush();
					data = in.readLine();
					poscerq = data.indexOf("#");
					strcliid = data.substring(0, poscerq);
					dadosCliente = data.substring(poscerq + 1, data.length());
				}
				System.out.println("Disconecting " + sock.getInetAddress() + "!");
				nclients -= 1;
				System.out.println("Current client number: " + nclients);
				if (nclients == 0) {
					System.out.println("Ending server...");
					sock.close();
					System.exit(0);
				}
			} catch (IOException ioe) {
				System.out.println(ioe);
			}
		}
	}

}

	

