package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import javax.swing.*;

public class EmailSender {
	private static final String CONFIG_FILE_NAME = "config.properties";
	private static final String MAIL_USERNAME = "mail.username";
	private static final String MAIL_PASSWORD = "mail.password";

	public static void sendEmail(String recipients, String subject, String body, byte[] fileData, String fileName) {

		// Загрузка свойств email
		Properties configProps = new Properties();
		try {
			String jarPath = EmailSender.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			String jarDirectory = new File(jarPath).getParent();
			File configFile = new File(jarDirectory, CONFIG_FILE_NAME);
			InputStream inputStream = new FileInputStream(configFile);
			configProps.load(inputStream);
		} catch (IOException e) {
			System.out.println("Ошибка при загрузке свойств email");
			JOptionPane.showMessageDialog(null,"Не удалось загрузить файл: " + CONFIG_FILE_NAME);
			return;
		}

		// Получение параметров подключения к почтовому серверу
		String host = configProps.getProperty("mail.smtp.host");
		int port = Integer.parseInt(configProps.getProperty("mail.smtp.port"));
		String username = configProps.getProperty(MAIL_USERNAME);
		String password = configProps.getProperty(MAIL_PASSWORD);

		// Создание свойств для настройки соединения
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);


		try {
			// Создание объекта Session для аутентификации
			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});
			Transport transport = session.getTransport("smtp");
			transport.connect(host, port, username, password);
			if (transport.isConnected()) {
				System.out.println("Авторизация прошла успешно!");
			}
			// Создание объекта MimeMessage
			Message message = new MimeMessage(session);
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));

			// Установка темы письма
			message.setSubject(subject);

			// Создание объекта MimeBodyPart для текстового содержимого
			BodyPart textPart = new MimeBodyPart();
			textPart.setText(body);

			// Создание объекта MimeBodyPart для вложения файла
			BodyPart filePart = new MimeBodyPart();
			DataSource source = new ByteArrayDataSource(fileData, "application/octet-stream");
			filePart.setDataHandler(new DataHandler(source));
			filePart.setFileName(fileName);

			// Создание объекта Multipart и добавление в него текстового содержимого и вложения
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(textPart);
			multipart.addBodyPart(filePart);

			// Установка Multipart в качестве содержимого письма
			message.setContent(multipart);

			// Отправка письма
			Transport.send(message);

			JOptionPane.showMessageDialog(null, "Письмо успешно отправлено!");
		} catch (AuthenticationFailedException e) {
			JOptionPane.showMessageDialog(null, "Ошибка аутентификации! Проверьте учетные данные.");
		} catch (MessagingException e) {
			JOptionPane.showMessageDialog(null, "Ошибка отправки письма!");
			e.printStackTrace();
		}
	}
}
