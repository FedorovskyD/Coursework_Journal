
import database.ConnectionFactory;
import gui.ConfigFrame;
import gui.MainFrame;
import gui.SplashScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;

public class Main {
	private static final String CONFIG_FILE_NAME = "config.properties";

	public static void main(String[] args) {
		SplashScreen splashScreen = new SplashScreen();
		splashScreen.getJbtnNext().addActionListener(e -> {
			splashScreen.dispose();
			String jarPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			String jarDirectory = new File(jarPath).getParent();
			File configFile = new File(jarDirectory, CONFIG_FILE_NAME);
			if (configFile.exists()) {
				System.out.println("Файл конфигурации существует.");
				if (!checkDatabaseCredentials()) {
					System.out.println("Неверные данные для подключения к базе данных.");
					ConfigFrame configFrame = new ConfigFrame(configFile);
					JOptionPane.showMessageDialog(configFrame,"Данные подключение к базе данных неверные");
					configFrame.setVisible(true);
				} else {
					new MainFrame();
				}
			} else {
				System.out.println("Файл конфигурации не найден. Создание файла...");
				ConfigFrame configFrame = new ConfigFrame(configFile);
				JOptionPane.showMessageDialog(configFrame,"Файл конфигурации не найден.\n Введите данные для подключения к базе данных!");
				configFrame.setVisible(true);
			}
		});
	}

	private static boolean checkDatabaseCredentials() {
		try {
			ConnectionFactory.loadConfigProperties();
			ConnectionFactory.getConnection();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
}