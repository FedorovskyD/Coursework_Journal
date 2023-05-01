package dialogs;

import MainFrame.MainWindow;
import database.dao.impl.GradeDaoImpl;
import database.dao.impl.StudentDaoImpl;
import entity.Grade;
import entity.Lab;
import entity.Student;
import utils.PhotoUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class StudentCardDialog extends JDialog {
	private final Student student;
	protected JLabel photoLabel;
	protected final JTextField txtFullName, txtEmail, txtPhone, txtGpa;
	protected JLabel phoneLabel;
	protected JLabel emailLabel;
	protected final JButton deleteButton,editButton;
	private final JPanel calendarPanel;
	protected final MainWindow mainWindow;

	public StudentCardDialog(JFrame owner, String title) {
		super(owner, title, true);
		setSize(new Dimension(1000, 735));
		mainWindow = (MainWindow) owner;
		BoxLayout layout1 = new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS);
		getContentPane().setLayout(layout1);
		// Создание панели информации о студенте
		JPanel infoPanel = new JPanel();
		// Создаем компоненты
		photoLabel = new JLabel(new ImageIcon("photos/default.jpg"));
		JLabel nameLabel = new JLabel("ФИО:");
		Dimension txtFieldDimension = new Dimension(300, 20);
		txtFullName = new JTextField(20);
		txtFullName.setMaximumSize(txtFieldDimension);
		txtFullName.setEditable(false);
		txtFullName.setBorder(null);
		emailLabel = new JLabel("Почта:");
		txtEmail = new JTextField(20);
		txtEmail.setMaximumSize(txtFieldDimension);
		txtEmail.setEditable(false);
		txtEmail.setBorder(null);
		phoneLabel = new JLabel("Телефон:");
		txtPhone = new JTextField(20);
		txtPhone.setMaximumSize(txtFieldDimension);
		txtPhone.setEditable(false);
		txtPhone.setBorder(null);
		JLabel gpaLabel = new JLabel("Средний балл:");
		txtGpa = new JTextField(20);
		txtGpa.setMaximumSize(txtFieldDimension);
		txtGpa.setEditable(false);
		txtGpa.setBorder(null);
		deleteButton = new JButton("Удалить студента");
		editButton = new JButton("Редактировать");

		deleteButton.setVisible(false);
		JButton btnEditPhoto = new JButton("Изменить фото");
		btnEditPhoto.setVisible(false);
		btnEditPhoto.setMaximumSize(new Dimension(10, 30));
		btnEditPhoto.setPreferredSize(btnEditPhoto.getMaximumSize());
		JPanel photoPanel = new JPanel(new BorderLayout());
		photoPanel.setPreferredSize(new Dimension(200, 240));
		photoPanel.setMaximumSize(photoPanel.getPreferredSize());
		photoPanel.setMinimumSize(photoPanel.getPreferredSize());
		photoPanel.add(photoLabel, BorderLayout.CENTER);
		photoPanel.add(btnEditPhoto, BorderLayout.SOUTH);
		// Создаем менеджер компоновки
		GroupLayout layout = new GroupLayout(infoPanel);
		infoPanel.setLayout(layout);

		// Определяем горизонтальную группу
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addComponent(photoPanel)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(nameLabel)
						.addComponent(emailLabel)
						.addComponent(phoneLabel)
						.addComponent(gpaLabel)
						.addComponent(editButton))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(txtFullName)
						.addComponent(txtEmail)
						.addComponent(txtPhone)
						.addComponent(txtGpa)
						.addComponent(deleteButton))
		);


// Задаем расстояние между строками
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(photoPanel)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(nameLabel)
								.addComponent(txtFullName))
						.addGap(10) // добавляем 10 пикселей расстояния между строками
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(emailLabel)
								.addComponent(txtEmail))
						.addGap(10) // добавляем 10 пикселей расстояния между строками
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(phoneLabel)
								.addComponent(txtPhone))
						.addGap(10) // добавляем 10 пикселей расстояния между строками
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(gpaLabel)
								.addComponent(txtGpa))
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
			jButton.setPreferredSize(new Dimension(80, 30));
			markPanel.add(jButton);
		}
		// Добавление панели календаря
		calendarPanel = new JPanel(new BorderLayout());
		calendarPanel.setBorder(BorderFactory.createTitledBorder("Посещаемость"));
		calendarPanel.setPreferredSize(new Dimension(800, 500));
		calendarPanel.setMinimumSize(calendarPanel.getPreferredSize());
		calendarPanel.setMaximumSize(calendarPanel.getPreferredSize());
		photoLabel.setPreferredSize(new Dimension(200, 220));
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
	}

	public void update(Student student) {
		this.setLocationRelativeTo(mainWindow);
		calendarPanel.removeAll();
		txtFullName.setText(student.getSurname() + " " + student.getName() +
				" " + student.getMiddleName());
		txtEmail.setText(student.getEmail());
		txtPhone.setText(student.getTelephone());
		txtGpa.setText(String.valueOf(student.getMark()));
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
			if (student.getGradeList().stream().anyMatch(grade -> grade.getLab() == lab.getId())) {
				labGrade = student.getGradeList().stream().filter(grade -> grade.getLab() == lab.getId()).findFirst().get().getGrade();
			}
			// Создаем новую кнопку с датой и оценкой студента
			JButton labButton = new JButton("<html>" + labDate + "<br> Оценка: " + labGrade + "</html>");

			if (student.getAttendanceList().stream()
					.filter(attendance -> attendance.getLab() == lab.getId()).toList().size() > 0) {
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
								Grade grade1 = new Grade(lab.getId(), student.getId(), Integer.parseInt(grade));
								student.getGradeList().add(grade1);
								txtGpa.setText(String.valueOf(student.getMark()));
								if (GradeDaoImpl.getInstance().save(grade1) != -1)
									System.out.println("Оценка не добавлена");
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
		StudentCardDialogListener studentCardDialogListener = new StudentCardDialogListener(this);
		deleteButton.addActionListener(studentCardDialogListener);
		editButton.addActionListener(studentCardDialogListener);
	}

	public JLabel getPhotoLabel() {
		return photoLabel;
	}

	public JButton getDeleteButton() {
		return deleteButton;
	}

	public JButton getEditButton() {
		return editButton;
	}

	public JPanel getCalendarPanel() {
		return calendarPanel;
	}

	public Student getStudent() {
		return student;
	}
}