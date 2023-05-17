package gui.dialogs;

import utils.PropertyLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class EmailConfigDialog extends JDialog {

	private JTextField textFieldUsername;
	private JPasswordField textFieldPassword;
	private JButton btnSave;
	private JCheckBox showPasswordCheckBox;

	private static final String PROPERTY_FILE_PATH = "resources/email/email.properties";
	private static final String PROPERTY_USERNAME = "mail.username";
	private static final String PROPERTY_PASSWORD = "mail.password";


	public EmailConfigDialog() {
		initComponents();
		setupListeners();
	}

	private void initComponents() {
		Properties properties = null;
		try {
			properties = PropertyLoader.loadProperty(PROPERTY_FILE_PATH);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		showPasswordCheckBox = new JCheckBox("Показать пароль");
		setTitle("Настройка почты");
		setSize(300, 150);
		setLayout(new GridLayout(4, 2));
		add(new JLabel("Почта:"));
		textFieldUsername = new JTextField(20);
		textFieldUsername.setText(properties.getProperty(PROPERTY_USERNAME));
		add(textFieldUsername);
		add(new JLabel("Пароль:"));
		textFieldPassword = new JPasswordField(20);
		textFieldPassword.setEchoChar('*');
		textFieldPassword.setText(properties.getProperty(PROPERTY_PASSWORD));
		add(textFieldPassword);
		add(new JPanel());
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
			JOptionPane.showMessageDialog(this, "Данные почты обновлены!");
		});
	}

	private void updateProperty(String key, String newValue) {
		Properties properties = new Properties();
		try {
			FileInputStream in = new FileInputStream(EmailConfigDialog.PROPERTY_FILE_PATH);
			properties.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		properties.setProperty(key, newValue);

		try {
			FileOutputStream out = new FileOutputStream(EmailConfigDialog.PROPERTY_FILE_PATH);
			properties.store(out, null);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

