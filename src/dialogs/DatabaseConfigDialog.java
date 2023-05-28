package dialogs;

import database.ConnectionFactory;
import gui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfigDialog extends JDialog {
	private final JTextField jTextFieldDatabase;
	private final JTextField jTextFieldHost;
	private final JTextField jTextFieldPort;
	private final JTextField usernameField;
	private final JPasswordField passwordField;
	private final JCheckBox showPasswordCheckbox;

	public DatabaseConfigDialog(File configFile) {
		setTitle("Конфигурация подключения к базе данных");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setSize(300, 250);
		setLayout(new GridLayout(7, 2));
		JLabel jlblDatabase = new JLabel("Название базы данных:");
		jTextFieldDatabase = new JTextField(20);
		JLabel jlblHost = new JLabel("Хост:");
		jTextFieldHost = new JTextField(20);
		JLabel jlblPort = new JLabel("Порт: ");
		jTextFieldPort = new JTextField(20);
		JLabel usernameLabel = new JLabel("Имя пользователя:");
		usernameField = new JTextField();
		JLabel passwordLabel = new JLabel("Пароль:");
		passwordField = new JPasswordField();
		showPasswordCheckbox = new JCheckBox("Показать пароль");
		if (configFile.exists()) {
			Properties properties = new Properties();
			try (InputStream input = new FileInputStream(configFile)) {
				properties.load(input);
				jTextFieldDatabase.setText(properties.getProperty("db.name"));
				jTextFieldHost.setText(properties.getProperty("db.host"));
				jTextFieldPort.setText(properties.getProperty("db.port"));
				usernameField.setText(properties.getProperty("db.username"));
				passwordField.setText(properties.getProperty("db.password"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		JButton saveButton = new JButton("Сохранить");
		saveButton.addActionListener(e -> {
			String databaseName = jTextFieldDatabase.getText().trim();
			String host = jTextFieldHost.getText().trim();
			String port = jTextFieldPort.getText().trim();
			String username = usernameField.getText();
			char[] passwordChars = passwordField.getPassword();
			String password = new String(passwordChars);
			// Создать новый файл конфигурации
			Properties properties = new Properties();
			properties.setProperty("db.name", databaseName);
			properties.setProperty("db.host", host);
			properties.setProperty("db.port", port);
			properties.setProperty("db.username", username);
			properties.setProperty("db.password", password);
			properties.setProperty("mail.password", "etpxgbfuhffspbwm");
			properties.setProperty("mail.smtp.auth", "true");
			properties.setProperty("mail.smtp.host", "smtp.gmail.com");
			properties.setProperty("mail.smtp.port", "587");
			properties.setProperty("mail.smtp.starttls.enable", "true");
			properties.setProperty("mail.username", "sinavskaau03@gmail.com");
			String url = "jdbc:mysql://" + host + ":" + port + "/" + databaseName;
			if (ConnectionFactory.checkConnectionData(url, username, password)) {
				try (OutputStream output = new FileOutputStream(configFile)) {
					properties.store(output, "Настройки подключения к БД");
					System.out.println("Файл конфигурации создан.");
					JOptionPane.showMessageDialog(null,
							"Подключение успешно установленно!",
							"Подключение установлено", JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException ex) {
					System.err.println("Ошибка при создании файла конфигурации: " + ex.getMessage());
				}
			} else {
				JOptionPane.showMessageDialog(null, "Не удалось установить подключение. Проверьте данные. ");
			}
		});
		showPasswordCheckbox.addActionListener(e -> {
			if (showPasswordCheckbox.isSelected()) {
				passwordField.setEchoChar((char) 0);
			} else {
				passwordField.setEchoChar('*');
			}
		});
		add(jlblDatabase);
		add(jTextFieldDatabase);
		add(jlblHost);
		add(jTextFieldHost);
		add(jlblPort);
		add(jTextFieldPort);
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
				}
			}
		});
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
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
