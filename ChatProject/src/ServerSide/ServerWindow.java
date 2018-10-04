package ServerSide;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.ActiveEvent;

import org.apache.commons.lang3.StringUtils;

public class ServerWindow extends JFrame{
	
	private JTextField portTF;
	private JButton btnLaunch;
	private Server server;
	
	public ServerWindow() {
		createWelcomePanel();
		
		setSize(239, 189);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		JLabel lblPort = new JLabel("Port: ");
		springLayout.putConstraint(SpringLayout.NORTH, lblPort, 34, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblPort, -148, SpringLayout.EAST, getContentPane());
		getContentPane().add(lblPort);
		
		portTF = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, portTF, -3, SpringLayout.NORTH, lblPort);
		springLayout.putConstraint(SpringLayout.WEST, portTF, 6, SpringLayout.EAST, lblPort);
		getContentPane().add(portTF);
		portTF.setColumns(10);
		
		btnLaunch = new JButton("Launch");
		springLayout.putConstraint(SpringLayout.WEST, btnLaunch, 77, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnLaunch, -37, SpringLayout.SOUTH, getContentPane());
		getContentPane().add(btnLaunch);
		
		JLabel lblServerHasStarted = new JLabel("Server has started working");
		springLayout.putConstraint(SpringLayout.NORTH, lblServerHasStarted, 6, SpringLayout.SOUTH, lblPort);
		springLayout.putConstraint(SpringLayout.WEST, lblServerHasStarted, 0, SpringLayout.WEST, lblPort);
		getContentPane().add(lblServerHasStarted);
		lblServerHasStarted.setVisible(false);
		
		setVisible(true);
		
		btnLaunch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (isPort()) {
					try {
						server = new Server(Integer.parseInt(portTF.getText()));
					} catch (NumberFormatException e) { 
						
					} catch (IOException e) {
						JOptionPane.showMessageDialog(ServerWindow.this, "Failed on connecting the port", "Socket port", JOptionPane.ERROR_MESSAGE);
					}
					lblPort.setVisible(false);
					portTF.setVisible(false);
					btnLaunch.setVisible(false);
					lblServerHasStarted.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(ServerWindow.this, "Please enter the port to connect the server", "Port Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
			
	}
	
	private void createWelcomePanel() {
		JOptionPane.showMessageDialog(this, "This is a simple project to learn how to code.", "Welcome", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public boolean isPort() {
		return StringUtils.isNumeric(portTF.getText());
	}
	
	public void closeSocket() {
		try {
			server.closeSocket();
		} catch (IOException e) { }
	}
	
}
