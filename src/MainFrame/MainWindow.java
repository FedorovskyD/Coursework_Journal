package MainFrame;

import dialogs.*;
import connection.MySQLConnector;
import entity.Lab;
import entity.Student;
import utils.PhotoUtils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class MainWindow extends JFrame {
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JLabel groupNumberLbl;
	private JComboBox<String> groupNumberCmb;
	private StudentCardPanel studentCard;
	private JButton addStudentBtn, addGroupBtn, deleteGroupBtn,
			aboutAuthorBtn, deleteStudentBtn, addPhotoBtn, addLabBtn;

	public JComboBox<String> getGroupNumberCmb() {
		return groupNumberCmb;
	}

	public StudentCardPanel getStudentCard() {
		return studentCard;
	}

	public JTable getStudentTable() {
		return studentTable;
	}

	private JTable studentTable;

	public JButton getAddStudentBtn() {
		return addStudentBtn;
	}

	public MainWindow() {
		studentCard = new StudentCardPanel();

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
		groupNumberCmb.setPreferredSize(new Dimension(15, 20));
		groupNumberCmb.setMaximumSize(new Dimension(24, 15));
		// Создаем кнопки
		addStudentBtn = new JButton("Добавить студента");
		addGroupBtn = new JButton("Добавить группу");
		deleteGroupBtn = new JButton("Удалить группу");
		aboutAuthorBtn = new JButton("Об авторе");
		deleteStudentBtn = new JButton("Удалить студента");
		addPhotoBtn = new JButton("Добавить фото студента");
		addLabBtn = new JButton("Добавить лабораторное занятие");
		MainWindow mainWindow = this;
		addPhotoBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = studentTable.getSelectedRow();
				if (selectedRow != -1) {
					long id = Long.parseLong(studentTable.getValueAt(selectedRow, 4).toString()); // Получаем данные из выделенной строки
					AddPhotoDialog addPhotoDialog = new AddPhotoDialog(mainWindow, id);
					addPhotoDialog.setVisible(true);
				}
			}
		});
		addStudentBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddStudentDialog addStudentDialog = new AddStudentDialog(mainWindow);
				addStudentDialog.setVisible(true);
			}
		});
		deleteStudentBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = studentTable.getSelectedRow();
				if (selectedRow != -1) {
					long data = Long.parseLong(studentTable.getValueAt(selectedRow, 4).toString()); // Получаем данные из выделенной строки

					studentCard.getPhoneLabel().setText("");
					studentCard.getFullNameLabel().setText("");
					studentCard.getEmailLabel().setText("");
					studentCard.getPhotoLabel().setIcon(null);
					String selectedGroup = (String) mainWindow.getGroupNumberCmb().getSelectedItem();
					String photoPath = MySQLConnector.getStudentById(data).getPhotoPath();
					if (photoPath != null) {
						File fileToDelete = new File(photoPath);
						if (fileToDelete.delete()) {
							System.out.println("File deleted successfully.");
						} else {
							System.out.println("Failed to delete the file.");
						}
					}
					if (MySQLConnector.deleteStudent(data)) {
						System.out.println("Student was deleted");
					}
					List<Student> students = MySQLConnector.getAllStudentsByGroup(selectedGroup);
					DefaultTableModel model = (DefaultTableModel) mainWindow.getStudentTable().getModel();
					model.setRowCount(0); // удаление всех строк
					for (Student student : students) {
						model.addRow(new Object[]{student.getSurname(), student.getName(), student.getMiddleName(),
								student.getEmail(), student.getId()});
					}

				}
			}
		});
		addGroupBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddGroupDialog addGroupDialog = new AddGroupDialog(mainWindow);
				addGroupDialog.setVisible(true);
			}
		});
		deleteGroupBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DeleteGroupDialog deleteGroupDialog = new DeleteGroupDialog(mainWindow);
				deleteGroupDialog.setVisible(true);
			}
		});
		aboutAuthorBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AboutDialog aboutAuthorDialog = new AboutDialog(mainWindow);
				aboutAuthorDialog.setVisible(true);
			}
		});
		addLabBtn.addActionListener(e -> {
			AddLabDialog addLabDialog = new AddLabDialog(mainWindow);
			addLabDialog.setVisible(true);
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

		studentTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					int selectedRow = studentTable.getSelectedRow();
					if (selectedRow != -1) {
						long data = Long.parseLong(studentTable.getValueAt(selectedRow, 4).toString()); // Получаем данные из выделенной строки

						Student student = MySQLConnector.getStudentById(data);
						studentCard.getFullNameLabel().setText(student.getSurname() + " " + student.getName() +
								" " + student.getMiddleName());
						studentCard.getEmailLabel().setText(student.getEmail());
						studentCard.getPhoneLabel().setText(student.getTelephone());
						Image image = PhotoUtils.getInstance().loadPhoto(student).getImage();
						JLabel photoLabel = studentCard.getPhotoLabel();

						if (image != null) {
							photoLabel.setSize(new Dimension(160, 200));
							ImageIcon icon = new ImageIcon(image.getScaledInstance(photoLabel.getWidth(), photoLabel.getHeight(), Image.SCALE_SMOOTH));
							photoLabel.setIcon(icon);
						} else {
							photoLabel.setSize(new Dimension(0, 0));
						}
						String currGroup = (String) groupNumberCmb.getSelectedItem();
						List<Lab> labs = MySQLConnector.getAllLabByGroup(currGroup);
						studentCard.getCalendarPanel().setLayout(new GridLayout(4, 5,5,5)); // задаем сетку для кнопок


						// Создаем кнопки для каждой лабораторной работы и добавляем их на панель
						for (Lab lab : labs) {
							String labDate = lab.getDate().toString(); // получаем дату лабораторной работы
							int labGrade = MySQLConnector.getGradeByLessonIDAndStudentID(lab.getId(), Long.parseLong(studentTable.getValueAt(selectedRow, 4).toString())); // получаем оценку студента за лабораторную работу

							// Создаем новую кнопку с датой и оценкой студента
							JButton labButton = new JButton(labDate + " (" + labGrade + ")");

							// Добавляем слушателя событий, который будет обрабатывать нажатие на кнопку и нажатие правой кнопки мыши
							labButton.addMouseListener(new MouseAdapter() {
								@Override
								public void mousePressed(MouseEvent e) {
									if (e.getButton() == MouseEvent.BUTTON1) { // если была нажата левая кнопка мыши
										labButton.setBackground(Color.GREEN); // меняем цвет кнопки на зеленый
									} else if (e.getButton() == MouseEvent.BUTTON3) { // если была нажата правая кнопка мыши
										JPopupMenu popupMenu = new JPopupMenu(); // создаем контекстное меню
										JMenuItem setGradeMenuItem = new JMenuItem("Поставить оценку"); // создаем пункт меню "Поставить оценку"

										// Добавляем слушателя событий, который будет обрабатывать нажатие на пункт меню
										setGradeMenuItem.addActionListener(e1 -> {
											String grade = JOptionPane.showInputDialog("Введите оценку:"); // выводим диалоговое окно для ввода оценки
											labButton.setText(labDate + " (" + grade + ")"); // изменяем текст кнопки, добавляя в него новую оценку
											labButton.setBackground(Color.WHITE); // меняем цвет кнопки обратно на белый
										});

										popupMenu.add(setGradeMenuItem); // добавляем пункт меню в контекстное меню
										popupMenu.show(labButton, e.getX(), e.getY());
									}
								}
							});
							studentCard.getCalendarPanel().add(labButton); // добавляем кнопку на панель
						}
					}


				}
			}
		});
		TableColumn column = studentTable.getColumnModel().getColumn(4);

		column.setMinWidth(0);
		column.setMaxWidth(0);
		column.setWidth(0);
		column.setPreferredWidth(0);
		// Заполнение таблицы начальными данными
		String selectedGroup = (String) groupNumberCmb.getSelectedItem();
		List<Student> students = MySQLConnector.getAllStudentsByGroup(selectedGroup);
		model.setRowCount(0); // удаление всех строк
		for (Student student : students) {
			model.addRow(new Object[]{student.getSurname(), student.getName(), student.getMiddleName(), student.getEmail(), student.getId()});
		}
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
						.addComponent(studentCard)
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
				.addComponent(studentCard)
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


		groupNumberCmb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedGroup = (String) mainWindow.getGroupNumberCmb().getSelectedItem();
				List<Student> students = MySQLConnector.getAllStudentsByGroup(selectedGroup);
				DefaultTableModel model = (DefaultTableModel) mainWindow.getStudentTable().getModel();
				model.setRowCount(0); // удаление всех строк
				for (Student student : students) {
					model.addRow(new Object[]{student.getSurname(), student.getName(), student.getMiddleName(),
							student.getEmail(), student.getId()});
				}
				studentCard.getPhotoLabel().setIcon(null);
				studentCard.getPhotoLabel().setSize(new Dimension(0, 0));
				studentCard.getFullNameLabel().setText("");
				studentCard.getEmailLabel().setText("");
				studentCard.getPhoneLabel().setText("");
			}
		});
	}

	public static void main(String[] args) {
		JFrame mainFrame = new MainWindow();
		mainFrame.setTitle("Student journal");
		mainFrame.setSize(new Dimension(1920, 1080));
		mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}

}
