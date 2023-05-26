package gui;

import dialogs.AboutDialog;
import dialogs.EmailConfigDialog;
import database.dao.GroupDao;
import database.dao.impl.GroupDaoImpl;
import dialogs.StudentCardDialog;
import entity.Group;
import entity.Lesson;
import entity.Student;
import listeners.StudentTableKeyListener;
import listeners.StudentTableListSelectionListener;
import listeners.StudentTableMouseListener;
import listeners.MainFrameListener;
import utils.Constants;
import utils.EmailSender;
import utils.ExcelTableUtil;

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


public class MainFrame extends JFrame {
	private final JButton jbtnAddStudent, jbtnAddLab,
			btnAddGroup, btnDeleteGroup,btnDeleteLesson,btnSendByEmail;
	protected StudentTable studentTable;
	protected StudentCardDialog studentCardDialog;
	protected final JRadioButton radioBtnLecture, radioBtnLab, radioBtnInc, radioBtnDec;
	private final JComboBox<Group> cmbGroupNumber;
	private final JComboBox<Lesson> currDateCmb;
	private final JComboBox<String> cmbSort;
	private final List<Group> groups;
	private Student currStudent;
	private final JCheckBox checkBox;

	public MainFrame() {
		//Получаем данные о группах из базы данных
		GroupDao groupDao = GroupDaoImpl.getInstance();
		groups = groupDao.findAll();
		//Создаем элементы гланого окна
		JLabel currDateLbl = new JLabel("Текущая дата");
		ButtonGroup buttonGroupLessonType = new ButtonGroup();
		radioBtnLab = new JRadioButton("Лабораторные", true);
		radioBtnLecture = new JRadioButton("Лекции", false);
		buttonGroupLessonType.add(radioBtnLab);
		buttonGroupLessonType.add(radioBtnLecture);
		ButtonGroup buttonGroupSortType = new ButtonGroup();
		radioBtnInc = new JRadioButton("в порядке возрастания", true);
		radioBtnDec = new JRadioButton("в порядке убывания", false);
		buttonGroupSortType.add(radioBtnInc);
		buttonGroupSortType.add(radioBtnDec);
		checkBox = new JCheckBox("отмечать подряд");
		JLabel lblSort = new JLabel("Сортировать по: ");
		cmbSort = new JComboBox<>();
		refreshSortCmb();
		cmbSort.setPreferredSize(new Dimension(100, 30));
		cmbSort.setMaximumSize(cmbSort.getPreferredSize());
		// Создание меню
		JMenu menuFile = new JMenu("Файл");
		JMenuBar menuBar = new JMenuBar();
		// Создание пунктов меню
		JMenuItem menuItemAboutAuthor = new JMenuItem("О авторе");
		JMenuItem menuItemConfigEmail = new JMenuItem("Настроить почту");
		JMenuItem menuItemAboutProgram = new JMenuItem("О программе");
		JMenuItem menuItemExit = new JMenuItem("Выход");
		// Добавление пунктов в меню
		menuFile.add(menuItemAboutAuthor);
		menuFile.add(menuItemAboutProgram);
		menuFile.add(menuItemConfigEmail);
		menuFile.addSeparator();
		menuFile.add(menuItemExit);
		// Добавление меню на фрейм
		menuBar.add(menuFile);
		setJMenuBar(menuBar);
		//Добавляем label и combobox для выбора номера группы
		JLabel lblGroupNumber = new JLabel("Номер группы:");
		cmbGroupNumber = new JComboBox<>();
		refreshGroupCmb();
		cmbGroupNumber.setPreferredSize(new Dimension(100, 30));
		cmbGroupNumber.setMaximumSize(cmbGroupNumber.getPreferredSize());
		// Создаем кнопки
		JButton btnSaveTable = new JButton("Сохранить таблицу в файл");
		jbtnAddStudent = new JButton("Добавить студента");
		btnAddGroup = new JButton("Добавить группу");
		btnDeleteGroup = new JButton("Удалить группу");
		jbtnAddLab = new JButton("Добавить занятие");
		btnDeleteLesson = new JButton("Удалить занятие");
		btnSendByEmail = new JButton("Отправить на почту");
		//Создаем combobox для выбора даты занятия
		currDateCmb = new JComboBox<>();
		refreshDateCmb();
		currDateCmb.setPreferredSize(new Dimension(100, 30));
		currDateCmb.setMaximumSize(currDateCmb.getPreferredSize());
		//Создаем таблицу для отображения списка студентов
		studentTable = new StudentTable(getCurrGroup(), radioBtnLecture.isSelected(), getCurrDateCmb());
		studentTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
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
						.addComponent(cmbGroupNumber)
						.addGroup(groupLayout.createParallelGroup()
								.addComponent(radioBtnLecture)
								.addComponent(radioBtnLab))
						.addComponent(currDateLbl)
						.addComponent(currDateCmb)
						.addComponent(lblSort)
						.addComponent(cmbSort)
						.addGroup(groupLayout.createParallelGroup()
								.addComponent(radioBtnInc)
								.addComponent(radioBtnDec))
						.addComponent(checkBox))
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(scrollPane)
						.addGroup(groupLayout.createParallelGroup()
								.addComponent(jbtnAddStudent)
								.addComponent(btnAddGroup)
								.addComponent(btnDeleteGroup)
								.addComponent(jbtnAddLab)
								.addComponent(btnDeleteLesson)
								.addComponent(btnSaveTable)
								.addComponent(btnSendByEmail))
				)
		);
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup()
				.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup()
								.addComponent(lblGroupNumber)
								.addComponent(cmbGroupNumber)
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(radioBtnLecture)
										.addComponent(radioBtnLab))
								.addComponent(currDateLbl)
								.addComponent(currDateCmb)
								.addComponent(lblSort)
								.addComponent(cmbSort)
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(radioBtnInc)
										.addComponent(radioBtnDec))
								.addComponent(checkBox))
						.addComponent(scrollPane))
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(jbtnAddStudent)
						.addComponent(btnAddGroup)
						.addComponent(btnDeleteGroup)
						.addComponent(jbtnAddLab)
						.addComponent(btnDeleteLesson)
						.addComponent(btnSendByEmail)
						.addComponent(btnSaveTable))
		);
		//Создаем карточку для отображения информации о студенте
		studentCardDialog = new StudentCardDialog(this, "Карточка студента");
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
		btnAddGroup.addActionListener(mainFrameListener);
		btnDeleteGroup.addActionListener(mainFrameListener);
		jbtnAddLab.addActionListener(mainFrameListener);
		studentTable.getSelectionModel().addListSelectionListener(mainFrameListener);
		cmbGroupNumber.addActionListener(mainFrameListener);
		radioBtnDec.addActionListener(mainFrameListener);
		radioBtnLecture.addActionListener(mainFrameListener);
		radioBtnLab.addActionListener(mainFrameListener);
		radioBtnInc.addActionListener(mainFrameListener);
		btnDeleteLesson.addActionListener(mainFrameListener);
		cmbSort.addActionListener(mainFrameListener);
		currDateCmb.addActionListener(mainFrameListener);
		studentTable.getSelectionModel().addListSelectionListener(studentTableListSelectionListener);
		studentTable.addMouseListener(studentTableMouseListener);
		studentTable.addKeyListener(studentTableKeyListener);
		menuItemConfigEmail.addActionListener(e -> {
			new EmailConfigDialog(null).setVisible(true);
		});
		btnSendByEmail.addActionListener(e -> {
			String input = JOptionPane.showInputDialog(null, "Введите получателей через запятую:");
			if(input!=null) {
				String body = getCurrentGroup().getName() + (radioBtnLecture.isSelected() ? " Лекции" : " Лабораторные");
				String filePath = body + ".xlsx";
				EmailSender.sendEmail(input, "Отчет посещаемости до "+ new SimpleDateFormat("dd.MM.yyyy").format(new Date())+" "+getCurrGroup().getName(),
						body,
						ExcelTableUtil.createAttendanceTable(studentTable.getStudentTableModel()), filePath);
			}
		});
		menuItemAboutAuthor.addActionListener(e -> new AboutDialog(null));
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
					fileName= fileName+".xlsx";
				}else {
					fileName = (radioBtnLab.isSelected() ? "Лабораторные " : "Лекции ") + getCurrentGroup() + ".xlsx";
				}
				File file = new File(selectedDir, fileName);
				System.out.println(file.getName());
				byte[] information = ExcelTableUtil.createAttendanceTable(getStudentTable().getStudentTableModel());
				try {
					FileOutputStream fos = new FileOutputStream(file);
					fos.write(information);
					fos.close();
					JOptionPane.showMessageDialog(null,"Файл успешно создан: " + file.getAbsolutePath());
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(null,"Файл не создан!");
					ex.printStackTrace();
				}
			}
		});

		setTitle("Журнал посещаемости");

		setSize(new Dimension(1920, 1080));

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setLocationRelativeTo(null);

		setExtendedState(MAXIMIZED_BOTH);

		setVisible(true);

	}

	public JComboBox<Group> getCmbGroupNumber() {
		return cmbGroupNumber;
	}

	public StudentTable getStudentTable() {
		return studentTable;
	}

	public JButton getJbtnAddStudent() {
		return jbtnAddStudent;
	}

	public Student getCurrentStudent() {
		int selectedRow = studentTable.getSelectedRow();
		if (selectedRow != -1) {
			return studentTable.getStudentAt(selectedRow);
		}
		return null;
	}

	public Group getCurrentGroup() {
		return (Group) cmbGroupNumber.getSelectedItem();
	}

	public JButton getBtnAddGroup() {
		return btnAddGroup;
	}

	public JButton getBtnDeleteGroup() {
		return btnDeleteGroup;
	}


	public JButton getBtnAddLesson() {
		return jbtnAddLab;
	}

	public JComboBox<Lesson> getCurrDateCmb() {
		return currDateCmb;
	}

	public Lesson getCurrDate() {
		return (Lesson) currDateCmb.getSelectedItem();
	}

	public Group getCurrGroup() {
		return (Group) cmbGroupNumber.getSelectedItem();
	}

	public JRadioButton getRadioBtnLecture() {
		return radioBtnLecture;
	}

	public JRadioButton getRadioBtnLab() {
		return radioBtnLab;
	}

	public void refreshStudentTable() {
		currStudent = getCurrentStudent();
		studentTable.setModel(new StudentTableModel(getCurrGroup(), radioBtnLecture.isSelected()));
		int selectedColumn = currDateCmb.getSelectedIndex() + (radioBtnLecture.isSelected()?2:3);
		if (selectedColumn < studentTable.getColumnCount()) {
			studentTable.setColumnSelectionInterval(
					selectedColumn,
					selectedColumn);
		}
		sortTable();
		if (currStudent != null) {
			int index = studentTable.getStudentTableModel().getRowIndex(currStudent);
			if (index != -1) {
				studentTable.setRowSelectionInterval(index, index);
				studentTable.revalidate();
				studentTable.repaint();
			} else {
				Student student = studentTable.getStudentAt(0);
				if (student != null) {
					studentTable.setRowSelectionInterval(0, 0);
					currStudent = student;
					studentCardDialog.setVisible(false);
				}
			}
		} else {
			Student student = studentTable.getStudentAt(0);
			if (student != null) {
				studentTable.setRowSelectionInterval(0, 0);
				currStudent = student;
			}
		}

		studentTable.getColumnModel().getColumn(0).setPreferredWidth(300);
		studentTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		if (!radioBtnLecture.isSelected()) {
			studentTable.getColumnModel().getColumn(2).setPreferredWidth(100);
		}
		studentTable.setDefaultRenderer(Object.class,new StudentTableCellRender(selectedColumn));
	}

	public void sortTable() {
		boolean isInc = radioBtnInc.isSelected();
		String option = (String) cmbSort.getSelectedItem();
		switch (Objects.requireNonNull(option)) {
			case Constants.SORT_BY_ALPHABET -> studentTable.getStudentTableModel().sortByAlphabet(isInc);
			case Constants.SORT_BY_GRADE -> studentTable.getStudentTableModel().sortByGrade(isInc);
			case Constants.SORT_BY_ATTENDANCE ->
					studentTable.getStudentTableModel().sortByAttendance(radioBtnLecture.isSelected(), isInc);
		}
	}

	public void refreshGroupCmb() {
		cmbGroupNumber.setModel(new DefaultComboBoxModel<>(groups.toArray(new Group[0])));
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void refreshSortCmb() {
		String option = (String) cmbSort.getSelectedItem();
		List<String> sortOptions = new ArrayList<>();
		sortOptions.add(Constants.SORT_BY_ALPHABET);
		sortOptions.add(Constants.SORT_BY_ATTENDANCE);
		if (radioBtnLab.isSelected()) {
			sortOptions.add(Constants.SORT_BY_GRADE);
		}
		cmbSort.setModel(new DefaultComboBoxModel<>(sortOptions.toArray(new String[0])));
		if (option != null) {
			cmbSort.setSelectedItem(option);
		}
	}

	public void refreshDateCmb() {
		Lesson lesson = (Lesson) currDateCmb.getSelectedItem();
		List<Lesson> currLessons;
		if (radioBtnLecture.isSelected()) {
			currLessons = getCurrGroup().getLectures();
		} else {
			currLessons = getCurrGroup().getLabs();
		}
		currDateCmb.setModel(new DefaultComboBoxModel<>(currLessons.toArray(new Lesson[0])));
		if (lesson != null) {
			currDateCmb.setSelectedItem(lesson);
		}
	}

	public StudentCardDialog getJDialogStudentCard() {
		return studentCardDialog;
	}

	public Student getCurrStudent() {
		return currStudent;
	}

	public JRadioButton getRadioBtnInc() {
		return radioBtnInc;
	}

	public JRadioButton getRadioBtnDec() {
		return radioBtnDec;
	}

	public void setCurrStudent(Student currStudent) {
		this.currStudent = currStudent;
	}

	public JComboBox<String> getCmbSort() {
		return cmbSort;
	}

	public JCheckBox getCheckBox() {
		return checkBox;
	}

	public JButton getBtnDeleteLesson() {
		return btnDeleteLesson;
	}
}
