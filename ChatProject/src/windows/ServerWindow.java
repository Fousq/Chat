package windows;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.apache.commons.lang3.StringUtils;

import serverSide.Server;

import dataBase.DataBase;

public class ServerWindow extends JFrame{
	
	private SpringLayout springLayout;
	private JTextField portTF;
	private JButton btnLaunch;
	private JLabel lblName;
	private JLabel lblPort;
	private JTextField NameTextField;
	private Server server;
	private DataBase dataBase = null;
	
	public ServerWindow() {
		setSize(239, 189);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		lblPort = new JLabel("Port: ");
		springLayout.putConstraint(SpringLayout.NORTH, lblPort, 34, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblPort, -148, SpringLayout.EAST, getContentPane());
		getContentPane().add(lblPort);
		
		portTF = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, portTF, 31, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, portTF, 6, SpringLayout.EAST, lblPort);
		springLayout.putConstraint(SpringLayout.EAST, portTF, -42, SpringLayout.EAST, getContentPane());
		getContentPane().add(portTF);
		portTF.setColumns(10);
		
		btnLaunch = new JButton("Launch");
		springLayout.putConstraint(SpringLayout.WEST, btnLaunch, 78, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnLaunch, -10, SpringLayout.SOUTH, getContentPane());
		getContentPane().add(btnLaunch);
		
		lblName = new JLabel("Name: ");
		springLayout.putConstraint(SpringLayout.NORTH, lblName, 31, SpringLayout.SOUTH, lblPort);
		springLayout.putConstraint(SpringLayout.EAST, lblName, 0, SpringLayout.EAST, lblPort);
		getContentPane().add(lblName);
		
		NameTextField = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, NameTextField, 22, SpringLayout.SOUTH, portTF);
		springLayout.putConstraint(SpringLayout.WEST, NameTextField, 0, SpringLayout.WEST, portTF);
		springLayout.putConstraint(SpringLayout.EAST, NameTextField, 0, SpringLayout.EAST, portTF);
		getContentPane().add(NameTextField);
		NameTextField.setColumns(10);
		
		try {
			dataBase = new DataBase();
		} catch (ClassNotFoundException | SQLException e) {
			JOptionPane.showMessageDialog(this, "Failed on connecting to data base", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		setVisible(true);	
	}
	
	public JButton getBtnLaunch() {
		return btnLaunch;
	}
	
	public String getName() {
		return NameTextField.getText();
	}
	
	public String getPort() {
		return portTF.getText();
	}
	
	public boolean isPort(String port) {
		return StringUtils.isNumeric(port);
	}
	
//	private boolean isIP() {
//		if (IPTextField.getText().equals("localhost")) {
//			return true;
//		}
//		String [] parts = IPTextField.getText().split("\\.");
//		if (parts.length > 4 || parts.length < 4) {
//			return false;
//		}
//		for (int i = 0; i < parts.length; i++) {
//			if (!StringUtils.isNumeric(parts[i])) {
//				return false; 
//			}
//			else if (Integer.parseInt(parts[i]) > 255) {
//				return false;
//			}
//		}
//		return true;
//	}
	
	public void closeSocket() {
		try {
			server.closeSocket();
		} catch (IOException e) { 
			return;
		} catch (SQLException e) {
			return;
		}
	}
	
	public void launchServer(String name, String port) {
			try {
				server = new Server(name, Integer.parseInt(port));
				dataBase.addServer(name, Integer.parseInt(port), server.getServerIP());
			} catch (NumberFormatException e) { 
				return;
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(ServerWindow.this, "Failed on connecting the port", "Socket port", JOptionPane.ERROR_MESSAGE);
				return;
			} catch (SQLException e) {
				e.printStackTrace();
				return;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return;
			}
	}
	
	public boolean isRunning() {
		return (server != null)? server.isRunning() : false;
	}
	
	public boolean isNameTaken(String name) {
		return dataBase.isNameTaken(name, "servers");
	}
	
}
