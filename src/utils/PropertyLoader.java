package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The class PropertyLoader.
 */
public final class PropertyLoader {
	private PropertyLoader() {

	}
	/**
	 * Load properties. Throws {@link IOException} if an error occurred when reading from the input stream.
	 *
	 * @param path the path to properties
	 * @return the properties
	 * @throws IOException if an error occurred when reading from the input stream.
	 */
	public static Properties loadProperty(String path) throws IOException {
		Properties properties = new Properties();
		InputStream inputStream = PropertyLoader.class.getClassLoader().getResourceAsStream(path);
		properties.load(inputStream);
		return properties;
	}
}
