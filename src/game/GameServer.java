package game;

import java.io.IOException;
import java.net.ServerSocket;

public class GameServer {
	static ServerSocket servsock;
	public GameServer() {
		
	}
	
	public GameServer(String port) {
		try(ServerSocket serverSocket = new ServerSocket(Integer.valueOf(port))){
			System.out.println("waiting for connection..");
			while(true) {
				Thread servDaemon = new Thread(new ServerThread(serverSocket.accept()));
				servDaemon.start();
			}
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
