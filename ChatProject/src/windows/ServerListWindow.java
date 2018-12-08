package windows;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;

import dataBase.DataBase;

public class ServerListWindow extends JFrame {
	
	private DataBase dataBase = null;
	private JButton btnCreateServer;
	private JButton btnConnect;
	
	public ServerListWindow() {
		setSize(449, 339);
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
		//serverListTable.setModel(dataBase.getServers());
		
		try {
			dataBase = new DataBase();
		} catch (ClassNotFoundException | SQLException e) {
			JOptionPane.showMessageDialog(this, "Failed on connecting to data base", "Data Base Error", JOptionPane.ERROR_MESSAGE);
		}
		
		dataBase.setColumnNames("SELECT name, ip, port FROM servers");
		dataBase.setData("SELECT name, ip, port FROM servers");
		JTable serverListTable = new JTable(dataBase.getData(), dataBase.getColumnNames());
		serverList.addTab("Server list", serverListTable);
		
		btnCreateServer = new JButton("Create Server");
		springLayout.putConstraint(SpringLayout.NORTH, btnCreateServer, 8, SpringLayout.SOUTH, serverList);
		springLayout.putConstraint(SpringLayout.WEST, btnCreateServer, 0, SpringLayout.WEST, serverList);
		getContentPane().add(btnCreateServer);
		
		btnConnect = new JButton("Connect");
		springLayout.putConstraint(SpringLayout.SOUTH, btnConnect, 0, SpringLayout.SOUTH, btnCreateServer);
		springLayout.putConstraint(SpringLayout.EAST, btnConnect, 0, SpringLayout.EAST, serverList);
		getContentPane().add(btnConnect);
		
		setVisible(true);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeDBConnection();
			}
		});
	}
	
	public JButton getBtnCreateServer() {
		return btnCreateServer;
	}
	
	public JButton getBtnConnect() {
		return btnConnect;
	}
	
	public void closeDBConnection() {
		if (dataBase != null) {
			dataBase.close();
		}
	}
	
}
