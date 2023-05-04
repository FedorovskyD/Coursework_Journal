package MainFrame;

import MainFrame.studentTable.StudentLabTableModel;
import MainFrame.studentTable.StudentTable;
import database.dao.impl.GroupDaoImpl;
import dialogs.studentCard.JDialogStudentCard;
import entity.Group;
import entity.Lab;
import entity.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;


public class MainWindow extends JFrame {
	private final JButton btnAddStudent, btnAddLab,
			btnAddGroup, btnDeleteGroup, btnAboutAuthor;
	protected StudentTable studentTable;
	protected JDialogStudentCard jDialogStudentCard;
	private final JRadioButton radioBtnLecture, radioBtnLab, radioBtnInc, radioBtnDec;
	private final JComboBox<Group> cmbGroupNumber;
	private final JComboBox<Lab> currDateCmb;
	private final JComboBox<String> cmbSort;
	private final List<Group> groups;
	private final MainWindowListener mainWindowListener;
	protected final JScrollPane scrollPane;

	private MainWindow() {
		//Получаем данные о группах из базы данных
		groups = GroupDaoImpl.getInstance().findAll();
		//Создаем элементы гланого окна
		JLabel lblLab = new JLabel("Лабораторные");
		JLabel lblLecture = new JLabel("Лекции");
		JLabel currDateLbl = new JLabel("Текущая дата");
		radioBtnLab = new JRadioButton();
		radioBtnLecture = new JRadioButton();
		radioBtnInc = new JRadioButton("в порядке возрастания");
		radioBtnDec = new JRadioButton("в поряке убывания");
		JLabel lblSort = new JLabel("Сортировать по: ");
		cmbSort = new JComboBox<>(new DefaultComboBoxModel<>(new String[]{"алфавиту", "среднему баллу", "посещаемости"}));
		cmbSort.setPreferredSize(new Dimension(100, 30));
		cmbSort.setMaximumSize(cmbSort.getPreferredSize());
		radioBtnLab.setSelected(true);
		radioBtnLab.addActionListener(e -> radioBtnLecture.setSelected(false));
		radioBtnInc.setSelected(true);
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
		cmbGroupNumber = new JComboBox<>(new DefaultComboBoxModel<>(groups.toArray(new Group[0])));
		cmbGroupNumber.setPreferredSize(new Dimension(100, 30));
		cmbGroupNumber.setMaximumSize(cmbGroupNumber.getPreferredSize());
		cmbGroupNumber.setMinimumSize(cmbGroupNumber.getPreferredSize());
		// Создаем кнопки
		btnAddStudent = new JButton("Добавить студента");
		btnAddGroup = new JButton("Добавить группу");
		btnDeleteGroup = new JButton("Удалить группу");
		btnAboutAuthor = new JButton("Об авторе");
		btnAddLab = new JButton("Добавить лабораторное занятие");
		//Создаем combobox для выбора даты занятия
		currDateCmb = new JComboBox<>(new DefaultComboBoxModel<>(getCurrentGroup().getLabs().toArray(new Lab[0])));
		currDateCmb.setPreferredSize(new Dimension(100, 30));
		currDateCmb.setMinimumSize(currDateCmb.getPreferredSize());
		currDateCmb.setMaximumSize(currDateCmb.getPreferredSize());
		//Создаем таблицу для отображения списка студентов
		studentTable = new StudentTable((Group) cmbGroupNumber.getSelectedItem());
		updateStudentTable();
		sortTable();
		scrollPane = new JScrollPane(studentTable);
		studentTable.setPreferredScrollableViewportSize(new Dimension(800, 200));
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
								.addComponent(lblLecture)
								.addComponent(lblLab))
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
								.addComponent(btnAboutAuthor)
								.addComponent(btnAddLab))
				)
		);
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup()
				.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup()
								.addComponent(lblGroupNumber)
								.addComponent(cmbGroupNumber)
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(lblLecture)
										.addComponent(lblLab))
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
						.addComponent(btnAboutAuthor)
						.addComponent(btnAddLab))
		);
		//Создаем карточку для отображения информации о студенте
		jDialogStudentCard = new JDialogStudentCard(this, "Карточка студента");
		// Добавление слушателей
		mainWindowListener = new MainWindowListener(this);
		btnAddStudent.addActionListener(mainWindowListener);
		btnAddGroup.addActionListener(mainWindowListener);
		btnDeleteGroup.addActionListener(mainWindowListener);
		btnAboutAuthor.addActionListener(mainWindowListener);
		btnAddLab.addActionListener(mainWindowListener);
		studentTable.getSelectionModel().addListSelectionListener(mainWindowListener);
		cmbGroupNumber.addActionListener(mainWindowListener);
		radioBtnInc.addActionListener(e -> {
			radioBtnDec.setSelected(false);
			sortTable();
		});
		radioBtnDec.addActionListener(e -> {
			radioBtnInc.setSelected(false);
			sortTable();
		});
		radioBtnLecture.addActionListener(e -> radioBtnLab.setSelected(false));
		cmbSort.addActionListener(e -> sortTable());
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
		if (selectedRow != -1)
			return studentTable.getStudentAt(studentTable.getSelectedRow());
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

	public JButton getBtnAboutAuthor() {
		return btnAboutAuthor;
	}

	public JButton getBtnAddLab() {
		return btnAddLab;
	}

	public JComboBox<Lab> getCurrDateCmb() {
		return currDateCmb;
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

	public void updateStudentTable() {
		int selectedRow = studentTable.getSelectedRow();
		Student currStudent = null;
		if(selectedRow!=-1) {
			currStudent = studentTable.getStudentAt(selectedRow);
		}
		studentTable.setModel(new StudentLabTableModel((Group) cmbGroupNumber.getSelectedItem()));
		sortTable();
		if(currStudent!=null){
			int index = studentTable.getStudentTableModel().getRowIndex(currStudent);
			if(index!=-1) {
				studentTable.setRowSelectionInterval(index,index);
			}
		}
		studentTable.repaint();
	}

	public void sortTable() {
		boolean isInc = radioBtnInc.isSelected();
		String option = (String) cmbSort.getSelectedItem();
		switch (Objects.requireNonNull(option)) {
			case "алфавиту" -> studentTable.getStudentTableModel().sortByAlphabet(isInc);
			case "среднему баллу" -> studentTable.getStudentTableModel().sortByGrade(isInc);
			case "посещаемости" -> studentTable.getStudentTableModel().sortByAttendance(isInc);
		}
	}

	public void updateGroupCmb() {
		cmbGroupNumber.setModel(new DefaultComboBoxModel<>(groups.toArray(new Group[0])));
	}

	public void updateCurrDateCmb() {
		Group currGroup = (Group) cmbGroupNumber.getSelectedItem();
		currDateCmb.setModel(new DefaultComboBoxModel<>(currGroup.getLabs().toArray(new Lab[0])));
	}

	public List<Group> getGroups() {
		return groups;
	}

	public MainWindowListener getMainWindowListener() {
		return mainWindowListener;
	}

	public JComboBox<String> getCmbSort() {
		return cmbSort;
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
