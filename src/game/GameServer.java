package game;

import java.io.IOException;
import java.net.ServerSocket;

public class GameServer {
	public static void main(String[] args) {
		try(ServerSocket serverSocket = new ServerSocket(3000)){
			System.out.println("waiting for connection..");
			while(true) {
				new ServerThread(serverSocket.accept()).start();

			}
		}
		catch(IOException e){
			System.out.println("cant connect to port 1254");
		}
	}
}
