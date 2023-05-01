package MainFrame;

import database.dao.impl.GroupDaoImpl;
import database.dao.impl.LabDaoImpl;
import dialogs.*;
import entity.Group;
import entity.Lab;
import entity.Student;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Objects;

public class MainWindow extends JFrame {
	private static MainWindow mainWindow;
	private JMenu menuFile;
	private JLabel jlblGroupNumber;
	private JComboBox<Group> cmbGroupNumber;
	private JButton btnAddStudent, btnAddGroup, btnDeleteGroup,
			btnAboutAuthor, btnDeleteStudent, btnAddPhoto, btnAddLab;
	private JTable studentTable;
	private StudentCardDialog studentCardDialog;
	private JRadioButton lectureBtn;
	private JRadioButton labBtn;
	private JLabel lectureLbl;
	private JLabel labLbl;
	private JLabel currDateLbl;
	private final JComboBox<Lab> currDateCmb;
	private final List<Group> groups;

	private MainWindow() {
		//Создаем панель с информацией о студенте
		groups = GroupDaoImpl.getInstance().findAll();
		labLbl = new JLabel("Лабораторные");
		lectureLbl = new JLabel("Лекции");
		currDateLbl = new JLabel("Текущая дата");

		labBtn = new JRadioButton();
		lectureBtn = new JRadioButton();
		lectureBtn.setSelected(true);
		// Создание меню
		menuFile = new JMenu("Файл");
		JMenuBar menuBar = new JMenuBar();
		// Создание пунктов меню
		JMenuItem aboutAuthorItem = new JMenuItem("О авторе");
		JMenuItem aboutProgramItem = new JMenuItem("О программе");
		JMenuItem exitItem = new JMenuItem("Выход");
		// Добавление пунктов в меню
		menuFile.add(aboutAuthorItem);
		menuFile.add(aboutProgramItem);
		menuFile.addSeparator();
		menuFile.add(exitItem);
		// Добавление меню на фрейм
		menuBar.add(menuFile);
		setJMenuBar(menuBar);
		//Добавляем label и combobox для выбора номера группы
		jlblGroupNumber = new JLabel("Номер группы:");
		cmbGroupNumber = new JComboBox<>(new DefaultComboBoxModel<>(groups.toArray(new Group[0])));
		cmbGroupNumber.setPreferredSize(new Dimension(100, 30));
		cmbGroupNumber.setMaximumSize(cmbGroupNumber.getPreferredSize());
		cmbGroupNumber.setMinimumSize(cmbGroupNumber.getPreferredSize());
		// Создаем кнопки
		btnAddStudent = new JButton("Добавить студента");
		btnAddGroup = new JButton("Добавить группу");
		btnDeleteGroup = new JButton("Удалить группу");
		btnAboutAuthor = new JButton("Об авторе");
		btnDeleteStudent = new JButton("Удалить студента");
		btnAddPhoto = new JButton("Добавить фото студента");
		btnAddLab = new JButton("Добавить лабораторное занятие");
		MainWindow mainWindow = this;

		currDateCmb = new JComboBox<>(new DefaultComboBoxModel<>(LabDaoImpl.getInstance().getAllLabByGroupId(getCurrentGroup().getId()).toArray(new Lab[0])));
		currDateCmb.setPreferredSize(new Dimension(200, 30));
		currDateCmb.setMinimumSize(currDateCmb.getPreferredSize());
		currDateCmb.setMaximumSize(currDateCmb.getPreferredSize());
		currDateCmb.setEditable(false);
		studentCardDialog = new StudentCardDialog(mainWindow, "Карточка студента");
		studentTable = new JTable(new StudentTableModel());
		StudentTableModel studentTableModel = (StudentTableModel) studentTable.getModel();
		studentTableModel.setData(((Group) Objects.requireNonNull(cmbGroupNumber.getSelectedItem())).getStudents());

		JScrollPane scrollPane = new JScrollPane(studentTable);
		studentTable.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP ||
						e.getKeyCode() == KeyEvent.VK_DOWN) {
					e.consume();
				}
			}
		});
		TableColumn column = studentTable.getColumnModel().getColumn(5);
		column.setMinWidth(0);
		column.setMaxWidth(0);
		column.setWidth(0);
		column.setPreferredWidth(0);
		studentTable.setDefaultRenderer(Object.class, new StudentTableCellRender());
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		getContentPane().setLayout(groupLayout);
		groupLayout.setAutoCreateGaps(true);
		groupLayout.setAutoCreateContainerGaps(true);
		// Компановка главной панели
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup()
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(jlblGroupNumber)
						.addComponent(cmbGroupNumber)
						.addGroup(groupLayout.createParallelGroup()
								.addComponent(lectureLbl)
								.addComponent(labLbl))
						.addGroup(groupLayout.createParallelGroup()
								.addComponent(lectureBtn)
								.addComponent(labBtn))
						.addComponent(currDateLbl)
						.addComponent(currDateCmb))
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(scrollPane)
						.addGroup(groupLayout.createParallelGroup()
								.addComponent(btnAddStudent)
								.addComponent(btnAddGroup)
								.addComponent(btnDeleteGroup)
								.addComponent(btnDeleteStudent)
								.addComponent(btnAboutAuthor)
								.addComponent(btnAddPhoto)
								.addComponent(btnAddLab))
				)
		);
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup()
				.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup()
								.addComponent(jlblGroupNumber)
								.addComponent(cmbGroupNumber)
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(lectureLbl)
										.addComponent(labLbl))
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(lectureBtn)
										.addComponent(labBtn))
								.addComponent(currDateLbl)
								.addComponent(currDateCmb))
						.addComponent(scrollPane))
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(btnAddStudent)
						.addComponent(btnAddGroup)
						.addComponent(btnDeleteGroup)
						.addComponent(btnDeleteStudent)
						.addComponent(btnAboutAuthor)
						.addComponent(btnAddPhoto)
						.addComponent(btnAddLab))
		);
		// Добавление слушателей
		MainWindowistener mainWindowistener = new MainWindowistener(this);
		btnAddStudent.addActionListener(mainWindowistener);
		btnAddGroup.addActionListener(mainWindowistener);
		btnDeleteGroup.addActionListener(mainWindowistener);
		btnDeleteStudent.addActionListener(mainWindowistener);
		btnAboutAuthor.addActionListener(mainWindowistener);
		btnAddPhoto.addActionListener(mainWindowistener);
		btnAddLab.addActionListener(mainWindowistener);
		studentTable.getSelectionModel().addListSelectionListener(mainWindowistener);
		cmbGroupNumber.addActionListener(mainWindowistener);
	}

	public JComboBox<Group> getCmbGroupNumber() {
		return cmbGroupNumber;
	}

	public JTable getStudentTable() {
		return studentTable;
	}

	public JButton getBtnAddStudent() {
		return btnAddStudent;
	}

	public Student getCurrentStudent() {
		return ((StudentTableModel) studentTable.getModel()).getStudentAt(studentTable.getSelectedRow());
	}

	public Group getCurrentGroup() {
		return (Group) cmbGroupNumber.getSelectedItem();
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

	public JButton getBtnAddGroup() {
		return btnAddGroup;
	}

	public JButton getBtnDeleteGroup() {
		return btnDeleteGroup;
	}

	public JButton getBtnAboutAuthor() {
		return btnAboutAuthor;
	}

	public JButton getBtnDeleteStudent() {
		return btnDeleteStudent;
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
}
