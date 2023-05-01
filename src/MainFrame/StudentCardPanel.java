package MainFrame;

import database.dao.impl.GradeDaoImpl;
import database.dao.impl.LabDaoImpl;
import database.dao.impl.StudentDaoImpl;
import entity.Grade;
import entity.Lab;
import entity.Student;
import utils.PhotoUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

public class StudentCardPanel extends JPanel {
	public Student getStudent() {
		return student;
	}

	private final Student student;
	private JLabel photoLabel;
	private final JTextField fullNameLabel,emailField,phoneField,gpaField;
	private JLabel phoneLabel;
	private JLabel emailLabel;
	private final JPanel calendarPanel;
	private final MainWindow mainWindow;

	public JLabel getPhotoLabel() {
		return photoLabel;
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



	public void setPhoneLabel(JLabel phoneLabel) {
		this.phoneLabel = phoneLabel;
	}

	public void setEmailLabel(JLabel emailLabel) {
		this.emailLabel = emailLabel;
	}

	public StudentCardPanel(JFrame parent) {
		mainWindow = (MainWindow) parent;
		BoxLayout layout1 = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(layout1);



		setPreferredSize(new Dimension(1000, 800));
		setMaximumSize(getPreferredSize());
		setMinimumSize(getPreferredSize());
		// Создание панели информации о студенте
		JPanel infoPanel = new JPanel();
		// Создаем компоненты
		photoLabel = new JLabel(new ImageIcon("photos/default.jpg"));
		JLabel nameLabel = new JLabel("ФИО:");
		Dimension txtFieldDimension = new Dimension(300,20);
		fullNameLabel = new JTextField(20);
		fullNameLabel.setMaximumSize(txtFieldDimension);
		fullNameLabel.setEditable(false);
		fullNameLabel.setBorder(null);
		emailLabel = new JLabel("Почта:");
		emailField = new JTextField(20);
		emailField.setMaximumSize(txtFieldDimension);
		emailField.setEditable(false);
		emailField.setBorder(null);
		phoneLabel = new JLabel("Телефон:");
		phoneField = new JTextField(20);
		phoneField.setMaximumSize(txtFieldDimension);
		phoneField.setEditable(false);
		phoneField.setBorder(null);
		JLabel gpaLabel = new JLabel("Средний балл:");
		gpaField = new JTextField(20);
		gpaField.setMaximumSize(txtFieldDimension);
		gpaField.setEditable(false);
		gpaField.setBorder(null);
		JButton deleteButton = new JButton("Удалить студента");
		JButton editButton = new JButton("Редактировать");

		MainWindow mainWindow = (MainWindow) parent;
		deleteButton.addActionListener(e->{
			int result = JOptionPane.showConfirmDialog(mainWindow, "Вы действительно хотите сохранить изменения?", "Подтверждение", JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {

					String photoPath = mainWindow.getCurrentStudent().getPhotoPath();
					if (photoPath != null) {
						File fileToDelete = new File(photoPath);
						if (fileToDelete.delete()) {
							System.out.println("File deleted successfully.");
						} else {
							System.out.println("Failed to delete the file.");
						}
					}
					if (StudentDaoImpl.getInstance().delete(mainWindow.getCurrentStudent())){
						mainWindow.getStudentCardDialog().setVisible(false);
						mainWindow.getCurrentGroup().getStudents().remove(mainWindow.getCurrentStudent());
						mainWindow.updateStudentTable();
						System.out.println("Student was deleted");
					}
			} else {
				// отменяем изменения
			}
		});
		editButton.addActionListener(e -> {
			if(editButton.getText().equalsIgnoreCase("Редактировать")){
				fullNameLabel.setEditable(true);
				emailField.setEditable(true);
				phoneField.setEditable(true);
				deleteButton.setVisible(true);
				editButton.setText("Сохранить");

			}else {
				Student student1 = ((MainWindow) parent).getCurrentStudent();
				String fullName = fullNameLabel.getText();
				String[] fio = fullName.split(" ");
				student1.setName(fio[1]);
				student1.setSurname(fio[0]);
				student1.setMiddleName(fio[2]);
				student1.setTelephone(phoneField.getText());
				student1.setEmail(emailField.getText());
				StudentDaoImpl.getInstance().update(student1);
				SwingUtilities.invokeLater(mainWindow.getStudentTable()::repaint);
				fullNameLabel.setEditable(false);
				emailField.setEditable(false);
				phoneField.setEditable(false);
				deleteButton.setVisible(false);
				editButton.setText("Редактировать");
			}
		});

		deleteButton.setVisible(false);

		// Создаем менеджер компоновки
		GroupLayout layout = new GroupLayout(infoPanel);
		infoPanel.setLayout(layout);

		// Определяем горизонтальную группу
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addComponent(photoLabel)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(nameLabel)
						.addComponent(emailLabel)
						.addComponent(phoneLabel)
						.addComponent(gpaLabel)
						.addComponent(editButton))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(fullNameLabel)
						.addComponent(emailField)
						.addComponent(phoneField)
						.addComponent(gpaField)
						.addComponent(deleteButton))
		);


// Задаем расстояние между строками
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(photoLabel)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(nameLabel)
								.addComponent(fullNameLabel))
						.addGap(10) // добавляем 10 пикселей расстояния между строками
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(emailLabel)
								.addComponent(emailField))
						.addGap(10) // добавляем 10 пикселей расстояния между строками
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(phoneLabel)
								.addComponent(phoneField))
						.addGap(10) // добавляем 10 пикселей расстояния между строками
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(gpaLabel)
								.addComponent(gpaField))
						.addGap(60) // добавляем 10 пикселей расстояния между строками
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(editButton)
								.addComponent(deleteButton)))
		);


		infoPanel.setPreferredSize(new Dimension(800, 200));
		infoPanel.setMinimumSize(infoPanel.getPreferredSize());
		infoPanel.setMaximumSize(infoPanel.getPreferredSize());
		JPanel markPanel = new JPanel(new FlowLayout());
		for (int i = 0; i < 11; i++) {
			JButton jButton = new JButton(String.valueOf(i));
			jButton.setPreferredSize(new Dimension(80,30));
			markPanel.add(jButton);
		}
		// Добавление панели календаря
		calendarPanel = new JPanel(new BorderLayout());
		calendarPanel.setBorder(BorderFactory.createTitledBorder("Посещаемость"));
		calendarPanel.setPreferredSize(new Dimension(800, 500));
		calendarPanel.setMinimumSize(calendarPanel.getPreferredSize());
		calendarPanel.setMaximumSize(calendarPanel.getPreferredSize());
		photoLabel.setPreferredSize(new Dimension(200,220));
		photoLabel.setMaximumSize(photoLabel.getPreferredSize());
		photoLabel.setMaximumSize(photoLabel.getPreferredSize());

		infoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
		add(infoPanel);

		markPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
		add(markPanel);

		calendarPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 450));
		add(calendarPanel);

		student = new Student();
		student.setName("Дмитрий");
		student.setSurname("Федоровский");
		student.setMiddleName("Александрович");
		student.setTelephone("+375292488182");
		student.setEmail("dimafedorovskij@gamil.com");
		student.setPhotoPath("photos/default.jpg");
		student.setAge(9);
		update(student);
		setVisible(true);
	}

	public void update(Student student) {
		calendarPanel.removeAll();
		fullNameLabel.setText(student.getSurname() + " " + student.getName() +
				" " + student.getMiddleName());
		emailField.setText(student.getEmail());
		phoneField.setText(student.getTelephone());
		gpaField.setText(String.valueOf(student.getMark()));
		Image image = PhotoUtils.getInstance().loadPhoto(student).getImage();
		if (image != null) {
			photoLabel.setSize(new Dimension(200, 220));
			ImageIcon icon = new ImageIcon(image.getScaledInstance(photoLabel.getWidth(), photoLabel.getHeight(), Image.SCALE_SMOOTH));
			photoLabel.setIcon(icon);
		} else {
			photoLabel.setSize(new Dimension(0, 0));
		}
		List<Lab> labs = mainWindow.getCurrentGroup().getLabs();
		getCalendarPanel().setLayout(new GridLayout(5, 5, 5, 5)); // задаем сетку для кнопок
		// Создаем кнопки для каждой лабораторной работы и добавляем их на панель
		for (Lab lab : labs) {

			String labDate = new SimpleDateFormat("dd.MM.yyyy").format(lab.getDate()); // получаем дату лабораторной работы
			int labGrade = 0;
			if(student.getGradeList().stream().anyMatch(grade -> grade.getLab() == lab.getId())){
				 labGrade = student.getGradeList().stream().filter(grade -> grade.getLab() == lab.getId()).findFirst().get().getGrade();
			}
			// Создаем новую кнопку с датой и оценкой студента
			JButton labButton = new JButton("<html>" + labDate + "<br> Оценка: " + labGrade + "</html>");

				if (student.getAttendanceList().stream()
						.filter(attendance -> attendance.getLab() == lab.getId()).toList().size()>0) {
					labButton.setBackground(Color.GREEN);
				} else {
				labButton.setBackground(Color.GRAY);
			}
			// Добавляем слушателя событий, который будет обрабатывать нажатие на кнопку и нажатие правой кнопки мыши
			labButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					Object o = e.getSource();
					if (o instanceof JButton jButton) {

						Color color = jButton.getBackground();
						if (e.getButton() == MouseEvent.BUTTON1 && color.equals(Color.GRAY)) { // если была нажата левая кнопка мыши
							labButton.setBackground(Color.GREEN); // меняем цвет кнопки на зеленый
						} else if (e.getButton() == MouseEvent.BUTTON1 && color.equals(Color.GREEN)) {
							labButton.setBackground(Color.GRAY);

						} else if (e.getButton() == MouseEvent.BUTTON3) { // если была нажата правая кнопка мыши
							JPopupMenu popupMenu = new JPopupMenu(); // создаем контекстное меню
							JMenuItem setGradeMenuItem = new JMenuItem("Поставить оценку"); // создаем пункт меню "Поставить оценку"

							// Добавляем слушателя событий, который будет обрабатывать нажатие на пункт меню
							setGradeMenuItem.addActionListener(e1 -> {
								String grade = JOptionPane.showInputDialog("Введите оценку:"); // выводим диалоговое окно для ввода оценки
								labButton.setText("<html>" + labDate + "<br> Оценка: " + grade + "</html>"); // изменяем текст кнопки, добавляя в него новую оценку
								Grade grade1 = new Grade(lab.getId(),student.getId(),Integer.parseInt(grade));
								student.getGradeList().add(grade1);
								gpaField.setText(String.valueOf(student.getMark()));
								if(GradeDaoImpl.getInstance().save(grade1)!=-1) System.out.println("Оценка не добавлена");
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
	}

	public JPanel getCalendarPanel() {
		return calendarPanel;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 400);
		frame.setLocationRelativeTo(null);
		frame.setContentPane(new StudentCardPanel(frame));
		frame.setVisible(true);
	}
}

