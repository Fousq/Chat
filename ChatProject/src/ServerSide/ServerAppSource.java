package ServerSide;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ServerAppSource {
	
	private static ServerWindow serverWindow;
	
	public static void main(String[] args) {
		serverWindow = new ServerWindow();
		serverWindow.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (serverWindow.isRunning()) {
					serverWindow.closeSocket();
				}
				//serverWindow.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
			}
		});
	}

}
