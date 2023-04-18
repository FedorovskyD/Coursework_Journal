package MainFrame;

import connection.MySQLConnector;
import dialogs.*;
import entity.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
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
	private JComboBox<String> groupNumberCmb;
	private JButton addStudentBtn, addGroupBtn, deleteGroupBtn,
			aboutAuthorBtn, deleteStudentBtn, addPhotoBtn, addLabBtn;
	private JTable studentTable;
	private StudentCardDialog studentCardDialog;

	private MainWindow() {
		//Создаем панель с информацией о студенте
		ImageIcon imageIcon = new ImageIcon("img/s.png");

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
		groupNumberCmb = new JComboBox<>(MySQLConnector.getAllGroupNumbers().toArray(new String[0]));
		groupNumberCmb.setPreferredSize(new Dimension(100, 24));
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
				long id = Long.parseLong(studentTable.getValueAt(selectedRow, 4).toString()); // Получаем данные из выделенной строки
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
				long id = Long.parseLong(studentTable.getValueAt(selectedRow, 4).toString()); // Получаем данные из выделенной строки
				String selectedGroup = (String) mainWindow.getGroupNumberCmb().getSelectedItem();
				String photoPath = MySQLConnector.getStudentById(id).getPhotoPath();
				if (photoPath != null) {
					File fileToDelete = new File(photoPath);
					if (fileToDelete.delete()) {
						System.out.println("File deleted successfully.");
					} else {
						System.out.println("Failed to delete the file.");
					}
				}
				if (MySQLConnector.deleteStudent(id)) {
					System.out.println("Student was deleted");
				}
				updStudentTable(studentTable, selectedGroup);
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
		// Создаем таблицу для отображения студентов
		// Создание заголовков столбцов
		String[] columns = {"Фамилия", "Имя", "Отчество", "Почта", "ID"};
		// Запрещаем редактирование таблицы
		DefaultTableModel model = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		// Установка заголовков
		model.setColumnIdentifiers(columns);
		// Создание таблицы и установка модели
		studentTable = new JTable(model);
		studentTable.setPreferredSize(new Dimension(800, 920));
		studentTable.setMaximumSize(studentTable.getPreferredSize());
		studentTable.setMaximumSize(studentTable.getPreferredSize());
		JScrollPane scrollPane = new JScrollPane(studentTable);
		studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Выбор только одной строки
		studentTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				if (isSelected) {
					setBackground(Color.YELLOW); // Задаем желтый фон для выделенной строки
				} else {
					setBackground(table.getBackground()); // Возвращаем цвет фона по умолчанию
				}
				return this;
			}
		});

		studentTable.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				int selectedRow = studentTable.getSelectedRow();
				if (selectedRow != -1) {
					long studentId = Long.parseLong(studentTable.getValueAt(selectedRow, 4).toString()); // Получаем данные из выделенной строки
					if(studentCardDialog==null) {
						studentCardDialog = new StudentCardDialog(mainWindow, "Карточка", MySQLConnector.getStudentById(studentId));
					}
					studentCardDialog.setVisible(true);
				}
			}
		});
		// подписка на события клавиатуры
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(event -> {
			if (event.getID() == KeyEvent.KEY_RELEASED && (event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_DOWN)) {
				if (event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_DOWN) {
					// получаем текущую выделенную строку в таблице
					int selectedRow = studentTable.getSelectedRow();
					// вычисляем номер следующей строки в зависимости от нажатой клавиши
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
					}
				}
			}
			return true;
		});


		TableColumn column = studentTable.getColumnModel().getColumn(4);
		column.setMinWidth(0);
		column.setMaxWidth(0);
		column.setWidth(0);
		column.setPreferredWidth(0);
		// Заполнение таблицы начальными данными
		String selectedGroup = (String) groupNumberCmb.getSelectedItem();
		updStudentTable(studentTable, selectedGroup);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		getContentPane().setLayout(groupLayout);
		groupLayout.setAutoCreateGaps(true);
		groupLayout.setAutoCreateContainerGaps(true);
		// Компановка главной панели
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup()
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(groupNumberLbl)
						.addComponent(groupNumberCmb))
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
								.addComponent(groupNumberCmb))
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
			String selectedGroup1 = (String) mainWindow.getGroupNumberCmb().getSelectedItem();
			updStudentTable(studentTable, selectedGroup1);
		});
	}

	public JComboBox<String> getGroupNumberCmb() {
		return groupNumberCmb;
	}

	public JTable getStudentTable() {
		return studentTable;
	}

	public JButton getAddStudentBtn() {
		return addStudentBtn;
	}

	protected static void updStudentTable(JTable studentTable, String selectedGroup) {
		List<Student> students = MySQLConnector.getAllStudentsByGroup(selectedGroup);
		DefaultTableModel model1 = (DefaultTableModel) studentTable.getModel();
		model1.setRowCount(0); // удаление всех строк
		for (Student student : students) {
			model1.addRow(new Object[]{student.getSurname(), student.getName(), student.getMiddleName(), student.getEmail(), student.getId()});
		}
	}

	protected String getSelectedGroup() {
		return groupNumberCmb.getSelectedItem().toString();
	}

	protected long getSelectedStudentID() {
		int selectedRow = studentTable.getSelectedRow();
		long studentId = -1;
		if (selectedRow != -1) {
			studentId = Long.parseLong(studentTable.getValueAt(selectedRow, 4).toString());
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
