package utils;

import java.io.IOException;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import javax.swing.*;

public class EmailSender {
	private static final String EMAIL_PROPERTY_PATH = "resources/email/email.properties";
	private static final String MAIL_USERNAME = "mail.username";
	private static final String MAIL_PASSWORD = "mail.password";

	public static void sendEmail(String recipients, String subject, String body, byte[] fileData, String fileName) {

		// Загрузка свойств email
		Properties emailProps;
		try {
			emailProps = PropertyLoader.loadProperty(EMAIL_PROPERTY_PATH);
		} catch (IOException e) {
			System.out.println("Ошибка при загрузке свойств email");
			throw new RuntimeException(e);
		}

		// Получение параметров подключения к почтовому серверу
		String host = emailProps.getProperty("mail.smtp.host");
		int port = Integer.parseInt(emailProps.getProperty("mail.smtp.port"));
		String username = emailProps.getProperty(MAIL_USERNAME);
		String password = emailProps.getProperty(MAIL_PASSWORD);

		// Создание свойств для настройки соединения
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);

		// Создание объекта Session для аутентификации
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
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
		} catch (MessagingException e) {
			JOptionPane.showMessageDialog(null, "Ошибка отправки письма!");
			e.printStackTrace();
		}
	}
}
