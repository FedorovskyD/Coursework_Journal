package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionFactory {
	private static final String PROPERTIES_FILE = "db.properties";
	private static final String URL_PROPERTY = "db.url";
	private static final String USERNAME_PROPERTY = "db.username";
	private static final String PASSWORD_PROPERTY = "db.password";
	private static final String url;
	private static final String username;
	private static final String password;

	static {
		Properties props = new Properties();
		try (FileInputStream fis = new FileInputStream(PROPERTIES_FILE)) {
			props.load(fis);
		} catch (IOException e) {
			try {
				throw new SQLException("Failed to read database configuration file", e);
			} catch (SQLException ex) {
				throw new RuntimeException(ex);
			}
		}
		url = props.getProperty(URL_PROPERTY);
		username = props.getProperty(USERNAME_PROPERTY);
		password = props.getProperty(PASSWORD_PROPERTY);
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}
}


