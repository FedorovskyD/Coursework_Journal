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
	private JLabel lblGroupNumber;
	private JComboBox<Group> CmbGroupNumber;
	private JButton btnAddStudent, btnAddGroup, btnDeleteGroup,
			btnAboutAuthor, btnDeleteStudent, btnAddPhoto, btnAddLab;
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
		lblGroupNumber = new JLabel("Номер группы:");
		CmbGroupNumber = new JComboBox<>(new DefaultComboBoxModel<>(groups.toArray(new Group[0])));
		CmbGroupNumber.setPreferredSize(new Dimension(100, 30));
		CmbGroupNumber.setMaximumSize(CmbGroupNumber.getPreferredSize());
		CmbGroupNumber.setMinimumSize(CmbGroupNumber.getPreferredSize());
		// Создаем кнопки
		btnAddStudent = new JButton("Добавить студента");
		btnAddGroup = new JButton("Добавить группу");
		btnDeleteGroup = new JButton("Удалить группу");
		btnAboutAuthor = new JButton("Об авторе");
		btnDeleteStudent = new JButton("Удалить студента");
		btnAddPhoto = new JButton("Добавить фото студента");
		btnAddLab = new JButton("Добавить лабораторное занятие");
		MainWindow mainWindow = this;

		studentCardDialog = new StudentCardDialog(mainWindow, "Карточка студента", new Student());
		btnAddPhoto.addActionListener(e -> {
			if (studentTable.getSelectedRow() != -1) {
				AddPhotoDialog addPhotoDialog = new AddPhotoDialog(mainWindow, getCurrentStudent());
				addPhotoDialog.setVisible(true);
			}
		});
		btnAddStudent.addActionListener(e -> {
			AddStudentDialog addStudentDialog = new AddStudentDialog(mainWindow);
			addStudentDialog.setVisible(true);
		});
		btnDeleteStudent.addActionListener(e -> {
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
		btnAddGroup.addActionListener(e -> {
			AddGroupDialog addGroupDialog = new AddGroupDialog(mainWindow);
			addGroupDialog.setVisible(true);
		});
		btnDeleteGroup.addActionListener(e -> {
			DeleteGroupDialog deleteGroupDialog = new DeleteGroupDialog(mainWindow);
			deleteGroupDialog.setVisible(true);
		});
		btnAboutAuthor.addActionListener(e -> {
			AboutDialog aboutAuthorDialog = new AboutDialog(mainWindow);
			aboutAuthorDialog.setVisible(true);
		});
		btnAddLab.addActionListener(e -> {
			AddLabDialog addLabDialog = new AddLabDialog(mainWindow);
			addLabDialog.setVisible(true);
			addLabDialog.getAddButton().addActionListener(e1 -> {
				int selectedRow = studentTable.getSelectedRow();
				long id = Long.parseLong(studentTable.getValueAt(selectedRow, 4).toString());
			});
		});
		studentTable = new JTable(new StudentTableModel());
		StudentTableModel studentTableModel = (StudentTableModel) studentTable.getModel();
		studentTableModel.setData(((Group) CmbGroupNumber.getSelectedItem()).getStudents());
		studentTable.setDefaultRenderer(StudentTableCellRender.class, new StudentTableCellRender());
		JScrollPane scrollPane = new JScrollPane(studentTable);
		studentTable.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP ||
						e.getKeyCode() == KeyEvent.VK_DOWN)
				{
					e.consume();
				}
			}
		});


		studentTable.getSelectionModel().addListSelectionListener(e -> {
			int selectedRow = studentTable.getSelectedRow();
			if(selectedRow != -1) {
				Student selectedStudent = ((StudentTableModel) studentTable.getModel()).getStudentAt(selectedRow);
				selectedStudent.setGroup(getCurrentGroup());
				studentCardDialog.getStudentCardPanel().update(selectedStudent);
				SwingUtilities.invokeLater(() -> studentCardDialog.setVisible(true));
			}

		});
		studentCardDialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				studentCardDialog.setVisible(false);
			//	studentTable.clearSelection();
			}
		});
		studentCardDialog.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
					studentCardDialog.setVisible(false);
					//studentTable.clearSelection();
				}
			}
		});
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(event -> {
			if (event.getID() == KeyEvent.KEY_RELEASED && (event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_DOWN)) {
				if (event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_DOWN) {
					// получаем текущую выделенную строку в таблице
					int selectedRow = studentTable.getSelectedRow();
					// вычисляем номер следующей строки в зависимости от нажатой клавиши
					if (selectedRow != -1) {
						int nextRow = event.getKeyCode() == KeyEvent.VK_UP ? selectedRow - 1 : selectedRow + 1;
						// проверяем, что следующая строка существует
						if (nextRow >= 0 && nextRow < studentTable.getRowCount()) {
							// обновляем выделение строки в таблице
							studentTable.setRowSelectionInterval(nextRow, nextRow);
							// прокручиваем таблицу к следующей строке
							studentTable.scrollRectToVisible(studentTable.getCellRect(nextRow, 0, true));
						}
					}
				}
			}
			return false;
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
						.addComponent(lblGroupNumber)
						.addComponent(CmbGroupNumber)
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
								.addComponent(lblGroupNumber)
								.addComponent(CmbGroupNumber)
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
		CmbGroupNumber.addActionListener(e -> {
			StudentTableModel studentTableModel1 = (StudentTableModel) studentTable.getModel();
			studentTableModel1.setData(getCurrentGroup().getStudents());
		});
	}

	public JComboBox<Group> getCmbGroupNumber() {
		return CmbGroupNumber;
	}

	public JTable getStudentTable() {
		return studentTable;
	}

	public JButton getBtnAddStudent() {
		return btnAddStudent;
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
		return (Group) CmbGroupNumber.getSelectedItem();
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
