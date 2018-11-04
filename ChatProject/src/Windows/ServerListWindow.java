package Windows;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;

import DataBase.DataBase;
import javax.swing.JButton;

public class ServerListWindow extends JFrame {
	
	private DataBase dataBase = null;
	private JButton btnCreateServer;
	private JButton btnConnect;
	
	public ServerListWindow() {
		//setSize(240, 480);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		JTabbedPane serverList = new JTabbedPane(JTabbedPane.TOP);
		springLayout.putConstraint(SpringLayout.NORTH, serverList, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, serverList, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, serverList, 251, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, serverList, 424, SpringLayout.WEST, getContentPane());
		getContentPane().add(serverList);
		//new JScrollPane(serverList);
		
		JTable serverListTable = new JTable(dataBase.getCountOfServers(), 3);
		serverListTable.setModel(dataBase.getServers());
		
		try {
			dataBase = new DataBase();
		} catch (ClassNotFoundException | SQLException e) { }
		
		serverList.addTab("Test", new JTable(3, 3));
		
		btnCreateServer = new JButton("Create Server");
		springLayout.putConstraint(SpringLayout.NORTH, btnCreateServer, 8, SpringLayout.SOUTH, serverList);
		springLayout.putConstraint(SpringLayout.WEST, btnCreateServer, 0, SpringLayout.WEST, serverList);
		getContentPane().add(btnCreateServer);
		
		btnConnect = new JButton("Connect");
		springLayout.putConstraint(SpringLayout.SOUTH, btnConnect, 0, SpringLayout.SOUTH, btnCreateServer);
		springLayout.putConstraint(SpringLayout.EAST, btnConnect, 0, SpringLayout.EAST, serverList);
		getContentPane().add(btnConnect);
		
		setVisible(true);
	}
	
	public JButton getBtnCreateServer() {
		return btnCreateServer;
	}
	
	public JButton getBtnConnect() {
		return btnConnect;
	}
}
