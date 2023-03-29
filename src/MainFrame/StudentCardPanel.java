package MainFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class StudentCardPanel extends JPanel {

	private JLabel photoLabel;
	private JLabel fullNameLabel;
	private JLabel phoneLabel;
	private JLabel emailLabel;

	private JCheckBox lectureCheckBox;
	private JCheckBox labCheckBox;
	private JPanel calendarPanel;

	public JLabel getPhotoLabel() {
		return photoLabel;
	}

	public JLabel getFullNameLabel() {
		return fullNameLabel;
	}

	public JLabel getPhoneLabel() {
		return phoneLabel;
	}

	public JLabel getEmailLabel() {
		return emailLabel;
	}

	public void setPhotoLabel(JLabel photoLabel) {
		this.photoLabel = photoLabel;
	}

	public void setFullNameLabel(JLabel fullNameLabel) {
		this.fullNameLabel = fullNameLabel;
	}

	public void setPhoneLabel(JLabel phoneLabel) {
		this.phoneLabel = phoneLabel;
	}

	public void setEmailLabel(JLabel emailLabel) {
		this.emailLabel = emailLabel;
	}

	public StudentCardPanel() {
		setLayout(new BorderLayout());

		// Создание панели информации о студенте
		JPanel infoPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.anchor = GridBagConstraints.WEST;

		// Добавление фотографии
		photoLabel = new JLabel();
		gbc.gridheight = 4;
		infoPanel.add(photoLabel, gbc);

		// Добавление ФИО
		fullNameLabel = new JLabel();
		fullNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
		gbc.gridx = 1;
		gbc.gridheight = 1;
		infoPanel.add(fullNameLabel, gbc);

		// Добавление телефона
		gbc.gridy++;
		phoneLabel = new JLabel();
		phoneLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		infoPanel.add(phoneLabel, gbc);

		// Добавление почты
		gbc.gridy++;
		emailLabel = new JLabel();
		emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		infoPanel.add(emailLabel, gbc);

		// Добавление пустой строки
		gbc.gridy++;
		gbc.weighty = 1;
		infoPanel.add(Box.createVerticalGlue(), gbc);

		// Добавление панели выбора посещений
		JPanel attendancePanel = new JPanel();
		attendancePanel.setLayout(new BoxLayout(attendancePanel, BoxLayout.Y_AXIS));

		lectureCheckBox = new JCheckBox("Посещение лекций");
		attendancePanel.add(lectureCheckBox);

		labCheckBox = new JCheckBox("Посещение лабораторных занятий");
		attendancePanel.add(labCheckBox);

		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridheight = 4;
		gbc.weightx = 1;
		gbc.anchor = GridBagConstraints.EAST;
		infoPanel.add(attendancePanel, gbc);
		// Добавление панели календаря
		calendarPanel = new JPanel(new BorderLayout());
		calendarPanel.setBorder(BorderFactory.createTitledBorder("Посещаемость"));

		add(infoPanel, BorderLayout.NORTH);
		add(calendarPanel, BorderLayout.CENTER);
	}


	public JPanel getCalendarPanel() {
		return calendarPanel;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 400);
		frame.setLocationRelativeTo(null);
		frame.setContentPane(new StudentCardPanel());
		frame.setVisible(true);
	}
}

