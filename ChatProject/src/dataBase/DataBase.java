package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class DataBase {
	
	private Connection connection = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	private ResultSetMetaData resultSetMetaData = null;
	private PreparedStatement preparedStatement = null;
	private final int TRUE = 1;
	private final int FALSE = 0;
	private Vector<String> columnsName = null;
	private Vector<Vector<Object>> datas = null;
	
	public DataBase() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clientsdb?useSSL=false"
				+ "&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false"
				+ "&serverTimezone=UTC",
				"root", "123456fifa");
	}
	
	public void addUser(String nickName, String password) throws SQLException {
		preparedStatement = connection.prepareStatement("INSERT INTO users (nickName, password)"
				+ " VALUES (?, ?);");
		preparedStatement.setString(1, nickName);
		preparedStatement.setString(2, password);
		preparedStatement.executeUpdate();
	}
	
	public void addServer(String name, int port, String IP) throws SQLException {
		preparedStatement = connection.prepareStatement("INSERT INTO severs (name, port, ip)"
				+ " VALUES (?, ?, ?);");
		preparedStatement.setString(1, name);
		preparedStatement.setInt(2, port);
		preparedStatement.setString(3, IP);
		preparedStatement.executeUpdate();
	}
	
	public void boundServerToUser(String adminName, String adminIP, int adminPort,
			String serverName) throws SQLException {
		preparedStatement = connection.prepareStatement("UPDATE servers "
				+ "SET admin = ?, adminIP = ?, adminPort = ? "
				+ "WHERE name = ?");
		preparedStatement.setString(1, adminName);
		preparedStatement.setString(2, adminIP);
		preparedStatement.setInt(3, adminPort);
		preparedStatement.setString(4, serverName);
		preparedStatement.executeUpdate();
	}
	
	public void deleteServer(String name, int port) throws SQLException {
		preparedStatement = connection.prepareStatement("DELETE FROM servers "
				+ "WHERE name = ? AND port = ?;");
		preparedStatement.setString(1, name);
		preparedStatement.setInt(2, port);
	}
	
	public void ban(String IP, int port) throws SQLException {
		preparedStatement = connection.prepareStatement("UPDATE users"
				+ " SET banned = ?"
				+ " WHERE IP = ? AND port = ?;");
		preparedStatement.setInt(1, TRUE);
		preparedStatement.setString(2, IP);
		preparedStatement.setInt(3, port);
		preparedStatement.executeUpdate();
	}
	
	public void delete(String IP, int port) throws SQLException {
		preparedStatement = connection.prepareStatement("DELETE FROM users "
				+ " WHERE IP = ? AND port = ?");
		preparedStatement.setString(1, IP);
		preparedStatement.setInt(2, port);
		preparedStatement.executeUpdate();
	}
	
	public void close() {
		try {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {	return; }
	}
	
	public boolean isUserExist(String name, String password) {
		boolean exist;
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM users "
					+ "WHERE nickName = ? AND password = ?;");
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, password);
			resultSet = preparedStatement.executeQuery();
			exist = resultSet.first(); 
		} catch (SQLException e) { return true; }
		return exist;
	}
	
	public boolean isNameTaken(String name, String dbName) {
		boolean exist;
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM " + dbName
					+ " WHERE nickName = ?");
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();
			exist = resultSet.first();
		} catch (SQLException e) { return true; }
		return exist;
	}
	
	public int getCountOfServers() {
		int count = 0;
		try {
			resultSet = statement.executeQuery("SELECT COUNT(id) FROM servers;");
			count = resultSet.getInt(1);
		} catch (SQLException e) { }
		return count;
	}
	
//	public DefaultTableModel getServers() {
//		DefaultTableModel dftm = null;
//		try {
//			resultSet = statement.executeQuery("SELECT name, ip, port FROM servers");
//			dftm = new DefaultTableModel(getData(resultSet, resultSet.getMetaData()),
//					getColumnNames(resultSet, resultSet.getMetaData()));
//		} catch (SQLException e) {
//		}
//		return dftm;
//	}
	
	public void setColumnNames(String sqlCommand) {
		columnsName = new Vector<>();
		try {
			preparedStatement = connection.prepareStatement(sqlCommand);
			resultSet = preparedStatement.executeQuery();
			resultSetMetaData = resultSet.getMetaData();
			int columnCount = resultSetMetaData.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				columnsName.add(resultSetMetaData.getColumnName(i));
			}
		} catch (SQLException e) { }
	}
	
	public void setData(String sqlCommand) {
		datas = new Vector<>();
		try {
			preparedStatement = connection.prepareStatement(sqlCommand);
			resultSet = preparedStatement.executeQuery();
			resultSetMetaData = resultSet.getMetaData();
			int columnCount = resultSetMetaData.getColumnCount();
			Vector<Object> data = new Vector<>();
			while (resultSet.next()) {
				for (int i = 1; i <= columnCount; i++) {
					data.add(resultSet.getObject(i));
				}
				datas.add(data);
			}
		} catch (SQLException e) {	}
	}
	
	public Vector<String> getColumnNames() {
		if (columnsName == null) {
			return null;
		}
		return columnsName;
	}
	
	public Vector<Vector<Object>> getData() {
		if (datas == null) {
			return null;
		}
		return datas;
	}
	
}
