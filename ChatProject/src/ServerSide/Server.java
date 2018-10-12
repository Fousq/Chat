package ServerSide;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;

public class Server {
	
	private DatagramSocket server;
	private Thread serverThread;
	
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
						data = packet.getData();
						server.send(process(packet));
					}
				} catch (IOException e) {}
			}
		});
		
		receivingThread.start();
	}
	
	private DatagramPacket process(DatagramPacket packet) {
		String message = new String(packet.getData());
		if (message.startsWith("/user/")) {
			message = message + " " + String.valueOf(packet.getAddress()) + ":" + packet.getPort();
			byte [] data = message.getBytes();
			return new DatagramPacket(data, data.length, packet.getAddress(), packet.getPort());
		}
		return packet;
	}
	
	public void closeSocket() throws IOException{
		server.close();
	}
	
}
