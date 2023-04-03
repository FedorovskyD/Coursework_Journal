package MainFrame;

import connection.MySQLConnector;
import dialogs.*;
import entity.Lab;
import entity.Student;
import utils.PhotoUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

public class MainWindow extends JFrame {
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JLabel groupNumberLbl;
	private JComboBox<String> groupNumberCmb;
	private StudentCardPanel studentCard;
	private JButton addStudentBtn, addGroupBtn, deleteGroupBtn,
			aboutAuthorBtn, deleteStudentBtn, addPhotoBtn, addLabBtn;
	private JTable studentTable;
	private JPanel backGround;
	private JLabel bgLabel;

	public MainWindow() {
		//Создаем панель с информацией о студенте
		studentCard = new StudentCardPanel();
		backGround = new JPanel();
		backGround.setPreferredSize(new Dimension(800,1000));
		backGround.setMaximumSize(backGround.getPreferredSize());
		backGround.setMinimumSize(backGround.getPreferredSize());
		studentCard.setVisible(false);
		ImageIcon imageIcon = new ImageIcon("img/s.png");
		bgLabel = new JLabel("",imageIcon,JLabel.CENTER);
		backGround.add(bgLabel);
		backGround.add(studentCard);
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
				updateStudentCard(studentCard,id);
				bgLabel.setVisible(true);
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
		studentTable.setPreferredSize(new Dimension(800,920));
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
					long data = Long.parseLong(studentTable.getValueAt(selectedRow, 4).toString()); // Получаем данные из выделенной строки
					updateStudentCard(studentCard, data);
					bgLabel.setVisible(false);
					String currGroup = (String) groupNumberCmb.getSelectedItem();
					List<Lab> labs = MySQLConnector.getAllLabByGroup(currGroup);
					studentCard.getCalendarPanel().setLayout(new GridLayout(4, 5, 5, 5)); // задаем сетку для кнопок
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
						.addComponent(backGround)
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
				.addComponent(backGround)
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
				updateStudentCard(studentCard,-1);
				updStudentTable(studentTable, selectedGroup);
				bgLabel.setVisible(true);
			}
		});
	}

	public JComboBox<String> getGroupNumberCmb() {
		return groupNumberCmb;
	}

	public StudentCardPanel getStudentCard() {
		return studentCard;
	}

	public JTable getStudentTable() {
		return studentTable;
	}

	public JButton getAddStudentBtn() {
		return addStudentBtn;
	}

	protected static void updStudentTable(JTable studentTable, String selectedGroup) {
		List<Student> students = MySQLConnector.getAllStudentsByGroup(selectedGroup);
		DefaultTableModel model = (DefaultTableModel) studentTable.getModel();
		model.setRowCount(0); // удаление всех строк
		for (Student student : students) {
			model.addRow(new Object[]{student.getSurname(), student.getName(), student.getMiddleName(), student.getEmail(), student.getId()});
		}
	}

	protected static void updateStudentCard(StudentCardPanel studentCard, long studentID) {
		Student student = MySQLConnector.getStudentById(studentID);
		if (student != null) {
			studentCard.getFullNameLabel().setText(student.getSurname() + " " + student.getName() +
					" " + student.getMiddleName());
			studentCard.getEmailLabel().setText(student.getEmail());
			studentCard.getPhoneLabel().setText(student.getTelephone());
			JLabel photoLabel = studentCard.getPhotoLabel();
			Image image = PhotoUtils.getInstance().loadPhoto(student).getImage();
			if (image != null) {
				photoLabel.setSize(new Dimension(160, 200));
				ImageIcon icon = new ImageIcon(image.getScaledInstance(photoLabel.getWidth(), photoLabel.getHeight(), Image.SCALE_SMOOTH));
				photoLabel.setIcon(icon);
			} else {
				photoLabel.setSize(new Dimension(0, 0));
			}
			studentCard.getCalendarPanel().removeAll() ;
			studentCard.getCalendarPanel().revalidate();
			studentCard.getCalendarPanel().repaint();
			studentCard.setVisible(true);
		}else {
			studentCard.getFullNameLabel().setText("");
			studentCard.getEmailLabel().setText("");
			studentCard.getPhoneLabel().setText("");
			studentCard.getPhotoLabel().setText("");
			studentCard.getCalendarPanel().removeAll() ;
			studentCard.getCalendarPanel().revalidate();
			studentCard.getCalendarPanel().repaint();
			studentCard.setVisible(false);
		}
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
