package DataBase;

import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

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
	
	public void addUser(String nickName, String IP, int port) throws SQLException {
		preparedStatement = connection.prepareStatement("INSERT INTO users (nickName, IP, port, "
				+ " banned)" + " VALUES (?, ?, ?, ?, ?);");
		preparedStatement.setString(1, nickName);
		preparedStatement.setString(2, IP);
		preparedStatement.setInt(3, port);
		preparedStatement.setInt(4, FALSE);
		preparedStatement.executeUpdate();
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
	
}
