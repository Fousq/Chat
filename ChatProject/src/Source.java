import java.awt.event.*;

import ClientSide.Client;
import Windows.ServerWindow;

public class Source {

	static LoginWindow loginWindow;
	static MainWindow mainWindow;
	static Client client;
	static ServerWindow serverWindow;

	public static void main(String[] args) {
		loginWindow = new LoginWindow();
		events();
	}

	private static void events() {
		loginWindow.getBtnJoin().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!loginWindow.areTFsEmpty()) {
					mainWindow = new MainWindow(loginWindow.getPort(), loginWindow.getIP(), loginWindow.getNickName(), "/user/");
					loginWindow.dispose();
					mainWindowEvents();
				}
			}
		});

		loginWindow.getBtnCreateServer().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				serverWindow = new ServerWindow();
				serverWindowEvents();
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
	
	private static void serverWindowEvents() {
		serverWindow.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (serverWindow.isRunning()) {
					serverWindow.closeSocket();
				}
			}
		});
		
		serverWindow.getBtnLaunch().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				serverWindow.launchServer(serverWindow.getName(), serverWindow.getPort());
				mainWindow = new MainWindow(Integer.parseInt(serverWindow.getPort()), "localhost", "admin", "/admin/");
			}
		});
	}

}
