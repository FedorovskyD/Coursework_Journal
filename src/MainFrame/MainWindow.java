package MainFrame;

import MainFrame.studentTable.StudentLabTableModel;
import MainFrame.studentTable.StudentTable;
import database.dao.GroupDao;
import database.dao.impl.GroupDaoImpl;
import MainFrame.studentCard.JDialogStudentCard;
import entity.Group;
import entity.Lesson;
import entity.Student;
import utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainWindow extends JFrame {
	private final JButton btnAddStudent, btnAddLab,
			btnAddGroup, btnDeleteGroup;
	protected StudentTable studentTable;
	protected JDialogStudentCard jDialogStudentCard;
	protected final JRadioButton radioBtnLecture, radioBtnLab, radioBtnInc, radioBtnDec;
	private final JComboBox<Group> cmbGroupNumber;
	private final JComboBox<Lesson> currDateCmb;
	private final JComboBox<String> cmbSort;
	private final List<Group> groups;
	protected Student currStudent;

	private MainWindow() {
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
		radioBtnDec = new JRadioButton("в поряке убывания", false);
		buttonGroupSortType.add(radioBtnInc);
		buttonGroupSortType.add(radioBtnDec);
		JLabel lblSort = new JLabel("Сортировать по: ");
		cmbSort = new JComboBox<>();
		updateSortCmb();
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
		updateGroupCmb();
		cmbGroupNumber.setPreferredSize(new Dimension(100, 30));
		cmbGroupNumber.setMaximumSize(cmbGroupNumber.getPreferredSize());
		// Создаем кнопки
		btnAddStudent = new JButton("Добавить студента");
		btnAddGroup = new JButton("Добавить группу");
		btnDeleteGroup = new JButton("Удалить группу");
		btnAddLab = new JButton("Добавить занятие");
		//Создаем combobox для выбора даты занятия
		currDateCmb = new JComboBox<>();
		updateDateCmb();
		currDateCmb.setPreferredSize(new Dimension(100, 30));
		currDateCmb.setMaximumSize(currDateCmb.getPreferredSize());
		//Создаем таблицу для отображения списка студентов
		studentTable = new StudentTable(getCurrGroup(), radioBtnLecture.isSelected());
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
								.addComponent(radioBtnDec)))
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(scrollPane)
						.addGroup(groupLayout.createParallelGroup()
								.addComponent(btnAddStudent)
								.addComponent(btnAddGroup)
								.addComponent(btnDeleteGroup)
								.addComponent(btnAddLab))
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
										.addComponent(radioBtnDec)))
						.addComponent(scrollPane))
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(btnAddStudent)
						.addComponent(btnAddGroup)
						.addComponent(btnDeleteGroup)
						.addComponent(btnAddLab))
		);
		//Создаем карточку для отображения информации о студенте
		jDialogStudentCard = new JDialogStudentCard(this, "Карточка студента");
		// Добавление слушателей
		MainWindowListener mainWindowListener = new MainWindowListener(this);
		btnAddStudent.addActionListener(mainWindowListener);
		btnAddGroup.addActionListener(mainWindowListener);
		btnDeleteGroup.addActionListener(mainWindowListener);
		btnAddLab.addActionListener(mainWindowListener);
		studentTable.getSelectionModel().addListSelectionListener(mainWindowListener);
		cmbGroupNumber.addActionListener(mainWindowListener);
		radioBtnDec.addActionListener(e -> {
			refreshStudentTable();
		});
		radioBtnLecture.addActionListener(e -> {
			jDialogStudentCard.getScrollPane().setBorder(BorderFactory.createTitledBorder("Лекции"));
			jDialogStudentCard.getTxtAverageGrade().setText(" ");
			jDialogStudentCard.getGradePanel().setVisible(radioBtnLab.isSelected());
			jDialogStudentCard.getLabButtons().clear();
			jDialogStudentCard.updateStudentCard(currStudent);
			if (jDialogStudentCard.isVisible()) {
				jDialogStudentCard.getCurrLessonButton().requestFocus();
			}
			updateDateCmb();
			updateSortCmb();
			refreshStudentTable();
		});
		radioBtnLab.addActionListener(e -> {
			jDialogStudentCard.getScrollPane().setBorder(BorderFactory.createTitledBorder("Лабораторные"));
			jDialogStudentCard.getGpaLabel().setText("Средний балл: ");
			jDialogStudentCard.getGradePanel().setVisible(!radioBtnLecture.isSelected());
			jDialogStudentCard.getLabButtons().clear();
			jDialogStudentCard.updateStudentCard(currStudent);
			if (jDialogStudentCard.isVisible()) {
				jDialogStudentCard.getCurrLessonButton().requestFocus();
			}
			updateDateCmb();
			updateSortCmb();
			refreshStudentTable();
		});
		radioBtnInc.addActionListener(e -> {
			refreshStudentTable();
		});
		cmbSort.addActionListener(e -> refreshStudentTable());
		studentTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = studentTable.rowAtPoint(e.getPoint());
				if (row >= 0) {
					Object value = studentTable.getValueAt(row, 0);
					// обработка щелчка на ячейке
					if (value instanceof Student student) {
						if (student == currStudent) {
							jDialogStudentCard.updateStudentCard(student);
							jDialogStudentCard.setVisible(true);
						}
					}
				}
			}
		});
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


	public JButton getBtnAddLab() {
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

	public JDialogStudentCard getStudentCardDialog() {
		return jDialogStudentCard;
	}

	public void refreshStudentTable() {
		currStudent = getCurrentStudent();
		studentTable.setModel(new StudentLabTableModel(getCurrGroup(), radioBtnLecture.isSelected()));
		sortTable();
		if (currStudent != null) {
			int index = studentTable.getStudentTableModel().getRowIndex(currStudent);
			if (index != -1) {
				studentTable.setRowSelectionInterval(index, index);
				studentTable.revalidate();
				studentTable.repaint();
			}
		} else {
			Student student = studentTable.getStudentAt(0);
			if (student != null) {
				currStudent = student;
				studentTable.setRowSelectionInterval(0, 0);
			}
		}
		studentTable.getColumnModel().getColumn(0).setPreferredWidth(300);
	}

	public void sortTable() {
		boolean isInc = radioBtnInc.isSelected();
		String option = (String) cmbSort.getSelectedItem();
		switch (Objects.requireNonNull(option)) {
			case Constants.SORT_BY_ALPHABET -> studentTable.getStudentTableModel().sortByAlphabet(isInc);
			case Constants.SORT_BY_GRADE -> studentTable.getStudentTableModel().sortByGrade(isInc);
			case Constants.SORT_BY_ATTENDANCE -> studentTable.getStudentTableModel().sortByAttendance(true,isInc);
		}
	}

	public void updateGroupCmb() {
		cmbGroupNumber.setModel(new DefaultComboBoxModel<>(groups.toArray(new Group[0])));
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void updateSortCmb() {
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

	public void updateDateCmb() {
		List<Lesson> currLessons;
		if (radioBtnLecture.isSelected()) {
			currLessons = getCurrGroup().getLectures();
		} else {
			currLessons = getCurrGroup().getLabs();
		}
		currDateCmb.setModel(new DefaultComboBoxModel<>(currLessons.toArray(new Lesson[0])));
	}

	public static void main(String[] args) {
		JFrame mainFrame = new MainWindow();
		mainFrame.setTitle("Student journal");
		mainFrame.setSize(new Dimension(1920, 1080));
		mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setExtendedState(MAXIMIZED_BOTH);
		mainFrame.setVisible(true);
	}
}
