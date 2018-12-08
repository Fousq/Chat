package clientSide;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.UnknownHostException;
import java.net.InetAddress;
import java.io.IOException;

public class Client {
	private DatagramSocket client;
	private InetAddress IP = null;
	private int port;
	
	public Client(String IP, int port) throws UnknownHostException, IOException {
		client = new DatagramSocket();
		this.IP = InetAddress.getByName(IP);
		this.port = port;
	}
	
	public void send(String message) throws IOException {
		byte [] data = message.getBytes();
		DatagramPacket packet = new DatagramPacket(data, data.length, IP, port);
		client.send(packet);
	}
	
	public String receive() throws IOException {
		byte [] data = new byte [1024];
		DatagramPacket packet = new DatagramPacket(data, data.length, IP, port);
		client.receive(packet);
		data = packet.getData();
		
		return new String(data, 0, data.length);
	}
	
	public void closeSocket() {
			client.close();
	}
	
	public String getUserIP() {
		return String.valueOf(IP);
	}

}
