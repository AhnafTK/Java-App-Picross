package game;

import java.net.Socket;

// handles client
public class ServerThread extends Thread{
	private Socket socket = null;
	
	public ServerThread(Socket socket) {
		super("ServerThread");
		this.socket = socket;
		System.out.println("new user joined");
	}
	
	public void run() {
		System.out.println("running client");


	}
}
