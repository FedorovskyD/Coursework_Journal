package gui;

import database.ConnectionFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.sql.SQLException;
import java.util.Properties;

public class ConfigFrame extends JFrame {
	private final JTextField databaseField;
	private final JTextField hostField;
	private final JTextField portField;
	private final JTextField usernameField;
	private final JPasswordField passwordField;
	private final JCheckBox showPasswordCheckbox;

	public ConfigFrame(File configFile) {
		setTitle("Конфигурация подключения к базе данных");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(300, 250);
		setLayout(new GridLayout(7, 2));

		JLabel databaseLabel = new JLabel("Название базы данных:");
		databaseField = new JTextField(20);
		JLabel hostLabel = new JLabel("Хост:");
		hostField = new JTextField(20);
		JLabel portLabel = new JLabel("Порт:");
		portField = new JTextField(20);
		JLabel usernameLabel = new JLabel("Имя пользователя:");
		usernameField = new JTextField();
		JLabel passwordLabel = new JLabel("Пароль:");
		passwordField = new JPasswordField();
		showPasswordCheckbox = new JCheckBox("Показать пароль");

		loadConfigProperties(configFile);

		JButton saveButton = new JButton("Сохранить");
		saveButton.addActionListener(e -> saveConfigProperties(configFile));

		showPasswordCheckbox.addActionListener(e -> {
			if (showPasswordCheckbox.isSelected()) {
				passwordField.setEchoChar((char) 0);
			} else {
				passwordField.setEchoChar('*');
			}
		});

		add(databaseLabel);
		add(databaseField);
		add(hostLabel);
		add(hostField);
		add(portLabel);
		add(portField);
		add(usernameLabel);
		add(usernameField);
		add(passwordLabel);
		add(passwordField);
		add(new JLabel());
		add(showPasswordCheckbox);
		add(new JLabel());
		add(saveButton);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (checkDatabaseCredentials()) {
					new MainFrame();
				} else {
					JOptionPane.showMessageDialog(ConfigFrame.this,
							"Не удалось прочитать файл config.properties",
							"Ошибка", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		pack();
		setLocationRelativeTo(null);
	}

	private void loadConfigProperties(File configFile) {
		if (configFile.exists()) {
			Properties properties = new Properties();
			try (InputStream input = new FileInputStream(configFile)) {
				properties.load(input);
				databaseField.setText(properties.getProperty("db.name"));
				hostField.setText(properties.getProperty("db.host"));
				portField.setText(properties.getProperty("db.port"));
				usernameField.setText(properties.getProperty("db.username"));
				passwordField.setText(properties.getProperty("db.password"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void saveConfigProperties(File configFile) {
		String databaseName = databaseField.getText().trim();
		String host = hostField.getText().trim();
		String port = portField.getText().trim();
		String username = usernameField.getText();
		char[] passwordChars = passwordField.getPassword();
		String password = new String(passwordChars);
		Properties properties = new Properties();

		// Загрузка существующих свойств из файла
		if (configFile.exists()) {
			try (InputStream input = new FileInputStream(configFile)) {
				properties.load(input);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!properties.containsKey("mail.smtp.auth")) {
			properties.setProperty("mail.smtp.auth", "true");
		}
		if(!properties.containsKey("mail.smtp.starttls.enable")){
			properties.setProperty("mail.smtp.starttls.enable", "true");
		}
		if(!properties.containsKey("mail.smtp.host")){
			properties.setProperty("mail.smtp.host", "smtp.gmail.com");
		}
		if(!properties.containsKey("mail.smtp.port")){
			properties.setProperty("mail.smtp.port", "587");
		}
		// Установка свойств для базы данных
		properties.setProperty("db.name", databaseName);
		properties.setProperty("db.host", host);
		properties.setProperty("db.port", port);
		properties.setProperty("db.username", username);
		properties.setProperty("db.password", password);

		// Сохранение свойств в файл
		try (OutputStream output = new FileOutputStream(configFile)) {
			properties.store(output, "Настройки подключения к БД");
			if(checkDatabaseCredentials()) {
				System.out.println("Файл конфигурации создан.");
				JOptionPane.showMessageDialog(null,
						"Подключение успешно установлено! Для запуска приложения закройте текущее окно.",
						"Подключение установлено", JOptionPane.INFORMATION_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(null,
						"Ошибка подключения! Проверьте введенные данные!",
						"Ошибка подключения", JOptionPane.ERROR_MESSAGE);
			}
		} catch (IOException ex) {
			System.err.println("Ошибка при создании файла конфигурации: " + ex.getMessage());
		}
	}


	private boolean checkDatabaseCredentials() {
		try {
			ConnectionFactory.loadConfigProperties();
			ConnectionFactory.getConnection();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
}
