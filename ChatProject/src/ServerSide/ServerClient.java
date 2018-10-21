package ServerSide;

import java.net.InetAddress;

public class ServerClient {
	
	private InetAddress IP;
	private int port;
	
	ServerClient(InetAddress IP, int port) {
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
