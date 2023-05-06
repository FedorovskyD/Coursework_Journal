package dialogs;

import MainFrame.MainWindow;
import entity.Group;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JDialogAddLesson extends JDialog {

	private final JTextField nameField;
	private JRadioButton radioButtonLab;
	private JRadioButton radioButtonLecture;
	private JSpinner dateSpinner;
	private JTextField roomField;
	private JComboBox<Group> groupComboBox;
	protected final JButton addButton, cancelButton;
	private boolean isAddButtonPressed = false;
	protected final MainWindow mainWindow;

	public JDialogAddLesson(JFrame parent) {
		super(parent, "Добавление лабораторного занятия", true);
		mainWindow = (MainWindow) parent;
		JLabel lessonTypeLabel = new JLabel("Тип занятия:");
		JLabel nameLabel = new JLabel("Название:");
		JLabel roomLabel = new JLabel("Аудитория:");
		JLabel dateLabel = new JLabel("Дата:");
		JLabel groupLabel = new JLabel("Группа:");
		radioButtonLab = new JRadioButton("Лабораторная",true);
		radioButtonLecture = new JRadioButton("Лекция");
		ButtonGroup buttonGroupLessonType = new ButtonGroup();
		buttonGroupLessonType.add(radioButtonLab);
		buttonGroupLessonType.add(radioButtonLecture);
		JPanel panelLessonType = new JPanel(new GridLayout(1,2));
		panelLessonType.add(radioButtonLab);
		panelLessonType.add(radioButtonLecture);
		nameField = new JTextField(20);
		roomField = new JTextField(20);
		groupComboBox = new JComboBox<>(new DefaultComboBoxModel<>(mainWindow.getGroups().toArray(new Group[0])));
		groupComboBox.setSelectedItem(mainWindow.getCurrentGroup());
		SpinnerDateModel dateModel = new SpinnerDateModel();
		dateSpinner = new JSpinner(dateModel);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, dateFormat.toPattern());
		dateSpinner.setEditor(dateEditor);

		addButton = new JButton("Добавить");
		cancelButton = new JButton("Отмена");

		JPanel panel = new JPanel(new GridLayout(6, 2));
		panel.add(lessonTypeLabel);
		panel.add(panelLessonType);
		panel.add(nameLabel);
		panel.add(nameField);
		panel.add(roomLabel);
		panel.add(roomField);
		panel.add(dateLabel);
		panel.add(dateSpinner);
		panel.add(groupLabel);
		panel.add(groupComboBox);
		panel.add(addButton);
		panel.add(cancelButton);
		cancelButton.addActionListener(e -> {
			dispose();
		});
		ListenerJDialogAddLesson listenerJDialogAddLesson = new ListenerJDialogAddLesson(this);
		addButton.addActionListener(listenerJDialogAddLesson);
		this.setContentPane(panel);
		this.pack();
		this.setLocationRelativeTo(null);
	}

	public JTextField getNameField() {
		return nameField;
	}

	public JTextField getRoomField() {
		return roomField;
	}

	public JComboBox<Group> getGroupComboBox() {
		return groupComboBox;
	}

	public Date getDate() {
		return ((SpinnerDateModel)dateSpinner.getModel()).getDate();
	}

	public JRadioButton getRadioButtonLab() {
		return radioButtonLab;
	}

	public JRadioButton getRadioButtonLecture() {
		return radioButtonLecture;
	}
}
