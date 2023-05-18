package gui.dialogs;

import utils.PropertyLoader;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class EmailConfigDialog extends JDialog {

	private JTextField textFieldUsername;
	private JPasswordField textFieldPassword;
	private JButton btnSave;
	private static final String PROPERTY_FILE_PATH = "resources/email/email.properties";
	private static final String PROPERTY_USERNAME = "mail.username";
	private static final String PROPERTY_PASSWORD = "mail.password";

	public EmailConfigDialog(JFrame parent) {
		super(parent, "Настройки почты отправителя", true);
		initComponents();
		setupListeners();
	}

	private void initComponents() {
		Properties properties = null;
		try {
			properties = PropertyLoader.loadProperty(PROPERTY_FILE_PATH);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Не удалось загрузить файл: " + PROPERTY_FILE_PATH);
		}

		setSize(300, 150);
		setLayout(new GridLayout(4, 2));

		if (properties != null) {
			add(new JLabel("Почта:"));
			textFieldUsername = new JTextField(20);
			textFieldUsername.setText(properties.getProperty(PROPERTY_USERNAME));
			add(textFieldUsername);

			add(new JLabel("Пароль:"));
			textFieldPassword = new JPasswordField(20);
			textFieldPassword.setEchoChar('*');
			textFieldPassword.setText(properties.getProperty(PROPERTY_PASSWORD));
			add(textFieldPassword);
		}

		add(new JPanel());
		JCheckBox showPasswordCheckBox = new JCheckBox("Показать пароль");
		add(showPasswordCheckBox);

		add(new JPanel());
		btnSave = new JButton("Сохранить");
		add(btnSave);

		setLocationRelativeTo(null);
		showPasswordCheckBox.addActionListener(e -> {
			JCheckBox checkBox = (JCheckBox) e.getSource();
			textFieldPassword.setEchoChar(checkBox.isSelected() ? '\0' : '*');
		});

		pack();
	}

	private void setupListeners() {
		btnSave.addActionListener(e -> {
			String newUsername = textFieldUsername.getText();
			char[] newCharPassword = textFieldPassword.getPassword();
			updateProperty(PROPERTY_USERNAME, newUsername);
			updateProperty(PROPERTY_PASSWORD, new String(newCharPassword));
			dispose();
			JOptionPane.showMessageDialog(this, "Данные почты обновлены!");
		});
	}

	private void updateProperty(String key, String newValue) {
		Properties properties = new Properties();
		try (FileInputStream in = new FileInputStream(PROPERTY_FILE_PATH)) {
			properties.load(in);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Не удалось загрузить данные из файла: " + PROPERTY_FILE_PATH);
		}

		properties.setProperty(key, newValue);

		try (FileOutputStream out = new FileOutputStream(PROPERTY_FILE_PATH)) {
			properties.store(out, null);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Не удалось сохранить файл: " + PROPERTY_FILE_PATH);
		}
	}
}
