package MainFrame;

import MainFrame.studentTable.StudentLabTableModel;
import MainFrame.studentTable.StudentTable;
import database.dao.impl.GroupDaoImpl;
import dialogs.studentCard.JDialogStudentCard;
import entity.Group;
import entity.Lesson;
import entity.Student;
import utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Objects;


public class MainWindow extends JFrame {
	private final JButton btnAddStudent, btnAddLab,
			btnAddGroup, btnDeleteGroup;
	protected StudentTable studentTable;
	protected JDialogStudentCard jDialogStudentCard;
	private final JRadioButton radioBtnLecture, radioBtnLab, radioBtnInc, radioBtnDec;
	private final JComboBox<Group> cmbGroupNumber;
	private final JComboBox<Lesson> currDateCmb;
	private final JComboBox<String> cmbSort;
	private final List<Group> groups;
	private final MainWindowListener mainWindowListener;
	protected final JScrollPane scrollPane;
	protected Student currStudent;

	private MainWindow() {
		//Получаем данные о группах из базы данных
		groups = GroupDaoImpl.getInstance().findAll();
		//Создаем элементы гланого окна
		JLabel currDateLbl = new JLabel("Текущая дата");
		ButtonGroup buttonGroupLessonType = new ButtonGroup();
		radioBtnLab = new JRadioButton("Лекции",false);
		radioBtnLecture = new JRadioButton("Лабораторные",true);
		buttonGroupLessonType.add(radioBtnLab);
		buttonGroupLessonType.add(radioBtnLecture);
		ButtonGroup buttonGroupSortType = new ButtonGroup();
		radioBtnInc = new JRadioButton("в порядке возрастания",true);
		radioBtnDec = new JRadioButton("в поряке убывания",false);
		buttonGroupSortType.add(radioBtnInc);
		buttonGroupSortType.add(radioBtnDec);
		JLabel lblSort = new JLabel("Сортировать по: ");
		cmbSort = new JComboBox<>(new DefaultComboBoxModel<>(new String[]{Constants.SORT_BY_ALPHABET, Constants.SORT_BY_GRADE, Constants.SORT_BY_ATTENDANCE}));
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
		cmbGroupNumber = new JComboBox<>(new DefaultComboBoxModel<>(groups.toArray(new Group[0])));
		cmbGroupNumber.setPreferredSize(new Dimension(100, 30));
		cmbGroupNumber.setMaximumSize(cmbGroupNumber.getPreferredSize());
		cmbGroupNumber.setMinimumSize(cmbGroupNumber.getPreferredSize());
		// Создаем кнопки
		btnAddStudent = new JButton("Добавить студента");
		btnAddGroup = new JButton("Добавить группу");
		btnDeleteGroup = new JButton("Удалить группу");
		btnAddLab = new JButton("Добавить занятие");
		//Создаем combobox для выбора даты занятия
		currDateCmb = new JComboBox<>(new DefaultComboBoxModel<>(getCurrentGroup().getLabs().toArray(new Lesson[0])));
		currDateCmb.setPreferredSize(new Dimension(100, 30));
		currDateCmb.setMinimumSize(currDateCmb.getPreferredSize());
		currDateCmb.setMaximumSize(currDateCmb.getPreferredSize());
		//Создаем таблицу для отображения списка студентов
		studentTable = new StudentTable(getCurrGroup());
		studentTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		refreshStudentTable();
		scrollPane = new JScrollPane(studentTable);
		studentTable.getColumnModel().getColumn(0).setPreferredWidth(300);
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
		mainWindowListener = new MainWindowListener(this);
		btnAddStudent.addActionListener(mainWindowListener);
		btnAddGroup.addActionListener(mainWindowListener);
		btnDeleteGroup.addActionListener(mainWindowListener);
		btnAddLab.addActionListener(mainWindowListener);
		studentTable.getSelectionModel().addListSelectionListener(mainWindowListener);
		cmbGroupNumber.addActionListener(mainWindowListener);
		radioBtnDec.addActionListener(e -> {
			refreshStudentTable();
			if(jDialogStudentCard.isVisible()){
				jDialogStudentCard.getCurrLabButton().requestFocus();
			}
		});
		radioBtnInc.addActionListener(e -> {
			refreshStudentTable();
			if(jDialogStudentCard.isVisible()){
				jDialogStudentCard.getCurrLabButton().requestFocus();
			}
		});
		cmbSort.addActionListener(e -> refreshStudentTable());
		studentTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = studentTable.rowAtPoint(e.getPoint());
				if (row >= 0) {
					Object value = studentTable.getValueAt(row, 0);
					// обработка щелчка на ячейке
					if(value instanceof Student student){
						if(student == currStudent){
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
			return studentTable.getStudentAt(studentTable.getSelectedRow());
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
	public Lesson getCurrDate(){
		return (Lesson) currDateCmb.getSelectedItem();
	}
	public Group getCurrGroup(){
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
		studentTable.setModel(new StudentLabTableModel(getCurrGroup()));
		sortTable();
		if(currStudent!=null){
			int index = studentTable.getStudentTableModel().getRowIndex(currStudent);
			if(index!=-1) {
				studentTable.setRowSelectionInterval(index,index);
				studentTable.revalidate();
				studentTable.repaint();
			}
		}else {
			Student student = studentTable.getStudentAt(0);
			if(student!=null){
				currStudent = student;
				studentTable.setRowSelectionInterval(0,0);
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
			case Constants.SORT_BY_ATTENDANCE -> studentTable.getStudentTableModel().sortByAttendance(isInc);
		}
	}

	public void updateGroupCmb() {
		cmbGroupNumber.setModel(new DefaultComboBoxModel<>(groups.toArray(new Group[0])));
	}

	public void updateCurrDateCmb() {
		Group currGroup = (Group) cmbGroupNumber.getSelectedItem();
		if (currGroup != null) {
			currDateCmb.setModel(new DefaultComboBoxModel<>(currGroup.getLabs().toArray(new Lesson[0])));
		}else {
			System.out.println("Не удалось обновить даты занятий");
		}
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
