package DataBase;

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
	private PreparedStatement preparedStatement = null;
	private final int TRUE = 1;
	private final int FALSE = 0;
	
	public DataBase() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.Driver");
		
		connection = DriverManager.getConnection("jdbc://localhost:3306.clientsdb?useSSL=false"
				+ "&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false"
				+ "&serverTimezone=UTC",
				"root", "123456fifa");
	}
	
	public void addUser(String nickName, String password) throws SQLException {
		preparedStatement = connection.prepareStatement("INSERT INTO users (nickName, password)"
				+ " VALUES (?, ?);");
		preparedStatement.setString(1, nickName);
		preparedStatement.setString(2, password);
		preparedStatement.executeQuery();
	}
	
	public void addServer(String name, int port, String adminIP, int adminPort) throws SQLException {
		preparedStatement = connection.prepareStatement("INSERT INTO severs (name, port, adminIP, adminPort)"
				+ " VALUES (?, ?, ?, ?);");
		preparedStatement.setString(1, name);
		preparedStatement.setInt(2, port);
		preparedStatement.setString(3, adminIP);
		preparedStatement.setInt(4, adminPort);
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
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM users "
					+ "WHERE ninckName = ? AND password = ?;");
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, password);
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) { }
		return (resultSet != null)? true : false;
	}
	
	public int getCountOfServers() {
		int count = 0;
		try {
			resultSet = statement.executeQuery("SELECT COUNT(id) FROM servers;");
			count = resultSet.getInt(1);
		} catch (SQLException e) { }
		return count;
	}
	
	public DefaultTableModel getServers() {
		DefaultTableModel dftm = null;
		try {
			resultSet = statement.executeQuery("SELECT name, ip, port FROM servers");
			dftm = new DefaultTableModel(getData(resultSet, resultSet.getMetaData()),
					getColumnNames(resultSet, resultSet.getMetaData()));
		} catch (SQLException e) {
		}
		return dftm;
	}
	
	private Vector<String> getColumnNames(ResultSet resultSet, 
			ResultSetMetaData resultSetMetaData) {
		Vector<String> columnNames = new Vector<>();
		try {
			int columnCount = resultSetMetaData.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				columnNames.add(resultSetMetaData.getColumnName(i));
			}
		} catch (SQLException e) { }
		return columnNames;
	}
	
	private Vector<Vector<Object>> getData(ResultSet resultSet, 
			ResultSetMetaData resultSetMetaData) {
		Vector<Vector<Object>> datas = new Vector<>();
		try {
			int columnCount = resultSetMetaData.getColumnCount();
			Vector<Object> data = new Vector<>();
			while (resultSet.next()) {
				for (int i = 1; i <= columnCount; i++) {
					data.add(resultSet.getObject(i));
				}
				datas.add(data);
			}
		} catch (SQLException e) {	}
		
		return datas;
	}
	
}
