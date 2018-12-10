package windows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import clientSide.User;

public class Windows {
	
	private User user = null;
	private LoginWindow loginWindow = null;
	private ServerListWindow serverListWindow = null;
	private RegistrationWindow registrationWindow = null;
	private ServerWindow serverWindow = null;
	private MainWindow mainWindow = null;
	
	public Windows() {
		loginWindow = new LoginWindow();
		loginWindowEvents();
	}
	
	private void loginWindowEvents() {
		loginWindow.getBtnLogIn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (loginWindow.getNickName().isEmpty() || loginWindow.getPassword().isEmpty()) {
					JOptionPane.showMessageDialog(loginWindow, "The nick name or password field is empty", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				} else if (!loginWindow.isUser(loginWindow.getNickName(), loginWindow.getPassword())) {
					JOptionPane.showMessageDialog(loginWindow, "Such user doesn't exist", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				user = new User(loginWindow.getName());
				loginWindow.closeDBConnection();
				loginWindow.dispose();
				loginWindow = null;
				
				serverListWindow = new ServerListWindow();
				serverListWindowEvents();
			}
		});
		
		loginWindow.getBtnSighUp().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loginWindow.closeDBConnection();
				loginWindow.dispose();
				loginWindow = null;
				
				registrationWindow = new RegistrationWindow();
				registrationWindowEvents();
			}
		});
	}
	
	private void registrationWindowEvents() {
		registrationWindow.getBtnRegistrate().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (registrationWindow.getNickName().isEmpty() || registrationWindow.getPassword().isEmpty()) {
					JOptionPane.showMessageDialog(registrationWindow, "Nick name or password field is empty", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				} else if (registrationWindow.doesUserExist(registrationWindow.getNickName())) {
					JOptionPane.showMessageDialog(registrationWindow, "Such nick name has been already taken", "Warning", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				try {
					registrationWindow.addNewUser(registrationWindow.getNickName(), registrationWindow.getPassword());
				} catch (SQLException err) {
					err.printStackTrace();
					JOptionPane.showMessageDialog(registrationWindow, "Failed on adding new user", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				registrationWindow.closeDBConnection();
				registrationWindow.dispose();
				
				loginWindow = new LoginWindow();
				loginWindowEvents();
			}
		});
	}
	
	private void serverListWindowEvents() {
		serverListWindow.getBtnConnect().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow = new MainWindow(serverListWindow.getSelectedServerPort(),
						serverListWindow.getSelectedServerIP(), user.getName(), "/user/");
			}
		});
		
		serverListWindow.getBtnCreateServer().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				serverWindow = new ServerWindow();
				serverWindowEvents();
			}
		});
	}
	
	private void serverWindowEvents() {
		serverWindow.getBtnLaunch().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (serverWindow.isPort(serverWindow.getPort()) && !serverWindow.getName().isEmpty()) {
					serverWindow.launchServer(serverWindow.getName(), serverWindow.getPort());
				}
				
				mainWindow = new MainWindow(Integer.parseInt(serverWindow.getPort()), 
						"localhost", user.getName(), "/admin/");
			}
		});
	}
	
}
