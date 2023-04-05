package MainFrame;

import connection.MySQLConnector;
import entity.Lab;
import entity.Student;
import utils.PhotoUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class StudentCardPanel extends JPanel {
	public Student getStudent() {
		return student;
	}

	private Student student;
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

	public StudentCardPanel(Student student) {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(800, 1000));
		setMaximumSize(getPreferredSize());
		setMinimumSize(getPreferredSize());
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
		infoPanel.setPreferredSize(new Dimension(800, 300));
		infoPanel.setMinimumSize(infoPanel.getPreferredSize());
		infoPanel.setMaximumSize(infoPanel.getPreferredSize());
		// Добавление панели календаря
		calendarPanel = new JPanel(new BorderLayout());
		calendarPanel.setBorder(BorderFactory.createTitledBorder("Посещаемость"));
		calendarPanel.setPreferredSize(new Dimension(800, 500));
		calendarPanel.setMinimumSize(calendarPanel.getPreferredSize());
		calendarPanel.setMaximumSize(calendarPanel.getPreferredSize());
		add(infoPanel, BorderLayout.NORTH);
		add(calendarPanel, BorderLayout.CENTER);
		fullNameLabel.setText(student.getSurname() + " " + student.getName() +
				" " + student.getMiddleName());
		emailLabel.setText(student.getEmail());
		photoLabel.setText(student.getTelephone());
		Image image = PhotoUtils.getInstance().loadPhoto(student).getImage();
		if (image != null) {
			photoLabel.setSize(new Dimension(200, 220));
			ImageIcon icon = new ImageIcon(image.getScaledInstance(photoLabel.getWidth(), photoLabel.getHeight(), Image.SCALE_SMOOTH));
			photoLabel.setIcon(icon);
		} else {
			photoLabel.setSize(new Dimension(0, 0));
		}
		List<Lab> labs = MySQLConnector.getAllLabByGroup("10702420");
		getCalendarPanel().setLayout(new GridLayout(5, 5, 5, 5)); // задаем сетку для кнопок
		// Создаем кнопки для каждой лабораторной работы и добавляем их на панель
		for (Lab lab : labs) {
			String labDate = lab.getDate().toString(); // получаем дату лабораторной работы
			String labGrade = MySQLConnector.getGradeByLessonIDAndStudentID(lab.getId(), student.getId()); // получаем оценку студента за лабораторную работу

			// Создаем новую кнопку с датой и оценкой студента
			JButton labButton = new JButton("<html>" + labDate + "<br> Оценка: " + labGrade + "</html>");
			if (MySQLConnector.isAttendance(student.getId(), lab.getId())) {
				labButton.setBackground(Color.GREEN);
			} else {
				labButton.setBackground(Color.GRAY);
			}
			// Добавляем слушателя событий, который будет обрабатывать нажатие на кнопку и нажатие правой кнопки мыши
			labButton.addMouseListener(new MouseAdapter() {
				                           @Override
				                           public void mousePressed(MouseEvent e) {
					                           Object o = e.getSource();
					                           if (o instanceof JButton) {

						                           JButton jButton = (JButton) o;
						                           Color color = jButton.getBackground();
						                           if (e.getButton() == MouseEvent.BUTTON1 && color.equals(Color.GRAY)) { // если была нажата левая кнопка мыши
							                           labButton.setBackground(Color.GREEN); // меняем цвет кнопки на зеленый
							                           MySQLConnector.addAttendance(student.getId(), lab.getId());
						                           } else if (e.getButton() == MouseEvent.BUTTON1 && color.equals(Color.GREEN)) {
							                           labButton.setBackground(Color.GRAY);

						                           } else if (e.getButton() == MouseEvent.BUTTON3) { // если была нажата правая кнопка мыши
							                           JPopupMenu popupMenu = new JPopupMenu(); // создаем контекстное меню
							                           JMenuItem setGradeMenuItem = new JMenuItem("Поставить оценку"); // создаем пункт меню "Поставить оценку"

							                           // Добавляем слушателя событий, который будет обрабатывать нажатие на пункт меню
							                           setGradeMenuItem.addActionListener(e1 -> {
								                           String grade = JOptionPane.showInputDialog("Введите оценку:"); // выводим диалоговое окно для ввода оценки
								                           labButton.setText("<html>" + labDate + "<br> Оценка: " + grade + "</html>"); // изменяем текст кнопки, добавляя в него новую оценку
							                           });

							                           popupMenu.add(setGradeMenuItem); // добавляем пункт меню в контекстное меню
							                           popupMenu.show(labButton, e.getX(), e.getY());
						                           }
					                           }
				                           }
			                           });
			labButton.setPreferredSize(new Dimension(100, 30));
			getCalendarPanel().add(labButton); // добавляем кнопку на панель
		}
		int remaining = 25 - labs.size();
		for (int i = 1; i <= remaining; i++) {
			JPanel filler = new JPanel();
			getCalendarPanel().add(filler);
		}
		setVisible(true);
	}


	public JPanel getCalendarPanel() {
		return calendarPanel;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 400);
		frame.setLocationRelativeTo(null);
		frame.setContentPane(new StudentCardPanel(new Student()));
		frame.setVisible(true);
	}
}

