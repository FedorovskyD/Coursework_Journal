package gui;

import database.dao.impl.GradeDaoImpl;
import entity.Absence;
import entity.Grade;
import entity.Lesson;
import entity.Student;
import listeners.LessonButtonKeyListener;
import listeners.LessonButtonMouseListener;
import listeners.StudentCardDialogListener;
import utils.PhotoUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс
 * для отображения информации
 * о студенте, в верхней части отображаются
 * персональные данные студента, в нижней части
 * отображается информация о посещаемости студента,
 * класс также служит для редактирования как персональных данных,
 * так и информации о посещаемости, а также о успеваемости студента,
 * если главная таблица отображает данные о лабораторных работах
 *
 * @author Fedorovsky D. A.
 */
public class StudentCard extends JDialog {
	private final JLabel jlblPhoto;
	private final JTextField jTextFieldLastName, jTextFieldFirstName, jTextFieldMiddleName, jTextFieldEmail, jTextFieldPhone;
	private final JLabel jlblAverageGradeValue, jlblLessonCountValue, jlblStudentHoursValue, jlblPercentageValue;
	private final JLabel jlblAverageMark;
	private final JButton jbtnDeleteStudent, jbtnEditStudent, jbtnEditPhoto;
	private final JPanel jpanelCalendar;
	private LessonButton currentLessonButton;
	private final List<LessonButton> lessonButtons;
	private final MainFrame mainFrame;
	private final JScrollPane jScrollPaneCalendar;
	private final JPanel jpanelGrade;
	private final List<JButton> gradeButtons;

	/**
	 * Конструктор класса, в котором инициализируются
	 * компоненты пользовательского интерфейса,
	 * задается расположение компонетов на окне,
	 * регистрируются слушатели этих компонентов
	 *
	 * @param owner Родительское окно
	 * @param title Заголовок
	 */
	public StudentCard(JFrame owner, String title) {
		super(owner, title, true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(new Dimension(1000, 800));
		mainFrame = (MainFrame) owner;
		BoxLayout layout = new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS);
		getContentPane().setLayout(layout);
		// Создание панели информации о студенте
		JPanel infoPanel = new JPanel();
		// Создаем компоненты
		jlblPhoto = new JLabel();
		jlblLessonCountValue = new JLabel();
		jlblStudentHoursValue = new JLabel();
		jlblPercentageValue = new JLabel();
		JLabel jlblLessonHours = new JLabel("Часов занятий:");
		JLabel jlblAttendanceHours = new JLabel("Часов присутствия:");
		JLabel jlblPercentage = new JLabel("Процент посещения:");
		JLabel jlblFullName = new JLabel("ФИО:");
		Dimension txtFieldDimension = new Dimension(300, 20);
		jTextFieldLastName = new JTextField(0);
		jTextFieldFirstName = new JTextField(0);
		jTextFieldMiddleName = new JTextField(0);
		jTextFieldLastName.setEditable(false);
		jTextFieldLastName.setBorder(null);
		jTextFieldMiddleName.setEditable(false);
		jTextFieldMiddleName.setBorder(null);
		jTextFieldFirstName.setEditable(false);
		jTextFieldFirstName.setBorder(null);
		JPanel jpanelFullName = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		jpanelFullName.setPreferredSize(new Dimension(500, 15));
		jpanelFullName.setMaximumSize(jpanelFullName.getPreferredSize());
		jpanelFullName.setMinimumSize(jpanelFullName.getPreferredSize());
		jpanelFullName.add(jTextFieldLastName);
		jpanelFullName.add(jTextFieldFirstName);
		jpanelFullName.add(jTextFieldMiddleName);
		JLabel jlblEmail = new JLabel("Почта:");
		jTextFieldEmail = new JTextField(20);
		jTextFieldEmail.setMaximumSize(txtFieldDimension);
		jTextFieldEmail.setEditable(false);
		jTextFieldEmail.setBorder(null);
		JLabel jlblPhone = new JLabel("Телефон:");
		jTextFieldPhone = new JTextField(20);
		jTextFieldPhone.setMaximumSize(txtFieldDimension);
		jTextFieldPhone.setEditable(false);
		jTextFieldPhone.setBorder(null);
		jlblAverageMark = new JLabel(mainFrame.getJradiobtnLab().isSelected() ? "Средний балл:" : " ");
		jlblAverageGradeValue = new JLabel();
		jbtnDeleteStudent = new JButton("Удалить студента");
		jbtnEditStudent = new JButton("Редактировать");
		jbtnDeleteStudent.setVisible(false);
		jbtnEditPhoto = new JButton("Изменить фото");
		jbtnEditPhoto.setVisible(false);
		jbtnEditPhoto.setMaximumSize(new Dimension(187, 25));
		jbtnEditPhoto.setPreferredSize(jbtnEditPhoto.getMaximumSize());
		jbtnEditPhoto.setMinimumSize(jbtnEditPhoto.getMaximumSize());
		jlblPhoto.setPreferredSize(new Dimension(187, 250));
		jlblPhoto.setMaximumSize(jlblPhoto.getPreferredSize());
		jlblPhoto.setMaximumSize(jlblPhoto.getPreferredSize());
		gradeButtons = new ArrayList<>();
		lessonButtons = new ArrayList<>();
		// Создаем менеджер компоновки
		GroupLayout groupLayout = new GroupLayout(infoPanel);
		infoPanel.setLayout(groupLayout);
		// Определяем горизонтальную группу
		groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
				.addGap(15)
				.addGroup(groupLayout.createParallelGroup()
						.addComponent(jlblPhoto).addComponent(jbtnEditPhoto))
				.addGap(15)
				.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(jlblFullName)
						.addComponent(jlblEmail)
						.addComponent(jlblPhone)
						.addComponent(jlblLessonHours)
						.addComponent(jlblAttendanceHours)
						.addComponent(jlblPercentage)
						.addComponent(jlblAverageMark)
						.addComponent(jbtnEditStudent))
				.addGap(10)
				.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(jpanelFullName)
						.addComponent(jTextFieldEmail)
						.addComponent(jTextFieldPhone)
						.addComponent(jlblLessonCountValue)
						.addComponent(jlblStudentHoursValue)
						.addComponent(jlblPercentageValue)
						.addComponent(jlblAverageGradeValue)
						.addComponent(jbtnDeleteStudent)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGap(15).addComponent(jlblPhoto)
						.addComponent(jbtnEditPhoto))
				.addGroup(groupLayout.createSequentialGroup()
						.addGap(15)
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(jlblFullName)
								.addComponent(jpanelFullName))
						.addGap(10) // добавляем 10 пикселей расстояния между строками
				.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(jlblEmail)
						.addComponent(jTextFieldEmail))
						.addGap(10) // добавляем 10 пикселей расстояния между строками
				.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(jlblPhone)
						.addComponent(jTextFieldPhone))
						.addGap(10) // добавляем 10 пикселей расстояния между строками
				.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(jlblLessonHours)
						.addComponent(jlblLessonCountValue))
						.addGap(10) // добавляем 10 пикселей расстояния между строками
				.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(jlblAttendanceHours)
						.addComponent(jlblStudentHoursValue))
						.addGap(10) // добавляем 10 пикселей расстояния между строками
				.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(jlblPercentage)
						.addComponent(jlblPercentageValue))
						.addGap(10) // добавляем 10 пикселей расстояния между строками
				.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(jlblAverageMark)
						.addComponent(jlblAverageGradeValue))
						.addGap(50) // добавляем 10 пикселей расстояния между строками
				.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(jbtnEditStudent)
						.addComponent(jbtnDeleteStudent))));
		// Устанавливаем размер панели с информацией о студенте
		infoPanel.setPreferredSize(new Dimension(1000, 300));
		infoPanel.setMinimumSize(infoPanel.getPreferredSize());
		infoPanel.setMaximumSize(infoPanel.getPreferredSize());
		jpanelGrade = new JPanel(new GridLayout(1, 11));
		jpanelGrade.setMinimumSize(new Dimension(100, 100));
		// Создаем кнопки для выставления оценок
		createGradeButtons(jpanelGrade);
		// Добавление панели календаря
		jpanelCalendar = new JPanel(new GridLayout(0, 5, 5, 5));
		jScrollPaneCalendar = new JScrollPane(jpanelCalendar);
		jScrollPaneCalendar.setBorder(BorderFactory.createTitledBorder(mainFrame.getJradiobtnLab().isSelected() ? "Лабораторные работы" : "Лекции"));
		jpanelCalendar.setPreferredSize(new Dimension(800, 500));
		jpanelCalendar.setMinimumSize(jpanelCalendar.getPreferredSize());
		jpanelCalendar.setMaximumSize(jpanelCalendar.getPreferredSize());
		add(infoPanel);
		add(jpanelGrade);
		add(jScrollPaneCalendar);
		jpanelGrade.setVisible(mainFrame.getJradiobtnLab().isSelected());
		//Регистрация слушателей
		StudentCardDialogListener studentCardDialogListener = new StudentCardDialogListener(this);
		jbtnEditPhoto.addActionListener(e -> editPhoto());
		jbtnDeleteStudent.addActionListener(studentCardDialogListener);
		jbtnEditStudent.addActionListener(studentCardDialogListener);
		// Слушатель для запрета закрытия карточки если включен режим редактирования
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (jbtnEditStudent.getText().equalsIgnoreCase("редактировать")) {
					mainFrame.refreshStudentTable();
					setVisible(false);
				} else {
					JOptionPane.showMessageDialog(mainFrame, "Перед закрытием карточки нужно завершить редактирование");
				}
			}
		});
		// Слушатель помещающий фокус на кнопку занятия при активации окна
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				if (currentLessonButton != null) {
					currentLessonButton.requestFocus();
				}
			}
		});
		setLocationRelativeTo(owner);
	}

	/**
	 * Обновляет карточку студента.
	 *
	 * @param student Студент, данные которого будут обновлены на карточке.
	 */
	public void updateStudentCard(Student student) {
		if (student == null) return;
		// Обновляем информацию о студенте
		setStudentInformation(student);
		// Обновляем данные о посещаемости
		boolean isLectureSelected = mainFrame.getJradiobtnLecture().isSelected();
		if (!isLectureSelected) {
			updateAttendanceAndGrade(student, student.getLabAbsenceHours(), mainFrame.getCurrentGroup().getLabHours());
		} else {
			updateAttendanceAndGrade(student, student.getLectureAbsenceHours(), mainFrame.getCurrentGroup().getLectureHours());
		}
		// Загружаем фото
		Image studentPhoto = PhotoUtils.getInstance().loadPhoto(student).getImage();
		updateStudentPhoto(studentPhoto);

		List<Lesson> lessons = isLectureSelected ? mainFrame.getCurrGroup().getLectures() : mainFrame.getCurrentGroup().getLabs();
		updateLessonButtons(lessons);

		mainFrame.getStudentTable().repaint();
	}


	private void setStudentInformation(Student student) {
		// Обновляем текстовые поля с информацией о студенте
		jTextFieldLastName.setText(student.getLastName());
		jTextFieldMiddleName.setText(student.getMiddleName());
		jTextFieldFirstName.setText(student.getFirstName());
		jTextFieldEmail.setText(student.getEmail());
		jTextFieldPhone.setText(student.getTelephone());
	}

	/*
	 * Обновляем данные о посещаемости и среднем балле на карточке
	 */
	private void updateAttendanceAndGrade(Student student, int absenceHours, int totalHours) {
		// Если отображаются данные по лабораторным устанавливаем значение для среднего балла
		if (!mainFrame.getJradiobtnLecture().isSelected()) {
			jlblAverageGradeValue.setText(String.format("%.2f", student.getAverageGrade()));
			jlblAverageMark.setText("Cредний балл:");
		} else {
			jlblAverageMark.setText("");
		}
		jlblLessonCountValue.setText(String.valueOf(totalHours));
		int hoursAttendance = totalHours - absenceHours;
		jlblStudentHoursValue.setText(String.valueOf(hoursAttendance));
		jlblPercentageValue.setText(String.format("%.1f ", ((double) hoursAttendance / totalHours) * 100) + " %");
	}

	/*
	 * Обновляем фото на карточке
	 */
	private void updateStudentPhoto(Image photo) {
		if (photo != null) {
			jlblPhoto.setSize(new Dimension(187, 250));
			ImageIcon icon = new ImageIcon(photo.getScaledInstance(jlblPhoto.getWidth(), jlblPhoto.getHeight(), Image.SCALE_SMOOTH));
			jlblPhoto.setIcon(icon);
		} else {
			jlblPhoto.setSize(new Dimension(0, 0));
		}
	}
	/*
	 * Обновляем кнопки отвечающие за отображение
	 * информации о посещаемости
	 */
	private void updateLessonButtons(List<Lesson> lessons) {
		if (lessonButtons.isEmpty()) {
			createLessonButtons(lessons);
		} else {
			updateExistingLessonButtons();
		}
	}
	/*
	 * Создаем кнопки для отображения посещаемости
	 */
	private void createLessonButtons(List<Lesson> lessons) {
		jpanelCalendar.removeAll();
		lessonButtons.clear();
		for (Lesson lesson : lessons) {
			LessonButton lessonButton = createLessonButton(lesson);
			setButtonListeners(lessonButton);
			lessonButtons.add(lessonButton);
			jpanelCalendar.add(lessonButton);
			setInitialSelection(lessonButton);
		}
	}
	/*
	 * Создаем кнопку для отображения информации
	 * о посещении занятия и успеваемости
	 */
	private LessonButton createLessonButton(Lesson lesson) {
		LessonButton lessonButton = new LessonButton(lesson);
		// Получаем информацию о оценке на занятии
		Grade grade = getCurrStudent().getLessonGrade(lesson);
		// Получаем информацию об отсутствии на занятии
		Absence absence = getCurrStudent().getLessonAbsence(lesson);
		// В зависимости от типа занятия выбираем вариан отображения информации о посещаемости
		if (mainFrame.getJradiobtnLecture().isSelected() || lesson.isLecture()) {
			lessonButton.setDate();
		} else if (grade != null) {
			lessonButton.setDateWithGrade(String.valueOf(grade.getGrade()));
		} else {
			lessonButton.setDate();
		}

		lessonButton.setPreferredSize(new Dimension(180, 80));

		if (absence != null) {
			lessonButton.setHalf(absence.isHalf());
		}

		lessonButton.setAbsence(absence != null);
		return lessonButton;
	}
	/*
	 * Обновляем уже существующие кнопки с занятиями
	 * метод используется если карточка обновляется
	 * в рамках одной группы, чтобы не создавать массив
	 * кнопок каждый раз при обновленни карточки студента
	 */
	private void updateExistingLessonButtons() {
		for (LessonButton lessonButton : lessonButtons) {
			SwingUtilities.invokeLater(() -> {
				// Получаем данные о оценке студента на занятии
				Grade grade = getCurrStudent().getLessonGrade(lessonButton.getLesson());
				// В зависимости от типа занятия выбираем вариан отображения информации о посещаемости
				if (mainFrame.getJradiobtnLecture().isSelected()) {
					lessonButton.setDate();
				} else if (grade != null) {
					lessonButton.setDateWithGrade(String.valueOf(grade.getGrade()));
				} else {
					lessonButton.setDate();
				}
				// Получаем данные о присутствии студента на занятии
				Absence absence = getCurrStudent().getLessonAbsence(lessonButton.getLesson());
				if (absence != null) {
					lessonButton.setHalf(absence.isHalf());
				}
				lessonButton.setAbsence(absence != null);
				setInitialSelection(lessonButton);
				lessonButton.repaint();
			});
		}
	}
	/*
	 * Находим кнопку с выбранным занятием и делаем ее текущей
	 */
	private void setInitialSelection(LessonButton lessonButton) {
		lessonButton.setCurrentColor(false);
		if (lessonButton.getLesson().equals(mainFrame.getCurrDate())) {
			if (currentLessonButton != null) {
				currentLessonButton.setCurrentColor(false);
			}
			lessonButton.setCurrentColor(true);
			currentLessonButton = lessonButton;
		}
	}
	/*
	 * Устанавливаем слушателей для кнопок с занятиями
	 */
	private void setButtonListeners(LessonButton lessonButton) {
		lessonButton.addMouseListener(new LessonButtonMouseListener(mainFrame.getJDialogStudentCard()));
		lessonButton.addKeyListener(new LessonButtonKeyListener(mainFrame.getJDialogStudentCard()));
		lessonButton.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (jbtnEditStudent.getText().equalsIgnoreCase("сохранить")) {
					jbtnEditStudent.requestFocus();
				}
			}
			@Override
			public void focusLost(FocusEvent e) {}
		});
	}

	/*
	 * Выбираем новое фото из файловой системы
	 * для отображения в карточке студента
	 */
	private void editPhoto() {
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			try {
				PhotoUtils.getInstance().savePhoto(mainFrame.getCurrentStudentFromTable(), selectedFile);
				BufferedImage image = ImageIO.read(mainFrame.getCurrentStudentFromTable().getPhotoPath());
				// Масштабируем изображение и создаем иконку
				ImageIcon icon = new ImageIcon(image.getScaledInstance(jlblPhoto.getWidth(), jlblPhoto.getHeight(), Image.SCALE_SMOOTH));
				// Устанавливаем иконку изображения в JLabel
				jlblPhoto.setIcon(icon);
			} catch (IOException ex) {
				System.out.println("Не удалось сохранить фото");
			}
		}
	}
	/*
	 * Создаем массив кнопок с оценками
	 */
	private void createGradeButtons(JPanel gradePanel) {
		for (int i = 0; i < 11; i++) {
			JButton gradeButton = new JButton(String.valueOf(i));
			gradeButton.addActionListener(e -> setGradeOnLessonButton(gradeButton));
			// Устанавливаем предпочтительные
			gradeButton.setPreferredSize(new Dimension(80, 30));
			gradeButtons.add(gradeButton);
			gradePanel.add(gradeButton);
		}
	}
	/*
	 * Выставляем оценку за занятие
	 */
	private void setGradeOnLessonButton(JButton gradeButton) {
		if (currentLessonButton.getLesson().isLecture()) return;
		String gradeStr;
		if ((!currentLessonButton.getLesson().isHoliday())) {
			if (currentLessonButton.getGrade().equals("1") && gradeButton.getText().equals("0")) {
				currentLessonButton.setDateWithGrade("10");
				gradeStr = "10";
			} else {
				currentLessonButton.setDateWithGrade(gradeButton.getText());
				gradeStr = gradeButton.getText();
			}

			Grade grade = getCurrStudent().getLessonGrade(currentLessonButton.getLesson());
			// Если оценки не существует создаем
			if (grade == null) {
				long id;
				Grade newGrade = new Grade(currentLessonButton.getLesson().getId(), mainFrame.getCurrentStudent().getId(), Integer.parseInt(gradeStr));
				if ((id = GradeDaoImpl.getInstance().save(newGrade)) != -1) {
					System.out.println("Оценка c id " + id + " добавлена");
					newGrade.setId(id);
					getCurrStudent().getGradeList().add(newGrade);
					mainFrame.refreshStudentTable();
				}
			} else {
				// Если оценка существовала изменяем её значение
				grade.setGrade(Integer.parseInt(gradeStr));
				mainFrame.refreshStudentTable();
				if (GradeDaoImpl.getInstance().update(grade)) {
					System.out.println("Оценка c id = " + grade.getId() + " изменена");
				}
			}
			// Устанавливаем новое значение среднего балла
			if (mainFrame.getJradiobtnLab().isSelected()) {
				jlblAverageGradeValue.setText(String.format("%.2f", mainFrame.getCurrentStudentFromTable().getAverageGrade()));
			}
		} else {
			JOptionPane.showMessageDialog(mainFrame, "Нельзя выставить оценку в этот день");
		}
		currentLessonButton.requestFocus();
	}

	/**
	 * Возвращает JLabel для отображения среднего балла.
	 *
	 * @return JLabel для отображения среднего балла.
	 */
	public JLabel getJlblAverageGradeValue() {
		return jlblAverageGradeValue;
	}

	/**
	 * Возвращает JLabel для отображения среднего балла.
	 *
	 * @return JLabel для отображения среднего балла.
	 */
	public JLabel getJlblAverageMark() {
		return jlblAverageMark;
	}

	/**
	 * Возвращает кнопку удаления.
	 *
	 * @return Кнопка удаления.
	 */
	public JButton getJbtnDeleteStudent() {
		return jbtnDeleteStudent;
	}

	/**
	 * Возвращает кнопку редактирования.
	 *
	 * @return Кнопка редактирования.
	 */
	public JButton getJbtnEditStudent() {
		return jbtnEditStudent;
	}

	/**
	 * Возвращает панель календаря.
	 *
	 * @return Панель календаря.
	 */
	public JPanel getJpanelCalendar() {
		return jpanelCalendar;
	}

	/**
	 * Возвращает список кнопок для лабораторных занятий.
	 *
	 * @return Список кнопок для лабораторных занятий.
	 */
	public List<LessonButton> getLabButtons() {
		return lessonButtons;
	}

	/**
	 * Возвращает текущую выбранную кнопку занятия.
	 *
	 * @return Текущая выбранная кнопка занятия.
	 */
	public LessonButton getCurrentLessonButton() {
		return currentLessonButton;
	}

	/**
	 * Возвращает JScrollPane для календаря.
	 *
	 * @return JScrollPane для календаря.
	 */
	public JScrollPane getJScrollPaneCalendar() {
		return jScrollPaneCalendar;
	}

	/**
	 * Возвращает текущего студента.
	 *
	 * @return Текущий студент.
	 */
	public Student getCurrStudent() {
		return mainFrame.getCurrentStudentFromTable();
	}

	/**
	 * Возвращает JTextField для фамилии.
	 *
	 * @return JTextField для фамилии.
	 */
	public JTextField getJTextFieldLastName() {
		return jTextFieldLastName;
	}

	/**
	 * Возвращает JTextField для электронной почты.
	 *
	 * @return JTextField для электронной почты.
	 */
	public JTextField getJTextFieldEmail() {
		return jTextFieldEmail;
	}

	/**
	 * Возвращает JTextField для телефона.
	 *
	 * @return JTextField для телефона.
	 */
	public JTextField getJTextFieldPhone() {
		return jTextFieldPhone;
	}

	/**
	 * Возвращает кнопку редактирования фото.
	 *
	 * @return Кнопка редактирования фото.
	 */
	public JButton getJbtnEditPhoto() {
		return jbtnEditPhoto;
	}

	/**
	 * Возвращает главное окно.
	 *
	 * @return Главное окно.
	 */
	public MainFrame getMainWindow() {
		return mainFrame;
	}

	/**
	 * Возвращает панель с оценками.
	 *
	 * @return Панель с оценками.
	 */
	public JPanel getJpanelGrade() {
		return jpanelGrade;
	}

	/**
	 * Возвращает список кнопок занятий.
	 *
	 * @return Список кнопок занятий.
	 */
	public List<LessonButton> getLessonButtons() {
		return lessonButtons;
	}

	/**
	 * Устанавливает текущую выбранную кнопку занятия.
	 *
	 * @param currentLessonButton Текущая выбранная кнопка занятия.
	 */
	public void setCurrentLessonButton(LessonButton currentLessonButton) {
		this.currentLessonButton = currentLessonButton;
	}

	/**
	 * Возвращает список кнопок оценок.
	 *
	 * @return Список кнопок оценок.
	 */
	public List<JButton> getGradeButtons() {
		return gradeButtons;
	}

	/**
	 * Возвращает JTextField для имени.
	 *
	 * @return JTextField для имени.
	 */
	public JTextField getJTextFieldFirstName() {
		return jTextFieldFirstName;
	}

	/**
	 * Возвращает JTextField для отчества.
	 *
	 * @return JTextField для отчества.
	 */
	public JTextField getJTextFieldMiddleName() {
		return jTextFieldMiddleName;
	}

}