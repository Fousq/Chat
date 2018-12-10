package windows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import clientSide.Client;


public class MainWindow extends JFrame{
	
	private JTextField textField;
	private Client client;
	private DateFormat time = new SimpleDateFormat("hh:mm:ss");
	private JTextArea textArea;
	private JTable usersList;
	private JMenuBar menuBar;
	private JMenuItem exitServerItem;
	
	public MainWindow(int port, String IP, String nickName, String conID) {
		setSize(712, 410);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		textField = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, textField, 337, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, textField, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textField, 458, SpringLayout.WEST, getContentPane());
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnSend = new JButton("Send");
		springLayout.putConstraint(SpringLayout.NORTH, btnSend, 337, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnSend, 6, SpringLayout.EAST, textField);
		springLayout.putConstraint(SpringLayout.EAST, btnSend, 85, SpringLayout.EAST, textField);
		getContentPane().add(btnSend);
		
		textArea = new JTextArea();
		textArea.setColumns(106);
		textArea.setRows(26);
		springLayout.putConstraint(SpringLayout.NORTH, textArea, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, textArea, 0, SpringLayout.WEST, textField);
		textArea.setEditable(false);
		getContentPane().add(textArea);
		
		usersList = new JTable();
		springLayout.putConstraint(SpringLayout.NORTH, usersList, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, usersList, 6, SpringLayout.EAST, textArea);
		springLayout.putConstraint(SpringLayout.SOUTH, usersList, 0, SpringLayout.SOUTH, textField);
		springLayout.putConstraint(SpringLayout.EAST, usersList, -10, SpringLayout.EAST, getContentPane());
		usersList.setSize(40, 30);
		getContentPane().add(usersList);
		
		createMenuBar(conID);
		
		setVisible(true);
		
		try {
			client = new Client(IP, port);
			client.send(conID + nickName);
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(this, "Failed on connecting to the server", "Socket Error", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Failed on getting IO streams", "Socket Error", JOptionPane.ERROR_MESSAGE);
		}
		
		btnSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!isTextFieldEmpty()) {
					try {
						client.send(time.format(new Date()) + "." + nickName + ": " + textField.getText() + "\n");
						textField.setText(null);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(MainWindow.this, "Failed on sending", "Socket Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		receive();
	}
	
	private boolean isTextFieldEmpty() {
		return textField.getText().isEmpty();
	}
	
	private void receive() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						textArea.append(client.receive());
					} catch (IOException e) {
						JOptionPane.showMessageDialog(MainWindow.this, "Failed on getting message from server", 
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}).start();
	}
	
	public void createMenuBar(String conID) {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu serverMenu = new JMenu("Server");
		if (conID.startsWith("/admin/")) {
			JMenuItem manageServersUsersItem = new JMenuItem("Manage users");
			manageServersUsersItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
				}
			});
			serverMenu.add(manageServersUsersItem);
			
			JMenuItem closeServerItem = new JMenuItem("Close server");
			closeServerItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						client.send("!close");
					} catch (IOException err) {
						JOptionPane.showMessageDialog(MainWindow.this, "Failed on sending the command",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			
			serverMenu.add(closeServerItem);
		}
		exitServerItem = new JMenuItem("Exit server");
		serverMenu.add(exitServerItem);
		
		menuBar.add(serverMenu);
	}
	
	public JMenuItem getExitServerItem() {
		return exitServerItem;
	}
	
	public void closeSocket() {
		client.closeSocket();
	}
}
