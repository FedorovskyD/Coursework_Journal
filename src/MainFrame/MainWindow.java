package MainFrame;

import connection.MySQLConnector;
import dialogs.*;
import entity.Group;
import entity.Student;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.KeyEvent;
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
		groups = MySQLConnector.getAllGroups();
		labLbl = new JLabel("Лабораторные");
		lectureLbl = new JLabel("Лекции");
		currDateLbl = new JLabel("Текущая дата");
		currDateCmb = new JComboBox<>();
		currDateCmb.setPreferredSize(new Dimension(200,30));
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
			int selectedRow = studentTable.getSelectedRow();
			if (selectedRow != -1) {
				long id = ((StudentTableModel)studentTable.getModel()).getStudentAt(selectedRow).getId(); // Получаем данные из выделенной строки
				AddPhotoDialog addPhotoDialog = new AddPhotoDialog(mainWindow, id);
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
				String photoPath = ((StudentTableModel)studentTable.getModel())
						.getStudentAt(selectedRow).getPhotoPath();
				if (photoPath != null) {
					File fileToDelete = new File(photoPath);
					if (fileToDelete.delete()) {
						System.out.println("File deleted successfully.");
					} else {
						System.out.println("Failed to delete the file.");
					}
				}
				if (MySQLConnector.deleteStudent(((StudentTableModel)studentTable.getModel())
						.getStudentAt(selectedRow).getId())) {
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
		studentTableModel.setData(((Group)groupNumberCmb.getSelectedItem()).getStudents());
		studentTable.setDefaultRenderer(StudentTableCellRender.class,new StudentTableCellRender());
		JScrollPane scrollPane = new JScrollPane(studentTable);
		// подписка на события клавиатуры
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

							// получаем данные следующего студента
							Student nextStudent = MySQLConnector.getStudentById(getSelectedStudentID());

							// обновляем данные в диалоговом окне
							studentCardDialog.updateData(nextStudent);

							// прокручиваем таблицу к следующей строке
							studentTable.scrollRectToVisible(studentTable.getCellRect(nextRow, 0, true));
						} else {
							// если следующей строки нет, то закрываем диалоговое окно
							studentCardDialog.dispose();
							studentTable.clearSelection();
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
			Group group = (Group) groupNumberCmb.getSelectedItem();
			StudentTableModel studentTableModel1 = (StudentTableModel) studentTable.getModel();
			studentTableModel1.setData(group.getStudents());
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

	protected String getSelectedGroup() {
		return groupNumberCmb.getSelectedItem().toString();
	}

	protected long getSelectedStudentID() {
		int selectedRow = studentTable.getSelectedRow();
		long studentId = -1;
		if (selectedRow != -1) {
			 studentId = ((StudentTableModel)studentTable.getModel()).getStudentAt(selectedRow).getId();
		}
		return studentId;
	}

	public static MainWindow getInstance() {
		if (mainWindow == null) {
			mainWindow = new MainWindow();
		}
		return mainWindow;
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
