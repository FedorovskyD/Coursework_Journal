package MainFrame;

import MainFrame.studentTable.StudentTable;
import MainFrame.studentTable.StudentTableModel;
import database.dao.impl.GroupDaoImpl;
import dialogs.StudentCardDialog;
import entity.Group;
import entity.Lab;
import entity.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Objects;

public class MainWindow extends JFrame {
	private final JButton btnAddStudent, btnAddLab,
			btnAddGroup, btnDeleteGroup, btnAboutAuthor, btnAddPhoto;
	private final StudentTable studentTable;
	private final StudentCardDialog studentCardDialog;
	private final JRadioButton radioBtnLecture, radioBtnLab, radioBtnInc,radioBtnDec;
	private final JComboBox<Group> cmbGroupNumber;
	private final JComboBox<Lab> currDateCmb;
	private final JComboBox<String> cmbSort;
	private final List<Group> groups;

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
		cmbSort = new JComboBox<>(new DefaultComboBoxModel<>(new String[]{"алфавиту", "среднему баллу","посещаемости"}));
		cmbSort.setPreferredSize(new Dimension(100,30));
		cmbSort.setMaximumSize(cmbSort.getPreferredSize());

		radioBtnLecture.setSelected(true);
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
		btnAddPhoto = new JButton("Добавить фото студента");
		btnAddLab = new JButton("Добавить лабораторное занятие");
		//Создаем combobox для выбора даты занятия
		currDateCmb = new JComboBox<>(new DefaultComboBoxModel<>(getCurrentGroup().getLabs().toArray(new Lab[0])));
		currDateCmb.setPreferredSize(new Dimension(100, 30));
		currDateCmb.setMinimumSize(currDateCmb.getPreferredSize());
		currDateCmb.setMaximumSize(currDateCmb.getPreferredSize());
		//Создаем карточку для отображения информации о студенте
		studentCardDialog = new StudentCardDialog(this, "Карточка студента");
		//Создаем таблицу для отображения списка студентов
		studentTable = new StudentTable();
		studentTable.setData(((Group) Objects.requireNonNull(cmbGroupNumber.getSelectedItem())).getStudents());
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
								.addComponent(btnAddPhoto)
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
						.addComponent(btnAddPhoto)
						.addComponent(btnAddLab))
		);
		// Добавление слушателей
		MainWindowListener mainWindowListener = new MainWindowListener(this);
		btnAddStudent.addActionListener(mainWindowListener);
		btnAddGroup.addActionListener(mainWindowListener);
		btnDeleteGroup.addActionListener(mainWindowListener);
		btnAboutAuthor.addActionListener(mainWindowListener);
		btnAddPhoto.addActionListener(mainWindowListener);
		btnAddLab.addActionListener(mainWindowListener);
		studentTable.getSelectionModel().addListSelectionListener(mainWindowListener);
		cmbGroupNumber.addActionListener(mainWindowListener);
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
		return ((StudentTableModel) studentTable.getModel()).getStudentAt(studentTable.getSelectedRow());
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

	public JButton getBtnAddPhoto() {
		return btnAddPhoto;
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

	public StudentCardDialog getStudentCardDialog() {
		return studentCardDialog;
	}

	public void updateStudentTable() {
		StudentTableModel studentTableModel = (StudentTableModel) studentTable.getModel();
		studentTableModel.setData(getCurrentGroup().getStudents());
	}

	public void updateGroupCmb() {
		cmbGroupNumber.setModel(new DefaultComboBoxModel<>(groups.toArray(new Group[0])));
	}

	public List<Group> getGroups() {
		return groups;
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
