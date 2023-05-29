package gui;

import dialogs.AboutAuthorDialog;
import dialogs.EmailConfigDialog;
import database.dao.GroupDao;
import database.dao.impl.GroupDaoImpl;
import entity.Group;
import entity.Lesson;
import entity.Student;
import listeners.StudentTableKeyListener;
import listeners.StudentTableListSelectionListener;
import listeners.StudentTableMouseListener;
import listeners.MainFrameListener;
import utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Класс главного окна, основным элементом является таблица,
 * позволяющая отображать информацию о посещаемости занятий студентами.
 * Сверху расположены элементы управления отображением данных в таблице,
 * справа находятся кнопки, позволяющие редактировать данные о группах,
 * студентах, занятиях, а также кнопки для сохранения отчета о посещаемости
 * и отправки этого отчета по электронной почте.
 */
public class MainFrame extends JFrame {
	private final JButton jbtnAddStudent, jbtnAddLab, jbtnAddGroup, jbtnDeleteGroup, jbtnDeleteLesson;
	private final StudentTable studentTable;
	private final StudentCard studentCard;
	private final JRadioButton jradiobtnLecture, jradiobtnLab, jradiobtnInc, jradiobtnDec;
	private final JComboBox<Group> jcmbGroupNumber;
	private final JComboBox<Lesson> jcmbCurrentDate;
	private final JComboBox<String> jcmbSortOption;
	private final List<Group> groups;
	private Student currentStudent;
	private final JCheckBox jCheckBoxMarkUnder;

	/**
	 * Конструктор класса главного окна, в котором инициализируются
	 * компоненты пользовательского интерфейса, задается их расположение
	 * на окне, регистрируются слушатели этих компонентов
	 */
	public MainFrame() {
		//Получаем данные о группах из базы данных
		GroupDao groupDao = GroupDaoImpl.getInstance();
		groups = groupDao.findAll();
		//Создаем элементы гланого окна
		JLabel currDateLbl = new JLabel("Текущая дата:");
		ButtonGroup buttonGroupLessonType = new ButtonGroup();
		jradiobtnLab = new JRadioButton("Лабораторные", true);
		jradiobtnLecture = new JRadioButton("Лекции", false);
		buttonGroupLessonType.add(jradiobtnLab);
		buttonGroupLessonType.add(jradiobtnLecture);
		ButtonGroup buttonGroupSortType = new ButtonGroup();
		jradiobtnInc = new JRadioButton("в порядке возрастания", true);
		jradiobtnDec = new JRadioButton("в порядке убывания", false);
		buttonGroupSortType.add(jradiobtnInc);
		buttonGroupSortType.add(jradiobtnDec);
		jCheckBoxMarkUnder = new JCheckBox("отмечать подряд");
		JLabel lblSort = new JLabel("Сортировать по: ");
		jcmbSortOption = new JComboBox<>();
		refreshSortCmb();
		jcmbSortOption.setPreferredSize(new Dimension(100, 30));
		jcmbSortOption.setMaximumSize(jcmbSortOption.getPreferredSize());
		// Создание меню
		JMenu menuFile = new JMenu("Файл");
		JMenuBar menuBar = new JMenuBar();
		// Создание пунктов меню
		JMenuItem menuItemAboutAuthor = new JMenuItem("Об авторе");
		JMenuItem menuItemConfigEmail = new JMenuItem("Настроить почту");
		JMenuItem menuItemConfigDateBase = new JMenuItem("Настроить подключение к БД");
		JMenuItem menuItemHelp = new JMenuItem("Помощь");
		JMenuItem menuItemAboutProgram = new JMenuItem("О программе");
		JMenuItem menuItemExit = new JMenuItem("Выход");
		// Добавление пунктов в меню

		menuFile.add(menuItemConfigEmail);
		menuFile.add(menuItemConfigDateBase);
		menuFile.add(menuItemHelp);
		menuFile.add(menuItemAboutProgram);
		menuFile.add(menuItemAboutAuthor);
		menuFile.addSeparator();
		menuFile.add(menuItemExit);
		// Добавление меню на фрейм
		menuBar.add(menuFile);
		setJMenuBar(menuBar);
		//Добавляем label и combobox для выбора номера группы
		JLabel lblGroupNumber = new JLabel("Номер группы:");
		jcmbGroupNumber = new JComboBox<>();
		refreshGroupCmb();
		jcmbGroupNumber.setPreferredSize(new Dimension(100, 30));
		jcmbGroupNumber.setMaximumSize(jcmbGroupNumber.getPreferredSize());
		// Создаем кнопки
		Dimension btnDimension = new Dimension(200,30);
		JButton btnSaveTable = new JButton("Сохранить таблицу в файл");
		btnSaveTable.setMinimumSize(btnDimension);
		btnSaveTable.setMaximumSize(btnDimension);
		jbtnAddStudent = new JButton("Добавить студента");
		jbtnAddStudent.setMinimumSize(btnDimension);
		jbtnAddStudent.setMinimumSize(btnDimension);
		jbtnAddGroup = new JButton("Добавить группу");
		jbtnAddGroup.setMinimumSize(btnDimension);
		jbtnAddGroup.setMaximumSize(btnDimension);
		jbtnDeleteGroup = new JButton("Удалить группу");
		jbtnDeleteGroup.setMinimumSize(btnDimension);
		jbtnDeleteGroup.setMaximumSize(btnDimension);
		jbtnAddLab = new JButton("Добавить занятие");
		jbtnAddLab.setMinimumSize(btnDimension);
		jbtnAddLab.setMaximumSize(btnDimension);
		jbtnDeleteLesson = new JButton("Удалить занятие");
		jbtnDeleteLesson.setMinimumSize(btnDimension);
		jbtnDeleteLesson.setMaximumSize(btnDimension);
		JButton jbtnSendByEmail = new JButton("Отправить на почту");
		jbtnSendByEmail.setMinimumSize(btnDimension);
		jbtnSendByEmail.setMaximumSize(btnDimension);
		//Создаем combobox для выбора даты занятия
		jcmbCurrentDate = new JComboBox<>();
		refreshDateCmb();
		jcmbCurrentDate.setPreferredSize(new Dimension(100, 30));
		jcmbCurrentDate.setMaximumSize(jcmbCurrentDate.getPreferredSize());
		//Создаем таблицу для отображения списка студентов
		studentTable = new StudentTable(getCurrGroup(), jradiobtnLecture.isSelected(), getJcmbCurrentDate());
		studentTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		//Создаем карточку для отображения информации о студенте
		studentCard = new StudentCard(this, "Карточка студента");
		refreshStudentTable();
		JScrollPane scrollPane = new JScrollPane(studentTable);
		//Задаем расположение раннее заданным компонентам
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		getContentPane().setLayout(groupLayout);
		groupLayout.setAutoCreateGaps(true);
		groupLayout.setAutoCreateContainerGaps(true);
		// Компановка главной панели
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup()
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(lblGroupNumber)
						.addComponent(jcmbGroupNumber)
						.addGroup(groupLayout.createParallelGroup()
								.addComponent(jradiobtnLecture)
								.addComponent(jradiobtnLab))
						.addComponent(currDateLbl)
						.addComponent(jcmbCurrentDate)
						.addComponent(lblSort)
						.addComponent(jcmbSortOption)
						.addGroup(groupLayout.createParallelGroup()
								.addComponent(jradiobtnInc)
								.addComponent(jradiobtnDec))
						.addComponent(jCheckBoxMarkUnder))
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(scrollPane)
						.addGroup(groupLayout.createParallelGroup()
								.addComponent(jbtnAddStudent)
								.addComponent(jbtnAddGroup)
								.addComponent(jbtnDeleteGroup)
								.addComponent(jbtnAddLab)
								.addComponent(jbtnDeleteLesson)
								.addComponent(btnSaveTable)
								.addComponent(jbtnSendByEmail))
				)
		);
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup()
				.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup()
								.addComponent(lblGroupNumber)
								.addComponent(jcmbGroupNumber)
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(jradiobtnLecture)
										.addComponent(jradiobtnLab))
								.addComponent(currDateLbl)
								.addComponent(jcmbCurrentDate)
								.addComponent(lblSort)
								.addComponent(jcmbSortOption)
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(jradiobtnInc)
										.addComponent(jradiobtnDec))
								.addComponent(jCheckBoxMarkUnder))
						.addComponent(scrollPane))
				.addGroup(groupLayout.createSequentialGroup()
						.addGap(50)
						.addComponent(jbtnAddStudent)
						.addComponent(jbtnAddGroup)
						.addComponent(jbtnDeleteGroup)
						.addComponent(jbtnAddLab)
						.addComponent(jbtnDeleteLesson)
						.addComponent(jbtnSendByEmail)
						.addComponent(btnSaveTable))
		);
		// Добавление слушателей
		addWindowListener(new WindowAdapter() {
			public void windowActivated(WindowEvent e) {
				studentTable.requestFocus();
			}
		});
		MainFrameListener mainFrameListener = new MainFrameListener(this);
		StudentTableListSelectionListener studentTableListSelectionListener = new StudentTableListSelectionListener(this);
		StudentTableMouseListener studentTableMouseListener = new StudentTableMouseListener(this);
		StudentTableKeyListener studentTableKeyListener = new StudentTableKeyListener(this);
		jbtnAddStudent.addActionListener(mainFrameListener);
		jbtnAddGroup.addActionListener(mainFrameListener);
		jbtnDeleteGroup.addActionListener(mainFrameListener);
		jbtnAddLab.addActionListener(mainFrameListener);
		studentTable.getSelectionModel().addListSelectionListener(mainFrameListener);
		jcmbGroupNumber.addActionListener(mainFrameListener);
		jradiobtnDec.addActionListener(mainFrameListener);
		jradiobtnLecture.addActionListener(mainFrameListener);
		jradiobtnLab.addActionListener(mainFrameListener);
		jradiobtnInc.addActionListener(mainFrameListener);
		jbtnDeleteLesson.addActionListener(mainFrameListener);
		jcmbSortOption.addActionListener(mainFrameListener);
		jcmbCurrentDate.addActionListener(mainFrameListener);
		studentTable.getSelectionModel().addListSelectionListener(studentTableListSelectionListener);
		studentTable.addMouseListener(studentTableMouseListener);
		studentTable.addKeyListener(studentTableKeyListener);
		menuItemConfigEmail.addActionListener(e -> new EmailConfigDialog(null).setVisible(true));
		// Слушатель кнопки для отправки отчета по почте
		jbtnSendByEmail.addActionListener(e -> {
			String input = JOptionPane.showInputDialog(null, "Введите получателей через запятую:");
			if (input != null) {
				String body = getCurrentGroup().getName() + (jradiobtnLecture.isSelected() ? " Лекции" : " Лабораторные");
				String fileName = body + ".xlsx";
				EmailSender.sendEmail(input, "Отчет посещаемости до " + new SimpleDateFormat("dd.MM.yyyy").format(new Date()) + " " + getCurrGroup().getName(),
						body,
						ExcelTableUtil.createAttendanceTable(studentTable.getStudentTableModel()), fileName);
			}
		});
		menuItemConfigDateBase.addActionListener(e -> {
			int option = JOptionPane.showOptionDialog(null, "Для изменения подключения к БД необходимо закрыть главное окно. Продолжить?",
					"Предупреждение", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
			if (option == JOptionPane.YES_OPTION) {
				dispose();
				String jarPath = MainFrame.class.getProtectionDomain().getCodeSource().getLocation().getPath();
				String jarDirectory = new File(jarPath).getParent();
				File configFile = new File(jarDirectory, "config.properties");
				if (configFile.exists()) {
					new ConfigFrame(configFile).setVisible(true);
				}
			}
		});
		menuItemHelp.addActionListener(e -> new ProgramInfoWindow(studentCard.getMainWindow()));
		menuItemAboutAuthor.addActionListener(e -> new AboutAuthorDialog(null));
		btnSaveTable.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			// Устанавливаем режим выбора директории
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			// Открываем диалог выбора файла
			int result = fileChooser.showSaveDialog(null);
			// Если пользователь выбрал директорию и нажал "OK"
			if (result == JFileChooser.APPROVE_OPTION) {
				// Получаем выбранную директорию
				File selectedDir = fileChooser.getSelectedFile();
				// Открываем диалоговое окно для ввода названия файла
				String fileName = JOptionPane.showInputDialog(null, "Введите название файла:");
				if (fileName != null && !fileName.trim().isEmpty()) {
					// Создаем новый файл в выбранной директории с введенным названием
					fileName = fileName + ".xlsx";
				} else {
					fileName = (jradiobtnLab.isSelected() ? "Лабораторные " : "Лекции ") + getCurrentGroup() + ".xlsx";
				}
				File file = new File(selectedDir, fileName);
				System.out.println(file.getName());
				byte[] information = ExcelTableUtil.createAttendanceTable(getStudentTable().getStudentTableModel());
				try {
					FileOutputStream fos = new FileOutputStream(file);
					if (information != null) {
						fos.write(information);
					}
					fos.close();
					JOptionPane.showMessageDialog(null, "Файл успешно создан: " + file.getAbsolutePath());
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(null, "Файл не создан!");
					ex.printStackTrace();
				}
			}
		});
		menuItemAboutProgram.addActionListener(e -> {
			String programName = "Журнал посещаемости";
			String version = "1.0";
			String description = """
					"Журнал посещаемости" - это программное приложение, разработанное для отслеживания посещаемости
					 студентов в образовательных учреждениях. Оно позволяет преподавателям записывать и управлять
					 данными о посещаемости, генерировать отчеты о посещаемости студентов и отправлять их по почте.""";

			String aboutMessage = "Программа: " + programName + "\n"
					+ "Версия: " + version + "\n\n"
					+ "Описание: \n" + description;

			JOptionPane.showMessageDialog(studentCard.getMainWindow(), aboutMessage, "О программе", JOptionPane.INFORMATION_MESSAGE);
		});
		menuItemExit.addActionListener(e -> {  // Программно вызываем событие закрытия окна
			WindowEvent closeEvent = new WindowEvent(studentCard.getMainWindow(), WindowEvent.WINDOW_CLOSING);
			studentCard.getMainWindow().dispatchEvent(closeEvent);});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog(studentCard.getMainWindow(),
						"Вы действительно хотите выйти из приложения?",
						"Подтверждение выхода",
						JOptionPane.YES_NO_OPTION);

				if (result == JOptionPane.YES_NO_OPTION) {
					dispose(); // Закрываем окно приложения
				}
			}
		});
		setTitle("Журнал посещаемости");
		setSize(new Dimension(1920, 1080));
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setExtendedState(MAXIMIZED_BOTH);
		setVisible(true);
	}

	/**
	 * Получает комбо-бокс с номером группы.
	 *
	 * @return комбо-бокс с номером группы
	 */
	public JComboBox<Group> getJcmbGroupNumber() {
		return jcmbGroupNumber;
	}

	/**
	 * Получает таблицу студентов.
	 *
	 * @return таблица студентов
	 */
	public StudentTable getStudentTable() {
		return studentTable;
	}

	/**
	 * Получает кнопку "Добавить студента".
	 *
	 * @return кнопка "Добавить студента"
	 */
	public JButton getJbtnAddStudent() {
		return jbtnAddStudent;
	}

	/**
	 * Получает текущего выбранного студента из таблицы.
	 *
	 * @return текущий выбранный студент из таблицы
	 */
	public Student getCurrentStudentFromTable() {
		int selectedRow = studentTable.getSelectedRow();
		if (selectedRow != -1) {
			return studentTable.getStudentAt(selectedRow);
		}
		return null;
	}

	/**
	 * Получает текущего выбранного студента.
	 *
	 * @return текущий выбранный студент
	 */
	public Student getCurrentStudent() {
		return currentStudent;
	}

	/**
	 * Получает текущую выбранную группу.
	 *
	 * @return текущая выбранная группа
	 */
	public Group getCurrentGroup() {
		return (Group) jcmbGroupNumber.getSelectedItem();
	}

	/**
	 * Получает кнопку "Добавить группу".
	 *
	 * @return кнопка "Добавить группу"
	 */
	public JButton getJbtnAddGroup() {
		return jbtnAddGroup;
	}

	/**
	 * Получает кнопку "Удалить группу".
	 *
	 * @return кнопка "Удалить группу"
	 */
	public JButton getJbtnDeleteGroup() {
		return jbtnDeleteGroup;
	}

	/**
	 * Получает кнопку "Добавить занятие".
	 *
	 * @return кнопка "Добавить занятие"
	 */
	public JButton getBtnAddLesson() {
		return jbtnAddLab;
	}

	/**
	 * Получает комбо-бокс текущей даты занятия.
	 *
	 * @return комбо-бокс текущей даты занятия
	 */
	public JComboBox<Lesson> getJcmbCurrentDate() {
		return jcmbCurrentDate;
	}

	/**
	 * Получает текущую выбранную дату занятия.
	 *
	 * @return текущая выбранная дата занятия
	 */
	public Lesson getCurrDate() {
		return (Lesson) jcmbCurrentDate.getSelectedItem();
	}

	/**
	 * Получает текущую выбранную группу.
	 *
	 * @return текущая выбранная группа
	 */
	public Group getCurrGroup() {
		return (Group) jcmbGroupNumber.getSelectedItem();
	}

	/**
	 * Получает радио-кнопку "Лекция".
	 *
	 * @return радио-кнопку "Лекция"
	 */
	public JRadioButton getJradiobtnLecture() {
		return jradiobtnLecture;
	}

	/**
	 * Получает радио-кнопку "Лабораторная работа".
	 *
	 * @return радио-кнопку "Лабораторная работа"
	 */
	public JRadioButton getJradiobtnLab() {
		return jradiobtnLab;
	}

	/**
	 * Обновляет таблицу студентов.
	 */
	public void refreshStudentTable() {
		currentStudent = getCurrentStudentFromTable();
		studentTable.setModel(new StudentTableModel(getCurrGroup(), jradiobtnLecture.isSelected()));
		int selectedColumn = jcmbCurrentDate.getSelectedIndex() + (jradiobtnLecture.isSelected() ? 2 : 3);
		if (selectedColumn < studentTable.getColumnCount()) {
			studentTable.setColumnSelectionInterval(selectedColumn, selectedColumn);
		}
		sortTable();
		if (currentStudent != null) {
			int index = studentTable.getStudentTableModel().getRowIndex(currentStudent);
			if (index != -1) {
				studentTable.setRowSelectionInterval(index, index);
				studentTable.revalidate();
				studentTable.repaint();
			} else {
				Student student = studentTable.getStudentAt(0);
				if (student != null) {
					studentTable.setRowSelectionInterval(0, 0);
					currentStudent = student;
					studentCard.setVisible(false);
				}
			}
		} else {
			Student student = studentTable.getStudentAt(0);
			if (student != null) {
				studentTable.setRowSelectionInterval(0, 0);
				currentStudent = student;
				studentCard.setVisible(false);
			}
		}

		studentTable.getColumnModel().getColumn(0).setPreferredWidth(300);
		studentTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		if (!jradiobtnLecture.isSelected()) {
			studentTable.getColumnModel().getColumn(2).setPreferredWidth(100);
		}
		studentTable.setDefaultRenderer(Object.class, new StudentTableCellRender(selectedColumn));
	}

	/**
	 * Сортирует таблицу студентов.
	 */
	public void sortTable() {
		boolean isInc = jradiobtnInc.isSelected();
		String option = (String) jcmbSortOption.getSelectedItem();
		switch (Objects.requireNonNull(option)) {
			case Constants.SORT_BY_ALPHABET -> studentTable.getStudentTableModel().sortByAlphabet(isInc);
			case Constants.SORT_BY_GRADE -> studentTable.getStudentTableModel().sortByGrade(isInc);
			case Constants.SORT_BY_ATTENDANCE ->
					studentTable.getStudentTableModel().sortByAttendance(jradiobtnLecture.isSelected(), isInc);
		}
	}

	/**
	 * Обновляет комбо-бокс с номерами групп.
	 */
	public void refreshGroupCmb() {
		jcmbGroupNumber.setModel(new DefaultComboBoxModel<>(groups.toArray(new Group[0])));
	}

	/**
	 * Возвращает список групп.
	 *
	 * @return список групп
	 */
	public List<Group> getGroups() {
		return groups;
	}

	/**
	 * Обновляет комбо-бокс с вариантами сортировки.
	 */
	public void refreshSortCmb() {
		String option = (String) jcmbSortOption.getSelectedItem();
		List<String> sortOptions = new ArrayList<>();
		sortOptions.add(Constants.SORT_BY_ALPHABET);
		sortOptions.add(Constants.SORT_BY_ATTENDANCE);
		if (jradiobtnLab.isSelected()) {
			sortOptions.add(Constants.SORT_BY_GRADE);
		}
		jcmbSortOption.setModel(new DefaultComboBoxModel<>(sortOptions.toArray(new String[0])));
		if (option != null) {
			jcmbSortOption.setSelectedItem(option);
		}
	}

	/**
	 * Обновляет комбо-бокс с датами занятий.
	 */
	public void refreshDateCmb() {
		Lesson lesson = (Lesson) jcmbCurrentDate.getSelectedItem();
		List<Lesson> currLessons;
		if(getCurrGroup()!=null) {
			if (jradiobtnLecture.isSelected()) {
				currLessons = getCurrGroup().getLectures();
			} else {
				currLessons = getCurrGroup().getLabs();
			}
			jcmbCurrentDate.setModel(new DefaultComboBoxModel<>(currLessons.toArray(new Lesson[0])));
			if (lesson != null) {
				jcmbCurrentDate.setSelectedItem(lesson);
			}
		}
	}

	/**
	 * Получает диалоговое окно карточки студента.
	 *
	 * @return диалоговое окно карточки студента
	 */
	public StudentCard getJDialogStudentCard() {
		return studentCard;
	}

	/**
	 * Получает радио-кнопку "По возрастанию".
	 *
	 * @return радио-кнопку "По возрастанию"
	 */
	public JRadioButton getJradiobtnInc() {
		return jradiobtnInc;
	}

	/**
	 * Получает радио-кнопку "По убыванию".
	 *
	 * @return радио-кнопку "По убыванию"
	 */
	public JRadioButton getJradiobtnDec() {
		return jradiobtnDec;
	}

	/**
	 * Устанавливает текущего выбранного студента.
	 *
	 * @param currentStudent текущий выбранный студент
	 */
	public void setCurrentStudent(Student currentStudent) {
		this.currentStudent = currentStudent;
	}

	/**
	 * Получает комбо-бокс с вариантами сортировки.
	 *
	 * @return комбо-бокс с вариантами сортировки
	 */
	public JComboBox<String> getJcmbSortOption() {
		return jcmbSortOption;
	}

	/**
	 * Получает флажок.
	 *
	 * @return флажок
	 */
	public JCheckBox getJCheckBoxMarkUnder() {
		return jCheckBoxMarkUnder;
	}

	/**
	 * Получает кнопку "Удалить занятие".
	 *
	 * @return кнопка "Удалить занятие"
	 */
	public JButton getJbtnDeleteLesson() {
		return jbtnDeleteLesson;
	}
}
