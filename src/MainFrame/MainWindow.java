package MainFrame;

import connection.MySQLConnector;
import dialogs.*;
import entity.Group;
import entity.Student;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;

public class MainWindow extends JFrame {
	private static MainWindow mainWindow;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JLabel groupNumberLbl;
	private JComboBox<Group> groupNumberCmb;
	private JButton addStudentBtn, addGroupBtn, deleteGroupBtn,
			aboutAuthorBtn, deleteStudentBtn, addPhotoBtn, addLabBtn;
	private JTable studentTable;
	private StudentCardDialog studentCardDialog;
	private JRadioButton lectureBtn;
	private JRadioButton labBtn;
	private JLabel lectureLbl;
	private JLabel labLbl;
	private JLabel currDateLbl;
	private JComboBox<String> currDateCmb;
	private List<Group> groups;

	private MainWindow() {
		//Создаем панель с информацией о студенте
		JOptionPane optionPane = new JOptionPane(new StudentCardPanel(new Student()), JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
		JDialog dialog = optionPane.createDialog("Student Info");
		groups = MySQLConnector.getAllGroups();
		labLbl = new JLabel("Лабораторные");
		lectureLbl = new JLabel("Лекции");
		currDateLbl = new JLabel("Текущая дата");
		currDateCmb = new JComboBox<>();
		currDateCmb.setPreferredSize(new Dimension(200, 30));
		currDateCmb.setMinimumSize(currDateCmb.getPreferredSize());
		currDateCmb.setMaximumSize(currDateCmb.getPreferredSize());
		labBtn = new JRadioButton();
		lectureBtn = new JRadioButton();
		lectureBtn.setSelected(true);
		// Создание меню
		fileMenu = new JMenu("Файл");
		menuBar = new JMenuBar();
		// Создание пунктов меню
		JMenuItem aboutAuthorItem = new JMenuItem("О авторе");
		JMenuItem aboutProgramItem = new JMenuItem("О программе");
		JMenuItem exitItem = new JMenuItem("Выход");
		// Добавление пунктов в меню
		fileMenu.add(aboutAuthorItem);
		fileMenu.add(aboutProgramItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		// Добавление меню на фрейм
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);
		//Добавляем label и combobox для выбора номера группы
		groupNumberLbl = new JLabel("Номер группы:");
		groupNumberCmb = new JComboBox<>(new DefaultComboBoxModel<>(groups.toArray(new Group[0])));
		groupNumberCmb.setPreferredSize(new Dimension(100, 30));
		groupNumberCmb.setMaximumSize(groupNumberCmb.getPreferredSize());
		groupNumberCmb.setMinimumSize(groupNumberCmb.getPreferredSize());
		// Создаем кнопки
		addStudentBtn = new JButton("Добавить студента");
		addGroupBtn = new JButton("Добавить группу");
		deleteGroupBtn = new JButton("Удалить группу");
		aboutAuthorBtn = new JButton("Об авторе");
		deleteStudentBtn = new JButton("Удалить студента");
		addPhotoBtn = new JButton("Добавить фото студента");
		addLabBtn = new JButton("Добавить лабораторное занятие");
		MainWindow mainWindow = this;

		addPhotoBtn.addActionListener(e -> {
			if (studentTable.getSelectedRow() != -1) {
				AddPhotoDialog addPhotoDialog = new AddPhotoDialog(mainWindow, getCurrentStudent());
				addPhotoDialog.setVisible(true);
			}
		});
		addStudentBtn.addActionListener(e -> {
			AddStudentDialog addStudentDialog = new AddStudentDialog(mainWindow);
			addStudentDialog.setVisible(true);
		});
		deleteStudentBtn.addActionListener(e -> {
			int selectedRow = studentTable.getSelectedRow();
			if (selectedRow != -1) {
				String photoPath = ((StudentTableModel) studentTable.getModel())
						.getStudentAt(selectedRow).getPhotoPath();
				if (photoPath != null) {
					File fileToDelete = new File(photoPath);
					if (fileToDelete.delete()) {
						System.out.println("File deleted successfully.");
					} else {
						System.out.println("Failed to delete the file.");
					}
				}
				if (MySQLConnector.deleteStudent(((StudentTableModel) studentTable.getModel())
						.getStudentAt(selectedRow).getId())) {
					getCurrentGroup().getStudents().remove(getCurrentStudent());
					System.out.println("Student was deleted");
				}
			}
		});
		addGroupBtn.addActionListener(e -> {
			AddGroupDialog addGroupDialog = new AddGroupDialog(mainWindow);
			addGroupDialog.setVisible(true);
		});
		deleteGroupBtn.addActionListener(e -> {
			DeleteGroupDialog deleteGroupDialog = new DeleteGroupDialog(mainWindow);
			deleteGroupDialog.setVisible(true);
		});
		aboutAuthorBtn.addActionListener(e -> {
			AboutDialog aboutAuthorDialog = new AboutDialog(mainWindow);
			aboutAuthorDialog.setVisible(true);
		});
		addLabBtn.addActionListener(e -> {
			AddLabDialog addLabDialog = new AddLabDialog(mainWindow);
			addLabDialog.setVisible(true);
			addLabDialog.getAddButton().addActionListener(e1 -> {
				int selectedRow = studentTable.getSelectedRow();
				long id = Long.parseLong(studentTable.getValueAt(selectedRow, 4).toString());
			});
		});
		studentTable = new JTable(new StudentTableModel());
		StudentTableModel studentTableModel = (StudentTableModel) studentTable.getModel();
		studentTableModel.setData(((Group) groupNumberCmb.getSelectedItem()).getStudents());
		studentTable.setDefaultRenderer(StudentTableCellRender.class, new StudentTableCellRender());
		JScrollPane scrollPane = new JScrollPane(studentTable);


		studentTable.getSelectionModel().addListSelectionListener(e -> {
			int selectedRow = studentTable.getSelectedRow();
			Student selectedStudent = ((StudentTableModel) studentTable.getModel()).getStudentAt(selectedRow);
			StudentCardPanel studentPanel = (StudentCardPanel) optionPane.getMessage();

// Обновить данные о студенте на пользовательской панели
			studentPanel.update(selectedStudent);

// Обновить значение JOptionPane, чтобы отобразить обновленные данные
			optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);
			dialog.setVisible(true);
			studentTable.setFocusable(true);

		});


		// Добавить слушателя для кнопки "OK"


		// подписка на события клавиатуры
		studentTable.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					// Отобразить информацию о предыдущем студенте
					StudentCardPanel studentPanel = (StudentCardPanel) optionPane.getMessage();

// Обновить данные о студенте на пользовательской панели
					studentPanel.update(getCurrentStudent());

// Обновить значение JOptionPane, чтобы отобразить обновленные данные
					optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);

				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					// Отобразить информацию о следующем студенте
					StudentCardPanel studentPanel = (StudentCardPanel) optionPane.getMessage();

// Обновить данные о студенте на пользовательской панели
					studentPanel.update(getCurrentStudent());

// Обновить значение JOptionPane, чтобы отобразить обновленные данные
					optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);
				}
			}
		});


		TableColumn column = studentTable.getColumnModel().getColumn(5);
		column.setMinWidth(0);
		column.setMaxWidth(0);
		column.setWidth(0);
		column.setPreferredWidth(0);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		getContentPane().setLayout(groupLayout);
		groupLayout.setAutoCreateGaps(true);
		groupLayout.setAutoCreateContainerGaps(true);
		// Компановка главной панели
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup()
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(groupNumberLbl)
						.addComponent(groupNumberCmb)
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
								.addComponent(addStudentBtn)
								.addComponent(addGroupBtn)
								.addComponent(deleteGroupBtn)
								.addComponent(deleteStudentBtn)
								.addComponent(aboutAuthorBtn)
								.addComponent(addPhotoBtn)
								.addComponent(addLabBtn))
				)
		);
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup()
				.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup()
								.addComponent(groupNumberLbl)
								.addComponent(groupNumberCmb)
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
						.addComponent(addStudentBtn)
						.addComponent(addGroupBtn)
						.addComponent(deleteGroupBtn)
						.addComponent(deleteStudentBtn)
						.addComponent(aboutAuthorBtn)
						.addComponent(addPhotoBtn)
						.addComponent(addLabBtn))
		);
		// Добавление слушателей
		groupNumberCmb.addActionListener(e -> {
			StudentTableModel studentTableModel1 = (StudentTableModel) studentTable.getModel();
			studentTableModel1.setData(getCurrentGroup().getStudents());
		});
	}

	public JComboBox<Group> getGroupNumberCmb() {
		return groupNumberCmb;
	}

	public JTable getStudentTable() {
		return studentTable;
	}

	public JButton getAddStudentBtn() {
		return addStudentBtn;
	}

	protected long getSelectedStudentID() {
		int selectedRow = studentTable.getSelectedRow();
		long studentId = -1;
		if (selectedRow != -1) {
			studentId = ((StudentTableModel) studentTable.getModel()).getStudentAt(selectedRow).getId();
		}
		return studentId;
	}

	public static MainWindow getInstance() {
		if (mainWindow == null) {
			mainWindow = new MainWindow();
		}
		return mainWindow;
	}

	private Student getCurrentStudent() {

		return ((StudentTableModel) studentTable.getModel()).getStudentAt(studentTable.getSelectedRow());
	}

	private Group getCurrentGroup() {
		return (Group) groupNumberCmb.getSelectedItem();
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
