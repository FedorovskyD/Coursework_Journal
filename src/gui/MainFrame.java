package gui;

import gui.studentTable.StudentTableModel;
import gui.studentTable.StudentTable;
import database.dao.GroupDao;
import database.dao.impl.GroupDaoImpl;
import gui.studentTable.studentCard.StudentCardDialog;
import entity.Group;
import entity.Lesson;
import entity.Student;
import gui.studentTable.studentTableListener.StudentTableKeyListener;
import gui.studentTable.studentTableListener.StudentTableListSelectionListener;
import gui.studentTable.studentTableListener.StudentTableMouseListener;
import listeners.MainFrameListener;
import utils.Constants;
import utils.ExcelTableExample;
import utils.WordConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainFrame extends JFrame {
	private final JButton btnAddStudent, btnAddLab,
			btnAddGroup, btnDeleteGroup,btnDeleteLesson;
	protected StudentTable studentTable;
	protected StudentCardDialog studentCardDialog;
	protected final JRadioButton radioBtnLecture, radioBtnLab, radioBtnInc, radioBtnDec;
	private final JComboBox<Group> cmbGroupNumber;
	private final JComboBox<Lesson> currDateCmb;
	private final JComboBox<String> cmbSort;
	private final List<Group> groups;
	private Student currStudent;
	private final JCheckBox checkBox;
	private final JButton btnSaveTable;

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
		JMenuItem menuItemAboutProgram = new JMenuItem("О программе");
		JMenuItem menuItemExit = new JMenuItem("Выход");
		// Добавление пунктов в меню
		menuFile.add(menuItemAboutAuthor);
		menuFile.add(menuItemAboutProgram);
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
		btnSaveTable = new JButton("Сохранить таблицу в файл");
		btnAddStudent = new JButton("Добавить студента");
		btnAddGroup = new JButton("Добавить группу");
		btnDeleteGroup = new JButton("Удалить группу");
		btnAddLab = new JButton("Добавить занятие");
		btnDeleteLesson = new JButton("Удалить занятие");
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
								.addComponent(btnAddStudent)
								.addComponent(btnAddGroup)
								.addComponent(btnDeleteGroup)
								.addComponent(btnAddLab)
								.addComponent(btnDeleteLesson)
								.addComponent(btnSaveTable))
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
						.addComponent(btnAddStudent)
						.addComponent(btnAddGroup)
						.addComponent(btnDeleteGroup)
						.addComponent(btnAddLab)
						.addComponent(btnDeleteLesson)
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
		btnAddStudent.addActionListener(mainFrameListener);
		btnAddGroup.addActionListener(mainFrameListener);
		btnDeleteGroup.addActionListener(mainFrameListener);
		btnAddLab.addActionListener(mainFrameListener);
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
		btnSaveTable.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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
					ExcelTableExample.createAttendanceTable(getStudentTable().getStudentTableModel(),file);
					//WordConnector.generateTable(getStudentTable().getStudentTableModel(), file);
				}
			}
		});

		setTitle("Student journal");

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

	public JButton getBtnAddStudent() {
		return btnAddStudent;
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
		return btnAddLab;
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
		int selectedColumn = currDateCmb.getSelectedIndex() + (radioBtnLecture.isSelected()?1:2);
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
		if (!radioBtnLecture.isSelected()) {
			studentTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		}
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
