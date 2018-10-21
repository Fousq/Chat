package ServerSide;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.SQLException;
import java.net.DatagramPacket;
import java.util.ArrayList;

import DataBase.DataBase;

public class Server {
	
	private DatagramSocket server;
	private String serverName;
	private int serverPort;
	private Thread serverThread;
	private boolean running = false;
	private ArrayList <ServerClient> serverClients = new ArrayList <ServerClient>();
	private DataBase dataBase = null;
	
	public Server(String name, int port) throws IOException, SQLException, ClassNotFoundException {
		server = new DatagramSocket(port);
		serverName = name;
		serverPort = port;
		//server.connect(InetAddress.getByName(IP), port);
		dataBase = new DataBase();
		serverThread = new Thread(new Runnable() {
			@Override
			public void run() {
				running = true;
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
	
	private DatagramPacket process(DatagramPacket packet){
		String message = new String(packet.getData());
		if (message.startsWith("/user/")) {
			serverClients.add(new ServerClient(packet.getAddress(), packet.getPort()));
			byte [] data = new byte [1024];
			return new DatagramPacket(data, data.length, packet.getAddress(), packet.getPort());
		} else if (message.startsWith("/admin/")) {
			try {
				dataBase.addServer(serverName, serverPort, packet.getAddress().toString(), packet.getPort());
				serverClients.add(new ServerClient(packet.getAddress(), packet.getPort()));
			} catch (SQLException e) { }
			byte [] data = new byte [1024];
			return new DatagramPacket(data, data.length, packet.getAddress(), packet.getPort());
		}
		return packet;
	}
	
	public void closeSocket() throws IOException, SQLException{
		server.close();
		dataBase.deleteServer(serverName, serverPort);
	}
	
	public boolean isRunning() {
		return running;
	}
	
}
