import java.awt.event.*;

import ClientSide.Client;

public class Source {

	static LoginWindow loginWindow;
	static MainWindow mainWindow;
	static Client client;
	
	public static void main(String[] args) {
		loginWindow = new LoginWindow();
		events();
	}
	
	private static void events() {		
		loginWindow.getBtnJoin().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!loginWindow.areTFsEmpty()) {
					mainWindow = new MainWindow(loginWindow.getPort(), loginWindow.getIP(), loginWindow.getNickName());
					loginWindow.dispose();
					mainWindowEvents();
				}
			}
		});
	}
	
	private static void mainWindowEvents() {
		mainWindow.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mainWindow.closeSocket();
				mainWindow.dispose();
			}
		});
	}

}
