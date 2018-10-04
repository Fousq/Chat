package ClientSide;

import java.net.Socket;
import java.net.UnknownHostException;
import java.net.InetAddress;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;

public class Client {
	
	private BufferedReader input;
	private BufferedWriter output;
	private Socket client;
	
	public Client(String IP, int port) throws UnknownHostException, IOException {
		client = new Socket(InetAddress.getByName(IP), port);
		input = new BufferedReader(new InputStreamReader(client.getInputStream()));
		output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
	}
	
	public void send(String message) throws IOException {
		output.write(message + "\n");
		output.flush();
	}
	
	public BufferedReader getInputStream() {
		return input;
	}
	
	public void closeSocket() {
		try {
			client.close();
			input.close();
			output.close();
		} catch (IOException e) { }
	}
	
}
