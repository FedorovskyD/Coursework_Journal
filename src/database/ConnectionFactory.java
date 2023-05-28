package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
	private static final String PROPERTIES_FILE = "config.properties";
	private static final String DATABASE_NAME_PROPERTY = "db.name";
	private static final String HOST_PROPERTY = "db.host";
	private static final String PORT_PROPERTY = "db.port";
	private static final String USERNAME_PROPERTY = "db.username";
	private static final String PASSWORD_PROPERTY = "db.password";
	private static Properties props;

	public static Connection getConnection() throws SQLException {
		if (props == null) {
			loadConfigProperties();
		}

		String databaseName = props.getProperty(DATABASE_NAME_PROPERTY);
		String host = props.getProperty(HOST_PROPERTY);
		String port = props.getProperty(PORT_PROPERTY);
		String username = props.getProperty(USERNAME_PROPERTY);
		String password = props.getProperty(PASSWORD_PROPERTY);
		String url = "jdbc:mysql://" + host + ":" + port + "/" + databaseName;

		return DriverManager.getConnection(url, username, password);
	}

	public static boolean checkConnectionData(String url, String username, String password) {
		try {
			DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			return false;
		}

		return true;
	}

	public static void loadConfigProperties() {
		props = new Properties();
		FileInputStream fileInputStream;

		try {
			String jarPath = ConnectionFactory.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			String jarDirectory = new File(jarPath).getParent();
			String filePath = jarDirectory + File.separator + PROPERTIES_FILE;
			fileInputStream = new FileInputStream(filePath);
			props.load(fileInputStream);
		} catch (IOException e) {
			System.out.println("Не удалось загрузить файл со свойствами " + PROPERTIES_FILE);
		}
	}
}
