import database.ConnectionFactory;
import dialogs.DatabaseConfigDialog;
import gui.MainFrame;

import javax.swing.*;
import java.io.File;
import java.sql.SQLException;

public class Main {
	private static final String CONFIG_FILE_NAME = "config.properties";
	public static void main(String[] args) {
		// Получить путь к директории, где находится JAR-файл
		String jarPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String jarDirectory = new File(jarPath).getParent();

		boolean validCredentials;
			// Проверить существование файла config.properties в директории
			File configFile = new File(jarDirectory, CONFIG_FILE_NAME);
			if (configFile.exists()) {
				System.out.println("Файл конфигурации существует.");
				// Проверить данные для подключения к базе данных
				validCredentials = checkDatabaseCredentials();
				if (!validCredentials) {
					System.out.println("Неверные данные для подключения к базе данных.");
					// Отобразить окно ввода данных
					new DatabaseConfigDialog(configFile);
				}else {
					new MainFrame();
				}
			} else {
				System.out.println("Файл конфигурации не найден. Создание файла...");
				// Отобразить окно ввода данных
				new DatabaseConfigDialog(configFile);
			}
	}

	private static boolean checkDatabaseCredentials() {
		try {
			ConnectionFactory.getConnection();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
}
