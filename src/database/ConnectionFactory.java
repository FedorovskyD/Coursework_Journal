package database;

import utils.PropertyLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionFactory {
	private static final String PROPERTIES_FILE = "resources/database/db.properties";
	private static final String URL_PROPERTY = "db.url";
	private static final String USERNAME_PROPERTY = "db.username";
	private static final String PASSWORD_PROPERTY = "db.password";
	private static final String url;
	private static final String username;
	private static final String password;

	static {
		Properties props = null;
		try {
			props = PropertyLoader.loadProperty(PROPERTIES_FILE);
		} catch (IOException e) {
			System.out.println("Не удалось загрузить файл бд");
			throw new RuntimeException(e);
		}
		url = props.getProperty(URL_PROPERTY);
		username = props.getProperty(USERNAME_PROPERTY);
		password = props.getProperty(PASSWORD_PROPERTY);
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}
}


