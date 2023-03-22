package game;

import java.net.Socket;

public class ServerThread extends Thread{
	private Socket socket = null;
	
	public ServerThread(Socket socket) {
		super("ServerThread");
		this.socket = socket;
		System.out.println("new user joined");
	}
	
	public void run() {
		while(socket.isConnected()) {
			System.out.println("running client");
		}
	}
}
