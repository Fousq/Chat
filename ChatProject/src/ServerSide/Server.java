package ServerSide;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	private ServerSocket server;
	private Socket client;
	private BufferedReader input;
	private BufferedWriter output;
	private Thread serverThread;
	
	public Server(int port) throws IOException{
		server = new ServerSocket(port);
		System.out.println("Server has started");
		client = server.accept();
		input = new BufferedReader(new InputStreamReader(client.getInputStream()));
		output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		serverThread = new Thread(new Runnable() {
			@Override
			public void run() {
				receive();
			}
		}, "serverThread");
		serverThread.start();
	}
	
	private void receive() {
		Thread receivingThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						String message = input.readLine();
						output.write(message + "\n");
						output.flush();
					}
				} catch (IOException e) {}
			}
		});
		
		receivingThread.start();
	}
	
	public void closeSocket() throws IOException{
		server.close();
	}
	
}
