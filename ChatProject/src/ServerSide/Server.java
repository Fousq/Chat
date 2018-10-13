package ServerSide;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.util.ArrayList;

public class Server {
	
	private DatagramSocket server;
	private Thread serverThread;
	private ArrayList <ServerClient> serverClients = new ArrayList <ServerClient>();
	
	public Server(int port) throws IOException{
		server = new DatagramSocket(port);
		System.out.println("Server has started");
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
						byte [] data = new byte [1024];
						DatagramPacket packet = new DatagramPacket(data, data.length);
						server.receive(packet);
						data = process(packet).getData();
						for (ServerClient serverClient : serverClients) {
							server.send(new DatagramPacket(data, data.length, serverClient.getIP(), serverClient.getPort()));
						}
					}
				} catch (IOException e) {}
			}
		});
		
		receivingThread.start();
	}
	
	private DatagramPacket process(DatagramPacket packet) {
		String message = new String(packet.getData());
		if (message.startsWith("/user/")) {
			serverClients.add(new ServerClient(1, packet.getAddress(), packet.getPort()));
			message = "";
			byte [] data = new byte [1024];
			return new DatagramPacket(data, data.length, packet.getAddress(), packet.getPort());
		}
		return packet;
	}
	
	public void closeSocket() throws IOException{
		server.close();
	}
	
}
