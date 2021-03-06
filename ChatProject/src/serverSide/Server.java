package serverSide;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.SQLException;
import java.net.DatagramPacket;
import java.util.ArrayList;

import dataBase.DataBase;

public class Server {
	
	private DatagramSocket server;
	private String serverName;
	private int serverPort;
	private Thread serverThread;
	private Thread receivingThread;
	private Thread manage;
	private boolean running = false;
	private boolean readyToSend = true;
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
				manage();
				receive();
			}
		}, "serverThread");
		serverThread.start();
	}
	
	private void receive() {
		receivingThread = new Thread(new Runnable() {
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
	
	private void manage() {
		manage = new Thread(new Runnable() {
			@Override 
			public void run() {
				try {
					while (true) {
						receivingThread.wait();
						byte [] data = new byte [1024];
						DatagramPacket packet = new DatagramPacket(data, data.length);
						server.receive(packet);
						String command = new String(packet.getData());
						if (command.compareTo("!close") == 0) {
							readyToSend = false;
							closeSocket();
						}
						if (readyToSend) {
							receivingThread.notify();
						}
					}
				} catch (IOException | SQLException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}
	private DatagramPacket process(DatagramPacket packet){
		String message = new String(packet.getData());
		if (message.startsWith("/user/")) {
			serverClients.add(new ServerClient(packet.getAddress(), packet.getPort()));
			byte [] data = new byte [1024];
			return new DatagramPacket(data, data.length, packet.getAddress(), packet.getPort());
		} else if (message.startsWith("/admin/")) {
			try {
				dataBase.boundServerToUser(message.substring(6), packet.getAddress().toString(), packet.getPort(), serverName);
				serverClients.add(new ServerClient(packet.getAddress(), packet.getPort()));
			} catch (SQLException e) { }
			byte [] data = new byte [1024];
			return new DatagramPacket(data, data.length, packet.getAddress(), packet.getPort());
		}
		return packet;
	}
	
	public void closeSocket() throws IOException, SQLException{
		dataBase.deleteServer(serverName, serverPort);
		server.close();
		dataBase.close();
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public String getServerIP() {
		return String.valueOf(server.getLocalAddress());
	}
	
}
