package dialogs;


import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Properties;

/**
 * Диалоговое окно для настройки почты отправителя.
 */
public class EmailConfigDialog extends JDialog {

	private JTextField jTextFieldUsername;
	private JPasswordField jPasswordFieldPassword;
	private JButton jbtnSave;
	private static final String CONFIG_FILE_NAME = "config.properties";
	private static final String PROPERTY_USERNAME = "mail.username";
	private static final String PROPERTY_PASSWORD = "mail.password";
	private File configFile;

	/**
	 * Создает новый экземпляр диалогового окна для настройки почты отправителя.
	 *
	 * @param parent родительское окно
	 */
	public EmailConfigDialog(JFrame parent) {
		super(parent, "Настройки почты отправителя", true);
		initComponents();
	}

	/**
	 * Инициализирует компоненты диалогового окна.
	 */
	private void initComponents() {
		Properties properties = new Properties();
		try {
			String jarPath = EmailConfigDialog.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			String jarDirectory = new File(jarPath).getParent();
			configFile = new File(jarDirectory, CONFIG_FILE_NAME);
			InputStream inputStream = new FileInputStream(configFile);
			properties.load(inputStream);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Не удалось загрузить файл: " + CONFIG_FILE_NAME);
			return;
		}

		setSize(300, 150);
		setLayout(new GridLayout(4, 2));

		add(new JLabel("Почта:"));
		jTextFieldUsername = new JTextField(20);
		jTextFieldUsername.setText(properties.getProperty(PROPERTY_USERNAME));
		add(jTextFieldUsername);

		add(new JLabel("Пароль:"));
		jPasswordFieldPassword = new JPasswordField(20);
		jPasswordFieldPassword.setEchoChar('*');
		jPasswordFieldPassword.setText(properties.getProperty(PROPERTY_PASSWORD));
		add(jPasswordFieldPassword);

		add(new JPanel());
		JCheckBox showPasswordCheckBox = new JCheckBox("Показать пароль");
		add(showPasswordCheckBox);

		add(new JPanel());
		jbtnSave = new JButton("Сохранить");
		add(jbtnSave);

		setLocationRelativeTo(null);
		showPasswordCheckBox.addActionListener(e -> {
			JCheckBox checkBox = (JCheckBox) e.getSource();
			jPasswordFieldPassword.setEchoChar(checkBox.isSelected() ? '\0' : '*');
		});
		setupListeners();
		pack();
	}

	/**
	 * Настраивает слушатели событий для кнопок.
	 */
	private void setupListeners() {
		jbtnSave.addActionListener(e -> {
			String newUsername = jTextFieldUsername.getText();
			char[] newCharPassword = jPasswordFieldPassword.getPassword();
			updateProperty(PROPERTY_USERNAME, newUsername);
			updateProperty(PROPERTY_PASSWORD, new String(newCharPassword));
			dispose();
			JOptionPane.showMessageDialog(this, "Данные почты обновлены!");
		});
	}

	/**
	 * Обновляет значение свойства в файле настроек.
	 *
	 * @param key      ключ свойства
	 * @param newValue новое значение свойства
	 */
	private void updateProperty(String key, String newValue) {
		Properties properties = new Properties();
		try {
			InputStream inputStream = new FileInputStream(configFile);
			properties.load(inputStream);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Не удалось загрузить данные из файла: " + CONFIG_FILE_NAME);
		}
		properties.setProperty(key, newValue);

		try (FileOutputStream out = new FileOutputStream(configFile)) {
			properties.store(out, null);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Не удалось сохранить файл: " + CONFIG_FILE_NAME);
		}
	}
}
