package ServerSide;

import java.net.InetAddress;

public class ServerClient {
	
	private final int ID;
	private InetAddress IP;
	private int port;
	
	ServerClient(final int ID, InetAddress IP, int port) {
		this.ID = ID;
		this.IP = IP;
		this.port = port;
	}
	
	public InetAddress getIP() {
		return IP;
	}
	
	public int getPort() {
		return port;
	}
	
}
